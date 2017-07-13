package com.clearsale.activity;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.clearsale.R;
import com.clearsale.utils.Constants;
import com.clearsale.utils.Utils;


/**
 * Created by l on 22/03/2017.
 */

public class AboutUsActivity extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBar;
    Toolbar toolbar;
   // TextView tvHyperlink;
    RelativeLayout rlBack;
    WebView webViewAboutUs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        rlBack = (RelativeLayout) findViewById (R.id.rlBack);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBar = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  tvHyperlink=(TextView)findViewById(R.id.tvHyperlink);
        webViewAboutUs = (WebView) findViewById (R.id.webviewAboutus);
    }

    private void initData() {
        collapsingToolbarLayout.setTitleEnabled (false);
        appBar.setExpanded (true);
        Utils.setTypefaceToAllViews (this, rlBack);
    
    
        String webview = "<p style=\"font-family: myFont;\">ClearSale&#39;s parent company, HomeTrust, was founded in 2001 as a locally owned and operated real estate investment business run by a husband/wife team. &nbsp;And 14 years later that&rsquo;s still what we are. &nbsp;Although we have now done over 300 buy/renovate/sell projects (what we call fix &amp; flips), have a multi-million dollar single family rental portfolio that is growing quickly, and a wholesale business, we are at our core a small family run business that strives to create fantastic value for our customers every day. &nbsp;\n" +
                "   <a href=\"http://www.bbb.org/denver/business-reviews/real-estate-services/hometrust-in-englewood-co-64008443\" >The BBB likes us too: &nbsp;Check out our A+ Rating from the Denver Better Business Bureau. &nbsp;Click here to view.</a></p>\n" +
                "<p>\n" +
                "And this is where you come in.</p>\n" +
                "<p>\n" +
                "We do tens of thousands of dollars of marketing each and every month, utilizing leading edge techniques, just to bring you the best wholesale deals in Metro Denver &amp; Northern Colorado.</p>\n" +
                "<p>\n" +
                "To get our full list of wholesale priced investment properties&hellip; &nbsp;</p>\n" +
                "<p>\n" +
                "<span><a href=\"http://www.clearsale.com/csvbuyer\">&gt;&gt; Click here to join our Preferred Buyers List &lt;&lt;</a></span></p>\n" +
                "<p >\n" +
                "Once you register you&rsquo;ll be taken to our&nbsp;Metro Denver &amp; Northern Colorado investment properties page. Whenever we get new properties in we&rsquo;ll put them up on that page and notify you by email.</p>\n" +
                "</div>\n" +
                "\n" +
                "            </div>\n" +
                "<div class=\"clearfix\"></div>\n" +
                "<div class=\"row\">\n" +
                "<div class=\"col-md-12\">\n" +
                "<div class=\"full-content\">\n" +
                "\n" +
                "           <h5>  Why Should You Work With Us?</h5>\n" +
                "<p>\n" +
                "We&rsquo;ve already done the work and made the investments to generate a consistent stream of deeply discounted deals.&nbsp; Because we come across so many great deals (30% &ndash; 50% below market value) we simply can&rsquo;t buy and keep all of these properties ourselves &nbsp;</p>\n" +
                "<h5>Real Estate Investors Looking For Rentals or Rehabs</h5>\n" +
                "<p>";
    
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + webview);
        webViewAboutUs.loadDataWithBaseURL ("www.google.com", spannableStringBuilder.toString (), "text/html", "UTF-8", "");
    
    }

    private void initListener() {
       // tvHyperlink.setClickable(true);
      //  tvHyperlink.setMovementMethod(LinkMovementMethod.getInstance());
      //  String text = "<a href='http://www.google.com'> Google </a>";
      //  tvHyperlink.setText(Html.fromHtml(text));
       
    
        rlBack.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
                overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }
    
    @Override
    public void onBackPressed () {
        finish ();
        overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
    }
    
}




