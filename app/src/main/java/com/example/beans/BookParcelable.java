package com.example.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class BookParcelable implements Parcelable {

    private int bookId;
    private String bookName;

    public BookParcelable(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    protected BookParcelable(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
    }

    public static final Parcelable.Creator<BookParcelable> CREATOR = new Creator<BookParcelable>() {
        @Override
        public BookParcelable createFromParcel(Parcel in) {
            return new BookParcelable(in);
        }

        @Override
        public BookParcelable[] newArray(int size) {
            return new BookParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
    }
}
