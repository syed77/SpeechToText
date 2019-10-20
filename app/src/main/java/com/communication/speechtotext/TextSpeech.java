package com.communication.speechtotext;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;
import java.util.Locale;


public class TextSpeech implements TextToSpeech.OnInitListener {

    TextToSpeech tts;

    TextSpeech(Context context)
    {
        tts = new TextToSpeech(context,this);
        Toast.makeText(context,"In Constructor",Toast.LENGTH_LONG).show();
    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.main);
//        tts = new TextToSpeech(this,this);
//
//    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
//                btnSpeak.setEnabled(true);
//                speakOut("Name");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

//    @Override
//    public void onDestroy() {
//        // Don't forget to shutdown tts!
//        if (tts != null) {
//            tts.stop();
//            tts.shutdown();
//        }
//        super.onDestroy();
//    }

    public void speakOut(String text) {

        tts.speak(text, TextToSpeech.QUEUE_ADD, null,null);
    }

}

