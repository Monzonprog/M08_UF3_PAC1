package com.example.jorge.m08_uf3_pac1;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ResultadoActivity extends AppCompatActivity {

    int aciertos, errores;
    TextView aciertosTV, erroresTV;
    EditText nombreET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        Bundle extras = getIntent().getExtras();
        aciertos = extras.getInt("aciertos", 0);
        errores = extras.getInt("errores", 0);

        aciertosTV = (TextView)findViewById(R.id.aciertosTV);
        erroresTV = (TextView)findViewById(R.id.erroresTV);
        nombreET = (EditText)findViewById(R.id.nombreET);
        aciertosTV.setText(getString(R.string.aciertos_resultado, aciertos));
        erroresTV.setText(getString(R.string.errores_resultado, errores));

    }

    private void guardarCampos() throws IOException {

               /*
            Comprobar la disponibilidad de la Red
             */
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Iniciar Tarea asícrona

            new guardarInfo().//Crear método
                    execute(
                    new URL("http://jmonzon.eu5.org/juego/insertar_puntuaciones.php"));


        } else {
            Toast.makeText(this, "Error de conexión", Toast.LENGTH_LONG).show();
        }
    }

    public void guardar(View view) {
        try {
            guardarCampos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public class guardarInfo extends AsyncTask<URL, Void, Void> {

        ProgressDialog progress;

        String puntuaciones = "nombre=" + nombreET.getText().toString() + "&puntuacion=" +
                aciertos ;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(ResultadoActivity.this, "Cargando",
                    "Guardando Información", true);
        }

        @Override
        protected Void doInBackground(URL... urls) {
            // Obtener la conexión
            HttpURLConnection con = null;

            try {

                con = (HttpURLConnection) urls[0].openConnection();
                con.setReadTimeout(10000);
                con.setConnectTimeout(15000);
                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);


                // Establecer application/x-www-form-urlencoded debido al formato clave-valor
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                OutputStream out = con.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));


                writer.write(puntuaciones);
                writer.flush();
                writer.close();

                out.close();

                con.connect();
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.v("MainActivity", "Conexión correcta");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null)
                    con.disconnect();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void s) {
            progress.dismiss();
            Toast.makeText(ResultadoActivity.this, "Puntuación guardada", Toast.LENGTH_LONG).show();
            finish();

        }
    }
}
