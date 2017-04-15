package io.scanbot.example;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by HP PC on 12/4/2016.
 */

public class Page2 extends Activity {

    //int paused;

    String tag = "Page2";
    private TextView resultText ; //spek method
    TextToSpeech ttsobject;
    int result;
    EditText et;
    TextView tv;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);
        Log.i(tag,"onCreat");

        resultText = (TextView)findViewById(R.id.editText2);// speak method
        tv = (TextView)findViewById(R.id.TVresult2);

        Button reset = (Button)findViewById(R.id.bclear2);//clear text view

        reset.setOnClickListener(new View.OnClickListener()

                                 {
                                     @Override
                                     public void onClick(View v) {

                                         resultText.setText("");
                                         tv.setText("");
                                     }
                                 }
        );

        et = (EditText) findViewById(R.id.editText2);

        ttsobject = new TextToSpeech(Page2.this,new TextToSpeech.OnInitListener()

        {
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {
                    result=ttsobject.setLanguage(Locale.ENGLISH);
                    ttsobject.setSpeechRate((float) 0.90);
                }

                else {
                    Toast.makeText(getApplicationContext(), "Feature is not support in the device", Toast.LENGTH_SHORT).show();

                }


            }

        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(tag,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(tag,"onResume");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(tag,"onRestart");
    }

    public void doSomething(View v) {
        switch (v.getId()) {
            case R.id.bspeak2:

                if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                    Toast.makeText(getApplicationContext(), "Feature is not support in the device",
                            Toast.LENGTH_SHORT).show();
                } else {
                    text = et.getText().toString();
                    ttsobject.speak(text, TextToSpeech.QUEUE_FLUSH, null);

                }
                break;
        }
    }

    public void stop(View v)
    {

        if(v.getId () ==R.id.bstopspeaking2)
        {
            ttsobject.stop();
        }
    }

    public void promptSpeechInput()
    {

        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH );
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL , RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE , Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT , "Say anything to me..");

        try {
            startActivityForResult(i, 100);
        }
        catch (ActivityNotFoundException a)
        {

            Toast.makeText(Page2.this ,"Sory divece is not support!!",Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int request_code ,int result_code , Intent i) {

        super.onActivityResult(request_code, result_code, i);

        switch (request_code) {
            case 100:
                if (result_code == RESULT_OK && i != null) {
                    ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tv.setText(result.get(0));
                    String val = (String) tv.getText();
                    switch (val) {
                        case "stop":
                            ttsobject.stop();
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (ttsobject != null)
        {
            ttsobject.stop();
            ttsobject.shutdown();
        }

    }

    public void onButtonClick(View v)
    {

        if(v.getId () ==R.id.imageButton2)
        {
            promptSpeechInput();
        }
    }


}
