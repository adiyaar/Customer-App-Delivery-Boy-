package com.themescoder.androidstore.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.themescoder.androidstore.R;

import java.util.List;

import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.fragment.Languages;
import com.themescoder.androidstore.models.language_model.LanguageDetails;


/**
 * LanguagesAdapter is the adapter class of ListView holding List of Languages in Languages Fragment
 **/

public class LanguagesAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    
    private Languages languages_fragment;
    private List<LanguageDetails> languagesList;


    public LanguagesAdapter(Context context, List<LanguageDetails> languagesList, Languages languages_fragment) {
        this.context = context;
        this.languagesList = languagesList;
        this.languages_fragment = languages_fragment;

        layoutInflater = LayoutInflater.from(context);
    }
    
    
    //********** Returns the total number of items in the data set represented by this Adapter *********//
    
    @Override
    public int getCount() {
        return languagesList.size();
    }
    
    
    //********** Returns the item associated with the specified position in the data set *********//
    
    @Override
    public Object getItem(int position) {
        return languagesList.get(position);
    }
    
    
    //********** Returns the item id associated with the specified position in the list *********//
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    
    
    //********** Returns a View that displays the data at the specified position in the data set *********//
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_language, parent, false);

            holder = new ViewHolder();

            holder.languageName = (TextView) convertView.findViewById(R.id.language_name);
            holder.languageIcon = (ImageView) convertView.findViewById(R.id.language_icon);
            holder.languageSelector = (CheckBox) convertView.findViewById(R.id.cb_language);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


    
        holder.languageName.setText(languagesList.get(position).getName());

        RequestOptions options = new RequestOptions()
                .centerInside()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);


        Glide.with(context)
                .setDefaultRequestOptions(options).load(ConstantValues.ECOMMERCE_URL+ languagesList.get(position).getImage()).into(holder.languageIcon);
        
        
        if (languagesList.get(position).getLanguagesId().equalsIgnoreCase(languages_fragment.getSelectedLanguageID())) {
            holder.languageSelector.setChecked(true);
            languages_fragment.setLastCheckedCB(holder.languageSelector);
            
        } else {
            holder.languageSelector.setChecked(false);
        }


        return convertView;
    }
    
    
    
    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/
    
    static class ViewHolder {
        TextView languageName;
        ImageView languageIcon;
        CheckBox languageSelector;

    }


}

