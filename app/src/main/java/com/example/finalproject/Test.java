package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Test extends Activity {

    ListView listView;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter adapter;

    private DBManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        listView=findViewById(R.id.listV);
        manager=new DBManager(this);
        manager.openDb();
        ArrayList<String> arr=new ArrayList<>();
        ArrayList<String> arr2=new ArrayList<>();
        Log.d("Name test", manager.getNQ(FirstFragment.text)+"");
        for (String name:manager.getNQ(FirstFragment.text)) {
            if (name!=null){
                arr.add(name);
            }
        }
        int i=0;
        for (String name:arr){

            if (i==0){
                arr2.add(name);
            }else {
                if (!(arr2.contains(name))){
                    arr2.add(name);
                }
            }
            i=1;
            Log.d("arr", arr+"");
            Log.d("arr2", arr2+"");
        }

        for (String name:arr2) {
            if (name != "") {
                if (name!=null){
                    arrayList.add(name);
                    Log.d("ArrayList", arrayList+"");
                }
            }

        }
        adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        manager.closeDb();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView toName;
                String name;
                toName=(TextView)view;
                name = toName.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("name_question", name);
                intent.setClass(Test.this, Questions.class);
                startActivity(intent);
            }
        });
    }
}
