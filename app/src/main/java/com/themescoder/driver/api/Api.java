package com.themescoder.driver.api;

import com.themescoder.driver.models.DriverDetails;
import com.themescoder.driver.models.OrdersResponse;
import com.themescoder.driver.models.PagesResponse;
import com.themescoder.driver.models.QrCodesResponse;
import com.themescoder.driver.models.RegisterDeviceResponce;
import com.themescoder.driver.models.SettingsResponse;
import com.themescoder.driver.models.StatusResponse;
import com.themescoder.driver.models.WithDrawAmountResponce;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("setting")
    Call<SettingsResponse> getSettings();

    @GET("login")
    Call<DriverDetails> getUser(
            @Query("password") String pinCode,
            @Query("device_id") String deviceID
    );

    @GET("changestatus")
    Call<StatusResponse> changeStatus(
            @Query("availability_status") String status,
            @Query("password") String pincode
    );

    @GET("qrcode")
    Call<QrCodesResponse> getQrCodes(
            @Query("language_id") String languageid
    );

    @GET("pages")
    Call<PagesResponse> getPages(
            @Query("language_id") String languageid
    );

    @GET("orders")
    Call<OrdersResponse> gatDeliveryOrders(
            @Query("language_id") String languageid,
            @Query("password") String pincode
    );

    @GET("changeorderstatus")
    Call<StatusResponse> changeOrderStatus(
            @Query("orders_id") String orderid,
            @Query("orders_status_id") String orderstatusid,
            @Query("comments") String comments,
            @Query("password") String deliveryboypincode
    );

    @FormUrlEncoded
    @POST("withdrawrequest")
    Call<WithDrawAmountResponce> withdrawAmount(
            @Field("password") String deliveryboypincode,
            @Field("amount") String amount,
            @Field("note") String note
    );

    @GET("deliveryboyinfo")
    Call<DriverDetails> updateUserInfo(
            @Query("password") String pinCode
    );

    @FormUrlEncoded
    @POST("registerdevices")
    Call<RegisterDeviceResponce> registerDevice(@Field("device_id") String device_id,
                                                @Field("device_type") String device_type,
                                                @Field("ram") String ram,
                                                @Field("processor") String processor,
                                                @Field("device_os") String device_os,
                                                @Field("location") String location,
                                                @Field("device_model") String device_model,
                                                @Field("manufacturer") String manufacturer,
                                                @Field("password") String user_pin);
}
