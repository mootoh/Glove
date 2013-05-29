package net.mootoh.glove;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.initialize(this, "", "");
        PushService.setDefaultPushCallback(this, MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseAnalytics.trackAppOpened(getIntent());

        String message = "normal";
        Intent intent = getIntent();
        String thrown = intent.getStringExtra("thrown");
        if (thrown != null) {
            message = thrown;
        } else {
            showNotification();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification() {
        Log.d(getClass().getSimpleName(), "notification");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Yay!")
                .setContentText("Hello World")
//                .addAction(R.drawable.ic_launcher, "Paste", null)
                ;

        // Setting a BroadcastReceiver as a notification action handler.
        Intent resultIntent = new Intent(this, NotifiedReceiver.class);
        resultIntent.putExtra("thrown", "yay");
        PendingIntent resultPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}