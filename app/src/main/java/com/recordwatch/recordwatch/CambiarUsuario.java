package com.recordwatch.recordwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.recordwatch.recordwatch.componentes.ComponenteBD;
import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.hash.sha;
import com.recordwatch.recordwatch.pojos.Usuario;

import java.math.BigInteger;

public class CambiarUsuario extends AppCompatActivity {
    EditText contraseñaActual;
    EditText contraseñaNueva;
    EditText contraseñaNueva2;
    Button cambiarContraseña;
    private final String SHA = "SHA-1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_usuario);
        this.setTitle("Cambiar Contraseña");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contraseñaActual = (EditText) findViewById(R.id.etActualContraseña);
        contraseñaNueva = (EditText) findViewById(R.id.etNuevaContraseña);
        contraseñaNueva2 = (EditText) findViewById(R.id.etNuevaContraseña2) ;
        cambiarContraseña = (Button) findViewById(R.id.buttonCambiarContraseña);
    }

    public void cambiarContraseña(View view) {
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
            Usuario usuario = new Usuario();
            try {
                ComponenteCAD cad = new ComponenteCAD(this);
                usuario = cad.leerUsuario(passwordConvertHash(contActual).toString(16));
                if(usuario == null){
                    Toast.makeText(this,"Escribe una contraseña existente en el sistema",Toast.LENGTH_SHORT).show();
                }else{
                    if(contNueva.equals(contNueva2)) {
                        Usuario usuario2 = new Usuario();
                        usuario2.setContrasena(passwordConvertHash(contNueva).toString(16));
                        cad.modificarUsuario(usuario, usuario2);
                        Toast.makeText(this, "Contraseña modificada correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,"Los campos de la nueva contraseña no coinciden",Toast.LENGTH_LONG).show();
                    }
                }
            } catch (ExcepcionRecordWatch excepcionRecordWatch) {

            }
        }
    }

    private BigInteger passwordConvertHash(String con) {
        byte[] inputData = con.getBytes();
        byte[] outputData = new byte[0];
        try {
            outputData = sha.encryptSHA(inputData, SHA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BigInteger(1, outputData);
    }

}
