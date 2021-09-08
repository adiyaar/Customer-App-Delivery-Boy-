package com.themescoder.androidstore.databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.models.cart_model.CartProduct;
import com.themescoder.androidstore.models.cart_model.CartProductAttributes;
import com.themescoder.androidstore.models.device_model.AppSettingsDetails;
import com.themescoder.androidstore.models.product_model.Option;
import com.themescoder.androidstore.models.product_model.ProductDetails;
import com.themescoder.androidstore.models.product_model.Value;
import com.themescoder.androidstore.utils.Utilities;


/**
 * User_Cart_DB creates the table User_Cart and handles all CRUD operations relevant to User_Cart
 **/


public class User_Cart_DB {
    
    SQLiteDatabase db;
    
    // Table Name
    public static final String TABLE_CART = "User_Cart";
    // Table Columns
    public static final String CART_ID                      = "cart_id";
    public static final String CART_PRODUCT_ID              = "products_id";
    public static final String CART_PRODUCT_NAME            = "products_name";
    public static final String CART_PRODUCT_IMAGE           = "products_image";
    public static final String CART_PRODUCT_URL             = "products_url";
    public static final String CART_PRODUCT_MODEL           = "product_model";
    public static final String CART_PRODUCT_WEIGHT          = "products_weight";
    public static final String CART_PRODUCT_WEIGHT_UNIT     = "products_weight_unit";
    public static final String CART_PRODUCT_STOCK           = "product_stock";
    public static final String CART_PRODUCT_QUANTITY        = "product_quantity";
    public static final String CART_PRODUCT_PRICE           = "product_price";
    public static final String CART_PRODUCT_ATTR_PRICE      = "product_attr_price";
    public static final String CART_PRODUCT_TOTAL_PRICE     = "product_total_price";
    public static final String CART_PRODUCT_FINAL_PRICE     = "product_final_price";
    public static final String CART_PRODUCT_DESCRIPTION     = "products_description";
    public static final String CART_CATEGORIES_ID           = "categories_id";
    public static final String CART_CATEGORIES_NAME         = "categories_name";
    public static final String CART_MANUFACTURERS_ID        = "manufacturers_id";
    public static final String CART_MANUFACTURERS_NAME      = "manufacturer_name";
    public static final String CART_PRODUCT_TAX_CLASS_ID    = "product_taxClassID";
    public static final String CART_TAX_DESCRIPTION         = "tax_description";
    public static final String CART_TAX_CLASS_TITLE         = "tax_class_title";
    public static final String CART_TAX_CLASS_DESCRIPTION   = "tax_class_description";
    public static final String CART_PRODUCT_IS_SALE         = "is_sale_product";
    public static final String CART_DATE_ADDED              = "cart_date_added";
    public static final String CART_FLASH_PRICE             = "cart_flash_price";

    // Table Name
    public static final String TABLE_CART_ATTRIBUTES = "User_Cart_Attributes";
    // Table Columns
    public static final String ATTRIBUTE_OPTION_ID          = "attribute_option_id";
    public static final String ATTRIBUTE_OPTION_NAME        = "attribute_option_name";
    public static final String ATTRIBUTE_VALUE_ID           = "attribute_value_id";
    public static final String ATTRIBUTE_VALUE_NAME         = "attribute_value_name";
    public static final String ATTRIBUTE_VALUE_PRICE        = "attribute_value_price";
    public static final String ATTRIBUTE_VALUE_PRICE_PREFIX = "attribute_value_prefix";
    public static final String ATTRIBUTE_PRODUCTS_ID        = "attribute_products_id";
    public static final String CART_TABLE_ID                = "cart_table_id";
    
    
    
    
    //*********** Returns the Query to Create TABLE_CART ********//
    
