package com.farhan.myapplication.Detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserItem implements Parcelable {
    @SerializedName("id")
    @Expose
    private String user_id;

    @SerializedName("name")
    @Expose
    private  String user_name;

    @SerializedName("email")
    @Expose
    private String user_email;

    @SerializedName("role")
    @Expose
    private String user_role;

    @SerializedName("api_token")
    @Expose
    private String api_token;

    public UserItem(String user_id, String user_name, String user_email, String user_role, String api_token) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_role = user_role;
        this.api_token = api_token;
    }

    protected UserItem(Parcel in) {
        user_id = in.readString();
        user_name = in.readString();
        user_email = in.readString();
        user_role = in.readString();
        api_token = in.readString();
    }

    public static final Creator<UserItem> CREATOR = new Creator<UserItem>() {
        @Override
        public UserItem createFromParcel(Parcel in) {
            return new UserItem(in);
        }

        @Override
        public UserItem[] newArray(int size) {
            return new UserItem[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(user_name);
        dest.writeString(user_email);
        dest.writeString(user_role);
        dest.writeString(api_token);
    }
}
