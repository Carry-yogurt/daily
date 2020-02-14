package com.example.daliy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SpinnerAdapterActivity extends Activity {

    private static final String TAG="SpinnerAdapterActivity";
    public Spinner spinner;
    public String selectText;
    public SpinnerAdapterActivity(){
        spinner.findViewById(R.id.sp_type);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectText=parent.getItemAtPosition(position).toString();
                //Log.d(TAG, "onItemSelected: "+selectText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
