package com.clearsale.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clearsale.R;
import com.clearsale.model.MyAppointment;
import com.clearsale.utils.SetTypeFace;

import java.util.List;

/**
 * Created by actiknow on 9/21/17.
 */

public class MyAppointmentAdapter extends RecyclerView.Adapter<MyAppointmentAdapter.ViewHolder> {
    private Activity activity;
    private List<MyAppointment> myAppointmentList;
    
    public MyAppointmentAdapter (Activity activity, List<MyAppointment> myAppointmentList) {
        this.activity = activity;
        this.myAppointmentList = myAppointmentList;
    }
    
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from (parent.getContext ());
        final View sView = layoutInflater.inflate (R.layout.list_item_appointment, parent, false);
        return new ViewHolder (sView);
    }
    
    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        final MyAppointment myAppointment = myAppointmentList.get (position);
        holder.tvAddress.setTypeface (SetTypeFace.getTypeface (activity));
        
        holder.tvAddress.setText (myAppointment.getUser_address ());
        holder.tvPropertyAddress.setText (myAppointment.getProperty_address ());
        holder.tvStartDate.setText (myAppointment.getAccess_start_date ());
        holder.tvTime.setText (myAppointment.getTime_frame ());
        holder.tvComment.setText (myAppointment.getUser_comment ());
        switch (myAppointment.getStatus ()) {
            case "0":
                holder.tvStatus.setText ("Pending");
                break;
            case "1":
                holder.tvStatus.setText ("Accepted");
                break;
            case "2":
                holder.tvStatus.setText ("Rejected");
                break;
        }
        
    }
    
    @Override
    public int getItemCount () {
        return myAppointmentList.size ();
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvAddress;
        TextView tvPropertyAddress;
        TextView tvStartDate;
        TextView tvTime;
        TextView tvComment;
        TextView tvStatus;
        
        public ViewHolder (View itemView) {
            super (itemView);
            tvAddress = (TextView) itemView.findViewById (R.id.tvAddress);
            tvPropertyAddress = (TextView) itemView.findViewById (R.id.tvPropertyAddress);
            tvStartDate = (TextView) itemView.findViewById (R.id.tvStartDate);
            tvTime = (TextView) itemView.findViewById (R.id.tvTime);
            tvComment = (TextView) itemView.findViewById (R.id.tvComment);
            tvStatus = (TextView) itemView.findViewById (R.id.tvStatus);
            itemView.setOnClickListener (this);
            
        }
        
        @Override
        public void onClick (View view) {
            
        }
    }
}
