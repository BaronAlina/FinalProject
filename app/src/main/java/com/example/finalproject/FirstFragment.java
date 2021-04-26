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

public class FirstFragment extends Fragment  {

    final int EDIT_NAME=1;
    final int EDIT_TEST=2;
    final int DELETE=3;
    TextView textView;
    String text;
    LinearLayout layout;
    View view;
    FloatingActionButton fab;
    Context context;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        context= getActivity().getApplicationContext();
            layout = container.findViewById(R.id.list);
            view= inflater.inflate(R.layout.fragment_first, null, false);
//            layout.findViewById(R.id.list);
            textView=view.findViewById(R.id.text2);
            textView.setOnCreateContextMenuListener(FirstFragment.this);
//        final Dialog dialog=new Dialog(context);
//        dialog.setContentView(R.layout.dialog_layout);
//        fab=new FloatingActionButton(MainActivity.class);
//        fab=container.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final EditText editText=dialog.findViewById(R.id.edText);
//                Button button=dialog.findViewById(R.id.btn);
//                Button button1=dialog.findViewById(R.id.btn1);
//                button1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        text=editText.getText().toString();


//


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

//        LinearLayout layout=find
//        LinearLayout fragment1 = new LinearLayout(MainActivity.this)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void Permission(String permission){
//        fragment1.findViewById(R.id.fragment);
        text = permission+"";
        layout.addView(view);
        textView.setText(text);
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        switch (v.getId()){
//            case R.id.text2:
//                menu.add(0, EDIT_NAME, 0, "edit name");
////                menu.add(1, EDIT_TEST, 1, "edit test");
//                menu.add(2, DELETE, 2, "delete test");
//                break;
//        }
//        super.onCreateContextMenu(menu, v, menuInfo);
//    }
//    @Override
//    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
//        switch (v.getId()){
//            case R.id.text:
//        }
//        super.onCreateContextMenu(menu, v, menuInfo);
//    }
//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        final Dialog dialog1=new Dialog(context);
//        dialog1.setContentView(R.layout.dialog_layout);
//
//        switch (item.getItemId()){
//            case EDIT_NAME:
//                final EditText editText=dialog1.findViewById(R.id.edText);
//                Button button=dialog1.findViewById(R.id.btn);
//                Button button1=dialog1.findViewById(R.id.btn1);
//                button1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        text=editText.getText().toString();
//                        textView.setText(text);
//                        dialog1.dismiss();
//                    }
//                });
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog1.dismiss();
//                    }
//                });
//                dialog1.setCancelable(false);
//                dialog1.show();
//                break;
////            case EDIT_TEST:
////                permission="yes";
////                fragment1.Permission(permission);
////                break;
//            case DELETE:
//                layout.removeView(view);
//                break;
//        }
//
//        return super.onContextItemSelected(item);
//    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.text2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
//        textView.findViewById(R.id.text);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.fab:
//                final EditText editText=dialog.findViewById(R.id.edText);
//                Button button=dialog.findViewById(R.id.btn);
//                Button button1=dialog.findViewById(R.id.btn1);
//                button1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        text=editText.getText().toString();
//                        layout.findViewById(R.id.list);
//                        layout.addView(view);
//                        textView=view.findViewById(R.id.text);
//                        textView.setOnCreateContextMenuListener(FirstFragment.this);
//                        textView.setText(text);
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
//        }
//
//    }
}