package com.clearsale.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.clearsale.R;
import com.clearsale.utils.BuyerDetailsPref;
import com.clearsale.utils.SetTypeFace;
import com.clearsale.utils.Utils;


/**
 * Created by l on 13/04/2017.
 */

public class ChatSupportActivity extends AppCompatActivity {
    
    AppBarLayout appBar;
    Toolbar toolbar;
    // TextView tvHyperlink;
    RelativeLayout rlBack;
    WebView webviewChatSupport;
    BuyerDetailsPref buyerDetailsPref;
    ProgressBar progressBar;
    FrameLayout fl1;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_chat_support);
        initView ();
        initData ();
        initListener ();
    }
    
    private void initView () {
        rlBack = (RelativeLayout) findViewById (R.id.rlBack);
        appBar = (AppBarLayout) findViewById (R.id.appbar);
        toolbar = (Toolbar) findViewById (R.id.toolbar);
        //  tvHyperlink=(TextView)findViewById(R.id.tvHyperlink);
        webviewChatSupport = (WebView) findViewById (R.id.webviewChatSupport);
        progressBar = (ProgressBar) findViewById (R.id.progressBar);
        fl1 = (FrameLayout) findViewById (R.id.fl1);
    }
    
    @SuppressLint("JavascriptInterface")
    private void initData () {
        buyerDetailsPref = BuyerDetailsPref.getInstance ();

//        appBar.setExpanded (true);
        Utils.setTypefaceToAllViews (this, rlBack);
        
        
        CookieManager cookieMgr = CookieManager.getInstance ();
        cookieMgr.setAcceptCookie (true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieMgr.setAcceptThirdPartyCookies (webviewChatSupport, true);
        }
        webviewChatSupport.setWebChromeClient (new WebChromeClient ());
        final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface (ChatSupportActivity.this);
        webviewChatSupport.addJavascriptInterface (myJavaScriptInterface, "AndroidFunction");
        
        
        webviewChatSupport.loadUrl ("http://clearsale.com/api/chat_support.php");
        
        webviewChatSupport.getSettings ().setJavaScriptEnabled (true);
        webviewChatSupport.setWebChromeClient (new WebChromeClient ());
        
        //webView.getSettings ().setJavaScriptEnabled (true);
        //CookieManager.getInstance().setAcceptCookie(true);
        webviewChatSupport.getSettings ().setGeolocationEnabled (true);
        webviewChatSupport.getSettings ().setJavaScriptCanOpenWindowsAutomatically (true);
        webviewChatSupport.getSettings ().setSupportMultipleWindows (true);
        webviewChatSupport.getSettings ().setSaveFormData (false);
        webviewChatSupport.setHorizontalScrollBarEnabled (true);
        webviewChatSupport.setVerticalScrollBarEnabled (true);
        webviewChatSupport.getSettings ().setCacheMode (webviewChatSupport.getSettings ().LOAD_NO_CACHE);
        webviewChatSupport.setWebChromeClient (new MyWebChromeClient ());
        
        
        if (Build.VERSION.SDK_INT >= 21) {
            progressBar.setProgressTintList (ColorStateList.valueOf (getResources ().getColor (R.color.primary_dark)));
            progressBar.setIndeterminateTintList (ColorStateList.valueOf (getResources ().getColor (R.color.primary_dark)));
        } else {
            progressBar.getProgressDrawable ().setColorFilter (
                    getResources ().getColor (R.color.primary_dark), android.graphics.PorterDuff.Mode.SRC_IN);
            progressBar.getIndeterminateDrawable ().setColorFilter (
                    getResources ().getColor (R.color.primary_dark), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        
        
    }
    
    private void initListener () {
        
        
        rlBack.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
                overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        webviewChatSupport.setWebViewClient (new WebViewClient () {
            public void onPageFinished (WebView view, String url) {
                fl1.setVisibility (View.GONE);
            }
        });
        
    }
    
    @Override
    public void onBackPressed () {
        finish ();
        overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
    }
    
    
    final class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged (WebView view, int progress) {
            if (progress < 30) {
                progressBar.setIndeterminate (true);
            }
            if (progress > 70) {
                progressBar.setIndeterminate (true);
            }
            if (progress < 70 && progress > 30) {
                progressBar.setIndeterminate (false);
                progressBar.setProgress (progress + 10);
            }
        }
        
        @Override
        public boolean onJsAlert (WebView view, String url, String message, final JsResult result) {
            MaterialDialog dialog = new MaterialDialog.Builder (ChatSupportActivity.this)
                    .alwaysCallInputCallback ()
                    .content (message.trim ())
                    .canceledOnTouchOutside (false)
                    .positiveText ("OK")
                    .typeface (SetTypeFace.getTypeface (ChatSupportActivity.this), SetTypeFace.getTypeface (ChatSupportActivity.this))
                    .onPositive (new MaterialDialog.SingleButtonCallback () {
                        @Override
                        public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            ChatSupportActivity.this.finish ();
                            result.confirm ();
                            dialog.dismiss ();
                        }
                    }).build ();
            dialog.show ();
            return true;
        }
        
        @Override
        public boolean onJsConfirm (WebView view, String url, String message, final JsResult result) {
            MaterialDialog dialog = new MaterialDialog.Builder (ChatSupportActivity.this)
                    .alwaysCallInputCallback ()
                    .content (message.trim ())
                    .canceledOnTouchOutside (false)
                    .positiveText ("OK")
                    .typeface (SetTypeFace.getTypeface (ChatSupportActivity.this), SetTypeFace.getTypeface (ChatSupportActivity.this))
                    .onPositive (new MaterialDialog.SingleButtonCallback () {
                        @Override
                        public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            ChatSupportActivity.this.finish ();
                            result.confirm ();
                            dialog.dismiss ();
                        }
                    }).build ();
            dialog.show ();
            return true;
        }
        
    }
    
    public class MyJavaScriptInterface {
        Context mContext;
        
        MyJavaScriptInterface (Context c) {
            mContext = c;
        }
        
        
    }
    
    
}
