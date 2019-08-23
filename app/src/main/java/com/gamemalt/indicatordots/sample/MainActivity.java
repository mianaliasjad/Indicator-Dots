package com.gamemalt.indicatordots.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gamemalt.indicatordots.IndicatorDots;

public class MainActivity extends AppCompatActivity {

    Button add,remove,removeAll;
    IndicatorDots dots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        dots=findViewById(R.id.dots);
        add=findViewById(R.id.btn_add);
        remove=findViewById(R.id.btn_remove);
        removeAll=findViewById(R.id.btn_remove_all);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dots.incrementDot();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dots.decrementDot();

            }
        });


        removeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dots.resetDotsWithAnim();
            }
        });


    }
}
