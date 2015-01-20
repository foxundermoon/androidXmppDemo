/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.swgis.android.xmpp.client.listener;

import android.content.Intent;
import android.util.Log;
import com.swgis.android.xmpp.client.Constants;
import com.swgis.android.xmpp.client.LogUtil;
import com.swgis.android.xmpp.client.NotificationIQ;
import com.swgis.android.xmpp.client.XmppManager;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;

/** 
 * This class notifies the receiver of incoming notifcation packets asynchronously.  
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationPacketListener implements PacketListener {
    private static NotificationPacketListener instance;
    private NotificationIqHandler notificationIqHandler;

    public synchronized static NotificationPacketListener getInstance(XmppManager xmppManager) {
        if(instance==null)
            instance = new NotificationPacketListener(xmppManager);
        return instance;
    }

    public static void setInstance(NotificationPacketListener instance) {
        NotificationPacketListener.instance = instance;
    }

    public NotificationIqHandler getNotificationIqHandler() {
        return notificationIqHandler;
    }

    public void setNotificationIqHandler(NotificationIqHandler notificationIqHandler) {
        this.notificationIqHandler = notificationIqHandler;
    }

    private static final String LOGTAG = LogUtil
            .makeLogTag(NotificationPacketListener.class);

    private final XmppManager xmppManager;

    private NotificationPacketListener(XmppManager xmppManager) {
        this.xmppManager = xmppManager;
    }

    @Override
    public void processPacket(Packet packet) {
        Log.d(LOGTAG, "NotificationPacketListener.processPacket()...");
        Log.d(LOGTAG, "packet.toXML()=" + packet.toXML());

        if (packet instanceof NotificationIQ) {
            NotificationIQ notification = (NotificationIQ) packet;
            if(notificationIqHandler !=null)
                notificationIqHandler.onNotificationIQ(notification);

//            if (notification.getChildElementXML().contains(
//                    "androidpn:iq:notification")) {
//                String notificationId = notification.getId();
//                String notificationApiKey = notification.getApiKey();
//                String notificationTitle = notification.getTitle();
//                String notificationMessage = notification.getMessage();
//                //                String notificationTicker = notification.getTicker();
//                String notificationUri = notification.getUri();
//
//                Intent intent = new Intent(Constants.ACTION_SHOW_NOTIFICATION);
//                intent.putExtra(Constants.NOTIFICATION_ID, notificationId);
//                intent.putExtra(Constants.NOTIFICATION_API_KEY,
//                        notificationApiKey);
//                intent
//                        .putExtra(Constants.NOTIFICATION_TITLE,
//                                notificationTitle);
//                intent.putExtra(Constants.NOTIFICATION_MESSAGE,
//                        notificationMessage);
//                intent.putExtra(Constants.NOTIFICATION_URI, notificationUri);
//                //                intent.setData(Uri.parse((new StringBuilder(
//                //                        "notif://notification.androidpn.org/")).append(
//                //                        notificationApiKey).append("/").append(
//                //                        System.currentTimeMillis()).toString()));
//
//                xmppManager.getContext().sendBroadcast(intent);
//            }
        }

    }
    public interface NotificationIqHandler{
        void onNotificationIQ(NotificationIQ notificationIQ);
    }

}
