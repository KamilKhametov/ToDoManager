package com.example.todomanager.itemtouchhelper;

import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todomanager.R;
import com.example.todomanager.adapter.AdapterMain;
import com.example.todomanager.view.MainActivity;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class TouchHelper extends androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback {

    private AdapterMain adapterMain;

    public TouchHelper( AdapterMain adapterMain ) {
        super ( 0, androidx.recyclerview.widget.ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT );
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
        }else {
            adapterMain.deleteData ( position );
        }
    }

    @Override
    public void onChildDraw( @NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive ) {
        new RecyclerViewSwipeDecorator.Builder( c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeRightBackgroundColor(Color.RED)
                .addSwipeRightActionIcon(R.drawable.ic_delete)
                .addSwipeLeftBackgroundColor(R.color.btn_cancel)
                .addSwipeLeftActionIcon(R.drawable.ic_edit)
                .create()
                .decorate();
        super.onChildDraw ( c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive );
    }
}
