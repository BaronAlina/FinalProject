package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Test extends Activity implements View.OnClickListener {

    TextView txt;
//    SimpleCursorAdapter adapter;
//    Cursor cursor;
//    mListAdapter adapterL;
//
//    int ADD_ACTIVITY=0;
//    int UPDATE_ACTIVITY=1;
//
//    //для варианта 1
//    public ArrayList<String> all_variantsT;
//    public ArrayList<String> correct_variantsT;
//    public int variantT;
//    public String text_questionT;
//    public String pointT;

    private DBManager manager;
    Button forDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
//        context=this;
//        DBConnector=new DBTests(this);
        txt=findViewById(R.id.txt);
        txt.setText("");
        manager=new DBManager(this);
        forDB=findViewById(R.id.forList);
        forDB.setOnClickListener(this);
//        adapterL=new mListAdapter(context, DBConnector.selectAll());
//        listView.setAdapter(adapterL);
//        variant=getIntent().getIntExtra("variant", 0);
//        t.setText(variant+"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.openDb();
//        for (String name:manager.getNQ(FirstFragment.text)) {
//            if (name!=null){
//                txt.append(name);
//                txt.append("\n");
//            }
//
//        }
    }

    @Override
    public void onClick(View v) {
        txt.setText("");
        for (String name:manager.getNameTest()){
            if (name==null){
                name="null";
            }
            txt.append(name);
            txt.append("\n");
        }
        txt.append("-----------------------to quest");
        for (String name:manager.getNameQuestion()) {
//            if (name!=null){
            if (name==null){
                name="null";
            }
                txt.append(name);
                txt.append("\n");
//            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.closeDb();
    }

    //    class mListAdapter extends BaseAdapter{
//        private LayoutInflater layoutInflater;
//        private ArrayList<Parameters> arrayList;
//
//        public mListAdapter(Context ctx, ArrayList<Parameters> arr){
//            layoutInflater=LayoutInflater.from(ctx);
//
//        }
//
//        @Override
//        public int getCount() {
//            return 0;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            if (convertView==null){
//
//            }
//            return null;
//        }
//    }
}
