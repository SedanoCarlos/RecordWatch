package com.recordwatch.recordwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.recordwatch.recordwatch.componentes.ComponenteBD;

public class CambiarUsuario extends AppCompatActivity {
    EditText contraseñaActual;
    EditText contraseñaNueva;
    EditText contraseñaNueva2;
    Button cambiarContraseña;

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
                ComponenteBD bd = new ComponenteBD(this);
                usuario = bd.leerUsuario(contActual);
                if(usuario == null){
                    Toast.makeText(this,"Escribe una contraseña existente en el sistema",Toast.LENGTH_SHORT).show();
                }else{
                    if(contNueva.equals(contNueva2)) {
                        Usuario usuario2 = new Usuario();
                        usuario2.setContrasena(contNueva);
                        bd.modificarUsuario(usuario, usuario2);
                        Toast.makeText(this, "Contraseña modificada correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,"Los campos de la nueva contraseña no coinciden",Toast.LENGTH_LONG).show();
                    }
                }
            } catch (ExcepcionRecordWatch excepcionRecordWatch) {

            }
        }
    }

}
