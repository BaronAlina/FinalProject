package com.example.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SecondFragment extends Fragment implements View.OnClickListener {

    final int EDIT_NAME_QUESTION=1;
    final int EDIT_QUESTION=2;
    final int DELETE=3;
    int number_of_questions=0;
    String number_after_verification;
    TextView textView;
    LinearLayout layout;
    FloatingActionButton fab;
    Context context;
    Dialog dialog;
    EditText editText;
    Button button, button1;
    public static String text;
    private DBManager manager;

    String i="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout=(LinearLayout)inflater.inflate(R.layout.fragment_second, container, false);
        context=inflater.getContext();
        fab=getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(this);

        manager=new DBManager(context);
        manager.openDb();
        for (String name:manager.getNQ(/*FirstFragment.text*/ FirstFragment.text)) {
            if (name != "") {
                Log.d("NOW", number_of_questions + ". " + name);
                if (name!=null){
                    Log.d("NOW", number_of_questions + ". " + name);
                    textView=new TextView(context);
                    number_of_questions++;
                    textView.setText(number_of_questions + ". " + name);
//            textView.append("\n");
                    textView.setTextSize(25);
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                    textView.setOnCreateContextMenuListener(SecondFragment.this);
                    layout.addView(textView);
                    Log.d("NOW", number_of_questions + ". " + name);
                }
                Log.d("NOW", number_of_questions + ". " + name);
            }

        }
        manager.closeDb();
        return layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                dialog=new Dialog(context);
//                dialog=new Dialog(context);
//                if (dialog==null){
//                    break;
//                }
                dialog.setContentView(R.layout.dialog_layout2);
                editText=dialog.findViewById(R.id.edText2);
                button=dialog.findViewById(R.id.btn_2);
                button1=dialog.findViewById(R.id.btn1_2);
                button1.setOnClickListener(this);
                button.setOnClickListener(this);
                dialog.setCancelable(false);
                dialog.show();
                break;
            case R.id.btn1_2:
                text=editText.getText().toString();
                textView=new TextView(context);
                number_of_questions++;
                textView.setText(number_of_questions + ". " + text);
                textView.setTextSize(25);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                textView.setOnCreateContextMenuListener(SecondFragment.this);
                layout.addView(textView);
                i+="";
                if (text!=null){
                    manager.openDb();
                    manager.insertSettings(FirstFragment.text+i, text);
//                    manager.insertNameTest(FirstFragment.text);
//                    manager.insertNameQuestion(text);
//                manager.insertNameQuestion(FirstFragment.text, text);
                    manager.closeDb();
                }
                dialog.dismiss();
                editText.setText("");
                break;
            case R.id.btn_2:
                dialog.dismiss();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()){
            case R.id.text:
                menu.add(0, EDIT_NAME_QUESTION, 0, "edit name question");
                menu.add(1, EDIT_QUESTION, 1, "edit question");
                menu.add(2, DELETE, 2, "delete question");
            default:
                textView=(TextView)v;
                menu.add(0, EDIT_NAME_QUESTION, 0, "edit name question");
                menu.add(1, EDIT_QUESTION, 1, "edit question");
                menu.add(2, DELETE, 2, "delete test");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        text=textView.getText().toString();
        String[] test=text.split("\\. ");
        final String name_question1=test[1];
        final String check=(String)textView.getText();
        String[] check2=check.split("");
        char[] check3=check2[2].toCharArray();
        if (check3[0]==46){
            number_after_verification=check2[1];
            number_of_questions=Integer.parseInt(number_after_verification);
        }
        switch (item.getItemId()){
            case EDIT_NAME_QUESTION:
                dialog=new Dialog(context);
                dialog.setContentView(R.layout.dialog_layout2);
                editText=dialog.findViewById(R.id.edText2);
                button=dialog.findViewById(R.id.btn_2);
                button1=dialog.findViewById(R.id.btn1_2);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        text=editText.getText().toString();
                        textView.setText(number_of_questions+". " + text);
                        manager.openDb();
                        Log.d("NAME", name_question1+"");
                        manager.updateQuestion(name_question1, text);
                        Log.d("NAME", text+"");
                        manager.closeDb();
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
                editText.setText("");
                break;
            case EDIT_QUESTION:
                String if_ed=textView.getText().toString();
                text=if_ed;
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_TestCreate);
//                dialog = null;
//                Intent intent=new Intent();
//                intent.putExtra("question", number_after_verification);
//                intent.setClass(context, TestCreate.class);
//                startActivity(intent);
                break;
            case DELETE:
                layout.removeView(textView);
                String del=textView.getText().toString();
                String [] chc=del.split("\\. ");
                manager.openDb();
                manager.deleteQ(chc[1]);
                manager.closeDb();
                number_of_questions--;
                break;
        }

        return super.onContextItemSelected(item);
    }

//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(SecondFragment.this)
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
//            }
//        });
//    }
}