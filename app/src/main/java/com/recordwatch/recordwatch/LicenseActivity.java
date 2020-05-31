package com.recordwatch.recordwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.recordwatch.recordwatch.componentes.ComponenteBD;
import com.recordwatch.recordwatch.hash.sha;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import static com.recordwatch.recordwatch.IniciarSesion.saveValuePreference;

public class LicenseActivity extends AppCompatActivity {

    EditText password;
    EditText password2;
    Button crearContraseña;
    private final String SHA = "SHA-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        password = (EditText) findViewById(R.id.editTextContraseñaNueva);
        password2 = (EditText) findViewById(R.id.editTextContraseñaNueva2);
        crearContraseña = (Button) findViewById(R.id.buttonCrearContraseña);
    }

    public void crearContraseña(View view){
        String pass = password.getText().toString();
        String pass2 = password2.getText().toString();
        Usuario usuario = new Usuario();
        usuario.setContrasena(passwordConvertHash().toString(16));
        if (!pass.isEmpty() && !pass2.isEmpty()) {
            if(pass.equals(pass2)) {
                try {
                    ComponenteBD bd = new ComponenteBD(this);
                    bd.insertarUsuario(usuario);
                } catch (ExcepcionRecordWatch excepcionRecordWatch) {

                }
                password.setText("");
                saveValuePreference(getApplicationContext(), false);
                Toast.makeText(this, "Se ha registrado su contraseña.", Toast.LENGTH_SHORT).show();
                File carpetaFiles = new File(getFilesDir(),"RecordWatch");
                try {
                    carpetaFiles.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(this, IniciarSesion.class);
                startActivity(i);
            }else{
                Toast.makeText(this,"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debes rellenar ambos campos.", Toast.LENGTH_SHORT).show();
        }
    }

    private BigInteger passwordConvertHash() {
        byte[] inputData = password.getText().toString().getBytes();
        byte[] outputData = new byte[0];
        try {
            outputData = sha.encryptSHA(inputData, SHA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BigInteger(1, outputData);
    }
}
