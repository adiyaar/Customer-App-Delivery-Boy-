package com.themescoder.driver.api;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicOAuth implements Interceptor {
    
    private static final String OAUTH_CONSUMER_KEY = "consumer_key";
    private static final String OAUTH_CONSUMER_SECRET = "consumer_secret";
    private static final String OAUTH_COMSUMER_IP = "consumer-ip";
    
    private final String consumerKey;
    private final String consumerSecret;
    private final String consumerIP;
    
    
    private BasicOAuth(String consumerKey, String consumerSecret,String consumerIP) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.consumerIP = consumerIP;
    }
    
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();
    
        Log.i("request_method", ""+original.method());
        Log.i("request_URL", original.url().toString());
        Log.i("request_host", ""+original.url().host());
        Log.i("request_encodedPath", original.url().encodedPath());
        Log.i("request_query", ""+original.url().query());
        Log.i("request_encodedQuery", ""+original.url().encodedQuery());
    
        ////////////////////////////////////////////////////////////
        
        
        String basicOAuthString = OAUTH_CONSUMER_KEY + "=" + consumerKey + "&" + OAUTH_CONSUMER_SECRET + "=" + consumerSecret
                                    +"&" + OAUTH_COMSUMER_IP + "=" + consumerIP;
        Log.i("basicOAuthString", "basicOAuthString="+basicOAuthString);
        
        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter(OAUTH_CONSUMER_KEY, consumerKey)
                .addQueryParameter(OAUTH_CONSUMER_SECRET, consumerSecret)
                .addQueryParameter(OAUTH_COMSUMER_IP,consumerIP)
                .build();
        
        
        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
                .addHeader("Content-Type", "application/json")
                .url(url);
        
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
    
    
    public static final class Builder {
        
        private String consumerKey;
        private String consumerSecret;
        private String consumerIP;
        private int type;
        
        public Builder consumerKey(String consumerKey) {
            if (consumerKey == null) throw new NullPointerException("consumerKey = null");
            this.consumerKey = consumerKey;
            return this;
        }
        
        public Builder consumerSecret(String consumerSecret) {
            if (consumerSecret == null) throw new NullPointerException("consumerSecret = null");
            this.consumerSecret = consumerSecret;
            return this;
        }

        public Builder consumerIP(String consumerIP) {
            if (consumerIP == null) throw new NullPointerException("consumerIP = null");
            this.consumerIP = consumerIP;
            return this;
        }



        public BasicOAuth build() {
            
            if (consumerKey == null) throw new IllegalStateException("consumerKey not set");
            if (consumerSecret == null) throw new IllegalStateException("consumerSecret not set");
            if (consumerSecret == null) throw new IllegalStateException("consumerIP not set");

            return new BasicOAuth(consumerKey, consumerSecret,consumerIP);
        }
    }
    
    public String urlEncoded(String url) {
        String encodedurl = "";
        try {
            
            encodedurl = URLEncoder.encode(url, "UTF-8");
            Log.d("encodedURL", encodedurl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        return encodedurl;
    }
}
