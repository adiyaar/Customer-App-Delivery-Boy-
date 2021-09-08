package com.themescoder.androidstore.models.order_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeliveryboyInfo implements Serializable {

    @SerializedName("orders_to_deliveryboy_id")
    @Expose
    private Integer ordersToDeliveryboyId;
    @SerializedName("deliveryboy_id")
    @Expose
    private Integer deliveryboyId;
    @SerializedName("orders_id")
    @Expose
    private Integer ordersId;
    @SerializedName("is_current")
    @Expose
    private Integer isCurrent;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
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
    private Integer voterId;
    @SerializedName("referrer_name")
    @Expose
    private String referrerName;
    @SerializedName("availability_status")
    @Expose
    private Integer availabilityStatus;
    private final static long serialVersionUID = -4991079580230300100L;

    public Integer getOrdersToDeliveryboyId() {
        return ordersToDeliveryboyId;
    }

    public void setOrdersToDeliveryboyId(Integer ordersToDeliveryboyId) {
        this.ordersToDeliveryboyId = ordersToDeliveryboyId;
    }

    public Integer getDeliveryboyId() {
        return deliveryboyId;
    }

    public void setDeliveryboyId(Integer deliveryboyId) {
        this.deliveryboyId = deliveryboyId;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public Integer getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Integer isCurrent) {
        this.isCurrent = isCurrent;
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

    public Integer getVoterId() {
        return voterId;
    }

    public void setVoterId(Integer voterId) {
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

}