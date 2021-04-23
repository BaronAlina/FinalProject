package com.example.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
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

public class FirstFragment extends Fragment {

    TextView textView;
    String text;
    LinearLayout layout;
    View view;
    FloatingActionButton fab;
    public void Permission(String permission){
//        fragment1.findViewById(R.id.fragment);
        text = permission+"";
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context context= getActivity().getApplicationContext();
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout);
        layout = new LinearLayout(context);
        view= inflater.inflate(R.layout.fragment_first, null, false);
        fab=container.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText=dialog.findViewById(R.id.edText);
                Button button=dialog.findViewById(R.id.btn);
                Button button1=dialog.findViewById(R.id.btn1);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        text=editText.getText().toString();
                        layout.findViewById(R.id.list);
                        layout.addView(view);
                        textView=view.findViewById(R.id.text);
                        textView.setOnCreateContextMenuListener(FirstFragment.this);
                        textView.setText(text);
                        dialog.dismiss();
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
            }
        });

//        LinearLayout layout=find
//        LinearLayout fragment1 = new LinearLayout(MainActivity.this)
        // Inflate the layout for this fragment
        return view;//inflater.inflate(R.layout.fragment_first, container, false);
    }

//    @Override
//    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
//        switch (v.getId()){
//            case R.id.text:
//        }
//        super.onCreateContextMenu(menu, v, menuInfo);
//    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }


}