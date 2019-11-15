package com.app.practica_11_adivinar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String PREFERENCES ="PREFERENCES";
    private static final String TRIES ="TRIES";
    private static final String SCORE ="SCORE";

    TextInputEditText et_number;
    TextView tv_score;
    TextView tv_tries;
    private int score = 0;
    private int tries = 0;

    int rand  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_number = findViewById(R.id.et_number);
        tv_score = findViewById(R.id.tv_score);
        tv_tries  = findViewById(R.id.tv_tries);

        SharedPreferences preferences = getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE);
        score = preferences.getInt(TRIES, 0);
        tries = preferences.getInt(SCORE, 0);

        tv_score.setText(String.valueOf(score));
        tv_tries.setText(String.valueOf(tries));
        random();
    }
    private void random()
    {
        rand = (int) (Math.random()*50);
        Log.d("NUM", String.valueOf(rand));
    }

    public void adivina(View view)
    {
        if(!et_number.getText().toString().isEmpty())
        {
            int num = Integer.parseInt(et_number.getText().toString());

            tv_tries.setText(String.valueOf(tries));
            if(num == rand)
            {
                score++;
                tries = 0;
                random();
                toast("Correcto!");
            }else
                toast((num < rand ? "El numero es mayor" : "El numero es menor"));
            tries++;

            tv_score.setText(String.valueOf(score));
            et_number.setText("");
            Log.d(TRIES, String.valueOf(score));
            Log.d(SCORE, String.valueOf(tries));

            SharedPreferences preferences = getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(SCORE, score);
            editor.putInt(TRIES, tries);
            editor.apply();

        }else
        {
            toast("Debe ingresar un número");
            et_number.requestFocus();
        }
    }

    private void toast(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}