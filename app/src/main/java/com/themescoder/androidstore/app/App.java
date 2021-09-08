package com.themescoder.androidstore.app;


import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import androidx.multidex.MultiDexApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.io.BaseEncoding;
import com.onesignal.OneSignal;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.databases.DB_Handler;
import com.themescoder.androidstore.databases.DB_Manager;
import com.themescoder.androidstore.enums.AppEnvironment;
import com.themescoder.androidstore.models.address_model.AddressDetails;
import com.themescoder.androidstore.models.banner_model.BannerDetails;
import com.themescoder.androidstore.models.category_model.CategoryDetails;
import com.themescoder.androidstore.models.device_model.AppSettingsDetails;
import com.themescoder.androidstore.models.drawer_model.Drawer_Items;
import com.themescoder.androidstore.models.pages_model.PagesDetails;
import com.themescoder.androidstore.models.product_model.ProductDetails;
import com.themescoder.androidstore.models.shipping_model.ShippingService;


/**
 * App extending Application, is used to save some Lists and Objects with Application Context.
 **/


public class App extends MultiDexApplication {

    // Application Context
    private static Context context;
    private static DB_Handler db_handler;


    private List<Drawer_Items> drawerHeaderList;
    private Map<Drawer_Items, List<Drawer_Items>> drawerChildList;
    
    
    private AppSettingsDetails appSettingsDetails = null;
    private List<BannerDetails> bannersList = new ArrayList<>();
    private List<CategoryDetails> categoriesList = new ArrayList<>();
    private List<PagesDetails> staticPagesDetails = new ArrayList<>();

    private String tax = "";
    private ShippingService shippingService = null;
    private AddressDetails shippingAddress = new AddressDetails();
    private AddressDetails billingAddress = new AddressDetails();
    private ProductDetails productDetails = new ProductDetails();

    AppEnvironment appEnvironment;


    @Override
    public void onCreate() {
        super.onCreate();

        // set App Context
        context = this.getApplicationContext();

        appEnvironment = AppEnvironment.SANDBOX;



        // initialize DB_Handler and DB_Manager
        db_handler = new DB_Handler();
        DB_Manager.initializeInstance(db_handler);
        String pkg_name = context.getPackageName();
        ConstantValues.PKG_NAME = pkg_name;
        ConstantValues.SHA1 = getSHA1(pkg_name);
    
        if (ConstantValues.DEFAULT_NOTIFICATION.equalsIgnoreCase("onesignal")) {
            
            OneSignal.sendTag("app", "AndroidEcommerceDemo2");
        
            // initialize OneSignal
            OneSignal.startInit(this)
                    .filterOtherGCMReceivers(true)
                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.InAppAlert)
                    .unsubscribeWhenNotificationsAreDisabled(false)
                    .init();
        
        }

    }



    //*********** Returns Application Context ********//

    public static Context getContext() {
        return context;
    }


    public AppEnvironment getAppEnvironment() {
        return appEnvironment;
    }

    public void setAppEnvironment(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }

    public List<Drawer_Items> getDrawerHeaderList() {
        return drawerHeaderList;
    }

    public void setDrawerHeaderList(List<Drawer_Items> drawerHeaderList) {
        this.drawerHeaderList = drawerHeaderList;
    }

    public Map<Drawer_Items, List<Drawer_Items>> getDrawerChildList() {
        return drawerChildList;
    }

    public void setDrawerChildList(Map<Drawer_Items, List<Drawer_Items>> drawerChildList) {
        this.drawerChildList = drawerChildList;
    }

    public AppSettingsDetails getAppSettingsDetails() {
        return appSettingsDetails;
    }
    
    public void setAppSettingsDetails(AppSettingsDetails appSettingsDetails) {
        this.appSettingsDetails = appSettingsDetails;
    }
    
    public List<BannerDetails> getBannersList() {
        return bannersList;
    }
    
    public void setBannersList(List<BannerDetails> bannersList) {
        this.bannersList = bannersList;
    }
    
    public List<CategoryDetails> getCategoriesList() {
        return categoriesList;
    }
    
    public void setCategoriesList(List<CategoryDetails> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public List<PagesDetails> getStaticPagesDetails() {
        return staticPagesDetails;
    }
    
    public void setStaticPagesDetails(List<PagesDetails> staticPagesDetails) {
        this.staticPagesDetails = staticPagesDetails;
    }


    public String getTax() {
        return tax;
    }
    
    public void setTax(String tax) {
        this.tax = tax;
    }
    
    public ShippingService getShippingService() {
        return shippingService;
    }
    
    public void setShippingService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }
    
    
    public AddressDetails getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressDetails shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public AddressDetails getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressDetails billingAddress) {
        this.billingAddress = billingAddress;
    }
    
    
    private String getSHA1(String packageName){
        try {
            Signature[] signatures = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures;
            for (Signature signature: signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA-1");
                md.update(signature.toByteArray());
                return BaseEncoding.base16().encode(md.digest());
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }
}


