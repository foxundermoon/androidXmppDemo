package com.swgis.android.xmpp.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.swgis.android.xmpp.client.Constants;

public class XmppStatusReceiver extends BroadcastReceiver {
    StatusHandler statusHandler;

    public XmppStatusReceiver(StatusHandler handler) {
        statusHandler = handler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        int code = intent.getIntExtra(Constants.XMPP_STATUS, 0);
        switch (code) {
            case XmppStatusCode.LoginedSuccess:
                statusHandler.onLogin(true);
                break;
            case XmppStatusCode.LoginFailed:
                statusHandler.onLogin(false);
                break;
            default:
                statusHandler.handle(intent);

        }
//        statusHandler.handle(intent);
    }

    public interface StatusHandler {
        public void handle(Intent intent);

        public void onLogin(boolean success);

        public void onXmppError(Intent intent);
    }
}
