package com.themescoder.androidstore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.adapters.DrawerExpandableListAdapter;
import com.themescoder.androidstore.models.drawer_model.Drawer_Items;

public class ExtraSettings extends Fragment {

    List<Drawer_Items> listDataHeader = new ArrayList<>();
    Map<Drawer_Items, List<Drawer_Items>> listDataChild = new HashMap<>();

    ExpandableListView expandableListView;
    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.extra_settings_fragment, container, false);

        activity = (MainActivity) getActivity();
        activity.toggleNavigaiton(false);
        expandableListView = rootView.findViewById(R.id.main_drawer_list);

        setupExpandableDrawerList();


        DrawerExpandableListAdapter drawerExpandableAdapter = new DrawerExpandableListAdapter(getContext(), listDataHeader, listDataChild);

        expandableListView.setAdapter(drawerExpandableAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // Navigate to Selected Child Item
                ((MainActivity) getActivity()).toggleNavigaiton(false);
                drawerSelectedItemNavigation(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTitle());
                return false;
            }
        });

        return rootView;
    }


    public void setupExpandableDrawerList() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        listDataHeader.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.actionHome)));
        listDataHeader.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.actionCategories)));
        listDataHeader.add(new Drawer_Items(R.drawable.ic_cart, getString(R.string.actionShop)));

        List<Drawer_Items> home_styles = new ArrayList<>();
        home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle1)));
        home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle2)));
        home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle3)));
        home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle4)));
        home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle5)));
        home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle6)));
        home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle7)));
        home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle8)));
        home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle9)));
        home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle10)));

        List<Drawer_Items> category_styles = new ArrayList<>();
        category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle1)));
        category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle2)));
        category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle3)));
        category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle4)));
        category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle5)));
        category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle6)));

        List<Drawer_Items> shop_childs = new ArrayList<>();
        shop_childs.add(new Drawer_Items(R.drawable.ic_arrow_up, getString(R.string.Newest)));
        shop_childs.add(new Drawer_Items(R.drawable.ic_sale, getString(R.string.topSeller)));
        shop_childs.add(new Drawer_Items(R.drawable.ic_star_circle, getString(R.string.superDeals)));
        shop_childs.add(new Drawer_Items(R.drawable.ic_most_liked, getString(R.string.mostLiked)));


        // Add Child to selective Headers
        listDataChild.put(listDataHeader.get(0), home_styles);
        listDataChild.put(listDataHeader.get(1), category_styles);
        listDataChild.put(listDataHeader.get(2), shop_childs);
    }

    public void drawerSelectedItemNavigation(String selectedItem) {

        Fragment fragment;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        if (selectedItem.equalsIgnoreCase(getString(R.string.actionHome))) {
            activity.mSelectedItem = selectedItem;

            // Navigate to any selected HomePage Fragment
            if (activity.homePage_1 == null) {
                activity.homePage_1 = new HomePage_1();
                if (activity.currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, activity.homePage_1)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(activity.currentFragment)
                            .add(R.id.main_fragment, activity.homePage_1)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();

            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.homeStyle1)) == null) {
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.homePage_1).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                } else {
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.homePage_1).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                }
            }
//            activity.currentFragment = activity.homePage_1;

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle1))) {
            activity.mSelectedItem = selectedItem;

            // Navigate to HomePage1 Fragment
            if (activity.homePage_1 == null) {
                activity.homePage_1 = new HomePage_1();
                if (activity.currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, activity.homePage_1)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(activity.currentFragment)
                            .add(R.id.main_fragment, activity.homePage_1)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.homeStyle1)) == null) {
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.homePage_1).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                } else {
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.homePage_1).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                }
            }
//            activity.currentFragment = activity.homePage_1;

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle2))) {
            activity.mSelectedItem = selectedItem;

            // Navigate to HomePage2 Fragment
            if (activity.homePage_2 == null) {
                activity.homePage_2 = new HomePage_2();
                if (activity.currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, activity.homePage_2)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(activity.currentFragment)
                            .add(R.id.main_fragment, activity.homePage_2)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.homeStyle2)) == null) {
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.homePage_2).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                } else {
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.homePage_2).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                }
            }
