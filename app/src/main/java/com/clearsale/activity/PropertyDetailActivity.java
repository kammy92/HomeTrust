package com.clearsale.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.clearsale.R;
import com.clearsale.fragment.CompsFragment;
import com.clearsale.fragment.OverviewFragment;
import com.clearsale.utils.AppConfigTags;
import com.clearsale.utils.AppConfigURL;
import com.clearsale.utils.BuyerDetailsPref;
import com.clearsale.utils.Constants;
import com.clearsale.utils.CustomImageSlider;
import com.clearsale.utils.NetworkConnection;
import com.clearsale.utils.PropertyDetailsPref;
import com.clearsale.utils.SetTypeFace;
import com.clearsale.utils.Utils;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by l on 22/03/2017.
 */

public class PropertyDetailActivity extends AppCompatActivity {
    List<String> bannerList = new ArrayList<> ();
    Toolbar toolbar;
    CoordinatorLayout clMain;
    TextView tv4;
    int property_id;
    ProgressDialog progressDialog;
    RelativeLayout rlSliderIndicator;
    TextView tvSliderPosition;
    PropertyDetailsPref propertyDetailsPref;
    BuyerDetailsPref buyerDetailsPref;
    RelativeLayout rlBack;
    FloatingActionButton fabMaps;
    RelativeLayout rlMain;
    boolean isFavourite;
    ImageView ivFavourite;
    ViewPagerAdapter viewPagerAdapter;
    private SliderLayout slider;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_property_detail);
        initView ();
        initData ();
        initListener ();
        getExtras ();
        getPropertyDetails ();
    }
    
    private void getExtras () {
        Intent intent = getIntent ();
        property_id = intent.getIntExtra (AppConfigTags.PROPERTY_ID, 0);
        propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_ID, intent.getIntExtra (AppConfigTags.PROPERTY_ID, 0));
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_ADDRESS1, intent.getStringExtra (AppConfigTags.PROPERTY_ADDRESS));
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_ADDRESS2, intent.getStringExtra (AppConfigTags.PROPERTY_ADDRESS2));
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_PRICE, intent.getStringExtra (AppConfigTags.PROPERTY_PRICE));
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_YEAR_BUILD, intent.getStringExtra (AppConfigTags.PROPERTY_BUILT_YEAR));
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_BEDROOM, intent.getStringExtra (AppConfigTags.PROPERTY_BEDROOMS));
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_BATHROOM, intent.getStringExtra (AppConfigTags.PROPERTY_BATHROOMS));
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_AREA, intent.getStringExtra (AppConfigTags.PROPERTY_AREA));
    
        propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_AUCTION_STATUS, intent.getIntExtra (AppConfigTags.PROPERTY_STATUS, 0));
    
        bannerList = intent.getStringArrayListExtra (AppConfigTags.PROPERTY_IMAGES);
    
    
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_STATE, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_LATITUDE, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_LONGITUDE, "");
    
    
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_ARV, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_OVERVIEW, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_OFFER, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_ACCESS, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_REALTOR, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_COMPS, "");
    
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_KEY_DETAILS, "");
    
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_COMPS_ADDRESSES, "");
    
        propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_AUCTION_ID, 0);
        propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_TOUR_STATUS, 0);
    
        propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_IMAGE_COUNT, intent.getStringArrayListExtra (AppConfigTags.PROPERTY_IMAGES).size ());


//        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_IMAGES, jsonArrayPropertyImages.toString ());
//        propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_IMAGE_COUNT, jsonArrayPropertyImages.length ());

