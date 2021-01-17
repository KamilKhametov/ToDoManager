package com.example.todomanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todomanager.R;
import com.example.todomanager.model.ModelMain;

import java.util.List;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {

    Context context;
    List<ModelMain> modelMainList;

    public AdapterMain( Context context, List<ModelMain> modelMainList ) {
        this.context=context;
        this.modelMainList=modelMainList;
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
