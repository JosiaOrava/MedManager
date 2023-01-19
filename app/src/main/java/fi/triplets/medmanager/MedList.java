package fi.triplets.medmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MedList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_list);
        loadTheme();
        loadData();
    }
    {

    }

    /** Called when the user taps the Home button */
    public void homeButtonPress (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void loadTheme(){
        SharedPreferences sp = getSharedPreferences("SWITCH_PREF", MODE_PRIVATE);
        boolean value = sp.getBoolean("SWITCH_STATE", false);
        SettingsActivity settings = new SettingsActivity();
        if(value){
            settings.setDarkTheme();
        } else{
            settings.setLightTheme();
        }
    }

    public void loadData(){
        SharedPreferences sp = getSharedPreferences("MED_LIST", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("medicines", null);

        Type type = new TypeToken<ArrayList<medicineAddClass>>() {}.getType();
        AddMedicine med = new AddMedicine();
        med.medicines = gson.fromJson(json, type);

        if(med.medicines == null){
            med.medicines = new ArrayList<>();
        }
    }
}