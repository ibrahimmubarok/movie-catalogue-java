package com.ibeybeh.mysubmission4.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.ibeybeh.mysubmission4.R;

import com.ibeybeh.mysubmission4.fragment.MyPreferenceFragment;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportFragmentManager().beginTransaction().add(R.id.setting_holder, new MyPreferenceFragment()).commit();
    }
}