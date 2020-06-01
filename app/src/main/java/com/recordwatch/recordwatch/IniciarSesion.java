package com.recordwatch.recordwatch;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.hash.sha;
import com.recordwatch.recordwatch.pojos.Usuario;

import java.math.BigInteger;


public class IniciarSesion extends AppCompatActivity {

    static String PREFS_KEY = "mispreferencias";
    TextView pass;
    Button login;
    private final String SHA = "SHA-1";


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
                ComponenteCAD cad = new ComponenteCAD(this);
                usuario = cad.leerUsuario(passwordConvertHash().toString(16));
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

    private BigInteger passwordConvertHash() {
        byte[] inputData = pass.getText().toString().getBytes();
        byte[] outputData = new byte[0];
        try {
            outputData = sha.encryptSHA(inputData, SHA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BigInteger(1, outputData);
    }


}

