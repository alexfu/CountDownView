package com.alexfu.countdownview;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

class CountDownViewState extends View.BaseSavedState {
    long startDuration;
    long currentDuration;
    boolean timerRunning;

    public static final Parcelable.Creator<CountDownViewState> CREATOR
            = new Parcelable.Creator<CountDownViewState>() {
        public CountDownViewState createFromParcel(Parcel in) {
            return new CountDownViewState(in);
        }

        public CountDownViewState[] newArray(int size) {
            return new CountDownViewState[size];
        }
    };

    CountDownViewState(Parcelable superState) {
        super(superState);
    }

    CountDownViewState(Parcel source) {
        super(source);
        startDuration = source.readLong();
        currentDuration = source.readLong();
        timerRunning = source.readInt() == 1;
    }

    @Override public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeLong(startDuration);
        out.writeLong(currentDuration);
        out.writeInt(timerRunning ? 1 : 0);
    }
}
