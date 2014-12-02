package com.swgis.android.xmpp.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.swgis.android.xmpp.MyApplication;
import org.jivesoftware.smack.packet.Message;

public class SendMessageReceiver extends BroadcastReceiver {
    NotificationService notificationService;
    MyApplication myApplication;
    public SendMessageReceiver()
    {

    }
    public SendMessageReceiver(NotificationService notificationService) {
        this.notificationService = notificationService;
        myApplication=(MyApplication)notificationService.getApplication();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        String uuid = intent.getStringExtra(Constants.SEND_MESSAGE);
        if(myApplication==null)
            myApplication=(MyApplication)notificationService.getApplication();
        Message message = (Message)myApplication.shareMap.get(uuid);
        notificationService.getXmppManager().getConnection().sendPacket(message);
//                myApplication.shareMap.remove(uuid);

//            Log.d(Constants.SEND_MESSAGE," Exception on SendMessageReciever:"+e.getMessage()+e.getLocalizedMessage()+e.toString());
    }
}
