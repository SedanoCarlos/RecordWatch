package com.recordwatch.recordwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.math.BigInteger;

import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.hash.CodificaciónSHA;
import com.recordwatch.recordwatch.pojos.Usuario;

/**
 * Activity que permite al usuario cambiar su contraseña de inicio  de sesión
 */
public class CambiarUsuario extends AppCompatActivity {
    EditText contraseñaActual;
    EditText contraseñaNueva;
    EditText contraseñaNueva2;
    Button cambiarContraseña;
    private final String CODIFICACIÓN = "SHA-1";

    /**
     * Metodo en el cual declaramos e inicializamos los componentes de la activity
     * @param savedInstanceState parametro que guarda la ultima instancia de la actividad cuando se crea
     * por primera vez
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_usuario);
        this.setTitle("Cambiar Contraseña");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contraseñaActual = (EditText) findViewById(R.id.etActualContraseña);
        contraseñaNueva = (EditText) findViewById(R.id.etNuevaContraseña);
        contraseñaNueva2 = (EditText) findViewById(R.id.etNuevaContraseña2);
        cambiarContraseña = (Button) findViewById(R.id.buttonCambiarContraseña);
    }

    /**
     * Metodo que sirve para cambiar la contraseña del usuario
     * @param view parametro que guarda la ultima instancia de la actividad cuando se crea
     * por primera vez
     */
    public void cambiarContraseña(View view) {
        //Comprobar que la contraseña existe y que los campos introducidos son válidos
        String contActual = contraseñaActual.getText().toString();
        String contNueva = contraseñaNueva.getText().toString();
        String contNueva2 = contraseñaNueva2.getText().toString();
        if (contActual.isEmpty()) {
            contraseñaActual.setError("Introduzca contraseña actual");
            return;
        } else if (contNueva.isEmpty()) {
            contraseñaNueva.setError("Introduzca contraseña nueva");
            return;
        } else if (contNueva2.isEmpty()) {
            contraseñaNueva2.setError("Introduzca de nuevo la contraseña nueva");
            return;
        } else {
            //Comprobar que la contraseña modificada cumpla las condiciones de la base de datos
            Usuario usuario = new Usuario();
            try {
                ComponenteCAD cad = new ComponenteCAD(this);
                usuario = cad.leerUsuario(convertirContraseña(contActual).toString(16));
                if (usuario == null) {
                    Toast.makeText(this, "Escribe una contraseña existente en el sistema", Toast.LENGTH_SHORT).show();
                } else {
                    if (contNueva.equals(contNueva2)) {
                        Usuario usuario2 = new Usuario();
                        usuario2.setContrasena(convertirContraseña(contNueva).toString(16));
                        cad.modificarUsuario(usuario, usuario2);
                        Toast.makeText(this, "Contraseña modificada correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Los campos de la nueva contraseña no coinciden", Toast.LENGTH_LONG).show();
                    }
                }
            } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            }
        }
    }

    /**
     * Método que convierte una cadena de texto a formato hash
     * @param con parámetro de tipo String  que indica la contraseña a insertar el usuario
     * @return la contraseña introducida en formato en hash
     */
    private BigInteger convertirContraseña(String con) {
        byte[] entrada = con.getBytes();
        byte[] salida = new byte[0];
        try {
            salida = CodificaciónSHA.encriptarContraseña(entrada, CODIFICACIÓN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BigInteger(1, salida);
    }

}
