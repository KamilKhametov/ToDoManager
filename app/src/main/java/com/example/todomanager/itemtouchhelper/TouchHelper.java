package com.example.todomanager.itemtouchhelper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todomanager.adapter.AdapterMain;

public class TouchHelper extends androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback {

    private AdapterMain adapterMain;

    public TouchHelper( AdapterMain adapterMain ) {
        super ( 0, androidx.recyclerview.widget.ItemTouchHelper.LEFT );
        this.adapterMain = adapterMain;
    }

    @Override
    public boolean onMove( @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target ) {
        return false;
    }

    @Override
    public void onSwiped( @NonNull RecyclerView.ViewHolder viewHolder, int direction ) {
        final int position = viewHolder.getAdapterPosition ();
        // Если произошел свайп влево, то переходи на редактирование Задачи
        if(direction == androidx.recyclerview.widget.ItemTouchHelper.LEFT){
            adapterMain.updateData ( position );
            adapterMain.notifyDataSetChanged ();
        }
    }
}
