package com.mira.booklibraryapp.view.info;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mira.booklibraryapp.R;

public class InfoActivity extends AppCompatActivity {
    ImageView back_button;
    TextView question1, question2, question3, question4, question5;
    LinearLayout answer1, answer2, answer3, answer4, answer5;
    ImageView expand_icon1, expand_icon2, expand_icon3, expand_icon4, expand_icon5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        back_button = findViewById(R.id.back_button);
        question1 = findViewById(R.id.question1);
        question2 = findViewById(R.id.question2);
        question3 = findViewById(R.id.question3);
        question4 = findViewById(R.id.question4);
        question5 = findViewById(R.id.question5);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        answer5 = findViewById(R.id.answer5);
        expand_icon1 = findViewById(R.id.expand_icon1);
        expand_icon2 = findViewById(R.id.expand_icon2);
        expand_icon3 = findViewById(R.id.expand_icon3);
        expand_icon4 = findViewById(R.id.expand_icon4);
        expand_icon5 = findViewById(R.id.expand_icon5);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setDropdownListeners();
    }

    private void setDropdownListeners() {
        question1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(answer1, expand_icon1);
            }
        });

        question2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(answer2, expand_icon2);
            }
        });

        question3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(answer3, expand_icon3);
            }
        });

        question4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(answer4, expand_icon4);
            }
        });

        question5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(answer5, expand_icon5);
            }
        });
    }

    private void toggleVisibility(LinearLayout answerLayout, ImageView expandIcon) {
        if (answerLayout.getVisibility() == View.GONE) {
            answerLayout.setVisibility(View.VISIBLE);
            expandIcon.setImageResource(R.drawable.ic_expand_less);
        } else {
            answerLayout.setVisibility(View.GONE);
            expandIcon.setImageResource(R.drawable.ic_expand_more);
        }
    }
}
