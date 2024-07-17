package com.mira.booklibraryapp.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.mira.booklibraryapp.R;
import com.mira.booklibraryapp.database.MyDatabaseHelper;
import com.mira.booklibraryapp.view.update.UpdateActivity;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> book_id, book_title, book_author, book_pages;
    private MyDatabaseHelper myDB;
    private OnBookListener mListener;

    public CustomAdapter(Context context, ArrayList<String> book_id, ArrayList<String> book_title,
                         ArrayList<String> book_author, ArrayList<String> book_pages, OnBookListener listener) {
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_pages = book_pages;
        this.mListener = listener;
        this.myDB = new MyDatabaseHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.book_id_txt.setText(book_id.get(position));
        holder.book_title_txt.setText(book_title.get(position));
        holder.book_author_txt.setText(book_author.get(position));
        holder.book_pages_txt.setText(book_pages.get(position));

        holder.update_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("BOOK_ID", book_id.get(adapterPosition));
                    intent.putExtra("BOOK_TITLE", book_title.get(adapterPosition));
                    intent.putExtra("BOOK_AUTHOR", book_author.get(adapterPosition));
                    intent.putExtra("BOOK_PAGES", book_pages.get(adapterPosition));
                    context.startActivity(intent);
                }
            }
        });

        holder.delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    confirmDeleteDialog(adapterPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView book_id_txt, book_title_txt, book_author_txt, book_pages_txt;
        ImageView update_icon, delete_icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_txt = itemView.findViewById(R.id.book_id_txt);
            book_title_txt = itemView.findViewById(R.id.book_title_txt);
            book_author_txt = itemView.findViewById(R.id.book_author_txt);
            book_pages_txt = itemView.findViewById(R.id.book_pages_txt);
            update_icon = itemView.findViewById(R.id.update_icon);
            delete_icon = itemView.findViewById(R.id.delete_icon);

            delete_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        confirmDeleteDialog(position);
                    }
                }
            });
        }
    }

    private void confirmDeleteDialog(final int position) {
        new AlertDialog.Builder(context)
                .setTitle("Konfirmasi Hapus")
                .setMessage("Apakah Anda yakin ingin menghapus buku ini?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int deletedRows = myDB.deleteBook(book_id.get(position));
                        if (deletedRows > 0) {
                            Toast.makeText(context, "Data berhasil dihapus.", Toast.LENGTH_SHORT).show();
                            book_id.remove(position);
                            book_title.remove(position);
                            book_author.remove(position);
                            book_pages.remove(position);
                            notifyItemRemoved(position);
                        } else {
                            Toast.makeText(context, "Gagal menghapus data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Tidak", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public interface OnBookListener {
        void onBookClick(int position);
    }
}
