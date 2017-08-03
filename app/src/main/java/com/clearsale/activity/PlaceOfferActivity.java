package com.clearsale.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.clearsale.R;
import com.clearsale.utils.AppConfigTags;

/**
 * Created by actiknow on 7/28/17.
 */

public class PlaceOfferActivity extends AppCompatActivity {
    WebView webviewPlaceAnOffer;
    Intent intent;
    
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
        /*String webview = "<form action=\"/newtheme/buyers/view_property/1064\" id=\"SearchData\" method=\"post\" accept-charset=\"utf-8\"><div style=\"display:none;\"><input type=\"hidden\" name=\"_method\" value=\"POST\"/></div>\n" +
                "                <div class=\"ct-bid\">\n" +
                "\n" +
                "                    <div class=\"panel panel-default details\">\n" +
                "                        <div class=\"panel-heading\" role=\"tab\" id=\"headingTwo\">\n" +
                "                            <h4 class=\"panel-title\">\n" +
                "                                <a class=\"collapsed\" role=\"button\" data-toggle=\"collapse\" data-parent=\"#accordion1\" href=\"#details\" aria-expanded=\"false\" aria-controls=\"collapseTwo\">Place an Offer</a>\n" +
                "                            </h4>\n" +
                "                        </div>\n" +
                "                        <div id=\"details\" class=\"panel-collapse collapse in\" role=\"tabpanel\" aria-labelledby=\"headingTwo\">\n" +
                "                            <div class=\"panel-body offer-form\" style=\"padding:14px 35px!important\">\n" +
                "\n" +
                "                                <form class=\"form-horizontal\">\n" +
                "                                    <div class=\"form-group row\">\n" +
                "                                        <div class=\"col-sm-12\">\n" +
                "                                            <p style=\"margin-bottom:5px;font-size:13px;\">In My Best Offer please enter the price that makes this property a buy for you.  My Best Offer can be higher, lower, or the same as the asking price.  Highest offer is the most important, but not the only, factor in determining who will be purchasing the property. </p>\n" +
                "                                        </div>\n" +
                "\n" +
                "                                    </div>\n" +
                "\n" +
                "                                    <div class=\"form-group row\">\n" +
                "                                        <div class=\"col-md-12\">\n" +
                "                                            <ul class=\"inline-list\">\n" +
                "                                                <li><label class=\"control-label\">My Best Offer :</label></li>\n" +
                "                                                <li><span  style=\"font-size: 15px;\">$</span></li>\n" +
                "                                                <li><input type=\"text\" class=\"form-control\"></li>\n" +
                "                                                <li><p style=\"margin-top:7px;margin-left:2px;\">(net to seller, no commissions paid)</p></li>\n" +
                "                                            </ul>    \n" +
                "                                        </div>   \n" +
                "                                    </div>\n" +
                "                                    <div class=\"form-group row\">\n" +
                "                                        <div class=\"col-sm-12\"> \n" +
                "                                            <p style=\"font-size:13px;\">I understand when \"Submit Offer\" is clicked this becomes a binding offer and a wire of up to $8000 of non-refundable earnest money/deposit, along with a signed contract, will be required within 4 business hours of me being chosen to purchase this property. </p>\n" +
                "                                        </div>\n" +
                "\n" +
                "                                    </div>\n" +
                "                                    <div class=\"divider\"></div>\n" +
                "                                    <div class=\"form-group row\">\n" +
                "                                        <label class=\"control-label col-sm-7\">This property is scheduled to close on <span>07/30/2017</span></label>\n" +
                "                                        <div class=\"col-sm-5\">\n" +
                "                                            <input type=\"checkbox\">\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"form-group row\">\n" +
                "                                        <label class=\"control-label col-sm-7\" for=\"email\">In the event seller wants to close earlier, I can close. </label>\n" +
                "                                        <div class=\"col-sm-5\">\n" +
                "                                            <label class=\"radio-inline\">\n" +
                "                                                <input type=\"radio\" id=\"cancloseearlier\" name=\"cancloseearlier\">Can Close Earlier\n" +
                "                                            </label>\n" +
                "                                            <label class=\"radio-inline\">\n" +
                "                                                <input type=\"radio\" id=\"cannotcloseearlier\" name=\"cancloseearlier\">Can not Close Earlier\n" +
                "                                            </label>\n" +
                "\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div id=\"divPRI\" style=\"display:none\" class=\"form-group row\">\n" +
                "                                        <label class=\"control-label col-sm-7\" for=\"email\"> I can close as early as</label>\n" +
                "                                        <div class=\"col-sm-3\">\n" +
                "                                            <input type=\"date\" class=\"form-control\">\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"form-group row\">\n" +
                "                                        <label class=\"control-label col-sm-7\" for=\"email\">I can close later if required by seller.</label>\n" +
                "                                        <div class=\"col-sm-5\">\n" +
                "                                            <label class=\"radio-inline\">\n" +
                "                                                <input type=\"radio\" id=\"canclose\" name=\"canclose\">Yes\n" +
                "                                            </label>\n" +
                "                                            <label class=\"radio-inline\">\n" +
                "                                                <input type=\"radio\" id=\"cannotcloseearlier\" name=\"cancloseearlier\">No\n" +
                "                                            </label>\n" +
                "\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"form-group row\">\n" +
                "                                        <label class=\"control-label col-sm-7\" for=\"email\">My funding to purchase this property will be. </label>\n" +
                "                                        <div class=\"col-sm-5\">\n" +
                "                                            <label class=\"radio\">\n" +
                "                                                <input type=\"radio\"  name=\"fundingToPurchase\">Actual cash (no deed of trust being will be recorded at closing)\n" +
                "                                            </label>\n" +
                "                                            <label class=\"radio\">\n" +
                "                                                <input type=\"radio\" id=\"loanOfSomeType\" name=\"fundingToPurchase\">Some type of loan\n" +
                "                                            </label>\n" +
                "                                            <div class=\"sometypeofloan\" id=\"sometypeloan\">\n" +
                "\n" +
                "                                                <div class=\"form-group row\">\n" +
                "                                                    <label  class=\"control-label col-sm-7\">Type of loan</label>\n" +
                "                                                    <div class=\"col-sm-5\">\n" +
                "                                                        <input type=\"text\" class=\"form-control\">\n" +
                "                                                    </div>   \n" +
                "                                                </div>\n" +
                "                                                <div class=\"form-group row\">\n" +
                "                                                    <label  class=\"control-label col-sm-7\">Lender name</label>\n" +
                "                                                    <div class=\"col-sm-5\">\n" +
                "                                                        <input type=\"text\" class=\"form-control\">\n" +
                "                                                    </div>   \n" +
                "                                                </div>\n" +
                "                                                <div class=\"form-group row\">\n" +
                "                                                    <label  class=\"control-label col-sm-7\">My lender will require an appraisal</label>\n" +
                "                                                    <div class=\"col-sm-5\">\n" +
                "                                                        <label class=\"radio-inline\">\n" +
                "                                                            <input type=\"radio\" id=\"radio\" name=\"radio\">Yes\n" +
                "                                                        </label>\n" +
                "                                                        <label class=\"radio-inline\">\n" +
                "                                                            <input type=\"radio\" id=\"radio\" name=\"radio\">No\n" +
                "                                                        </label>\n" +
                "                                                    </div>   \n" +
                "                                                </div>\n" +
                "\n" +
                "\n" +
                "                                            </div>   \n" +
                "\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"form-group row\">\n" +
                "                                        <label class=\"control-label col-sm-7\" for=\"email\">A real estate commission will be paid as a part of this transaction.</label>\n" +
                "                                        <div class=\"col-sm-5\">\n" +
                "                                            <label class=\"radio-inline\">\n" +
                "                                                <input type=\"radio\" id=\"yes\"  name=\"realstate\">Yes\n" +
                "                                            </label>\n" +
                "                                            <label class=\"radio-inline\">\n" +
                "                                                <input type=\"radio\" name=\"realstate\">No\n" +
                "                                            </label>\n" +
                "                                            <div class=\"realstate\" id=\"realstate\">\n" +
                "\n" +
                "                                                <div class=\"form-group row\">\n" +
                "                                                    <label  class=\"control-label col-sm-7\">The commission due the agent</label>\n" +
                "                                                    <div class=\"col-sm-5\">\n" +
                "                                                        <input type=\"text\" class=\"form-control\">\n" +
                "                                                    </div>   \n" +
                "                                                </div>\n" +
                "                                                <div class=\"form-group row\">\n" +
                "                                                    <label  class=\"control-label col-sm-7\">The net price to the seller (â€œYour Best Offerâ€� minus commission)</label>\n" +
                "                                                    <div class=\"col-sm-5\">\n" +
                "                                                        <input type=\"text\" class=\"form-control\">\n" +
                "                                                    </div>   \n" +
                "                                                </div>\n" +
                "                                                <div class=\"form-group row\">\n" +
                "                                                    <label  class=\"control-label col-sm-7\"> Agentâ€™s name and contract information.</label>\n" +
                "                                                    <div class=\"col-sm-5\">\n" +
                "                                                        <input type=\"text\" class=\"form-control\">\n" +
                "                                                    </div>    \n" +
                "                                                </div>\n" +
                "\n" +
                "\n" +
                "                                            </div>   \n" +
                "\n" +
                "                                        </div>\n" +
                "\n" +
                "                                    </div>\n" +
                "                                    <div class=\"form-group row\">\n" +
                "                                        <label class=\"control-label col-sm-7\" for=\"email\"> This property is scheduled to be delivered vacant or occupied TBD.</label>\n" +
                "                                        <div class=\"col-sm-5\">\n" +
                "\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"divider\"></div>\n" +
                "                                    <div class=\"form-group row\">\n" +
                "\n" +
                "                                        <div class=\"col-sm-12\">\n" +
                "                                            <h5>Property Confirmation Details:</h5>\n" +
                "\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "\n" +
                "                                    <div class=\"form-group row\"> \n" +
                "                                        <label class=\"control-label col-sm-11\" > I have attended access and understand no further inspections or property visits will be allowed. </label>\n" +
                "\n" +
                "                                        <div class=\"col-sm-1\">\n" +
                "                                            <input type=\"checkbox\">\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"form-group row\"> \n" +
                "                                        <label class=\"control-label col-sm-11\" >I understand the property is sold as-is.  I have had the opportunity to inspect the property and perform my required due diligence.</label>\n" +
                "\n" +
                "                                        <div class=\"col-sm-1\">\n" +
                "                                            <input type=\"checkbox\">\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"form-group row\"> \n" +
                "                                        <label class=\"control-label col-sm-11\" >The seller will, at any time of the seller's choosing, make the final selection of who will be buying the property. Seller does not allow escalation clauses. </label>\n" +
                "\n" +
                "                                        <div class=\"col-sm-1\">\n" +
                "                                            <input type=\"checkbox\">\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"form-group row\"> \n" +
                "                                        <label class=\"control-label col-sm-11\" >Non-refundable earnest money/deposit are due at contract signing.  Both count towards the contract and what you owe at closing.  Both are non-refundable except when seller is not able to provide clear title or, if seller is buying property prior to reselling it, if seller is not able to buy property for any reason.</label>\n" +
                "\n" +
                "                                        <div class=\"col-sm-1\">\n" +
                "                                            <input type=\"checkbox\">\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"form-group row\"> \n" +
                "                                        <label class=\"control-label col-sm-11\" >My Best Offer is net to seller.  Any commissions/finder fees must be added on top.  I understand I will pay all of seller closing costs, even the closing costs that are traditionally paid by the seller.</label>\n" +
                "\n" +
                "                                        <div class=\"col-sm-1\">\n" +
                "                                            <input type=\"checkbox\">\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"form-group row\"> \n" +
                "                                        <label class=\"control-label col-sm-11\" >I understand any information shared by HomeTrust in writing or verbally, including but not limited to after repair value estimates, fix estimates, rent estimates, square footage estimates, bed and bath info, zoning, and utilities are estimates deemed to be reliable but are not guaranteed.  I must perform my own due diligence.  All information and estimates are opinions and none are traditional appraisal products.</label>\n" +
                "\n" +
                "                                        <div class=\"col-sm-1\">\n" +
                "                                            <input type=\"checkbox\">\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"form-group row\"> \n" +
                "                                        <label class=\"control-label col-sm-11\" >I agree to the all terms and conditions</label>\n" +
                "\n" +
                "                                        <div class=\"col-sm-1\">\n" +
                "                                            <input type=\"checkbox\">\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "                                    <div class=\"form-group\"> \n" +
                "                                        <div class=\"col-sm-12 text-center\">\n" +
                "                                            <button type=\"submit\" class=\"btn btn-primary\">Submit Offer</button>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div style=\"height:10px;\"></div>\n" +
                "                                </form>";

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder ("<style>@font-face{font-family: myFont; font-size: 14px;src: url(file:///android_asset/" + Constants.font_name + ");}</style>" + webview);
        webviewPlaceAnOffer.loadDataWithBaseURL ("www.google.com", spannableStringBuilder.toString (), "text/html", "UTF-8", "");*/
        webviewPlaceAnOffer.loadUrl ("http://hometrustaustin.com/newtheme/buyers/app_view?property_id=" + intent.getIntExtra (AppConfigTags.PROPERTY_ID, 0) + "&buyer_id=" + intent.getIntExtra (AppConfigTags.BUYER_ID, 0) + "");
        webviewPlaceAnOffer.getSettings ().setJavaScriptEnabled (true);
        Log.e ("URL", "http://hometrustaustin.com/newtheme/buyers/app_view?property_id=" + intent.getIntExtra (AppConfigTags.PROPERTY_ID, 0) + "&buyer_id=" + intent.getIntExtra (AppConfigTags.BUYER_ID, 0));
    }
    
    private void initListener () {
        
    }
    
    private void initView () {
        webviewPlaceAnOffer = (WebView) findViewById (R.id.webviewPlaceAnOffer);
    }
}
