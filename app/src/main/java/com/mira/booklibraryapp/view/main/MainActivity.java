package com.mira.booklibraryapp.view.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mira.booklibraryapp.R;
import com.mira.booklibraryapp.view.add.AddActivity;
import com.mira.booklibraryapp.view.adapter.CustomAdapter;
import com.mira.booklibraryapp.database.MyDatabaseHelper;
import com.mira.booklibraryapp.view.info.InfoActivity;
import com.mira.booklibraryapp.view.welcome.WelcomeActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CustomAdapter.OnBookListener {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    RelativeLayout logout_icon, question_icon;

    MyDatabaseHelper myDB;
    ArrayList<String> book_id, book_title, book_author, book_pages;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        logout_icon = findViewById(R.id.logout_icon);
        question_icon = findViewById(R.id.question_icon);

        myDB = new MyDatabaseHelper(MainActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();

        customAdapter = new CustomAdapter(MainActivity.this, book_id, book_title, book_author, book_pages, this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        add_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        question_icon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(intent);
        });

        logout_icon.setOnClickListener(v -> showLogoutDialog());

        if (!isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            reloadData();
        }
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Yakin untuk keluar?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        logoutUser();
                    }
                })
                .setNegativeButton("Tidak", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void logoutUser() {
        SharedPreferences.Editor editor = getSharedPreferences("loginPrefs", MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void reloadData() {
        book_id.clear();
        book_title.clear();
        book_author.clear();
        book_pages.clear();

        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Tidak ada data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
        }
        customAdapter.notifyDataSetChanged();
    }

    private boolean isLoggedIn() {
        SharedPreferences prefs = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        return prefs.getBoolean("isLoggedIn", false);
    }

    @Override
    public void onBookClick(int position) {
        int deletedRows = myDB.deleteBook(book_id.get(position));
        if (deletedRows > 0) {
            Toast.makeText(this, "Data berhasil dihapus.", Toast.LENGTH_SHORT).show();
            reloadData();
        } else {
            Toast.makeText(this, "Gagal menghapus data.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadData();
    }
}
