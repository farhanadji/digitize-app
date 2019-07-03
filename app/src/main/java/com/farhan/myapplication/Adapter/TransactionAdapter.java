package com.farhan.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farhan.myapplication.Detail.TransactionItem;
import com.farhan.myapplication.R;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<TransactionItem> transactionItems;
    private Context context;
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public TransactionAdapter(Context context, List<TransactionItem> transactionItems){
        this.context = context;
        this.transactionItems = transactionItems;
    }

    @NonNull
    @Override
    public TransactionAdapter.TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaction_item,parent,false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.TransactionViewHolder holder, int position) {
        final int transactionId = transactionItems.get(position).getTransaction_id();

        holder.TransactionId.setText(Integer.toString(transactionItems.get(position).getTransaction_id()));
        holder.Transaction_Status.setText(transactionItems.get(position).getStatus());
        holder.TransactionBorrowed.setText(transactionItems.get(position).getBorrowdate());
        holder.TransactionReturned.setText(transactionItems.get(position).getReturndate());
        holder.TransactionPrice.setText(formatRupiah.format(transactionItems.get(position).getTransaction_price()));



    }

    @Override
    public int getItemCount() {
        return transactionItems.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder{
        TextView Transaction_Status;
        TextView TransactionId;
        TextView TransactionBorrowed;
        TextView TransactionReturned;
        TextView TransactionPrice;
        CardView TransactionCv;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            Transaction_Status = itemView.findViewById(R.id.tv_status);
            TransactionId = itemView.findViewById(R.id.tv_transactionId);
            TransactionBorrowed = itemView.findViewById(R.id.tv_borrowed);
            TransactionReturned = itemView.findViewById(R.id.tv_returned);
            TransactionPrice = itemView.findViewById(R.id.tv_price_trans);
            TransactionCv = itemView.findViewById(R.id.item_transaction_cv);
        }
    }
}
