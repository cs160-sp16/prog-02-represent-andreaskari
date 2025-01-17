package com.example.andreaskarinam.represent;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import io.fabric.sdk.android.Fabric;

public class Main_mobile extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "7sIEH0sd2ieymR8e9i9HV3BnE";
    private static final String TWITTER_SECRET = "WhWUsVkDiLFTJCpM3wDwf2VsaxQ4GptKPogbBHA4mKPXykYlo2";

    TwitterLoginButton login_button;
    Button enter_zipcode;
    Button current_location;

    Location mLastLocation;
    String mLatitudeText;
    String mLongitudeText;

    GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main_mobile);

        enter_zipcode = (Button) findViewById(R.id.button);
        current_location = (Button) findViewById(R.id.button2);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        login_button = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        login_button.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                System.out.println("Set Session");
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                System.out.println("Session failed");
            }
        });
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onRestart()
    {  // After a pause OR at startup
        mGoogleApiClient.connect();
        super.onRestart();
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        System.out.println("Trying to get last location");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            System.out.println(mLastLocation.getLatitude());
            mLatitudeText = String.valueOf(mLastLocation.getLatitude());
            mLongitudeText = String.valueOf(mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void switch_to_zipcode_view(View view) {
        Intent intent = new Intent(this, zipcode_mobile.class);
        startActivity(intent);
    }

    public void switch_to_congressional_view(View view) {
        String message = "/Latitude and Longitude";
        String[] message_contents = {mLatitudeText, mLongitudeText};
        System.out.println(message_contents);

//        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
//        sendIntent.putExtra(message, message_contents);
//        startService(sendIntent);

        Intent intent = new Intent(this, congressional_mobile.class);
        intent.putExtra(message, message_contents);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        login_button.onActivityResult(requestCode, resultCode, data);
    }
}
