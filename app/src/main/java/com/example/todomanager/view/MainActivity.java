package com.example.todomanager.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.todomanager.R;
import com.example.todomanager.adapter.AdapterMain;
import com.example.todomanager.itemtouchhelper.TouchHelper;
import com.example.todomanager.model.ModelMain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private AdapterMain adapterMain;
    private List<ModelMain> modelMainList;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        // Объявление полей
        fab = findViewById ( R.id.fab );

        // Установка recyclerView
        recyclerView = findViewById ( R.id.recyclerViewMain );
        recyclerView.setHasFixedSize ( true );
        recyclerView.setLayoutManager ( new LinearLayoutManager ( this ) );

        // Установка полученных данных из FirebaseFireStore и установка в RecyclerView
        firestore = FirebaseFirestore.getInstance ();
        modelMainList = new ArrayList<> ();
        adapterMain = new AdapterMain ( MainActivity.this, modelMainList );
        recyclerView.setAdapter ( adapterMain );

        // Привязка свайпа с RecyclerView
        ItemTouchHelper touchHelper = new ItemTouchHelper ( new TouchHelper ( adapterMain ) );
        touchHelper.attachToRecyclerView ( recyclerView );
        showData();


        // Реализация кнопки для перехода на экран с созданием задачи
        fab.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent (MainActivity.this, AddTaskActivity.class);
                startActivity ( intent );
            }
        } );

    }

    // Получение данных из FirebaseFireStore
    private void showData() {
        firestore.collection ( "TaskDocuments" ).get ()
                .addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
                    @Override
                    public void onComplete( @NonNull Task<QuerySnapshot> task ) {
                        modelMainList.clear ();
                        for(DocumentSnapshot snapshot : task.getResult ()){
                            ModelMain modelMain = new ModelMain ( snapshot.getString ( "id" ), snapshot.getString ( "task" ), snapshot.getString ( "desc" ), snapshot.getString ( "date" ) );
                            modelMainList.add ( modelMain );
                        }
                        adapterMain.notifyDataSetChanged ();
                    }
                } ).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure( @NonNull Exception e ) {
                Toast.makeText ( MainActivity.this, "Oops ...", Toast.LENGTH_SHORT ).show ();
            }
        } );
    }
}