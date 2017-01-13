package com.example.jorge.m08_uf3_pac1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class JuegoActivity extends AppCompatActivity {

    //Strings de preguntas y respuestas

    String[] respuesta1 = {"La amenaza fantasma", "Una nueva esperanza", "El despertar de la fuerza",
            "No sé, soy más de Star Trek"};
    String[] respuesta2 = {"1", "10", "1000", "10000"};
    String[] respuesta3 = {"El páncreas", "La tiroides", "El higado", "Los riñones"};
    String[] respuesta4 = {"Steven Spielberg", "Alfred Hitchcock", "George Lucas", "J. J. Abrams"};

    String[][] respuestaList = {respuesta1, respuesta2, respuesta3, respuesta4};

    String[] pregunta = {"¿Cuál fue la primera pelicula estrenada de Star Wars?",
            "¿Cuántas pirámides hay en Egipto?",
            "¿Qué órgano segrega la hormona insulina? ",
            "¿Quién dirigio Star Wars VII?"};

    //Elementos a usar

    public TextView preguntaTV;
    public RadioButton respuesta1RB, respuesta2RB, respuesta3RB, respuesta4RB;
    public RadioGroup radioGroupRespuestas;

    //Contadores para las preguntas, para los radioButton y contadores de aciertos y errores
    int aux = 0;
    int radio_seleccionado = 0;
    int positivo = 0;
    int negativo = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        radioGroupRespuestas = (RadioGroup) findViewById(R.id.radioGroupRespuestas);

        /*Prepara,para usar los radioButton y además añadumos un listener al radioGroup para
        poder determinar que radioButton está pulsado*/

        respuesta1RB = (RadioButton) findViewById(R.id.respuesta1RB);
        respuesta2RB = (RadioButton) findViewById(R.id.respuesta2RB);
        respuesta3RB = (RadioButton) findViewById(R.id.respuesta3RB);
        respuesta4RB = (RadioButton) findViewById(R.id.respuesta4RB);
        radioGroupRespuestas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radio_seleccionado = checkedId;
            }
        });

        //Pintamos en la pantalla las preguntas y las respuestas
        pintarPreguntas(pregunta);
        pintarRespuestas(respuestaList);


    }

    //Método que pintara las preguntas en el TextView
    public void pintarPreguntas(String[] pregunta) {

        preguntaTV = (TextView) findViewById(R.id.preguntaTV);
        preguntaTV.setText(pregunta[aux]);

    }

    //Método que pintara las respuestas en los radioButton
    public void pintarRespuestas(String[][] respuestaList) {


        respuesta1RB.setText(respuestaList[aux][0]);
        respuesta2RB.setText(respuestaList[aux][1]);
        respuesta3RB.setText(respuestaList[aux][2]);
        respuesta4RB.setText(respuestaList[aux][3]);
        radioGroupRespuestas.clearCheck();

    }

    /*Acción al presionar el botón, al ser tan pocas preguntas he tratado la validación
     individualmente, además usaremos un contador para llevar la cuenta de los aciertos y fallos
      demás se mostrara un toast indicando si la respuesta es bueno o no*/
    public void presionarBoton(View v) {

        comprobarRespuesta();

        if(aux < 3) {

            aux++;

            pintarPreguntas(pregunta);
            pintarRespuestas(respuestaList);

        }

        else{
                aux = 0;
                Intent i = new Intent(this, ResultadoActivity.class);
                i.putExtra("aciertos", positivo);
                i.putExtra("errores", negativo);
                startActivity(i);
                finish();

            }

    }

    public void comprobarRespuesta() {
        switch (radio_seleccionado) {
            case R.id.respuesta1RB:
                if (aux == 2) {

                    positivo++;
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Respuesta correcta", Toast.LENGTH_SHORT);
                    toast1.show();

                } else {

                    negativo++;
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Respuesta incorrecta", Toast.LENGTH_SHORT);
                    toast1.show();

                }
                break;

            case R.id.respuesta2RB:
                if (aux == 0) {

                    positivo++;
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Respuesta correcta", Toast.LENGTH_SHORT);
                    toast1.show();

                } else {

                    negativo++;
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Respuesta incorrecta", Toast.LENGTH_SHORT);
                    toast1.show();

                }
                break;

            case R.id.respuesta3RB:

                negativo++;
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Respuesta incorrecta", Toast.LENGTH_SHORT);
                toast1.show();


                break;
            case R.id.respuesta4RB:
                if (aux == 1 || aux == 3) {

                    positivo++;
                    Toast toast2 =
                            Toast.makeText(getApplicationContext(),
                                    "Respuesta correcta", Toast.LENGTH_SHORT);
                    toast2.show();

                } else {

                    negativo++;
                    Toast toast2 =
                            Toast.makeText(getApplicationContext(),
                                    "Respuesta incorrecta", Toast.LENGTH_SHORT);
                    toast2.show();

                }
                break;
        }

    }

}

