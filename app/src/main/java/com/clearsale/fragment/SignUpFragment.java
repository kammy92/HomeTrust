package com.clearsale.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.clearsale.R;
import com.clearsale.activity.MainActivity;
import com.clearsale.utils.AppConfigTags;
import com.clearsale.utils.AppConfigURL;
import com.clearsale.utils.BuyerDetailsPref;
import com.clearsale.utils.Constants;
import com.clearsale.utils.NetworkConnection;
import com.clearsale.utils.TypefaceSpan;
import com.clearsale.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by l on 20/03/2017.
 */

public class SignUpFragment extends Fragment {
    
    EditText etName;
    EditText etEmail;
    EditText etMobile;
    CoordinatorLayout clMain;
    ProgressDialog progressDialog;
    TextView tvTerm;
    
    TextView tvSignUp;
    
    BuyerDetailsPref buyerDetailsPref;
    
    
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate (R.layout.fragment_sign_up, container, false);
        initView (rootView);
        initData ();
        initListener ();
        return rootView;
    }
    
    private void initView (View rootView) {
        etName = (EditText) rootView.findViewById (R.id.etUserName);
        etEmail = (EditText) rootView.findViewById (R.id.etEmail);
        etMobile = (EditText) rootView.findViewById (R.id.etPhone);
        tvSignUp = (TextView) rootView.findViewById (R.id.tvSignUp);
        clMain = (CoordinatorLayout) rootView.findViewById (R.id.clMain);
        tvTerm = (TextView) rootView.findViewById (R.id.tvTermConditions);
        Utils.setTypefaceToAllViews (getActivity (), tvSignUp);
    }
    
    private void initData () {
        buyerDetailsPref = BuyerDetailsPref.getInstance ();
        progressDialog = new ProgressDialog (getActivity ());
        SpannableString ss = new SpannableString (getResources ().getString (R.string.activity_login_text_i_agree));
        ss.setSpan (new myClickableSpan (1), 17, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan (new myClickableSpan (2), 40, 54, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan (new ForegroundColorSpan (getResources ().getColor (R.color.colorPrimary)), 17, 35, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan (new ForegroundColorSpan (getResources ().getColor (R.color.colorPrimary)), 40, 54, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTerm.setText (ss);
        tvTerm.setMovementMethod (LinkMovementMethod.getInstance ());
    }
    
    private void initListener () {
        tvSignUp.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                Utils.hideSoftKeyboard (getActivity ());
                SpannableString s = new SpannableString (getResources ().getString (R.string.please_enter_name));
                s.setSpan (new TypefaceSpan (getActivity (), Constants.font_name), 0, s.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString s2 = new SpannableString (getResources ().getString (R.string.please_enter_email));
                s2.setSpan (new TypefaceSpan (getActivity (), Constants.font_name), 0, s2.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString s3 = new SpannableString (getResources ().getString (R.string.please_enter_mobile));
                s3.setSpan (new TypefaceSpan (getActivity (), Constants.font_name), 0, s3.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString s4 = new SpannableString (getResources ().getString (R.string.please_enter_valid_email));
                s4.setSpan (new TypefaceSpan (getActivity (), Constants.font_name), 0, s4.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString s5 = new SpannableString (getResources ().getString (R.string.please_enter_valid_mobile));
                s5.setSpan (new TypefaceSpan (getActivity (), Constants.font_name), 0, s5.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString s6 = new SpannableString (getResources ().getString (R.string.please_enter_valid_email));
                s6.setSpan (new TypefaceSpan (getActivity (), Constants.font_name), 0, s6.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                if (etName.getText ().toString ().trim ().length () == 0 && etEmail.getText ().toString ().length () == 0 && etMobile.getText ().toString ().length () == 0) {
                    etName.setError (s);
                    etEmail.setError (s2);
                    etMobile.setError (s3);
                } else if (etName.getText ().toString ().trim ().length () == 0) {
                    etName.setError (s);
                } else if (etEmail.getText ().toString ().trim ().length () == 0) {
                    etEmail.setError (s2);
                } else if (! Utils.isValidEmail1 (etEmail.getText ().toString ())) {
                    etEmail.setError (s6);
                } else if (etMobile.getText ().toString ().trim ().length () == 0) {
                    etMobile.setError (s3);
                } else {
                    sendSignUpDetailsToServer (etName.getText ().toString ().trim (), etEmail.getText ().toString ().trim (), etMobile.getText ().toString ().trim ());
                }
            }
            
        });
        etName.addTextChangedListener (new TextWatcher () {
            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    etName.setError (null);
                }
            }
            
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void afterTextChanged (Editable s) {
            }
        });
        etEmail.addTextChangedListener (new TextWatcher () {
            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    etEmail.setError (null);
                }
            }
            
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void afterTextChanged (Editable s) {
            }
        });
        etMobile.addTextChangedListener (new TextWatcher () {
            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    etMobile.setError (null);
                }
            }
            
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void afterTextChanged (Editable s) {
            }
        });
    }
    
    private void sendSignUpDetailsToServer (final String name, final String email, final String number) {
        if (NetworkConnection.isNetworkAvailable (getActivity ())) {
            Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), true);
            Utils.showLog (Log.INFO, "" + AppConfigTags.URL, AppConfigURL.URL_SIGN_UP, true);
            StringRequest strRequest1 = new StringRequest (Request.Method.POST, AppConfigURL.URL_SIGN_UP,
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
                                        buyerDetailsPref.putIntPref (getActivity (), BuyerDetailsPref.BUYER_ID, jsonObj.getInt (AppConfigTags.BUYER_ID));
                                        buyerDetailsPref.putStringPref (getActivity (), BuyerDetailsPref.BUYER_NAME, jsonObj.getString (AppConfigTags.BUYER_NAME));
                                        buyerDetailsPref.putStringPref (getActivity (), BuyerDetailsPref.BUYER_EMAIL, jsonObj.getString (AppConfigTags.BUYER_EMAIL));
                                        buyerDetailsPref.putStringPref (getActivity (), BuyerDetailsPref.BUYER_MOBILE, jsonObj.getString (AppConfigTags.BUYER_MOBILE));
                                        buyerDetailsPref.putIntPref (getActivity (), BuyerDetailsPref.PROFILE_STATUS, jsonObj.getInt (AppConfigTags.PROFILE_STATUS));
        
                                        switch (jsonObj.getInt (AppConfigTags.PROFILE_STATUS)) {
                                            case 0:
                                                break;
                                            case 1:
                                                buyerDetailsPref.putStringPref (getActivity (), BuyerDetailsPref.PROFILE_STATE, jsonObj.getString (AppConfigTags.PROFILE_STATE));
                                                buyerDetailsPref.putStringPref (getActivity (), BuyerDetailsPref.PROFILE_PRICE_RANGE, jsonObj.getString (AppConfigTags.PROFILE_PRICE_RANGE));
                                                buyerDetailsPref.putStringPref (getActivity (), BuyerDetailsPref.PROFILE_HOME_TYPE, jsonObj.getString (AppConfigTags.PROFILE_HOME_TYPE));
                                                break;
                                        }
        
        
                                        Intent intent = new Intent (getActivity (), MainActivity.class);
                                        intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity (intent);
                                        getActivity ().overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
                                    } else {
                                        Utils.showSnackBar (getActivity (), clMain, message, Snackbar.LENGTH_LONG, null, null);
                                    }
                                    progressDialog.dismiss ();
                                } catch (Exception e) {
                                    progressDialog.dismiss ();
                                    Utils.showSnackBar (getActivity (), clMain, getResources ().getString (R.string.snackbar_text_exception_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                                    e.printStackTrace ();
                                }
                            } else {
                                Utils.showSnackBar (getActivity (), clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
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
                            Utils.showSnackBar (getActivity (), clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String> ();
                    params.put (AppConfigTags.BUYER_DEVICE, "ANDROID");
                    params.put (AppConfigTags.BUYER_NAME, name);
                    params.put (AppConfigTags.BUYER_EMAIL, email);
                    params.put (AppConfigTags.BUYER_MOBILE, number);
                    params.put (AppConfigTags.BUYER_FIREBASE_ID, buyerDetailsPref.getStringPref (getActivity (), BuyerDetailsPref.BUYER_FIREBASE_ID));
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
            Utils.showSnackBar (getActivity (), clMain, getResources ().getString (R.string.snackbar_text_no_internet_connection_available), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_go_to_settings), new View.OnClickListener () {
                @Override
                public void onClick (View v) {
                    Intent dialogIntent = new Intent (Settings.ACTION_SETTINGS);
                    dialogIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity (dialogIntent);
                }
            });
        }
    }
    
    public class myClickableSpan extends ClickableSpan {
        int pos;
    
        public myClickableSpan (int position) {
            this.pos = position;
        }
    
        @Override
        public void onClick (View widget) {
            switch (pos) {
                case 1:
                    //t and c
                    Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse ("http://clearsale.com/csvbuyer/Homes/termsandcondition"));
                    getActivity ().startActivity (intent);
                    break;
                case 2:
                    //privacy policy
                    Intent intent2 = new Intent (Intent.ACTION_VIEW, Uri.parse ("http://clearsale.com/csvbuyer/Homes/privacy"));
                    getActivity ().startActivity (intent2);
                    break;
            }
        }
    }
}