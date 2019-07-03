package com.farhan.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farhan.myapplication.Activity.DetailBookActivity;
import com.farhan.myapplication.Activity.RegisterActivity;
import com.farhan.myapplication.Detail.BookItem;
import com.farhan.myapplication.Detail.UserItem;
import com.farhan.myapplication.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<BookItem> book;
    private Context context;
    UserItem user;
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public BookAdapter(Context context,List<BookItem> book,UserItem user) {
        this.book = book;
        this.context = context;
        this.user = user;
    }

    public BookAdapter(List<BookItem> book){
        this.book = book;
    }

    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_book_item,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
        final int bookId = book.get(position).getBook_id();
        holder.bookTitle.setText(book.get(position).getBook_title());
        holder.bookAuthor.setText(book.get(position).getBook_author());
        holder.bookPrice.setText(formatRupiah.format(book.get(position).getPrice()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailBook = new Intent(context, DetailBookActivity.class);
                detailBook.putExtra("book_id", bookId);
                detailBook.putExtra("user_id", user.getUser_id());
                detailBook.putExtra("api_token", user.getApi_token());
                context.startActivity(detailBook);
            }
        });

    }

    @Override
    public int getItemCount() {
        return book.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle;
        TextView bookAuthor;
        TextView bookPrice;
        CardView cardView;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.tv_title);
            bookAuthor = itemView.findViewById(R.id.tv_author);
            bookPrice = itemView.findViewById(R.id.tv_price);
            cardView = itemView.findViewById(R.id.item_book_cardview);

        }
    }
}
