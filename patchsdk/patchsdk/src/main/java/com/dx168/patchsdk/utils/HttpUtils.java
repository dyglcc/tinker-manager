package com.dx168.patchsdk.utils;

import android.text.TextUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by dongyuangui on 2017/11/7.
 */

public class HttpUtils {

    // add https
    private static void trustAllHosts(HttpsURLConnection conn) {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
            }

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, null);
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HttpURLConnection getCon(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        if (url.startsWith("https:")) {
            try {
                HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
                trustAllHosts(conn);
                return conn;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (url.startsWith("http:")) {
            try {
                return (HttpURLConnection) new URL(url).openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
}
