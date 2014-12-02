package com.swgis.android.xmpp.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.swgis.android.xmpp.R;
import com.swgis.android.xmpp.http.ErrorResponseException;
import com.swgis.android.xmpp.http.MyHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpGetHC4;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtilsHC4;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.LogRecord;

public class HcActivity extends ActionBarActivity {
    private Holder holder;
    private Handler handler;
    private static String uploadUrl = "http://10.80.5.222:2222/upload/index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hc);
        holder = new Holder();
        holder.downloadBtn = (Button) findViewById(R.id.downloadbtn);
        holder.postBtn = (Button) findViewById(R.id.postbtn);
        holder.getBtn = (Button) findViewById(R.id.getbtn);
        holder.responseTextView = (TextView) findViewById(R.id.responseTextView);
        holder.inputEditText = (EditText) findViewById(R.id.httpclientEditText);
        handler = new Handler();

        registerBtnClickListener();
    }

    private void registerBtnClickListener() {
        holder.setButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == holder.downloadBtn) {
//                    new DownloadTask(holder.responseTextView).execute();
                    new UploadTask().execute();

                }
                if (v == holder.getBtn) {
//                    CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
//                    new HttpGetTask(holder.responseTextView).execute();


                }
                if (v == holder.postBtn) {
                    new Thread() {
                        @Override
                        public void run() {
                            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
                            HttpGetHC4 httpGetHC4 = new HttpGetHC4("http://baidu.com");
                            CloseableHttpResponse closeableHttpResponse = null;

                            try {
                                closeableHttpResponse = closeableHttpClient.execute(httpGetHC4);
                                final String result = EntityUtilsHC4.toString(closeableHttpResponse.getEntity());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        holder.responseTextView.setText(result);
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();


                }
            }
        });
    }

    class HttpGetTask extends AsyncTask<String, Void, String> {
        private final TextView textView;

        public HttpGetTask(TextView textView) {
            super();
            this.textView = textView;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
        }

        @Override
        protected String doInBackground(String... params) {
            CloseableHttpClient chc = HttpClients.createDefault();
            return null;
        }
    }

    class UploadTask extends AsyncTask<Void, Void, String> {
        public UploadTask() {
            super();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            holder.responseTextView.setText(s);
        }

        @Override
        protected String doInBackground(Void... params) {
//            InputStream inputStream =getResources().openRawResource(R.raw.cat);
            MyHttpClient myHttpClient = MyHttpClient.getInstance();
            try {
                String path = getSDPath();
                File file = new File(path+"/acat.jpg");
                if (!file.exists()) {
                    InputStream inputStream = getResources().openRawResource(R.raw.cat); //getAssets().open("cat.jpg");
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len=inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer,0,len);
                    }
                    fileOutputStream.close();
                    inputStream.close();
                }

                String result = myHttpClient.upload(uploadUrl, file);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } catch (ErrorResponseException e) {
                e.printStackTrace();
                return e.toString();
            }
        }

    }

    class DownloadTask extends AsyncTask<Void, Void, String> {
        private final TextView textView;
        private String editText;

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            editText = holder.inputEditText.getText().toString();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                SSLContext sslContext = SSLContexts.createSystemDefault();
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                        sslContext,
                        SSLConnectionSocketFactory.STRICT_HOSTNAME_VERIFIER);
                CloseableHttpClient chc = HttpClients.custom().setSSLSocketFactory(sslsf).build();
                if (TextUtils.isEmpty(editText)) {
                    editText = "http://baidu.com";
                }
                HttpGetHC4 httpGet = new HttpGetHC4(editText);
                try {
                    CloseableHttpResponse httpResponse = chc.execute(httpGet);
                    return EntityUtilsHC4.toString(httpResponse.getEntity(), Charset.forName("UTF-8"));

                } catch (IOException e) {
                    e.printStackTrace();
                    return e.toString();
                }
            } catch (Exception e) {
                return e.toString();
            }

        }

        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);

        }

        public DownloadTask(TextView textView) {
            super();
            this.textView = textView;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if   (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();

    }

    class Holder {
        public Button getBtn;
        public Button postBtn;
        public Button downloadBtn;
        public TextView responseTextView;
        public EditText inputEditText;

        public void setButtonClickListener(View.OnClickListener listener) {
            getBtn.setOnClickListener(listener);
            postBtn.setOnClickListener(listener);
            downloadBtn.setOnClickListener(listener);
        }
    }
}
