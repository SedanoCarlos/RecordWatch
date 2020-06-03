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
import java.math.BigInteger;

import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.encriptacion.CodificaciónSHA;
import com.recordwatch.recordwatch.pojos.Usuario;

/**
 * Activity que permite al usuario entrar en la aplicación con su cuenta de usuario
 */
public class IniciarSesion extends AppCompatActivity {

    static String PREFS_KEY = "mispreferencias";
    TextView pass;
    Button login;
    private final String CODIFICACIÓN = "SHA-1";

    /**
     * Metodo en el cual declaramos e inicializamos los componentes de la activity
     * @param savedInstanceState parametro que guarda la ultima instancia de la actividad cuando se crea
     * por primera vez
     */
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

    /**
     * Método que comprueba si se creo una contraseña y ya se mostro la LicenseActivity
     * @param context activity desde donde es llamada el método
     * @param mostrar objeto de tipo boolean que comprueba si la license activity ya fue mostrada
     */
    public static void saveValuePreference(Context context, Boolean mostrar) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putBoolean("license", mostrar);
        editor.commit();
    }

    /**
     * Método que comprueba si la licenseActivity fue pasada a true
     * @param context activity desde donde es llamada el método
     */
    public boolean getValuePreference(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        return preferences.getBoolean("license", true);
    }


    /**
     * Método encargado de iniciar sesión en la cuenta del usuario
     * @param view Representación de los componentes especificados en la actividad
     */
    public void iniciar(View view) {
        String contraseña = pass.getText().toString();
        Usuario usuario = new Usuario();
        //Comprueba si ha escrito una contraseña
        if (contraseña.isEmpty()) {
            Toast.makeText(this, "Escribe tu contraseña", Toast.LENGTH_SHORT).show();
        } else {
            try {
                //Busca esa contraseña en la base de datos comprobando si existe
                ComponenteCAD cad = new ComponenteCAD(this);
                usuario = cad.leerUsuario(convertirContraseña().toString(16));
            } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            }
            if (usuario != null) {
                //Si coincide concede el acceso
                Toast.makeText(this, "Acceso concedido", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "Contraseña incorrecta.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Método encargado de convertir a formato hash una cadena de texto
     * @return un objeto de tipo BigInteger
     */
    private BigInteger convertirContraseña() {
        byte[] entrada = pass.getText().toString().getBytes();
        byte[] salida = new byte[0];
        try {
            //Método de la clase CodiificaciónSHA que encripta la contraseña
            salida = CodificaciónSHA.encriptarContraseña(entrada, CODIFICACIÓN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BigInteger(1, salida);
    }

}

