package com.swgis.android.xmpp;

import android.app.Application;
import android.content.Intent;
import com.swgis.android.xmpp.client.Constants;
import org.jivesoftware.smack.packet.Packet;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by foxundermoon on 2014/10/30.
 */
public class MyApplication extends Application {
    public Map<String, Object> shareMap;
    public  boolean isLogin = false;
    private static MyApplication applicationInstance;

    public void sendPacketByXmpp(Packet packet) {
        String uuid = UUID.randomUUID().toString();
        shareMap.put(uuid, packet);
        Intent intent = new Intent();
        intent.setAction(Constants.SEND_PACKET);
        intent.putExtra(Constants.SEND_PACKET,uuid);
        sendBroadcast(intent);

    }
    public static MyApplication getInstance(){
        return applicationInstance;
    }

//    public void sendBroadcast(String action,String shareMapKey) {
//        Intent intent = new Intent();
//        intent.setAction(action);
//        intent.putExtra(action,shareMapKey);
//        sendBroadcast(intent);
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        shareMap = new HashMap<String, Object>();
        Map<String,Objects> xmppStatus = new HashMap<String, Objects>();
        shareMap.put(Constants.XMPP_STATUS,xmppStatus);
        applicationInstance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public MyApplication() {
        super();
    }
}