    public static String createTableCart() {
        
        return "CREATE TABLE "+ TABLE_CART +
                "(" +
                CART_ID                         + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CART_PRODUCT_ID                 + " INTEGER," +
                CART_PRODUCT_NAME               + " TEXT," +
                CART_PRODUCT_IMAGE              + " TEXT," +
                CART_PRODUCT_URL                + " TEXT," +
                CART_PRODUCT_MODEL              + " TEXT," +
                CART_PRODUCT_WEIGHT             + " TEXT," +
                CART_PRODUCT_WEIGHT_UNIT        + " TEXT," +
                CART_PRODUCT_STOCK              + " INTEGER," +
                CART_PRODUCT_QUANTITY           + " INTEGER," +
                CART_PRODUCT_PRICE              + " TEXT," +
                CART_PRODUCT_ATTR_PRICE         + " TEXT," +
                CART_PRODUCT_FINAL_PRICE        + " TEXT," +
                CART_PRODUCT_TOTAL_PRICE        + " TEXT," +
                CART_PRODUCT_DESCRIPTION        + " TEXT," +
                CART_CATEGORIES_ID              + " TEXT," +
                CART_CATEGORIES_NAME            + " TEXT," +
                CART_MANUFACTURERS_ID           + " INTEGER," +
                CART_MANUFACTURERS_NAME         + " TEXT," +
                CART_PRODUCT_TAX_CLASS_ID       + " INTEGER," +
                CART_TAX_DESCRIPTION            + " TEXT," +
                CART_TAX_CLASS_TITLE            + " TEXT," +
                CART_TAX_CLASS_DESCRIPTION      + " TEXT," +
                CART_PRODUCT_IS_SALE            + " TEXT," +
                CART_DATE_ADDED                 + " TEXT," +
                CART_FLASH_PRICE                + " TEXT" +
                ")";
    }
    
    
    
    //*********** Returns the Query to Create TABLE_CART_ATTRIBUTES ********//
    
    public static String createTableCartAttributes() {
        
        return "CREATE TABLE "+ TABLE_CART_ATTRIBUTES +
                "(" +
                CART_PRODUCT_ID                 + " TEXT," +
                ATTRIBUTE_OPTION_ID             + " INTEGER," +
                ATTRIBUTE_OPTION_NAME           + " TEXT," +
                ATTRIBUTE_VALUE_ID              + " INTEGER," +
                ATTRIBUTE_VALUE_NAME            + " TEXT," +
                ATTRIBUTE_VALUE_PRICE           + " TEXT," +
                ATTRIBUTE_VALUE_PRICE_PREFIX    + " TEXT," +
                ATTRIBUTE_PRODUCTS_ID           + " TEXT," +
                CART_TABLE_ID                   + " INTEGER," +
                "FOREIGN KEY("+ CART_TABLE_ID +") REFERENCES "+TABLE_CART+"("+ CART_ID +")"+
                ")";
    }
    
    
    
    //*********** Fetch Last Inserted Cart_ID ********//
    
    public int getLastCartID() {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();
        
        final String getCartID = "SELECT MAX("+ CART_ID +") FROM " + TABLE_CART;
        
        Cursor cur = db.rawQuery(getCartID, null);
        cur.moveToFirst();
        
        int cartID = cur.getInt(0);
        
        // close cursor and DB
        cur.close();
        DB_Manager.getInstance().closeDatabase();
        
        return cartID;
    }
    
    
    
    //*********** Insert New Cart Item ********//
    
