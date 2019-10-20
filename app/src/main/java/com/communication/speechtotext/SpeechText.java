package com.communication.speechtotext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class SpeechText {

    SpeechRecognizer recognizer;
    Context context;
    TextSpeech textSpeech;
    String text;

    SpeechText(Context context)//These are the requirements
    {
        this.context = context;
        textSpeech = new TextSpeech(context);
        Toast.makeText(context,"SpeechText Constructor",Toast.LENGTH_LONG).show();
        initializeSpeechRecognizer();
    }

    private void initializeSpeechRecognizer() {
        //this is initialization of SpeechText
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            recognizer = SpeechRecognizer.createSpeechRecognizer(context);
            recognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {
                    textSpeech.speakOut("Sorry I didnt get that");
                }

                @Override
                public void onResults(Bundle results) {
                    List<String> result_arr = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    processResult(result_arr.get(0));
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }


    private void processResult(String result_message)
    //
    {
        result_message = result_message.toLowerCase();

        text= result_message;

        Log.d("TextGenerate", "processResult: "+text);
        SharedPreferences.Editor editor= context.getSharedPreferences("TextofSpeech",Context.MODE_PRIVATE).edit();
        editor.putString("Name",text);
        editor.commit();
//        Handle at least four sample cases

//        First: What is your Name?
//        Second: What is the time?
//        Third: Is the earth flat or a sphere?
//        Fourth: Open a browser and open url
        if(result_message.contains("what")){
            if(result_message.contains("your name")){
                textSpeech.speakOut("My Name is Mr.Android. Nice to meet you!");
            }
            if (result_message.indexOf("time") != -1){
                String time_now = DateUtils.formatDateTime(context, new Date().getTime(),DateUtils.FORMAT_SHOW_TIME);
                textSpeech.speakOut("The time is now: " + time_now);
            }
        } else if (result_message.indexOf("earth") != -1){
            textSpeech.speakOut("Don't be silly, The earth is a sphere. As are all other planets and celestial bodies");
        } else if (result_message.indexOf("browser") != -1){
            textSpeech.speakOut("Opening a browser right away master.");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/AnNJPf-4T70"));
            context.startActivity(intent);
        }


    }


    void listen()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
        recognizer.startListening(intent);
    }


}
