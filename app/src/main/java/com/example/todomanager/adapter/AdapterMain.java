package com.example.todomanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todomanager.R;
import com.example.todomanager.model.ModelMain;
import com.example.todomanager.view.AddTaskActivity;
import com.example.todomanager.view.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {

    private MainActivity context;
    private List<ModelMain> modelMainList;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance ();

    public AdapterMain( MainActivity context, List<ModelMain> modelMainList ) {
        this.context=context;
        this.modelMainList=modelMainList;
    }

    // Метод обновления данных
    public void updateData(int position){
        ModelMain modelTask = modelMainList.get ( position );
        Intent intent = new Intent ( context, AddTaskActivity.class );
        intent.putExtra ( "taskId", modelTask.getId () );
        intent.putExtra ( "taskTitle", modelTask.getTask () );
        intent.putExtra ( "taskDesc", modelTask.getDesc () );
        intent.putExtra ( "taskDate", modelTask.getDate () );
        context.startActivity ( intent );
    }

    // Метод удаления данных с FirebaseFireStore
    public void deleteData(int position){
        ModelMain modelMain = modelMainList.get ( position );
        firestore.collection ( "TaskDocuments" ).document (modelMain.getId ()).delete ()
                .addOnCompleteListener ( new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete( @NonNull Task<Void> task ) {
                        if(task.isSuccessful ()){
                            notifyRemoved ( position );
                            Toast.makeText ( context, "Задача удалена", Toast.LENGTH_SHORT ).show ();
                        }else {
                            Toast.makeText ( context, "Error " + task.getException ().getMessage (), Toast.LENGTH_SHORT ).show ();
                        }
                    }
                } );
    }

    // Метод удаление значений с modelMainList
    private void notifyRemoved(int position){
        modelMainList.remove ( position );
        notifyItemRemoved ( position );
        context.showData ();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View view =LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.item_layout_main, parent, false );
        ViewHolder vh = new ViewHolder ( view );
        return vh;
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder, int position ) {
        ModelMain modelMain = modelMainList.get ( position );
        holder.textTask.setText ( modelMain.getTask () );
        holder.textDesc.setText ( modelMain.getDesc () );
        holder.textDate.setText ( modelMain.getDate () );
    }

    @Override
    public int getItemCount() {
        return modelMainList.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textTask;
        public TextView textDesc;
        public TextView textDate;

        public ViewHolder( @NonNull View itemView ) {
            super ( itemView );

            textTask = itemView.findViewById ( R.id.textTask );
            textDesc = itemView.findViewById ( R.id.textDesc );
            textDate = itemView.findViewById ( R.id.textDate );
        }
    }
}
