package com.swgis.android.xmpp.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.swgis.android.xmpp.MyApplication;
import com.swgis.android.xmpp.client.Constants;
import org.jivesoftware.smack.packet.Packet;

public class ReceivePacketReceiver extends BroadcastReceiver {
    PacketHandler packetHandler;
    private  MyApplication myApplication;

    public ReceivePacketReceiver( PacketHandler packetHandler) {
        this.packetHandler = packetHandler;
        myApplication = MyApplication.getInstance();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String uuid = intent.getStringExtra(Constants.RECEIVER_MESSAGE);
        if(uuid!=null)
        {
            Packet packet =(Packet) myApplication.shareMap.get(uuid);
//            Message message =(Message) myApplication.shareMap.get(uuid);
            packetHandler.onPacket(packet);
        }
    }

    public interface PacketHandler {
        public  void onPacket(Packet packet);
    }
}
