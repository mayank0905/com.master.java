package com.hsbc.qe.api.httpoperations;

import javax.net.ssl.TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpsCertTrust implements TrustManager, javax.net.ssl.X509TrustManager {
    /**
     * Method to trust all the HTTPS certificates. To be used only in the
     * development environment for convenience.
     */
    public static void trustAllHttpsCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[1];
            TrustManager tm = new HttpsCertTrust();
            trustAllCerts[0] = tm;
            javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
            javax.net.ssl.SSLSessionContext serverSessionContext = sc.getServerSessionContext();
            serverSessionContext.setSessionTimeout(0);
            sc.init(null, trustAllCerts, null);
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException | NoSuchAlgorithmException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    @Override
    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
        // Explicitly do nothing
    }

    @Override
    public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
        // Explicitly do nothing
    }
}
