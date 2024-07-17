package com.mira.booklibraryapp.view.update;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mira.booklibraryapp.R;
import com.mira.booklibraryapp.database.MyDatabaseHelper;
import com.mira.booklibraryapp.view.add.AddActivity;
import com.mira.booklibraryapp.view.main.MainActivity;

public class UpdateActivity extends AppCompatActivity {
    EditText editTitle, editAuthor, editPages;
    Button btnUpdate, btnDelete;
    MyDatabaseHelper myDB;
    String bookId;
    ImageView back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        editTitle = findViewById(R.id.edit_title);
        editAuthor = findViewById(R.id.edit_author);
        editPages = findViewById(R.id.edit_pages);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        back_button = findViewById(R.id.back_button);

        myDB = new MyDatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bookId = extras.getString("BOOK_ID");
            String title = extras.getString("BOOK_TITLE");
            String author = extras.getString("BOOK_AUTHOR");
            String pages = extras.getString("BOOK_PAGES");

            editTitle.setText(title);
            editAuthor.setText(author);
            editPages.setText(pages);
        }

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTitle = editTitle.getText().toString().trim();
                String updatedAuthor = editAuthor.getText().toString().trim();
                String updatedPages = editPages.getText().toString().trim();

                if (updatedTitle.isEmpty() || updatedAuthor.isEmpty() || updatedPages.isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isUpdated = myDB.updateBook(bookId, updatedTitle, updatedAuthor, updatedPages);

                if (isUpdated) {
                    Toast.makeText(UpdateActivity.this, "Book updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateActivity.this, "Failed to update book", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int deletedRows = myDB.deleteBook(bookId);
                if (deletedRows > 0) {
                    Toast.makeText(UpdateActivity.this, "Book deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateActivity.this, "Failed to delete book", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
