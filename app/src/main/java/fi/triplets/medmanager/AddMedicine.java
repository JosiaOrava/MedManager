package fi.triplets.medmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class AddMedicine extends AppCompatActivity {
    Boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    EditText medNameTxt, medAmmTxt, medTimeTxt;
    int hours, minutes;
    String medName, medAmm, medTime, medHour, medMin;
    Boolean[] dates = {monday, tuesday, wednesday, thursday, friday, saturday, sunday};
    ArrayList<medicineAddClass> medicines = new ArrayList<medicineAddClass>();
    TimePicker timepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        timepicker = (TimePicker)findViewById(R.id.simpleTimePicker);
        timepicker.setIs24HourView(true);


        medNameTxt = findViewById(R.id.medNameTxt);
        medAmmTxt = findViewById(R.id.medAmmTxt);

        loadTheme();
        loadData();
    }

    public void addMed(View v){
        createClass();

        // these are only for debugging
        Log.d("ButtonPressed","name: " + medicines.get(0).getName());
        Log.d("ButtonPressed","amount: " + medicines.get(0).getAmount());
        Log.d("ButtonPressed","Time: " + medHour + " " + medMin);
        Log.d("ButtonPressed","dates: " + Arrays.toString(medicines.get(0).getDates()));


        saveData();
    }

    public void loadData(){
        SharedPreferences sp = getSharedPreferences("MED_LIST", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("medicines", null);

        Type type = new TypeToken<ArrayList<medicineAddClass>>() {}.getType();

        medicines = gson.fromJson(json, type);

        if(medicines == null){
            medicines = new ArrayList<>();
        }
    }

    public void saveData(){
        SharedPreferences sp = getSharedPreferences("MED_LIST", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        // we need to create json string with gson because we can't save arraylist to sharedpref
        Gson gson = new Gson();
        String json = gson.toJson(medicines);
        editor.putString("medicines", json);
        editor.apply();

        // this gives small notification that the data is saved successfully
        Toast.makeText(this, "Successfully saved data. ", Toast.LENGTH_SHORT).show();
    }

    public void createClass(){
        // these are the info that user enters. dates is an array of booleans so monday is ether true or false etc...
        medName = medNameTxt.getText().toString();
        medAmm = medAmmTxt.getText().toString();
        hours = timepicker.getCurrentHour();
        minutes = timepicker.getCurrentMinute();
        medMin = String.format("%02d", minutes);
        medHour = String.format("%02d", hours);

        monday = ((CheckBox) findViewById(R.id.checkBoxMa)).isChecked();
        tuesday = ((CheckBox) findViewById(R.id.checkBoxTi)).isChecked();
        wednesday = ((CheckBox) findViewById(R.id.checkBoxKe)).isChecked();
        thursday = ((CheckBox) findViewById(R.id.checkBoxTo)).isChecked();
        friday = ((CheckBox) findViewById(R.id.checkBoxPe)).isChecked();
        saturday = ((CheckBox) findViewById(R.id.checkBoxLa)).isChecked();
        sunday = ((CheckBox) findViewById(R.id.checkBoxSu)).isChecked();

        dates = new Boolean[]{monday, tuesday, wednesday, thursday, friday, saturday, sunday};
        medicines.add(new medicineAddClass(medName, medAmm, medHour, medMin, dates));
    }

    public void settingsButtonPress(View v){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        Log.d("settings", "pressed");
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
    public void homeButtonPress (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void medListButtonPress (View view) {
        Intent intent = new Intent(this, MedList.class);
        startActivity(intent);
    }

}