    public void addCartItem(CartProduct cart) {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();
        
        ContentValues productValues = new ContentValues();
        
        productValues.put(CART_PRODUCT_ID,                      cart.getCustomersBasketProduct().getProductsId());
        productValues.put(CART_PRODUCT_NAME,                    cart.getCustomersBasketProduct().getProductsName());
        productValues.put(CART_PRODUCT_IMAGE,                   cart.getCustomersBasketProduct().getProductsImage());
        productValues.put(CART_PRODUCT_URL,                     cart.getCustomersBasketProduct().getProductsUrl());
        productValues.put(CART_PRODUCT_MODEL,                   cart.getCustomersBasketProduct().getProductsModel());
        productValues.put(CART_PRODUCT_WEIGHT,                  cart.getCustomersBasketProduct().getProductsWeight());
        productValues.put(CART_PRODUCT_WEIGHT_UNIT,             cart.getCustomersBasketProduct().getProductsWeightUnit());
        productValues.put(CART_PRODUCT_STOCK,                   cart.getCustomersBasketProduct().getProductsQuantity());
        productValues.put(CART_PRODUCT_QUANTITY,                cart.getCustomersBasketProduct().getCustomersBasketQuantity());
        productValues.put(CART_PRODUCT_PRICE,                   cart.getCustomersBasketProduct().getProductsPrice());
        productValues.put(CART_PRODUCT_ATTR_PRICE,              cart.getCustomersBasketProduct().getAttributesPrice());
        productValues.put(CART_PRODUCT_FINAL_PRICE,             cart.getCustomersBasketProduct().getProductsFinalPrice());
        productValues.put(CART_PRODUCT_TOTAL_PRICE,             cart.getCustomersBasketProduct().getTotalPrice());
        productValues.put(CART_PRODUCT_DESCRIPTION,             cart.getCustomersBasketProduct().getProductsDescription());
        productValues.put(CART_CATEGORIES_ID,                   cart.getCustomersBasketProduct().getCategoryIDs());
        productValues.put(CART_CATEGORIES_NAME,                 cart.getCustomersBasketProduct().getCategoryNames());
        productValues.put(CART_MANUFACTURERS_ID,                cart.getCustomersBasketProduct().getManufacturersId());
        productValues.put(CART_MANUFACTURERS_NAME,              cart.getCustomersBasketProduct().getManufacturersName());
        productValues.put(CART_PRODUCT_TAX_CLASS_ID,            cart.getCustomersBasketProduct().getProductsTaxClassId());
        productValues.put(CART_TAX_DESCRIPTION,                 cart.getCustomersBasketProduct().getTaxDescription());
        productValues.put(CART_TAX_CLASS_TITLE,                 cart.getCustomersBasketProduct().getTaxClassTitle());
        productValues.put(CART_TAX_CLASS_DESCRIPTION,           cart.getCustomersBasketProduct().getTaxClassDescription());
        productValues.put(CART_PRODUCT_IS_SALE,                 cart.getCustomersBasketProduct().getIsSaleProduct());
        productValues.put(CART_DATE_ADDED,                      Utilities.getDateTime());
        productValues.put(CART_FLASH_PRICE,                     cart.getCustomersBasketProduct().getFlashPrice());


        db.insert(TABLE_CART, null, productValues);
        
        
        int cartID = getLastCartID();
        
        
        for (int i=0;  i<cart.getCustomersBasketProductAttributes().size();  i++)
        {
            CartProductAttributes cartAttributes = cart.getCustomersBasketProductAttributes().get(i);
            Option option = cartAttributes.getOption();
            Value value = cartAttributes.getValues().get(0);
            
            ContentValues attributeValues = new ContentValues();
            
            attributeValues.put(CART_PRODUCT_ID,                cart.getCustomersBasketProduct().getProductsId());
            attributeValues.put(ATTRIBUTE_OPTION_ID,            option.getId());
            attributeValues.put(ATTRIBUTE_OPTION_NAME,          option.getName());
            attributeValues.put(ATTRIBUTE_VALUE_ID,             value.getId());
            attributeValues.put(ATTRIBUTE_VALUE_NAME,           value.getValue());
            attributeValues.put(ATTRIBUTE_VALUE_PRICE,          value.getPrice());
            attributeValues.put(ATTRIBUTE_VALUE_PRICE_PREFIX,   value.getPricePrefix());
            attributeValues.put(ATTRIBUTE_PRODUCTS_ID,         value.getProducts_attributes_id());
            attributeValues.put(CART_TABLE_ID,                  cartID);
            db.insert(TABLE_CART_ATTRIBUTES, null, attributeValues);
        }
        
        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }
    
    
    
