package com.themescoder.driver.models;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WithDrawResponceData implements Serializable, Parcelable
{

@SerializedName("payment_withdraw_id")
@Expose
private Integer paymentWithdrawId;
@SerializedName("user_id")
@Expose
private Integer userId;
@SerializedName("amount")
@Expose
private Integer amount;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("method")
@Expose
private String method;
@SerializedName("note")
@Expose
private Object note;
@SerializedName("updated_at")
@Expose
private Object updatedAt;
public final static Parcelable.Creator<WithDrawResponceData> CREATOR = new Creator<WithDrawResponceData>() {


@SuppressWarnings({
"unchecked"
})
public WithDrawResponceData createFromParcel(Parcel in) {
return new WithDrawResponceData(in);
}

public WithDrawResponceData[] newArray(int size) {
return (new WithDrawResponceData[size]);
}

}
;
private final static long serialVersionUID = 4375661183594941784L;

protected WithDrawResponceData(Parcel in) {
this.paymentWithdrawId = ((Integer) in.readValue((Integer.class.getClassLoader())));
this.userId = ((Integer) in.readValue((Integer.class.getClassLoader())));
this.amount = ((Integer) in.readValue((Integer.class.getClassLoader())));
this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
this.method = ((String) in.readValue((String.class.getClassLoader())));
this.note = ((Object) in.readValue((Object.class.getClassLoader())));
this.updatedAt = ((Object) in.readValue((Object.class.getClassLoader())));
}

public WithDrawResponceData() {
}

public Integer getPaymentWithdrawId() {
return paymentWithdrawId;
}

public void setPaymentWithdrawId(Integer paymentWithdrawId) {
this.paymentWithdrawId = paymentWithdrawId;
}

public Integer getUserId() {
return userId;
}

public void setUserId(Integer userId) {
this.userId = userId;
}

public Integer getAmount() {
return amount;
}

public void setAmount(Integer amount) {
this.amount = amount;
}

public String getCreatedAt() {
return createdAt;
}

public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public String getMethod() {
return method;
}

public void setMethod(String method) {
this.method = method;
}

public Object getNote() {
return note;
}

public void setNote(Object note) {
this.note = note;
}

public Object getUpdatedAt() {
return updatedAt;
}

public void setUpdatedAt(Object updatedAt) {
this.updatedAt = updatedAt;
}

public void writeToParcel(Parcel dest, int flags) {
dest.writeValue(paymentWithdrawId);
dest.writeValue(userId);
dest.writeValue(amount);
dest.writeValue(createdAt);
dest.writeValue(status);
dest.writeValue(method);
dest.writeValue(note);
dest.writeValue(updatedAt);
}

public int describeContents() {
return 0;
}

}