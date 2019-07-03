package com.farhan.myapplication.Detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TransactionItem implements Parcelable {
    @SerializedName("id")
    @Expose
    private int transaction_id;

    @SerializedName("total_price")
    @Expose
    private double transaction_price;

    @SerializedName("borrow_date")
    @Expose
    private String  borrowdate;

    @SerializedName("return_date")
    @Expose
    private String returndate;

    @SerializedName("status")
    @Expose
    private String status;

    public TransactionItem(int transaction_id, double transaction_price, String borrowdate, String returndate, String status) {
        this.transaction_id = transaction_id;
        this.transaction_price = transaction_price;
        this.borrowdate = borrowdate;
        this.returndate = returndate;
        this.status = status;
    }

    protected TransactionItem(Parcel in) {
        transaction_id = in.readInt();
        transaction_price = in.readDouble();
    }

    public static final Creator<TransactionItem> CREATOR = new Creator<TransactionItem>() {
        @Override
        public TransactionItem createFromParcel(Parcel in) {
            return new TransactionItem(in);
        }

        @Override
        public TransactionItem[] newArray(int size) {
            return new TransactionItem[size];
        }
    };

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public double getTransaction_price() {
        return transaction_price;
    }

    public void setTransaction_price(double transaction_price) {
        this.transaction_price = transaction_price;
    }

    public String getBorrowdate() {
        return borrowdate;
    }

    public void setBorrowdate(String borrowdate) {
        this.borrowdate = borrowdate;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }

    public String  getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(transaction_id);
        dest.writeDouble(transaction_price);
    }
}
