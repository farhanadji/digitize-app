package com.farhan.myapplication.Detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookItem implements Parcelable {
    @SerializedName("id")
    @Expose
    private int book_id;

    @SerializedName("title")
    @Expose
    private String book_title;

    @SerializedName("author")
    @Expose
    private String book_author;

    @SerializedName("price")
    @Expose
    private double price;

    public BookItem(int book_id, String book_title, String book_author, double price) {
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.price = price;
    }

    protected BookItem(Parcel in) {
        book_id = in.readInt();
        book_title = in.readString();
        book_author = in.readString();
        price = in.readDouble();
    }

    public static final Creator<BookItem> CREATOR = new Creator<BookItem>() {
        @Override
        public BookItem createFromParcel(Parcel in) {
            return new BookItem(in);
        }

        @Override
        public BookItem[] newArray(int size) {
            return new BookItem[size];
        }
    };

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(book_id);
        dest.writeString(book_title);
        dest.writeString(book_author);
        dest.writeDouble(price);
    }
}
