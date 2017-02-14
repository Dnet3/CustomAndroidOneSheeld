package com.integreight.onesheeld.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Max on 14/2/17.
 *
 * An object for storing Arduino database history entries
 */

public class ArduinoHistoryObject implements Parcelable {

    private String mImageStorageAddress;
    private Float mLatitude, mLongitude;
    private String mTimeStamp;

    public ArduinoHistoryObject(String imageStorageAddress, Float latitude, Float longitude, String timeStamp) {
        mImageStorageAddress = imageStorageAddress;
        mLatitude = latitude;
        mLongitude = longitude;
        mTimeStamp = timeStamp;
    }

    public String getImageStorageAddress() {
        return mImageStorageAddress;
    }

    public void setImageStorageAddress(String imageStorageAddress) {
        mImageStorageAddress = imageStorageAddress;
    }

    public Float getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Float latitude) {
        mLatitude = latitude;
    }

    public Float getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Float longitude) {
        mLongitude = longitude;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        mTimeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "ArduinoHistoryObject{" +
                "mImageStorageAddress='" + mImageStorageAddress + '\'' +
                ", mTimeStamp='" + mTimeStamp + '\'' +
                ", mLatitude=" + mLatitude +
                ", mLongitude=" + mLongitude +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.mImageStorageAddress);
        dest.writeFloat(this.mLatitude);
        dest.writeFloat(this.mLongitude);
        dest.writeString(this.mTimeStamp);
    }

    protected ArduinoHistoryObject(Parcel in) {
        this.mImageStorageAddress = in.readString();
        this.mLatitude = in.readFloat();
        this.mLongitude = in.readFloat();
        this.mTimeStamp = in.readString();
    }

    public static final Creator<ArduinoHistoryObject> CREATOR = new Creator<ArduinoHistoryObject>() {
        public ArduinoHistoryObject createFromParcel(Parcel source) {
            return new ArduinoHistoryObject(source);
        }

        public ArduinoHistoryObject[] newArray(int size) {
            return new ArduinoHistoryObject[size];
        }
    };
}
