package com.themescoder.androidstore.models.shipping_model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingPrice {

@SerializedName("success")
@Expose
private String success;
@SerializedName("message")
@Expose
private String message;
@SerializedName("name")
@Expose
private String name;
@SerializedName("services")
@Expose
private List<ShippingService> services = null;

public String getSuccess() {
return success;
}

public void setSuccess(String success) {
this.success = success;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public List<ShippingService> getServices() {
return services;
}

public void setServices(List<ShippingService> services) {
this.services = services;
}

}