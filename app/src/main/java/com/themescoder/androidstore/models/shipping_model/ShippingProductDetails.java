package com.themescoder.androidstore.models.shipping_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.themescoder.androidstore.models.product_model.Attribute;
import com.themescoder.androidstore.models.product_model.Image;
import com.themescoder.androidstore.models.product_model.ProductCategories;
import com.themescoder.androidstore.models.ratings.RatingDataList;

import java.util.ArrayList;
import java.util.List;

public class ShippingProductDetails implements Parcelable {

    @SerializedName("server_time")
    @Expose
    private String serverTime;
    @SerializedName("products_id")
    @Expose
    private int productsId;
    @SerializedName("products_quantity")
    @Expose
    private int productsQuantity;
    @SerializedName("products_model")
    @Expose
    private String productsModel;
    @SerializedName("products_image")
    @Expose
    private String productsImage;
    @SerializedName("price")
    @Expose
    private double productsPrice;
    @SerializedName("discount_price")
    @Expose
    private String discountPrice;
    @SerializedName("products_date_added")
    @Expose
    private String productsDateAdded;
    @SerializedName("products_last_modified")
    @Expose
    private String productsLastModified;
    @SerializedName("products_date_available")
    @Expose
    private String productsDateAvailable;
    @SerializedName("weight")
    @Expose
    private String productsWeight;
    @SerializedName("unit")
    @Expose
    private String productsWeightUnit;
    @SerializedName("products_status")
    @Expose
    private int productsStatus;
    @SerializedName("products_ordered")
    @Expose
    private int productsOrdered;
    @SerializedName("products_liked")
    @Expose
    private int productsLiked;
    @SerializedName("language_id")
    @Expose
    private int languageId;
    @SerializedName("products_name")
    @Expose
    private String productsName;
    @SerializedName("products_description")
    @Expose
    private String productsDescription;
    @SerializedName("products_url")
    @Expose
    private String productsUrl;
    @SerializedName("defaultStock")
    @Expose
    private int productsDefaultStock;
    @SerializedName("products_viewed")
    @Expose
    private int productsViewed;
    @SerializedName("products_type")
    @Expose
    private int productsType;
    @SerializedName("products_tax_class_id")
    @Expose
    private int productsTaxClassId;
    @SerializedName("tax_rates_id")
    @Expose
    private int taxRatesId;
    @SerializedName("tax_zone_id")
    @Expose
    private int taxZoneId;
    @SerializedName("tax_class_id")
    @Expose
    private int taxClassId;
    @SerializedName("tax_priority")
    @Expose
    private int taxPriority;
    @SerializedName("tax_rate")
    @Expose
    private String taxRate;
    @SerializedName("tax_description")
    @Expose
    private String taxDescription;
    @SerializedName("tax_class_title")
    @Expose
    private String taxClassTitle;
    @SerializedName("tax_class_description")
    @Expose
    private String taxClassDescription;
    @SerializedName("sort_order")
    @Expose
    private int sortOrder;
    @SerializedName("isLiked")
    @Expose
    private String isLiked;
    @SerializedName("manufacturers_id")
    @Expose
    private int manufacturersId;
    @SerializedName("manufacturers_name")
    @Expose
    private String manufacturersName;
    @SerializedName("manufacturers_image")
    @Expose
    private String manufacturersImage;
    @SerializedName("manufacturers_url")
    @Expose
    private String manufacturersUrl;
    @SerializedName("flash_start_date")
    @Expose
    private String flashStartDate;
    @SerializedName("flash_expires_date")
    @Expose
    private String flashExpireDate;
    @SerializedName("flash_price")
    @Expose
    private String flashPrice;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("last_modified")
    @Expose
    private String lastModified;

    @SerializedName("isSale_product")
    @Expose
    private String isSaleProduct;

