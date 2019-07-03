package com.farhan.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.farhan.myapplication.API.ClientAPI;
import com.farhan.myapplication.API.ServerResponse;
import com.farhan.myapplication.API.UserResponse;
import com.farhan.myapplication.Detail.UserItem;
import com.farhan.myapplication.MainActivity;
import com.farhan.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView register;
    EditText email;
    EditText password;
    Button loginBtn;
    ProgressDialog loadDialog;
    private ClientAPI clientApi = null;
    private Call<UserResponse> call;
    private UserItem user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);

        register = findViewById(R.id.create_account);
        register.setOnClickListener(this);
        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_account:
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
                break;
            case R.id.login_btn:
                loadDialog = ProgressDialog.show(this,null,"Please wait..",true,false);
                requestLogin();
                break;
        }
    }

    public void requestLogin(){
        clientApi = ClientAPI.getInstance();
        call = clientApi.getApi().loginRequest(email.getText().toString(),password.getText().toString());
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    user = response.body().getData();
                    if(user!= null){
                        Toast.makeText(LoginActivity.this,"Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent success = new Intent(LoginActivity.this, MainActivity.class);
                        success.putExtra("dataUser",user);
                        loadDialog.dismiss();
                        startActivity(success);
                        finish();
                    }else{
                        String error = response.body().getMessage();
                        Toast.makeText(LoginActivity.this, "Email or Password incorrect!",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    loadDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("error", t.getMessage());
                Toast.makeText(LoginActivity.this, "Can't connect to server, please wait for a while..", Toast.LENGTH_LONG).show();
            }
        });

    }
}
