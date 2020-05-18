package com.recordwatch.recordwatch;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.recordwatch.recordwatch.componentes.ComponenteBD;


public class IniciarSesion extends AppCompatActivity {

    static String PREFS_KEY = "mispreferencias";
    TextView pass;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        pass = (TextView) findViewById(R.id.editTextContraseña);
        login = (Button) findViewById(R.id.buttonLogin);
        //Obtiene valor de preferencia (la primera ocasión es por default true).
        boolean muestra = getValuePreference(getApplicationContext());

        //Valida si muestra o no LicenseActivity.
        if (muestra) {
            Intent myIntent = new Intent(IniciarSesion.this, LicenseActivity.class);
            startActivity(myIntent);
        }
    }

        public static void saveValuePreference(Context context, Boolean mostrar) {
            SharedPreferences settings = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
            SharedPreferences.Editor editor;
            editor = settings.edit();
            editor.putBoolean("license", mostrar);
            editor.commit();
        }



        public boolean getValuePreference(Context context) {
            SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
            return  preferences.getBoolean("license", true);
        }


    public void iniciar(View view) {
        String contraseña = pass.getText().toString();
        Usuario usuario = new Usuario();
        if(contraseña.isEmpty()){
            Toast.makeText(this, "Escribe tu contraseña", Toast.LENGTH_SHORT).show();
        }else{
            try {
                ComponenteBD bd = new ComponenteBD(this);
                usuario = bd.leerUsuario(contraseña);
            } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            }
            if (usuario != null) {
                Toast.makeText(this, "Acceso concedido", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "Contraseña incorrecta.", Toast.LENGTH_SHORT).show();
            }

        }
    }


}

