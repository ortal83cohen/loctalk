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
import com.travoca.app.fragment.LoginFragment;
import com.travoca.app.fragment.NewRecordFragment;
import com.travoca.app.widget.ImagePicker;

import java.io.File;
import java.util.List;

/**
 * @author user
 * @date 2016-02-17
 */
public class LoginActivity extends BaseActivity {


    private static final String FRAGMENT_LOGIN = "login";

    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment.newInstance(),
                            FRAGMENT_LOGIN)
                    .commit();
        }

    }

    @Override
    protected void onCreateContentView() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}