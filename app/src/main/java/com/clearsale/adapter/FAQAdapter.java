package com.clearsale.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clearsale.R;
import com.clearsale.model.FAQ;
import com.clearsale.utils.ExpandableTextView;
import com.clearsale.utils.SetTypeFace;

import java.util.ArrayList;
import java.util.List;


public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder> {
    OnItemClickListener mItemClickListener;
    private Activity activity;
    private List<FAQ> FAQList = new ArrayList<FAQ> ();
    
    public FAQAdapter (Activity activity, List<FAQ> FAQList) {
        this.activity = activity;
        this.FAQList = FAQList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.listview_item_faq, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//        runEnterAnimation (holder.itemView);
        final FAQ faq = FAQList.get (position);
    
        holder.tvAnswer.setTypeface (SetTypeFace.getTypeface (activity));
        holder.tvQuestion.setTypeface (SetTypeFace.getTypeface (activity));
    
        holder.tvQuestion.setText (faq.getQuestion ());
        if (faq.getAnswer ().length () > (ExpandableTextView.DEFAULT_TRIM_LENGTH + ExpandableTextView.DEFAULT_TRIM_LENGTH)) {
            holder.tvAnswer.setText (faq.getAnswer ());
            holder.tvAnswer.setTrimLength (ExpandableTextView.DEFAULT_TRIM_LENGTH);
            holder.tvAnswer.setVisibility (View.VISIBLE);
            holder.tvAnswer2.setVisibility (View.GONE);
        } else {
            holder.tvAnswer2.setText (faq.getAnswer ());
            holder.tvAnswer2.setVisibility (View.VISIBLE);
            holder.tvAnswer.setVisibility (View.GONE);
        }
        
        final ViewHolder tempholder = holder;
    }

    @Override
    public int getItemCount() {
        return FAQList.size ();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick (View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvQuestion;
        com.clearsale.utils.ExpandableTextView tvAnswer;
        TextView tvAnswer2;
    
    
        public ViewHolder(View view) {
            super(view);
            tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
            tvAnswer = (com.clearsale.utils.ExpandableTextView) view.findViewById (R.id.tvAnswers);
            tvAnswer2 = (TextView) view.findViewById (R.id.tvAnswers2);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            FAQ FAQ = FAQList.get (getLayoutPosition ());
        }
    }
}