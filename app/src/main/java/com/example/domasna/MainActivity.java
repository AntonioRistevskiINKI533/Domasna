package com.example.domasna;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView search_food;
    ArrayAdapter<String> adapter;
    Boolean isEdit=false;
    int index;

    public List<String> stringList = new ArrayList<String>();
    TextInputEditText textInputEditText;

    public void add(View view) {

        textInputEditText = findViewById(R.id.tagYourQueryInput);

        if(isEdit==false) {

            stringList.add(textInputEditText.getText().toString().trim());
        }
        else {
            stringList.set(index,textInputEditText.getText().toString().trim());
            isEdit=false;
        }


        search_food = (ListView) findViewById(R.id.search_food);

        ArrayList<String> arrayFood = new ArrayList<>();
        arrayFood.addAll(stringList);

        adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R. layout.simple_list_item_1,
                arrayFood
        );

        search_food.setAdapter(adapter);
    }

    public void clear(View view) {

        stringList.clear();

        search_food = (ListView) findViewById(R.id.search_food);

        ArrayList<String> arrayFood = new ArrayList<>();
        arrayFood.addAll(stringList);

        adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R. layout.simple_list_item_1,
                arrayFood
        );

        search_food.setAdapter(adapter);
    }

//    Button saveButton = (Button) findViewById(R.id.saveButton) ;
//    saveButton.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            String phno="telephone";
//
//            Intent i=new Intent(Intent.ACTION_DIAL,Uri.parse(phno));
//            startActivity(i);
//        }
//    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stringList.add("Toyota");
        stringList.add("Mercedes");


        search_food = (ListView) findViewById(R.id.search_food);

        ArrayList<String> arrayFood = new ArrayList<>();
        arrayFood.addAll(stringList);

        adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R. layout.simple_list_item_1,
                arrayFood
        );

        search_food.setAdapter(adapter);

        search_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(stringList.get(position));
                textInputEditText = findViewById(R.id.tagYourQueryInput);
                textInputEditText.setText(stringList.get(position));

                index=position;
                isEdit=true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search_food);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }
}