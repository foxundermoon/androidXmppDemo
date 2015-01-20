package com.swgis.android.xmpp.http;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.entity.UrlEncodedFormEntityHC4;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.EntityUtilsHC4;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;
/**
 * Created by foxundermoon on 2015/1/19.
 */
public class HttpClientDemo {
    public  static void  Demo(){
        SSLContext sslContext = SSLContexts.createSystemDefault();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext,
                SSLConnectionSocketFactory.STRICT_HOSTNAME_VERIFIER);
//        getConfigBuilder = RequestConfig.custom().setConnectionRequestTimeout(30*1000).setMaxRedirects(10).setRedirectsEnabled(true).setSocketTimeout(60*1000);
//        postConfigBuilder = RequestConfig.custom().setCircularRedirectsAllowed(false).setConnectionRequestTimeout(30*1000).setConnectTimeout(30 * 1000).setMaxRedirects(10).setSocketTimeout(60 * 1000);
        CloseableHttpClient  hc = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        HttpGetHC4  getHC4 = new HttpGetHC4();
        getHC4.setURI(URI.create("http://baidu.com"));
        try {
            CloseableHttpResponse response = hc.execute(getHC4);
            String body= EntityUtilsHC4.toString(response.getEntity());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
