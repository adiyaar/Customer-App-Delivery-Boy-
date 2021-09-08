package com.themescoder.androidstore.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.R;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.adapters.NewsListAdapter;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.customs.DialogLoader;
import com.themescoder.androidstore.models.news_model.all_news.NewsData;
import com.themescoder.androidstore.models.news_model.all_news.NewsDetails;
import com.themescoder.androidstore.network.APIClient;

import am.appwise.components.ni.NoInternetDialog;
import com.themescoder.androidstore.customs.DividerItemDecoration;
import com.themescoder.androidstore.customs.EndlessRecyclerViewScroll;
import retrofit2.Call;
import retrofit2.Callback;


public class News_of_Category extends Fragment {

    View rootView;

    int pageNo = 0;
    Boolean isHeaderVisible = false;

    String newsCategoryID;

    ProgressBar progressBar;
    TextView emptyText, headerText;
    RecyclerView news_recycler;

    NewsListAdapter newsAdapter;
    List<NewsDetails> newsList;

    GridLayoutManager gridLayoutManager;
    DialogLoader dialogLoader;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.news_all, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
       // MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        ((MainActivity)getActivity()).toggleNavigaiton(false);
        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        // Set the Title of Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getArguments().getString("NewsCategoryName", getString(R.string.actionNews)));
        dialogLoader = new DialogLoader(getContext());

        // Get CategoryID from Bundle arguments
        newsCategoryID = getArguments().getString("NewsCategoryID");

        if (getArguments() != null) {
            if (getArguments().containsKey("isHeaderVisible")) {
                isHeaderVisible = getArguments().getBoolean("isHeaderVisible");
            }
        }


        // Binding Layout Views
        headerText = (TextView) rootView.findViewById(R.id.news_header);
        emptyText = (TextView) rootView.findViewById(R.id.empty_record_text);
        progressBar =  rootView.findViewById(R.id.loading_bar);
        news_recycler = (RecyclerView) rootView.findViewById(R.id.news_recycler);


        // Hide some of the Views
        emptyText.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        // Check if Header must be Invisible or not
        if (isHeaderVisible) {
            headerText.setText(getString(R.string.news_all));

        } else {
            // Hide the Header of CategoriesList
            headerText.setVisibility(View.GONE);
        }


        // Initialize ProductList
        newsList = new ArrayList<>();

        // Request for Products based on PageNo.
        RequestAllNews(pageNo);


        gridLayoutManager = new GridLayoutManager(getContext(), 1);

        // Initialize the ProductAdapter for RecyclerView
        newsAdapter = new NewsListAdapter(getContext(), newsList);

        // Set the Adapter and LayoutManager to the RecyclerView
        news_recycler.setAdapter(newsAdapter);
        news_recycler.setLayoutManager(gridLayoutManager);
        news_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));



        // Handle the Scroll event of Product's RecyclerView
        news_recycler.addOnScrollListener(new EndlessRecyclerViewScroll() {
            // Override abstract method onLoadMore() of EndlessRecyclerViewScroll class
            @Override
            public void onLoadMore(int current_page) {
                progressBar.setVisibility(View.VISIBLE);
                // Execute AsyncTask LoadMoreTask to Load More Products from Server
                new LoadMoreTask(current_page).execute();
            }
        });


        newsAdapter.notifyDataSetChanged();


        return rootView;
    }



    //*********** Add News returned to the NewsList ********//

    private void addNews(NewsData newsData) {

        for (int i = 0; i < newsData.getNewsData().size(); i++) {
            newsList.add(newsData.getNewsData().get(i));
        }

        newsAdapter.notifyDataSetChanged();


        // Change the Visibility of emptyRecord Text based on ProductList's Size
        if (newsAdapter.getItemCount() == 0) {
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.GONE);
        }
    }



    //*********** Request News from the Server based on PageNo. ********//

    public void RequestAllNews(int pageNumber) {
        dialogLoader.showProgressDialog();
        Call<NewsData> call = APIClient.getInstance()
                .getAllNews
                        (
                                ConstantValues.LANGUAGE_ID,
                                pageNumber,
                                0,
                                newsCategoryID
                        );

        call.enqueue(new Callback<NewsData>() {
            @Override
            public void onResponse(Call<NewsData> call, retrofit2.Response<NewsData> response) {
                dialogLoader.hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        
                        // Products have been returned. Add Products to the ProductsList
                        addNews(response.body());

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        addNews(response.body());
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
    
                    }
                    else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
    
                    progressBar.setVisibility(View.GONE);
    
                }
                else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    /*********** LoadMoreTask Used to Load more Products from the Server in the Background Thread using AsyncTask ********/

    private class LoadMoreTask extends AsyncTask<String, Void, String> {

        int page_number;


        private LoadMoreTask(int page_number) {
            this.page_number = page_number;
        }


        //*********** Runs on the UI thread before #doInBackground() ********//

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        //*********** Performs some Processes on Background Thread and Returns a specified Result  ********//

        @Override
        protected String doInBackground(String... params) {

            // Request for Products based on PageNo.
            RequestAllNews(page_number);

            return "All Done!";
        }


        //*********** Runs on the UI thread after #doInBackground() ********//

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

}