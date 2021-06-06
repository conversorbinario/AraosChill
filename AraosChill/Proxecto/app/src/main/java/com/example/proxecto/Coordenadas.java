package com.example.proxecto;

import android.os.Parcel;
import android.os.Parcelable;

public class Coordenadas implements Parcelable {

    private String latitude;
    private String lonxitude;

    public Coordenadas(String latitude, String lonxitude) {
        this.latitude = latitude;
        this.lonxitude = lonxitude;
    }

    protected Coordenadas(Parcel in) {
        latitude = in.readString();
        lonxitude = in.readString();
    }

    public static final Creator<Coordenadas> CREATOR = new Creator<Coordenadas>() {
        @Override
        public Coordenadas createFromParcel(Parcel in) {
            return new Coordenadas(in);
        }

        @Override
        public Coordenadas[] newArray(int size) {
            return new Coordenadas[size];
        }
    };

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLonxitude() {
        return lonxitude;
    }

    public void setLonxitude(String lonxitude) {
        this.lonxitude = lonxitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(latitude);
        dest.writeString(lonxitude);
    }
}
