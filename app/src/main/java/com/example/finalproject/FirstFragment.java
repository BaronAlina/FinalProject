package com.example.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FirstFragment extends Fragment implements View.OnClickListener {

    final int EDIT_NAME=1;
    final int EDIT_TEST=2;
    final int DELETE=3;
    TextView textView;
    public static String text = "";
    LinearLayout layout;
    View view;
    FloatingActionButton fab;
    Context context;
    Dialog dialog;
    EditText editText;
    Button button, button1, forTest;
    ArrayList<TextView> arrayList;
    DBManager manager;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        layout=(LinearLayout)inflater.inflate(R.layout.fragment_first, container, false);
        context=inflater.getContext();
        fab=getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(this);
        dialog=new Dialog(context);
        arrayList=new ArrayList<>();
        forTest=layout.findViewById(R.id.button);
        forTest.setOnClickListener(this);
        manager=new DBManager(context);
        manager.openDb();
        ArrayList<String> arr=new ArrayList<>();
        for (String name:manager.getNameTest()) {
            if (name!=null){
                arr.add(name);
            }
        }
        int i=0;
        for (String name:arr){
            ArrayList<String> arr2=new ArrayList<>();
            if (i==0){
                arr2.add(name);
            }else {
                if (!(arr2.contains(name))){
                    arr2.add(name);
                }
            }
            i=1;
        }
        for (String name:manager.getNameTest()) {
            if (name!=null) {


                    textView = new TextView(context);
                    textView.setText(name);
                    textView.setTextSize(25);
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    textView.setOnCreateContextMenuListener(FirstFragment.this);
                    layout.addView(textView);
            }
        }
        manager.closeDb();
        return layout;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        manager.openDb();
//        textView=new TextView(context);
//        for (String name:manager.getFromDb()) {
//            textView.append(name);
//            textView.append("\n");
//            textView.setTextSize(25);
//            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//            layout.addView(textView);
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
//                dialog=new Dialog(context);
                if (dialog==null){
                    break;
                }
                dialog.setContentView(R.layout.dialog_layout);
                editText=dialog.findViewById(R.id.edText);
                button=dialog.findViewById(R.id.btn);
                button1=dialog.findViewById(R.id.btn1);
                button1.setOnClickListener(this);
                button.setOnClickListener(this);
                dialog.setCancelable(false);
                dialog.show();
                break;
            case R.id.btn1:
                text=editText.getText().toString();
                textView=new TextView(context);
                textView.setText(text);
                textView.setTextSize(25);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setOnCreateContextMenuListener(FirstFragment.this);
                arrayList.add(textView);
                layout.addView(textView);
//                manager.openDb();
//                manager.insertNameTest(text);
//                manager.closeDb();
                dialog.dismiss();
                break;
            case R.id.btn:
                dialog.dismiss();
                break;
            case R.id.button:
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_Test);
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()){
            case R.id.text2:
                menu.add(0, EDIT_NAME, 0, "edit name");
                menu.add(1, EDIT_TEST, 1, "edit test");
                menu.add(2, DELETE, 2, "delete test");
            default:
                textView=(TextView)v;
                menu.add(0, EDIT_NAME, 0, "edit name");
                menu.add(1, EDIT_TEST, 1, "edit test");
                menu.add(2, DELETE, 2, "delete test");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        text=textView.getText().toString();
        final String name_test1=text;
        final Dialog dialog1=new Dialog(context);
        dialog1.setContentView(R.layout.dialog_layout);
        switch (item.getItemId()){
            case EDIT_NAME:
                Button button=dialog1.findViewById(R.id.btn);
                final EditText editText=dialog1.findViewById(R.id.edText);
                Button button1=dialog1.findViewById(R.id.btn1);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        text=editText.getText().toString();
                        textView.setText(text);
                        manager.openDb();
                        manager.updateTest(name_test1, text);
                        manager.closeDb();
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
                String if_ed=textView.getText().toString();
                text=if_ed;
                NavHostFragment.findNavController(FirstFragment.this)
                                .navigate(R.id.action_FirstFragment_to_SecondFragment);
                dialog = null;
                break;
            case DELETE:
                String if_del=textView.getText().toString();
                text=if_del;
                layout.removeView(textView);
                String del=textView.getText().toString();
                manager.openDb();
                manager.delete(del);
                manager.closeDb();
                break;
        }

        return super.onContextItemSelected(item);
    }
}