    @SerializedName("attributes_price")
    @Expose
    private String attributesPrice;
    @SerializedName("final_price")
    @Expose
    private double productsFinalPrice;
    @SerializedName("total")
    @Expose
    private double totalPrice;
    @SerializedName("customers_basket_quantity")
    @Expose
    private int customersBasketQuantity;

    @SerializedName("category_ids")
    @Expose
    private String categoryIDs;
    @SerializedName("category_names")
    @Expose
    private String categoryNames;

    @SerializedName("images")
    @Expose
    private List<Image> images = new ArrayList<Image>();
    @SerializedName("categories")
    @Expose
    private List<ProductCategories> categories = new ArrayList<ProductCategories>();
    @SerializedName("attributes")
    @Expose
    private List<Attribute> attributes = new ArrayList<Attribute>();
    @SerializedName("reviewed_customers")
    @Expose
    private List<RatingDataList> ratingDataLists = new ArrayList<>();
    @SerializedName("rating")
    @Expose
    private float rating;
    @SerializedName("total_user_rated")
    @Expose
    private int total_user_rated;
    @SerializedName("five_ratio")
    @Expose
    private int five_ratio;
    @SerializedName("four_ratio")
    @Expose
    private int four_ratio;
    @SerializedName("three_ratio")
    @Expose
    private int three_ratio;
    @SerializedName("two_ratio")
    @Expose
    private int two_ratio;
    @SerializedName("one_ratio")
    @Expose
    private int one_ratio;
    @SerializedName("vendors_id")
    @Expose
    private int vendors_id;

    public int getVendors_id() {
        return vendors_id;
    }

    public void setVendors_id(int vendors_id) {
        this.vendors_id = vendors_id;
    }

    public int getFive_ratio() {
        return five_ratio;
    }

    public void setFive_ratio(int five_ratio) {
        this.five_ratio = five_ratio;
    }

    public int getFour_ratio() {
        return four_ratio;
    }

    public void setFour_ratio(int four_ratio) {
        this.four_ratio = four_ratio;
    }

    public int getThree_ratio() {
        return three_ratio;
    }

    public void setThree_ratio(int three_ratio) {
        this.three_ratio = three_ratio;
    }

    public int getTwo_ratio() {
        return two_ratio;
    }

    public void setTwo_ratio(int two_ratio) {
        this.two_ratio = two_ratio;
    }

    public int getOne_ratio() {
        return one_ratio;
    }

    public void setOne_ratio(int one_ratio) {
        this.one_ratio = one_ratio;
    }

    public List<RatingDataList> getRatingDataLists() {
        return ratingDataLists;
    }

