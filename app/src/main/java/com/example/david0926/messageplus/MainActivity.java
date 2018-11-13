package com.example.david0926.messageplus;


import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david0926.messageplus.Auth.UserDB;
import com.example.david0926.messageplus.Auth.LoginActivity;
import com.example.david0926.messageplus.Auth.UserModel;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();



    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //user
        final UserDB userDB = new UserDB();

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);


        //Tab & ViewPager
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        View view1 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.icon_people);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));

        View view2 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.icon_chat);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));

        View view3 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.icon_setting);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view3));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager pager = findViewById(R.id.pager);
        final TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        pager.getRootView().setBackgroundColor(Color.WHITE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        databaseReference.child("message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                final RecycleModel_ChatPage model = dataSnapshot.getValue(RecycleModel_ChatPage.class);
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                GetTimeDate getTimeDate = new GetTimeDate();



                if (!model.getName().equals(user.getEmail()) && model.getTo().equals(user.getEmail())) {
                    if (model.getTime().equals(getTimeDate.getTime()) && model.getDate().equals(getTimeDate.getDate())) {
                        CheckBackground checkBackground = new CheckBackground();
                        if(checkBackground.isAppBackground(getApplicationContext())){
                            if(userDB.isSettingPush(getApplicationContext())){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    NotificationChannel notificationChannel = new NotificationChannel("ChannelID", "ChannelName", NotificationManager.IMPORTANCE_DEFAULT);
                                    notificationChannel.enableLights(true);
                                    notificationChannel.setLightColor(Color.GREEN);
                                    notificationChannel.enableVibration(true);
                                    notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                                    notificationManager.createNotificationChannel(notificationChannel);

                                    Notification.Builder builder = new Notification.Builder(getApplicationContext(), "ChannelID");

                                    builder.setContentTitle(model.getNickname() + " (" + model.getName() + ")")
                                            .setContentText(model.getMsg())
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                            .setAutoCancel(true)
                                            .setVisibility(Notification.VISIBILITY_PUBLIC);

                                    //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                                    Intent intent = new Intent(getApplicationContext(), ChatPageActivity.class);
                                    intent.putExtra("name", model.getName());
                                    intent.putExtra("nickname", model.getNickname());

                                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    builder.setContentIntent(pendingIntent);
                                    notificationManager.notify(1234, builder.build());


                                }
                                else {

                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                                            .setContentTitle(model.getNickname() + " (" + model.getName() + ")")
                                            .setContentText(model.getMsg())
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                            .setAutoCancel(true)
                                            .setVisibility(Notification.VISIBILITY_PUBLIC)
                                            .setVibrate(new long[]{1000, 1000});

                                    Intent intent = new Intent(getApplicationContext(), ChatPageActivity.class);
                                    intent.putExtra("name", model.getName());
                                    intent.putExtra("nickname", model.getNickname());
                                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                                    builder.setContentIntent(pendingIntent);
                                    NotificationManager notificationManager =
                                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    notificationManager.notify(1234, builder.build());
                                }
                            }

                        }
                        else {
                            Snackbar snackbar = Snackbar.make(pager, model.getNickname()+": "+model.getMsg(), Snackbar.LENGTH_LONG);
                            snackbar.setAction("확인하기", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), ChatPageActivity.class);
                                    intent.putExtra("name", model.getName());
                                    intent.putExtra("nickname", model.getNickname());
                                    startActivity(intent);
                                }
                            });
                            snackbar.setActionTextColor(Color.parseColor("#80cbc4"));
                            View snackBarView = snackbar.getView();
                            snackBarView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            snackBarView.setPadding(12, 12, 12, 12);
                            TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setBackground(getDrawable(R.drawable.snackbar_noti));
                            textView.setTextColor(Color.BLACK);
                            snackbar.show();
                            if(userDB.isSettingVib(getApplicationContext())){
                                Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(200);
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

        //Navigation Drawer

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View drawerHead = navigationView.getHeaderView(0);
        TextView drawerName, drawerEmail;
        ImageView drawerProfile;
        drawerName = drawerHead.findViewById(R.id.nav_title);
        drawerEmail = drawerHead.findViewById(R.id.nav_email);
        drawerProfile = drawerHead.findViewById(R.id.nav_profile);
        drawerName.setText(userDB.getUserNickname(getApplicationContext()));
        drawerEmail.setText(userDB.getUserEmail(getApplicationContext()));
        drawerProfile.setBackgroundColor(userDB.getProfilenum(getApplicationContext()));

//        title = findViewById(R.id.navTitle);
//        title.setText("hatban");
        //end of Navigation Drawer


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_devinfo) {
            DialogFragment dialogFragment = new Dialog_DevInfo();
            dialogFragment.show(getSupportFragmentManager(), "dialog_devinfo");
        }
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        if (id == R.id.action_user) {
            DialogFragment dialogFragment = new Dialog_User();
            dialogFragment.show(getSupportFragmentManager(), "dialog_user");
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user) {
            DialogFragment dialogFragment = new Dialog_User();
            dialogFragment.show(getSupportFragmentManager(), "dialog_user");
        }
        else if (id == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        else if (id == R.id.nav_share){
            Toast.makeText(getApplicationContext(), "아직 공유할 항목이 없습니다", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
