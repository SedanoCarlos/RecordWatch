package com.recordwatch.recordwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.ivbaranov.mfb.Utils;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveId;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import static com.recordwatch.recordwatch.AdminSQLite.copiaBD;
import static com.recordwatch.recordwatch.Driver_utils.preferences_driverId;

public class CopiaSeguridad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copia_seguridad);
        this.setTitle("Copias de Seguridad");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void exportDatabase(View view) {

    }

    public void importDatabase(View view) {
    }

}
