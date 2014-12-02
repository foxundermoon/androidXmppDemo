package com.swgis.android.xmpp.client;

import android.util.Log;
import com.swgis.android.xmpp.config.XmppConfig;
import org.jivesoftware.smack.packet.Presence;

/**
 * Created by foxundermoon on 2014/11/28.
 */
public class HeartThreadRunnable implements Runnable{
    private static String TAG = HeartThreadRunnable.class.getSimpleName();
    public static boolean interrupted=false;
    private static int defaultDuration = 30*1000;
    private  XmppManager xmppManager;
    private Presence presence;

    public static HeartThreadRunnable getInstance(XmppManager xmppManager) {
        synchronized (HeartThreadRunnable.class){
            if(instance==null){
                instance=new HeartThreadRunnable(xmppManager,defaultDuration);
            }
        }
        return instance;
    }

    private static HeartThreadRunnable instance;

    public boolean isInterrupt() {
        return interrupt;
    }


    public static void setInterrupt(boolean _interrupt){
        interrupt = _interrupt;
        interrupted=true;

    }

    private static boolean interrupt =false;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    private int duration;
    public HeartThreadRunnable(XmppManager xmppManager, int duration) {
        this.xmppManager = xmppManager;
        presence = new Presence(Presence.Type.available);
        presence.setFrom(XmppConfig.getLocalJid());
        presence.setTo(XmppConfig.getServerJid());
        presence.setStatus("online");
        setDuration(duration);

    }

    @Override
    public void run() {
        while (!interrupt){
            heartBeat();
            try {
                Thread.currentThread().sleep((long)duration);
            } catch (InterruptedException ignore) {
                Log.d(TAG,"thread sleep catch exception when net heart beat!");
            }
        }
    }
    private void heartBeat(){
        xmppManager.getConnection().sendPacket(presence);
    }
    public static void setup(XmppManager xmppManager){
            HeartThreadRunnable runnable= HeartThreadRunnable.getInstance(xmppManager);
        runnable.setInterrupt(false);
        new Thread(runnable).start();
    }
    public static void  shutDown(){
        HeartThreadRunnable.setInterrupt(true);
    }
}
