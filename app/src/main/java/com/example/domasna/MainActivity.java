package com.example.domasna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    ListView search_food;
    ArrayAdapter<String> adapter;
    Boolean isEdit=false;
    int index;

    public List<String> stringList = new ArrayList<String>();
    public List<String> stringList2 = new ArrayList<String>();
    public List<String> joinedStringList = new ArrayList<String>();
    //private HashMap<String,String> dictionary = new HashMap<String, String>();
    TextInputEditText textInputEditText;
    TextInputEditText textInputEditText2;


    public void add(View view) {

        textInputEditText = findViewById(R.id.tagYourQueryInput);
        textInputEditText2 = findViewById(R.id.tagYourQueryInput2);

        if(isEdit==false) {
            stringList.add(textInputEditText.getText().toString().trim());
            stringList2.add(textInputEditText2.getText().toString().trim());
            joinedStringList.add(textInputEditText.getText().toString().trim()+"-"+textInputEditText2.getText().toString().trim());
            System.out.println("addlist:"+stringList);
            System.out.println("addlist2:"+stringList2);
            System.out.println("addlistjoined:"+joinedStringList);
            //dictionary.put(textInputEditText.getText().toString().trim(),textInputEditText2.getText().toString().trim());
        }
        else {
            stringList.set(index,textInputEditText.getText().toString().trim());
            stringList2.set(index,textInputEditText2.getText().toString().trim());
            joinedStringList.set(index,textInputEditText.getText().toString().trim()+"-"+textInputEditText2.getText().toString().trim());
            //dictionary.put(textInputEditText.getText().toString().trim(),textInputEditText2.getText().toString().trim());
            isEdit=false;
            System.out.println("editlist:"+stringList);
            System.out.println("editlist2:"+stringList2);
            System.out.println("editlistjoined:"+joinedStringList);
            Button saveButton = findViewById(R.id.saveButton);
            saveButton.setText("SAVE");
        }

        textInputEditText.setText("");
        textInputEditText2.setText("");

        ListView lv = (ListView) findViewById(R.id.search_food);
        //lv.setAdapter(new MyListAdapter(this, R.layout.list_item, stringList));
        adapter = new MyListAdapter(this, R.layout.list_item, joinedStringList);
        lv.setAdapter(adapter);

        writeToFile();
    }

    public void clear(View view) {

        stringList.clear();
        stringList2.clear();
        joinedStringList.clear();
        //dictionary.clear();

        ListView lv = (ListView) findViewById(R.id.search_food);
        //lv.setAdapter(new MyListAdapter(this, R.layout.list_item, joinedStringList));
        adapter = new MyListAdapter(this, R.layout.list_item, joinedStringList);
        lv.setAdapter(adapter);

        writeToFile();
    }

    public void writeToFile(){
        System.out.println("1");
        File file = new File(MainActivity.this.getFilesDir(), "text");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File gpxfile = new File(file, "Dictionary.txt");
            System.out.println("2");
            FileWriter writer = new FileWriter(gpxfile);
            System.out.println("d1:"+joinedStringList);
            System.out.println("d2:"+stringList);
            System.out.println("d3:"+stringList2);
            for(int i=0;i<joinedStringList.size();i++){
                writer.write(stringList.get(i)+"    "+stringList2.get(i)+System.lineSeparator());
            }
            writer.flush();
            System.out.println("3");
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//    @Override
//    protected void onDestroy() {
//        writeToFile();
//        super.onDestroy();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("1");
        String string = new String();
        File file = new File(MainActivity.this.getFilesDir(), "text");
        if (!file.exists()) {
            file.mkdir();
            System.out.println("2");
        }
        try {
            File gpxfile = new File(file, "Dictionary.txt");
            FileReader reader = new FileReader(gpxfile);
            reader.close();
            System.out.println("3");
            BufferedReader in = new BufferedReader(new FileReader(gpxfile));
            string = in.readLine();
            System.out.println("4");
            int i=0;
            while(string!=null){
                String[] words = string.split("    ");
                stringList.add(words[0]);
                System.out.println("word0:"+words[0]);
                stringList2.add(words[1]);
                System.out.println("word1:"+words[1]);
                joinedStringList.add(words[0]+"-"+words[1]);
                System.out.println("wordjoinedlist:"+joinedStringList);
                //dictionary.put(string,string);
                //dictionary.put;
                string = in.readLine();
                i++;
            }
            in.close();
            //System.out.println(dictionary.entrySet());
        } catch (Exception e) {
            System.out.println(e);
        }

        //for(int i=0;i<stringList.size();i++){
        //    joinedStringList.add(stringList.get(i)+"-"+stringList2.get(i));
        //}

        ListView lv = (ListView) findViewById(R.id.search_food);
        adapter = new MyListAdapter(this, R.layout.list_item, joinedStringList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textInputEditText = findViewById(R.id.tagYourQueryInput);
                textInputEditText2 = findViewById(R.id.tagYourQueryInput2);
                textInputEditText.setText(stringList.get(position));
                textInputEditText2.setText(stringList2.get(position));
                System.out.println("list2:"+stringList2);

                index=position;
                isEdit=true;
                Button saveButton = findViewById(R.id.saveButton);
                saveButton.setText("EDIT");
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

    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;
        private MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewHolder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.title = (Button) convertView.findViewById(R.id.list_item_name_btn);
                viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
                convertView.setTag(viewHolder);
            }
            mainViewHolder = (ViewHolder) convertView.getTag();
            mainViewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textInputEditText = findViewById(R.id.tagYourQueryInput);
                    textInputEditText2 = findViewById(R.id.tagYourQueryInput2);
                    textInputEditText.setText(stringList.get(position));
                    textInputEditText2.setText(stringList2.get(position));
                    System.out.println("list2:"+stringList2);

                    index=position;
                    isEdit=true;
                    Button saveButton = findViewById(R.id.saveButton);
                    saveButton.setText("EDIT");
                }
            });
            mainViewHolder.title.setText(getItem(position));

            TextInputEditText search = (TextInputEditText) findViewById (R.id.enterQueryInput);
            //lv.setTextFilterEnabled (true);

            search.addTextChangedListener (new TextWatcher() {
                @Override
                public void beforeTextChanged (CharSequence charSequence, int i, int i1, int i2) {

                }
                @Override
                public void onTextChanged (CharSequence charsequence, int i, int i1, int i2) {
                    MainActivity.this.adapter.getFilter().filter (charsequence) ;
                }

                @Override
                public void afterTextChanged (Editable editable) {

                }
            });

            return convertView;
        }
    }
    public class ViewHolder {

        ImageView thumbnail;
        TextView title;
        Button button;
    }
}

