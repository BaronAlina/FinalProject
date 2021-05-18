package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Result extends Activity implements View.OnClickListener {

    ArrayList<String> answers=new ArrayList<>();
    DBManager manager;
    ArrayList<String> correctArr=new ArrayList<>();
    String correct, answer, points;
    int p=0, pfor1=0, pointRec=0, forTask=0, total_amount=0;
    ListView listView;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter adapter;
    ArrayList<String> nameQ=new ArrayList<>();
    ArrayList<String> arr=new ArrayList<>();
    ArrayList<String> arr2=new ArrayList<>();
    TextView textView, nameQ_and_MaxP, mark, tablet1, tablet2;
    LayoutInflater inflater;
    View view;
    LinearLayout rem;
    Button complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
//        listView=findViewById(R.id.listView);
        complete=findViewById(R.id.complete);
        complete.setOnClickListener(this);
        textView=findViewById(R.id.total);
        nameQ_and_MaxP=findViewById(R.id.nameq);
        nameQ_and_MaxP.setText("Name question"+"\n"+"max point");
        mark=findViewById(R.id.points);
        mark.setText("Your/Max");
        answers=getIntent().getStringArrayListExtra("Array answers");
        Log.d("Vyvod", answers+"");
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
        int index=0;

        for (String nameQ:nameQ){
            points=manager.getPoints(nameQ);
            p=Integer.parseInt(points);
            Log.d("За задание", points+"");
            total_amount+=p;
            Log.d("За тест", total_amount+"");
            String[] ansp;
            for (String ans:answers){
                ansp=ans.split("\\|");
                Log.d("Ваши ответы на задание", answers+"");
                if (ansp[0].equals(nameQ)){
                    Log.d("Задание из вашего ответа", ansp[0]+"");
                    correct=manager.getCor_Var(nameQ);
                    String[] cor_var=correct.split("\\| ");
                    Log.d("Правильные ответы на задание", correct+"");
                    ArrayList<String> cor=new ArrayList<>();
                    for (String vr:cor_var){
                        if (!(vr.equals(""))){
                            cor.add(vr);
                        }
                    }
                    pfor1=p/cor.size();
                    Log.d("Балл за один правильный ответ", pfor1+"");
                    for (String cr: cor_var){
                        Log.d("Правильный вариант", cr+"");
                        if (1!=ansp.length){
                            if (ansp[1].equals(cr)){
                                Log.d("Совпало", ansp[1]+" and "+cr);
                                pointRec+=pfor1;//полученные очки
                                forTask+=pfor1;//за конкретное задание
                            }
                        }

                    }
                }
            }

//            Log.d("Masiv cor", correct+"");
//            Log.d("Points for one", pfor1+"");
//
//            for (int j=0; j<cor_var.length; j++){
//                Log.d("SRAV", answers.size()+" "+j);
//                if (answers.size()!=j && answers.size()>=j){
//                    answer=answers.get(j);
//                    Log.d("Answer", answer+"");
//                    answers.remove(j);
//                    Log.d("CVlen", cor_var[j]+"");
//                    if (answer.equals(cor_var[j])){
//                        Log.d("Answer and CVlen", answer+" and "+cor_var[j]);
//
//                        Log.d("points received", pointRec+"");
//
//                    }
//                }
//            }
            arrayList.add(nameQ+"\n"+points);
            correctArr.add(forTask+"/"+points);
            textView.setText(pointRec+"/"+total_amount);
            forTask=0;
        }

//        rem=findViewById(R.id.rem);
        int ind=0;
        for (String name:arrayList){
//            for (String p:correctArr){
            String c=correctArr.get(ind);
            inflater=getLayoutInflater();
            view=inflater.inflate(R.layout.view, null, false);
            tablet1=new TextView(this);
            tablet1=view.findViewById(R.id.tablet1);
            tablet1.setText(name);
            tablet2=new TextView(this);
            tablet2=view.findViewById(R.id.tablet2);
            tablet2.setText(c);
            rem=findViewById(R.id.rem);
            rem.addView(view);
            ind++;
//            }
        }

//        for (String p:correctArr){
////            inflater=getLayoutInflater();
////            view=inflater.inflate(R.layout.view, null, false);
//
//            rem=findViewById(R.id.rem);
//            rem.addView(view);
//        }

//        adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
//        listView.setAdapter(adapter);
//        for (String answer:answers){
//
//            /*correct=manager.getCor_Var(answer)*/
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.complete:
                Intent intent=new Intent();
                intent.putExtra("to main", p);
                intent.setClass(Result.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
