package fi.triplets.medmanager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<String> medNames = new ArrayList<>();
    ArrayAdapter<String> ad;
    AlertDialog.Builder message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.homeMedListView);
        message = new AlertDialog.Builder(this);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                deleteMessage(i);
                return false;
            }
        });

        loadTheme();
        loadData();
    }

    /** Called when the user taps the Add med button */
    public void addMedButtonPress (View view) {
        Intent intent = new Intent(this, AddMedicine.class);
        startActivity(intent);


    }
    /** Called when the user taps the Home button */
    public void homeButtonPress (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


        /** Called when the user taps the Med List button */
        public void medListButtonPress (View view) {
        Intent intent = new Intent(this, MedList.class);
        startActivity(intent);


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
        for (int i=0; i<med.medicines.size(); i++){
            medNames.add(med.medicines.get(i).getName());
        }
        ad = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, medNames);
        lv.setAdapter(ad);
    }

    public void deleteMessage(int y){
         message.setMessage("Haluatko varmasti poistaa tämän?");
         message.setCancelable(true);
         SharedPreferences sp = getSharedPreferences("MED_LIST", MODE_PRIVATE);
         SharedPreferences.Editor editor = sp.edit();
         Gson gson = new Gson();


         message.setPositiveButton("Kyllä", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 ad.remove(ad.getItem(y));

                 String json = sp.getString("medicines", null);
                 Type type = new TypeToken<ArrayList<medicineAddClass>>() {}.getType();
                 AddMedicine med = new AddMedicine();
                 med.medicines = gson.fromJson(json, type);

                 if(med.medicines == null){
                     med.medicines = new ArrayList<>();
                 }
                 med.medicines.remove(y);
                 String jsonUpdated = gson.toJson(med.medicines);
                 editor.putString("medicines", jsonUpdated);
                 editor.apply();
             }
         });

         message.setNegativeButton("Ei", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 dialogInterface.cancel();
             }
         });

         AlertDialog alert = message.create();
         alert.show();
    }
}