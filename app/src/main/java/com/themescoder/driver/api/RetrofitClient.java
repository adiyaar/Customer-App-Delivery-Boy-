package com.themescoder.driver.api;

import android.text.format.Formatter;
import android.util.Log;

import com.themescoder.driver.constents.ConstentValues;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String BASE_URL = ConstentValues.WEBSITE_URL + "deliveryboy/";
    public static final String BASE_URL_RES = ConstentValues.WEBSITE_URL;


    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private static Api apiRequests;

    // Singleton Instance of APIRequests
    public static Api getInstance() {
        if (apiRequests == null) {

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            API_Interceptor apiInterceptor = new API_Interceptor.Builder()
                    .consumerKey(getMd5Hash(ConstentValues.ECOMMERCE_CONSUMER_KEY))
                    .consumerSecret(getMd5Hash(ConstentValues.ECOMMERCE_CONSUMER_SECRET))
                    .consumerIP(getLocalIpAddress())
                    .build();

            BasicOAuth basicOAuthWoocommerce = new BasicOAuth.Builder()
                    .consumerKey(ConstentValues.ECOMMERCE_CONSUMER_KEY)
                    .consumerSecret(ConstentValues.ECOMMERCE_CONSUMER_SECRET)
                    .consumerIP(getLocalIpAddress())
                    .build();

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    //.addInterceptor(apiInterceptor)
                    .addInterceptor(httpLoggingInterceptor)
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder()
                                    //.addHeader(Constant.Header, authToken)
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    //.addInterceptor(BASE_URL.startsWith("http://")?  apiInterceptor : basicOAuthWoocommerce)
                    // Above interceptor isnot working in https://
                    .addInterceptor(apiInterceptor)
                    .build();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            apiRequests = retrofit.create(Api.class);

            return apiRequests;
        }
        else {
            return apiRequests;
        }
    }



    public static String getMd5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);

            while (md5.length() < 32)
                md5 = "0" + md5;

            return md5;
        } catch (NoSuchAlgorithmException e) {
            Log.e("MD5", e.getLocalizedMessage());
            return null;
        }
    }

    public static final String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("GETLOCALIPADDRESS", ex.toString());
        }
        return null;
    }

}
