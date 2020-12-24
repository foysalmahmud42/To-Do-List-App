package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextItem, editTextDate;
    Button btnAdd, btnDelete;
    ListView listView;

    List<Item> allItems;
    ArrayList<String> itemsName;
    DataBaseHandler dataBaseHandler;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextItem = (EditText) findViewById(R.id.editTextItem);
        editTextDate = (EditText) findViewById(R.id.editTextDate);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        listView = (ListView) findViewById(R.id.listView);

        btnAdd.setOnClickListener(MainActivity.this);
        btnDelete.setOnClickListener(MainActivity.this);

        dataBaseHandler = new DataBaseHandler(MainActivity.this);
        allItems = dataBaseHandler.getAllItem();
        itemsName = new ArrayList<>();

        if(allItems.size() > 0){

            for (int i = 0; i < allItems.size(); i++){

                Item item = allItems.get(i);
                itemsName.add(item.getItem() + " - " + item.getDate() );

            }

        }

        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, itemsName);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnAdd:

                if (editTextItem.getText().toString().matches("") || editTextDate.getText().toString().matches("")){

                    return;

                }

                Item item = new Item(editTextItem.getText().toString(), editTextDate.getText().toString());


                allItems.add(item);
                dataBaseHandler.addItem(item);
                itemsName.add(item.getItem()+ " - " + item.getDate());
                editTextItem.setText("");
                editTextDate.setText("");

                break;

            case R.id.btnDelete:

                if (allItems.size() > 0) {

                    itemsName.remove(0);
                    dataBaseHandler.deleteItem(allItems.get(0));
                    allItems.remove(0);

                } else {

                    return;

                }

                break;

        }

        adapter.notifyDataSetChanged();

    }
}