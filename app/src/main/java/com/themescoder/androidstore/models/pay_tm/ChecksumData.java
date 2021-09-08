package com.themescoder.androidstore.models.pay_tm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChecksumData {

@SerializedName("CHECKSUMHASH")
@Expose
private String cHECKSUMHASH;
@SerializedName("payt_STATUS")
@Expose
private String paytSTATUS;

public String getCHECKSUMHASH() {
return cHECKSUMHASH;
}

public void setCHECKSUMHASH(String cHECKSUMHASH) {
this.cHECKSUMHASH = cHECKSUMHASH;
}

public String getPaytSTATUS() {
return paytSTATUS;
}

public void setPaytSTATUS(String paytSTATUS) {
this.paytSTATUS = paytSTATUS;
}

}