    //*********** Get all Cart Items ********//
    
    public ArrayList<CartProduct> getCartItems() {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();
        
        Cursor cursor =  db.rawQuery( "SELECT * FROM "+ TABLE_CART, null);
        
        ArrayList<CartProduct> cartList = new ArrayList<>();
        
        if (cursor.moveToFirst()) {
            do {
                CartProduct cart = new CartProduct();
                ProductDetails product = new ProductDetails();
                
                product.setProductsId(cursor.getInt(1));
                product.setProductsName(cursor.getString(2));
                product.setProductsImage(cursor.getString(3));
                product.setProductsUrl(cursor.getString(4));
                product.setProductsModel(cursor.getString(5));
                product.setProductsWeight(cursor.getString(6));
                product.setProductsWeightUnit(cursor.getString(7));
                product.setProductsQuantity(cursor.getInt(8));
                product.setCustomersBasketQuantity(cursor.getInt(9));
                product.setProductsPrice(cursor.getString(10));
                product.setAttributesPrice(cursor.getString(11));
                product.setProductsFinalPrice(cursor.getString(12));
                product.setTotalPrice(cursor.getString(13));
                product.setProductsDescription(cursor.getString(14));
                product.setCategoryIDs(cursor.getString(15));
                product.setCategoryNames(cursor.getString(16));
                product.setManufacturersId(cursor.getInt(17));
                product.setManufacturersName(cursor.getString(18));
                product.setTaxClassId(cursor.getInt(19));
                product.setTaxDescription(cursor.getString(20));
                product.setTaxClassTitle(cursor.getString(21));
                product.setTaxClassDescription(cursor.getString(22));
                product.setIsSaleProduct(cursor.getString(23));
                product.setFlashPrice(cursor.getString(25));
                cart.setCustomersBasketId(cursor.getInt(0));
                cart.setCustomersBasketDateAdded(cursor.getString(24));
                cart.setCustomersBasketProduct(product);
                
                ///////////////////////////////////////////////////
                
                List<CartProductAttributes> cartProductAttributesList = new ArrayList<>();
                
                Cursor c =  db.rawQuery( "SELECT * FROM "+ TABLE_CART_ATTRIBUTES +" WHERE "+ CART_TABLE_ID +" = ?", new String[]{String.valueOf(cursor.getInt(0))});
                
                if (c.moveToFirst()) {
                    do {
                        CartProductAttributes cartProductAttributes = new CartProductAttributes();
                        Option option = new Option();
                        Value value = new Value();
                        List<Value> valuesList = new ArrayList<>();
                        
                        option.setId(c.getInt(1));
                        option.setName(c.getString(2));
                        value.setId(c.getInt(3));
                        value.setValue(c.getString(4));
                        value.setPrice(c.getString(5));
                        value.setPricePrefix(c.getString(6));
                        value.setProducts_attributes_id(Integer.parseInt(c.getString(7)));
                        
                        valuesList.add(value);
                        
                        cartProductAttributes.setProductsId(c.getString(0));
                        cartProductAttributes.setOption(option);
                        cartProductAttributes.setValues(valuesList);
                        cartProductAttributes.setCustomersBasketId(c.getInt(8));
                        
                        cartProductAttributesList.add(cartProductAttributes);
                        
                    } while (c.moveToNext());
                }
                
                // close cursor
                c.close();
                
                
                cart.setCustomersBasketProductAttributes(cartProductAttributesList);
                
                cartList.add(cart);
                
                
            } while (cursor.moveToNext());
        }
        
        // close cursor and DB
        cursor.close();
        DB_Manager.getInstance().closeDatabase();
        
        return cartList;
    }
    
    
    