//            activity.currentFragment = activity.homePage_2;

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle3))) {
            activity.mSelectedItem = selectedItem;

            // Navigate to HomePage3 Fragment
            if (activity.homePage_3 == null) {
                activity.homePage_3 = new HomePage_3();
                if (activity.currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, activity.homePage_3)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(activity.currentFragment)
                            .add(R.id.main_fragment, activity.homePage_3)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.homeStyle3)) == null)
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.homePage_3).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.homePage_3).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.homePage_3;


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle4))) {
            activity.mSelectedItem = selectedItem;

            // Navigate to HomePage4 Fragment
            if (activity.homePage_4 == null) {
                activity.homePage_4 = new HomePage_4();
                if (activity.currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, activity.homePage_4)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(activity.currentFragment)
                            .add(R.id.main_fragment, activity.homePage_4)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.homeStyle4)) == null)
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.homePage_4).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.homePage_4).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.homePage_4;


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle5))) {
            activity.mSelectedItem = selectedItem;

            // Navigate to HomePage5 Fragment
            if (activity.homePage_5 == null) {
                activity.homePage_5 = new HomePage_5();
                if (activity.currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, activity.homePage_5)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(activity.currentFragment)
                            .add(R.id.main_fragment, activity.homePage_5)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.homeStyle5)) == null)
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.homePage_5).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.homePage_5).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.homePage_5;


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle6))) {
            activity.mSelectedItem = selectedItem;

            // Navigate to HomePage5 Fragment
            if (activity.homePage_6 == null) {
                activity.homePage_6 = new HomePage_6();
                if (activity.currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, activity.homePage_6)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(activity.currentFragment)
                            .add(R.id.main_fragment, activity.homePage_6)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.homeStyle6)) == null)
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.homePage_6).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.homePage_6).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.homePage_6;


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle7))) {
            activity.mSelectedItem = selectedItem;

            // Navigate to HomePage5 Fragment
            if (activity.homePage_7 == null) {
                activity.homePage_7 = new HomePage_7();
                if (activity.currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, activity.homePage_7)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(activity.currentFragment)
                            .add(R.id.main_fragment, activity.homePage_7)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.homeStyle7)) == null)
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.homePage_7).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.homePage_7).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.homePage_7;


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle8))) {
            activity.mSelectedItem = selectedItem;

            // Navigate to HomePage5 Fragment
            if (activity.homePage_8 == null) {
                activity.homePage_8 = new HomePage_8();
                if (activity.currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, activity.homePage_8)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(activity.currentFragment)
                            .add(R.id.main_fragment, activity.homePage_8)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.homeStyle8)) == null)
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.homePage_8).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.homePage_8).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.homePage_8;


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle9))) {
            activity.mSelectedItem = selectedItem;

            // Navigate to HomePage5 Fragment
            if (activity.homePage_9 == null) {
                activity.homePage_9 = new HomePage_9();
                if (activity.currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, activity.homePage_9)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(activity.currentFragment)
                            .add(R.id.main_fragment, activity.homePage_9)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.homeStyle9)) == null)
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.homePage_9).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.homePage_9).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.homePage_9;


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle10))) {
            activity.mSelectedItem = selectedItem;

            // Navigate to HomePage5 Fragment
            if (activity.homePage_10 == null) {
                activity.homePage_10 = new HomePage_10();
                if (activity.currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, activity.homePage_10)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(activity.currentFragment)
                            .add(R.id.main_fragment, activity.homePage_10)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.homeStyle10)) == null)
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.homePage_10).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.homePage_10).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.homePage_10;


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionCategories))) {
            activity.mSelectedItem = selectedItem;

            if (activity.categories_1 == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHeaderVisible", false);
                activity.categories_1 = new Categories_1();
                activity.categories_1.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(activity.currentFragment)
                        .add(R.id.main_fragment, activity.categories_1)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.categoryStyle1)) == null)
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.categories_1).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(getString(R.string.actionHome)).addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.categories_1).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(getString(R.string.actionHome)).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.categories_1;

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle1))) {
            activity.mSelectedItem = selectedItem;

            if (activity.categories_1 == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHeaderVisible", false);
                activity.categories_1 = new Categories_1();
                activity.categories_1.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(activity.currentFragment)
                        .add(R.id.main_fragment, activity.categories_1)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.categoryStyle1)) == null)
                fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.categories_1).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.categories_1).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.categories_1;

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle2))) {
            activity.mSelectedItem = selectedItem;

            if (activity.categories_2 == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHeaderVisible", false);
                activity.categories_2 = new Categories_2();
                activity.categories_2.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(activity.currentFragment)
                        .add(R.id.main_fragment, activity.categories_2)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.categoryStyle2)) == null)
                fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.categories_2).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.categories_2).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.categories_2;


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle3))) {
            activity.mSelectedItem = selectedItem;

            if (activity.categories_3 == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHeaderVisible", false);
                activity.categories_3 = new Categories_3();
                activity.categories_3.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(activity.currentFragment)
                        .add(R.id.main_fragment, activity.categories_3)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.categoryStyle3)) == null)
                fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.categories_3).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.categories_3).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.categories_3;


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle4))) {
            activity.mSelectedItem = selectedItem;

            if (activity.categories_4 == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHeaderVisible", false);
                activity.categories_4 = new Categories_4();
                activity.categories_4.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(activity.currentFragment)
                        .add(R.id.main_fragment, activity.categories_4)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.categoryStyle4)) == null)
                fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.categories_4).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.categories_4).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.categories_4;


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle5))) {
            activity.mSelectedItem = selectedItem;

            if (activity.categories_5 == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHeaderVisible", false);
                activity.categories_5 = new Categories_5();
                activity.categories_5.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(activity.currentFragment)
                        .add(R.id.main_fragment, activity.categories_5)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.categoryStyle5)) == null)
                fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.categories_5).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.categories_5).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.categories_5;


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle6))) {
            activity.mSelectedItem = selectedItem;

            if (activity.categories_6 == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHeaderVisible", false);
                activity.categories_6 = new Categories_6();
                activity.categories_6.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(activity.currentFragment)
                        .add(R.id.main_fragment, activity.categories_6)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            } else {
                if (fragmentManager.findFragmentByTag(getString(R.string.categoryStyle6)) == null)
                fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.categories_6).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                else
                    fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).show(activity.categories_6).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.categories_6;


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionShop))) {
            activity.mSelectedItem = selectedItem;

            if (activity.newest == null) {
                Bundle bundle = new Bundle();
                bundle.putString("sortBy", "Newest");
                bundle.putBoolean("isMenuItem", false);
                activity.newest = new Products();
                activity.newest.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(activity.currentFragment)
                        .add(R.id.main_fragment, activity.newest)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            } else {
                fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.newest).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.newest;

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.Newest))) {
            activity.mSelectedItem = selectedItem;

            if (activity.newest == null) {
                Bundle bundle = new Bundle();
                bundle.putString("sortBy", "Newest");
                bundle.putBoolean("isMenuItem", false);
                activity.newest = new Products();
                activity.newest.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(activity.currentFragment)
                        .add(R.id.main_fragment, activity.newest)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            } else {
                fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.newest).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.newest;

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.superDeals))) {
            activity.mSelectedItem = selectedItem;

            if (activity.superDeals == null) {
                Bundle bundle = new Bundle();
                bundle.putString("sortBy", "special");
                bundle.putBoolean("isMenuItem", false);
                activity.superDeals = new Products();
                activity.superDeals.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(activity.currentFragment)
                        .add(R.id.main_fragment, activity.superDeals)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            } else {
                fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.superDeals).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.superDeals;

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.topSeller))) {
            activity.mSelectedItem = selectedItem;

            if (activity.topSellers == null) {
                Bundle bundle = new Bundle();
                bundle.putString("sortBy", "top seller");
                bundle.putBoolean("isMenuItem", false);
                activity.topSellers = new Products();
                activity.topSellers.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(activity.currentFragment)
                        .add(R.id.main_fragment, activity.topSellers)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            } else {
                fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.topSellers).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.topSellers;

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.mostLiked))) {
            activity.mSelectedItem = selectedItem;

            if (activity.mostLiked == null) {
                Bundle bundle = new Bundle();
                bundle.putString("sortBy", "most liked");
                bundle.putBoolean("isMenuItem", false);
                activity.mostLiked = new Products();
                activity.mostLiked.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(activity.currentFragment)
                        .add(R.id.main_fragment, activity.mostLiked)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            } else {
                fragmentManager.beginTransaction().hide(activity.currentFragment).hide(activity.extraSettings).add(R.id.main_fragment, activity.mostLiked).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
            }
//            activity.currentFragment = activity.mostLiked;

        }
    }
}
