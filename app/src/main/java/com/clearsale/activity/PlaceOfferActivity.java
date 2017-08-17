package com.clearsale.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.clearsale.R;
import com.clearsale.utils.AppConfigTags;
import com.clearsale.utils.SetTypeFace;
import com.clearsale.utils.Utils;

public class PlaceOfferActivity extends AppCompatActivity {
    WebView webviewPlaceAnOffer;
    Intent intent;
    RelativeLayout rlBack;
    ProgressDialog progressDialog;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_place_an_offer);
        initView ();
        getIntentValue ();
        initData ();
        initListener ();
    }
    
    private void getIntentValue () {
        intent = getIntent ();
    }
    
    private void initData () {
        progressDialog = new ProgressDialog (PlaceOfferActivity.this);
        Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), true);
    
        webviewPlaceAnOffer.loadUrl ("http://hometrustaustin.com/newtheme/buyers/app_view?property_id=" + intent.getIntExtra (AppConfigTags.PROPERTY_ID, 0) + "&buyer_id=" + intent.getIntExtra (AppConfigTags.BUYER_ID, 0) + "&auction_id=" + intent.getIntExtra (AppConfigTags.PROPERTY_BID_AUCTION_ID, 0) + "");
        webviewPlaceAnOffer.getSettings ().setJavaScriptEnabled (true);
        webviewPlaceAnOffer.setWebChromeClient (new MyWebChromeClient ());
        Log.e ("URL", "http://hometrustaustin.com/newtheme/buyers/app_view?property_id=" + intent.getIntExtra (AppConfigTags.PROPERTY_ID, 0) + "&buyer_id=" + intent.getIntExtra (AppConfigTags.BUYER_ID, 0) + "&auction_id=" + intent.getIntExtra (AppConfigTags.PROPERTY_BID_AUCTION_ID, 0));
        Utils.setTypefaceToAllViews (this, rlBack);
    }
    
    private void initListener () {
        webviewPlaceAnOffer.setWebViewClient (new WebViewClient () {
            public void onPageFinished (WebView view, String url) {
                progressDialog.dismiss ();
            }
        });
        rlBack.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
            }
        });
    }
    
    private void initView () {
        webviewPlaceAnOffer = (WebView) findViewById (R.id.webviewPlaceAnOffer);
        rlBack = (RelativeLayout) findViewById (R.id.rlBack);
    }
    
    
    final class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert (WebView view, String url, String message, final android.webkit.JsResult result) {
            MaterialDialog dialog = new MaterialDialog.Builder (PlaceOfferActivity.this)
                    .alwaysCallInputCallback ()
                    .content (message.trim ())
                    .canceledOnTouchOutside (false)
                    .positiveText ("OK")
                    .typeface (SetTypeFace.getTypeface (PlaceOfferActivity.this), SetTypeFace.getTypeface (PlaceOfferActivity.this))
                    .onPositive (new MaterialDialog.SingleButtonCallback () {
                        @Override
                        public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            finish ();
                            result.confirm ();
                            dialog.dismiss ();
                        }
                    }).build ();
            dialog.show ();
            return true;
        }
        
        @Override
        public boolean onJsConfirm (WebView view, String url, String message, final JsResult result) {
            MaterialDialog dialog = new MaterialDialog.Builder (PlaceOfferActivity.this)
                    .alwaysCallInputCallback ()
                    .content (message.trim ())
                    .canceledOnTouchOutside (false)
                    .positiveText ("OK")
                    .typeface (SetTypeFace.getTypeface (PlaceOfferActivity.this), SetTypeFace.getTypeface (PlaceOfferActivity.this))
                    .onPositive (new MaterialDialog.SingleButtonCallback () {
                        @Override
                        public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            finish ();
                            result.confirm ();
                            dialog.dismiss ();
                        }
                    }).build ();
            dialog.show ();
            return true;
        }
    }
}