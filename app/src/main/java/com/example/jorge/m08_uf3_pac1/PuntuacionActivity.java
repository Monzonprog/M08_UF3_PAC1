package com.example.jorge.m08_uf3_pac1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PuntuacionActivity extends AppCompatActivity {

    TextView puntuacion1, puntuacion2, puntuacion3, puntuacion4, puntuacion5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuacion);
        puntuacion1 = (TextView)findViewById(R.id.puesto1TV);
        puntuacion2 = (TextView)findViewById(R.id.puesto2TV);
        puntuacion3 = (TextView)findViewById(R.id.puesto3TV);
        puntuacion4 = (TextView)findViewById(R.id.puesto4TV);
        puntuacion5 = (TextView)findViewById(R.id.puesto5TV);
        try {
            buscarCampos();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void buscarCampos() throws MalformedURLException {


               /*
            Comprobar la disponibilidad de la Red
             */
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Iniciar Tarea asícrona

            new buscarRegistros().//Crear método
                    execute(
                    new URL("http://jmonzon.eu5.org/juego/consulta_puntuaciones.php"));


        } else {
            Toast.makeText(this, "Error de conexión", Toast.LENGTH_LONG).show();
        }

    }


    public class buscarRegistros extends AsyncTask<URL, Void, String> {

        ProgressDialog progress;
        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(PuntuacionActivity.this, "Cargando",
                    "Cargando Información", true);
        }

        @Override
        protected String doInBackground(URL... urls) {
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

                writer.flush();
                writer.close();

                out.close();

                con.connect();
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }

                } else {
                    response = "";

                }


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null)
                    con.disconnect();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String resultado) {
            progress.dismiss();
            rellenarCampos(resultado);
        }
    }

    private void rellenarCampos(String resultado) {

        try {
            JSONArray jsonArray = new JSONArray(resultado);
            for (int i=0; i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                switch (i){
                    case 0:
                        puntuacion1.setText(jsonObject.getString("nombre")+" - " +
                                jsonObject.getString("puntos")+ " puntos");
                        break;
                    case 1:
                        puntuacion2.setText(jsonObject.getString("nombre")+" - " +
                                jsonObject.getString("puntos")+ " puntos");

                        break;
                    case 2:
                        puntuacion3.setText(jsonObject.getString("nombre")+" - " +
                                jsonObject.getString("puntos")+ " puntos");

                        break;
                    case 3:
                        puntuacion4.setText(jsonObject.getString("nombre")+" - " +
                                jsonObject.getString("puntos")+ " puntos");

                        break;
                    case 4:
                        puntuacion5.setText(jsonObject.getString("nombre")+" - " +
                                jsonObject.getString("puntos")+ " puntos");

                        break;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



    public void volverMain(View view){ //Metodo para volver a la pantalla de Main

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();

    }
}
