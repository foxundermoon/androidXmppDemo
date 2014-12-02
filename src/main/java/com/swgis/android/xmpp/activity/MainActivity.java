package com.swgis.android.xmpp.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.swgis.android.xmpp.MyApplication;
import com.swgis.android.xmpp.R;
import com.swgis.android.xmpp.client.*;
import com.swgis.android.xmpp.config.XmppConfig;
import com.swgis.android.xmpp.http.MyHttpClient;
import org.apache.http.client.methods.HttpPostHC4;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtilsHC4;
import org.jivesoftware.smack.packet.Message;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.UUID;


public class MainActivity extends ActionBarActivity implements ReceiverMessageBroadcastReceiver.MessageProcesser {
    Holder holder;
    View.OnClickListener listener;
    private ServiceManager serviceManager;
    NotificationService notificationService;
    //    MyHttpClient httpClient;
    String uploadUrl = "http://10.80.5.222:2222/Upload";
    Handler handler;
    private ReceiverMessageBroadcastReceiver receiverMessageReceiver;
    private com.swgis.android.xmpp.http.MyHttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        httpClient = MyHttpClient.getInstance();
        setContentView(R.layout.activity_main);
        holder = new Holder();
        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 007) {
                    //do sth
                }
            }
        };
        holder.sendBtn = (Button) findViewById(R.id.sendBtn);
        holder.etxt = (EditText) findViewById(R.id.editText);
        holder.txtView = (TextView) findViewById(R.id.textView);
        holder.startBtn = (Button) findViewById(R.id.button2);
        holder.stopBtn = (Button) findViewById(R.id.button3);
        holder.settingBtn = (Button) findViewById(R.id.button4);
        holder.sendImage = (Button) findViewById(R.id.button5);
        holder.imageView = (ImageView) findViewById(R.id.imageView);
        holder.startHcActivity = (Button) findViewById(R.id.startHcActivity);
//        httpClient = MyHttpClient.getInstance();
        registerBtnListener();
        registerMessageReceiver();
//        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

