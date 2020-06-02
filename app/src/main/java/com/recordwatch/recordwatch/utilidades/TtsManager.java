package com.recordwatch.recordwatch.utilidades;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * CLase que lleva a cabo la reproducción a voz de un texto
 */
public class TtsManager {

    private TextToSpeech lecturaVoz = null;
    private boolean isLoaded = false;

    /**
     * Inicializa la variable lecturaVoz
     * @param context
     */
    public void init(Context context) {
        try {
            lecturaVoz = new TextToSpeech(context, onInitListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Método que cuando se clica en el botón relacionado al listener comprueba si el idioma del texto es correcto
    private TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            Locale spanish = new Locale("es", "ES");
            //Comprueba si no hay errores
            if (status == TextToSpeech.SUCCESS) {
                int result = lecturaVoz.setLanguage(spanish);
                isLoaded = true;
                //Comprueba si e lenguaje del texto esta en español
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("error", "Este Lenguaje no esta permitido");
                }
            } else {
                Log.e("error", "Fallo al Inicilizar!");
            }
        }
    };

    /**
     * Método que detiene la reproducción en voz
     */
    public void shutDown() {
        lecturaVoz.shutdown();
    }

    /**
     * Método que pone en pausa la reproducción del audio, pudiendo luego continuar
     */
    public void stop() {
        lecturaVoz.stop();
    }


    /**
     * Método que reproduce el testo en audio siempre y cuando el idioma del texto introducido anteriormente sea correcto
     * @param text
     */
    public void initQueue(String text) {
        if (isLoaded)
            lecturaVoz.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        else
            Log.e("error", "TTS Not Initialized");
    }
}
