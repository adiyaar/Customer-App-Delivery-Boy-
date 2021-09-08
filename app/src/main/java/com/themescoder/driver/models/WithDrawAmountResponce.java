package com.themescoder.driver.models;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WithDrawAmountResponce implements Serializable, Parcelable
{

@SerializedName("success")
@Expose
private String success;
@SerializedName("data")
@Expose
private List<WithDrawResponceData> data = null;
@SerializedName("message")
@Expose
private String message;
public final static Parcelable.Creator<WithDrawAmountResponce> CREATOR = new Creator<WithDrawAmountResponce>() {


@SuppressWarnings({
"unchecked"
})
public WithDrawAmountResponce createFromParcel(Parcel in) {
return new WithDrawAmountResponce(in);
}

public WithDrawAmountResponce[] newArray(int size) {
return (new WithDrawAmountResponce[size]);
}

}
;
private final static long serialVersionUID = 1953758147203465259L;

protected WithDrawAmountResponce(Parcel in) {
this.success = ((String) in.readValue((String.class.getClassLoader())));
in.readList(this.data, (WithDrawResponceData.class.getClassLoader()));
this.message = ((String) in.readValue((String.class.getClassLoader())));
}

public WithDrawAmountResponce() {
}

public String getSuccess() {
return success;
}

public void setSuccess(String success) {
this.success = success;
}

public List<WithDrawResponceData> getData() {
return data;
}

public void setData(List<WithDrawResponceData> data) {
this.data = data;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public void writeToParcel(Parcel dest, int flags) {
dest.writeValue(success);
dest.writeList(data);
dest.writeValue(message);
}

public int describeContents() {
return 0;
}

}