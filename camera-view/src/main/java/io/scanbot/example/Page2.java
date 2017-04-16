package io.scanbot.example;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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

    String UrlGET, img, ip = "192.168.0.102";


    private static final String TAG_AllLINKS = "GetSentencesResult";

    private static final String TAG_Main = "GetSentencesResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);
        Log.i(tag,"onCreat");
        UrlGET = "http://" + ip + "/Schoomzer/SchmoozerMethod.svc/GetSentences/All";
        new ExecuteTaskPut().execute("", "10", "tt");


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

                    resultText.setText(img);

                    new ExecuteTaskPut().execute("", "10", "tt");

                    text = et.getText().toString();
                    ttsobject.speak(img, TextToSpeech.QUEUE_FLUSH, null);

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

    // Respond to server

    class ExecuteTaskPut extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            String res = PutDriverData(params);
            //  t.setText(UserDropLocation);
            return res;
        }

        @Override
        protected void onPostExecute(String result) {

            //status = result;
            // Toast.makeText(getApplicationContext(), "UID"+result, Toast.LENGTH_LONG).show();
            try {
                JSONObject jObj = new JSONObject(result);
                if (jObj == null) {
                    Log.d("String is ", "json is null");
                }
                // Check your log cat for JSON reponse
                Log.d("All links: ", jObj.toString());


                // Checking for SUCCESS TAG
                result = jObj.getString(TAG_Main);

                img = jObj.getString(TAG_Main);

                jObj = new JSONObject(img);

//                UserDropLocation = jObj.getString(TAG_DropLocation);
//                UserName = jObj.getString(TAG_CName);
//                UserContactNum = jObj.getString(TAG_Contact);
//                PickupDescription = jObj.getString(TAG_CurrentLocation);
//                BookingID = jObj.getString(TAG_BookingId);

//                UserLocationLan = Double.valueOf(jObj.getString(TAG_CurrentLan));
//                UserLocationLong = Double.valueOf(jObj.getString(TAG_CurrentLong));
//
                img = jObj.getString(TAG_Main);
//                UserDropLocationLong = Double.valueOf(jObj.getString(TAG_DropLong));

                Log.d("All Lannnnnnnnnn Test: ", img);

//                MessageNT(UserName, UserContactNum, PickupDescription, UserDropLocation);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //t.setText(result);
        }

    }

    public String PutDriverData(String[] valuse) {
        String ss = "";


        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(UrlGET);

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("CustomerName", valuse[0]));
            list.add(new BasicNameValuePair("Password", valuse[1]));
            list.add(new BasicNameValuePair("IMEI", valuse[2]));
            // list.add(new BasicNameValuePair("UserID",valuse[3]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //Toast.makeText(getApplicationContext(),  valuse[0], Toast.LENGTH_LONG).show();
            HttpEntity httpEntity = httpResponse.getEntity();
            ss = readResponse(httpResponse);


        } catch (Exception exception) {
        }


        return ss;


    }

    public String readResponse(HttpResponse res) {
        InputStream is = null;
        String return_text = "";
        try {
            is = res.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return_text = sb.toString();
        } catch (Exception e) {

        }
        return return_text;

    }


}
