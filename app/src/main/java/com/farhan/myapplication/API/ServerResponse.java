package com.farhan.myapplication.API;

import com.farhan.myapplication.Detail.BookItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ServerResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ArrayList<BookItem> data = null;

    public ServerResponse(String status, String message, ArrayList<BookItem> data) {
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

    public ArrayList<BookItem> getData() {
        return data;
    }

    public void setData(ArrayList<BookItem> data) {
        this.data = data;
    }
}
