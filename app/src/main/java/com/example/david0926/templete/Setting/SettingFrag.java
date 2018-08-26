package com.example.david0926.templete.Setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.david0926.templete.R;

public class SettingFrag extends PreferenceFragmentCompat{


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference);
    }
}
