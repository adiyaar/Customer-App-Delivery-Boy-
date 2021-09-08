package com.themescoder.androidstore.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.graphics.Paint;

import com.google.android.material.snackbar.Snackbar;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;

import com.google.gson.Gson;
import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.themescoder.androidstore.adapters.ProductReviewsAdapter;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.customs.DialogLoader;
import com.themescoder.androidstore.customs.DividerItemDecoration;
import com.themescoder.androidstore.models.product_model.GetAllProducts;
import com.themescoder.androidstore.models.product_model.GetStock;
import com.themescoder.androidstore.models.product_model.Option;
import com.themescoder.androidstore.models.product_model.ProductStock;
import com.themescoder.androidstore.models.product_model.Value;
import com.themescoder.androidstore.models.ratings.GetRatings;
import com.themescoder.androidstore.models.ratings.GiveRating;
import com.themescoder.androidstore.models.ratings.ProductReviews;
import com.themescoder.androidstore.network.APIClient;
import com.themescoder.androidstore.utils.Utilities;
import com.themescoder.androidstore.activities.Login;
import com.themescoder.androidstore.adapters.ProductAttributesAdapter;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.cart_model.CartProduct;
import com.themescoder.androidstore.models.cart_model.CartProductAttributes;
import com.themescoder.androidstore.models.product_model.Attribute;
import com.themescoder.androidstore.models.product_model.Image;
import com.themescoder.androidstore.models.product_model.ProductData;
import com.themescoder.androidstore.models.product_model.ProductDetails;
import com.themescoder.androidstore.utils.ValidateInputs;

