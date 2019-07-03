package com.farhan.myapplication.API;

import com.farhan.myapplication.Detail.TransactionItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TransactionResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ArrayList<TransactionItem> data = null;

    public TransactionResponse(String status, String message, ArrayList<TransactionItem> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<TransactionItem> getData() {
        return data;
    }

    public void setData(ArrayList<TransactionItem> data) {
        this.data = data;
    }
}
