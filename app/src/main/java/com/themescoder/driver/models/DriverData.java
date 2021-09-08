package com.themescoder.driver.models;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverData implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("user_name")
    @Expose
    private Object userName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("default_address_id")
    @Expose
    private Integer defaultAddressId;
    @SerializedName("country_code")
    @Expose
    private Object countryCode;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_seen")
    @Expose
    private String isSeen;
    @SerializedName("phone_verified")
    @Expose
    private Object phoneVerified;
    @SerializedName("remember_token")
    @Expose
    private Object rememberToken;
    @SerializedName("auth_id_tiwilo")
    @Expose
    private Object authIdTiwilo;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("deliveryboy_id")
    @Expose
    private Integer deliveryboyId;
    @SerializedName("users_id")
    @Expose
    private Integer usersId;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("bike_name")
    @Expose
    private String bikeName;
    @SerializedName("bike_details")
    @Expose
    private String bikeDetails;
    @SerializedName("bike_color")
    @Expose
    private String bikeColor;
    @SerializedName("owner_name")
    @Expose
    private String ownerName;
    @SerializedName("vehicle_registration_number")
    @Expose
    private String vehicleRegistrationNumber;
    @SerializedName("driving_license_image")
    @Expose
    private String drivingLicenseImage;
    @SerializedName("vehicle_rc_book_image")
    @Expose
    private String vehicleRcBookImage;
    @SerializedName("aadhaar_card_image")
    @Expose
    private String aadhaarCardImage;
    @SerializedName("bank_passbook_image")
    @Expose
    private String bankPassbookImage;
    @SerializedName("pan_card_image")
    @Expose
    private String panCardImage;
    @SerializedName("referrer_aadhaar_image")
    @Expose
    private String referrerAadhaarImage;
    @SerializedName("voter_id")
    @Expose
    private String voterId;
    @SerializedName("referrer_name")
    @Expose
    private String referrerName;
    @SerializedName("availability_status")
    @Expose
    private Integer availabilityStatus;
    @SerializedName("bank_detail_id")
    @Expose
    private Integer bankDetailId;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("bank_account_number")
    @Expose
    private String bankAccountNumber;
    @SerializedName("bank_routing_number")
    @Expose
    private String bankRoutingNumber;
    @SerializedName("bank_address")
    @Expose
    private String bankAddress;
    @SerializedName("bank_iban")
    @Expose
    private String bankIban;
    @SerializedName("bank_swift")
    @Expose
    private String bankSwift;
    @SerializedName("is_current")
    @Expose
    private Integer isCurrent;
    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("flosting_cash")
    @Expose
    private String flostingCash;


    public final static Parcelable.Creator<DriverData> CREATOR = new Creator<DriverData>() {

        @SuppressWarnings({
                "unchecked"
        })
        public DriverData createFromParcel(Parcel in) {
            return new DriverData(in);
        }

        public DriverData[] newArray(int size) {
            return (new DriverData[size]);
        }

    }
            ;
    private final static long serialVersionUID = 8010781414176716215L;

    protected DriverData(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.roleId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.userName = ((Object) in.readValue((Object.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((String) in.readValue((String.class.getClassLoader())));
        this.defaultAddressId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.countryCode = ((Object) in.readValue((Object.class.getClassLoader())));
        this.phone = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.avatar = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.isSeen = ((String) in.readValue((String.class.getClassLoader())));
        this.phoneVerified = ((Object) in.readValue((Object.class.getClassLoader())));
        this.rememberToken = ((Object) in.readValue((Object.class.getClassLoader())));
        this.authIdTiwilo = ((Object) in.readValue((Object.class.getClassLoader())));
        this.dob = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((Object) in.readValue((Object.class.getClassLoader())));
        this.deliveryboyId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.usersId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.bloodGroup = ((String) in.readValue((String.class.getClassLoader())));
        this.bikeName = ((String) in.readValue((String.class.getClassLoader())));
        this.bikeDetails = ((String) in.readValue((String.class.getClassLoader())));
        this.bikeColor = ((String) in.readValue((String.class.getClassLoader())));
        this.ownerName = ((String) in.readValue((String.class.getClassLoader())));
        this.vehicleRegistrationNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.drivingLicenseImage = ((String) in.readValue((String.class.getClassLoader())));
        this.vehicleRcBookImage = ((String) in.readValue((String.class.getClassLoader())));
        this.aadhaarCardImage = ((String) in.readValue((String.class.getClassLoader())));
        this.bankPassbookImage = ((String) in.readValue((String.class.getClassLoader())));
        this.panCardImage = ((String) in.readValue((String.class.getClassLoader())));
        this.referrerAadhaarImage = ((String) in.readValue((String.class.getClassLoader())));
        this.voterId = ((String) in.readValue((Integer.class.getClassLoader())));
        this.referrerName = ((String) in.readValue((String.class.getClassLoader())));
        this.availabilityStatus = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.bankDetailId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.bankName = ((String) in.readValue((String.class.getClassLoader())));
        this.bankAccountNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.bankRoutingNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.bankAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.bankIban = ((String) in.readValue((String.class.getClassLoader())));
        this.bankSwift = ((String) in.readValue((String.class.getClassLoader())));
        this.isCurrent = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.balance = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.flostingCash= ((String) in.readValue((String.class.getClassLoader())));
    }

    public DriverData() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getDefaultAddressId() {
        return defaultAddressId;
    }

    public void setDefaultAddressId(Integer defaultAddressId) {
        this.defaultAddressId = defaultAddressId;
    }

    public Object getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Object countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(String isSeen) {
        this.isSeen = isSeen;
    }

    public Object getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(Object phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public Object getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(Object rememberToken) {
        this.rememberToken = rememberToken;
    }

    public Object getAuthIdTiwilo() {
        return authIdTiwilo;
    }

    public void setAuthIdTiwilo(Object authIdTiwilo) {
        this.authIdTiwilo = authIdTiwilo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getDeliveryboyId() {
        return deliveryboyId;
    }

    public void setDeliveryboyId(Integer deliveryboyId) {
        this.deliveryboyId = deliveryboyId;
    }

    public Integer getUsersId() {
        return usersId;
    }

    public void setUsersId(Integer usersId) {
        this.usersId = usersId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getBikeName() {
        return bikeName;
    }

    public void setBikeName(String bikeName) {
        this.bikeName = bikeName;
    }

    public String getBikeDetails() {
        return bikeDetails;
    }

    public void setBikeDetails(String bikeDetails) {
        this.bikeDetails = bikeDetails;
    }

    public String getBikeColor() {
        return bikeColor;
    }

    public void setBikeColor(String bikeColor) {
        this.bikeColor = bikeColor;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    public String getDrivingLicenseImage() {
        return drivingLicenseImage;
    }

    public void setDrivingLicenseImage(String drivingLicenseImage) {
        this.drivingLicenseImage = drivingLicenseImage;
    }

    public String getVehicleRcBookImage() {
        return vehicleRcBookImage;
    }

    public void setVehicleRcBookImage(String vehicleRcBookImage) {
        this.vehicleRcBookImage = vehicleRcBookImage;
    }

    public String getAadhaarCardImage() {
        return aadhaarCardImage;
    }

    public void setAadhaarCardImage(String aadhaarCardImage) {
        this.aadhaarCardImage = aadhaarCardImage;
    }

    public String getBankPassbookImage() {
        return bankPassbookImage;
    }

    public void setBankPassbookImage(String bankPassbookImage) {
        this.bankPassbookImage = bankPassbookImage;
    }

    public String getPanCardImage() {
        return panCardImage;
    }

    public void setPanCardImage(String panCardImage) {
        this.panCardImage = panCardImage;
    }

    public String getReferrerAadhaarImage() {
        return referrerAadhaarImage;
    }

    public void setReferrerAadhaarImage(String referrerAadhaarImage) {
        this.referrerAadhaarImage = referrerAadhaarImage;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public Integer getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(Integer availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public Integer getBankDetailId() {
        return bankDetailId;
    }

    public void setBankDetailId(Integer bankDetailId) {
        this.bankDetailId = bankDetailId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankRoutingNumber() {
        return bankRoutingNumber;
    }

    public void setBankRoutingNumber(String bankRoutingNumber) {
        this.bankRoutingNumber = bankRoutingNumber;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankIban() {
        return bankIban;
    }

    public void setBankIban(String bankIban) {
        this.bankIban = bankIban;
    }

    public String getBankSwift() {
        return bankSwift;
    }

    public void setBankSwift(String bankSwift) {
        this.bankSwift = bankSwift;
    }

    public Integer getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Integer isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getFlostingCash() {
        return flostingCash;
    }

    public void setFlostingCash(String flostingCash) {
        this.flostingCash = flostingCash;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(roleId);
        dest.writeValue(userName);
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(gender);
        dest.writeValue(defaultAddressId);
        dest.writeValue(countryCode);
        dest.writeValue(phone);
        dest.writeValue(email);
        dest.writeValue(password);
        dest.writeValue(avatar);
        dest.writeValue(status);
        dest.writeValue(isSeen);
        dest.writeValue(phoneVerified);
        dest.writeValue(rememberToken);
        dest.writeValue(authIdTiwilo);
        dest.writeValue(dob);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(deliveryboyId);
        dest.writeValue(usersId);
        dest.writeValue(bloodGroup);
        dest.writeValue(bikeName);
        dest.writeValue(bikeDetails);
        dest.writeValue(bikeColor);
        dest.writeValue(ownerName);
        dest.writeValue(vehicleRegistrationNumber);
        dest.writeValue(drivingLicenseImage);
        dest.writeValue(vehicleRcBookImage);
        dest.writeValue(aadhaarCardImage);
        dest.writeValue(bankPassbookImage);
        dest.writeValue(panCardImage);
        dest.writeValue(referrerAadhaarImage);
        dest.writeValue(voterId);
        dest.writeValue(referrerName);
        dest.writeValue(availabilityStatus);
        dest.writeValue(bankDetailId);
        dest.writeValue(bankName);
        dest.writeValue(bankAccountNumber);
        dest.writeValue(bankRoutingNumber);
        dest.writeValue(bankAddress);
        dest.writeValue(bankIban);
        dest.writeValue(bankSwift);
        dest.writeValue(isCurrent);
        dest.writeValue(balance);
        dest.writeValue(flostingCash);
    }

    public int describeContents() {
        return 0;
    }

}