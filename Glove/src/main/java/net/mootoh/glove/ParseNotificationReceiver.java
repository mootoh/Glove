package net.mootoh.glove;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by mootoh on 5/29/13.
 */
public class ParseNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(getClass().getSimpleName(), "got notification from Parse: Data=" + intent.getExtras().getString("com.parse.Data"));
        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            final String alert = json.getString("alert");

            // Store the passed object into clipboard.
            ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData cd = ClipData.newPlainText("thrown", alert);
            cm.setPrimaryClip(cd);
        } catch (JSONException e) {
            Log.d(getClass().getSimpleName(), "JSONException: " + e.getMessage());
        }
    }
}
