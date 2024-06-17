package com.example.newsandroidproject.adapter;

import android.content.DialogInterface;
import android.view.View;

import com.example.newsandroidproject.activity.PostArticleActivity;
import com.example.newsandroidproject.adapter.BodyItemAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final BodyItemAdapter adapter;
    private final PostArticleActivity context;
    private final RecyclerView rv;

    public ItemTouchHelperCallback(PostArticleActivity context, BodyItemAdapter adapter, RecyclerView rv) {
        this.adapter = adapter;
        this.context = context;
        this.rv = rv;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();

        // Kiểm tra nếu target là phần tử đầu tiên, không cho phép di chuyển
        if (toPosition == 0) {
            return false;
        }

        adapter.moveItem(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getBindingAdapterPosition();

        if (direction == ItemTouchHelper.END) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xóa mục")
                    .setMessage("Bạn có muốn xóa mục này không?")
                    .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Xóa item khỏi adapter
                            adapter.removedItem(position);
                        }
                    })
                    .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Khôi phục lại item
                            adapter.notifyItemChanged(position);
                        }
                    })
                    .show();
        }
    }
}
