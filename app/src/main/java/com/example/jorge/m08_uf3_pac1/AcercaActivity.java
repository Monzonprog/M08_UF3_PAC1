package com.example.jorge.m08_uf3_pac1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AcercaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca);
    }

    public void volverMain(View view){ //Metodo para volver a la pantalla de Main

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();

    }
}
