
package com.themescoder.androidstore.models.payment_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PaymentMethodsInfo {
    
    @SerializedName("environment")
    @Expose
    private String environment;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("public_key")
    @Expose
    private String publicKey;
    @SerializedName("active")
    @Expose
    private String active;
    /*
        @SerializedName("payment_currency")
        @Expose
        private String paymentCurrency;
    */
    @SerializedName("client_id")
    @Expose
    private String client_id;
    @SerializedName("client_secret")
    @Expose
    private String client_secret;
    
    
    public String getEnvironment() {
        return environment;
    }
    
    public void setEnvironment(String environment) {
        this.environment = environment;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
    
    /*public String getPaymentCurrency() {
        return paymentCurrency;
    }
    
    public void setPaymentCurrency(String paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }*/
    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

}

