package com.stg.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.socialtravelguide.api.StgApi;
import com.socialtravelguide.api.model.ResultsResponse;
import com.squareup.okhttp.ResponseBody;
import com.stg.app.HotelsApplication;
import com.stg.app.R;
import com.stg.app.etbapi.RetrofitCallback;
import com.stg.app.utils.AppLog;
import com.stg.app.widget.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import retrofit.Response;

/**
 * @author user
 * @date 2016-02-17
 */
public class NewRecord extends Activity {
    private static final int PICK_IMAGE_ID = 10;
    Button playButton, stopButton, recordButton, sendButton;
    ImageView imageView;
    private static final String TEMP_IMAGE_NAME = "tempImage";
    private MediaRecorder audioRecorder;
    private String outputFile = null;
    private Bitmap selectedBitmap;


    public static Intent createIntent(Context context) {
        return new Intent(context, NewRecord.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

        imageView = (ImageView) findViewById(R.id.image);
        playButton = (Button) findViewById(R.id.button3);
        stopButton = (Button) findViewById(R.id.button2);
        recordButton = (Button) findViewById(R.id.button);
        sendButton = (Button) findViewById(R.id.button4);
        stopButton.setEnabled(false);
        playButton.setEnabled(false);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        audioRecorder = new MediaRecorder();
        audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        audioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        audioRecorder.setOutputFile(outputFile);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(takePicture, 0);
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(getApplicationContext());
                startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);

            }
        });

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    audioRecorder.prepare();
                    audioRecorder.start();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                recordButton.setEnabled(false);
                stopButton.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioRecorder.stop();
                audioRecorder.release();
                audioRecorder = null;

                stopButton.setEnabled(false);
                playButton.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException, SecurityException, IllegalStateException {
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                StgApi stgApi = HotelsApplication.provide(getApplicationContext()).etbApi();
                selectedBitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
                stgApi.saveImage(encodedImage,"name").enqueue(mResultsCallback);
            }
        });

    }
    private RetrofitCallback<ResultsResponse> mResultsCallback = new RetrofitCallback<ResultsResponse>() {
        @Override
        protected void failure(ResponseBody response, boolean isOffline) {
            AppLog.e("RouteActivity - recordButton failure");

        }

        @Override
        protected void success(ResultsResponse apiResponse, Response<ResultsResponse> response) {

        }

    };

    private static File getTempFile(Context context) {
        File imageFile = new File(context.getExternalCacheDir(), TEMP_IMAGE_NAME);
        imageFile.getParentFile().mkdirs();
        return imageFile;
    }

    private static List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
        }
        return list;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case PICK_IMAGE_ID:
                selectedBitmap = ImagePicker.getImageFromResult(this, resultCode, imageReturnedIntent);
                if(selectedBitmap.getWidth()<selectedBitmap.getHeight()){
                    selectedBitmap = null;
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.place_holder_img));
                    new AlertDialog.Builder(this)
                            .setTitle("Image Alert")
                            .setMessage("You should take image only in landscape mode")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else {
                    imageView.setImageBitmap(selectedBitmap);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}