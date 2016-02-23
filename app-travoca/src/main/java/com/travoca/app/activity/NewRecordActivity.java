package com.travoca.app.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.travoca.app.R;
import com.travoca.app.fragment.NewRecordFragment;
import com.travoca.app.widget.ImagePicker;

import java.io.File;
import java.util.List;

/**
 * @author user
 * @date 2016-02-17
 */
public class NewRecordActivity extends BaseActivity implements NewRecordFragment.Listener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    public static final int PICK_IMAGE_ID = 10;
    private static final int REQUEST_PERMISSION_LOCATION = 1;
    private static final String FRAGMENT_NEW_RECORD = "fragment new record";
    private static final String TEMP_IMAGE_NAME = "image";
    protected GoogleApiClient mGoogleApiClient;
    private Bitmap selectedBitmap;

    public static Intent createIntent(Context context) {
        return new Intent(context, NewRecordActivity.class);
    }

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, NewRecordFragment.newInstance(),
                            FRAGMENT_NEW_RECORD)
                    .commit();
        }
    }

    @Override
    protected void onCreateContentView() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case PICK_IMAGE_ID:
                if (resultCode != 0) {
                    NewRecordFragment fragment = (NewRecordFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_NEW_RECORD);
                    selectedBitmap = ImagePicker.getImageFromResult(this, resultCode, imageReturnedIntent);
                    if (selectedBitmap.getWidth() < selectedBitmap.getHeight()) {
                        selectedBitmap = null;
                        fragment.setImage(getResources().getDrawable(R.drawable.place_holder_img));
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
                    } else {
                        fragment.setImage(selectedBitmap);
                    }
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
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Toast.makeText(this,
                getString(R.string.could_not_connect_to_google) + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public Bitmap getSelectedBitmap() {
        return selectedBitmap;
    }
}