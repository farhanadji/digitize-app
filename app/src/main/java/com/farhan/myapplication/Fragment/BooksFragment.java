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
import com.farhan.myapplication.API.ServerResponse;
import com.farhan.myapplication.Adapter.BookAdapter;
import com.farhan.myapplication.Detail.BookItem;
import com.farhan.myapplication.Detail.UserItem;
import com.farhan.myapplication.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class BooksFragment extends Fragment {
    private RecyclerView rvBooks;
    private BookAdapter adapter;
    public String TAG = "TEST";
    private ClientAPI clientApi = null;
    private Call<ServerResponse> call;
    private ArrayList<BookItem> bookItems =  new ArrayList<>();
    private static final String KEY_BOOK = "key_books";
    UserItem userData;
    ProgressBar pb;

    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null){
            bookItems = savedInstanceState.getParcelableArrayList(KEY_BOOK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books,container,false);
        Log.d(TAG,"sucess fragment");
        pb = view.findViewById(R.id.pg_books);
        pb.setVisibility(View.VISIBLE);

        userData = getArguments().getParcelable("userData");

        rvBooks = view.findViewById(R.id.books_rv);
        rvBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        if(savedInstanceState != null){
            bookItems = savedInstanceState.getParcelableArrayList(KEY_BOOK);
            adapter = new BookAdapter(getContext(),bookItems,userData);
            rvBooks.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            pb.setVisibility(View.INVISIBLE);
        }else {
            getBookData();
        }
        return view;
    }

    private void getBookData(){
        Log.d(TAG,"masuk get");
        clientApi = ClientAPI.getInstance();
        call = clientApi.getApi().getBooks();
        Log.d(TAG,"bawah request");
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()) {
                    bookItems = response.body().getData();
                    if (bookItems != null) {
                        Log.d(TAG, "sucess");
                        adapter = new BookAdapter(getContext(), bookItems,userData);
                        rvBooks.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        pb.setVisibility(View.INVISIBLE);
                    } else {
                        Log.d(TAG,"fail");
                        Toast.makeText(getActivity(), "Book not found!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_BOOK,bookItems);
        super.onSaveInstanceState(outState);
    }
}
