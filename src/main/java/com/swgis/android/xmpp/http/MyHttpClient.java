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
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

public class MyHttpClient {
    public static final boolean DEBUG = LibConfig.HTTP_DEBUG;
    public static final String TAG = "MyHttpClient";
    public static final String HTTP_ENCODE = LibConfig.DEFAULT_HTTP_ENCODE;
    private CloseableHttpClient hc;
    public static MyHttpClient mhc;
    private static final String PREFIX = "--";
    private static final String LINE_END = "\n\r";
    private RequestConfig.Builder getConfigBuilder;
    private RequestConfig.Builder postConfigBuilder;

    protected MyHttpClient() {
        SSLContext sslContext = SSLContexts.createSystemDefault();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext,
                SSLConnectionSocketFactory.STRICT_HOSTNAME_VERIFIER);
        getConfigBuilder = RequestConfig.custom().setConnectionRequestTimeout(30*1000).setMaxRedirects(10).setRedirectsEnabled(true).setSocketTimeout(60*1000);
        postConfigBuilder = RequestConfig.custom().setCircularRedirectsAllowed(false).setConnectionRequestTimeout(30*1000).setConnectTimeout(30 * 1000).setMaxRedirects(10).setSocketTimeout(60 * 1000);
        hc = HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(getConfigBuilder.build()).build();
//		hc=HttpClients.createDefault();
//		hc = HttpClients.custom().setDefaultCookieStore(new BasicCookieStoreHC4()).setMaxConnTotal(100).build(); //new DefaultHttpClient(tcc,params);
//		CookieStore cookieStore = new BasicCookieStore();
//		((DefaultHttpClient) hc).setCookieStore(cookieStore);
    }

    // 封装url 参数  name1=value1&name2=value2
    public static String wrapUrlParam(List<NameValuePair> params)
            throws UnsupportedEncodingException {
        return new UrlEncodedFormEntity(params, HTTP_ENCODE).toString();
    }

    // 封装完整的url  url?n1=v1&n2=v2
    public static String WrapFullUrl(String baseUrl, List<NameValuePair> params)
            throws UnsupportedEncodingException {
        if (!baseUrl.endsWith("?")) {
            baseUrl += "?";
        }
        return baseUrl + wrapUrlParam(params);
    }

    // single tone
    public static MyHttpClient getInstance() {
        if (mhc == null) {
            mhc = new MyHttpClient();
        }
        return mhc;
    }

    public CloseableHttpResponse getResponse(String url) throws IOException {
        HttpGetHC4 get = new HttpGetHC4(url);
        return hc.execute(get);
    }

    //下载bitMap文件
    public Bitmap downloadBitmap(String url) throws IOException, Throwable {
        return BitmapFactory.decodeStream(getInputStream(url));
    }

    // 通过url下载字节文件
    public byte[] downloadBytes(String url) throws
            IOException, ErrorResponseException {
        HttpEntity he = getHttpEntity(url);
        return EntityUtils.toByteArray(he);
    }

    // 通过 url地址下载文本文件
    public String downloadText(String url) throws
            IOException, ErrorResponseException {
        HttpEntity he = getHttpEntity(url);
        return getText(he);
    }

    //从url获取 inputStream
    public InputStream getInputStream(String url) throws Throwable, IOException {
        HttpEntity he = getHttpEntity(url);
        return he.getContent();
    }

    private String getText(HttpEntity he) throws ParseException, IOException {
        return EntityUtilsHC4.toString(he, HTTP_ENCODE);
    }

    // 执行post操作 性能比get低 数据少的时候尽量少用
    public String doPost(String url, List<NameValuePair> params) throws IOException, ErrorResponseException {
        HttpPostHC4 hp = new HttpPostHC4(url);
        UrlEncodedFormEntityHC4 entity = new UrlEncodedFormEntityHC4(params, HTTP_ENCODE);
        hp.setEntity(entity);
        hp.setConfig(getConfigBuilder.build());
        CloseableHttpResponse response = hc.execute(hp);
        HttpEntity he = getHttpEntity(response);
        return getText(he);
    }


    //	上传单个文件
    public String upload(String url, File file) throws ClientProtocolException, IOException, ErrorResponseException {
        return upload(url, new File[]{file}, null);
    }

    public String upload(String url, File[] files) throws ErrorResponseException, IOException {
        return upload(url, files, null);
    }

    //	上传一组文件
    public String upload(String url, File[] files, String textBody) throws ParseException, ClientProtocolException, IOException, ErrorResponseException {
//        HttpPost hp = new HttpPost(url);
        MultipartEntityBuilder multipartEntityBuilder = getDefaultmultipartEntityBuilder();
        String boundary = creatBoundary();
        multipartEntityBuilder.setBoundary(boundary);
        for (File file : files) {
            multipartEntityBuilder.addBinaryBody(file.getName(), file);
//            multipartEntityBuilder.addBinaryBody("file_a",file,ContentType.create("image/jpeg"),file.getName());
//            multipartEntityBuilder.addPart("jpg",new FileBody(file));
        }
        if (!TextUtils.isEmpty(textBody)) {
            multipartEntityBuilder.addTextBody("text", textBody);
        }
        HttpEntity he = multipartEntityBuilder.build();
        return upload(url, he, boundary);
    }

    public String creatBoundary() {
        return "-------------" + System.currentTimeMillis();
    }

    //	上传字节文件
    public String upload(String url, byte[] bytes) throws ParseException, ClientProtocolException, IOException, ErrorResponseException {
        MultipartEntityBuilder multipartEntityBuilder = getDefaultmultipartEntityBuilder();
        String boundary = creatBoundary();
        multipartEntityBuilder.addBinaryBody("no name file", bytes).setBoundary(boundary);
        HttpEntity he = multipartEntityBuilder.build();
        return upload(url, he, boundary);
    }

    //	上传流文件
    public String upload(String url, InputStream is) throws ParseException, ClientProtocolException, IOException, ErrorResponseException {

        MultipartEntityBuilder multipartEntityBuilder = getDefaultmultipartEntityBuilder();
        String boundary = creatBoundary();
        multipartEntityBuilder.addBinaryBody("no_name_file", is).setBoundary(boundary);
        HttpEntity he = multipartEntityBuilder.build();
        HttpPostHC4 httpPostHC4 = new HttpPostHC4(url);
        httpPostHC4.setEntity(he);
//        if(httpPostHC4.containsHeader("Content-Length")){
//            httpPostHC4.removeHeaders("Content-Length");
//        }
//        httpPostHC4.setHeader("Content-Length", is.available() + "");
        httpPostHC4.setHeader("Content-type", "multipart/form-data; boundary="+boundary);
        httpPostHC4.setHeader("IsStream","true");
//        httpPostHC4.setConfig(postConfigBuilder.build());
        String response =  EntityUtilsHC4.toString(hc.execute(httpPostHC4).getEntity());
        return response;

//        return upload(url, he, boundary);
    }

    public String upload(String url, HttpEntity postHttpEntity, String boundary) throws ParseException, ClientProtocolException, IOException, ErrorResponseException {
        HttpPostHC4 hp = new HttpPostHC4(url);
        hp.setHeader("Content-type", "multipart/form-data; boundary=" + boundary);
        hp.setEntity(postHttpEntity);
        hp.setConfig(postConfigBuilder.build());
        return getText(getHttpEntity(hc.execute(hp)));
    }


    public CloseableHttpClient getHttpClient() {
        return hc;
    }

    public MultipartEntityBuilder getDefaultmultipartEntityBuilder() {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartEntityBuilder.setCharset(Charset.forName(HTTP_ENCODE));
        return multipartEntityBuilder;
    }

    private HttpEntity getHttpEntity(String url)
            throws ClientProtocolException, IOException, ErrorResponseException {
        HttpResponse rsp = getResponse(url);
        return getHttpEntity(rsp);
    }

    private HttpEntity getHttpEntity(HttpResponse rsp)
            throws ClientProtocolException, IOException, ErrorResponseException {
        StatusLine sl = rsp.getStatusLine();
        int statusCode = sl.getStatusCode();
        switch (statusCode) {
            case HttpStatus.SC_OK: // 200
                return rsp.getEntity();
            case HttpStatus.SC_MOVED_PERMANENTLY: // 301
            case HttpStatus.SC_MOVED_TEMPORARILY: // 302 httpClient 默认实现了跳转
                // 处理重定向
                String newUri = rsp.getLastHeader("Location").getValue();
                return getHttpEntity(newUri);
            default:
                throw new ErrorResponseException(sl);
        }
    }

    private String getBoundry() {
        return "-------" + UUID.randomUUID().toString();
    }

}
