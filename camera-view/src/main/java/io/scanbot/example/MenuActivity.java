package io.scanbot.example;

/**
 * Created by ashanudeshitha on 4/15/17.
 */

        import android.app.Activity;
        import android.app.TabActivity;
        import android.content.ActivityNotFoundException;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.database.Cursor;
        import android.net.Uri;
        import android.provider.ContactsContract;
        import android.speech.RecognizerIntent;
        import android.speech.tts.TextToSpeech;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.telephony.SmsMessage;
        import android.util.Log;
        import android.view.View;
        import android.widget.CompoundButton;
        import android.widget.EditText;
        import android.widget.TabHost;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.widget.ToggleButton;

        import java.util.ArrayList;
        import java.util.Locale;


public class MenuActivity extends TabActivity {

    /////////////////SMS//////////////
    //private final int CHECK_CODE = 0x1;
    //private final int LONG_DURATION = 5000;
    //private final int SHORT_DURATION = 1200;

    //private Speaker speaker;

    //private ToggleButton toggle;
    //private CompoundButton.OnCheckedChangeListener toggleListener;
    //private TextView smsText;
    //private TextView smsSender;

    //private BroadcastReceiver smsReceiver;


    //////////////////////////////

    String tag = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Log.i(tag,"onCreat");


        TabHost tabHost = getTabHost();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("Tab1");
        tab1.setIndicator("VI MODE");
        Intent i1 = new Intent(MenuActivity.this,Page1.class);
        tab1.setContent(i1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("Tab2");
        tab2.setIndicator("SIGHTED MODE");
        Intent i2 = new Intent(MenuActivity.this,Page2.class);
        tab2.setContent(i2);

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

/////////////////////////SMS/////////////////////

        /*toggle = (ToggleButton)findViewById(R.id.speechToggle);
        smsText = (TextView) findViewById(R.id.sms_text);
        smsSender = (TextView) findViewById(R.id.sms_text);

        toggleListener = new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    speaker.allow(true);
                    speaker.speak("okay! I will read your message out loud you now");
                }
                else
                {
                    speaker.speak("oky ! i will stay silent now");
                    speaker.allow(false);
                }
            }
        };
        toggle.setOnCheckedChangeListener(toggleListener);

        checkTTS();
        initializeSMSReceiver();
        registerSmsReceiver();*/



        /////////////////////////////////////////////////

    }

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

    //@Override
    //protected void onDestroy() {
    //super.onDestroy();
    //Log.i(tag,"onDestroy");
    //}

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(tag,"onRestart");
    }
////////////////////////// SMS////////////////////////////////

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
        speaker.destroy();
    }

    private void checkTTS()
    {
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, CHECK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CHECK_CODE)
        {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
            {
                speaker = new Speaker(this);
            }
            else
            {
                Intent install = new Intent();
                install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(install);
            }
        }


    }

    private void  initializeSMSReceiver()
    {
        smsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Bundle bundle = intent.getExtras();
                if(bundle != null)
                {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    for (int i = 0; i < pdus.length; i++)
                    {
                        byte[] pdu = (byte[]) pdus[i];
                        SmsMessage message = SmsMessage.createFromPdu(pdu);
                        String text = message.getDisplayMessageBody();
                        String sender = getContactName(message.getOriginatingAddress());
                        speaker.pause(LONG_DURATION);
                        speaker.speak("You have a new message from" + sender + "!");
                        speaker.pause(SHORT_DURATION);
                        speaker.speak(text);
                        smsSender.setText("Message from " + sender);
                        smsText.setText(text);
                    }
                }


            }
        };
    }

    private String getContactName(String phone)
    {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phone));
        String projection[] = new String[]{ContactsContract.Data.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(uri, projection,null ,null ,null);

        if (cursor.moveToFirst())
        {
            return cursor.getString(0);
        }
        else
        {
            return "unknow number";
        }
    }

    private void registerSmsReceiver()
    {
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, intentFilter);
    }*/


}
