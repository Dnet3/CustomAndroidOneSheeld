package com.integreight.onesheeld.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Max on 14/2/17.
 *
 * An object for storing Arduino database images entries
 *
 * Implements Parcelable so as to allow this custom object to be passed in
 * an Android Intent.
 */

public class LAMMImageCaptureObject implements Parcelable {

    private String mImageStorageAddress, mLatitude, mLongitude, mTimestamp;

    public LAMMImageCaptureObject() {}

    public LAMMImageCaptureObject(String imageStorageAddress, String latitude, String longitude, String timestamp) {
        mImageStorageAddress = imageStorageAddress;
        mLatitude = latitude;
        mLongitude = longitude;
        mTimestamp = timestamp;
    }

    public String getImageStorageAddress() {
        return mImageStorageAddress;
    }

    public void setImageStorageAddress(String imageStorageAddress) {
        mImageStorageAddress = imageStorageAddress;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
    }

    public String getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(String timestamp) {
        mTimestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LAMMImageCaptureObject{" +
                "mImageStorageAddress='" + mImageStorageAddress + '\'' +
                ", mLatitude=" + mLatitude +
                ", mLongitude=" + mLongitude +
                ", mTimestamp=" + mTimestamp +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.mImageStorageAddress);
        dest.writeString(this.mLatitude);
        dest.writeString(this.mLongitude);
        dest.writeString(this.mTimestamp);
    }

    protected LAMMImageCaptureObject(Parcel in) {
        this.mImageStorageAddress = in.readString();
        this.mLatitude = in.readString();
        this.mLongitude = in.readString();
        this.mTimestamp = in.readString();
    }

    public static final Creator<LAMMImageCaptureObject> CREATOR = new Creator<LAMMImageCaptureObject>() {
        public LAMMImageCaptureObject createFromParcel(Parcel source) {
            return new LAMMImageCaptureObject(source);
        }

        public LAMMImageCaptureObject[] newArray(int size) {
            return new LAMMImageCaptureObject[size];
        }
    };
}
