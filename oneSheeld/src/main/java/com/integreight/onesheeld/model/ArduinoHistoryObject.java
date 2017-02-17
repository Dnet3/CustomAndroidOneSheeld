package com.integreight.onesheeld.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Max on 14/2/17.
 *
 * An object for storing Arduino database location_history entries
 *
 * Implements Parcelable so as to allow this custom object to be passed in
 * an Android Intent.
 */

public class ArduinoHistoryObject implements Parcelable {

    private String mImageStorageAddress;
    private Float mLatitude, mLongitude;

    public ArduinoHistoryObject(String imageStorageAddress, Float latitude, Float longitude) {
        mImageStorageAddress = imageStorageAddress;
        mLatitude = latitude;
        mLongitude = longitude;
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

    @Override
    public String toString() {
        return "ArduinoHistoryObject{" +
                "mImageStorageAddress='" + mImageStorageAddress + '\'' +
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
    }

    protected ArduinoHistoryObject(Parcel in) {
        this.mImageStorageAddress = in.readString();
        this.mLatitude = in.readFloat();
        this.mLongitude = in.readFloat();
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
