package com.travoca.app.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.squareup.okhttp.ResponseBody;
import com.travoca.api.TravocaApi;
import com.travoca.api.model.ResultsResponse;
import com.travoca.api.model.search.Type;
import com.travoca.app.App;
import com.travoca.app.R;
import com.travoca.app.TravocaApplication;
import com.travoca.app.activity.NewRecordActivity;
import com.travoca.app.member.MemberStorage;
import com.travoca.app.member.model.User;
import com.travoca.app.travocaapi.RetrofitCallback;
import com.travoca.app.widget.ImagePicker;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Response;

/**
 * @author ortal
 * @date 2015-05-17
 */
public class NewRecordFragment extends BaseFragment {

    private static final int REQUEST_PERMISSION_LOCATION = 2;
    @Bind(R.id.button3)
    Button playButton;
    @Bind(R.id.button2)
    Button stopButton;
    @Bind(R.id.button)
    Button recordButton;
    @Bind(R.id.button4)
    Button sendButton;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.description)
    TextView mDescription;
    @Bind(R.id.locationName)
    TextView mLocationName;
    @Bind(R.id.lat)
    TextView mLat;
    @Bind(R.id.lon)
    TextView mLon;
    @Bind(R.id.type)
    TextView mType;
    @Bind(R.id.image)
    ImageView mImageView;
    private MediaRecorder audioRecorder;
    private String outputFile = null;
    private Listener mListener;
    private LocationRequest mLocationRequest = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY) // (~100m "block" accuracy)
            .setNumUpdates(1);
    private RetrofitCallback<ResultsResponse> mResultsCallback = new RetrofitCallback<ResultsResponse>() {
        @Override
        protected void failure(ResponseBody response, boolean isOffline) {
            if (getActivity() != null) {
                Toast.makeText(getActivity(), "failure", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void success(ResultsResponse apiResponse, Response<ResultsResponse> response) {
            if (getActivity() != null) {
                Toast.makeText(getActivity(), "success", Toast.LENGTH_LONG).show();
            }
        }

    };

    public static NewRecordFragment newInstance() {
        NewRecordFragment fragment = new NewRecordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_record, container, false);
        ButterKnife.bind(this, view);

        stopButton.setEnabled(false);
        playButton.setEnabled(false);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        audioRecorder = new MediaRecorder();
        audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        audioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        audioRecorder.setOutputFile(outputFile);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findCurrentLocation();
                getActivity().startActivityForResult(ImagePicker.getPickImageIntent(getActivity()), NewRecordActivity.PICK_IMAGE_ID);
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

                Toast.makeText(getActivity(), "Recording started", Toast.LENGTH_LONG).show();
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

                Toast.makeText(getActivity(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getActivity(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String encodedFile = "";
                File file = new File(outputFile);

                int size = (int) file.length();
                byte[] bytes = new byte[size];
                try {
                    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                    buf.read(bytes, 0, bytes.length);
                    buf.close();
                    encodedFile = Base64.encodeToString(bytes, Base64.DEFAULT);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                TravocaApi travocaApi = TravocaApplication.provide(getActivity()).travocaApi();
                ((NewRecordActivity) getActivity()).getSelectedBitmap().compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                MemberStorage memberStorage = App.provide(getActivity()).memberStorage();
                User user = memberStorage.loadUser();
                if(user != null) {
                    travocaApi.saveRecordDetails(encodedImage, encodedFile, mTitle.getText().toString(), mDescription.getText().toString(), mLocationName.getText().toString()
                            , mLat.getText().toString(), mLon.getText().toString(), mType.getText().toString(),user.id).enqueue(mResultsCallback);
                }else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Alert")
                            .setMessage("You must login to upload record")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
        });

        return view;
    }

    private void findCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request missing location permission.
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mListener.getGoogleApiClient(),
                    mLocationRequest, new LocationListener() {
                        @Override
                        public void onLocationChanged(android.location.Location location) {
                            mLat.setText(String.valueOf(location.getLatitude()));
                            mLon.setText(String.valueOf(location.getLongitude()));
                        }
                    });
        }
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    public void setImage(Bitmap selectedBitmap) {
        mImageView.setImageBitmap(selectedBitmap);

    }

    public void setImage(Drawable drawable) {
        mImageView.setImageDrawable(drawable);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(false);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                findCurrentLocation();
            }
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }





    public interface Listener {
        GoogleApiClient getGoogleApiClient();

    }


}
