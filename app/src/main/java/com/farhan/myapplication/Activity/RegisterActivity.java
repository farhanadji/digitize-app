package com.farhan.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.farhan.myapplication.API.ClientAPI;
import com.farhan.myapplication.API.UserResponse;
import com.farhan.myapplication.Detail.UserItem;
import com.farhan.myapplication.MainActivity;
import com.farhan.myapplication.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText email;
    EditText name;
    EditText password;
    Button registerBtn;
    ProgressDialog regisDialog;
    private ClientAPI clientApi = null;
    private Call<UserResponse> call;
    private UserItem user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Objects.requireNonNull(getSupportActionBar()).hide();
        email = findViewById(R.id.email_register);
        name = findViewById(R.id.name_register);
        password = findViewById(R.id.password_register);
        registerBtn = findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                    regisDialog = ProgressDialog.show(this,null,"Please wait..",true,false);
                    registerRequest();
                break;
        }
    }

    private void registerRequest(){
        clientApi = ClientAPI.getInstance();
        call = clientApi.getApi().registerRequest(name.getText().toString(),email.getText().toString(),password.getText().toString());
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    user = response.body().getData();
                    if(user!=null){
                        Toast.makeText(RegisterActivity.this,"Register Successfully, Login with your account!", Toast.LENGTH_LONG).show();
                        Intent success = new Intent(RegisterActivity.this, LoginActivity.class);
                        regisDialog.dismiss();
                        startActivity(success);
                        finish();
                    }else{
                        String error = response.body().getMessage();
                        Toast.makeText(RegisterActivity.this,error, Toast.LENGTH_LONG).show();
                    }
                }else{
                    regisDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("error", t.getMessage());
                Toast.makeText(RegisterActivity.this, "Can't connect to server, please wait for a while..", Toast.LENGTH_LONG).show();
            }
        });

    }
}
