package com.clearsale.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.clearsale.R;
import com.clearsale.activity.PlaceOfferActivity;
import com.clearsale.activity.ScheduleTourActivity;
import com.clearsale.utils.AppConfigTags;
import com.clearsale.utils.AppConfigURL;
import com.clearsale.utils.BuyerDetailsPref;
import com.clearsale.utils.Constants;
import com.clearsale.utils.NetworkConnection;
import com.clearsale.utils.PropertyDetailsPref;
import com.clearsale.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class OverviewFragment extends Fragment {
    PropertyDetailsPref propertyDetailsPref;
    WebView wvDescription;

    TextView tvYear;
    TextView tvBedroom;
    TextView tvBathroom;
    TextView tvPropertyRate;
    TextView tvStatus;
    TextView tvAddress1;
    TextView tvAddress2;
    TextView tvSqFeet;
    
    ProgressDialog progressDialog;
    BuyerDetailsPref buyerDetailsPref;
    
    CardView cvDescription;
    Button btShowMoreDescription;
    TextView tvPlaceAnOffer;
    boolean descriptionExpanded = true;
    WebView wvKeyDetail;
    Button btShowMoreKeyDetail;
    boolean keyDetailExpanded = true;
    boolean showofferDialog = true;
    CardView cvKeyDetails;
    TextView tvScheduleTour;
    EditText etOfferUsd;
    
    LinearLayout llDescription;
    
    
    LinearLayout llKeyDetails;
    
    
    LinearLayout llOverview;
    CardView cvOverview;
    Button btShowMoreOverview;
    WebView wvOverview;
    boolean overviewExpanded = true;
    
    
    LinearLayout llWorkScope;
    WebView wvWorkScope;
    
    LinearLayout llFinishedProduct;
    WebView wvFinishedProduct;
    
    LinearLayout llClosingDetails;
    WebView wvClosingDetails;
    
    LinearLayout llRealtor;
    WebView wvRealtor;
    
    
    Animation animation1, animation2;
    ProgressBar progressBar;
    private View positiveAction;
    
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate (R.layout.fragment_overview, container, false);
        initView (rootView);
        initData ();
        initListener ();
        //    getOverviewData();
        return rootView;
    }

    private void initView (View rootView) {
        // clMain = (CoordinatorLayout)rootView.findViewById(R.id.clMain);
    
        progressBar = (ProgressBar) rootView.findViewById (R.id.progressBar);
        
        tvYear = (TextView) rootView.findViewById (R.id.tvYear);
        tvBedroom = (TextView) rootView.findViewById (R.id.tvBedroom);
        tvBathroom = (TextView) rootView.findViewById (R.id.tvBathroom);
        tvPropertyRate = (TextView) rootView.findViewById (R.id.tvPropertyRate);
        tvStatus = (TextView) rootView.findViewById (R.id.tvStatus);
        tvAddress1 = (TextView) rootView.findViewById (R.id.tvAddress1);
        tvAddress2 = (TextView) rootView.findViewById (R.id.tvAddress2);
        tvSqFeet = (TextView) rootView.findViewById (R.id.tvSqFeet);
    
    
        cvDescription = (CardView) rootView.findViewById (R.id.cvDescription);
        llDescription = (LinearLayout) rootView.findViewById (R.id.llDescription);
        wvDescription = (WebView) rootView.findViewById (R.id.wvDescription);
        btShowMoreDescription = (Button) rootView.findViewById (R.id.btShowMoreDescription);
    
        llOverview = (LinearLayout) rootView.findViewById (R.id.llOverview);
        cvOverview = (CardView) rootView.findViewById (R.id.cvOverview);
        wvOverview = (WebView) rootView.findViewById (R.id.wvOverview);
        btShowMoreOverview = (Button) rootView.findViewById (R.id.btShowMoreOverview);
        
        tvScheduleTour = (TextView) rootView.findViewById (R.id.tvScheduleTour);
    
        llWorkScope = (LinearLayout) rootView.findViewById (R.id.llWorkScope);
        wvWorkScope = (WebView) rootView.findViewById (R.id.wvWorkScope);
    
        llFinishedProduct = (LinearLayout) rootView.findViewById (R.id.llFinishedProduct);
        wvFinishedProduct = (WebView) rootView.findViewById (R.id.wvFinishedProduct);
    
        llClosingDetails = (LinearLayout) rootView.findViewById (R.id.llClosingDetails);
        wvClosingDetails = (WebView) rootView.findViewById (R.id.wvClosingDetails);
    
        llKeyDetails = (LinearLayout) rootView.findViewById (R.id.llKeyDetails);
        cvKeyDetails = (CardView) rootView.findViewById (R.id.cvKeyDetails);
        wvKeyDetail = (WebView) rootView.findViewById (R.id.wvKeyDetail);
        btShowMoreKeyDetail = (Button) rootView.findViewById (R.id.btShowMoreKeyDetail);
    
        tvPlaceAnOffer = (TextView) rootView.findViewById (R.id.tvPlaceAnOffer);
    
        llRealtor = (LinearLayout) rootView.findViewById (R.id.llRealtor);
        wvRealtor = (WebView) rootView.findViewById (R.id.wvRealtor);
    
    
    }

    private void initData () {
        buyerDetailsPref = BuyerDetailsPref.getInstance ();
        propertyDetailsPref = PropertyDetailsPref.getInstance ();
        progressDialog = new ProgressDialog (getActivity ());
        
        animation1 = new AlphaAnimation (0.5f, 1.0f);
        animation1.setDuration (2000);
        animation2 = new AlphaAnimation (1.0f, 0.5f);
        animation2.setDuration (2000);
    
    
        tvYear.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_YEAR_BUILD));
        tvAddress1.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_ADDRESS1));
        tvAddress2.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_ADDRESS2));
        tvPropertyRate.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_PRICE));
        tvBedroom.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_BEDROOM));
        tvBathroom.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_BATHROOM));
        tvSqFeet.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_AREA));
        switch (propertyDetailsPref.getIntPref (getActivity (), PropertyDetailsPref.PROPERTY_AUCTION_STATUS)) {
            case 1:
                Drawable img = getActivity ().getResources ().getDrawable (R.drawable.circle_green);
                img.setBounds (0, 0, 20, 20);
                tvStatus.setCompoundDrawables (img, null, null, null);
                tvStatus.setText ("Available");
                break;
            case 2:
                Drawable img2 = getActivity ().getResources ().getDrawable (R.drawable.circle_yellow);
                img2.setBounds (0, 0, 20, 20);
                tvStatus.setCompoundDrawables (img2, null, null, null);
                tvStatus.setText ("Pending");
                break;
            case 3:
                Drawable img3 = getActivity ().getResources ().getDrawable (R.drawable.circle_red);
                img3.setBounds (0, 0, 20, 20);
                tvStatus.setCompoundDrawables (img3, null, null, null);
                tvStatus.setText ("Sold");
                break;
            case 4:
                Drawable img4 = getActivity ().getResources ().getDrawable (R.drawable.circle_red);
                img4.setBounds (0, 0, 20, 20);
                tvStatus.setCompoundDrawables (img4, null, null, null);
                tvStatus.setText ("Closed");
                break;
            case 9:
                Drawable img9 = getActivity ().getResources ().getDrawable (R.drawable.circle_red);
                img9.setBounds (0, 0, 20, 20);
                tvStatus.setCompoundDrawables (img9, null, null, null);
                tvStatus.setText ("Offer Window Closing");
                break;
        }


