package net.mootoh.glove;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String message = "normal";
        Intent intent = getIntent();
        String thrown = intent.getStringExtra("thrown");
        if (thrown != null) {
            message = thrown;
        } else {
            showNotification();
        }

        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Yay!")
                .setContentText("Hello World")
//                .addAction(R.drawable.ic_launcher, "Paste", null)
                ;
        Intent resultIntent = new Intent(this, CopyActivity.class);
        resultIntent.putExtra("thrown", "yay");

//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
