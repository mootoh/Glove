package net.mootoh.glove;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by mootoh on 5/29/13.
 */
public class NotifiedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String thrown = intent.getStringExtra("thrown");
        Log.d(getClass().getSimpleName(), "received an action from notification: " + thrown);

        // Store the passed object into clipboard.
        ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData cd = ClipData.newPlainText("thrown", thrown);
        cm.setPrimaryClip(cd);
    }
}