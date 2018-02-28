package com.clearsale.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.clearsale.R;
import com.clearsale.utils.AppConfigTags;
import com.clearsale.utils.AppConfigURL;
import com.clearsale.utils.BuyerDetailsPref;
import com.clearsale.utils.Constants;
import com.clearsale.utils.NetworkConnection;
import com.clearsale.utils.SetTypeFace;
import com.clearsale.utils.TypefaceSpan;
import com.clearsale.utils.Utils;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MyProfileActivity extends AppCompatActivity {
    public static List<String> stateSelectedList3 = new ArrayList<String> ();
    EditText etFullName;
    EditText etEmail;
    EditText etPhone;
    CheckBox cb1;
    CheckBox cb2;
    CheckBox cb3;
    CheckBox cb4;
    MaterialSpinner spinner;
    RecyclerView rvStates;
    LinearLayout llState;
    BuyerDetailsPref buyerDetailsPref;
    CoordinatorLayout clMain;
    List<String> stateSelectedList = new ArrayList<String> ();
    List<String> stateSelectedList2 = new ArrayList<String> ();
    List<String> budgetSelectedList = new ArrayList ();
    ProgressDialog progressDialog;
    String[] spinnerItems;
    
    StateAdapter stateAdapter;
    
    
    RelativeLayout rlBack;
    TextView tvSave;
    
    List<String> stateList = new ArrayList<String> ();
    
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_my_profile);
        initView ();
        initData ();
        initListener ();
