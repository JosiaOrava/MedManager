package fi.triplets.medmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    Switch lightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        lightMode = findViewById(R.id.lightMode);
        loadSwitchState();
        lightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(lightMode.isChecked()){
                    setDarkTheme();
                } else{
                    setLightTheme();
                }
                saveSwitchState();
            }
        });

    }
    public void setDarkTheme(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
    public void setLightTheme(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
    public void saveSwitchState(){
        SharedPreferences sp = getSharedPreferences("SWITCH_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean("SWITCH_STATE", lightMode.isChecked());
        editor.apply();
    }
    public void loadSwitchState(){
        SharedPreferences sp = getSharedPreferences("SWITCH_PREF", MODE_PRIVATE);
        boolean value = sp.getBoolean("SWITCH_STATE", false);

        lightMode.setChecked(value);
        if(lightMode.isChecked()){
            setDarkTheme();
        } else{
            setLightTheme();
        }
    }
    public void homeButtonPress (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void medListButtonPress (View view) {
        Intent intent = new Intent(this, MedList.class);
        startActivity(intent);
    }
}

