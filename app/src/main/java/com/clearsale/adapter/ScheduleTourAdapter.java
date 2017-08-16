package com.clearsale.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.clearsale.R;
import com.clearsale.model.ScheduleTour;
import com.clearsale.utils.AppConfigTags;
import com.clearsale.utils.AppConfigURL;
import com.clearsale.utils.BuyerDetailsPref;
import com.clearsale.utils.Constants;
import com.clearsale.utils.NetworkConnection;
import com.clearsale.utils.PropertyDetailsPref;
import com.clearsale.utils.SetTypeFace;
import com.clearsale.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class ScheduleTourAdapter extends RecyclerView.Adapter<ScheduleTourAdapter.ViewHolder> {
    OnItemClickListener mItemClickListener;
    int checked;
    ScheduleTour scheduleTour;
    PropertyDetailsPref propertyDetailsPref;
    private Activity activity;
    private List<ScheduleTour> scheduleTours = new ArrayList<ScheduleTour> ();
    
    public ScheduleTourAdapter (Activity activity, List<ScheduleTour> scheduleTours) {
        this.activity = activity;
        this.scheduleTours = scheduleTours;
    }
    
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from (parent.getContext ());
        final View sView = mInflater.inflate (R.layout.list_item_tour, parent, false);
        return new ViewHolder (sView);
    }
    
    @Override
    public void onBindViewHolder (final ViewHolder holder, int position) {//        runEnterAnimation (holder.itemView);
        propertyDetailsPref = PropertyDetailsPref.getInstance ();
        final ScheduleTour scheduleTour = scheduleTours.get (position);
    
        Utils.setTypefaceToAllViews (activity, holder.tvAddress);
    
        holder.tvAddress.setText (scheduleTour.getAddress ());
        holder.tvTime.setText (scheduleTour.getTime ());
        holder.tvDate.setText (scheduleTour.getDate ());
//        Glide.with(activity).load("").placeholder(testimonial.getImage2()).into(holder.ivVideo);
    }
    
    @Override
    public int getItemCount () {
        return scheduleTours.size ();
    }
    
    public void SetOnItemClickListener (final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    
    private void ScheduleTourDialog (final int id, String buyer_address) {
        
        final MaterialDialog dialog = new MaterialDialog.Builder (activity)
                .typeface (SetTypeFace.getTypeface (activity), SetTypeFace.getTypeface (activity))
                .canceledOnTouchOutside (false)
                .onNegative (new MaterialDialog.SingleButtonCallback () {
                    @Override
                    public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss ();
                    }
                })
                .negativeText ("CANCEL")
                .positiveText ("SUBMIT")
                .positiveColor (activity.getResources ().getColor (R.color.primary))
                .customView (R.layout.dialog_schedule_appointment, true)
                .build ();
        
        final EditText etComment = (EditText) dialog.getCustomView ().findViewById (R.id.etComment);
        final EditText etNumberOfUsers = (EditText) dialog.getCustomView ().findViewById (R.id.etNumberOfUsers);
        final EditText etAddress = (EditText) dialog.getCustomView ().findViewById (R.id.etAddress);
        Utils.setTypefaceToAllViews (activity, etAddress);
        etAddress.setText (buyer_address);
        
        dialog.getActionButton (DialogAction.POSITIVE).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                String comment = etComment.getText ().toString ();
                String numberOfUsers = etNumberOfUsers.getText ().toString ();
                String address = etAddress.getText ().toString ();
                if (numberOfUsers.equalsIgnoreCase ("")) {
                    Utils.showToast (activity, "Please Enter Number of Users", true);
                } else if (address.equalsIgnoreCase ("")) {
                    Utils.showToast (activity, "Please Enter the Address", true);
                } else if (! numberOfUsers.equalsIgnoreCase ("") && ! address.equalsIgnoreCase ("")) {
                    scheduleAppointment (comment, numberOfUsers, address, id, 1);
                    dialog.dismiss ();
                }
            }
        });
        
        dialog.show ();
        
    }
    
    private void scheduleAppointment (final String etComment, final String etNumberOfUsers, final String etAddress, final int access_id, final int checked) {
        if (NetworkConnection.isNetworkAvailable (activity)) {
            Utils.showLog (Log.INFO, "" + AppConfigTags.URL, AppConfigURL.URL_SCHEDULE_APPOINTMENT, true);
            StringRequest strRequest1 = new StringRequest (Request.Method.POST, AppConfigURL.URL_SCHEDULE_APPOINTMENT,
                    new com.android.volley.Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            // FAQList.clear ();
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);
                                    if (! error) {
                                        Utils.showToast (activity, message, true);
                                    } else {
                                        Utils.showToast (activity, message, true);
                                    }
                                } catch (Exception e) {
                                    
                                    Utils.showToast (activity, activity.getResources ().getString (R.string.snackbar_text_exception_occurred), true);
                                    e.printStackTrace ();
                                }
                            } else {
                                Utils.showToast (activity, activity.getResources ().getString (R.string.snackbar_text_error_occurred), true);
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                            Utils.showToast (activity, activity.getResources ().getString (R.string.snackbar_text_error_occurred), true);
                            
                        }
                    }) {
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    BuyerDetailsPref buyerDetailsPref = BuyerDetailsPref.getInstance ();
                    Map<String, String> params = new Hashtable<String, String> ();
                    params.put (AppConfigTags.TYPE, "BookAppointment");
                    params.put (AppConfigTags.SCHEDULE_ACCESS_TOKEN_ID, String.valueOf (access_id));
                    params.put (AppConfigTags.SCHEDULE_NEW_PEOPLE, etNumberOfUsers);
                    params.put (AppConfigTags.BUYER_ID, String.valueOf (buyerDetailsPref.getIntPref (activity, BuyerDetailsPref.BUYER_ID)));
                    params.put (AppConfigTags.SCHEDULE_COMMENT, etComment);
                    params.put (AppConfigTags.SCHEDULE_ADDRESS, etAddress);
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
            Utils.showToast (activity, activity.getResources ().getString (R.string.snackbar_text_no_internet_connection_available), true);
            //Intent dialogIntent = new Intent(Settings.ACTION_SETTINGS);
            //dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //startActivity(dialogIntent);
        }
    }
    
    public interface OnItemClickListener {
        public void onItemClick (View view, int position);
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvAddress;
        TextView tvDate;
        TextView tvTime;
        TextView tvScheduleATour;
        
        public ViewHolder (View view) {
            super (view);
            tvAddress = (TextView) view.findViewById (R.id.tvName);
            tvDate = (TextView) view.findViewById (R.id.tvStartDate);
            tvTime = (TextView) view.findViewById (R.id.tvTime);
            tvScheduleATour = (TextView) view.findViewById (R.id.tvScheduleATour);
            view.setOnClickListener (this);
        }
        
        @Override
        public void onClick (View v) {
            scheduleTour = scheduleTours.get (getLayoutPosition ());
            tvScheduleATour.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick (View view) {
                    ScheduleTourDialog (scheduleTour.getId (), scheduleTour.getBuyer_address ());
                    
                }
                
                
            });
            
        }
    }
}


