package com.swgis.android.xmpp.client.listener;

import android.util.Log;
import com.swgis.android.xmpp.MyApplication;
import com.swgis.android.xmpp.client.LogUtil;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
/**
 * Created by foxundermoon on 2014/12/8.
 */
public class PresencePacketListener implements PacketListener {
    public PresenceHandler getPresenceHandler() {
        return presenceHandler;
    }

    public void setPresenceHandler(PresenceHandler presenceHandler) {
        this.presenceHandler = presenceHandler;
    }

    public synchronized static PresencePacketListener getInstance() {
        if(instance==null)
            instance = new PresencePacketListener();
        return instance;
    }

    public static void setInstance(PresencePacketListener instance) {
        PresencePacketListener.instance = instance;
    }

    private PresenceHandler presenceHandler;
    private static PresencePacketListener instance;
    String TAG = LogUtil.makeLogTag(PresencePacketListener.class);
    MyApplication  myApplication = MyApplication.getInstance();
    public void processPacket(Packet packet) {
        if(packet instanceof Presence){
            Presence presence = (Presence)packet;
            if(presenceHandler!=null)
                presenceHandler.onPresence(presence);
            Log.d(TAG,"Presence receive packet ...:"+packet.toXML() );
            //登录响应
//            if(presence.getType()==Presence.Type.subscribed){
//                Intent intent = new Intent();
//                intent.setAction(Constants.XMPP_STATUS);
//                intent.putExtra(Constants.XMPP_STATUS, Constants.SUBSCRIBED);
//                intent.putExtra(Constants.SUBSCRIBED,true);
//                intent.putExtra("from",0);
//                myApplication.sendBroadcast(intent);
//            }
//            //其他用户登录
//            if(presence.getType()== Presence.Type.subscribe){
//                Intent intent = new Intent();
//                intent.setAction(Constants.XMPP_STATUS);
//                intent.putExtra(Constants.XMPP_STATUS,Constants.SUBSCRIB);
//                intent.putExtra("from",presence.getFrom());
//                myApplication.sendBroadcast(intent);
//            }
//            //其他用户下
//            if(presence.getType()== Presence.Type.unsubscribe) {
//                Intent intent = new Intent();
//                intent.setAction(Constants.XMPP_STATUS)
//                        .putExtra(Constants.XMPP_STATUS,Constants.UNSUBSCRID)
//                        .putExtra("from",presence.getFrom());
//                myApplication.sendBroadcast(intent);
//
//
//            }
//            String id = packet.getPacketID();

        }

    }
    public  interface PresenceHandler{
        void onPresence(Presence presence);
    }
}