//        getStateListFromServer();
        getPreferencesData ();
    }
    
    private void getPreferencesData () {
        etFullName.setText (buyerDetailsPref.getStringPref (MyProfileActivity.this, BuyerDetailsPref.BUYER_NAME).trim ());
        etEmail.setText (buyerDetailsPref.getStringPref (MyProfileActivity.this, BuyerDetailsPref.BUYER_EMAIL));
        etPhone.setText (buyerDetailsPref.getStringPref (MyProfileActivity.this, BuyerDetailsPref.BUYER_MOBILE));
        
        String stateName[] = buyerDetailsPref.getStringPref (MyProfileActivity.this, BuyerDetailsPref.PROFILE_STATE).trim ().split (",");
        Log.e ("STATENAME", "" + stateName);
    
        stateSelectedList2.clear ();
        MyProfileActivity.stateSelectedList3.clear ();
        for (int i = 0; i < stateName.length; i++) {
            stateSelectedList2.add (stateName[i]);
            MyProfileActivity.stateSelectedList3.add (stateName[i]);
        }
    
    
        String priceRange[] = buyerDetailsPref.getStringPref (MyProfileActivity.this, BuyerDetailsPref.PROFILE_PRICE_RANGE).trim ().split (",");
        
        for (int i = 0; i < priceRange.length; i++) {
            switch (priceRange[i]) {
                case "1":
                    cb1.setChecked (true);
                    break;
                case "2":
                    cb2.setChecked (true);
                    break;
                case "3":
                    cb3.setChecked (true);
                    break;
                case "4":
                    cb4.setChecked (true);
                    break;
            }
        }
        
        if (buyerDetailsPref.getStringPref (MyProfileActivity.this, BuyerDetailsPref.PROFILE_HOME_TYPE).equalsIgnoreCase (spinnerItems[1])) {
            spinner.setSelectedIndex (1);
        } else if (buyerDetailsPref.getStringPref (MyProfileActivity.this, BuyerDetailsPref.PROFILE_HOME_TYPE).equalsIgnoreCase (spinnerItems[2])) {
            spinner.setSelectedIndex (2);
        } else if (buyerDetailsPref.getStringPref (MyProfileActivity.this, BuyerDetailsPref.PROFILE_HOME_TYPE).equalsIgnoreCase (spinnerItems[3])) {
            spinner.setSelectedIndex (3);
        } else if (buyerDetailsPref.getStringPref (MyProfileActivity.this, BuyerDetailsPref.PROFILE_HOME_TYPE).equalsIgnoreCase (spinnerItems[4])) {
            spinner.setSelectedIndex (4);
        }
    }
    
    private void initView () {
        etFullName = (EditText) findViewById (R.id.etFullName);
        etEmail = (EditText) findViewById (R.id.etEmail);
        etPhone = (EditText) findViewById (R.id.etPhone);
        rlBack = (RelativeLayout) findViewById (R.id.rlBack);
        tvSave = (TextView) findViewById (R.id.tvSave);
        cb1 = (CheckBox) findViewById (R.id.cb1);
        cb2 = (CheckBox) findViewById (R.id.cb2);
        cb3 = (CheckBox) findViewById (R.id.cb3);
        cb4 = (CheckBox) findViewById (R.id.cb4);
        spinner = (MaterialSpinner) findViewById (R.id.spinner);
        llState = (LinearLayout) findViewById (R.id.llState);
        clMain = (CoordinatorLayout) findViewById (R.id.clMain);
        rvStates = (RecyclerView) findViewById (R.id.rvStates);
        
        
        Utils.setTypefaceToAllViews (this, tvSave);
    }
    
    private void initData () {
        progressDialog = new ProgressDialog (MyProfileActivity.this);
        buyerDetailsPref = BuyerDetailsPref.getInstance ();
        spinnerItems = new String[] {
                "Select Home Type",
                "Cosmetic only",
                "Baths and Kitchens plus all of the above",
                "Whole house remodel includes moving walls plus all of the above",
                "Structural work plus all of the above",
        };
    
        stateList.clear ();
        String allState[] = buyerDetailsPref.getStringPref (MyProfileActivity.this, BuyerDetailsPref.STATE_LIST).trim ().split (",");
        for (int i = 0; i < allState.length; i++) {
            stateList.add (allState[i]);
        }
    
        stateAdapter = new StateAdapter (MyProfileActivity.this, stateList, stateSelectedList2);
        rvStates.setAdapter (stateAdapter);
        rvStates.setHasFixedSize (true);
        rvStates.setLayoutManager (new LinearLayoutManager (this, LinearLayoutManager.HORIZONTAL, false));
    
    
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String> (this, R.layout.spinner, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource (R.layout.spinner);
        spinner.setAdapter (spinnerArrayAdapter);
    
    
    }
    
    private void initListener () {
        rlBack.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
                overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        /*stateAdapter.SetOnItemClickListener(new StateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView tvState = (TextView)view.findViewById(R.id.tvState);
                if(stateSelectedList2.contains(stateList.get(position))){
                    stateSelectedList3.remove(stateList.get(position));
                    tvState.setBackgroundResource (R.drawable.state_button_unselected);
                    tvState.setTextColor (getResources ().getColor (R.color.app_text_color_dark));
                }else{
                    stateSelectedList2.add(stateList.get(position));
                    stateSelectedList3.add(stateList.get(position));
                    tvState.setBackgroundResource(R.drawable.state_button_selected);
                    tvState.setTextColor(getResources().getColor(R.color.text_color_white));
                }
                Log.e("StateList", ""+stateSelectedList2);
            }
        });*/
        
        cb1.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                if (cb1.isChecked ()) {
                    budgetSelectedList.add ("1");
                } else {
                    budgetSelectedList.remove ("1");
                }
            }
        });
        cb2.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                if (cb2.isChecked ()) {
                    budgetSelectedList.add ("2");
                } else {
                    budgetSelectedList.remove ("2");
                }
            }
        });
        cb3.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                if (cb3.isChecked ()) {
                    budgetSelectedList.add ("3");
                } else {
                    budgetSelectedList.remove ("3");
                }
            }
        });
        cb4.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                if (cb4.isChecked ()) {
                    budgetSelectedList.add ("4");
                } else {
                    budgetSelectedList.remove ("4");
                    
                }
            }
        });
        
        spinner.setItems (spinnerItems);
        spinner.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Utils.hideSoftKeyboard (MyProfileActivity.this);
            }
        });
        spinner.setOnItemSelectedListener (new MaterialSpinner.OnItemSelectedListener<String> () {
            @Override
            public void onItemSelected (MaterialSpinner view, int position, long id, String item) {
            }
        });
        etFullName.addTextChangedListener (new TextWatcher () {
            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    etFullName.setError (null);
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
        etPhone.addTextChangedListener (new TextWatcher () {
            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    etPhone.setError (null);
                }
            }
            
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void afterTextChanged (Editable s) {
            }
        });
        tvSave.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                String budget_csv = "";
                for (int j = 0; j < budgetSelectedList.size (); j++) {
                    if (j == 0) {
                        budget_csv = budgetSelectedList.get (j);
                    } else {
                        budget_csv = budget_csv + "," + budgetSelectedList.get (j);
                    }
                }
    
                String state_csv = "";
                for (int k = 0; k < stateSelectedList3.size (); k++) {
                    if (k == 0) {
                        state_csv = stateSelectedList3.get (k);
                    } else {
                        state_csv = state_csv + "," + stateSelectedList3.get (k);
                    }
                }
                buyerDetailsPref.putStringPref (MyProfileActivity.this, BuyerDetailsPref.PROFILE_STATE, state_csv);
    
                Log.e ("StateCSV", state_csv);
                
                SpannableString s = new SpannableString (getResources ().getString (R.string.please_enter_full_name));
                s.setSpan (new TypefaceSpan (MyProfileActivity.this, Constants.font_name), 0, s.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString s2 = new SpannableString (getResources ().getString (R.string.please_enter_email));
                s2.setSpan (new TypefaceSpan (MyProfileActivity.this, Constants.font_name), 0, s2.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString s3 = new SpannableString (getResources ().getString (R.string.please_enter_valid_email));
                s3.setSpan (new TypefaceSpan (MyProfileActivity.this, Constants.font_name), 0, s3.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString s4 = new SpannableString (getResources ().getString (R.string.please_enter_mobile));
                s4.setSpan (new TypefaceSpan (MyProfileActivity.this, Constants.font_name), 0, s4.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString s5 = new SpannableString (getResources ().getString (R.string.please_select_state_type));
                s5.setSpan (new TypefaceSpan (MyProfileActivity.this, Constants.font_name), 0, s5.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString s6 = new SpannableString (getResources ().getString (R.string.please_select_home_type));
                s6.setSpan (new TypefaceSpan (MyProfileActivity.this, Constants.font_name), 0, s6.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    
    
                if (etFullName.getText ().toString ().trim ().length () == 0 && etEmail.getText ().toString ().length () == 0 && etPhone.getText ().toString ().length () == 0) {
                    etFullName.setError (s);
                    etEmail.setError (s2);
                    etPhone.setError (s4);
                } else if (etFullName.getText ().toString ().trim ().length () == 0) {
                    etFullName.setError (s);
                } else if (etEmail.getText ().toString ().trim ().length () == 0) {
                    etEmail.setError (s2);
                } else if (! Utils.isValidEmail1 (etEmail.getText ().toString ())) {
                    etEmail.setError (s3);
                } else if (etPhone.getText ().toString ().trim ().length () == 0) {
                    etPhone.setError (s4);
                } else if (stateSelectedList3.size () == 0) {
                    Utils.showSnackBar (MyProfileActivity.this, clMain, s5.toString (), Snackbar.LENGTH_LONG, null, null);
                } else if (spinner.getText ().toString ().trim ().equalsIgnoreCase (spinnerItems[0])) {
                    Utils.showSnackBar (MyProfileActivity.this, clMain, s6.toString (), Snackbar.LENGTH_LONG, null, null);
                } else {
                    sendProfileDetailsToServer (
                            etFullName.getText ().toString ().trim (),
                            etEmail.getText ().toString ().trim (),
                            etPhone.getText ().toString ().trim (),
                            spinner.getText ().toString ().trim (),
                            state_csv, budget_csv);
                }
            }
        });
        
    }
    
    private void sendProfileDetailsToServer (final String name, final String email, final String mobile, final String homeType, final String state, final String budget) {
        if (NetworkConnection.isNetworkAvailable (MyProfileActivity.this)) {
            Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), true);
            Utils.showLog (Log.INFO, "" + AppConfigTags.URL, AppConfigURL.URL_EDIT_PROFILE, true);
            StringRequest strRequest1 = new StringRequest (Request.Method.POST, AppConfigURL.URL_EDIT_PROFILE,
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
                                        buyerDetailsPref.putStringPref (MyProfileActivity.this, BuyerDetailsPref.BUYER_NAME, jsonObj.getString (AppConfigTags.BUYER_NAME));
                                        buyerDetailsPref.putStringPref (MyProfileActivity.this, BuyerDetailsPref.BUYER_MOBILE, jsonObj.getString (AppConfigTags.BUYER_MOBILE));
                                        buyerDetailsPref.putStringPref (MyProfileActivity.this, BuyerDetailsPref.PROFILE_HOME_TYPE, jsonObj.getString (AppConfigTags.PROFILE_HOME_TYPE));
                                        //buyerDetailsPref.putStringPref (MyProfileActivity.this, BuyerDetailsPref.PROFILE_STATE, jsonObj.getString (AppConfigTags.PROFILE_STATE));
                                        buyerDetailsPref.putStringPref (MyProfileActivity.this, BuyerDetailsPref.PROFILE_PRICE_RANGE, jsonObj.getString (AppConfigTags.PROFILE_PRICE_RANGE));
                                        buyerDetailsPref.putIntPref (MyProfileActivity.this, BuyerDetailsPref.PROFILE_STATUS, jsonObj.getInt (AppConfigTags.PROFILE_STATUS));
                                        new MaterialDialog.Builder (MyProfileActivity.this)
                                                .content (message)
                                                .positiveText ("OK")
                                                .onPositive (new MaterialDialog.SingleButtonCallback () {
                                                    @Override
                                                    public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                        dialog.dismiss ();
                                                        finish ();
                                                    }
                                                })
                                                .show ();
                                    } else {
                                        Utils.showSnackBar (MyProfileActivity.this, clMain, message, Snackbar.LENGTH_LONG, null, null);
                                    }
                                    progressDialog.dismiss ();
                                } catch (Exception e) {
                                    progressDialog.dismiss ();
                                    Utils.showSnackBar (MyProfileActivity.this, clMain, getResources ().getString (R.string.snackbar_text_exception_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                                    e.printStackTrace ();
                                }
                            } else {
                                Utils.showSnackBar (MyProfileActivity.this, clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
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
                            Utils.showSnackBar (MyProfileActivity.this, clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String> ();
                    params.put (AppConfigTags.TYPE, "edit_profile");
                    params.put (AppConfigTags.BUYER_ID, String.valueOf (buyerDetailsPref.getIntPref (MyProfileActivity.this, BuyerDetailsPref.BUYER_ID)));
                    params.put (AppConfigTags.BUYER_NAME, name);
                    params.put (AppConfigTags.BUYER_EMAIL, email);
                    params.put (AppConfigTags.BUYER_MOBILE, mobile);
                    params.put (AppConfigTags.PROFILE_STATE, state);
                    params.put (AppConfigTags.PROFILE_PRICE_RANGE, budget);
                    params.put (AppConfigTags.PROFILE_HOME_TYPE, homeType);
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
            Utils.showSnackBar (this, clMain, getResources ().getString (R.string.snackbar_text_no_internet_connection_available), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_go_to_settings), new View.OnClickListener () {
                @Override
                public void onClick (View v) {
                    Intent dialogIntent = new Intent (Settings.ACTION_SETTINGS);
                    dialogIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity (dialogIntent);
                }
            });
        }
    }
    
    @Override
    public void onBackPressed () {
        finish ();
        overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
    }
    
    public static class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {
        StateAdapter.OnItemClickListener mItemClickListener;
        List<String> stateList = new ArrayList<String> ();
        List<String> selectedStateList = new ArrayList<String> ();
        private Activity activity;
        
        public StateAdapter (Activity activity, List<String> stateList, List<String> selectedStateList) {
            this.activity = activity;
            this.stateList = stateList;
            this.selectedStateList = selectedStateList;
        }
        
        @Override
        public StateAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            final LayoutInflater mInflater = LayoutInflater.from (parent.getContext ());
            final View sView = mInflater.inflate (R.layout.list_item_state, parent, false);
            return new StateAdapter.ViewHolder (sView);
        }
        
        @Override
        public void onBindViewHolder (StateAdapter.ViewHolder holder, int position) {//        runEnterAnimation (holder.itemView);
            //final FAQ faq = FAQList.get (position);
            
            holder.tvState.setTypeface (SetTypeFace.getTypeface (activity));
            if (selectedStateList.contains (stateList.get (position))) {
                holder.tvState.setText (stateList.get (position));
                holder.tvState.setBackgroundResource (R.drawable.state_button_selected);
                holder.tvState.setTextColor (activity.getResources ().getColor (R.color.text_color_white));
            } else {
                holder.tvState.setText (stateList.get (position));
                holder.tvState.setBackgroundResource (R.drawable.state_button_unselected);
                holder.tvState.setTextColor (activity.getResources ().getColor (R.color.app_text_color_dark));
            }
        }
        
        @Override
        public int getItemCount () {
            return stateList.size ();
        }
        
        public void SetOnItemClickListener (StateAdapter.OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;
        }
        
        public interface OnItemClickListener {
            public void onItemClick (View view, int position);
        }
        
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvState;
            
            
            public ViewHolder (View view) {
                super (view);
                tvState = (TextView) view.findViewById (R.id.tvState);
                view.setOnClickListener (this);
            }
            
            @Override
            public void onClick (View v) {
                if (selectedStateList.contains (stateList.get (getLayoutPosition ()))) {
                    MyProfileActivity.stateSelectedList3.remove (stateList.get (getLayoutPosition ()));
                    tvState.setBackgroundResource (R.drawable.state_button_unselected);
                    tvState.setTextColor (activity.getResources ().getColor (R.color.app_text_color_dark));
                } else {
                    selectedStateList.add (stateList.get (getLayoutPosition ()));
                    MyProfileActivity.stateSelectedList3.add (stateList.get (getLayoutPosition ()));
                    tvState.setBackgroundResource (R.drawable.state_button_selected);
                    tvState.setTextColor (activity.getResources ().getColor (R.color.text_color_white));
                }
                
            }
            
            
        }
        
        
    }
}