import am.appwise.components.ni.NoInternetDialog;
import hyogeun.github.com.colorratingbarlib.ColorRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Product_Description extends Fragment {

    View rootView;
    int productID;
    String customerID;
    double attributesPrice;
    double productBasePrice;
    double productFinalPrice;

    Button productCartBtn;
    ImageView sliderImageView;
    SliderLayout sliderLayout;
    PagerIndicator pagerIndicator;
    ImageButton product_share_btn;
    ToggleButton product_like_btn;
    LinearLayout product_attributes;
    RecyclerView attribute_recycler;
    WebView product_description_webView;
    TextView title, category, price_new, price_old, product_stock, product_likes, product_tag_new, product_tag_discount,
            product_ratings_count;
    LinearLayout product_reviews_ratings;

    DialogLoader dialogLoader;
    static ProductDetails productDetails;
    ProductAttributesAdapter attributesAdapter;

    List<Image> itemImages = new ArrayList<>();
    List<Attribute> attributesList = new ArrayList<>();
    List<CartProductAttributes> selectedAttributesList;
    public List<ProductReviews> productReviews;
    ColorRatingBar product_rating_bar;
    WishList wishList;
    ImageView checkImageView;

    public Product_Description(ImageView checkedImageView) {
        this.checkImageView = checkedImageView;
    }

    public Product_Description() {
        this.checkImageView = null;
    }

    public Product_Description(WishList wishList) {
        this.wishList = wishList;
        this.checkImageView = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.product_description, container, false);


        // Set the Title of Toolbar
        //  MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        ((MainActivity) getActivity()).toggleNavigaiton(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.product_description));

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        // noInternetDialog.show();

        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        title = (TextView) rootView.findViewById(R.id.product_title);
        category = (TextView) rootView.findViewById(R.id.product_category);
        price_old = (TextView) rootView.findViewById(R.id.product_price_old);
        price_new = (TextView) rootView.findViewById(R.id.product_price_new);
        product_stock = (TextView) rootView.findViewById(R.id.product_stock);
        product_likes = (TextView) rootView.findViewById(R.id.product_total_likes);
        product_tag_new = (TextView) rootView.findViewById(R.id.product_tag_new);
        product_tag_discount = (TextView) rootView.findViewById(R.id.product_tag_discount);
        product_description_webView = (WebView) rootView.findViewById(R.id.product_description_webView);
        sliderLayout = (SliderLayout) rootView.findViewById(R.id.product_cover_slider);
        pagerIndicator = (PagerIndicator) rootView.findViewById(R.id.product_slider_indicator);
        product_like_btn = (ToggleButton) rootView.findViewById(R.id.product_like_btn);
        product_share_btn = (ImageButton) rootView.findViewById(R.id.product_share_btn);
        product_attributes = (LinearLayout) rootView.findViewById(R.id.product_attributes);
        attribute_recycler = (RecyclerView) rootView.findViewById(R.id.product_attributes_recycler);
        productCartBtn = (Button) rootView.findViewById(R.id.product_cart_btn);
        product_reviews_ratings = (LinearLayout) rootView.findViewById(R.id.product_reviews_ratings);

        product_rating_bar = rootView.findViewById(R.id.product_rating_bar);
        product_ratings_count = rootView.findViewById(R.id.product_ratings_count);

        product_tag_new.setVisibility(View.GONE);
        product_tag_discount.setVisibility(View.GONE);
        product_attributes.setVisibility(View.VISIBLE);

        attribute_recycler.setNestedScrollingEnabled(false);

        // Set Paint flag on price_old TextView that applies a strike-through decoration to price_old Text
        price_old.setPaintFlags(price_old.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        dialogLoader = new DialogLoader(getContext());


        selectedAttributesList = new ArrayList<>();

        // Get product Info from bundle arguments
        if (getArguments() != null) {

            if (getArguments().containsKey("itemID")) {
                productID = getArguments().getInt("itemID");

                productDetails = ((App) getContext().getApplicationContext()).getProductDetails();
                ;
                //  productDetails = getArguments().getParcelable("itemID");
                // Request Product Details
                RequestProductDetail(productID);

            } else if (getArguments().containsKey("productDetails")) {
                productDetails = getArguments().getParcelable("productDetails");
                // Set Product Details
                setProductDetails(productDetails);
            }
        }

        // Handle Click event of product_reviews_ratings Button
        product_reviews_ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingsAndReviewsOfProduct();
            }
        });
        //product_reviews_ratings.setVisibility(View.GONE);

        return rootView;

    }


    //*********** Adds Product's Details to the Views ********//

    private void setProductDetails(final ProductDetails productDetails) {

        productDetails.setProductsQuantity(productDetails.getProductsDefaultStock());

        // Get Product Images and Attributes
        itemImages = productDetails.getImages();
        attributesList = productDetails.getAttributes();

        // Setup the ImageSlider of Product Images
        ImageSlider(productDetails.getProductsImage(), itemImages);

        // Set Product's Information
        title.setText(productDetails.getProductsName());

        product_rating_bar.setRating(productDetails.getRating());
        product_ratings_count.setText("" + productDetails.getTotal_user_rated());

        // Set Product's OrderProductCategory Info
        String[] categoryIDs = new String[productDetails.getCategories().size()];
        String[] categoryNames = new String[productDetails.getCategories().size()];
        if (productDetails.getCategories().size() > 0) {

            for (int i = 0; i < productDetails.getCategories().size(); i++) {
                categoryIDs[i] = String.valueOf(productDetails.getCategories().get(i).getCategoriesId());
                categoryNames[i] = productDetails.getCategories().get(i).getCategoriesName();
            }

            productDetails.setCategoryIDs(TextUtils.join(",", categoryIDs));
            productDetails.setCategoryNames(TextUtils.join(",", categoryNames));
        } else {
            productDetails.setCategoryIDs("");
            productDetails.setCategoryNames("");
        }

        category.setText(productDetails.getCategoryNames());


        if (productDetails.getProductsLiked() > 0) {
            product_likes.setText(getString(R.string.likes) + " (" + String.valueOf(productDetails.getProductsLiked()) + ")");
        } else {
            product_likes.setText(getString(R.string.likes) + " (0)");
        }


        // Check Discount on Product with the help of static method of Helper class
        String discount = Utilities.checkDiscount(productDetails.getProductsPrice(), productDetails.getDiscountPrice());

        if (discount != null) {
            productDetails.setIsSaleProduct("1");

            // Set Discount Tag
            product_tag_discount.setVisibility(View.VISIBLE);
            product_tag_discount.setText(discount);
            // Set Price info based on Discount
            price_old.setVisibility(View.VISIBLE);
            price_old.setText(ConstantValues.CURRENCY_SYMBOL + productDetails.getProductsPrice());
            productBasePrice = Double.parseDouble(productDetails.getDiscountPrice().replace(",", ""));

        } else {
            productDetails.setIsSaleProduct("0");

            price_old.setVisibility(View.GONE);
            product_tag_discount.setVisibility(View.GONE);
            productBasePrice = Double.parseDouble(productDetails.getProductsPrice());
        }

        // Check if the Product is Out of Stock
        if (productDetails.getProductsType() == 0)
            RequestProductStock(productDetails.getProductsId(), null);

        if (productDetails.getProductsType() == 2) {
            productCartBtn.setText(getString(R.string.view_product));
            productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_green));
        }

        // Check if the Product is Newly Added with the help of static method of Helper class
        if (Utilities.checkNewProduct(productDetails.getProductsDateAdded())) {
            product_tag_new.setVisibility(View.VISIBLE);
        } else {
            product_tag_new.setVisibility(View.GONE);
        }

        String description = productDetails.getProductsDescription();
        String styleSheet = "<style> " +
                "body{background:#ffffff; margin:0; padding:0} " +
                "p{color:#757575;} " +
                "img{display:inline; height:auto; max-width:100%;}" +
                "</style>";
        description = description.replace("\\", "");

        product_description_webView.setHorizontalScrollBarEnabled(false);
        product_description_webView.getSettings().setJavaScriptEnabled(true);
        product_description_webView.loadDataWithBaseURL(null, styleSheet + description, "text/html", "utf-8", null);


        // Set Product's Prices
        attributesPrice = 0;


        if (productDetails.getProductsType() == 1) {
            if (attributesList.size() > 0) {
                product_attributes.setVisibility(View.VISIBLE);

                for (int i = 0; i < attributesList.size(); i++) {

                    CartProductAttributes productAttribute = new CartProductAttributes();

                    // Get Name and First Value of current Attribute
                    Option option = attributesList.get(i).getOption();
                    Value value = attributesList.get(i).getValues().get(0);


                    // Add the Attribute's Value Price to the attributePrices
                    String attrPrice = value.getPricePrefix() + value.getPrice();
                    attributesPrice += Double.parseDouble(attrPrice);


                    // Add Value to new List
                    List<Value> valuesList = new ArrayList<>();
                    valuesList.add(value);


                    // Set the Name and Value of Attribute
                    productAttribute.setOption(option);
                    productAttribute.setValues(valuesList);


                    // Add current Attribute to selectedAttributesList
                    selectedAttributesList.add(i, productAttribute);
                }


                // Initialize the ProductAttributesAdapter for RecyclerView
                attributesAdapter = new ProductAttributesAdapter(getContext(), Product_Description.this, attributesList, selectedAttributesList);

                // Set the Adapter and LayoutManager to the RecyclerView
                attribute_recycler.setAdapter(attributesAdapter);
                attribute_recycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                attributesAdapter.notifyDataSetChanged();


                RequestProductStock(productDetails.getProductsId(), attributesAdapter.getAttributeIDs());

            } else {
                product_attributes.setVisibility(View.GONE);
            }
        } else {
            product_attributes.setVisibility(View.GONE);
        }


        productFinalPrice = productBasePrice + attributesPrice;
        price_new.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(productFinalPrice));

        // Check if the User has Liked the Product
        if (productDetails.getIsLiked().equalsIgnoreCase("1")) {
            product_like_btn.setChecked(true);
        } else {
            product_like_btn.setChecked(false);
        }


        // Handle Click event of product_share_btn Button
        product_share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Share Product with the help of static method of Helper class
                Utilities.shareProduct
                        (
                                getContext(),
                                productDetails.getProductsName(),
                                sliderImageView,
                                productDetails.getProductsUrl()
                        );
            }
        });


        // Handle Click event of product_like_btn Button
        product_like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check if the User is Authenticated
                if (ConstantValues.IS_USER_LOGGED_IN) {

                    // Check if the User has Checked the Like Button
                    if (product_like_btn.isChecked()) {
                        productDetails.setIsLiked("1");
                        product_like_btn.setChecked(true);

                        // Request the Server to Like the Product for the User
                        LikeProduct(productDetails.getProductsId(), customerID, getContext(), view);

                    } else {
                        productDetails.setIsLiked("0");
                        product_like_btn.setChecked(false);

                        // Request the Server to Unlike the Product for the User
                        UnlikeProduct(productDetails.getProductsId(), customerID, getContext(), view);
                    }

                    if (wishList != null) {
                        wishList.refresh();
                    }

                } else {
                    // Keep the Like Button Unchecked
                    product_like_btn.setChecked(false);

                    // Navigate to Login Activity
                    Intent i = new Intent(getContext(), Login.class);
                    getContext().startActivity(i);
                    ((MainActivity) getContext()).finish();
                    ((MainActivity) getContext()).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                }
            }
        });


        // Handle Click event of productCartBtn Button
        productCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (productDetails.getProductsType() == 2) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(productDetails.getProductsUrl())));
                } else {
                    if (ConstantValues.IS_INVENTORY_ENABLED && productDetails.getProductsQuantity() <= 0) {

                        Toast.makeText(getContext(), "Product Out of Stock", Toast.LENGTH_SHORT).show();

                    } else {
                        CartProduct cartProduct = new CartProduct();

                        // Set Product's Price, Quantity and selected Attributes Info
                        double finalPrice = productFinalPrice;
                        productDetails.setCustomersBasketQuantity(1);
                        productDetails.setProductsPrice(String.valueOf(productBasePrice));
                        productDetails.setAttributesPrice(String.valueOf(attributesPrice));
                        productDetails.setProductsFinalPrice(String.valueOf(productFinalPrice));
                        productDetails.setTotalPrice(String.valueOf(productFinalPrice));
                        cartProduct.setCustomersBasketProduct(productDetails);
                        cartProduct.setCustomersBasketProductAttributes(selectedAttributesList);


                        // Add the Product to User's Cart with the help of static method of My_Cart class
                        My_Cart.AddCartItem
                                (
                                        cartProduct
                                );


                        // Recreate the OptionsMenu
                        ((MainActivity) getContext()).invalidateOptionsMenu();

                        Snackbar.make(view, getContext().getString(R.string.item_added_to_cart), Snackbar.LENGTH_SHORT).show();
                        if (checkImageView != null)
                            checkImageView.setVisibility(View.VISIBLE);

                    }
                }

                /*if (productDetails.getProductsQuantity() > 0) {
                    
                    CartProduct cartProduct = new CartProduct();
    
                    // Set Product's Price, Quantity and selected Attributes Info
                    productDetails.setCustomersBasketQuantity(1);
                    productDetails.setProductsPrice(String.valueOf(productBasePrice));
                    productDetails.setAttributesPrice(String.valueOf(attributesPrice));
                    productDetails.setProductsFinalPrice(String.valueOf(productFinalPrice));
                    productDetails.setTotalPrice(String.valueOf(productFinalPrice));
                    cartProduct.setCustomersBasketProduct(productDetails);
                    cartProduct.setCustomersBasketProductAttributes(selectedAttributesList);
    
    
                    // Add the Product to User's Cart with the help of static method of My_Cart class
                    My_Cart.AddCartItem
                            (
                                    cartProduct
                            );
    
    
                    // Recreate the OptionsMenu
                    ((MainActivity) getContext()).invalidateOptionsMenu();
    
                    Snackbar.make(view, getContext().getString(R.string.item_added_to_cart), Snackbar.LENGTH_SHORT).show();
                }*/

            }
        });
    }


    //*********** Update Product's final Price based on selected Attributes ********//

    public void updateProductPrice() {

        RequestProductStock(productDetails.getProductsId(), attributesAdapter.getAttributeIDs());

        attributesPrice = 0;

        // Get Attribute's Prices List from ProductAttributesAdapter
        String[] attributePrices = attributesAdapter.getAttributePrices();

        double attributesTotalPrice = 0.0;

        for (int i = 0; i < attributePrices.length; i++) {
            // Get the Price of Attribute at given Position in attributePrices array
            double price = Double.parseDouble(attributePrices[i]);

            attributesTotalPrice += price;
        }

        attributesPrice = attributesTotalPrice;


        // Check if product from flash sale
        // Calculate and Set Product's total Price
        productFinalPrice = productBasePrice + attributesPrice;
        price_new.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(productFinalPrice));

    }


    //*********** Update Product's Stock ********//

    public void updateProductStock(String stock) {

        productDetails.setProductsQuantity(Integer.parseInt(stock));
        productDetails.setProductsDefaultStock(Integer.parseInt(stock));

        // Check if the Product is Out of Stock
        if (ConstantValues.IS_INVENTORY_ENABLED && stock.equalsIgnoreCase("0")) {
            product_stock.setText(getString(R.string.outOfStock));
            productCartBtn.setText(getString(R.string.outOfStock));
            product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentRed));
            productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_red));

        } else {
            product_stock.setText(getString(R.string.in_stock));
            productCartBtn.setText(getString(R.string.addToCart));
            product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentBlue));
            productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_accent));
        }

        // Check if product from flash sale

        if (productDetails.getFlashPrice() != null) {
            if (!productDetails.getFlashPrice().isEmpty()) {
                product_like_btn.setVisibility(View.GONE);

                price_new.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(productDetails.getFlashPrice())));
                long serverTime = Long.parseLong(productDetails.getServerTime()) * 1000L;
                long startDate = Long.parseLong(productDetails.getFlashStartDate()) * 1000L;
                productBasePrice = Double.parseDouble(productDetails.getFlashPrice());
                productFinalPrice = productBasePrice + attributesPrice;
                if (startDate > serverTime) {
                    productCartBtn.setEnabled(false);
                    productCartBtn.setBackgroundResource(R.drawable.rounded_corners_button_red);

                }
            }

        }
    }


    //*********** Setup the ImageSlider with the given List of Product Images ********//

    private void ImageSlider(String itemThumbnail, List<Image> itemImages) {

        // Initialize new HashMap<ImageName, ImagePath>
        final HashMap<String, String> slider_covers = new HashMap<>();
        // Initialize new Array for Image's URL
        final String[] images = new String[itemImages.size()];


        if (itemImages.size() > 0) {
            for (int i = 0; i < itemImages.size(); i++) {
                // Get Image's URL at given Position from itemImages List
                images[i] = itemImages.get(i).getImage();
            }
        }


        // Put Image's Name and URL to the HashMap slider_covers
        if (itemThumbnail == null || itemThumbnail.equalsIgnoreCase("")) {
            slider_covers.put("a", "" + R.drawable.placeholder);

        } else if (images.length == 0) {
            slider_covers.put("a", ConstantValues.ECOMMERCE_URL + itemThumbnail);

        } else {
            slider_covers.put("a", ConstantValues.ECOMMERCE_URL + itemThumbnail);

            for (int i = 0; i < images.length; i++) {
                slider_covers.put("b" + i, ConstantValues.ECOMMERCE_URL + images[i]);
            }
        }


        for (String name : slider_covers.keySet()) {

            // Initialize DefaultSliderView
            DefaultSliderView defaultSliderView = new DefaultSliderView(getContext()) {
                @Override
                public View getView() {
                    View v = LayoutInflater.from(getContext()).inflate(com.daimajia.slider.library.R.layout.render_type_default, null);

                    // Get daimajia_slider_image ImageView of DefaultSliderView
                    sliderImageView = (ImageView) v.findViewById(com.daimajia.slider.library.R.id.daimajia_slider_image);

                    // Set ScaleType of ImageView
                    sliderImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    bindEventAndShow(v, sliderImageView);

                    return v;
                }
            };

            // Set Attributes(Name, Placeholder, Image, Type etc) to DefaultSliderView
            defaultSliderView
                    .description(name)
                    .empty(R.drawable.placeholder)
                    .image(slider_covers.get(name))
                    .setScaleType(DefaultSliderView.ScaleType.FitCenterCrop);

            // Add DefaultSliderView to the SliderLayout
            sliderLayout.addSlider(defaultSliderView);
        }

        // Set PresetTransformer type of the SliderLayout
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);


        // Check if the size of Images in the Slider is less than 2
        if (slider_covers.size() < 2) {
            // Disable PagerTransformer
            sliderLayout.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });

            // Hide Slider PagerIndicator
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);

        } else {
            // Set custom PagerIndicator to the SliderLayout
            sliderLayout.setCustomIndicator(pagerIndicator);
            // Make PagerIndicator Visible
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        }
    }


    //*********** Request Product Details from the Server based on productID ********//

    public void RequestProductDetail(final int productID) {

        dialogLoader.showProgressDialog();


        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(0);
        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID);
        getAllProducts.setProductsId(String.valueOf(productID));
        getAllProducts.setCurrencyCode(ConstantValues.CURRENCY_CODE);

        Call<ProductData> call = APIClient.getInstance()
                .getAllProducts
                        (
                                getAllProducts
                        );

        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {

                dialogLoader.hideProgressDialog();

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Product's Details has been returned
                        setProductDetails(response.body().getProductData().get(0));

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();

                    } else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(App.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                Toast.makeText(App.getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
            }
        });
    }


    //*********** Request Product's Stock from the Server based on productID and Attributes ********//

    public void RequestProductStock(int productID, List<String> attributes) {

        dialogLoader.showProgressDialog();

        GetStock getStock = new GetStock();
        getStock.setProductsId(String.valueOf(productID));
        getStock.setAttributes(attributes);


        Call<ProductStock> call = APIClient.getInstance()
                .getProductStock
                        (
                                getStock
                        );

        call.enqueue(new Callback<ProductStock>() {
            @Override
            public void onResponse(Call<ProductStock> call, retrofit2.Response<ProductStock> response) {

                dialogLoader.hideProgressDialog();

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        updateProductStock(response.body().getStock());

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();

                    } else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(App.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductStock> call, Throwable t) {
                Toast.makeText(App.getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
            }
        });
    }


    //*********** Request the Server to Like the Product based on productID and customerID ********//

    public static void LikeProduct(int productID, String customerID, final Context context, final View view) {

        Call<ProductData> call = APIClient.getInstance()
                .likeProduct
                        (
                                productID,
                                customerID
                        );

        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {
                // Check if the Response is successful
                if (response.isSuccessful()) {

                    // Check the Success status
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Product has been Liked. Show the message to User
                        //Snackbar.make(view, context.getString(R.string.added_to_favourites), Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(context, context.getString(R.string.added_to_favourites), Toast.LENGTH_SHORT).show();
                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        //Snackbar.make(view, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        // Unable to get Success status
                        //Snackbar.make(view, context.getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(context, context.getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                Toast.makeText(context, "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
            }
        });
    }


    //*********** Request the Server to Unlike the Product based on productID and customerID ********//

    public static void UnlikeProduct(int productID, String customerID, final Context context, final View view) {

        Call<ProductData> call = APIClient.getInstance()
                .unlikeProduct
                        (
                                productID,
                                customerID
                        );

        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {
                // Check if the Response is successful
                if (response.isSuccessful()) {

                    // Check the Success status
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Product has been Disliked. Show the message to User
                        //Snackbar.make(view, context.getString(R.string.removed_from_favourites), Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(context, context.getString(R.string.removed_from_favourites), Toast.LENGTH_SHORT).show();
                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        //Snackbar.make(view, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        // Unable to get Success status
                        //Snackbar.make(view, context.getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(context, context.getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                Toast.makeText(context, "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    //*********** Setup the ImageSlider with the given List of Product Images ********//

    public void showRatingsAndReviewsOfProduct() {

        int rating_1_count = productDetails.getOne_ratio();
        int rating_2_count = productDetails.getTwo_ratio();
        int rating_3_count = productDetails.getThree_ratio();
        int rating_4_count = productDetails.getFour_ratio();
        int rating_5_count = productDetails.getFive_ratio();

        final Dialog reviews_ratings_dialog = new Dialog(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        reviews_ratings_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reviews_ratings_dialog.setCancelable(true);
        reviews_ratings_dialog.setContentView(R.layout.dialog_product_rating_reviews);
        reviews_ratings_dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        // Bind Dialog Views
        TextView average_rating = (TextView) reviews_ratings_dialog.findViewById(R.id.average_rating);
        TextView total_rating_count = (TextView) reviews_ratings_dialog.findViewById(R.id.total_rating_count);
        ProgressBar rating_progress_5 = (ProgressBar) reviews_ratings_dialog.findViewById(R.id.rating_progress_5);
        ProgressBar rating_progress_4 = (ProgressBar) reviews_ratings_dialog.findViewById(R.id.rating_progress_4);
        ProgressBar rating_progress_3 = (ProgressBar) reviews_ratings_dialog.findViewById(R.id.rating_progress_3);
        ProgressBar rating_progress_2 = (ProgressBar) reviews_ratings_dialog.findViewById(R.id.rating_progress_2);
        ProgressBar rating_progress_1 = (ProgressBar) reviews_ratings_dialog.findViewById(R.id.rating_progress_1);
        Button rate_product_button = (Button) reviews_ratings_dialog.findViewById(R.id.rate_product);
        ImageButton dialog_back_button = (ImageButton) reviews_ratings_dialog.findViewById(R.id.dialog_button);
        RecyclerView reviews_list_recycler = (RecyclerView) reviews_ratings_dialog.findViewById(R.id.reviews_list_recycler);

        reviews_list_recycler.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(reviews_list_recycler, false);

        average_rating.setText("" + productDetails.getRating());
        total_rating_count.setText(String.valueOf(productDetails.getTotal_user_rated()));

        /*rate_product_button.setVisibility(productDetails.isReviewsAllowed()? View.VISIBLE : View.GONE);

        rating_progress_1.setMax(productDetails.getRatingCount());
        rating_progress_2.setMax(productDetails.getRatingCount());
        rating_progress_3.setMax(productDetails.getRatingCount());
        rating_progress_4.setMax(productDetails.getRatingCount());
        rating_progress_5.setMax(productDetails.getRatingCount());

        for (int i=0;  i<productReviews.size();  i++) {
            if (productReviews.get(i).getRating() == 1)
                rating_1_count += 1;
            else if (productReviews.get(i).getRating() == 2)
                rating_2_count += 1;
            else if (productReviews.get(i).getRating() == 3)
                rating_3_count += 1;
            else if (productReviews.get(i).getRating() == 4)
                rating_4_count += 1;
            else if (productReviews.get(i).getRating() == 5)
                rating_5_count += 1;
        }*/

        rating_progress_1.setProgress(rating_1_count);
        rating_progress_2.setProgress(rating_2_count);
        rating_progress_3.setProgress(rating_3_count);
        rating_progress_4.setProgress(rating_4_count);
        rating_progress_5.setProgress(rating_5_count);

        dialogLoader = new DialogLoader(getContext());
        productReviews = new ArrayList<>();
        // Initialize the ReviewsAdapter for RecyclerView
        ProductReviewsAdapter reviewsAdapter = new ProductReviewsAdapter(getContext(), productReviews);

        // Set the Adapter and LayoutManager to the RecyclerView
        reviews_list_recycler.setAdapter(reviewsAdapter);
        reviews_list_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        reviews_list_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        getProductReviews("" + productDetails.getProductsId(), reviewsAdapter);


        rate_product_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConstantValues.IS_USER_LOGGED_IN) {
                    showRateProductDialog();
                } else {
                    getContext().startActivity(new Intent(getContext(), Login.class));
                    ((MainActivity) getContext()).finish();
                    ((MainActivity) getContext()).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                }
            }
        });


        dialog_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviews_ratings_dialog.dismiss();
            }
        });


        reviews_ratings_dialog.show();
    }

    //*********** Setup the ImageSlider with the given List of Product Images ********//

    public void showRateProductDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_rate_product, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        final ColorRatingBar dialog_rating_bar = dialogView.findViewById(R.id.dialog_rating_bar);
        final EditText dialog_author_name = dialogView.findViewById(R.id.dialog_author_name);
        final EditText dialog_author_message = dialogView.findViewById(R.id.dialog_author_message);
        final Button dialog_button = dialogView.findViewById(R.id.dialog_button);

        final AlertDialog rateProductDialog = dialog.create();

        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateInputs.isValidName(dialog_author_name.getText().toString())) {
                    if (!"".equalsIgnoreCase(dialog_author_message.getText().toString())) {

                        rateProductDialog.dismiss();

                        RequestGiveRating(
                                String.valueOf(productDetails.getProductsId()),
                                String.valueOf(customerID),
                                dialog_author_name.getText().toString().trim(),
                                String.valueOf((int) dialog_rating_bar.getRating()),
                                String.valueOf(productDetails.getVendors_id()),
                                String.valueOf(productDetails.getLanguageId()),
                                dialog_author_message.getText().toString().trim()
                        );
                           /* getNonceForProductRating
                                    (
                                            String.valueOf(productDetails.getId()),
                                            String.valueOf(dialog_rating_bar.getRating()),
                                            dialog_author_name.getText().toString().trim(),
                                            dialog_author_email.getText().toString().trim(),
                                            dialog_author_message.getText().toString().trim()
                                    );*/

                    } else {
                        dialog_author_message.setError(getContext().getString(R.string.enter_message));
                    }

                } else {
                    dialog_author_name.setError(getContext().getString(R.string.enter_name));
                }
            }
        });

        rateProductDialog.show();
    }


    //*********** Proceed User Registration Request ********//

    private void getProductReviews(final String productID, final ProductReviewsAdapter adapter) {

        dialogLoader.showProgressDialog();

        Call<GetRatings> call = APIClient.getInstance()
                .getProductReviews
                        (
                                productID,
                                "" + ConstantValues.LANGUAGE_ID
                        );

        call.enqueue(new Callback<GetRatings>() {
            @Override
            public void onResponse(Call<GetRatings> call, retrofit2.Response<GetRatings> response) {

                dialogLoader.hideProgressDialog();

                String str = response.raw().request().url().toString();

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    String strGson = new Gson().toJson(response.body().getData()).toString();

                    productReviews.addAll(response.body().getData());
                    int size = productReviews.size();
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(App.getContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetRatings> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(App.getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void RequestGiveRating(String products_id, String customers_id, String customers_nam, String reviews_rating,
                                   String vendors_id, String languages_id, String reviews_text) {

        Map<String, String> map = new HashMap<>();
        map.put("products_id", products_id);
        map.put("customers_id", customers_id);
        map.put("customers_name", customers_nam);
        map.put("reviews_rating", reviews_rating);
        //map.put("vendors_id",vendors_id);
        map.put("languages_id", languages_id);
        map.put("reviews_text", reviews_text);


        Call<GiveRating> call = APIClient.getInstance().giveRating(map);

        call.enqueue(new Callback<GiveRating>() {
            @Override
            public void onResponse(Call<GiveRating> call, Response<GiveRating> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GiveRating> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure: " + t, Toast.LENGTH_SHORT).show();
            }
        });


    }

}