//        new setPropertyDetails().execute ();
    
        if (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_DESCRIPTION).length () > 0) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_DESCRIPTION));
            wvDescription.loadDataWithBaseURL ("www.google.com", spannableStringBuilder.toString (), "text/html", "UTF-8", "");
            llDescription.setVisibility (View.VISIBLE);
            if (propertyDetailsPref.getIntPref (getActivity (), PropertyDetailsPref.PROPERTY_AUCTION_STATUS) == 1) {
                tvPlaceAnOffer.setVisibility (View.VISIBLE);
            } else {
                tvPlaceAnOffer.setVisibility (View.GONE);
            }
        
            if (propertyDetailsPref.getIntPref (getActivity (), PropertyDetailsPref.PROPERTY_TOUR_STATUS) == 1) {
                tvScheduleTour.setVisibility (View.VISIBLE);
            } else {
                tvScheduleTour.setVisibility (View.GONE);
            }
        } else {
            llDescription.setVisibility (View.GONE);
        }
    
        if (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_KEY_DETAILS).length () > 0) {
            SpannableStringBuilder spannableStringBuilderKeyDetail = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_KEY_DETAILS));
            wvKeyDetail.loadDataWithBaseURL ("www.google.com", spannableStringBuilderKeyDetail.toString (), "text/html", "UTF-8", "");
            llKeyDetails.setVisibility (View.VISIBLE);
        } else {
            llKeyDetails.setVisibility (View.GONE);
        }
    
        if (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_REALTOR).length () > 0) {
            SpannableStringBuilder spannableStringBuilderRealtor = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_REALTOR));
            wvRealtor.loadDataWithBaseURL ("www.google.com", spannableStringBuilderRealtor.toString (), "text/html", "UTF-8", "");
            llRealtor.setVisibility (View.VISIBLE);
        } else {
            llRealtor.setVisibility (View.GONE);
        }
    
        if (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_OVERVIEW).length () > 0) {
            SpannableStringBuilder spannableStringBuilderOverview = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_OVERVIEW));
            wvOverview.loadDataWithBaseURL ("www.google.com", spannableStringBuilderOverview.toString (), "text/html", "UTF-8", "");
            llOverview.setVisibility (View.VISIBLE);
        } else {
            llOverview.setVisibility (View.GONE);
        }
    
        if (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_WORK_SCOPE).length () > 0) {
            SpannableStringBuilder spannableAccessWorkScope = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_WORK_SCOPE));
            wvWorkScope.loadDataWithBaseURL ("www.google.com", spannableAccessWorkScope.toString (), "text/html", "UTF-8", "");
            llWorkScope.setVisibility (View.VISIBLE);
        } else {
            llWorkScope.setVisibility (View.GONE);
        }
    
        if (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_FINISHED_PRODUCT).length () > 0) {
            SpannableStringBuilder spannableAccessFinishedProduct = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_FINISHED_PRODUCT));
            wvFinishedProduct.loadDataWithBaseURL ("www.google.com", spannableAccessFinishedProduct.toString (), "text/html", "UTF-8", "");
            llFinishedProduct.setVisibility (View.VISIBLE);
        } else {
            llFinishedProduct.setVisibility (View.GONE);
        }
    
        if (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_CLOSING_DETAILS).length () > 0) {
            SpannableStringBuilder spannableAccessClosingDetails = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_CLOSING_DETAILS));
            wvClosingDetails.loadDataWithBaseURL ("www.google.com", spannableAccessClosingDetails.toString (), "text/html", "UTF-8", "");
            llClosingDetails.setVisibility (View.VISIBLE);
        } else {
            llClosingDetails.setVisibility (View.GONE);
        }
    
        progressBar.setVisibility (View.GONE);
    
    
        Utils.setTypefaceToAllViews (getActivity (), tvAddress1);
    
    }
    
    private void initListener () {
        btShowMoreDescription.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                if (descriptionExpanded) {
                    btShowMoreDescription.setText ("LESS");
                    descriptionExpanded = false;
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                            cvDescription.setLayoutParams (params);
                        }
                    };
                    a.setDuration (1500); // in ms
                    cvDescription.startAnimation (a);
                } else {
                    btShowMoreDescription.setText ("MORE");
                    descriptionExpanded = true;
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            if ((1.0f - interpolatedTime) < 1.0f) {
                                if ((cvDescription.getHeight () * (1.0f - interpolatedTime)) <= Utils.pxFromDp (getActivity (), 200.0f)) {
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (LinearLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cvDescription.setLayoutParams (params);
                                } else {
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (LinearLayout.LayoutParams.MATCH_PARENT, (int) (cvDescription.getHeight () * (1.0f - interpolatedTime)));
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cvDescription.setLayoutParams (params);
                                }
                            }
                        }
                    };
                    a.setDuration (1500); // in ms
                    cvDescription.startAnimation (a);
                }
            }
        });
        
        btShowMoreKeyDetail.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                if (keyDetailExpanded) {
                    btShowMoreKeyDetail.setText ("HIDE FULL DETAILS");
                    keyDetailExpanded = false;
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                            cvKeyDetails.setLayoutParams (params);
                        }
                    };
                    a.setDuration (1500); // in ms
                    cvKeyDetails.startAnimation (a);
                } else {
                    btShowMoreKeyDetail.setText ("SHOW FULL DETAILS");
                    keyDetailExpanded = true;
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            if ((1.0f - interpolatedTime) < 1.0f) {
                                if ((cvKeyDetails.getHeight () * (1.0f - interpolatedTime)) <= Utils.pxFromDp (getActivity (), 200.0f)) {
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (LinearLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cvKeyDetails.setLayoutParams (params);
                                } else {
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (LinearLayout.LayoutParams.MATCH_PARENT, (int) (cvKeyDetails.getHeight () * (1.0f - interpolatedTime)));
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cvKeyDetails.setLayoutParams (params);
                                }
                            }
                        }
                    };
                    a.setDuration (1500); // in ms
                    cvKeyDetails.startAnimation (a);
                }
            }
        });
    
        tvPlaceAnOffer.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                Intent placeOffer = new Intent (getActivity (), PlaceOfferActivity.class);
                placeOffer.putExtra (AppConfigTags.PROPERTY_ID, propertyDetailsPref.getIntPref (getActivity (), PropertyDetailsPref.PROPERTY_ID));
                placeOffer.putExtra (AppConfigTags.BUYER_ID, buyerDetailsPref.getIntPref (getActivity (), BuyerDetailsPref.BUYER_ID));
                placeOffer.putExtra (AppConfigTags.PROPERTY_BID_AUCTION_ID, propertyDetailsPref.getIntPref (getActivity (), PropertyDetailsPref.PROPERTY_AUCTION_ID));
                startActivity (placeOffer);
                getActivity ().overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
            }
            
        });
        
        tvScheduleTour.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                Intent scheduleTour = new Intent (getActivity (), ScheduleTourActivity.class);
                scheduleTour.putExtra (AppConfigTags.PROPERTY_ID, propertyDetailsPref.getIntPref (getActivity (), PropertyDetailsPref.PROPERTY_ID));
                scheduleTour.putExtra (AppConfigTags.PROPERTY_ADDRESS, propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_ADDRESS1));
                scheduleTour.putExtra (AppConfigTags.PROPERTY_CITY, propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_ADDRESS2));
                startActivity (scheduleTour);
            }
        });
    
        btShowMoreOverview.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                if (overviewExpanded) {
                    btShowMoreOverview.setText ("LESS");
                    overviewExpanded = false;
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                            cvOverview.setLayoutParams (params);
                        }
                    };
                    a.setDuration (1500); // in ms
                    cvOverview.startAnimation (a);
                } else {
                    btShowMoreOverview.setText ("MORE");
                    overviewExpanded = true;
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            if ((1.0f - interpolatedTime) < 1.0f) {
                                if ((cvOverview.getHeight () * (1.0f - interpolatedTime)) <= Utils.pxFromDp (getActivity (), 200.0f)) {
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (LinearLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cvOverview.setLayoutParams (params);
                                } else {
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (LinearLayout.LayoutParams.MATCH_PARENT, (int) (cvOverview.getHeight () * (1.0f - interpolatedTime)));
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cvOverview.setLayoutParams (params);
                                }
                            }
                        }
                    };
                    a.setDuration (1500); // in ms
                    cvOverview.startAnimation (a);
                }
            }
        });
        
        animation1.setAnimationListener (new Animation.AnimationListener () {
            @Override
            public void onAnimationEnd (Animation arg0) {
            }
    
            @Override
            public void onAnimationRepeat (Animation arg0) {
                // TODO Auto-generated method stub
        
            }
    
            @Override
            public void onAnimationStart (Animation arg0) {
                // TODO Auto-generated method stub
        
            }
    
        });
        
        animation2.setAnimationListener (new Animation.AnimationListener () {
            
            @Override
            public void onAnimationEnd (Animation arg0) {
            }
            
            @Override
            public void onAnimationRepeat (Animation arg0) {
                // TODO Auto-generated method stub
                
            }
            @Override
            public void onAnimationStart (Animation arg0) {
                // TODO Auto-generated method stub
            }
        });
    }
    
    private void sendBidCredentialsToServer (final String offerAmount, final String offerDescription, final int checked) {
        if (NetworkConnection.isNetworkAvailable (getActivity ())) {
            Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), true);
            Utils.showLog (Log.INFO, "" + AppConfigTags.URL, AppConfigURL.URL_PROPERTY_OFFER_BID, true);
            StringRequest strRequest1 = new StringRequest (Request.Method.POST, AppConfigURL.URL_PROPERTY_OFFER_BID,
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
                                        // Utils.showToast (getActivity (), message, true);
    
                                        new MaterialDialog.Builder (getActivity ())
                                                .content (message)
                                                .positiveText ("OK")
                                                .show ();
    
    
                                    } else {
                                        Utils.showToast (getActivity (), message, true);
                                    }
                                    progressDialog.dismiss ();
                                } catch (Exception e) {
                                    progressDialog.dismiss ();
                                    Utils.showToast (getActivity (), getResources ().getString (R.string.snackbar_text_error_occurred), true);
                                    e.printStackTrace ();
                                }
                            } else {
                                Utils.showToast (getActivity (), getResources ().getString (R.string.snackbar_text_error_occurred), true);
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }
                            progressDialog.dismiss ();
                        }
                    },
                    new com.android.volley.Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            progressDialog.dismiss ();
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                            Utils.showToast (getActivity (), getResources ().getString (R.string.snackbar_text_error_occurred), true);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String> ();
                    params.put (AppConfigTags.BUYER_ID, String.valueOf (buyerDetailsPref.getIntPref (getActivity (), BuyerDetailsPref.BUYER_ID)));
                    params.put (AppConfigTags.PROPERTY_BID_AUCTION_ID, String.valueOf (String.valueOf (propertyDetailsPref.getIntPref (getActivity (), PropertyDetailsPref.PROPERTY_AUCTION_ID))));
                    params.put (AppConfigTags.PROPERTY_BID_COMMENTS, offerDescription);
                    params.put (AppConfigTags.TYPE, "property_submit_bid");
                    params.put ("is_access", String.valueOf (checked));
                    params.put (AppConfigTags.PROPERTY_BID_AMOUNT, offerAmount);
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
    
    private class setPropertyDetails extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground (String... params) {
            return "Executed";
        }
        
        @Override
        protected void onPostExecute (String result) {
            Log.e ("karman", "executed");
        }
        
        @Override
        protected void onPreExecute () {
        }
        
        @Override
        protected void onProgressUpdate (Void... values) {
        }
    }
    
    
}