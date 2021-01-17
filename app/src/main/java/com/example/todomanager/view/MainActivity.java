package com.example.todomanager.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.todomanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        // Объявление полей
        fab = findViewById ( R.id.fab );

        // Установка recyclerView
        recyclerView = findViewById ( R.id.recyclerViewMain );










        // Реализация кнопки для перехода на экран с созданием задачи
        fab.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent (MainActivity.this, AddTaskActivity.class);
                startActivity ( intent );
            }
        } );


    }
}