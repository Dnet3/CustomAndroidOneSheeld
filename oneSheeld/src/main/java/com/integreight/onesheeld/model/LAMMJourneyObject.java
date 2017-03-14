package com.integreight.onesheeld.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Max on 21/2/17.
 *
 * An object for storing a complete Arduino journey entry
 *
 * Implements Parcelable so as to allow this custom object to be passed in
 * an Android Intent.
 */

public class LAMMJourneyObject implements Parcelable {

    private String mEndTime;
    private ArrayList<LAMMJourneyPointObject> mJourneyPoints;
    private String mStartTime;

    public LAMMJourneyObject() {}

    public LAMMJourneyObject(ArrayList<LAMMJourneyPointObject> journeyPoints, String startTime) {
        mJourneyPoints = journeyPoints;
        mStartTime = startTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public ArrayList getJourneyPoints() {
        return mJourneyPoints;
    }

    public void setJourneyPoints(ArrayList<LAMMJourneyPointObject> journeyPoints) {
        mJourneyPoints = journeyPoints;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    @Override
    public String toString() {
        return "LAMMJourneyObject{" +
                "mEndTime='" + mEndTime + '\'' +
                ", mJourneyPoints=" + mJourneyPoints +
                ", mStartTime='" + mStartTime + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.mEndTime);
        dest.writeSerializable(this.mJourneyPoints);
        dest.writeString(this.mStartTime);
    }

    protected LAMMJourneyObject(Parcel in) {
        this.mEndTime = in.readString();
        this.mJourneyPoints = (ArrayList<LAMMJourneyPointObject>) in.readSerializable();
        this.mStartTime = in.readString();
    }

    public static final Creator<LAMMJourneyObject> CREATOR = new Creator<LAMMJourneyObject>() {
        public LAMMJourneyObject createFromParcel(Parcel source) {
            return new LAMMJourneyObject(source);
        }

        public LAMMJourneyObject[] newArray(int size) {
            return new LAMMJourneyObject[size];
        }
    };
}
