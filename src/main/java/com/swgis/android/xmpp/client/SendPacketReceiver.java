package com.swgis.android.xmpp.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.swgis.android.xmpp.MyApplication;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

public class SendPacketReceiver extends BroadcastReceiver {
    private NotificationService notificationService;
    private MyApplication myApplication;
    public SendPacketReceiver(NotificationService notificationService) {
        this.notificationService = notificationService;
        myApplication = (MyApplication) notificationService.getApplication();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (myApplication == null)
            myApplication = (MyApplication) notificationService.getApplication();

        String uuid = intent.getStringExtra(Constants.SEND_PACKET);
        if (uuid != null) {
            Packet packet = (Packet) myApplication.shareMap.get(uuid);
            if(packet !=null){
                notificationService.getXmppManager().getConnection().sendPacket(packet);
                myApplication.shareMap.remove(uuid);
                Log.d(Constants.SEND_PACKET,packet.toXML());
            }else{
                Log.d(Constants.SEND_PACKET,"the packet from intent is null");
            }

        }else{
            Log.d(Constants.SEND_PACKET,"the uuid is null  from intent");
        }
    }
}