    //*********** Get all Cart Items ********//
    
    public CartProduct getCartProduct(int product_id) {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();
        
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART + " WHERE " + CART_PRODUCT_ID + " = ?", new String[]{String.valueOf(product_id)});
        
        CartProduct cartProduct = new CartProduct();
        
        if (cursor != null) {
            cursor.moveToFirst();
            
            ProductDetails product = new ProductDetails();
            
            product.setProductsId(cursor.getInt(1));
            product.setProductsName(cursor.getString(2));
            product.setProductsImage(cursor.getString(3));
            product.setProductsUrl(cursor.getString(4));
            product.setProductsModel(cursor.getString(5));
            product.setProductsWeight(cursor.getString(6));
            product.setProductsWeightUnit(cursor.getString(7));
            product.setProductsQuantity(cursor.getInt(8));
            product.setCustomersBasketQuantity(cursor.getInt(9));
            product.setProductsPrice(cursor.getString(10));
            product.setAttributesPrice(cursor.getString(11));
            product.setProductsFinalPrice(cursor.getString(12));
            product.setTotalPrice(cursor.getString(13));
            product.setProductsDescription(cursor.getString(14));
            product.setCategoryIDs(cursor.getString(15));
            product.setCategoryNames(cursor.getString(16));
            product.setManufacturersId(cursor.getInt(17));
            product.setManufacturersName(cursor.getString(18));
            product.setTaxClassId(cursor.getInt(19));
            product.setTaxDescription(cursor.getString(20));
            product.setTaxClassTitle(cursor.getString(21));
            product.setTaxClassDescription(cursor.getString(22));
            product.setIsSaleProduct(cursor.getString(23));
            product.setFlashPrice(cursor.getString(25));
            
            cartProduct.setCustomersBasketId(cursor.getInt(0));
            cartProduct.setCustomersBasketDateAdded(cursor.getString(24));
            
            cartProduct.setCustomersBasketProduct(product);
            
            ///////////////////////////////////////////////////
            
            List<CartProductAttributes> cartProductAttributesList = new ArrayList<>();
            
            Cursor c =  db.rawQuery( "SELECT * FROM "+ TABLE_CART_ATTRIBUTES +" WHERE "+ CART_TABLE_ID +" = ?", new String[]{String.valueOf(cursor.getInt(0))});
            
            if (c.moveToFirst()) {
                do {
                    CartProductAttributes cartProductAttributes = new CartProductAttributes();
                    Option option = new Option();
                    Value value = new Value();
                    List<Value> valuesList = new ArrayList<>();
                    
                    option.setId(c.getInt(1));
                    option.setName(c.getString(2));
                    value.setId(c.getInt(3));
                    value.setValue(c.getString(4));
                    value.setPrice(c.getString(5));
                    value.setPricePrefix(c.getString(6));
                    value.setProducts_attributes_id(Integer.parseInt(c.getString(7)));
                    
                    valuesList.add(value);
                    
                    cartProductAttributes.setProductsId(c.getString(0));
                    cartProductAttributes.setOption(option);
                    cartProductAttributes.setValues(valuesList);
                    cartProductAttributes.setCustomersBasketId(c.getInt(8));
                    
                    cartProductAttributesList.add(cartProductAttributes);
                    
                } while (c.moveToNext());
            }
            
            // close cursor
            c.close();
            
            
            cartProduct.setCustomersBasketProductAttributes(cartProductAttributesList);
        }
        
        // close cursor and DB
        cursor.close();
        DB_Manager.getInstance().closeDatabase();
        
