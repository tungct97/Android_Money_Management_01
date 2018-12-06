package com.framgia.moneymanagement.screen.alarm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.framgia.moneymanagement.R;
import com.framgia.moneymanagement.data.model.Alarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    private List<Alarm> mAlarms;
    private LayoutInflater mLayoutInflater;
    private OnItemLongClickListener mListener;

    public AlarmAdapter(OnItemLongClickListener itemLongClickListener) {
        mAlarms = new ArrayList<>();
        mListener = itemLongClickListener;
    }

    @NonNull
    @Override
    public AlarmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        Context context = parent.getContext();
        View view = mLayoutInflater.inflate(R.layout.item_alram, parent, false);
        return new AlarmAdapter.ViewHolder(context, view, mListener);
    }

    public void addData(List<Alarm> alarms) {
        if (alarms == null) {
            return;
        }
        if (getItemCount() != 0) {
            mAlarms.clear();
        }
        mAlarms.addAll(alarms);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmAdapter.ViewHolder holder, int position) {
        holder.bindData(mAlarms.get(position));
    }


    @Override
    public int getItemCount() {
        return mAlarms != null ? mAlarms.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView mTextViewTimeAlarm, mTextViewNameAlarm;
        private Context mContext;
        private CardView mCardView;
        private Alarm mAlarm;
        private OnItemLongClickListener mListener;

        ViewHolder(Context context, View itemView, OnItemLongClickListener itemLongClickListener) {
            super(itemView);
            mListener = itemLongClickListener;
            mContext = context;
            mCardView = itemView.findViewById(R.id.cardview_alarm);
            mTextViewNameAlarm = itemView.findViewById(R.id.text_name);
            mTextViewTimeAlarm = itemView.findViewById(R.id.text_time);
            mCardView.setOnLongClickListener(this);
        }

        void bindData(Alarm alarm) {
            if (alarm == null) {
                return;
            }
            mAlarm = alarm;
            mTextViewTimeAlarm.setText(alarm.getHour() + ":" + alarm.getMinute());
            mTextViewNameAlarm.setText(alarm.getAlarmName());
        }

        @Override
        public boolean onLongClick(View view) {
            mListener.OnLongClickListener(mAlarm.getId());
            return false;
        }
    }

    public interface OnItemLongClickListener {
        void OnLongClickListener(String idAlarm);
    }
}