//        for (int j = 0; j < jsonArrayPropertyImages.length (); j++) {
//            JSONObject jsonObjectImages = jsonArrayPropertyImages.getJSONObject (j);
//            bannerList.add (jsonObjectImages.getString (AppConfigTags.PROPERTY_IMAGE));
////                                            propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_IMAGES + j, jsonObjectImages.getString (AppConfigTags.PROPERTY_IMAGE));
//        }
        initSlider ();
    
        if (intent.getBooleanExtra (AppConfigTags.PROPERTY_IS_FAVOURITE, false)) {
            ivFavourite.setImageResource (R.drawable.ic_heart_filled);
        } else {
            ivFavourite.setImageResource (R.drawable.ic_heart);
        }
        setupViewPager (viewPager);
    }
    
    private void initView () {
        rlBack = (RelativeLayout) findViewById (R.id.rlBack);
        clMain = (CoordinatorLayout) findViewById (R.id.clMain);
        slider = (SliderLayout) findViewById (R.id.slider);
        ivFavourite = (ImageView) findViewById (R.id.ivFavourite);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById (R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById (R.id.toolbar);
        tabLayout = (TabLayout) findViewById (R.id.tabs);
        viewPager = (ViewPager) findViewById (R.id.viewpager);
        rlSliderIndicator = (RelativeLayout) findViewById (R.id.rlSliderIndicator);
        tvSliderPosition = (TextView) findViewById (R.id.tvSliderPosition);
        fabMaps = (FloatingActionButton) findViewById (R.id.fabMap);
    }
    
    private void initData () {
        propertyDetailsPref = PropertyDetailsPref.getInstance ();
        buyerDetailsPref = BuyerDetailsPref.getInstance ();
        progressDialog = new ProgressDialog (this);
        tabLayout.setupWithViewPager (viewPager);
        tabLayout.setTabGravity (TabLayout.GRAVITY_FILL);
        collapsingToolbarLayout.setTitleEnabled (false);
//        appBar.setExpanded(true);
        
        Utils.setTypefaceToAllViews (this, rlBack);
    }
    
    protected void changeTabsFont (TabLayout tabLayout) {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt (0);
        int tabsCount = vg.getChildCount ();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt (j);
            int tabChildsCount = vgTab.getChildCount ();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt (i);
                if (tabViewChild instanceof TextView) {
                    TextView viewChild = (TextView) tabViewChild;
                    viewChild.setTypeface (SetTypeFace.getTypeface (this));
//                    viewChild.setAllCaps (false);
                }
            }
        }
    }
    
    private void initListener () {
        rlBack.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_ID, 0);
                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_ADDRESS1, "");
                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_ADDRESS2, "");
                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_STATE, "");
                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_LATITUDE, "");
                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_LONGITUDE, "");
                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_PRICE, "");
                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_YEAR_BUILD, "");
                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_BEDROOM, "");
                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_BATHROOM, "");
                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_AREA, "");
                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_OVERVIEW, "");
                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_OFFER, "");
                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_ACCESS, "");
                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_REALTOR, "");
                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_COMPS, "");
                propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_AUCTION_ID, 0);
                propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_AUCTION_STATUS, 0);
                finish ();
                overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        
        fabMaps.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
    
                try {
                    Double.parseDouble (propertyDetailsPref.getStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_LATITUDE));
                    Double.parseDouble (propertyDetailsPref.getStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_LONGITUDE));
                    Intent intent = new Intent (PropertyDetailActivity.this, PropertyLocationActivity.class);
                    startActivity (intent);
                } catch (Exception e) {
                    e.printStackTrace ();
                    Utils.showToast (PropertyDetailActivity.this, "No Map Details", false);
                }
                
            }
        });
    
        ivFavourite.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (isFavourite) {
                    isFavourite = false;
                    ivFavourite.setImageResource (R.drawable.ic_heart);
                    updateFavouriteStatus (false, property_id);
                } else {
                    isFavourite = true;
                    ivFavourite.setImageResource (R.drawable.ic_heart_filled);
                    updateFavouriteStatus (true, property_id);
                }
            }
        });
    }
    
    private void initSlider () {
        slider.removeAllSliders ();
        for (int i = 0; i < bannerList.size (); i++) {
            String image = bannerList.get (i);
            CustomImageSlider slider2 = new CustomImageSlider (this);
            final int k = i;
            slider2
                    .image (image)
                    .setScaleType (BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener (new BaseSliderView.OnSliderClickListener () {
                        @Override
                        public void onSliderClick (BaseSliderView slider) {
                            Intent intent = new Intent (PropertyDetailActivity.this, PropertyImageActivity.class);
                            intent.putExtra ("position", k);
                            startActivity (intent);
                        }
                    });

//            DefaultSliderView defaultSliderView = new DefaultSliderView (activity);
//            defaultSliderView
//                    .image (image)
//                    .setScaleType (BaseSliderView.ScaleType.Fit)
//                    .setOnSliderClickListener (new BaseSliderView.OnSliderClickListener () {
//                        @Override
//                        public void onSliderClick (BaseSliderView slider) {
//                            Intent intent = new Intent (activity, PropertyDetailActivity.class);
//                            intent.putExtra (AppConfigTags.PROPERTY_ID, property.getId ());
//                            activity.startActivity (intent);
//                            activity.overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
//                        }
//                    });
//
//            defaultSliderView.bundle (new Bundle ());
            // defaultSliderView.getBundle ().putString ("extra", String.valueOf (s));
//            holder.slider.addSlider (defaultSliderView);
            slider.addSlider (slider2);
        }
        
        slider.setIndicatorVisibility (PagerIndicator.IndicatorVisibility.Invisible);
        slider.getPagerIndicator ().setVisibility (View.INVISIBLE);
        slider.setPresetTransformer (SliderLayout.Transformer.Stack);
        slider.setCustomAnimation (new DescriptionAnimation ());
        slider.addOnPageChangeListener (new ViewPagerEx.OnPageChangeListener () {
            @Override
            public void onPageScrolled (int position, float positionOffset, int positionOffsetPixels) {
//                tvSliderPosition.setText (position  + bannerList.size ());
            }
            
            @Override
            public void onPageSelected (int position) {
                tvSliderPosition.setText ((position + 1) + " of " + bannerList.size ());
                tvSliderPosition.setVisibility (View.VISIBLE);
            }
            
            @Override
            public void onPageScrollStateChanged (int state) {
//                final Handler handler = new Handler ();
//                Runnable finalizer = null;
//                switch (state) {
//                    case 0:
//                        finalizer = new Runnable () {
//                            public void run () {
//                                rlSliderIndicator.setVisibility (View.GONE);
//                            }
//                        };
//                        handler.postDelayed (finalizer, 1500);
//                        break;
//                    case 1:
//                        handler.removeCallbacks (finalizer);
//                        rlSliderIndicator.setVisibility (View.VISIBLE);
//                        break;
//                    case 2:
//                        break;
//                }
            }
        });
    }
    
    private void getPropertyDetails () {
        if (NetworkConnection.isNetworkAvailable (PropertyDetailActivity.this)) {
            Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), true);
            Utils.showLog (Log.INFO, "" + AppConfigTags.URL, AppConfigURL.URL_PROPERTY_DETAIL, true);
            StringRequest strRequest1 = new StringRequest (Request.Method.POST, AppConfigURL.URL_PROPERTY_DETAIL,
                    new com.android.volley.Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);
                                    if (! error) {
                                        new setPropertyDetails ().execute (jsonObj.toString ());
                                    }
                                } catch (Exception e) {
                                    progressDialog.dismiss ();
                                    clMain.setVisibility (View.VISIBLE);
                                    Utils.showSnackBar (PropertyDetailActivity.this, clMain, getResources ().getString (R.string.snackbar_text_exception_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                                    e.printStackTrace ();
                                }
                            } else {
                                clMain.setVisibility (View.VISIBLE);
                                Utils.showSnackBar (PropertyDetailActivity.this, clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }
                            clMain.setVisibility (View.VISIBLE);
                            progressDialog.dismiss ();
                        }
                    },
                    new com.android.volley.Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            progressDialog.dismiss ();
                            clMain.setVisibility (View.VISIBLE);
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                            Utils.showSnackBar (PropertyDetailActivity.this, clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String> ();
                    params.put (AppConfigTags.PROPERTY_ID, String.valueOf (property_id));
                    params.put (AppConfigTags.BUYER_ID, String.valueOf (buyerDetailsPref.getIntPref (PropertyDetailActivity.this, BuyerDetailsPref.BUYER_ID)));
                    params.put (AppConfigTags.TYPE, "property_detail");
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }
    
                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                    Map<String, String> params = new HashMap<> ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            Utils.sendRequest (strRequest1, 60);
        } else {
//            clMain.setVisibility (View.VISIBLE);
            Utils.showSnackBar (this, clMain, getResources ().getString (R.string.snackbar_text_no_internet_connection_available), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_go_to_settings), new View.OnClickListener () {
                @Override
                public void onClick (View v) {
                    Intent dialogIntent = new Intent (Settings.ACTION_SETTINGS);
                    dialogIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity (dialogIntent);
                }
            });
        }
    }
    
    
    private void setupViewPager (ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter (getSupportFragmentManager ());
        viewPagerAdapter.addFragment (new OverviewFragment (), "OVERVIEW");
        viewPagerAdapter.addFragment (new CompsFragment (), "COMPS");
        viewPager.setAdapter (viewPagerAdapter);
    }
    
    @Override
    public void onBackPressed () {
        propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_ID, 0);
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_ADDRESS1, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_ADDRESS2, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_STATE, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_LATITUDE, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_LONGITUDE, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_PRICE, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_YEAR_BUILD, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_BEDROOM, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_BATHROOM, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_AREA, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_OVERVIEW, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_OFFER, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_ACCESS, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_REALTOR, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_COMPS, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_COMPS_ADDRESSES, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_OFFER, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_ARV, "");
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_IMAGES, "");
        propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_IMAGE_COUNT, 0);
        propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_AUCTION_ID, 0);
        propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_AUCTION_STATUS, 0);
        propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_TOUR_STATUS, "");
        
        finish ();
        overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
    }
    
    public void updateFavouriteStatus (final boolean favourite, final int property_id) {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showLog (Log.INFO, "" + AppConfigTags.URL, AppConfigURL.URL_TESTIMONIALS, true);
            StringRequest strRequest1 = new StringRequest (Request.Method.POST, AppConfigURL.URL_TESTIMONIALS,
                    new com.android.volley.Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);
                                    if (! error) {
                                        if (favourite) {
                                            
                                        } else {
                                        }
                                    } else {
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace ();
                                }
                            } else {
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    BuyerDetailsPref buyerDetailsPref = BuyerDetailsPref.getInstance ();
                    Map<String, String> params = new Hashtable<String, String> ();
                    params.put (AppConfigTags.TYPE, "property_favourite");
                    params.put (AppConfigTags.BUYER_ID, String.valueOf (buyerDetailsPref.getIntPref (PropertyDetailActivity.this, BuyerDetailsPref.BUYER_ID)));
                    params.put (AppConfigTags.PROPERTY_ID, String.valueOf (property_id));
                    if (favourite) {
                        params.put (AppConfigTags.IS_FAVOURITE, String.valueOf (1));
                    } else {
                        params.put (AppConfigTags.IS_FAVOURITE, String.valueOf (0));
                    }
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }
                
                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                    Map<String, String> params = new HashMap<> ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            Utils.sendRequest (strRequest1, 60);
        } else {
        }
    }
    
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<> ();
        private final List<String> mFragmentTitleList = new ArrayList<> ();
        
        public ViewPagerAdapter (FragmentManager manager) {
            super (manager);
        }
        
        @Override
        public Fragment getItem (int position) {
            return mFragmentList.get (position);
        }
        
        @Override
        public int getCount () {
            return mFragmentList.size ();
        }
        
        public void addFragment (Fragment fragment, String title) {
            mFragmentList.add (fragment);
            mFragmentTitleList.add (title);
        }
        
        @Override
        public CharSequence getPageTitle (int position) {
            return mFragmentTitleList.get (position);
        }
    }
    
    private class setPropertyDetails extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground (String... params) {
            try {
                JSONObject jsonObj = new JSONObject (params[0]);
                JSONArray jsonArrayPropertyImages = jsonObj.getJSONArray (AppConfigTags.PROPERTY_IMAGES);
                SharedPreferences.Editor editor = getSharedPreferences ("PROPERTY_DETAILS", Context.MODE_PRIVATE).edit ();
                editor.putString (PropertyDetailsPref.PROPERTY_STATE, jsonObj.getString (AppConfigTags.PROPERTY_STATE));
                editor.putString (PropertyDetailsPref.PROPERTY_LATITUDE, jsonObj.getString (AppConfigTags.LATITUDE));
                editor.putString (PropertyDetailsPref.PROPERTY_LONGITUDE, jsonObj.getString (AppConfigTags.LONGITUDE));
                editor.putString (PropertyDetailsPref.PROPERTY_ARV, jsonObj.getString (AppConfigTags.PROPERTY_ARV));
                editor.putString (PropertyDetailsPref.PROPERTY_OVERVIEW, jsonObj.getString (AppConfigTags.PROPERTY_OVERVIEW));
                editor.putString (PropertyDetailsPref.PROPERTY_OFFER, jsonObj.getString (AppConfigTags.PROPERTY_OFFER));
                editor.putString (PropertyDetailsPref.PROPERTY_ACCESS, jsonObj.getString (AppConfigTags.PROPERTY_ACCESS));
                editor.putString (PropertyDetailsPref.PROPERTY_REALTOR, jsonObj.getString (AppConfigTags.PROPERTY_REALTOR));
                editor.putString (PropertyDetailsPref.PROPERTY_COMPS, jsonObj.getString (AppConfigTags.PROPERTY_COMPS));
                editor.putString (PropertyDetailsPref.PROPERTY_KEY_DETAILS, jsonObj.getString (AppConfigTags.PROPERTY_KEY_DETAILS));
                editor.putString (PropertyDetailsPref.PROPERTY_COMPS_ADDRESSES, jsonObj.getJSONArray (AppConfigTags.PROPERTY_COMPS_ADDRESSES).toString ());
                editor.putString (PropertyDetailsPref.PROPERTY_IMAGES, jsonArrayPropertyImages.toString ());
    
                editor.putInt (PropertyDetailsPref.PROPERTY_AUCTION_ID, jsonObj.getInt (AppConfigTags.PROPERTY_BID_AUCTION_ID));
                editor.putInt (PropertyDetailsPref.PROPERTY_AUCTION_STATUS, jsonObj.getInt (AppConfigTags.PROPERTY_BID_AUCTION_STATUS));
                editor.putInt (PropertyDetailsPref.PROPERTY_TOUR_STATUS, jsonObj.getInt (AppConfigTags.PROPERTY_TOUR_STATUS));
                editor.putInt (PropertyDetailsPref.PROPERTY_IMAGE_COUNT, jsonArrayPropertyImages.length ());
    
                editor.apply ();


//                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_STATE, jsonObj.getString (AppConfigTags.PROPERTY_STATE));
//                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_LATITUDE, jsonObj.getString (AppConfigTags.LATITUDE));
//                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_LONGITUDE, jsonObj.getString (AppConfigTags.LONGITUDE));
//                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_ARV, jsonObj.getString (AppConfigTags.PROPERTY_ARV));
//                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_OVERVIEW, jsonObj.getString (AppConfigTags.PROPERTY_OVERVIEW));
//                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_OFFER, jsonObj.getString (AppConfigTags.PROPERTY_OFFER));
//                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_ACCESS, jsonObj.getString (AppConfigTags.PROPERTY_ACCESS));
//                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_REALTOR, jsonObj.getString (AppConfigTags.PROPERTY_REALTOR));
//                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_COMPS, jsonObj.getString (AppConfigTags.PROPERTY_COMPS));
//
//                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_KEY_DETAILS, jsonObj.getString (AppConfigTags.PROPERTY_KEY_DETAILS));
//
//                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_COMPS_ADDRESSES, jsonObj.getJSONArray (AppConfigTags.PROPERTY_COMPS_ADDRESSES).toString ());
//
//                propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_AUCTION_ID, jsonObj.getInt (AppConfigTags.PROPERTY_BID_AUCTION_ID));
//                propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_AUCTION_STATUS, jsonObj.getInt (AppConfigTags.PROPERTY_BID_AUCTION_STATUS));
//
//                propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_TOUR_STATUS, jsonObj.getInt (AppConfigTags.PROPERTY_TOUR_STATUS));
//
//                JSONArray jsonArrayPropertyImages = jsonObj.getJSONArray (AppConfigTags.PROPERTY_IMAGES);
//                propertyDetailsPref.putStringPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_IMAGES, jsonArrayPropertyImages.toString ());
//                propertyDetailsPref.putIntPref (PropertyDetailActivity.this, PropertyDetailsPref.PROPERTY_IMAGE_COUNT, jsonArrayPropertyImages.length ());
//
                bannerList.clear ();
                for (int j = 0; j < jsonArrayPropertyImages.length (); j++) {
                    JSONObject jsonObjectImages = jsonArrayPropertyImages.getJSONObject (j);
                    bannerList.add (jsonObjectImages.getString (AppConfigTags.PROPERTY_IMAGE));
                }
            } catch (JSONException e) {
                e.printStackTrace ();
            }
            return "Executed";
        }
        
        @Override
        protected void onPostExecute (String result) {
            Log.e ("karman", "executed");
            clMain.setVisibility (View.VISIBLE);
            progressDialog.dismiss ();
            initSlider ();
//            viewPager.getAdapter ().notifyDataSetChanged ();
            setupViewPager (viewPager);
        }
        
        @Override
        protected void onPreExecute () {
        }
        
        @Override
        protected void onProgressUpdate (Void... values) {
        }
    }
}