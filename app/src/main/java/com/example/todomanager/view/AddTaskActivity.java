package com.example.todomanager.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todomanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class AddTaskActivity extends AppCompatActivity {

    private EditText edTask, edDesc, edDate;
    private Button btnCreateTask, btnCancel;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_task );

        // Нахождение полей
        edTask = findViewById ( R.id.edTask );
        edDesc = findViewById ( R.id.edDesc );
        edDate = findViewById ( R.id.edDate );
        btnCreateTask = findViewById ( R.id.btnCreateTask );
        btnCancel = findViewById ( R.id.btnCancel );

        // Получение от Firebase
        firestore = FirebaseFirestore.getInstance ();

        // Создание клика на кнопку создания задачи
        btnCreateTask.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                // Получение текста с EditText
                String task = edTask.getText ().toString ().trim ();
                String desc = edDesc.getText ().toString ().trim ();
                String date = edDate.getText ().toString ().trim ();
                String id = UUID.randomUUID ().toString ();

                saveToFireStore(id,task,desc,date);

            }
        } );

    }

    // Метод сохранения данных в Firebase
    private void saveToFireStore( String id, String task, String desc, String date ) {
        // Если все поля заполнены, то
        if(!task.isEmpty () && !desc.isEmpty () && !date.isEmpty ()){
            HashMap<String, Object> map = new HashMap<> ();
            // Клади данные в HashMap
            map.put ( "id", id );
            map.put ( "task", task );
            map.put ( "desc", desc );
            map.put ( "date", date );

            // Отправляй данные в Firebase
            firestore.collection ( "TaskDocuments" ).document (id).set ( map )
                    .addOnCompleteListener ( new OnCompleteListener<Void> () {
                        // Методы обработки запроса: успешно или неуспешно
                        @Override
                        public void onComplete( @NonNull Task<Void> task ) {
                            if(task.isSuccessful ()){
                                Toast.makeText ( AddTaskActivity.this, "Данные сохранились", Toast.LENGTH_SHORT ).show ();
                                // Возвращение на MainActivity
                                Intent intent = new Intent (AddTaskActivity.this, MainActivity.class);
                                startActivity ( intent );
                            }
                        }
                    } ).addOnFailureListener ( new OnFailureListener () {
                @Override
                public void onFailure( @NonNull Exception e ) {
                    Toast.makeText ( AddTaskActivity.this, "Ошибка при сохрании данных", Toast.LENGTH_SHORT ).show ();
                }
            } );
        }else {
            Toast.makeText ( this, "Заполните все поля)", Toast.LENGTH_SHORT ).show ();
        }
    }
}