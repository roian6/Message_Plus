package com.example.david0926.messageplus;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david0926.messageplus.Auth.UserDB;
import com.example.david0926.messageplus.Auth.LoginActivity;
import com.example.david0926.messageplus.Chat.ChatPageActivity;
import com.example.david0926.messageplus.Chat.RecycleModel_ChatPage;
import com.example.david0926.messageplus.Dialog.Dialog_DevInfo;
import com.example.david0926.messageplus.Dialog.Dialog_User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //앱 전체를 담당하는 MainActivity


    //Firebase Database 가져오기
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final UserDB userDB = new UserDB(); //현재 유저 정보가 담긴 DB 가져오기

        Toolbar toolbar = findViewById(R.id.toolbar); //ToolBar 가져오기
        toolbar.setTitleTextColor(Color.WHITE); //Title 색상을 흰색으로 설정
        setSupportActionBar(toolbar); //toolbar를 ActionBar로 지정

        TabLayout tabLayout = findViewById(R.id.tabLayout); //탭 레이아웃 가져오기
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE); //선택된 탭을 표시하는 바의 색상을 흰색으로 설정

        View view1 = getLayoutInflater().inflate(R.layout.custom_tab, null); //커스텀 탭 아이템 가져오기
        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.icon_people);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1)); //People Custom Tab 추가

        View view2 = getLayoutInflater().inflate(R.layout.custom_tab, null); //커스텀 탭 아이템 가져오기
        view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.icon_chat);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2)); //Chat Custom Tab 추가

        View view3 = getLayoutInflater().inflate(R.layout.custom_tab, null); //커스텀 탭 아이템 가져오기
        view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.icon_setting);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view3)); //Setting Custom Tab 추가

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL); //탭 Gravity 설정

        final ViewPager pager = findViewById(R.id.pager); //ViewPager 선언

        //탭 Adapter 선언 및 pager의 어댑터로 설정
        final TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout)); //탭 전환 리스너 설정
        pager.getRootView().setBackgroundColor(Color.WHITE); //pager의 배경색을 흰색으로 설정
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) { //탭이 선택되었다면
                pager.setCurrentItem(tab.getPosition()); //선택된 탭으로 현재 탭 지정
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        databaseReference.child("message").addChildEventListener(new ChildEventListener() { //Firebase Database의 message 항목에 이벤트 리스너 생성
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { //message 항목에 값이 추가되었다면

                final RecycleModel_ChatPage model = dataSnapshot.getValue(RecycleModel_ChatPage.class); //추가된 값을 RecycleModel_ChatPage 양식으로 DB에서 가져옴
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(); //Firebase 현재 Auth 정보 가져오기
                FirebaseUser user = firebaseAuth.getCurrentUser(); //현재 유저 정보 가져오기
                GetTimeDate getTimeDate = new GetTimeDate(); //현재 시간/날짜 가져오기

                //메세지 수신 알림 구현
                if (!model.getName().equals(user.getEmail()) && model.getTo().equals(user.getEmail())) { //자신에게 온 메세지이고 자신이 작성한 게 아니라면
                    if (model.getTime().equals(getTimeDate.getTime()) && model.getDate().equals(getTimeDate.getDate())) { //만약 메세지를 보낸 시간과 날짜가 현재라면
                        CheckBackground checkBackground = new CheckBackground(); //앱이 백그라운드에 있는지 확인
                        if (checkBackground.isAppBackground(getApplicationContext())) { //앱이 백그라운드에 있다면(푸시 알림)
                            if (userDB.isSettingPush(getApplicationContext())) { //만약 설정에서 푸시 알림이 켜져 있다면
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //만약 안드로이드 버전이 오레오 이상이라면(푸시 양식 설정)
                                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); //NotificationManager 가져오기
                                    //노티 알림 생성/설정
                                    NotificationChannel notificationChannel = new NotificationChannel("ChannelID", "ChannelName", NotificationManager.IMPORTANCE_DEFAULT); //채널 ID/Name
                                    if (userDB.isSettingVib(getApplicationContext())) { //만약 설정에서 진동이 켜져 있다면
                                        notificationChannel.enableVibration(true); //진동 사용
                                    } else {
                                        notificationChannel.enableVibration(false); //진동 미사용
                                    }
                                    notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                                    notificationManager.createNotificationChannel(notificationChannel); //Manager에 Channel 설정

                                    //Notification Builder 생성/설정
                                    Notification.Builder builder = new Notification.Builder(getApplicationContext(), "ChannelID"); //채널 ID 설정
                                    builder.setContentTitle(model.getNickname() + " (" + model.getName() + ")") //푸시 제목 설정
                                            .setContentText(model.getMsg()) //내용 설정
                                            // 아이콘 설정
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                            .setAutoCancel(true) //앱 실행시 자동으로 알림 삭제
                                            .setVisibility(Notification.VISIBILITY_PUBLIC);

                                    Intent intent = new Intent(getApplicationContext(), ChatPageActivity.class); //ChatPageActivity로 Intent 선언
                                    //Intent에 Firebase DB에서 가져온 이메일/닉네임 첨부
                                    intent.putExtra("name", model.getName());
                                    intent.putExtra("nickname", model.getNickname());

                                    //PendingIntent 설정/builder 적용
                                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    builder.setContentIntent(pendingIntent);
                                    notificationManager.notify(1234, builder.build()); //푸시 알림 실행
                                } else { //안드로이드 버전이 오레오 미만이라면

                                    //오레오 버전 미만 양식으로 동일 푸시 설정
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                                            .setContentTitle(model.getNickname() + " (" + model.getName() + ")")
                                            .setContentText(model.getMsg())
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                            .setAutoCancel(true)
                                            .setVisibility(Notification.VISIBILITY_PUBLIC);
                                    if (userDB.isSettingVib(getApplicationContext())) { //만약 설정에서 진동이 켜져 있다면
                                        builder.setVibrate(new long[]{1000, 1000}); //진동 사용
                                    } else {
                                        builder.setVibrate(new long[]{0}); //진동 미사용
                                    }

                                    //Intent에 Firebase DB에서 가져온 이메일/닉네임 첨부
                                    Intent intent = new Intent(getApplicationContext(), ChatPageActivity.class);
                                    intent.putExtra("name", model.getName());
                                    intent.putExtra("nickname", model.getNickname());

                                    //PendingIntent 설정/builder 적용
                                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    builder.setContentIntent(pendingIntent);
                                    NotificationManager notificationManager =
                                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    notificationManager.notify(1234, builder.build()); //푸시 알림 실행
                                }
                            }

                        } else { //만약 앱이 백그라운드 상태가 아니라면(커스텀 스낵바 알림)
                            Snackbar snackbar = Snackbar.make(pager, model.getNickname() + ": " + model.getMsg(), Snackbar.LENGTH_LONG); //수신한 메세지로 스낵바 설정
                            snackbar.setAction("확인하기", new View.OnClickListener() { //스낵바에 확인 버튼 생성
                                @Override
                                public void onClick(View v) { //확인 버튼이 클릭되었을 때
                                    Intent intent = new Intent(getApplicationContext(), ChatPageActivity.class); //ChatPageActivity로 Intent 선언

                                    //Intent에 Firebase DB에서 가져온 이메일/닉네임 첨부
                                    intent.putExtra("name", model.getName());
                                    intent.putExtra("nickname", model.getNickname());
                                    startActivity(intent); //ChatPageActivity 실행
                                }
                            });
                            snackbar.setActionTextColor(Color.parseColor("#80cbc4")); //스낵바 버튼 글자 색상 설정
                            View snackBarView = snackbar.getView(); //스낵바 커스텀을 위해 뷰 가져오기
                            snackBarView.setBackgroundColor(Color.parseColor("#FFFFFF")); //배경색을 흰색으로 설정
                            snackBarView.setPadding(12, 12, 12, 12); //padding 설정
                            TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text); //스낵바의 텍스트 View 가져오기
                            textView.setBackground(getDrawable(R.drawable.snackbar_noti)); //텍스트뷰 배경을 메세지 수신 아이템 모양으로 설정
                            textView.setTextColor(Color.BLACK); //텍스트 색상을 검은색으로 설정
                            snackbar.show(); //스낵바 실행
                            if (userDB.isSettingVib(getApplicationContext())) { //만약 설정에서 진동이 켜져 있다면
                                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); //Vibrator 가져오기
                                vibrator.vibrate(200); //200ms 동안 진동
                            }
                        }
                    }
                }
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout); //네비게이션 드로어 설정

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( //액션바의 드로어 토글 버튼 설정
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //액션바의 토글 버튼에 드로어 실행 설정
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //네비게이션 드로어 가져오기
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View drawerHead = navigationView.getHeaderView(0); //네비게이션 드로어의 Head 부분 가져오기

        //Head 부분의 아이템 가져오기
        TextView drawerName, drawerEmail;
        CircleImageView drawerProfile;
        drawerName = drawerHead.findViewById(R.id.nav_title);
        drawerEmail = drawerHead.findViewById(R.id.nav_email);
        drawerProfile = drawerHead.findViewById(R.id.nav_profile_circle);

        //각 아이템에 현재 유저 정보가 담긴 DB에서 가져온 유저 정보를 대입
        drawerName.setText(userDB.getUserNickname(getApplicationContext()));
        drawerEmail.setText(userDB.getUserEmail(getApplicationContext()));
        drawerProfile.setImageDrawable(new ColorDrawable(userDB.getProfilenum(getApplicationContext())));

    }


    @Override
    public void onBackPressed() { //뒤로 가기 버튼이 클릭되었을 때
        DrawerLayout drawer = findViewById(R.id.drawer_layout); //네비게이션 드로어 가져오기
        if (drawer.isDrawerOpen(GravityCompat.START)) { //만약 드로어가 열려 있다면
            drawer.closeDrawer(GravityCompat.START); //드로어 닫기
        } else { //만약 닫혀 있다면
            super.onBackPressed(); //뒤로 가기 이벤트 반환
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //액션바의 메뉴 버튼이 클릭되었을 때
        getMenuInflater().inflate(R.menu.drawer, menu); //메뉴 실행
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //메뉴의 아이템이 클릭되었을 때

        int id = item.getItemId(); //클릭된 아이템의 id 가져오기

        if (id == R.id.action_devinfo) { //개발 정보 항목이 클릭되었다면
            //해당 Dialog 실행
            DialogFragment dialogFragment = new Dialog_DevInfo();
            dialogFragment.show(getSupportFragmentManager(), "dialog_devinfo");
        }
        if (id == R.id.action_logout) { //로그아웃 항목이 클릭되었다면
            FirebaseAuth.getInstance().signOut(); //Firebase의 현재 Auth 항목을 가져와 로그아웃 실행
            startActivity(new Intent(MainActivity.this, LoginActivity.class)); //로그인 액티비티 실행
            finish(); //현재 액티비티 종료
        }
        if (id == R.id.action_user) { //유저 항목이 클릭되었다면
            //해당 Dialog 실행
            DialogFragment dialogFragment = new Dialog_User();
            dialogFragment.show(getSupportFragmentManager(), "dialog_user");
        }

        return super.onOptionsItemSelected(item); //선택된 아이템 반환
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) { //네비게이션 바의 아이템이 선택되었다면

        int id = item.getItemId(); //선택된 아이템의 id 가져오기

        if (id == R.id.nav_user) { //유저 정보 항목 클릭
            //해당 Dialog 실행
            DialogFragment dialogFragment = new Dialog_User();
            dialogFragment.show(getSupportFragmentManager(), "dialog_user");
        } else if (id == R.id.nav_logout) { //로그아웃 항목 클릭
            FirebaseAuth.getInstance().signOut(); //Firebase의 현재 Auth 항목을 가져와 로그아웃 실행
            startActivity(new Intent(MainActivity.this, LoginActivity.class)); //로그인 액티비티 실행
            finish(); //현재 액티비티 종료
        } else if (id == R.id.nav_share) { //공유 항목 클릭
            Intent intent = new Intent(android.content.Intent.ACTION_SEND); //공유할 값을 담을 Intent 선언
            intent.setType("text/plain"); //Intent type 설정
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share)); //공유할 텍스트 설정
            Intent shareIntent = Intent.createChooser(intent, "공유하기"); //공유용 Intent 선언
            startActivity(shareIntent); //공유 화면 실행
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout); //드로어 가져오기
        drawer.closeDrawer(GravityCompat.START); //버튼이 클릭되었으므로 드로어 닫기
        return true;
    }
}