    public void setRatingDataLists(List<RatingDataList> ratingDataLists) {
        this.ratingDataLists = ratingDataLists;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getTotal_user_rated() {
        return total_user_rated;
    }

    public void setTotal_user_rated(int total_user_rated) {
        this.total_user_rated = total_user_rated;
    }




    public ShippingProductDetails() {
    }


    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public int getProductsId() {
        return productsId;
    }

    public void setProductsId(int productsId) {
        this.productsId = productsId;
    }

    public int getProductsQuantity() {
        return productsQuantity;
    }

    public void setProductsQuantity(int productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    public String getProductsModel() {
        return productsModel;
    }

    public void setProductsModel(String productsModel) {
        this.productsModel = productsModel;
    }

    public String getProductsImage() {
        return productsImage;
    }

    public void setProductsImage(String productsImage) {
        this.productsImage = productsImage;
    }

    public double getProductsPrice() {
        return productsPrice;
    }

    public void setProductsPrice(double productsPrice) {
        this.productsPrice = productsPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getProductsDateAdded() {
        return productsDateAdded;
    }

    public void setProductsDateAdded(String productsDateAdded) {
        this.productsDateAdded = productsDateAdded;
    }

    public String getProductsLastModified() {
        return productsLastModified;
    }

    public void setProductsLastModified(String productsLastModified) {
        this.productsLastModified = productsLastModified;
    }

    public String getProductsDateAvailable() {
        return productsDateAvailable;
    }

    public void setProductsDateAvailable(String productsDateAvailable) {
        this.productsDateAvailable = productsDateAvailable;
    }

    public String getProductsWeight() {
        return productsWeight;
    }

    public void setProductsWeight(String productsWeight) {
        this.productsWeight = productsWeight;
    }

    public String getProductsWeightUnit() {
        return productsWeightUnit;
    }

    public void setProductsWeightUnit(String productsWeightUnit) {
        this.productsWeightUnit = productsWeightUnit;
    }

    public int getProductsStatus() {
        return productsStatus;
    }

    public void setProductsStatus(int productsStatus) {
        this.productsStatus = productsStatus;
    }

    public int getProductsOrdered() {
        return productsOrdered;
    }

    public void setProductsOrdered(int productsOrdered) {
        this.productsOrdered = productsOrdered;
    }

    public int getProductsLiked() {
        return productsLiked;
    }

    public void setProductsLiked(int productsLiked) {
        this.productsLiked = productsLiked;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }

    public String getProductsDescription() {
        return productsDescription;
    }

    public void setProductsDescription(String productsDescription) {
        this.productsDescription = productsDescription;
    }

    public String getProductsUrl() {
        return productsUrl;
    }

    public void setProductsUrl(String productsUrl) {
        this.productsUrl = productsUrl;
    }

    public int getProductsDefaultStock() {
        return productsDefaultStock;
    }

    public void setProductsDefaultStock(int productsDefaultStock) {
        this.productsDefaultStock = productsDefaultStock;
    }

    public int getProductsViewed() {
        return productsViewed;
    }

    public void setProductsViewed(int productsViewed) {
        this.productsViewed = productsViewed;
    }

    public int getProductsType() {
        return productsType;
    }

    public void setProductsType(int productsType) {
        this.productsType = productsType;
    }

    public int getProductsTaxClassId() {
        return productsTaxClassId;
    }

    public void setProductsTaxClassId(int productsTaxClassId) {
        this.productsTaxClassId = productsTaxClassId;
    }

    public int getTaxRatesId() {
        return taxRatesId;
    }

    public void setTaxRatesId(int taxRatesId) {
        this.taxRatesId = taxRatesId;
    }

    public int getTaxZoneId() {
        return taxZoneId;
    }

    public void setTaxZoneId(int taxZoneId) {
        this.taxZoneId = taxZoneId;
    }

    public int getTaxClassId() {
        return taxClassId;
    }

    public void setTaxClassId(int taxClassId) {
        this.taxClassId = taxClassId;
    }

    public int getTaxPriority() {
        return taxPriority;
    }

    public void setTaxPriority(int taxPriority) {
        this.taxPriority = taxPriority;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getTaxDescription() {
        return taxDescription;
    }

    public void setTaxDescription(String taxDescription) {
        this.taxDescription = taxDescription;
    }

    public String getTaxClassTitle() {
        return taxClassTitle;
    }

    public void setTaxClassTitle(String taxClassTitle) {
        this.taxClassTitle = taxClassTitle;
    }

    public String getTaxClassDescription() {
        return taxClassDescription;
    }

    public void setTaxClassDescription(String taxClassDescription) {
        this.taxClassDescription = taxClassDescription;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(String isLiked) {
        this.isLiked = isLiked;
    }

    public int getManufacturersId() {
        return manufacturersId;
    }

    public void setManufacturersId(int manufacturersId) {
        this.manufacturersId = manufacturersId;
    }

    public String getManufacturersName() {
        return manufacturersName;
    }

    public void setManufacturersName(String manufacturersName) {
        this.manufacturersName = manufacturersName;
    }

    public String getManufacturersImage() {
        return manufacturersImage;
    }

    public void setManufacturersImage(String manufacturersImage) {
        this.manufacturersImage = manufacturersImage;
    }

    public String getManufacturersUrl() {
        return manufacturersUrl;
    }

    public void setManufacturersUrl(String manufacturersUrl) {
        this.manufacturersUrl = manufacturersUrl;
    }

    public String getFlashStartDate() {
        return flashStartDate;
    }

    public void setFlashStartDate(String flashStartDate) {
        this.flashStartDate = flashStartDate;
    }

    public String getFlashExpireDate() {
        return flashExpireDate;
    }

    public void setFlashExpireDate(String flashExpireDate) {
        this.flashExpireDate = flashExpireDate;
    }

    public String getFlashPrice() {
        return flashPrice;
    }

    public void setFlashPrice(String flashPrice) {
        this.flashPrice = flashPrice;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }


    public String getIsSaleProduct() {
        return isSaleProduct;
    }

    public void setIsSaleProduct(String isSaleProduct) {
        this.isSaleProduct = isSaleProduct;
    }


    public String getAttributesPrice() {
        return attributesPrice;
    }

    public void setAttributesPrice(String attributesPrice) {
        this.attributesPrice = attributesPrice;
    }

    public double getProductsFinalPrice() {
        return productsFinalPrice;
    }

    public void setProductsFinalPrice(double productsFinalPrice) {
        this.productsFinalPrice = productsFinalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCustomersBasketQuantity() {
        return customersBasketQuantity;
    }

    public void setCustomersBasketQuantity(int customersBasketQuantity) {
        this.customersBasketQuantity = customersBasketQuantity;
    }

    public String getCategoryIDs() {
        return categoryIDs;
    }

    public void setCategoryIDs(String categoryIDs) {
        this.categoryIDs = categoryIDs;
    }

    public String getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(String categoryNames) {
        this.categoryNames = categoryNames;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<ProductCategories> getCategories() {
        return categories;
    }

    public void setCategories(List<ProductCategories> categories) {
        this.categories = categories;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }


    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//

    @Override
    public int describeContents() {
        return 0;
    }


    //********** Writes the values to the Parcel *********//

    @Override
    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeInt(productsId);
        parcel_out.writeInt(productsQuantity);
        parcel_out.writeInt(customersBasketQuantity);
        parcel_out.writeInt(productsStatus);
        parcel_out.writeInt(productsOrdered);
        parcel_out.writeInt(productsViewed);
        parcel_out.writeInt(productsLiked);
        parcel_out.writeInt(manufacturersId);
        parcel_out.writeInt(taxClassId);
        parcel_out.writeInt(taxRatesId);
        parcel_out.writeInt(taxPriority);
        parcel_out.writeInt(taxZoneId);
        parcel_out.writeInt(productsTaxClassId);
        parcel_out.writeInt(languageId);
        parcel_out.writeInt(sortOrder);
        parcel_out.writeInt(productsType);
        parcel_out.writeInt(productsDefaultStock);
        parcel_out.writeString(productsName);
        parcel_out.writeString(productsModel);
        parcel_out.writeString(productsImage);
        parcel_out.writeString(productsWeight);
        parcel_out.writeString(productsWeightUnit);
        parcel_out.writeString(productsUrl);
        parcel_out.writeString(productsDescription);
        parcel_out.writeDouble(productsPrice);
        parcel_out.writeString(discountPrice);
        parcel_out.writeString(attributesPrice);
        parcel_out.writeDouble(productsFinalPrice);
        parcel_out.writeDouble(totalPrice);
        parcel_out.writeString(isLiked);
        parcel_out.writeString(dateAdded);
        parcel_out.writeString(productsDateAdded);
        parcel_out.writeString(productsDateAvailable);
        parcel_out.writeString(lastModified);
        parcel_out.writeString(productsLastModified);
        parcel_out.writeString(taxRate);
        parcel_out.writeString(taxDescription);
        parcel_out.writeString(taxClassTitle);
        parcel_out.writeString(taxClassDescription);
        parcel_out.writeString(manufacturersName);
        parcel_out.writeString(manufacturersImage);
        parcel_out.writeString(manufacturersUrl);
        parcel_out.writeString(categoryIDs);
        parcel_out.writeString(categoryNames);
        parcel_out.writeList(images);
        parcel_out.writeList(categories);
        parcel_out.writeList(attributes);
        parcel_out.writeList(ratingDataLists);
        parcel_out.writeFloat(rating);
        parcel_out.writeInt(total_user_rated);
    }


    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<ShippingProductDetails> CREATOR = new Creator<ShippingProductDetails>() {

        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public ShippingProductDetails createFromParcel(Parcel parcel_in) {
            return new ShippingProductDetails(parcel_in);
        }

        // Creates a new array of the Parcelable class
        @Override
        public ShippingProductDetails[] newArray(int size) {
            return new ShippingProductDetails[size];
        }
    };


    //********** Retrieves the values from the Parcel *********//

    protected ShippingProductDetails(Parcel parcel_in) {
        this.productsId = parcel_in.readInt();
        this.productsQuantity = parcel_in.readInt();
        this.customersBasketQuantity = parcel_in.readInt();
        this.productsStatus = parcel_in.readInt();
        this.productsOrdered = parcel_in.readInt();
        this.productsViewed = parcel_in.readInt();
        this.productsLiked = parcel_in.readInt();
        this.manufacturersId = parcel_in.readInt();
        this.taxClassId = parcel_in.readInt();
        this.taxRatesId = parcel_in.readInt();
        this.taxPriority = parcel_in.readInt();
        this.taxZoneId = parcel_in.readInt();
        this.productsTaxClassId = parcel_in.readInt();
        this.languageId = parcel_in.readInt();
        this.sortOrder = parcel_in.readInt();
        this.productsType = parcel_in.readInt();
        this.productsDefaultStock = parcel_in.readInt();
        this.productsName = parcel_in.readString();
        this.productsModel = parcel_in.readString();
        this.productsImage = parcel_in.readString();
        this.productsWeight = parcel_in.readString();
        this.productsWeightUnit = parcel_in.readString();
        this.productsUrl = parcel_in.readString();
        this.productsDescription = parcel_in.readString();
        this.productsPrice = parcel_in.readDouble();
        this.discountPrice = parcel_in.readString();
        this.attributesPrice = parcel_in.readString();
        this.productsFinalPrice = parcel_in.readDouble();
        this.totalPrice = parcel_in.readDouble();
        this.isLiked = parcel_in.readString();
        this.dateAdded = parcel_in.readString();
        this.productsDateAdded = parcel_in.readString();
        this.productsDateAvailable = parcel_in.readString();
        this.lastModified = parcel_in.readString();
        this.productsLastModified = parcel_in.readString();
        this.taxRate = parcel_in.readString();
        this.taxDescription = parcel_in.readString();
        this.taxClassTitle = parcel_in.readString();
        this.taxClassDescription = parcel_in.readString();
        this.manufacturersName = parcel_in.readString();
        this.manufacturersImage = parcel_in.readString();
        this.manufacturersUrl = parcel_in.readString();
        this.categoryIDs = parcel_in.readString();
        this.categoryNames = parcel_in.readString();
        this.rating= parcel_in.readFloat();
        this.total_user_rated=parcel_in.readInt();

        this.images = new ArrayList<Image>();
        parcel_in.readList(images, Image.class.getClassLoader());

        this.categories = new ArrayList<ProductCategories>();
        parcel_in.readList(categories, ProductCategories.class.getClassLoader());

        this.attributes = new ArrayList<Attribute>();
        parcel_in.readList(attributes, Attribute.class.getClassLoader());

        this.ratingDataLists = new ArrayList<>();
        parcel_in.readList(ratingDataLists,RatingDataList.class.getClassLoader());
    }

}
