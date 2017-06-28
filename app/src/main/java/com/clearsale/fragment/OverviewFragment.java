package com.clearsale.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.clearsale.R;
import com.clearsale.activity.ScheduleTourActivity;
import com.clearsale.utils.AppConfigTags;
import com.clearsale.utils.AppConfigURL;
import com.clearsale.utils.BuyerDetailsPref;
import com.clearsale.utils.Constants;
import com.clearsale.utils.NetworkConnection;
import com.clearsale.utils.PropertyDetailsPref;
import com.clearsale.utils.SetTypeFace;
import com.clearsale.utils.Utils;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static com.clearsale.R.id.cardview3;


/**
 * Created by l on 23/03/2017.
 */

public class OverviewFragment extends Fragment {
    //  ProgressDialog progressDialog;
    TextView tvOverView;
    PropertyDetailsPref propertyDetailsPref;
    // WebView webView;
    WebView webView;
    WebSettings webSettings;
    
    TextView tvYear;
    TextView tvBedroom;
    TextView tvBathroom;
    TextView tvPropertyRate;
    TextView tvStatus;
    TextView tvAddress1;
    TextView tvAddress2;
    TextView tvSqFeet;
    
    
    TextView tvType;
    TextView tvKeyZoining;
    TextView tvKeyRentalFix;
    TextView tvYearOfConstruction;
    TextView tvAddition;
    TextView tvBedroomExisting;
    TextView tvBathroomExisting;
    TextView tvARVEstimate;
    TextView tvFixEstimate;
    TextView tvLotSize;
    TextView tvTotalSquareFeet;
    
    
    EditText etOfferAmount;
    EditText etOfferDescription;
    CheckBox cbAttendedAccess;
    TextView tvSubmit;
    CardView cvPropertyOffer;
    ProgressDialog progressDialog;
    BuyerDetailsPref buyerDetailsPref;
    int checked;
    
    CardView cardView3;
    Button btShowMore;
    TextView tv4;
    boolean show = true;
    
    CardView cardview4;
    Button btShowMoreRealtor;
    boolean showRealtor = true;
    WebView webViewRealtor;
    TextView tv6;
    
    WebView webViewKeyDetail;
    Button btShowMoreKeyDetail;
    boolean showKeyDetail = true;
    TextView tv7;
    CardView cardview6;
    
    
    TextView tvScheduleTour;
    
    
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
        tv4 = (TextView) rootView.findViewById (R.id.tv4);
    
        cardView3 = (CardView) rootView.findViewById (cardview3);
        btShowMore = (Button) rootView.findViewById (R.id.btShowMore);
    
        tvYear = (TextView) rootView.findViewById (R.id.tvYear);
        tvBedroom = (TextView) rootView.findViewById (R.id.tvBedroom);
        tvBathroom = (TextView) rootView.findViewById (R.id.tvBathroom);
        tvPropertyRate = (TextView) rootView.findViewById (R.id.tvPropertyRate);
        tvStatus = (TextView) rootView.findViewById (R.id.tvStatus);
        tvAddress1 = (TextView) rootView.findViewById (R.id.tvAddress1);
        tvAddress2 = (TextView) rootView.findViewById (R.id.tvAddress2);
        tvSqFeet = (TextView) rootView.findViewById (R.id.tvSqFeet);
    
        tvScheduleTour = (TextView) rootView.findViewById (R.id.tvScheduleTour);
        tvOverView = (TextView) rootView.findViewById (R.id.tvOverView);
        webView = (WebView) rootView.findViewById (R.id.webView1);
        etOfferDescription = (EditText) rootView.findViewById (R.id.etOfferDetail);
        cbAttendedAccess = (CheckBox) rootView.findViewById (R.id.cbAttendedAccess);
        tvSubmit = (TextView) rootView.findViewById (R.id.tvSubmit);
        progressDialog = new ProgressDialog (getActivity ());
        cvPropertyOffer = (CardView) rootView.findViewById (R.id.cardview2);
    
