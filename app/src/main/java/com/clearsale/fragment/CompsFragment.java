package com.clearsale.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clearsale.R;
import com.clearsale.utils.Constants;
import com.clearsale.utils.PropertyDetailsPref;


/**
 * Created by l on 23/03/2017.
 */

public class CompsFragment extends Fragment {
    TextView tvComps;
    PropertyDetailsPref propertyDetailsPref;
    WebView webView;
    
    LinearLayout llComps;
    LinearLayout llLoading;
    
    Animation animation1, animation2;
    
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate (R.layout.fragment_comps, container, false);
        initView (rootView);
        initData ();
        initListener ();
        return rootView;

    }

    private void initView(View rootView) {
        tvComps = (TextView) rootView.findViewById (R.id.tvComps);
        webView = (WebView) rootView.findViewById (R.id.webView1);
        llLoading = (LinearLayout) rootView.findViewById (R.id.llLoading);
        llComps = (LinearLayout) rootView.findViewById (R.id.llComps);
    }

    private void initData() {
        propertyDetailsPref = PropertyDetailsPref.getInstance ();
        tvComps.setText (Html.fromHtml (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_COMPS)));
        tvComps.setAutoLinkMask (Linkify.WEB_URLS);
        tvComps.setLinksClickable (true);
    
        animation1 = new AlphaAnimation (0.0f, 1.0f);
        animation1.setDuration (1000);
        animation2 = new AlphaAnimation (1.0f, 0.0f);
        animation2.setDuration (1000);
    
        llLoading.startAnimation (animation2);
    
    
        if (propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_COMPS).length () > 0) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder ("<style>@font-face{font-family: myFont;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + propertyDetailsPref.getStringPref (getActivity (), PropertyDetailsPref.PROPERTY_COMPS));
            webView.loadDataWithBaseURL ("www.google.com", spannableStringBuilder.toString (), "text/html", "UTF-8", "");
            llComps.setVisibility (View.VISIBLE);
        } else {
            llComps.setVisibility (View.GONE);
        }
    }

    private void initListener() {
        animation1.setAnimationListener (new Animation.AnimationListener () {
        
            @Override
            public void onAnimationEnd (Animation arg0) {
                // start animation2 when animation1 ends (continue)
                llLoading.startAnimation (animation2);
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
}
