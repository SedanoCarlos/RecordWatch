package com.recordwatch.recordwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Activity que muestra una pantalla de splash antes de iniciarse la app
 */
public class SplashScreen extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /**
     * Método encargado de crear una activity temporal que se muestra durante un corto periodo de tiempo antes de
     * llamar a la activity IniciarSesion
     * @param icicle
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this, IniciarSesion.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}