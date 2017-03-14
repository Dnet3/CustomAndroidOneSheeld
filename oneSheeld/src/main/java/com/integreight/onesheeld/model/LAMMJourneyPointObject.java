package com.integreight.onesheeld.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Max on 21/2/17.
 *
 * An object for storing Arduino database journey points
 *
 * Implements Parcelable so as to allow this custom object to be passed in
 * an Android Intent.
 */

public class LAMMJourneyPointObject implements Parcelable {

    private String mLatitude, mLongitude;

    public LAMMJourneyPointObject() {}

    public LAMMJourneyPointObject(String latitude, String longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
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

    @Override
    public String toString() {
        return "LAMMJourneyPointObject{" +
                "mLatitude='" + mLatitude + '\'' +
                ", mLongitude='" + mLongitude + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.mLatitude);
        dest.writeString(this.mLongitude);
    }

    protected LAMMJourneyPointObject(Parcel in) {
        this.mLatitude = in.readString();
        this.mLongitude = in.readString();
    }

    public static final Creator<LAMMJourneyPointObject> CREATOR = new Creator<LAMMJourneyPointObject>() {
        public LAMMJourneyPointObject createFromParcel(Parcel source) {
            return new LAMMJourneyPointObject(source);
        }

        public LAMMJourneyPointObject[] newArray(int size) {
            return new LAMMJourneyPointObject[size];
        }
    };
}
