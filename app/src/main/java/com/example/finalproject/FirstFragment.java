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

public class FirstFragment extends Fragment implements View.OnClickListener {

    final int EDIT_NAME=1;
    final int EDIT_TEST=2;
    final int DELETE=3;
    TextView textView;
    String text;
    LinearLayout layout;
    View view;
    FloatingActionButton fab;
    Context context;
    Dialog dialog;
    EditText editText;
    Button button, button1;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        layout=(LinearLayout)inflater.inflate(R.layout.fragment_first, container, false);
        context=inflater.getContext();
        fab=getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(this);
        dialog=new Dialog(context);
        return layout;
    }

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
                layout.addView(textView);
                dialog.dismiss();
                break;
            case R.id.btn:
                dialog.dismiss();
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
                menu.add(0, EDIT_NAME, 0, "edit name");
                menu.add(1, EDIT_TEST, 1, "edit test");
                menu.add(2, DELETE, 2, "delete test");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final Dialog dialog1=new Dialog(context);
        dialog1.setContentView(R.layout.dialog_layout);
        switch (item.getItemId()){
            case EDIT_NAME:
                final EditText editText=dialog1.findViewById(R.id.edText);
                Button button=dialog1.findViewById(R.id.btn);
                Button button1=dialog1.findViewById(R.id.btn1);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        text=editText.getText().toString();
                        textView.setText(text);
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
                dialog1.dismiss();
                break;
            case EDIT_TEST:
                NavHostFragment.findNavController(FirstFragment.this)
                                .navigate(R.id.action_FirstFragment_to_SecondFragment);
                dialog = null;
                break;
            case DELETE:
                layout.removeView(textView);
                break;
        }

        return super.onContextItemSelected(item);
    }
    }