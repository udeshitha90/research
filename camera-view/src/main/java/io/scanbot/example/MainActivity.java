package io.scanbot.example;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import net.doo.snap.camera.AutoSnappingController;
import net.doo.snap.camera.ContourDetectorFrameHandler;
import net.doo.snap.camera.PictureCallback;
import net.doo.snap.camera.ScanbotCameraView;
import net.doo.snap.ui.PolygonView;

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
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements PictureCallback {

    private ScanbotCameraView cameraView;
    private ImageView resultView;
    String UrlPut,img;
    private static final String TAG_Main = "PutImageResult";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
       // UrlPut = "http://192.168.0.102/Schoomzer/SchmoozerMethod.svc/PutImg/123";
        UrlPut = "http://192.168.0.102/Schoomzer/SchmoozerMethod.svc/PutImg/123";
        //new ExecuteTaskPut().execute("", "10", "tt");

        // new ExecuteTaskStatus().execute("", "10", "tt");

        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        cameraView = (ScanbotCameraView) findViewById(R.id.camera);
        resultView = (ImageView) findViewById(R.id.result);

        ContourDetectorFrameHandler contourDetectorFrameHandler = ContourDetectorFrameHandler.attach(cameraView);

        PolygonView polygonView = (PolygonView) findViewById(R.id.polygonView);
        contourDetectorFrameHandler.addResultHandler(polygonView);

        AutoSnappingController.attach(cameraView, contourDetectorFrameHandler);

        cameraView.addPictureCallback(this);

        findViewById(R.id.snap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.takePicture(false);
            }
        });

        findViewById(R.id.flash).setOnClickListener(new View.OnClickListener() {

            boolean flashEnabled = false;

            @Override
            public void onClick(View v) {
                cameraView.useFlash(!flashEnabled);
                flashEnabled = !flashEnabled;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.onPause();
        //hello
    }

    @Override
    public void onPictureTaken(byte[] image, int imageOrientation) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;

        final Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length, options);

        resultView.post(new Runnable() {
            @Override
            public void run() {


                resultView.setImageBitmap(bitmap);
                cameraView.startPreview();
                //resultView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 720, 576, false));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100,stream);

               // SendImage(stream);
                byte[] byteArray = stream.toByteArray();
                String someString= Base64.encodeToString(byteArray,0);

                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                intent.putExtra("picture", byteArray);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), " Pic Size "+someString, Toast.LENGTH_LONG).show();
                Log.i("Image Size is ", String.valueOf(someString));
//                 File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/" + System.currentTimeMillis()+".jpg");
              //  new ExecuteTaskDStatus().execute("", "10", "tt");
                new ExecuteTaskDStatus().execute("", "10", "tt");


            }
        });
    }


    // server connection

    class ExecuteTaskDStatus extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {

            String res=PutDStatus(params);

            return res;
        }

        @Override
        protected void onPostExecute(String result) {

            // status = result;
            Toast.makeText(getApplicationContext(), "UID"+result, Toast.LENGTH_LONG).show();
        }

    }
    public String PutDStatus(String[] valuse) {
        String s="";
        String ss="test";

        try
        {
            HttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost(UrlPut);

            List<NameValuePair> list=new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("Status", valuse[0]));
            list.add(new BasicNameValuePair("BookingID",valuse[1]));
            list.add(new BasicNameValuePair("DriverID",valuse[2]));

            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse=	httpClient.execute(httpPost);
            // Toast.makeText(getApplicationContext(),  "IMEI is : "+telephonyManager, Toast.LENGTH_LONG).show();
            HttpEntity httpEntity=httpResponse.getEntity();
            s= readResponse(httpResponse);


        }
        catch(Exception ex) 	{
            ex.printStackTrace();
        }

        //status=ss;

        return s;


    }


    //
    public String readResponse(HttpResponse res) {
        InputStream is=null;
        String return_text="";
        try {
            is=res.getEntity().getContent();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
            String line="";
            StringBuffer sb=new StringBuffer();
            while ((line=bufferedReader.readLine())!=null)
            {
                sb.append(line);
            }
            return_text=sb.toString();
        } catch (Exception e)
        {

        }
        return return_text;

    }


}
