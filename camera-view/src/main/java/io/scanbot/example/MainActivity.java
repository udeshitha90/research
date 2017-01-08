package io.scanbot.example;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import net.doo.snap.camera.AutoSnappingController;
import net.doo.snap.camera.ContourDetectorFrameHandler;
import net.doo.snap.camera.PictureCallback;
import net.doo.snap.camera.ScanbotCameraView;
import net.doo.snap.ui.PolygonView;

import java.io.ByteArrayOutputStream;


public class MainActivity extends AppCompatActivity implements PictureCallback {

    private ScanbotCameraView cameraView;
    private ImageView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);

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
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Intent intent = new Intent(MainActivity.this, ViewImage.class);
                intent.putExtra("picture", byteArray);
                startActivity(intent);
                // File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/" + System.currentTimeMillis()+".jpg");
            }
        });
    }

}
