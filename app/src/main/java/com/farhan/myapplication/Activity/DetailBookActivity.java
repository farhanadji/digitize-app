package com.farhan.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.farhan.myapplication.API.BorrowResponse;
import com.farhan.myapplication.API.ClientAPI;
import com.farhan.myapplication.API.DetailBookResponse;
import com.farhan.myapplication.API.ServerResponse;
import com.farhan.myapplication.API.UserResponse;
import com.farhan.myapplication.Detail.BookItem;
import com.farhan.myapplication.MainActivity;
import com.farhan.myapplication.R;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBookActivity extends AppCompatActivity implements View.OnClickListener {
    TextView title;
    TextView author;
    TextView price;
    TextView total;
    Button borrowDate;
    Button returnDate;
    Button submit;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String borrow_date;
    private String return_date;
    Date brw;
    Date rtn;
    private ClientAPI clientApi = null;
    private Call<DetailBookResponse> call;
    private ClientAPI clientBorrow = null;
    private Call<BorrowResponse> callBorrow;
    BookItem bookItem;
    private int book_id;
    private int user_id;
    private String api_token;
    private long days;
    private double dailyPrice;
    static double totalPrice;
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    String statusRes;
    ProgressBar pb;
    ProgressDialog loadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);

        book_id = getIntent().getIntExtra("book_id", 0);
        user_id = getIntent().getIntExtra("user_id", 0);
        api_token = getIntent().getStringExtra("api_token");

        title = findViewById(R.id.bookTitle);
        author = findViewById(R.id.bookAuthor);
        price = findViewById(R.id.bookPrice);
        borrowDate = findViewById(R.id.btn_borrowdate);
        returnDate = findViewById(R.id.btn_returndate);
        submit = findViewById(R.id.btn_requestBorrow);
        total = findViewById(R.id.totalBookPrice);
        pb = findViewById(R.id.pg_detail);
        pb.setVisibility(View.VISIBLE);

        getData(book_id);
        borrowDate.setOnClickListener(this);
        returnDate.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    public void getData(int id) {
        clientApi = ClientAPI.getInstance();
        call = clientApi.getApi().getBooksbyId(id);
        call.enqueue(new Callback<DetailBookResponse>() {
            @Override
            public void onResponse(Call<DetailBookResponse> call, Response<DetailBookResponse> response) {
                if (response.isSuccessful()) {
                    bookItem = response.body().getData();
                    String titleActionBar = bookItem.getBook_title().toUpperCase();
                    Objects.requireNonNull(getSupportActionBar()).setTitle(titleActionBar);
                    title.setText(bookItem.getBook_title());
                    author.setText(bookItem.getBook_author());
                    price.setText(formatRupiah.format(bookItem.getPrice()));
                    dailyPrice = bookItem.getPrice();
                    pb.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(DetailBookActivity.this, "Fail to get book detail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailBookResponse> call, Throwable t) {
                Toast.makeText(DetailBookActivity.this, "Can't connect to server, please wait for a while..", Toast.LENGTH_LONG).show();
                Log.d("error", t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_borrowdate:
                requestBorrowDate();
                break;
            case R.id.btn_returndate:
                if (brw == null) {
                    Toast.makeText(this, "Please select borrow date first!", Toast.LENGTH_LONG).show();
                } else {
                    requestReturnDate();
                }
                break;
            case R.id.btn_requestBorrow:
                if(rtn == null){
                    Toast.makeText(this, "Please select date first!", Toast.LENGTH_LONG).show();
                }else {
                    loadDialog = ProgressDialog.show(this,"Borrow Book","Please wait...",true,false);
                    requestBorrow();
                }
                break;
        }

    }

    public void requestBorrowDate() {
        int bYear, bMonth, bDay;
        final Calendar borrow = Calendar.getInstance();
        bYear = borrow.get(Calendar.YEAR);
        bMonth = borrow.get(Calendar.MONTH);
        bDay = borrow.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                borrow_date = dateFormat.format(calendar.getTime());
                brw = calendar.getTime();
                borrowDate.setText(borrow_date);
            }
        }, bYear, bMonth, bDay);
        datePickerDialog.show();
    }
    public void requestReturnDate() {
        int rYear, rMonth, rDay;
        final Calendar dateReturn = Calendar.getInstance();
        rYear = dateReturn.get(Calendar.YEAR);
        rMonth = dateReturn.get(Calendar.MONTH);
        rDay = dateReturn.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                return_date = dateFormat.format(calendar.getTime());
                rtn = calendar.getTime();
                returnDate.setText(return_date);
                days = (rtn.getTime() - brw.getTime()) / (1000 * 60 * 60 * 24);
                Log.d("value", Long.toString(days));
                totalPrice = days * dailyPrice;
                Log.d("price",Double.toString(totalPrice));
                total.setText(formatRupiah.format(totalPrice));
            }
        }, rYear, rMonth, rDay);
        datePickerDialog.show();
    }
    public void requestBorrow(){
        clientBorrow = ClientAPI.getInstance();
        Log.d("VALUE REQUEST", Double.toString(totalPrice));
        callBorrow = clientBorrow.getApi().borrowRequest("Bearer " + api_token,borrow_date,return_date,totalPrice,book_id);
        callBorrow.enqueue(new Callback<BorrowResponse>() {
            @Override
            public void onResponse(Call<BorrowResponse> call, Response<BorrowResponse> response) {
                if(response.isSuccessful()){
                    statusRes = response.body().getStatus();
                    if(statusRes.equals("success")){
                        Toast.makeText(DetailBookActivity.this,"Borrow Book Successfully",Toast.LENGTH_LONG).show();
                        Intent success = new Intent(DetailBookActivity.this, MainActivity.class);
                        success.putExtra("api_key",api_token);
                        startActivity(success);
                        finish();
                    }else{
                        Toast.makeText(DetailBookActivity.this,"Failed to borrow book",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BorrowResponse> call, Throwable t) {
                Log.d("error", t.getMessage());
            }
        });

    }
}
