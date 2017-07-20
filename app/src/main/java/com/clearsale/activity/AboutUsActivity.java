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
    
    
        String webview = "<p style=\"font-family: myFont;\" style=\"font-family: myFont;\">ClearSale's parent company, HomeTrust, was founded in 2001 as a locally owned and operated real estate investment business run by a husband/wife team.  And 14 years later that’s still what we are.  Although we have now done over 300 buy/renovate/sell projects (what we call fix & flips), have a multi-million dollar single family rental portfolio that is growing quickly, and a wholesale business, we are, at our core, a small family run business that strives to create fantastic value for our customers every day. &nbsp;\n" +
                "\t\t\t\t   <a href=\"http://www.bbb.org/denver/business-reviews/real-estate-services/hometrust-in-englewood-co-64008443\" >The BBB likes us too:  Check out our A+ Rating from the Denver Better Business Bureau.  Click here to view.\n" +
                "</a></p>\n" +
                "\t\t\t\t\t<p style=\"font-family: myFont;\">\n" +
                "\t\t\t\t\t\tThis is where you come in</p>\n" +
                "\t\t\t\t\t<p style=\"font-family: myFont;\">\n" +
                "\t\t\t\t\t\tWe do tens of thousands of dollars of marketing each and every month, utilizing leading edge techniques, just to bring you the best wholesale deals in Metro Denver & Northern Colorado.</p>\n" +
                "\t\t\t\t\t<p style=\"font-family: myFont;\">\n" +
                "\t\t\t\t\t\tTo get our full list of wholesale priced investment properties&hellip; &nbsp;</p>\n" +
                "\t\t\t\t\t<p style=\"font-family: myFont;\">\n" +
                "\t\t\t\t\t\t<span><a href=\"http://www.clearsale.com/csvbuyer\">&gt;&gt; To get our full list of wholesale priced investment properties… &lt;&lt;</a></span></p>\n" +
                "\t\t\t\t\t<p style=\"font-family: myFont;\">\n" +
                "\t\t\t\t\t\tOnce you register you’ll be taken to our Metro Denver & Northern Colorado investment properties page. Whenever we get new properties we’ll put them up on that page and notify you by email.\n" +
                "</p>\n" +
                "<h5>Why Should You Work With Us?</h5>\n" +
                "<p style=\"font-family: myFont;\">\n" +
                "\tWe’ve already done the work and made the investments to generate a consistent stream of deeply discounted deals.  Because we come across so many great deals (30% – 50% below market value) we simply can’t buy and keep all of these properties ourselves.  \n" +
                "</p>\n" +
                "<h5>Real Estate Investors Looking For Rentals or Rehabs</h5>\n" +
                "<p style=\"font-family: myFont;\">\n" +
                "\tIf you’re a real estate investor and are looking for great rental properties that will generate cash flow or <strong> distressed properties</strong> to rehab and resell… we will find those properties for you.  Just tell us what your buying criteria is and we’ll custom search for properties that fit your needs. The beauty of it is you never pay for our service… we make our money by adding on a modest fee once you close on one of our properties.</p>\n" +
                "<p style=\"font-family: myFont;\">\n" +
                "\tIn the end, we’ve gotten to where we are today because we’ve focused on becoming really good at finding great deals and passing those deals off to people like you at a huge discount.  That’s all we do.  You can rest assured that we are a great company to work with… we’re just real people just like you.\n" +
                "</p>\n" +
                "<p style=\"font-family: myFont;\">\n" +
                "\tThe Acquisitions Team,</p>\n" +
                "<p style=\"font-family: myFont;\">\n" +
                "\t<span style=\"color: rgb(85, 85, 85); font-family: &quot;Helvetica Neue&quot;, Helvetica, sans-serif; font-size: 12px;\">ClearSale &amp; HomeTrust</span></p>\n" +
                "<p style=\"font-family: myFont;\">\n" +
                "\t<span><a href=\"http://www.clearsale.com/csvbuyer\" style=\"margin: 0px; padding: 0px; border: 0px; outline: 0px; vertical-align: baseline; background: transparent; color: rgb(17, 17, 213); text-decoration: none;\" title=\"Complete Your Buyer Profile (Get On Our Priority List)\">&gt;&gt; Click here to join our Preferred Buyers List &lt;&lt;</a></span></p>";
    
    
        String webview2 = "<p style=\"font-family: myFont;\">ClearSale&#39;s parent company, HomeTrust, was founded in 2001 as a locally owned and operated real estate investment business run by a husband/wife team. &nbsp;And 14 years later that&rsquo;s still what we are. &nbsp;Although we have now done over 300 buy/renovate/sell projects (what we call fix &amp; flips), have a multi-million dollar single family rental portfolio that is growing quickly, and a wholesale business, we are at our core a small family run business that strives to create fantastic value for our customers every day. &nbsp;\n" +
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




