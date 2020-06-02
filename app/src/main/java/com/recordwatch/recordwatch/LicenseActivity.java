package com.recordwatch.recordwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.hash.CodificaciónSHA;
import com.recordwatch.recordwatch.pojos.Usuario;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import static com.recordwatch.recordwatch.IniciarSesion.saveValuePreference;

/**
 * Activity que nos permite crear la contraseña para el posterior inicio de sesión del usuario
 */
public class LicenseActivity extends AppCompatActivity {

    EditText password;
    EditText password2;
    Button crearContraseña;
    private final String CODIFICACIÓN = "SHA-1";

    /**
     * Metodo en el cual declaramos e inicializamos los componentes de la activity
     * @param savedInstanceState parametro que guarda la ultima instancia de la actividad cuando se crea
     * por primera vez
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        password = (EditText) findViewById(R.id.editTextContraseñaNueva);
        password2 = (EditText) findViewById(R.id.editTextContraseñaNueva2);
        crearContraseña = (Button) findViewById(R.id.buttonCrearContraseña);
    }

    /**
     * Método encargado de crear una cuenta de usuario
     * @param view Representación de los componentes especificados en la actividad
     */
    public void crearContraseña(View view) {
        String pass = password.getText().toString();
        String pass2 = password2.getText().toString();
        Usuario usuario = new Usuario();
        usuario.setContrasena(convertirContraseña().toString(16));
        //Comprueba si los campos han sido rellenados
        if (!pass.isEmpty() && !pass2.isEmpty()) {
            //Comprueba sin son iguales
            if (pass.equals(pass2)) {
                try {
                    //Inserta al usuario en la base de datos
                    ComponenteCAD cad = new ComponenteCAD(this);
                    cad.insertarUsuario(usuario);
                } catch (ExcepcionRecordWatch excepcionRecordWatch) {
                }
                password.setText("");
                saveValuePreference(getApplicationContext(), false);
                Toast.makeText(this, "Se ha registrado su contraseña.", Toast.LENGTH_SHORT).show();
                //Creamos una carpeta files en la carpeta del proyecto,necesaria para el funcionamiento de las copias de seguridad
                File carpetaFiles = new File(getFilesDir(), "RecordWatch");
                try {
                    carpetaFiles.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(this, IniciarSesion.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debes rellenar ambos campos.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método encargado de convertir a formato hash una cadena de texto
     * @return un objeto de tipo BigInteger
     */
    private BigInteger convertirContraseña() {
        byte[] entrada = password.getText().toString().getBytes();
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
