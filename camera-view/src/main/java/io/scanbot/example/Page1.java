package io.scanbot.example;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static io.scanbot.example.R.id.TVresult;

/**
 * Created by HP PC on 12/4/2016.
 */

public class Page1 extends Activity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener{
    //////////////meadia player pause method //////////////



    //////////////////////////////////////

    String tag = "Page1";
    private TextView resultText ; //spek method
    TextToSpeech ttsobject;
    int result;
    EditText et;
    TextView tv;
    String text;
    private TextView message;
    private GestureDetectorCompat myGesture;
    Vibrator vibe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page1);
        Log.i(tag, "onCreat");
        vibe= (Vibrator)Page1.this.getSystemService(Context.VIBRATOR_SERVICE);

        /*
        speckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(80);
                result = ttsobject.setLanguage(Locale.ENGLISH);
                text = "speack text button";
                ttsobject.setSpeechRate((float) 0.95); // set the speech rate
                ttsobject.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        speckbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;

            }

        });

        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(180);
                result = ttsobject.setLanguage(Locale.ENGLISH);
                text = "stop speaking button";
                ttsobject.setSpeechRate((float) 0.95); // set the speech rate
                ttsobject.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        });*/
        /////////////////////////////////////

        tv = (TextView) findViewById(TVresult);

        this.myGesture = new GestureDetectorCompat(this, this);

        myGesture.setOnDoubleTapListener(this);
        ///////////////////////////////////////////////////////////////////////
        resultText = (TextView) findViewById(R.id.editText);// speak method
        tv = (TextView) findViewById(TVresult);

        et = (EditText) findViewById(R.id.editText);

        ttsobject = new TextToSpeech(Page1.this, new TextToSpeech.OnInitListener()

        {
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {
                    result = ttsobject.setLanguage(Locale.ENGLISH);
                    text = et.getText().toString();
                    ttsobject.setSpeechRate((float) 0.95); // set the speech rate
                   ttsobject.speak("Welcom to the schmooser , The smartest document reader  , To exit from this app please press home button  ", TextToSpeech.QUEUE_FLUSH, null);



                    //promptSpeechInput();//speach
                } else {
                    Toast.makeText(getApplicationContext(), "Feature is not support in the device", Toast.LENGTH_SHORT).show();

                }


            }

        });

    }
/////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(tag,"onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(tag,"onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(tag,"onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(tag,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(tag,"onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(tag,"onRestart");
    }

    /*public void doSomething(View v) {
        switch (v.getId()) {
            case R.id.bspeak:

                if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                    Toast.makeText(getApplicationContext(), "Feature is not support in the device",
                            Toast.LENGTH_SHORT).show();
                }

                else {
                    text = et.getText().toString();
                    ttsobject.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                    //promptSpeechInput();
                }
                break;
        }
        }
    */

    /*public void stop(View v)
    {

        if(v.getId () ==R.id.bstopspeaking)
        {
            ttsobject.stop();
        }
    }
*/
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

            Toast.makeText(Page1.this ,"Sorry divece is not support!!",Toast.LENGTH_LONG).show();
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

                    switch (val) {
                    case "play" :
                            ttsobject.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                    }
                    break;
                }

        }
    }


    /////////////////////////////////////////gester////////////////////////////


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.myGesture.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {


        tv.setText("onSingletap");
        String valnew = (String) tv.getText();

        vibe.vibrate(180);
        result = ttsobject.setLanguage(Locale.ENGLISH);

        ttsobject.setSpeechRate((float) 0.95); // set the speech rate
        ttsobject.speak("wait for a moment until document is ready , Instruction to use this app ,LONG PRESS for speaking ,SCROLL for stop,  DOUBLE TAP for voice commands",TextToSpeech.QUEUE_FLUSH, null);
        return true;

    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {

        vibe.vibrate(200);
        tv.setText("onDoubletap");
        String valnew = (String) tv.getText();

        if(valnew == "onDoubletap")
        {
            promptSpeechInput(); // speeking when double tap
        }
        return true;


    }

    @Override
    public void onLongPress(MotionEvent e) {

        vibe.vibrate(200);
        tv.setText("onLongPress");
        String valnew = (String) tv.getText();

        if(valnew == "onLongPress")

        {
            ttsobject.speak(text, TextToSpeech.QUEUE_FLUSH, null);

        }

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        vibe.vibrate(200);
        tv.setText("onScroll");
        String valnew = (String) tv.getText();

        if(valnew == "onScroll")
        {
            ttsobject.stop();      // stop speeking when single tap
        }
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        tv.setText("onDoubleTapEvent");

        String valnew = (String) tv.getText();

        if(valnew == "onDoubleTapEvent")
        {
            promptSpeechInput();// speeking when double tap
            ttsobject.speak("Opening voice command pannel ,TAP and Say command to me , STOP or PLAY ,To exit from the panel please double tap", TextToSpeech.QUEUE_FLUSH, null);
        }
        return true;
    }



    @Override
    public boolean onDown(MotionEvent e) {
        tv.setText("onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        tv.setText("onShowPress");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        tv.setText("onSingleTapUp");
        return true;
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        vibe.vibrate(200);
        tv.setText("onFling");
        String valnew = (String) tv.getText();

        if(valnew == "onFling")
        {
            ttsobject.stop();      // stop speeking when single tap
        }
        return true;
    }

    @Override
    public void onBackPressed() // stop the back button
    {
        //super.onBackPressed();
    }
}
