package com.themescoder.androidstore.models.device_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AppSettingsDetails {
    
    @SerializedName("app_name")
    @Expose
    private String appName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("email_login")
    @Expose
    private String emailLogin;
    @SerializedName("phone_login")
    @Expose
    private String phoneLogin;
    @SerializedName("phone_verificatio_type")
    @Expose
    private String phoneVerificatioType;

    @SerializedName("contact_us_email")
    @Expose
    private String contactUsEmail;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("new_product_duration")
    @Expose
    private String newProductDuration;
    @SerializedName("external_website_link")
    @Expose
    private String siteUrl;
    @SerializedName("notification_title")
    @Expose
    private String notificationTitle;
    @SerializedName("notification_text")
    @Expose
    private String notificationText;
    @SerializedName("notification_duration")
    @Expose
    private String notificationDuration;
    @SerializedName("featured_category")
    @Expose
    private String featuredCategory;
    @SerializedName("cart_button")
    @Expose
    private String cartButton;
    @SerializedName("home_style")
    @Expose
    private String homeStyle;
    @SerializedName("category_style")
    @Expose
    private String categoryStyle;
    @SerializedName("card_style")
    @Expose
    private String cardStyle;
    @SerializedName("banner_style")
    @Expose
    private String bannerStyle;
    @SerializedName("wish_list_page")
    @Expose
    private String wishListPage;
    @SerializedName("edit_profile_page")
    @Expose
    private String editProfilePage;
    @SerializedName("shipping_address_page")
    @Expose
    private String shippingAddressPage;
    @SerializedName("my_orders_page")
    @Expose
    private String myOrdersPage;
    @SerializedName("contact_us_page")
    @Expose
    private String contactUsPage;
    @SerializedName("about_us_page")
    @Expose
    private String aboutUsPage;
    @SerializedName("news_page")
    @Expose
    private String newsPage;
    @SerializedName("intro_page")
    @Expose
    private String introPage;
    @SerializedName("setting_page")
    @Expose
    private String settingPage;
    @SerializedName("share_app")
    @Expose
    private String shareApp;
    @SerializedName("rate_app")
    @Expose
    private String rateApp;
    @SerializedName("default_notification")
    @Expose
    private String defaultNotification;
    @SerializedName("fcm_android")
    @Expose
    private String fcmAndroid;
    @SerializedName("google_login")
    @Expose
    private String googleLogin;
    @SerializedName("facebook_login")
    @Expose
    private String facebookLogin;
    @SerializedName("admob")
    @Expose
    private String admob;
    @SerializedName("admob_id")
    @Expose
    private String admobId;
    @SerializedName("ad_unit_id_banner")
    @Expose
    private String adUnitIdBanner;
    @SerializedName("ad_unit_id_interstitial")
    @Expose
    private String adUnitIdInterstitial;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("facebook_url")
    @Expose
    private String facebookUrl;
    @SerializedName("google_url")
    @Expose
    private String googleUrl;
    @SerializedName("twitter_url")
    @Expose
    private String twitterUrl;
    @SerializedName("linked_in")
    @Expose
    private String linkedIn;
    @SerializedName("is_app_purchased")
    @Expose
    private String isAppPurchased;
    @SerializedName("is_desktop_purchased")
    @Expose
    private String isDesktopPurchased;
    @SerializedName("consumer_key")
    @Expose
    private String consumerKey;
    @SerializedName("consumer_secret")
    @Expose
    private String consumerSecret;
    @SerializedName("order_email")
    @Expose
    private String orderEmail;
    @SerializedName("package_name")
    @Expose
    private String packageName;
    @SerializedName("website_logo")
    @Expose
    private String websiteLogo;
    @SerializedName("website_themes")
    @Expose
    private String websiteThemes;
    @SerializedName("themes")
    @Expose
    private String themes;

    @SerializedName("app_version")
    @Expose
    private String appVersion;

    @SerializedName("youtube_link")
    @Expose
    private String youtube;
    @SerializedName("maintenance_text")
    @Expose
    private String maintenance_text;
    @SerializedName("environment")
    @Expose
    private String app_web_environment;
    @SerializedName("environmentt")
    @Expose
    private String app_web_environmentt;
    @SerializedName("package_charge_taxt")
    @Expose
    private String packing_charge_tax;
    @SerializedName("Inventory")
    @Expose
    private String inventory;

    public String getPacking_charge_tax() {
        return packing_charge_tax;
    }
    
    public void setPacking_charge_tax(String packing_charge_tax) {
        this.packing_charge_tax = packing_charge_tax;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }


    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppName() {
        return appName;
    }
    
    public void setAppName(String appName) {
        this.appName = appName;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getZip() {
        return zip;
    }
    
    public void setZip(String zip) {
        this.zip = zip;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getLatitude() {
        return latitude;
    }
    
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    public String getLongitude() {
        return longitude;
    }
    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    public String getPhoneNo() {
        return phoneNo;
    }
    
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailLogin() {
        return emailLogin;
    }

    public void setEmailLogin(String emailLogin) {
        this.emailLogin = emailLogin;
    }

    public String getPhoneLogin() {
        return phoneLogin;
    }

    public void setPhoneLogin(String phoneLogin) {
        this.phoneLogin = phoneLogin;
    }

    public String getPhoneVerificatioType() {
        return phoneVerificatioType;
    }

    public void setPhoneVerificatioType(String phoneVerificatioType) {
        this.phoneVerificatioType = phoneVerificatioType;
    }

    public String getContactUsEmail() {
        return contactUsEmail;
    }
    
    public void setContactUsEmail(String contactUsEmail) {
        this.contactUsEmail = contactUsEmail;
    }
    
    public String getCurrencySymbol() {
        return currencySymbol;
    }
    
    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }
    
    public String getNewProductDuration() {
        return newProductDuration;
    }
    
    public void setNewProductDuration(String newProductDuration) {
        this.newProductDuration = newProductDuration;
    }
    
    public String getSiteUrl() {
        return siteUrl;
    }
    
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }
    
    public String getNotificationTitle() {
        return notificationTitle;
    }
    
    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }
    
    public String getNotificationText() {
        return notificationText;
    }
    
    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }
    
    public String getNotificationDuration() {
        return notificationDuration;
    }
    
    public void setNotificationDuration(String notificationDuration) {
        this.notificationDuration = notificationDuration;
    }
    
    public String getFeaturedCategory() {
        return featuredCategory;
    }
    
    public void setFeaturedCategory(String featuredCategory) {
        this.featuredCategory = featuredCategory;
    }
    
    public String getCartButton() {
        return cartButton;
    }
    
    public void setCartButton(String cartButton) {
        this.cartButton = cartButton;
    }
    
    public String getHomeStyle() {
        return homeStyle;
    }
    
    public void setHomeStyle(String homeStyle) {
        this.homeStyle = homeStyle;
    }
    
    public String getCategoryStyle() {
        return categoryStyle;
    }
    
    public void setCategoryStyle(String categoryStyle) {
        this.categoryStyle = categoryStyle;
    }

    public String getCardStyle() {
        return cardStyle;
    }

    public void setCardStyle(String cardStyle) {
        this.cardStyle = cardStyle;
    }

    public String getBannerStyle() {
        return bannerStyle;
    }

    public void setBannerStyle(String bannerStyle) {
        this.bannerStyle = bannerStyle;
    }

    public String getWishListPage() {
        return wishListPage;
    }
    
    public void setWishListPage(String wishListPage) {
        this.wishListPage = wishListPage;
    }
    
    public String getEditProfilePage() {
        return editProfilePage;
    }
    
    public void setEditProfilePage(String editProfilePage) {
        this.editProfilePage = editProfilePage;
    }
    
    public String getShippingAddressPage() {
        return shippingAddressPage;
    }
    
    public void setShippingAddressPage(String shippingAddressPage) {
        this.shippingAddressPage = shippingAddressPage;
    }
    
    public String getMyOrdersPage() {
        return myOrdersPage;
    }
    
    public void setMyOrdersPage(String myOrdersPage) {
        this.myOrdersPage = myOrdersPage;
    }
    
    public String getContactUsPage() {
        return contactUsPage;
    }
    
    public void setContactUsPage(String contactUsPage) {
        this.contactUsPage = contactUsPage;
    }
    
    public String getAboutUsPage() {
        return aboutUsPage;
    }
    
    public void setAboutUsPage(String aboutUsPage) {
        this.aboutUsPage = aboutUsPage;
    }
    
    public String getNewsPage() {
        return newsPage;
    }
    
    public void setNewsPage(String newsPage) {
        this.newsPage = newsPage;
    }
    
    public String getIntroPage() {
        return introPage;
    }
    
    public void setIntroPage(String introPage) {
        this.introPage = introPage;
    }
    
    public String getSettingPage() {
        return settingPage;
    }
    
    public void setSettingPage(String settingPage) {
        this.settingPage = settingPage;
    }
    
    public String getShareApp() {
        return shareApp;
    }
    
    public void setShareApp(String shareApp) {
        this.shareApp = shareApp;
    }
    
    public String getRateApp() {
        return rateApp;
    }
    
    public void setRateApp(String rateApp) {
        this.rateApp = rateApp;
    }
    
    public String getDefaultNotification() {
        return defaultNotification;
    }
    
    public void setDefaultNotification(String defaultNotification) {
        this.defaultNotification = defaultNotification;
    }
    
    public String getFcmAndroid() {
        return fcmAndroid;
    }
    
    public void setFcmAndroid(String fcmAndroid) {
        this.fcmAndroid = fcmAndroid;
    }
    
    public String getGoogleLogin() {
        return googleLogin;
    }
    
    public void setGoogleLogin(String googleLogin) {
        this.googleLogin = googleLogin;
    }
    
    public String getFacebookLogin() {
        return facebookLogin;
    }
    
    public void setFacebookLogin(String facebookLogin) {
        this.facebookLogin = facebookLogin;
    }
    
    public String getAdmob() {
        return admob;
    }
    
    public void setAdmob(String admob) {
        this.admob = admob;
    }
    
    public String getAdmobId() {
        return admobId;
    }
    
    public void setAdmobId(String admobId) {
        this.admobId = admobId;
    }
    
    public String getAdUnitIdBanner() {
        return adUnitIdBanner;
    }
    
    public void setAdUnitIdBanner(String adUnitIdBanner) {
        this.adUnitIdBanner = adUnitIdBanner;
    }
    
    public String getAdUnitIdInterstitial() {
        return adUnitIdInterstitial;
    }
    
    public void setAdUnitIdInterstitial(String adUnitIdInterstitial) {
        this.adUnitIdInterstitial = adUnitIdInterstitial;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getFacebookUrl() {
        return facebookUrl;
    }
    
    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }
    
    public String getGoogleUrl() {
        return googleUrl;
    }
    
    public void setGoogleUrl(String googleUrl) {
        this.googleUrl = googleUrl;
    }
    
    public String getTwitterUrl() {
        return twitterUrl;
    }
    
    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }
    
    public String getLinkedIn() {
        return linkedIn;
    }
    
    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }
    
    public String getIsAppPurchased() {
        return isAppPurchased;
    }
    
    public void setIsAppPurchased(String isAppPurchased) {
        this.isAppPurchased = isAppPurchased;
    }
    
    public String getIsDesktopPurchased() {
        return isDesktopPurchased;
    }
    
    public void setIsDesktopPurchased(String isDesktopPurchased) {
        this.isDesktopPurchased = isDesktopPurchased;
    }
    
    public String getConsumerKey() {
        return consumerKey;
    }
    
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }
    
    public String getConsumerSecret() {
        return consumerSecret;
    }
    
    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }
    
    public String getOrderEmail() {
        return orderEmail;
    }
    
    public void setOrderEmail(String orderEmail) {
        this.orderEmail = orderEmail;
    }
    
    public String getPackageName() {
        return packageName;
    }
    
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    
    public String getWebsiteLogo() {
        return websiteLogo;
    }
    
    public void setWebsiteLogo(String websiteLogo) {
        this.websiteLogo = websiteLogo;
    }
    
    public String getWebsiteThemes() {
        return websiteThemes;
    }
    
    public void setWebsiteThemes(String websiteThemes) {
        this.websiteThemes = websiteThemes;
    }
    
    public String getThemes() {
        return themes;
    }
    
    public void setThemes(String themes) {
        this.themes = themes;
    }

    public String getMaintenance_text() {
        return maintenance_text;
    }

    public void setMaintenance_text(String maintenance_text) {
        this.maintenance_text = maintenance_text;
    }

    public String getApp_web_environment() {
        return app_web_environment;
    }

    public void setApp_web_environment(String app_web_environment) {
        this.app_web_environment = app_web_environment;
    }

    public String getApp_web_environmentt() {
        return app_web_environmentt;
    }

    public void setApp_web_environmentt(String app_web_environmentt) {
        this.app_web_environmentt = app_web_environmentt;
    }
}
