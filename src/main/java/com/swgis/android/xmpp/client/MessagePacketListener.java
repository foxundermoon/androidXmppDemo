package com.swgis.android.xmpp.client;

import android.content.Intent;
import android.util.Log;
import com.swgis.android.xmpp.MyApplication;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import java.util.UUID;

/**
 * Created by foxundermoon on 2014/11/13.
 */
public class MessagePacketListener implements PacketListener {
    private MyApplication myApplication;

    public MessagePacketListener() {
        this.myApplication = MyApplication.getInstance();
    }

    @Override
    public void processPacket(Packet packet) {
        Log.d(Constants.RECEIVER_MESSAGE, "MessagePacketListener.processPacket()...");
        Log.d(Constants.RECEIVER_MESSAGE, "packet.toXML()=" + packet.toXML());
        if(packet instanceof Message)
        {
            String uuid = UUID.randomUUID().toString();
            myApplication.shareMap.put(uuid,packet);
//            Intent intent = new Intent();
//            intent.setAction(Constants.RECEIVER_MESSAGE);
//            intent.putExtra(Constants.RECEIVER_MESSAGE,uuid);
            myApplication.sendBroadcast(Constants.RECEIVER_MESSAGE,uuid);
        }else{
            Log.d(Constants.RECEIVER_MESSAGE, "packet is not message...");
        }



    }
}
