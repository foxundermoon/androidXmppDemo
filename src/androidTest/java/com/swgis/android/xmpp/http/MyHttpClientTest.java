package com.swgis.android.xmpp.http;

import android.content.res.Resources;
import android.test.AndroidTestCase;
import android.util.Log;
import com.swgis.android.xmpp.R;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyHttpClientTest extends AndroidTestCase {
    private MyHttpClient myHttpClient;
    private static String TAG = MyHttpClientTest.class.getSimpleName();

    public void setUp() throws Exception {
        myHttpClient = MyHttpClient.getInstance();
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    public void testWrapUrlParam() throws Exception {

    }

    public void testWrapFullUrl() throws Exception {

    }

    public void testGetResponse() throws Exception {

    }

    public void testDownloadBitmap() throws Exception {

    }

    public void testDownloadBytes() throws Exception {
        String url = "http://10.80.5.222/test/test.txt";
        try {
            byte[] result = myHttpClient.downloadBytes(url);
            Log.i(getName(), new String(result));
            Assert.assertNotNull(result);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }

    }

    public void testDownloadText() throws Exception {
        String url = "http://10.80.5.222/test/test.txt";
        try {
            String result = myHttpClient.downloadText(url);
            Log.i(this.getName(), result);
            Log.v("---", result);
            Assert.assertFalse(result, result == null);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        } catch (ErrorResponseException e) {
            Assert.fail(e.getMessage());
        }

    }

    public void testGetInputStream() throws Exception {
        String url = "http://10.80.5.222/test/test.txt";
        try {
            InputStream is = myHttpClient.getInputStream(url);


        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Assert.fail(throwable.getMessage() + throwable.toString());
        }

    }

    public void testDoPost() throws Exception {
        NameValuePair nameValuePair = new NameValuePair() {
            @Override
            public String getName() {
                return "test";
            }

            @Override
            public String getValue() {
                return "im in idea";
            }
        };

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(nameValuePair);
        String postUrl = "http://10.80.5.222:2222/test/testpost";
        try {
            String result = myHttpClient.doPost(postUrl, nameValuePairs);
            Log.i("httpresult", "myHttpClient.doPost:" + result);
            Assert.assertEquals(result, "ok");

        } catch (ErrorResponseException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }
    }

    public void testUpload() throws Exception {
        File file = File.createTempFile(UUID.randomUUID().toString(), "tst");
        FileWriter fileWriter = new FileWriter(file);
        for (int i = 0; i < 1000; i++) {
            fileWriter.write(UUID.randomUUID().toString() + "\r\n");
        }
        fileWriter.flush();
        fileWriter.close();
        String uploadUrl = "http://local.vvfox.com:2222/upload";
        try {
            String result = myHttpClient.upload(uploadUrl, file);

        } catch (IOException e) {
            Assert.fail(e.getMessage());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }

    public void testUploadByInputStream() throws Exception {
        Resources resources = Resources.getSystem();
        InputStream inputStream = resources.openRawResource(R.raw.cat);
        String url = "http://10.80.5.222:2222/upload";
        try {
            String result =myHttpClient.upload(url,inputStream);
            Log.i(TAG,"testUploadByInputStream() result:"+result);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        }

    }

    public void testUpload2() throws Exception {

    }

    public void testUpload3() throws Exception {

    }

    public void testUpload4() throws Exception {

    }
}