//    //        FileOutputStream fOut = openFileOutput("Dictionary.txt", MODE_PRIVATE);
////        OutputStreamWriter osw = new OutputStreamWriter(fOut);
//    File file = new File(MainActivity.this.getFilesDir(), "text");
//        if (!file.exists()) {
//                file.mkdir();
//                }
//                try {
//                File gpxfile = new File(file, "sample");
//                FileWriter writer = new FileWriter(gpxfile);
//                //writer.append(enterText.getText().toString());
//                //writer.flush();
//                //writer.close();
//                //Toast.makeText(MainActivity.this, "Saved your text", Toast.LENGTH_LONG).show();
//                } catch (Exception e) { }



//    Scanner scan = new Scanner(getResources ().openRawResource(R.raw.dictionary));
//        //String allText=""; // read entire file String allText =
//        while (scan.hasNextLine()) {
//            String line = scan.nextLine();
//            //allText += line;
//            stringList.add(line);
//        }
//        scan.close();

//        try{
//                File yourFile = new File(this.getFilesDir(),"Dictionary.txt");
//                yourFile.createNewFile(); // if file already exists will do nothing
//                openFileInput("Dictionary.txt");
//                System.out.println(getFilesDir()+"/Dictionary.txt"+"1");
//                }
//                catch (Exception e){
//                System.out.println(e+"2");
//                }