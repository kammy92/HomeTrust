package com.clearsale.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
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
import com.clearsale.utils.SetTypeFace;
import com.clearsale.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static com.clearsale.R.id.cardview3;
import static com.clearsale.R.id.cardview8;
import static com.clearsale.R.id.cardviewWorkScope;


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
    TextView tv8;
    CardView cardView8;
    Button btShowMoreAccessPossession;
    WebView webViewAccessPossession;
    boolean showAccessPossession = true;
    boolean showofferDialog = true;
    TextView tv7;
    CardView cardview6;
    TextView tvScheduleTour;
    EditText etOfferUsd;
    RelativeLayout rlDescription;
    LinearLayout llLoading;

    RelativeLayout rlKeyDetails;
    TableLayout llLoading2;

    RelativeLayout rlRealtor;
    LinearLayout llLoading3;

    RelativeLayout rlPossession;
    LinearLayout llLoading4;
    
    RelativeLayout rlWorkScopeMain;
    RelativeLayout rlWorkScope;
    TextView tvWorkScopeText;
    CardView cardViewWorkScope;
    Button btShowMoreWorkScope;
    WebView webViewWorkScope;
    LinearLayout llLoadingWorkScope;
    boolean showWorkScope = true;
    
    RelativeLayout rlFinishedProductMain;
    RelativeLayout rlFinishedProduct;
    TextView tvFinishedProductText;
    CardView cardViewFinishedProduct;
    Button btShowMoreFinishedProduct;
    WebView webViewFinishedProduct;
    LinearLayout llLoadingFinishedProduct;
    boolean showFinishedProduct = true;
    
    RelativeLayout rlClosingDetailsMain;
    RelativeLayout rlClosingDetails;
    TextView tvClosingDetailsText;
    CardView cardViewClosingDetails;
    Button btShowMoreClosingDetails;
    WebView webViewClosingDetails;
    LinearLayout llLoadingClosingDetails;
    boolean showClosingDetails = true;

    Animation animation1, animation2;
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
        tv4 = (TextView) rootView.findViewById (R.id.tv4);

        cardView3 = (CardView) rootView.findViewById (cardview3);
        rlDescription = (RelativeLayout) rootView.findViewById (R.id.rlDescription);
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
        rlRealtor = (RelativeLayout) rootView.findViewById (R.id.rlRealtor);
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

        rlKeyDetails = (RelativeLayout) rootView.findViewById (R.id.rlKeyDetails);
        webViewKeyDetail = (WebView) rootView.findViewById (R.id.webViewKeyDetail);
        btShowMoreKeyDetail = (Button) rootView.findViewById (R.id.btShowMoreKeyDetail);
        cardview6 = (CardView) rootView.findViewById (R.id.cardview6);
        tv7 = (TextView) rootView.findViewById (R.id.tv7);

        rlPossession = (RelativeLayout) rootView.findViewById (R.id.rlPossession);
        webViewAccessPossession = (WebView) rootView.findViewById (R.id.webViewAccessPossession);
        btShowMoreAccessPossession = (Button) rootView.findViewById (R.id.btShowMoreAccessPossession);
        cardView8 = (CardView) rootView.findViewById (cardview8);
        tv8 = (TextView) rootView.findViewById (R.id.tv8);
    
        rlWorkScopeMain = (RelativeLayout) rootView.findViewById (R.id.rlWorkScopeMain);
        rlWorkScope = (RelativeLayout) rootView.findViewById (R.id.rlWorkScope);
        webViewWorkScope = (WebView) rootView.findViewById (R.id.webViewWorkScope);
        btShowMoreWorkScope = (Button) rootView.findViewById (R.id.btShowMoreWorkScope);
        cardViewWorkScope = (CardView) rootView.findViewById (cardviewWorkScope);
        tvWorkScopeText = (TextView) rootView.findViewById (R.id.tvWorkScopeText);
        llLoadingWorkScope = (LinearLayout) rootView.findViewById (R.id.llLoadingWorkScope);
    
        rlFinishedProductMain = (RelativeLayout) rootView.findViewById (R.id.rlFinishedProductMain);
        rlFinishedProduct = (RelativeLayout) rootView.findViewById (R.id.rlFinishedProduct);
        webViewFinishedProduct = (WebView) rootView.findViewById (R.id.webViewFinishedProduct);
        btShowMoreFinishedProduct = (Button) rootView.findViewById (R.id.btShowMoreFinishedProduct);
        cardViewFinishedProduct = (CardView) rootView.findViewById (R.id.cardviewFinishedProduct);
        tvFinishedProductText = (TextView) rootView.findViewById (R.id.tvFinishedProductText);
        llLoadingFinishedProduct = (LinearLayout) rootView.findViewById (R.id.llLoadingFinishedProduct);
    
        rlClosingDetailsMain = (RelativeLayout) rootView.findViewById (R.id.rlClosingDetailsMain);
        rlClosingDetails = (RelativeLayout) rootView.findViewById (R.id.rlClosingDetails);
        webViewClosingDetails = (WebView) rootView.findViewById (R.id.webViewClosingDetails);
        btShowMoreClosingDetails = (Button) rootView.findViewById (R.id.btShowMoreClosingDetails);
        cardViewClosingDetails = (CardView) rootView.findViewById (R.id.cardviewClosingDetails);
        tvClosingDetailsText = (TextView) rootView.findViewById (R.id.tvClosingDetailsText);
        llLoadingClosingDetails = (LinearLayout) rootView.findViewById (R.id.llLoadingClosingDetails);

        llLoading = (LinearLayout) rootView.findViewById (R.id.llLoading);
        llLoading2 = (TableLayout) rootView.findViewById (R.id.llLoading2);
        llLoading3 = (LinearLayout) rootView.findViewById (R.id.llLoading3);
        llLoading4 = (LinearLayout) rootView.findViewById (R.id.llLoading4);
    }

    private void initData () {
        buyerDetailsPref = BuyerDetailsPref.getInstance ();
        propertyDetailsPref = PropertyDetailsPref.getInstance ();

        animation1 = new AlphaAnimation (0.5f, 1.0f);
        animation1.setDuration (2000);
        animation2 = new AlphaAnimation (1.0f, 0.5f);
        animation2.setDuration (2000);

        llLoading.startAnimation (animation2);
        llLoading2.startAnimation (animation2);
        llLoading3.startAnimation (animation2);
        llLoading4.startAnimation (animation2);
        llLoadingWorkScope.startAnimation (animation2);
        llLoadingFinishedProduct.startAnimation (animation2);
        llLoadingClosingDetails.startAnimation (animation2);
    
        //  llLoadingWorkScope.startAnimation(animation2);


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
    
        if (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_ARV).length () > 0) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_ARV));
            webView.loadDataWithBaseURL ("www.google.com", spannableStringBuilder.toString (), "text/html", "UTF-8", "");
            rlDescription.setVisibility (View.VISIBLE);
        } else {
            rlDescription.setVisibility (View.GONE);
        }

        if (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_KEY_DETAILS).length () > 0) {
            SpannableStringBuilder spannableStringBuilderKeyDetail = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_KEY_DETAILS));
            webViewKeyDetail.loadDataWithBaseURL ("www.google.com", spannableStringBuilderKeyDetail.toString (), "text/html", "UTF-8", "");
            rlKeyDetails.setVisibility (View.VISIBLE);
        } else {
            rlKeyDetails.setVisibility (View.GONE);
        }
    
        if (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_REALTOR).length () > 0) {
            SpannableStringBuilder spannableStringBuilderRealtor = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_REALTOR));
            webViewRealtor.loadDataWithBaseURL ("www.google.com", spannableStringBuilderRealtor.toString (), "text/html", "UTF-8", "");
            rlRealtor.setVisibility (View.VISIBLE);
        } else {
            rlRealtor.setVisibility (View.GONE);
        }

       /* if (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_REALTOR).length () > 0) {
            SpannableStringBuilder spannableAccessPossession = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_ACCESS));
            webViewAccessPossession.loadDataWithBaseURL ("www.google.com", spannableAccessPossession.toString (), "text/html", "UTF-8", "");
            rlPossession.setVisibility (View.VISIBLE);
        } else {
            rlPossession.setVisibility (View.GONE);
        }*/
        Log.e ("WORKSCOPE", propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_WORK_SCOPE));
        if (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_WORK_SCOPE).length () > 0) {
            SpannableStringBuilder spannableAccessWorkScope = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_WORK_SCOPE));
            webViewWorkScope.loadDataWithBaseURL ("www.google.com", spannableAccessWorkScope.toString (), "text/html", "UTF-8", "");
            rlWorkScopeMain.setVisibility (View.VISIBLE);
        } else {
            rlWorkScopeMain.setVisibility (View.GONE);
        }
    
        if (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_FINISHED_PRODUCT).length () > 0) {
            SpannableStringBuilder spannableAccessFinishedProduct = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_FINISHED_PRODUCT));
            webViewFinishedProduct.loadDataWithBaseURL ("www.google.com", spannableAccessFinishedProduct.toString (), "text/html", "UTF-8", "");
            rlFinishedProductMain.setVisibility (View.VISIBLE);
        } else {
            rlFinishedProductMain.setVisibility (View.GONE);
        }
    
    
        if (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_CLOSING_DETAILS).length () > 0) {
            SpannableStringBuilder spannableAccessClosingDetails = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_CLOSING_DETAILS));
            webViewClosingDetails.loadDataWithBaseURL ("www.google.com", spannableAccessClosingDetails.toString (), "text/html", "UTF-8", "");
            rlClosingDetailsMain.setVisibility (View.VISIBLE);
        } else {
            rlClosingDetailsMain.setVisibility (View.GONE);
        }



        Utils.setTypefaceToAllViews (getActivity (), tvSubmit);
    
        if (propertyDetailsPref.getIntPref (getActivity (), PropertyDetailsPref.PROPERTY_AUCTION_STATUS) == 1) {
            tv4.setVisibility (View.VISIBLE);
            cvPropertyOffer.setVisibility (View.VISIBLE);
        } else {
            tv4.setVisibility (View.GONE);
            cvPropertyOffer.setVisibility (View.GONE);
        }
    
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
                    btShowMore.setText ("LESS");
                    show = false;
    
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            params.addRule (RelativeLayout.BELOW, R.id.tv5);
                            params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
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
                    btShowMore.setText ("MORE");
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
        
        
        btShowMoreKeyDetail.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                if (showKeyDetail) {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    params.addRule (RelativeLayout.BELOW, R.id.tv6);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardview4.setLayoutParams (params);
                    btShowMoreKeyDetail.setText ("HIDE FULL DETAILS");
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
                    btShowMoreKeyDetail.setText ("SHOW FULL DETAILS");
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
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (cardview6.getHeight () * (1.0f - interpolatedTime)));
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
                //    PlaceOffer ();
                Intent placeOffer = new Intent (getActivity (), PlaceOfferActivity.class);
                placeOffer.putExtra (AppConfigTags.PROPERTY_ID, propertyDetailsPref.getIntPref (getActivity (), PropertyDetailsPref.PROPERTY_ID));
                placeOffer.putExtra (AppConfigTags.BUYER_ID, buyerDetailsPref.getIntPref (getActivity (), BuyerDetailsPref.BUYER_ID));
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
        
        
        btShowMoreAccessPossession.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                if (showAccessPossession) {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    params.addRule (RelativeLayout.BELOW, R.id.tv6);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardview4.setLayoutParams (params);
                    btShowMoreAccessPossession.setText ("LESS");
                    showAccessPossession = false;
    
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            params.addRule (RelativeLayout.BELOW, R.id.tv8);
                            params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                            cardView8.setLayoutParams (params);
                        }
                    };
                    a.setDuration (1500); // in ms
                    cardView8.startAnimation (a);
                } else {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
//                    params.addRule (RelativeLayout.BELOW, R.id.tv6);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardview4.setLayoutParams (params);
                    btShowMoreAccessPossession.setText ("MORE");
                    showAccessPossession = true;
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            if ((1.0f - interpolatedTime) < 1.0f) {
                                if ((cardView8.getHeight () * (1.0f - interpolatedTime)) <= Utils.pxFromDp (getActivity (), 200.0f)) {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
                                    params.addRule (RelativeLayout.BELOW, R.id.tv8);
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cardView8.setLayoutParams (params);
                                } else {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (cardView8.getHeight () * (1.0f - interpolatedTime)));
                                    params.addRule (RelativeLayout.BELOW, R.id.tv8);
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cardView8.setLayoutParams (params);
                                }
                            }
                        }
                    };
                    a.setDuration (2000); // in ms
                    cardView8.startAnimation (a);
                }
            }
        });
        
        
        btShowMoreWorkScope.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                if (showWorkScope) {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    params.addRule (RelativeLayout.BELOW, R.id.tv6);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardview4.setLayoutParams (params);
                    btShowMoreWorkScope.setText ("LESS");
                    showWorkScope = false;
                    
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            params.addRule (RelativeLayout.BELOW, R.id.tvWorkScopeText);
                            params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                            cardViewWorkScope.setLayoutParams (params);
                        }
                    };
                    a.setDuration (1500); // in ms
                    cardViewWorkScope.startAnimation (a);
                } else {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
//                    params.addRule (RelativeLayout.BELOW, R.id.tv6);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardview4.setLayoutParams (params);
                    btShowMoreWorkScope.setText ("MORE");
                    showWorkScope = true;
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            if ((1.0f - interpolatedTime) < 1.0f) {
                                if ((cardViewWorkScope.getHeight () * (1.0f - interpolatedTime)) <= Utils.pxFromDp (getActivity (), 200.0f)) {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
                                    params.addRule (RelativeLayout.BELOW, R.id.tvWorkScopeText);
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cardViewWorkScope.setLayoutParams (params);
                                } else {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (cardViewWorkScope.getHeight () * (1.0f - interpolatedTime)));
                                    params.addRule (RelativeLayout.BELOW, R.id.tvWorkScopeText);
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cardViewWorkScope.setLayoutParams (params);
                                }
                            }
                        }
                    };
                    a.setDuration (2000); // in ms
                    cardViewWorkScope.startAnimation (a);
                }
            }
        });
        
        
        btShowMoreFinishedProduct.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                if (showFinishedProduct) {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    params.addRule (RelativeLayout.BELOW, R.id.tv6);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardview4.setLayoutParams (params);
                    btShowMoreFinishedProduct.setText ("LESS");
                    showFinishedProduct = false;
                    
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            params.addRule (RelativeLayout.BELOW, R.id.tvFinishedProductText);
                            params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                            cardViewFinishedProduct.setLayoutParams (params);
                        }
                    };
                    a.setDuration (1500); // in ms
                    cardViewFinishedProduct.startAnimation (a);
                } else {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
//                    params.addRule (RelativeLayout.BELOW, R.id.tv6);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardview4.setLayoutParams (params);
                    btShowMoreFinishedProduct.setText ("MORE");
                    showFinishedProduct = true;
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            if ((1.0f - interpolatedTime) < 1.0f) {
                                if ((cardViewFinishedProduct.getHeight () * (1.0f - interpolatedTime)) <= Utils.pxFromDp (getActivity (), 200.0f)) {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
                                    params.addRule (RelativeLayout.BELOW, R.id.tvFinishedProductText);
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cardViewFinishedProduct.setLayoutParams (params);
                                } else {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (cardViewFinishedProduct.getHeight () * (1.0f - interpolatedTime)));
                                    params.addRule (RelativeLayout.BELOW, R.id.tvFinishedProductText);
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cardViewFinishedProduct.setLayoutParams (params);
                                }
                            }
                        }
                    };
                    a.setDuration (2000); // in ms
                    cardViewFinishedProduct.startAnimation (a);
                }
            }
        });
        
        
        btShowMoreClosingDetails.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                if (showClosingDetails) {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    params.addRule (RelativeLayout.BELOW, R.id.tv6);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardview4.setLayoutParams (params);
                    btShowMoreClosingDetails.setText ("LESS");
                    showClosingDetails = false;
                    
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            params.addRule (RelativeLayout.BELOW, R.id.tvClosingDetailsText);
                            params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                            cardViewClosingDetails.setLayoutParams (params);
                        }
                    };
                    a.setDuration (1500); // in ms
                    cardViewClosingDetails.startAnimation (a);
                } else {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
//                    params.addRule (RelativeLayout.BELOW, R.id.tv6);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardview4.setLayoutParams (params);
                    btShowMoreClosingDetails.setText ("MORE");
                    showClosingDetails = true;
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            if ((1.0f - interpolatedTime) < 1.0f) {
                                if ((cardViewClosingDetails.getHeight () * (1.0f - interpolatedTime)) <= Utils.pxFromDp (getActivity (), 200.0f)) {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
                                    params.addRule (RelativeLayout.BELOW, R.id.tvClosingDetailsText);
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cardViewClosingDetails.setLayoutParams (params);
                                } else {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (cardViewClosingDetails.getHeight () * (1.0f - interpolatedTime)));
                                    params.addRule (RelativeLayout.BELOW, R.id.tvClosingDetailsText);
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cardViewClosingDetails.setLayoutParams (params);
                                }
                            }
                        }
                    };
                    a.setDuration (2000); // in ms
                    cardViewClosingDetails.startAnimation (a);
                }
            }
        });


        animation1.setAnimationListener (new Animation.AnimationListener () {
    
            @Override
            public void onAnimationEnd (Animation arg0) {
                // start animation2 when animation1 ends (continue)
                llLoading.startAnimation (animation2);
                llLoading2.startAnimation (animation2);
                llLoading3.startAnimation (animation2);
                llLoading4.startAnimation (animation2);
                llLoadingWorkScope.startAnimation (animation2);
                llLoadingFinishedProduct.startAnimation (animation2);
                llLoadingClosingDetails.startAnimation (animation2);
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
                // start animation1 when animation2 ends (repeat)
                llLoading.startAnimation (animation1);
                llLoading2.startAnimation (animation1);
                llLoading3.startAnimation (animation1);
                llLoading4.startAnimation (animation1);
                llLoadingWorkScope.startAnimation (animation2);
                llLoadingFinishedProduct.startAnimation (animation2);
                llLoadingClosingDetails.startAnimation (animation2);
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
    
    private void PlaceOffer () {
        MaterialDialog dialog = new MaterialDialog.Builder (getActivity ())
                .limitIconToDefaultSize ()
                .alwaysCallInputCallback ()
                .canceledOnTouchOutside (false)
                .positiveText ("SUBMIT")
                .positiveColor (getResources ().getColor (R.color.primary))
                .negativeText ("CANCEL")
                .typeface (SetTypeFace.getTypeface (getActivity ()), SetTypeFace.getTypeface (getActivity ()))
                .customView (R.layout.dialog_place_an_offer, false)
                .onNegative (new MaterialDialog.SingleButtonCallback () {
                    @Override
                    public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
    
                        dialog.dismiss ();
                    }
                })
        
        
                .onPositive (new MaterialDialog.SingleButtonCallback () {
                    @Override
                    public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.getActionButton (DialogAction.POSITIVE).setEnabled (false);
                        final String etOfferAmount = etOfferUsd.getText ().toString ();
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
                .build ();
        positiveAction = dialog.getActionButton (DialogAction.POSITIVE);
        etOfferUsd = (EditText) dialog.getCustomView ().findViewById (R.id.etOfferUsd);
        Utils.setTypefaceToAllViews (getActivity (), etOfferUsd);
        final WebView webView = (WebView) dialog.findViewById (R.id.webView1);
        final Button btShowMoreDialog = (Button) dialog.findViewById (R.id.btShowMoreDialog);
        final CardView cardviewOffer = (CardView) dialog.findViewById (R.id.cardviewOffer);
        
        
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_OFFER));
        webView.loadDataWithBaseURL ("www.google.com", spannableStringBuilder2.toString (), "text/html", "UTF-8", "");
        etOfferUsd.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {
            }
    
            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                positiveAction.setEnabled (s.toString ().trim ().length () > 0);
            }
    
            @Override
            public void afterTextChanged (Editable s) {
            }
        });
        
        dialog.show ();
        positiveAction.setEnabled (false);
        btShowMoreDialog.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                if (showofferDialog) {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    params.addRule (RelativeLayout.BELOW, R.id.tv5);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardView3.setLayoutParams (params);
                    btShowMoreDialog.setText ("LESS");
                    showofferDialog = false;
    
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            params.addRule (RelativeLayout.BELOW, R.id.llPropertyOffer);
                            params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                            cardviewOffer.setLayoutParams (params);
                        }
                    };
                    a.setDuration (2000); // in ms
                    cardviewOffer.startAnimation (a);
                } else {
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
//                    params.addRule (RelativeLayout.BELOW, R.id.tv5);
//                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
//                    cardView3.setLayoutParams (params);
                    btShowMoreDialog.setText ("MORE");
                    showofferDialog = true;
                    Animation a = new Animation () {
                        @Override
                        protected void applyTransformation (float interpolatedTime, Transformation t) {
                            if ((1.0f - interpolatedTime) < 1.0f) {
                                if ((cardviewOffer.getHeight () * (1.0f - interpolatedTime)) <= Utils.pxFromDp (getActivity (), 200.0f)) {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Utils.pxFromDp (getActivity (), 200.0f)));
                                    params.addRule (RelativeLayout.BELOW, R.id.llPropertyOffer);
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cardviewOffer.setLayoutParams (params);
                                } else {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT, (int) (cardviewOffer.getHeight () * (1.0f - interpolatedTime)));
                                    params.addRule (RelativeLayout.BELOW, R.id.llPropertyOffer);
                                    params.setMargins ((int) (Utils.pxFromDp (getActivity (), 8.0f)), 0, (int) (Utils.pxFromDp (getActivity (), 8.0f)), (int) (Utils.pxFromDp (getActivity (), 8.0f)));
                                    cardviewOffer.setLayoutParams (params);
                                }
                            }
                        }
                    };
                    a.setDuration (2000); // in ms
                    cardviewOffer.startAnimation (a);
                }
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