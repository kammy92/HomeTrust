package com.clearsale.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clearsale.R;
import com.clearsale.model.HowItWork;
import com.clearsale.utils.BuyerDetailsPref;
import com.clearsale.utils.SetTypeFace;
import com.clearsale.utils.Utils;

import java.util.ArrayList;


/**
 * Created by l on 19/10/2016.
 */
public class HowItWorksActivity extends AppCompatActivity {
    ArrayList<HowItWork> howItWorkList = new ArrayList<> ();
    RelativeLayout rlBack;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private Button btPrev, btNext;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    BuyerDetailsPref buyerDetailsPref;
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener () {
        @Override
        public void onPageSelected (int position) {
            addBottomDots (position);
            if (position == 0) {
                btNext.setVisibility (View.VISIBLE);
                btPrev.setVisibility (View.INVISIBLE);
            }
            if (position > 0) {
                btNext.setVisibility (View.VISIBLE);
                btPrev.setVisibility (View.VISIBLE);
            }
            if (position == howItWorkList.size () - 1) {
                btNext.setVisibility (View.INVISIBLE);
                btPrev.setVisibility (View.VISIBLE);
            }
        }
        
        @Override
        public void onPageScrolled (int arg0, float arg1, int arg2) {
        }
        
        @Override
        public void onPageScrollStateChanged (int arg0) {
        }
    };
    
    FloatingActionButton fabChat;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_how_it_works);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        // pageIndicatorView = (PageIndicatorView) findViewById (R.id.pageIndicatorView);
        btPrev = (Button) findViewById (R.id.btPrev);
        btNext = (Button) findViewById(R.id.btNext);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        rlBack = (RelativeLayout) findViewById (R.id.rlBack);
        fabChat = (FloatingActionButton) findViewById (R.id.fabChat);
    }

    private void initData() {
        buyerDetailsPref = BuyerDetailsPref.getInstance ();
        howItWorkList.add (new HowItWork (1, "Step 1", "Join our preferred buyers list and tell us what kinds of properties you are looking for.", "", R.drawable.img1));
        howItWorkList.add (new HowItWork (2, "Step 2", "With our proprietary marketing systems we find the best properties at great prices and pass those deals on to you.", "", R.drawable.img2));
        howItWorkList.add (new HowItWork (3, "Step 3", "As soon as we have a new property under contract in your area, you will receive an immediate alert and email allowing you to preview the property details and schedule a time to access the property.", "", R.drawable.img3));
        howItWorkList.add (new HowItWork (4, "Step 4", "Once you have viewed a property, you are eligible to place an offer directly on this app.", "", R.drawable.img4));

        addBottomDots(0);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        Utils.setTypefaceToAllViews (this, rlBack);
    }
    
    private void initListener() {
        btPrev.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                int current = getItem (- 1);
                if (current >= 0) {
                    viewPager.setCurrentItem (current);
                }
            }
        });
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < howItWorkList.size ()) {
                    viewPager.setCurrentItem(current);
                }
            }
        });
        rlBack.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
                overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        fabChat.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
    
                Intent intent = new Intent (HowItWorksActivity.this, com.livechatinc.inappchat.ChatWindowActivity.class);
                intent.putExtra (com.livechatinc.inappchat.ChatWindowActivity.KEY_GROUP_ID, "7");
                intent.putExtra (com.livechatinc.inappchat.ChatWindowActivity.KEY_LICENCE_NUMBER, "9704635");
                intent.putExtra (com.livechatinc.inappchat.ChatWindowActivity.KEY_VISITOR_NAME, buyerDetailsPref.getStringPref (HowItWorksActivity.this, BuyerDetailsPref.BUYER_NAME));
                intent.putExtra (com.livechatinc.inappchat.ChatWindowActivity.KEY_VISITOR_EMAIL, buyerDetailsPref.getStringPref (HowItWorksActivity.this, BuyerDetailsPref.BUYER_EMAIL));
    
                startActivity (intent);
    
    
            }
        });
    }
    
    private void addBottomDots(int currentPage) {
        dots = new TextView[howItWorkList.size ()];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.text_color_grey_dark));
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
    
    @Override
    public void onBackPressed () {
        finish ();
        overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
    }
    
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate (R.layout.how_it_works_slide, container, false);
    
            HowItWork howItWork = howItWorkList.get (position);
    
            ImageView ivIcon = (ImageView) view.findViewById (R.id.ivIcon);
            TextView tvTitle = (TextView) view.findViewById (R.id.tvTitle);
            TextView tvDescription = (TextView) view.findViewById (R.id.tvDescription);
    
            tvTitle.setTypeface (SetTypeFace.getTypeface (HowItWorksActivity.this));
            tvDescription.setTypeface (SetTypeFace.getTypeface (HowItWorksActivity.this));
    
            ivIcon.setImageResource (howItWork.getImage_drawable ());
            tvTitle.setText (howItWork.getTitle ());
            tvDescription.setText (howItWork.getDescription ());
    
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return howItWorkList.size ();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}