//        notificationService =(NotificationService)activityManager.getRunningServices()
    }

    private void registerMessageReceiver() {
        receiverMessageReceiver = new ReceiverMessageBroadcastReceiver((MyApplication) getApplication(), this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.RECEIVER_MESSAGE);
        registerReceiver(receiverMessageReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        unRegisterMessageReceiver();
        handler.getLooper().quit();
        handler = null;
        super.onDestroy();
    }

    private void unRegisterMessageReceiver() {
        unregisterReceiver(receiverMessageReceiver);
    }

    private void registerBtnListener() {
        listener = new View.OnClickListener() {
            class ServiceState {
                public boolean isStart = false;
//                public  boolean
            }

            ServiceState serviceState = new ServiceState();

            @Override
            public void onClick(View v) {
//                httpClient = MyHttpClient.getInstance();

                if (v == holder.sendBtn) {
                    if (serviceState.isStart) {
                        sendMessage(holder.etxt.getText().toString());
                    }
                }
                if (v == holder.settingBtn) {
                    serviceManager.viewNotificationSettings(MainActivity.this);
                }
                if (v == holder.startBtn) {
                    if (serviceState.isStart) {
                        holder.txtView.setText("was started!");
                    } else {
                        serviceManager = new ServiceManager(MainActivity.this);
                        serviceManager.setNotificationIcon(R.drawable.ic_launcher);
                        serviceManager.startService();
                        serviceState.isStart = true;
                        holder.txtView.setText("started!");
                        Message message = new Message();
                        message.setTo(XmppConfig.getServerJid());
                        message.setFrom(XmppConfig.getLocalJid());
                        message.setBody("connected!");
                        MyApplication.getInstance().sendPacketByXmpp(message);
                    }

                }
                if (v == holder.stopBtn) {
                    serviceState.isStart = false;
                    serviceManager.stopService();

                }
                if (v == holder.sendImage) {
                    sendImage(holder.etxt.getText().toString());
                }
                if(v==holder.sendBtn){
                    NotificationIQ notificationIQ = new NotificationIQ();
                    notificationIQ.setFrom("1@10.80.5.222");
                    notificationIQ.setTo("0@10.80.5.222");
                    notificationIQ.setId(UUID.randomUUID().toString());
                    notificationIQ.setMessage("message from android client");
                    notificationIQ.setTitle("the title");
                    holder.txtView.setText(notificationIQ.toXML());
                    MyApplication.getInstance().sendPacketByXmpp(notificationIQ);
                }
                if (v == holder.startHcActivity) {
                    startHcActivity();
                }
            }
        };
        holder.setOnclickListener(listener);
    }

    private void startHcActivity() {
        startActivity(new Intent(MainActivity.this, HcActivity.class));
        finish();
    }

    private void sendImage(final String s) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = getResources().openRawResource(R.raw.cat);
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(is.available());
                    int len;
                    int total = 0;
                    byte[] buf = new byte[1024];
                    while ((len = is.read(buf)) != -1) {
                        byteArrayOutputStream.write(buf, 0, len);
                    }

//                    final String response = httpClient.upload(uploadUrl,byteArrayOutputStream.toByteArray());
                    CloseableHttpClient hc = httpClient.getHttpClient();
                    String boundary = httpClient.creatBoundary();
                    MultipartEntityBuilder multipartEntityBuilder = httpClient.getDefaultmultipartEntityBuilder()
                            .addBinaryBody("inputStream", is, ContentType.create("image/jpeg"), "cat.jpg")
                            .addBinaryBody("byteArray", byteArrayOutputStream.toByteArray(), ContentType.create("image/jpeg"), "cat.jpg")
                            .addTextBody("text", "the text from textBody..............")
                            .setBoundary(boundary);
                    HttpPostHC4 httpPostHC4 = new HttpPostHC4(uploadUrl);
                    httpPostHC4.setHeader("Content-type", "multipart/form-data; boundary=" + boundary);
//                    httpPostHC4.setHeader("IsStream", "true");
                    httpPostHC4.setEntity(multipartEntityBuilder.build());
                    final String response = EntityUtilsHC4.toString(hc.execute(httpPostHC4).getEntity());
                    Log.d("httpclient", "post file response:" + response);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("text", s).put("img", response);
                    Message message = new Message();
                    message.setTo("0@10.80.50.222");
                    message.setFrom("1@10.80.5.222");
                    message.setBody(jsonObject.toString());
//                    MyApplication.getInstance().sendPacketByXmpp(message);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.txtView.append(response);
                        }
                    });
                } catch (Exception e) {
                    Log.e("uploadexception", e.getLocalizedMessage(), e);

                }
            }
        });
        t.start();

        NotificationIQ noti = new NotificationIQ();
        noti.setMessage(s);
        noti.setId(UUID.randomUUID().toString());
        noti.setTitle("c 2 s");
        try {
            ((MyApplication) getApplication()).sendPacketByXmpp(noti);
            Log.d("notification", noti.toString());
        } catch (Exception e) {
            holder.txtView.append(e.getMessage() + e.toString());
        }

    }

    /**
     * send message to server
     *
     * @param s
     */
    private void sendMessage(String s) {
        Message message = new Message();
        String to = ((TextView) findViewById(R.id.editText2)).getText().toString();
        message.setTo(to + "@10.80.5.222");
        message.setFrom("1@10.80.5.222");
        message.setBody("hello ni hao");
        Intent intent = new Intent();
        intent.setAction(Constants.SEND_MESSAGE);
        Bundle bundle = new Bundle();
        String uuid = UUID.randomUUID().toString();
        MyApplication myApplication = (MyApplication) getApplication();
        myApplication.shareMap.put(uuid, message);
//        bundle.putCharSequence(Constants.SEND_MESSAGE,uuid);
        intent.putExtra(Constants.SEND_MESSAGE, uuid);
        sendBroadcast(intent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onReceive(Message message) {
        holder.txtView.append(message.toXML());
    }

    class Holder {
        public Button sendBtn;
        public TextView txtView;
        public EditText etxt;
        public Button startBtn;
        public Button stopBtn;
        public Button settingBtn;
        public Button sendImage;
        public Button startHcActivity;

        public ImageView imageView;

        public void setOnclickListener(View.OnClickListener listener) {
            sendBtn.setOnClickListener(listener);
            startBtn.setOnClickListener(listener);
            stopBtn.setOnClickListener(listener);
            settingBtn.setOnClickListener(listener);
            sendImage.setOnClickListener(listener);
            startHcActivity.setOnClickListener(listener);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("sendBtn:").append(sendBtn == null ? "null" : sendBtn.toString());
            sb.append("txtView:").append(txtView == null ? "null" : txtView.toString());
            sb.append("etxt:").append(etxt == null ? "null" : etxt.toString());
            return sb.toString();
        }
    }
}
