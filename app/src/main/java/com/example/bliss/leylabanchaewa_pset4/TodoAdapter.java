package com.example.bliss.leylabanchaewa_pset4;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by Bliss on 11/20/17.
 */

public class TodoAdapter extends ResourceCursorAdapter {

    public TodoAdapter (Context context, Cursor cursor) {
        super(context, R.layout.row_todo, cursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.row_todo, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        // Find the textview and checkbox
        TextView todo = view.findViewById(R.id.textView);
        CheckBox box = view.findViewById(R.id.checkBox);


        // Set the textview and checkbox
        todo.setText(cursor.getString(cursor.getColumnIndex("title")));
        Integer bool = cursor.getInt(cursor.getColumnIndex("completed"));

        // Check if the box has to be checked or not
        if (bool == 1){
            box.setChecked(true);
        }
        else{
            box.setChecked(false);
        }

    }
}
