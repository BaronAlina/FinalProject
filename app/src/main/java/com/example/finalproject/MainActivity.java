package com.example.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    String permission,
            editName="edit name",
            editTest="edit test";
    final int EDIT_NAME=1;
    final int EDIT_TEST=2;
    final int DELETE=3;
    LinearLayout layout;
    View view1;
    final FirstFragment fragment1=new FirstFragment();
    SecondFragment fragment2=new SecondFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        LayoutInflater itInflater=getLayoutInflater();
//        View view = itInflater.inflate(R.layout.text, null, false);
//        ViewGroup.LayoutParams lp = view.getLayoutParams();
//        LinearLayout linLayout = (LinearLayout) findViewById(R.id.fragment);
//        linLayout.addView(view);


//        final Dialog dialog=new Dialog(MainActivity.this);
//        dialog.setContentView(R.layout.dialog_layout);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                permission = "YES";
////                fragment1.Permission(permission);
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//                final EditText editText=dialog.findViewById(R.id.edText);
//                Button button=dialog.findViewById(R.id.btn);
//                Button button1=dialog.findViewById(R.id.btn1);
//                button1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        permission=editText.getText().toString();
//                        LayoutInflater inflater=getLayoutInflater();
//                        layout = findViewById(R.id.list);
//                        view1 = inflater.inflate(R.layout.fragment_first, null, false);
//                        layout.addView(view1);
//                        textView = view1.findViewById(R.id.text);
//                        textView.setOnCreateContextMenuListener(MainActivity.this);
//                        textView.setText(permission);
//                        fragment1.Permission(permission);
//                        dialog.dismiss();
//                    }
//                });
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.setCancelable(false);
//                dialog.show();
//            }
//        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()){
            case R.id.text:
                menu.add(0, EDIT_NAME, 0, "edit name");
                menu.add(1, EDIT_TEST, 1, "edit test");
                menu.add(2, DELETE, 2, "delete test");
                break;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final Dialog dialog1=new Dialog(MainActivity.this);
        dialog1.setContentView(R.layout.dialog_layout);

        switch (item.getItemId()){
            case EDIT_NAME:
                final EditText editText=dialog1.findViewById(R.id.edText);
                Button button=dialog1.findViewById(R.id.btn);
                Button button1=dialog1.findViewById(R.id.btn1);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        permission=editText.getText().toString();
                        textView.setText(permission);
                        dialog1.dismiss();
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });
                dialog1.setCancelable(false);
                dialog1.show();
                break;
            case EDIT_TEST:
                permission="yes";
//                intent.putExtra("yes", permission);
//                intent.setClass(MainActivity.this, SecondFragment.class);
//                startActivity(intent);
                break;
            case DELETE:
                layout.removeView(view1);
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}