package org.flexlabs.widgets.gmailmulti;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Region;
import android.os.PatternMatcher;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by IntelliJ IDEA.
 * User: Artiom Chilaru
 * Date: 19/06/11
 * Time: 10:11
 */
public class GMailWizApplication extends Application {
    private static boolean isRegistered = false;
    public static int mailCount = 0;

    private static final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mailCount = intent.getIntExtra("count", 0);
            Log.d("FlexLabs", "Receiver intent: " + intent);
            Log.d("FlexLabs", "Receiver bundle: " + TextUtils.join(", ", intent.getExtras().keySet()));
            for (String key : intent.getExtras().keySet()) {
                Log.d("FlexLabs", "Receiver key: " + key + " = " + intent.getExtras().get(key));
            }

            context.sendBroadcast(new Intent(Constants.ACTION_WIDGET_UPDATE));
        }
    };

    public static void registerReceiver(Context context) {
        if (!isRegistered) {
            IntentFilter intentFilter = new IntentFilter(Intent.ACTION_PROVIDER_CHANGED);
            intentFilter.addDataScheme("content");
            intentFilter.addDataAuthority("gmail-ls", null);
            //intentFilter.addDataPath("/unread/^i", PatternMatcher.PATTERN_SIMPLE_GLOB);
            context.getApplicationContext().registerReceiver(receiver, intentFilter);
            isRegistered = true;
        }
    }

    public static void unregisterReceiver(Context context) {
        if (isRegistered) {
            context.getApplicationContext().unregisterReceiver(receiver);
            isRegistered = false;
        }
    }
}
