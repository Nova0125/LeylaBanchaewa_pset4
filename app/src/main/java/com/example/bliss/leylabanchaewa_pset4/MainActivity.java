package com.example.bliss.leylabanchaewa_pset4;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private TodoDatabase db;
    private TodoAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = TodoDatabase.getInstance(getApplicationContext());
        Cursor cursor = db.selectAll();

        listView = findViewById(R.id.listView);
        adapter = new TodoAdapter(this, cursor);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new shortClick());
        listView.setOnItemLongClickListener(new longClick());


        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                addItem();
            }
        });

    }

    private void addItem(){
        EditText editText = findViewById(R.id.editText);
        String todo_item = editText.getText().toString();
        editText.setText("");
        db = TodoDatabase.getInstance(getApplicationContext());
        db.insert(todo_item, 0);
        updateData();
    }

    private void updateData() {
        db = TodoDatabase.getInstance(getApplicationContext());

        Cursor newCursor = db.selectAll();
        adapter.swapCursor(newCursor);
        listView.setAdapter(adapter);
    }

    private class shortClick implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CheckBox cb_completed = view.findViewById(R.id.checkBox);
            Cursor cursor = db.selectAll();
            cursor.move(position + 1);

            int long_id = cursor.getInt(cursor.getColumnIndex("_id"));
            if (cb_completed.isChecked()) {
                db.update(long_id, 0);
            }
            else {
                db.update(long_id , 1);
            }

            updateData();
        }
    }

    private class longClick implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor cursor = db.selectAll();
            cursor.move(position + 1);

            int long_id = cursor.getInt(cursor.getColumnIndex("_id"));
            db.delete(long_id);
            updateData();
            return false;
        }
    }


}