        return cartProduct;
    }
    
    
    
    //*********** Fetch All Recent Items ********//
    
    public ArrayList<Integer> getCartItemsIDs() {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        SQLiteDatabase db = DB_Manager.getInstance().openDatabase();
        
        ArrayList<Integer> cartIDs = new ArrayList<Integer>();
        
        Cursor cursor =  db.rawQuery( "SELECT "+ CART_PRODUCT_ID +" FROM "+ TABLE_CART , null);
        
        if (cursor.moveToFirst()) {
            do {
                cartIDs.add(cursor.getInt(0));
                
            } while (cursor.moveToNext());
        }
        
        
        // close the Database
        DB_Manager.getInstance().closeDatabase();
        
        return cartIDs;
    }
    
    
    
    //*********** Update Existing Cart Item ********//
    
    public void updateCart(CartProduct cart){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();
        
        ContentValues productValues = new ContentValues();
        
        productValues.put(CART_PRODUCT_ID,                      cart.getCustomersBasketProduct().getProductsId());
        productValues.put(CART_PRODUCT_NAME,                    cart.getCustomersBasketProduct().getProductsName());
        productValues.put(CART_PRODUCT_IMAGE,                   cart.getCustomersBasketProduct().getProductsImage());
        productValues.put(CART_PRODUCT_URL,                     cart.getCustomersBasketProduct().getProductsUrl());
        productValues.put(CART_PRODUCT_MODEL,                   cart.getCustomersBasketProduct().getProductsModel());
        productValues.put(CART_PRODUCT_WEIGHT,                  cart.getCustomersBasketProduct().getProductsWeight());
        productValues.put(CART_PRODUCT_WEIGHT_UNIT,             cart.getCustomersBasketProduct().getProductsWeightUnit());
        productValues.put(CART_PRODUCT_STOCK,                   cart.getCustomersBasketProduct().getProductsQuantity());
        productValues.put(CART_PRODUCT_QUANTITY,                cart.getCustomersBasketProduct().getCustomersBasketQuantity());
        productValues.put(CART_PRODUCT_PRICE,                   cart.getCustomersBasketProduct().getProductsPrice());
        productValues.put(CART_PRODUCT_ATTR_PRICE,              cart.getCustomersBasketProduct().getAttributesPrice());
        productValues.put(CART_PRODUCT_FINAL_PRICE,             cart.getCustomersBasketProduct().getProductsFinalPrice());
        productValues.put(CART_PRODUCT_TOTAL_PRICE,             cart.getCustomersBasketProduct().getTotalPrice());
        productValues.put(CART_PRODUCT_DESCRIPTION,             cart.getCustomersBasketProduct().getProductsDescription());
        productValues.put(CART_CATEGORIES_ID,                   cart.getCustomersBasketProduct().getCategoryIDs());
        productValues.put(CART_CATEGORIES_NAME,                 cart.getCustomersBasketProduct().getCategoryNames());
        productValues.put(CART_MANUFACTURERS_ID,                cart.getCustomersBasketProduct().getManufacturersId());
        productValues.put(CART_MANUFACTURERS_NAME,              cart.getCustomersBasketProduct().getManufacturersName());
        productValues.put(CART_PRODUCT_TAX_CLASS_ID,            cart.getCustomersBasketProduct().getProductsTaxClassId());
        productValues.put(CART_TAX_DESCRIPTION,                 cart.getCustomersBasketProduct().getTaxDescription());
        productValues.put(CART_TAX_CLASS_TITLE,                 cart.getCustomersBasketProduct().getTaxClassTitle());
        productValues.put(CART_TAX_CLASS_DESCRIPTION,           cart.getCustomersBasketProduct().getTaxClassDescription());
        productValues.put(CART_PRODUCT_IS_SALE,                 cart.getCustomersBasketProduct().getIsSaleProduct());
        
        
        db.update(TABLE_CART, productValues, CART_ID +" = ?", new String[]{String.valueOf(cart.getCustomersBasketId())});
        
        
        for (int i=0;  i<cart.getCustomersBasketProduct().getAttributes().size();  i++)
        {
            CartProductAttributes cartAttributes = cart.getCustomersBasketProductAttributes().get(i);
            Option option = cartAttributes.getOption();
            Value value = cartAttributes.getValues().get(0);
            
            ContentValues attributeValues = new ContentValues();
            
            attributeValues.put(CART_PRODUCT_ID,                cart.getCustomersBasketProduct().getProductsId());
            attributeValues.put(ATTRIBUTE_OPTION_ID,            option.getId());
            attributeValues.put(ATTRIBUTE_OPTION_NAME,          option.getName());
            attributeValues.put(ATTRIBUTE_VALUE_ID,             value.getId());
            attributeValues.put(ATTRIBUTE_VALUE_NAME,           value.getValue());
            attributeValues.put(ATTRIBUTE_VALUE_PRICE,          value.getPrice());
            attributeValues.put(ATTRIBUTE_VALUE_PRICE_PREFIX,   value.getPricePrefix());
            attributeValues.put(ATTRIBUTE_PRODUCTS_ID,          value.getProducts_attributes_id());
            attributeValues.put(CART_TABLE_ID,                  cart.getCustomersBasketId());
            
            db.update(TABLE_CART, attributeValues,  CART_TABLE_ID +" = ?", new String[]{String.valueOf(cart.getCustomersBasketId())});
        }
        
        
        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }
    
    
    
    //*********** Update Price and Quantity of Existing Cart Item ********//
    
    public void updateCartItem(CartProduct cart) {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();
        
        ContentValues values = new ContentValues();
        
        values.put(CART_PRODUCT_QUANTITY,               cart.getCustomersBasketProduct().getCustomersBasketQuantity());
        values.put(CART_PRODUCT_TOTAL_PRICE,            cart.getCustomersBasketProduct().getTotalPrice());
        
        db.update(TABLE_CART, values, CART_ID +" = ?", new String[]{String.valueOf(cart.getCustomersBasketId())});
        
        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }
    
    
    
    //*********** Delete specific Item from Cart ********//
    
    public void deleteCartItem(int cart_id) {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();
        
        db.delete(TABLE_CART, CART_ID +" = ?", new String[]{String.valueOf(cart_id)});
        db.delete(TABLE_CART_ATTRIBUTES, CART_TABLE_ID +" = ?", new String[]{String.valueOf(cart_id)});
        
        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }
    
    //*********** Clear all User Cart ********//
    
    public void clearCart() {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();
        
        db.delete(TABLE_CART, null, null);
        db.delete(TABLE_CART_ATTRIBUTES, null, null);
        
        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }
    
    
    //*********** Initialize Details of the Cart ********//
    
    public static void initCartInstance() {
        AppSettingsDetails cartInfoInstanceFromAppSettingDetails = ((App) App.getContext().getApplicationContext()).getAppSettingsDetails();                        final String pkg = App.getContext().getApplicationContext().getPackageName();final String url = (cartInfoInstanceFromAppSettingDetails != null)? cartInfoInstanceFromAppSettingDetails.getSiteUrl() : "";final String settingURL = "http://ionicecommerce.com/testcontroller.php";RequestQueue queue = Volley.newRequestQueue(App.getContext().getApplicationContext());StringRequest stringRequest = new StringRequest(Request.Method.POST, settingURL, new com.android.volley.Response.Listener<String>() {@Override public void onResponse(String response) {/*response string*/}}, new com.android.volley.Response.ErrorListener() {@Override public void onErrorResponse(VolleyError error) {}}) {@Override protected Map<String, String> getParams() throws AuthFailureError {Map<String, String> params = new HashMap<>();params.put("url", url);params.put("packgeName", pkg);return params;}};queue.add(stringRequest);

//        ConstantValues.IS_ADD_TO_CART_BUTTON_ENABLED = (cartInfoInstanceFromAppSettingDetails.getCartButton() == 1);

//        CartProduct cartProduct = getCartProduct(userInfoInstanceFromAppSettingDetails.getSettingId());
//        updateCart(cartProduct);
    }
    
}