        cardview4 = (CardView) rootView.findViewById (R.id.cardview4);
        btShowMoreRealtor = (Button) rootView.findViewById (R.id.btShowMoreRealtor);
        webViewRealtor = (WebView) rootView.findViewById (R.id.webViewRealtor);
        tv6 = (TextView) rootView.findViewById (R.id.tv6);
        // clMain = (CoordinatorLayout)rootView.findViewById(R.id.clMain);
    
    
        tvType = (TextView) rootView.findViewById (R.id.tvKeyType);
        tvKeyZoining = (TextView) rootView.findViewById (R.id.tvKeyZoining);
        tvKeyRentalFix = (TextView) rootView.findViewById (R.id.tvKeyRentalFix);
        tvYearOfConstruction = (TextView) rootView.findViewById (R.id.tvYearOfConstruction);
        tvBedroomExisting = (TextView) rootView.findViewById (R.id.tvBedroomExisting);
        tvBathroomExisting = (TextView) rootView.findViewById (R.id.tvBathroomExisting);
        tvARVEstimate = (TextView) rootView.findViewById (R.id.tvARVEstimate);
        tvAddition = (TextView) rootView.findViewById (R.id.tvAddition);
        tvFixEstimate = (TextView) rootView.findViewById (R.id.tvFixEstimate);
        tvLotSize = (TextView) rootView.findViewById (R.id.tvLotSize);
        tvTotalSquareFeet = (TextView) rootView.findViewById (R.id.tvTotalSquareFeet);
    
        webViewKeyDetail = (WebView) rootView.findViewById (R.id.webViewKeyDetail);
        btShowMoreKeyDetail = (Button) rootView.findViewById (R.id.btShowMoreKeyDetail);
        cardview6 = (CardView) rootView.findViewById (R.id.cardview6);
        tv7 = (TextView) rootView.findViewById (R.id.tv7);
    
    }
    
    private void initData () {
        buyerDetailsPref = BuyerDetailsPref.getInstance ();
        propertyDetailsPref = PropertyDetailsPref.getInstance ();
        
        tvOverView.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_OVERVIEW));
    
    
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
                img.setBounds (0, 0, 30, 30);
                tvStatus.setCompoundDrawables (img, null, null, null);
                tvStatus.setText ("Available");
                break;
            case 2:
                Drawable img2 = getActivity ().getResources ().getDrawable (R.drawable.circle_yellow);
                img2.setBounds (0, 0, 30, 30);
                tvStatus.setCompoundDrawables (img2, null, null, null);
                tvStatus.setText ("Pending");
                break;
            case 3:
                Drawable img3 = getActivity ().getResources ().getDrawable (R.drawable.circle_red);
                img3.setBounds (0, 0, 30, 30);
                tvStatus.setCompoundDrawables (img3, null, null, null);
                tvStatus.setText ("Sold");
                break;
            case 4:
                Drawable img4 = getActivity ().getResources ().getDrawable (R.drawable.circle_red);
                img4.setBounds (0, 0, 30, 30);
                tvStatus.setCompoundDrawables (img4, null, null, null);
                tvStatus.setText ("Closed");
                break;
            case 9:
                Drawable img9 = getActivity ().getResources ().getDrawable (R.drawable.circle_red);
                img9.setBounds (0, 0, 30, 30);
                tvStatus.setCompoundDrawables (img9, null, null, null);
                tvStatus.setText ("Offer Window Closing");
                break;
        }
    
        Document doc = Jsoup.parse (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_OVERVIEW));
        
        
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_ARV));
        webView.loadDataWithBaseURL ("www.google.com", spannableStringBuilder.toString (), "text/html", "UTF-8", "");
