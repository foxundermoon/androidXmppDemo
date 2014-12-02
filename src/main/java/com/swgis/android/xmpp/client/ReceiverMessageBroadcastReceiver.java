package com.swgis.android.xmpp.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.swgis.android.xmpp.MyApplication;
import com.swgis.android.xmpp.activity.MainActivity;
import org.jivesoftware.smack.packet.Message;

public class ReceiverMessageBroadcastReceiver extends BroadcastReceiver {
    MessageProcesser messageProcesser;
    private  MyApplication myApplication;

    public ReceiverMessageBroadcastReceiver(MyApplication myApplication,MessageProcesser messageProcesser) {
        this.messageProcesser = messageProcesser;
        this.myApplication = myApplication;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String uuid = intent.getStringExtra(Constants.RECEIVER_MESSAGE);
        if(uuid!=null)
        {
            Message message =(Message) myApplication.shareMap.get(uuid);
            messageProcesser.onReceive(message);
        }
    }

    public interface  MessageProcesser{
        public  void onReceive(Message message);
    }
}
