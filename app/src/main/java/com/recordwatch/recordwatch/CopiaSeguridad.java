package com.recordwatch.recordwatch;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.recordwatch.recordwatch.componentes.ComponenteBD;
import com.recordwatch.recordwatch.componentes.ComponenteCAD;
import com.recordwatch.recordwatch.googledrive.DriveServiceHelper;
import com.recordwatch.recordwatch.googledrive.GoogleDriveFileHolder;
import com.recordwatch.recordwatch.hash.sha;
import com.recordwatch.recordwatch.pojos.Usuario;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import static com.recordwatch.recordwatch.googledrive.DriveServiceHelper.getGoogleDriveService;


public class CopiaSeguridad extends AppCompatActivity {

    private static final int REQUEST_CODE_SIGN_IN = 100;
    private GoogleSignInClient mGoogleSignInClient;
    private DriveServiceHelper mDriveServiceHelper;
    private static final String TAG = "CopiaSeguridad";
    static String idFile;
    static ComponenteCAD cad;
    private final String SHA = "SHA-1";
    String contraseña;
    private ProgressDialog progressDialogCopiar;
    private ProgressDialog progressDialogRestaurar;
    TextView cuentaCorreo;
    String correo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copia_seguridad);
        this.setTitle("Copias de Seguridad");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialogCopiar = new ProgressDialog(CopiaSeguridad.this);
        progressDialogRestaurar = new ProgressDialog(CopiaSeguridad.this);
        cuentaCorreo = findViewById(R.id.tvCuentaCorreo);

        try {
            cad = new ComponenteCAD(CopiaSeguridad.this);
            contraseña = cad.leerUsuarios().getContrasena();
            Log.d("Contraseña Actual : ", "" + contraseña);
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account == null) {
            signIn();
        } else {
            mDriveServiceHelper = new DriveServiceHelper(getGoogleDriveService(getApplicationContext(), account, "appName"));

        }
        if(account != null) {
            correo = account.getEmail();
            cuentaCorreo.setVisibility(View.VISIBLE);
            cuentaCorreo.setText("Inicio de Sesión en cuenta: \n" + correo);
        }
    }

    private void signIn() {

        mGoogleSignInClient = buildGoogleSignInClient();
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);

    }

    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .requestEmail()
                        .build();
        return GoogleSignIn.getClient(getApplicationContext(), signInOptions);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode == Activity.RESULT_OK && resultData != null) {
                    handleSignInResult(resultData);

                } else {
                    Toast.makeText(CopiaSeguridad.this, "Fallo al iniciar sesión.Comprueba tu conexion a Internet", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "" + requestCode + " : " + resultCode + " : " + resultData);
                }

        }
        super.onActivityResult(requestCode, resultCode, resultData);
    }

    public void test() {
        System.out.println("test");
    }

    private void handleSignInResult(Intent result) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                    @Override
                    public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                        Log.d(TAG, "Signed in as " + googleSignInAccount.getEmail());
                        mDriveServiceHelper = new DriveServiceHelper(getGoogleDriveService(getApplicationContext(), googleSignInAccount, "appName"));
                        correo = googleSignInAccount.getEmail();
                        cuentaCorreo.setVisibility(View.VISIBLE);
                        cuentaCorreo.setText("Inicio de Sesión en cuenta: \n" + correo);
                        Log.d(TAG, "handleSignInResult: " + mDriveServiceHelper);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        cuentaCorreo.setVisibility(View.INVISIBLE);
                        Log.e(TAG, "Unable to sign in.", e);
                        Toast.makeText(CopiaSeguridad.this, "Comprueba tu conexion a Internet", Toast.LENGTH_LONG).show();
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void exportDatabase(View view) {
        if (mDriveServiceHelper == null) {
            Toast.makeText(CopiaSeguridad.this, "Primero debes iniciar sesión con tu cuenta de Google Drive", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialogCopiar.show();
        progressDialogCopiar.setContentView(R.layout.progress_dialog);
        progressDialogCopiar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        mDriveServiceHelper.searchFile("RecordWatch", "application/octet-stream")
                .addOnSuccessListener(new OnSuccessListener<List<GoogleDriveFileHolder>>() {
                    @Override
                    public void onSuccess(List<GoogleDriveFileHolder> googleDriveFileHolders) {
                        Gson gson = new Gson();
                        if (googleDriveFileHolders != null) {
                            GoogleDriveFileHolder googleDriveFileHolder = googleDriveFileHolders.get(0);
                            idFile = googleDriveFileHolder.getId();

                        }
                        Log.d(TAG, "onSuccess2: " + idFile);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                progressDialogCopiar.dismiss();
                Toast.makeText(CopiaSeguridad.this, "Comprueba tu conexion a Internet", Toast.LENGTH_LONG).show();
            }
        });


        mDriveServiceHelper.deleteFolderFile(idFile).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccesDelete");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        mDriveServiceHelper.uploadFile(new java.io.File("/data/data/com.recordwatch.recordwatch/databases/", "RecordWatch"), "application/octet-stream", null)
                .addOnSuccessListener(new OnSuccessListener<GoogleDriveFileHolder>() {
                    @Override
                    public void onSuccess(GoogleDriveFileHolder googleDriveFileHolder) {
                        Gson gson = new Gson();
                        Log.d(TAG, "onSuccess: " + gson.toJson(googleDriveFileHolder));
                        idFile = googleDriveFileHolder.getId();
                        progressDialogCopiar.dismiss();
                        Toast.makeText(CopiaSeguridad.this, "Copia de Datos Subida", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialogCopiar.dismiss();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void importDatabase(View view) {
        if (mDriveServiceHelper == null) {
            Toast.makeText(CopiaSeguridad.this, "Primero debes iniciar sesión con tu cuenta de Google Drive", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialogRestaurar.show();
        progressDialogRestaurar.setContentView(R.layout.progress_dialog2);
        progressDialogRestaurar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        try {
            Log.d("Contraseña", "Contraseña Inicial : " + cad.leerUsuarios().getContrasena());
        } catch (ExcepcionRecordWatch excepcionRecordWatch) {
            excepcionRecordWatch.printStackTrace();
        }
        mDriveServiceHelper.searchFile("RecordWatch", "application/octet-stream")
                .addOnSuccessListener(new OnSuccessListener<List<GoogleDriveFileHolder>>() {
                    @Override
                    public void onSuccess(List<GoogleDriveFileHolder> googleDriveFileHolders) {
                        GoogleDriveFileHolder googleDriveFileHolder = null;
                        if (googleDriveFileHolders != null) {
                            googleDriveFileHolder = googleDriveFileHolders.get(0);
                            idFile = googleDriveFileHolder.getId();
                        } else {
                            idFile = null;
                        }
                        if (idFile != null) {
                            Log.d(TAG, "1 : " + idFile);
                            File dir = new File("/data/data/com.recordwatch.recordwatch/databases");
                            if (dir.isDirectory()) {
                                //obtiene un listado de los archivos contenidos en el directorio.
                                String[] hijos = dir.list();
                                //Elimina los archivos contenidos.
                                for (int i = 0; i < hijos.length; i++) {
                                    new File(dir, hijos[i]).delete();
                                }
                                Log.d("TAG", "Archivo Database Borrado");
                                File ficheroDatabase = new File("/data/data/com.recordwatch.recordwatch/databases/RecordWatch");
                                try {
                                    ficheroDatabase.createNewFile();
                                    Log.d("TAG", "Archivo Database Creado");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            mDriveServiceHelper.downloadFile(new File("/data/data/com.recordwatch.recordwatch/databases/", "RecordWatch"), "" + idFile + "")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialogRestaurar.dismiss();
                                            Toast.makeText(CopiaSeguridad.this, "Datos Restaurados", Toast.LENGTH_SHORT).show();
                                            ComponenteCAD cad2;
                                            try {
                                                cad2 = new ComponenteCAD(CopiaSeguridad.this);
                                                Log.d("Contraseña", "Contraseña Eliminada : " + cad2.leerUsuarios().getContrasena());
                                                cad2.eliminarUsuario(cad2.leerUsuarios().getContrasena());
                                                Usuario usuario = new Usuario(contraseña);
                                                Log.d("Contraseña", "Contraseña a Insertar de usuario : " + usuario.getContrasena());
                                                Log.d("Contraseña", "Contraseña a Insertar de variable : " + contraseña);
                                                cad2.insertarUsuario(usuario);
                                                Log.d("Contraseña", "Contraseña insertada : " + usuario.getContrasena());
                                            } catch (ExcepcionRecordWatch excepcionRecordWatch) {
                                                excepcionRecordWatch.printStackTrace();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialogRestaurar.dismiss();
                                    Log.d(TAG, "onFailureDownload: " + e.getMessage());

                                }
                            });
                        } else {
                            progressDialogRestaurar.dismiss();
                            Toast.makeText(CopiaSeguridad.this, "No existen copias de seguridad", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailureSearching: " + e.getMessage());
                progressDialogRestaurar.dismiss();
                Toast.makeText(CopiaSeguridad.this, "Comprueba tu conexion a Internet o que exista " +
                        "alguna copia en tu cuenta de Google Drive", Toast.LENGTH_LONG).show();
            }
        });



    }


}