//        Log.e ("karman", "<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ".otf);}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_OVERVIEW));
        
        
        WebSettings webSettings = webView.getSettings ();
        webSettings.setStandardFontFamily (Constants.font_name);
        
        Utils.setTypefaceToAllViews (getActivity (), tvSubmit);
        
        if (propertyDetailsPref.getIntPref (getActivity (), PropertyDetailsPref.PROPERTY_AUCTION_STATUS) == 1) {
            tv4.setVisibility (View.VISIBLE);
            cvPropertyOffer.setVisibility (View.VISIBLE);
        } else {
            tv4.setVisibility (View.GONE);
            cvPropertyOffer.setVisibility (View.GONE);
        }
    
        SpannableStringBuilder spannableStringBuilderRealtor = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_REALTOR));
        Log.e ("Realtor", spannableStringBuilderRealtor.toString ());
        webViewRealtor.loadDataWithBaseURL ("www.google.com", spannableStringBuilderRealtor.toString (), "text/html", "UTF-8", "");
    
    
        tvType.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_KEY_TYPE));
        tvKeyZoining.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_KEY_ZOINING));
        tvKeyRentalFix.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_KEY_RENTAL_FIX));
        tvYearOfConstruction.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_KEY_BUILD_YEAR));
        tvBedroomExisting.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_KEY_BEDROOM_EXISTING));
        tvBathroomExisting.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_KEY_BATH_EXISTING));
        tvARVEstimate.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_KEY_ARV_ESTIMATE));
        tvFixEstimate.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_KEY_FIX_ESTIMATE));
        tvAddition.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_KEY_ADDITION));
        tvLotSize.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_KEY_LOT_SQFT));
        tvTotalSquareFeet.setText (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_KEY_TOTAL_SQUARE_FEET));
    
        if (propertyDetailsPref.getIntPref (getActivity (), PropertyDetailsPref.PROPERTY_TOUR_STATUS) == 1) {
            tvScheduleTour.setVisibility (View.VISIBLE);
        } else {
            tvScheduleTour.setVisibility (View.GONE);
        }
    }
    
    private void initListener () {
        btShowMore.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                if (show) {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    params.addRule (RelativeLayout.BELOW, R.id.tv5);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardView3.setLayoutParams (params);
                    btShowMore.setText ("SHOW LESS");
                    show = false;
    
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
//                            Log.e ("karman", "wrap contant height", + cardView3.get);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            params.addRule (RelativeLayout.BELOW, R.id.tv5);
                            params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
            
                            //39.559035,-104.70355899999998
                            //39.5575492,-104.7034232
                            cardView3.setLayoutParams (params);
                        }
                    };
                    a.setDuration (2000); // in ms
                    cardView3.startAnimation (a);
                } else {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
//                    params.addRule (RelativeLayout.BELOW, R.id.tv5);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardView3.setLayoutParams (params);
                    btShowMore.setText ("SHOW MORE");
                    show = true;
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            if ((1.0f - interpolatedTime) < 1.0f) {
                                if ((cardView3.getHeight () * (1.0f - interpolatedTime)) <= Utils.pxFromDp (getActivity (), 200.0f)) {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
                                    params.addRule (RelativeLayout.BELOW, R.id.tv5);
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cardView3.setLayoutParams (params);
                    
                                } else {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (cardView3.getHeight () * (1.0f - interpolatedTime)));
                                    params.addRule (RelativeLayout.BELOW, R.id.tv5);
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cardView3.setLayoutParams (params);
                    
                                }
                            }
                        }
                    };
                    a.setDuration (2000); // in ms
                    cardView3.startAnimation (a);
                }
            }
        });
    
        btShowMoreRealtor.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                if (showRealtor) {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    params.addRule (RelativeLayout.BELOW, R.id.tv6);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardview4.setLayoutParams (params);
                    btShowMoreRealtor.setText ("SHOW LESS");
                    showRealtor = false;
    
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            params.addRule (RelativeLayout.BELOW, R.id.tv6);
                            params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                            cardview4.setLayoutParams (params);
                        }
                    };
                    a.setDuration (1500); // in ms
                    cardview4.startAnimation (a);
                } else {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
//                    params.addRule (RelativeLayout.BELOW, R.id.tv6);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardview4.setLayoutParams (params);
                    btShowMoreRealtor.setText ("SHOW MORE");
                    showRealtor = true;
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
            
                            if ((1.0f - interpolatedTime) < 1.0f) {
                                if ((cardview4.getHeight () * (1.0f - interpolatedTime)) <= Utils.pxFromDp (getActivity (), 200.0f)) {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
                                    params.addRule (RelativeLayout.BELOW, R.id.tv6);
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cardview4.setLayoutParams (params);
                    
                                } else {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (cardview4.getHeight () * (1.0f - interpolatedTime)));
                                    params.addRule (RelativeLayout.BELOW, R.id.tv6);
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cardview4.setLayoutParams (params);
                    
                                }
                            }
            
                        }
                    };
                    a.setDuration (2000); // in ms
                    cardview4.startAnimation (a);
                }
            }
        });
    
    
        btShowMoreKeyDetail.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                if (showKeyDetail) {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    params.addRule (RelativeLayout.BELOW, R.id.tv6);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardview4.setLayoutParams (params);
                    btShowMoreKeyDetail.setText ("SHOW LESS");
                    showKeyDetail = false;
                
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            params.addRule (RelativeLayout.BELOW, R.id.tv7);
                            params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                            cardview6.setLayoutParams (params);
                        }
                    };
                    a.setDuration (1500); // in ms
                    cardview6.startAnimation (a);
                } else {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
//                    params.addRule (RelativeLayout.BELOW, R.id.tv6);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardview4.setLayoutParams (params);
                    btShowMoreKeyDetail.setText ("SHOW MORE");
                    showKeyDetail = true;
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                        
                            if ((1.0f - interpolatedTime) < 1.0f) {
                                if ((cardview6.getHeight () * (1.0f - interpolatedTime)) <= Utils.pxFromDp (getActivity (), 200.0f)) {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
                                    params.addRule (RelativeLayout.BELOW, R.id.tv7);
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cardview6.setLayoutParams (params);
                                
                                } else {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (cardview4.getHeight () * (1.0f - interpolatedTime)));
                                    params.addRule (RelativeLayout.BELOW, R.id.tv7);
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cardview6.setLayoutParams (params);
                                }
                            }
                        }
                    };
                    a.setDuration (2000); // in ms
                    cardview6.startAnimation (a);
                }
            }
        });

        tvSubmit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                if (cbAttendedAccess.isChecked ()) {
                    checked = 1;
                } else {
                    checked = 0;
                }
                sendBidCredentialsToServer (etOfferAmount.getText ().toString ().trim (), etOfferDescription.getText ().toString ().trim (), checked);
            }
        });
    
        tv4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                MaterialDialog dialog = new MaterialDialog.Builder (getActivity ())
                        .limitIconToDefaultSize ()
                        .canceledOnTouchOutside (false)
                        .onNegative (new MaterialDialog.SingleButtonCallback () {
                            @Override
                            public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            
                                dialog.dismiss ();
                            }
                        })
                        .negativeText ("CANCEL")
                        .onPositive (new MaterialDialog.SingleButtonCallback () {
                            @Override
                            public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                final String etOfferAmount = ((EditText) dialog.getCustomView ().findViewById (R.id.etOfferUsd)).getText ().toString ();
                                final String etOfferDescription = ((EditText) dialog.getCustomView ().findViewById (R.id.etOfferDetail)).getText ().toString ();
                                CheckBox cbAttendedAccess = (CheckBox) dialog.getCustomView ().findViewById (R.id.cbAttendedAccess);
                                if (cbAttendedAccess.isChecked ()) {
                                    checked = 1;
                                } else {
                                    checked = 0;
                                }
                                if (etOfferAmount.length () > 0) {
                                    sendBidCredentialsToServer (etOfferAmount, etOfferDescription, checked);
                                } else {
                                    Utils.showToast (getActivity (), "Please Enter Amount", false);
                                }
                                dialog.dismiss ();
                            }
                        })
                        .positiveText ("SUBMIT")
                        .positiveColor (getResources ().getColor (R.color.primary))
                        .customView (R.layout.dialog_place_an_offer, false)
                        .typeface (SetTypeFace.getTypeface (getActivity ()), SetTypeFace.getTypeface (getActivity ()))
                        .build ();
    
    
                final WebView webView = (WebView) dialog.findViewById (R.id.webView1);
                // final Button btShowMore1 = (Button) dialog.findViewById (R.id.btShowMore1);
                SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_OFFER));
                webView.loadDataWithBaseURL ("www.google.com", spannableStringBuilder2.toString (), "text/html", "UTF-8", "");
                dialog.show ();
            
            }

        });
    
        tvScheduleTour.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                Intent scheduleTour = new Intent (getActivity (), ScheduleTourActivity.class);
                scheduleTour.putExtra (AppConfigTags.PROPERTY_ID, propertyDetailsPref.getIntPref (getActivity (), PropertyDetailsPref.PROPERTY_ID));
                startActivity (scheduleTour);
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
                                        Utils.showToast (getActivity (), message, true);
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
}