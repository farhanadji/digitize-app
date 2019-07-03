package com.farhan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.farhan.myapplication.Activity.LoginActivity;
import com.farhan.myapplication.Detail.UserItem;
import com.farhan.myapplication.Fragment.ProfileFragment;
import com.farhan.myapplication.Fragment.TransactionsFragment;
import com.farhan.myapplication.Fragment.BooksFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    UserItem userItem;
    public static String api_key = "";
    public String key_intent = "";


    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_layer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_book:
                    fragment = new BooksFragment();
                    Bundle user = new Bundle();
                    user.putParcelable("userData",userItem);
                    fragment.setArguments(user);
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_transaction:
                    fragment = new TransactionsFragment();
                    Bundle transaction = new Bundle();
                    transaction.putParcelable("userData",userItem);
                    fragment.setArguments(transaction);
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("userData",userItem);
                    fragment.setArguments(bundle);
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent login = new Intent(MainActivity.this, LoginActivity.class);
        Bundle extras = getIntent().getExtras();
        key_intent = getIntent().getStringExtra("api_key");
        if (extras != null) {
            userItem = extras.getParcelable("dataUser");
            api_key = userItem.getApi_token();
        }

        if(key_intent != null){
            api_key = key_intent;
        }
            if (api_key.isEmpty()) {
                startActivity(login);
                finish();
            } else {
                setContentView(R.layout.activity_main);
                BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                navigation.setSelectedItemId(R.id.navigation_book);
            }
    }

}
