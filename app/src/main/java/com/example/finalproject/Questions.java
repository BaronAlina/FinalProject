package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Questions extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    String name;
    DBManager manager;
    Button button;
    ImageView imageView;
    TextView textView, textCreate;
    EditText editText;
    LinearLayout layout, layout2, layoutBasic, linLayout;
    Uri imageUri;
    Bitmap selectedImage;
    int index=1, variant, check=0;
    ArrayList<String> nameQ=new ArrayList<>();
    ArrayList<String> arr=new ArrayList<>();
    ArrayList<String> arr2=new ArrayList<>();
    ArrayList<String> answers=new ArrayList<>();
    String all_var, answer;
    View view;
    LayoutInflater ltInflater;
    CheckBox checkBox;
    String first;

    String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_layout);
        name=getIntent().getStringExtra("name_question");
        button=findViewById(R.id.save_and_next);
        button.setOnClickListener(this);
        imageView=findViewById(R.id.imageView);
        textView=findViewById(R.id.textView);
        textView.setBackgroundResource(R.drawable.ugl);
        editText=findViewById(R.id.edit);
        editText.setBackgroundResource(R.drawable.ugl);
        layout=findViewById(R.id.layout);
        layout2=findViewById(R.id.layout2);
        layoutBasic=findViewById(R.id.layoutBasic);
        manager=new DBManager(this);
        manager.openDb();

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
                    nameQ.add(name);
                }
            }

        }
        manager.closeDb();

        first=nameQ.get(0);
        Log.d("FIRST", first+"");
//        arr2.remove(0);
        manager.openDb();
        uri=manager.getUri(first);
        Log.d("URI", uri+"");
        variant=manager.getVar(first);
        Log.d("VARIANT", variant+"");
        for (String ed_text:manager.getTxt(first)){
            Log.d("Ed_text", ed_text+"");
            textView.setTextSize(20);
            textView.setText(ed_text);
        }

        if (uri!=null){
            imageUri= Uri.parse(uri);
            try {
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        if (variant==1){
            layoutBasic.removeView(layout2);
            all_var=manager.getAll_Var(first);
            Log.d("ALL_VARIANTS", all_var+"");
            if (all_var!=null){
                String[] variants=all_var.split("\\| ");
                for (String var:variants){
                    ltInflater = getLayoutInflater();
                    view = ltInflater.inflate(R.layout.edit, null, false);
                    checkBox=view.findViewById(R.id.checkbox2);
                    checkBox.setOnCheckedChangeListener(this);
                    textCreate=view.findViewById(R.id.textCreate);
                    textCreate.setHint("");
                    textCreate.setText(var);
                    linLayout=findViewById(R.id.layout);
                    linLayout.addView(view);
                }
            }
        }if (variant==2){
            layoutBasic.removeView(layout);
        }
        manager.closeDb();
    }

    @Override
    public void onClick(View v) {
        Log.d("index_and_nameQ.size", index+" "+nameQ.size());
        imageView.setImageResource(0);
        manager.openDb();
        if (index==nameQ.size()){
            if (index-1>=0){
                first=nameQ.get(index-1);
                Log.d("first", first+" "+(index-1));
                if ((manager.getVar(first))==2){
                    Log.d("first variant", manager.getVar(first)+"");
                    answer=editText.getText().toString();
                    Log.d("first answer", answer+"");
                    answers.add(first+"|"+answer);
                }
            }
            Intent intent=new Intent();
            intent.putExtra("Array answers", answers);
            Log.d("putExtra", answers+"");
            intent.setClass(Questions.this, Result.class);
            startActivity(intent);
            index=0;
        }else {
            check=0;
            if (index-1>=0){
                first=nameQ.get(index-1);
                Log.d("first", first+" "+(index-1));
                if ((manager.getVar(first))==2){
                    Log.d("first variant", manager.getVar(first)+"");
                    answer=editText.getText().toString();
                    Log.d("first answer", answer+"");
                    if (!(answer.equals(""))){
                        answers.add(first+"|"+answer);
                    }

                }
            }
            editText.setText("");
            editText.setHint("Write your answer");
            answer="";
            name=nameQ.get(index-1);
            Log.d("VAR", index+"");
            if ((variant=manager.getVar(name))==1){
                Log.d("maheg", (variant=manager.getVar(name))+"");
                Log.d("Viser1", ((variant=manager.getVar(name))==1)+"");
                layoutBasic.addView(layout2);
                linLayout.removeAllViews();
            }if ((variant=manager.getVar(name))==2){
                Log.d("maheg", (variant=manager.getVar(name))+"");
                Log.d("Viser2", ((variant=manager.getVar(name))==2)+"");
                layoutBasic.addView(layout);
            }
            name=nameQ.get(index);

            uri=manager.getUri(name);
            if (uri!=null){
                imageUri= Uri.parse(uri);
                try {
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    selectedImage = BitmapFactory.decodeStream(imageStream);
                    imageView.setImageBitmap(selectedImage);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
            for (String ed_text:manager.getTxt(name)){
                textView.setText(ed_text);
            }
            variant=manager.getVar(name);
            Log.d("tig", index+" "+variant+" "+name);
            if (variant==1){
                layoutBasic.removeView(layout2);
                all_var=manager.getAll_Var(name);
                Log.d("all_var23", all_var+"");
                if (all_var!=null){
                    String[] variants=all_var.split("\\| ");
                    for (String var:variants){
                        ltInflater = getLayoutInflater();
                        view = ltInflater.inflate(R.layout.edit, null, false);
                        checkBox=view.findViewById(R.id.checkbox2);
                        checkBox.setOnCheckedChangeListener(this);
                        textCreate=view.findViewById(R.id.textCreate);
                        textCreate.setText(var);
                        linLayout=findViewById(R.id.layout);
                        linLayout.addView(view);
                    }
                }
            }if (variant==2){
                layoutBasic.removeView(layout);
            }
            index++;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        answer="";
        switch (buttonView.getId()){
            case R.id.checkbox2:
                checkBox=(CheckBox) buttonView;
                view=(View)checkBox.getParent();
                textCreate=view.findViewById(R.id.textCreate);
                if (isChecked){
                    answer=textCreate.getText().toString();
                    answers.add(first+"|"+answer);
                }else {
                    answer=textCreate.getText().toString();
                    answers.remove(first+"|"+answer);
                }
                Log.d("a_N_s_W_e_R", answers+"");
        }
    }
}
