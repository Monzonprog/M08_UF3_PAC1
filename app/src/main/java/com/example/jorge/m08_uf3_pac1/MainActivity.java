package com.example.jorge.m08_uf3_pac1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.musica);
        mediaPlayer.start();
    }

    public void acercaDe(View view) { //Método para ir a la pantalla de acerca de

        mediaPlayer.stop();
        Intent i = new Intent(this, AcercaActivity.class);
        startActivity(i);


    }

    public void puntuacion(View view) { //Método para ir a las puntuaciones

        mediaPlayer.stop();
        Intent i = new Intent(this, PuntuacionActivity.class);
        startActivity(i);

    }

    public void empezar(View view) { //Método para comenzar el juego

        mediaPlayer.stop();
        Intent i = new Intent(this, JuegoActivity.class);
        startActivity(i);

    }
}
