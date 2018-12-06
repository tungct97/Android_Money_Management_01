package com.framgia.moneymanagement.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Alarm implements Parcelable {
    private String mId;
    private String mAlarmName;
    private Integer mHour;
    private Integer mMinute;

    public Alarm() {
    }

    public Alarm(String id, String alarmName, Integer hour, Integer minute) {
        mId = id;
        mAlarmName = alarmName;
        mHour = hour;
        mMinute = minute;
    }

    protected Alarm(Parcel in) {
        mId = in.readString();
        mAlarmName = in.readString();
        if (in.readByte() == 0) {
            mHour = null;
        } else {
            mHour = in.readInt();
        }
        if (in.readByte() == 0) {
            mMinute = null;
        } else {
            mMinute = in.readInt();
        }
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getAlarmName() {
        return mAlarmName;
    }

    public void setAlarmName(String alarmName) {
        mAlarmName = alarmName;
    }

    public Integer getHour() {
        return mHour;
    }

    public void setHour(Integer hour) {
        mHour = hour;
    }

    public Integer getMinute() {
        return mMinute;
    }

    public void setMinute(Integer minute) {
        mMinute = minute;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mAlarmName);
        if (mHour == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mHour);
        }
        if (mMinute == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mMinute);
        }
    }

    public static class Key {
        public static final String ALARM = "alarm";
    }
}
