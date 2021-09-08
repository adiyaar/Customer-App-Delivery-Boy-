package com.themescoder.androidstore.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.activities.Login;
import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.adapters.CartItemsAdapter;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.customs.DialogLoader;
import com.themescoder.androidstore.databases.User_Cart_DB;
import com.themescoder.androidstore.models.cart_model.CartProduct;
import com.themescoder.androidstore.models.cart_model.CartProductAttributes;
import com.themescoder.androidstore.models.product_model.GetAllProducts;
import com.themescoder.androidstore.models.product_model.GetStock;
import com.themescoder.androidstore.models.product_model.Option;
import com.themescoder.androidstore.models.product_model.ProductData;
import com.themescoder.androidstore.models.product_model.ProductDetails;
import com.themescoder.androidstore.models.product_model.ProductStock;
import com.themescoder.androidstore.models.product_model.Value;
import com.themescoder.androidstore.network.APIClient;
import com.themescoder.androidstore.utils.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import am.appwise.components.ni.NoInternetDialog;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class My_Cart extends Fragment {

    public static final int MYCART_FRAGMENT_CODE = 6543;
    public TextView cart_total_price;
    String customerID;
    public RecyclerView cart_items_recycler;
    LinearLayout cart_view, cart_view_empty;
    Button cart_checkout_btn, continue_shopping_btn;
    NestedScrollView mainRvLayout;
    public TextView cart_item_subtotal_price, cart_item_discount_price, cart_item_total_price;

    CartItemsAdapter cartItemsAdapter;
    User_Cart_DB user_cart_db = new User_Cart_DB();

    List<CartProduct> cartItemsList = new ArrayList<>();
    List<CartProduct> finalCartItemsList = new ArrayList<>();
    List<CartProduct> allNEwCartItemList = new ArrayList<>();
    List<CartProduct> newCartItemsList = new ArrayList<>();
    List<ProductDetails> cartProducts = new ArrayList<>();
    List<String> stocks = new ArrayList<>();
    DialogLoader dialogLoader;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.actionCart));
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_cart, container, false);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        setHasOptionsMenu(true);
        dialogLoader = new DialogLoader(getContext());

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        ((MainActivity) getActivity()).toggleNavigaiton(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.actionCart));


        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        // Get the List of Cart Items from the Local Databases User_Cart_DB
        finalCartItemsList = cartItemsList = user_cart_db.getCartItems();


        // Binding Layout Views
        cart_view = (LinearLayout) rootView.findViewById(R.id.cart_view);
        cart_total_price = (TextView) rootView.findViewById(R.id.cart_total_price);
        cart_checkout_btn = (Button) rootView.findViewById(R.id.cart_checkout_btn);
        cart_items_recycler = (RecyclerView) rootView.findViewById(R.id.cart_items_recycler);
        cart_view_empty = (LinearLayout) rootView.findViewById(R.id.cart_view_empty);
        continue_shopping_btn = (Button) rootView.findViewById(R.id.continue_shopping_btn);
        mainRvLayout = rootView.findViewById(R.id.mainRvLayout);
        cart_item_subtotal_price = rootView.findViewById(R.id.cart_item_subtotal_price);
        cart_item_total_price = rootView.findViewById(R.id.cart_item_total_price);
        cart_item_discount_price = rootView.findViewById(R.id.cart_item_discount_price);

        // Change the Visibility of cart_view and cart_view_empty LinearLayout based on CartItemsList's Size
        if (cartItemsList.size() != 0) {
            cart_view.setVisibility(View.VISIBLE);
            cart_view_empty.setVisibility(View.GONE);
        } else {
            cart_view.setVisibility(View.GONE);
            cart_view_empty.setVisibility(View.VISIBLE);
        }


        // Initialize the AddressListAdapter for RecyclerView
        cartItemsAdapter = new CartItemsAdapter(getContext(), finalCartItemsList, com.themescoder.androidstore.fragment.My_Cart.this);

        // Set the Adapter and LayoutManager to the RecyclerView
        cart_items_recycler.setAdapter(cartItemsAdapter);
        cart_items_recycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));


        // Show the Cart's Total Price with the help of static method of CartItemsAdapter
        cartItemsAdapter.setCartTotal();


        cartItemsAdapter.notifyDataSetChanged();

        //getCartProductsFromServer();


        // Handle Click event of continue_shopping_btn Button
        continue_shopping_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putBoolean("isSubFragment", false);

                // Navigate to Products Fragment
                Fragment fragment = new Products();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionCart)).commit();

            }
        });

        // Handle Click event of cart_checkout_btn Button
        cart_checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConstantValues.MAINTENANCE_MODE != null) {
                    if (ConstantValues.MAINTENANCE_MODE.equalsIgnoreCase("Maintenance"))
                        showDialog(ConstantValues.MAINTENANCE_TEXT);
                    else {
                        // Check if cartItemsList isn't empty
                        if (cartItemsList.size() != 0) {

                            // Check if User is Logged-In
                            if (ConstantValues.IS_USER_LOGGED_IN) {

                                //new CheckStockTask().execute();
                                Fragment fragment = new Shipping_Address(com.themescoder.androidstore.fragment.My_Cart.this);
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction().add(R.id.main_fragment, fragment)
                                        .addToBackStack(getString(R.string.actionCart)).commit();
                            } else {
                                // Navigate to Login Activity
                                Intent i = new Intent(getContext(), Login.class);
                                getContext().startActivity(i);
                                ((MainActivity) getContext()).finish();
                                ((MainActivity) getContext()).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                            }
                        }

                    }
                } else {
                    Toast.makeText(getContext(), "Maintenance Mode is Null", Toast.LENGTH_SHORT).show();
                }
            }
        });


        new MyTask().execute();


        return rootView;
    }

    private void getCartProductsFromServer() {
        dialogLoader.showProgressDialog();
        String customerID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        newCartItemsList = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < cartItemsList.size(); i++) {
            ids.add(cartItemsList.get(i).getCustomersBasketProduct().getProductsId());
        }
        GetAllProducts postObject = new GetAllProducts();
        postObject.setLanguageId(ConstantValues.LANGUAGE_ID);
        postObject.setCurrencyCode(ConstantValues.CURRENCY_CODE);
        postObject.setCustomersId(customerID);
        postObject.setMultipleProductsId(ids);
        APIClient.getInstance().getAllProducts(postObject).enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, Response<ProductData> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        refillFinalCArtITems(response.body().getProductData());
                        dialogLoader.hideProgressDialog();
                        cartItemsAdapter = new CartItemsAdapter(getContext(), allNEwCartItemList, com.themescoder.androidstore.fragment.My_Cart.this);

                        cart_items_recycler.setAdapter(cartItemsAdapter);
                        cart_items_recycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        cartItemsAdapter.setCartTotal();

                        cartItemsAdapter.notifyDataSetChanged();

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        dialogLoader.hideProgressDialog();
                        Toast.makeText(App.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        dialogLoader.hideProgressDialog();
                        Toast.makeText(App.getContext(), "Unexpected Response.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialogLoader.hideProgressDialog();
                    Toast.makeText(App.getContext(), response.message() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(App.getContext(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refillFinalCArtITems(List<ProductDetails> productData) {
        allNEwCartItemList.clear();
        for (int i = 0; i < cartItemsList.size(); i++) {
            ProductDetails product = getProductDetails(productData, cartItemsList.get(i).getCustomersBasketProduct().getProductsId());
            if (product != null) {
                CartProduct cartProduct = cartItemsList.get(i);
                List<CartProductAttributes> selectedAttributesList = new ArrayList<>();
                double productBasePrice, productFinalPrice = 0.0, attributesPrice = 0;
                // Check Discount on Product with the help of static method of Helper class
                final String discount = Utilities.checkDiscount(product.getProductsPrice(), product.getDiscountPrice());

                // Get Product's Price based on Discount
                if (discount != null) {
                    product.setIsSaleProduct("1");
                    productBasePrice = Double.parseDouble(product.getDiscountPrice());
                } else {
                    product.setIsSaleProduct("0");
                    productBasePrice = Double.parseDouble(product.getProductsPrice());
                }

                // Get Default Attributes from AttributesList
                for (int j = 0; j < product.getAttributes().size(); j++) {

                    CartProductAttributes productAttribute = new CartProductAttributes();

                    // Get Name and First Value of current Attribute
                    Option option = product.getAttributes().get(j).getOption();
                    Value value = product.getAttributes().get(j).getValues().get(0);


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
                    selectedAttributesList.add(j, productAttribute);
                }

                if (product.getFlashPrice() != null) {
                    product.setIsSaleProduct("1");
                    productFinalPrice = Double.parseDouble(product.getFlashPrice()) + attributesPrice;
                } else {
                    product.setIsSaleProduct("0");
                    // Add Attributes Price to Product's Final Price
                    productFinalPrice = productBasePrice + attributesPrice;
                }

                product.setCustomersBasketQuantity(cartProduct.getCustomersBasketProduct().getCustomersBasketQuantity());
                product.setProductsPrice(String.valueOf(productBasePrice));
                product.setAttributesPrice(String.valueOf(attributesPrice));
                product.setProductsFinalPrice(String.valueOf(productFinalPrice));

                int quantity = product.getProductsDefaultStock();
                product.setProductsQuantity(product.getProductsDefaultStock());

                // Set Product's OrderProductCategory Info
                String[] categoryIDs = new String[product.getCategories().size()];
                String[] categoryNames = new String[product.getCategories().size()];
                if (product.getCategories().size() > 0) {

                    for (int k = 0; k < product.getCategories().size(); k++) {
                        categoryIDs[k] = String.valueOf(product.getCategories().get(k).getCategoriesId());
                        categoryNames[k] = product.getCategories().get(k).getCategoriesName();
                    }

                    product.setCategoryIDs(TextUtils.join(",", categoryIDs));
                    product.setCategoryNames(TextUtils.join(",", categoryNames));
                } else {
                    product.setCategoryIDs("");
                    product.setCategoryNames("");
                }
                // product.setCategoryNames(product.getCategoryNames());

                product.setTotalPrice(String.valueOf(productFinalPrice * cartProduct.getCustomersBasketProduct().getCustomersBasketQuantity()));

                cartProduct.setCustomersBasketProduct(product);
                cartProduct.setCustomersBasketProductAttributes(selectedAttributesList);
                allNEwCartItemList.add(cartProduct);
            } else {
                DeleteCartItem(cartItemsList.get(i).getCustomersBasketId());
            }
        }
    }

    private ProductDetails getProductDetails(List<ProductDetails> productData, int productsId) {
        for (int i = 0; i < productData.size(); i++) {
            if (productData.get(i).getProductsId() == productsId) {
                return productData.get(i);
            }
        }
        return null;
    }


    //*********** Change the Layout View of My_Cart Fragment based on Cart Items ********//

    public void updateCartView(int cartListSize) {

        // Check if Cart has some Items
        if (cartListSize != 0) {
            cart_view.setVisibility(View.VISIBLE);
            cart_view_empty.setVisibility(View.GONE);
        } else {
            cart_view.setVisibility(View.GONE);
            cart_view_empty.setVisibility(View.VISIBLE);
        }
    }


    //*********** Used to Share the App with Others ********//

    private void showDialog(String str) {

        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_maintenance, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        final TextView dialog_message = (TextView) dialogView.findViewById(R.id.maintenanceText);
        final Button dialog_button_positive = (Button) dialogView.findViewById(R.id.dialog_button);

        dialog_message.setText(str);

        final android.app.AlertDialog alertDialog = dialog.create();
        alertDialog.show();


        dialog_button_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }


    //*********** Static method to Add the given Item to User's Cart ********//

    public static void AddCartItem(CartProduct cartProduct) {
        User_Cart_DB user_cart_db = new User_Cart_DB();
        user_cart_db.addCartItem
                (
                        cartProduct
                );
    }


    //*********** Static method to Get the Cart Product based on product_id ********//

    public static CartProduct GetCartProduct(int product_id) {
        User_Cart_DB user_cart_db = new User_Cart_DB();

        CartProduct cartProduct = user_cart_db.getCartProduct
                (
                        product_id
                );

        return cartProduct;
    }

    public void refreshCartProducts() {
        cartItemsList = user_cart_db.getCartItems();
        cartItemsAdapter.notifyDataSetChanged();
    }

    //*********** Static method to Update the given Item in User's Cart ********//

    public static void UpdateCartItem(CartProduct cartProduct) {
        User_Cart_DB user_cart_db = new User_Cart_DB();
        user_cart_db.updateCartItem
                (
                        cartProduct
                );
    }


    //*********** Static method to Delete the given Item from User's Cart ********//

    public static void DeleteCartItem(int cart_item_id) {
        User_Cart_DB user_cart_db = new User_Cart_DB();
        user_cart_db.deleteCartItem
                (
                        cart_item_id
                );
    }


    //*********** Static method to Clear User's Cart ********//

    public static void ClearCart() {
        User_Cart_DB user_cart_db = new User_Cart_DB();
        user_cart_db.clearCart();
    }


    //*********** Static method to get total number of Items in User's Cart ********//

    public static int getCartSize() {
        int cartSize = 0;

        User_Cart_DB user_cart_db = new User_Cart_DB();
        List<CartProduct> cartItems = user_cart_db.getCartItems();

        for (int i = 0; i < cartItems.size(); i++) {
            cartSize += cartItems.get(i).getCustomersBasketProduct().getCustomersBasketQuantity();
        }

        return cartSize;
    }


    //*********** Static method to check if the given Product is already in User's Cart ********//

    public static boolean checkCartHasProduct(int cart_item_id) {
        User_Cart_DB user_cart_db = new User_Cart_DB();
        return user_cart_db.getCartItemsIDs().contains(cart_item_id);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Hide Cart Icon in the Toolbar
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);
        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        cartItem.setVisible(false);
        searchItem.setVisible(true);
    }

    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            dialogLoader.showProgressDialog();
            cartProducts.clear();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            for (int i = 0; i < cartItemsList.size(); i++) {
                try {
                    RequestProductDetails(i, cartItemsList.get(i).getCustomersBasketProduct().getProductsId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialogLoader.hideProgressDialog();
            for (int i = 0; i < finalCartItemsList.size(); i++) {
                final String discount = Utilities.checkDiscount(cartProducts.get(i).getProductsPrice(), cartProducts.get(i).getDiscountPrice());
                finalCartItemsList.get(i).getCustomersBasketProduct().setProductsName(cartProducts.get(i).getProductsName());
                //finalCartItemsList.get(i).getCustomersBasketProduct().setCategoryNames(cartProducts.get(i).getCategoryNames());
                //finalCartItemsList.get(i).getCustomersBasketProduct().setCustomersBasketQuantity(cartProducts.get(i).getCustomersBasketQuantity());
                if (discount == null)
                    finalCartItemsList.get(i).getCustomersBasketProduct().setProductsPrice(cartProducts.get(i).getProductsPrice());
                else
                    finalCartItemsList.get(i).getCustomersBasketProduct().setProductsPrice(cartProducts.get(i).getDiscountPrice());
                //finalCartItemsList.get(i).getCustomersBasketProduct().setTotalPrice(cartProducts.get(i).getTotalPrice());
                //finalCartItemsList.get(i).getCustomersBasketProduct().setProductsFinalPrice(cartProducts.get(i).getProductsFinalPrice());
                //finalCartItemsList.get(i).getCustomersBasketProduct().setProductsQuantity(cartProducts.get(i).getProductsQuantity());

            }
            cartItemsAdapter.notifyDataSetChanged();
        }
    }


    public void RequestProductDetails(final int position, final int products_id) throws IOException {

        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(0);
        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID);
        getAllProducts.setProductsId(String.valueOf(products_id));
        getAllProducts.setCurrencyCode(ConstantValues.CURRENCY_CODE);


        Call<ProductData> call = APIClient.getInstance()
                .getAllProducts
                        (
                                getAllProducts
                        );
        Response<ProductData> response = call.execute();

        if (response.isSuccessful()) {

            if (response.body().getSuccess().equalsIgnoreCase("1")) {
                cartProducts.add(response.body().getProductData().get(0));
            } else {
                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
            }
            dialogLoader.hideProgressDialog();

        } else {
            dialogLoader.hideProgressDialog();
            Toast.makeText(getContext(), "Coud not get response.", Toast.LENGTH_SHORT).show();
        }

    }


    private void requestProductStock2(int productID, List<String> attributes, int position) {
        GetStock getStockParams = new GetStock();
        getStockParams.setProductsId(productID + "");
        getStockParams.setAttributes(attributes);
        Call<ProductStock> call = APIClient.getInstance().getProductStock(getStockParams);
        try {
            Response<ProductStock> response = call.execute();
            if (response.isSuccessful()) {
                stocks.add(position, response.body().getStock());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getSelectedAttributesIds(List<CartProductAttributes> selectedAttributes) {
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < selectedAttributes.size(); i++) {
            ids.add(String.valueOf(selectedAttributes.get(i).getValues().get(0).getProducts_attributes_id()));
        }
        return ids;
    }

    private boolean isAllStockValid(List<String> stocks) {
        for (int i = 0; i < stocks.size(); i++) {
            if (Integer.parseInt(stocks.get(i)) <= 0)
                return false;
        }
        return true;
    }

    public class CheckStockTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogLoader.showProgressDialog();
            stocks.clear();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < cartItemsList.size(); i++) {
                requestProductStock2(cartItemsList.get(i).getCustomersBasketProduct().getProductsId(), getSelectedAttributesIds(cartItemsList.get(i).getCustomersBasketProductAttributes()), i);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialogLoader.hideProgressDialog();
            if (isAllStockValid(stocks)) {
                Fragment fragment = new Shipping_Address(com.themescoder.androidstore.fragment.My_Cart.this);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.main_fragment, fragment)
                        .addToBackStack(getString(R.string.actionCart)).commit();
            } else {
                Toast.makeText(getContext(), "Your Product in the cart is out of stock.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode==MYCART_FRAGMENT_CODE){
                String porductID = data.getStringExtra("porductID");

                Toast.makeText(getContext(), porductID, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showCartError(String productId) {
        for (int i=0; i<finalCartItemsList.size(); i++){
            if ((finalCartItemsList.get(i).getCustomersBasketProduct().getProductsId() + "").equals(productId)){
                LinearLayout linearLayout = Objects.requireNonNull(cart_items_recycler.findViewHolderForAdapterPosition(i)).itemView.findViewById(R.id.mainCartItemLayout);
                if (linearLayout != null){
                    linearLayout.setBackground(getResources().getDrawable(R.drawable.red_outline));
                }
                cartItemsAdapter.notifyDataSetChanged();
                break;
            }
        }
    }
}

