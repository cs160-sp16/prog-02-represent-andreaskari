package com.example.andreaskarinam.represent;

import android.content.Intent;
import android.util.Log;

import com.example.andreaskarinam.mylibrary.FakeData;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

public class WatchListenerService extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());

        if(messageEvent.getPath().equalsIgnoreCase(FakeData.COUNTY_INDEX_KEY) ) {
            String county_string = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            int county_index = Integer.valueOf(county_string);
            System.out.println(county_index);
            Intent intent = new Intent(this, main_watch.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(FakeData.COUNTY_INDEX_KEY, county_index);
            startActivity(intent);
        } else {
            super.onMessageReceived(messageEvent );
        }

    }
}