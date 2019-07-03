package com.farhan.myapplication.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.farhan.myapplication.API.ClientAPI;
import com.farhan.myapplication.API.UserResponse;
import com.farhan.myapplication.Activity.LoginActivity;
import com.farhan.myapplication.Detail.UserItem;
import com.farhan.myapplication.MainActivity;
import com.farhan.myapplication.R;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    TextView name;
    TextView email;
    Button btnLogout;
    UserItem user;
    private ClientAPI clientApi = null;
    private Call<UserResponse> call;
    private String api_token;
    ProgressDialog loadDialog;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        name = view.findViewById(R.id.profile_name);
        email = view.findViewById(R.id.profile_email);
        btnLogout = view.findViewById(R.id.btn_logout);
        user = getArguments().getParcelable("userData");

        name.setText(user.getUser_name());
        email.setText(user.getUser_email());
        api_token = user.getApi_token();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.btn_logout){
                    loadDialog = ProgressDialog.show(getContext(),"Logout", "Please wait..",true,false);
                    requestLogout();
                }
            }
        });

        return view;
    }

    public void requestLogout(){
        clientApi = ClientAPI.getInstance();
        call = clientApi.getApi().logoutRequest("Bearer " + api_token);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    String status = response.body().getStatus();
                    if(status.equals("success")){
                        MainActivity.api_key = "";
                        Toast.makeText(getActivity(), "Logout Successfully", Toast.LENGTH_SHORT).show();
                        Intent succes = new Intent(getActivity(), LoginActivity.class);
                        startActivity(succes);
                        getActivity().finish();
                        loadDialog.dismiss();
                    }else{
                        Toast.makeText(getActivity(), "Logout Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("error", t.getMessage());
                Toast.makeText(getActivity(), "Can't connect to server, please wait for a while..", Toast.LENGTH_LONG).show();
            }
        });

    }

}
