package com.mira.booklibraryapp.view.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mira.booklibraryapp.R;
import com.mira.booklibraryapp.view.add.AddActivity;
import com.mira.booklibraryapp.view.main.MainActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView bookIdTextView, bookTitleTextView, bookAuthorTextView, bookPagesTextView;
    ImageView back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bookIdTextView = findViewById(R.id.tvDetailId);
        bookTitleTextView = findViewById(R.id.tvTitle);
        bookAuthorTextView = findViewById(R.id.tvAuthor);
        bookPagesTextView = findViewById(R.id.tvPages);
        back_button = findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Get data from Intent
        if (getIntent() != null) {
            String bookId = getIntent().getStringExtra("BOOK_ID");
            String bookTitle = getIntent().getStringExtra("BOOK_TITLE");
            String bookAuthor = getIntent().getStringExtra("BOOK_AUTHOR");
            String bookPages = getIntent().getStringExtra("BOOK_PAGES");

            // Set data to TextViews
            bookIdTextView.setText(bookId);
            bookTitleTextView.setText(bookTitle);
            bookAuthorTextView.setText(bookAuthor);
            bookPagesTextView.setText(bookPages);
        }
    }
}
