package com.swgis.android.xmpp.client.listener;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.provider.IQProvider;

/**
 * Created by foxundermoon on 2015/1/15.
 */
public class IqPacketListener implements PacketListener{
    private IqHandler iqHandler;

    public static synchronized IqPacketListener getInstance() {
        if(instance==null)
            instance = new IqPacketListener();
        return instance;
    }

    public static  void setInstance(IqPacketListener instance) {
        IqPacketListener.instance = instance;
    }

    public IqHandler getIqHandler() {
        return iqHandler;
    }

    public void setIqHandler(IqHandler iqHandler) {
        this.iqHandler = iqHandler;
    }

    private IqPacketListener() {
    }

    private static IqPacketListener instance;
    @Override
    public void processPacket(Packet packet) {
        if(packet instanceof IQ){
            IQ iq = (IQ)packet;
        }
    }
    public interface IqHandler{
        void onIQ(IQ iq);
    }
}
