package com.recordwatch.recordwatch.utilidades;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.recordwatch.recordwatch.componentes.ComponenteWS.url;

/**
 * Clase encargada de realizar las llamadas a la api de TheMovieDB y convertir su respuesta en un objeto JSON
 */
public class JSONTask extends AsyncTask<Void, Void, String> {

    String result = "";

    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Método que convierte la url en una cadena de texto sacada como respuesta de la llamada a la api
     * @param voids url que realiza la llamada a la api
     * @return cadena de texto que contiene la respuesta de la llamada a la api
     */
    protected String doInBackground(Void... voids) {
        try {
            URL myurl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) myurl.openConnection();
            InputStreamReader streamReader = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            result = builder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Método que convierte la cadena de texto en un archivo JSON
     * @param s cadena de texto sacada de la respuesta de la llamada a la api
     */
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Log.e("jsonobject", object.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
