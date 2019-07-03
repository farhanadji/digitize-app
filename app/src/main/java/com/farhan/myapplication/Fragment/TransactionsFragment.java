package com.farhan.myapplication.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.farhan.myapplication.API.ClientAPI;
import com.farhan.myapplication.API.TransactionResponse;
import com.farhan.myapplication.Adapter.TransactionAdapter;
import com.farhan.myapplication.Detail.TransactionItem;
import com.farhan.myapplication.Detail.UserItem;
import com.farhan.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionsFragment extends Fragment {
    RecyclerView recyclerView;
    TransactionAdapter transactionAdapter;
    private ClientAPI clientApi = null;
    private Call<TransactionResponse> call;
    private String TAG = "log";
    private ArrayList<TransactionItem> trans = new ArrayList<>();
    private static final String KEY_TRANSACTION = "key_transactions";
    UserItem userData;
    ProgressBar pb;


    public TransactionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null ){
            trans = savedInstanceState.getParcelableArrayList(KEY_TRANSACTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"masuk oncreate");
        View view = inflater.inflate(R.layout.fragment_transaction_history,container,false);
        pb = view.findViewById(R.id.pg_transaction);
        pb.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.transaction_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userData = getArguments().getParcelable("userData");
        if(savedInstanceState != null){
            trans = savedInstanceState.getParcelableArrayList(KEY_TRANSACTION);
            transactionAdapter = new TransactionAdapter(getContext(),trans);
            recyclerView.setAdapter(transactionAdapter);
            transactionAdapter.notifyDataSetChanged();
            pb.setVisibility(View.INVISIBLE);
        }else{
            getTransactionData();
        }

        return view;
    }

    private void getTransactionData(){
        clientApi = ClientAPI.getInstance();
        call = clientApi.getApi().getMyTransaction("Bearer " + userData.getApi_token());
        Log.d(TAG,"masuk");

        call.enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                Log.d(TAG,"onresponse");
                trans = response.body().getData();
                if(response.isSuccessful()){
                    if(trans!=null){
                        Log.d(TAG,"success");
                        transactionAdapter = new TransactionAdapter(getContext(),trans);
                        recyclerView.setAdapter(transactionAdapter);
                        transactionAdapter.notifyDataSetChanged();
                        pb.setVisibility(View.INVISIBLE);
                    }else{
                        Toast.makeText(getActivity(), "Transaction not found!", Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_TRANSACTION,trans);
        super.onSaveInstanceState(outState);
    }
}
