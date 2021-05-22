package com.example.finalproject;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.Inflater;

public class TestCreate extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener/*, View.OnKeyListener*/ {

    private DBManager manager;
    Context context;

    final int Pick_image=1;
    EditText edPoints, editText, exactAnswer;
    String answer="";
    Button variants_question, save, delete_image, add_variants, save_variants;
    TextView textView, check;

    int picture=0;
    LinearLayout layoutCreate,
            removableLayout,
            changeLayout, linLayout;
    final int DELETE=1;
    LayoutInflater ltInflater;


    //для edit.xml
    View view;
    CheckBox checkBox;
    TextView textCreate;
    String textCreateString;

    //диалог для создания варианта
    Dialog dialogCreate;
    EditText write_variant;
    Button createCancel, createAdd;

    //для передачи в будующий тест
    public ImageView imageView;//изображение
    public static String text_question;//текст вопроса
    public int variant=0;//тип ответа 1-по вариантам, 2-точный ответ
    public String exactAnswerString;//точный ответ
    public String points="";//количество баллов
    public ArrayList<String> all_variants=new ArrayList<>();//для всех вариантов
    ArrayList<String> correct_variant=new ArrayList<>();//правильные варианты ответов
    Bitmap selectedImage;//путь до изображения
    Uri imageUri;
    public static String cor_var="";
    public static String all_var="";
    public static String uri;
    String savedUri, savePoints;

    //для диалога
    Dialog dialog;
    Button cancel, saveAll;
    String pr_txt;

    String all_var2, cor_var2;
    int variant1=0;
    int savedVar;
    int nul=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_layout);
        String title="Create your test";
        SpannableString s = new SpannableString(title);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.Teal)), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        manager=new DBManager(this);
        exactAnswer=findViewById(R.id.editExactAnswer);
        layoutCreate=findViewById(R.id.beforeRemove);
        removableLayout=findViewById(R.id.removable);
        changeLayout=findViewById(R.id.exactAnswer);
        layoutCreate.removeView(changeLayout);
        layoutCreate.removeView(removableLayout);
        layoutCreate.addView(removableLayout);
        variants_question=findViewById(R.id.btnCreate2);
        textView=findViewById(R.id.textV);
        editText=findViewById(R.id.editCreate);
        editText.setText("");
        check=findViewById(R.id.check);
        save=findViewById(R.id.saveCrate);
        delete_image=findViewById(R.id.deleteCreate);
        add_variants=findViewById(R.id.addBtn);

        imageView = findViewById(R.id.imageView);
        Button PickImage = findViewById(R.id.btnCreate);

        manager.openDb();

        all_variants.clear();
        correct_variant.clear();
        //считываем все варианты ответов из бд и корректные варианты
        Log.d("SecondFragment", "name quest "+SecondFragment.text);
        all_var2=manager.getAll_Var(SecondFragment.text);//передали название вопроса, считали все варинты ответов с него
        Log.d("ALL_VARIANTS", all_var2+"");
        cor_var2=manager.getCor_Var(SecondFragment.text);//передали название вопроса, считали только правильные варианты ответов
        Log.d("CORRECT_VARIANT", cor_var2+"");

        //пребразует варинты в массивы извлекает из них информацию и выводит на экран
            if (all_var2!=null){
                String[] variants=all_var2.split("\\| ");//поделили сплитом строку со всеми ответами
                String[] corVar;
                for (String var:variants){//цикл для всех элементов массива
                    ltInflater = getLayoutInflater();
                    view = ltInflater.inflate(R.layout.edit, null, false);
                    view.setOnCreateContextMenuListener(TestCreate.this);
                    checkBox=view.findViewById(R.id.checkbox2);
                    checkBox.setOnCheckedChangeListener(TestCreate.this);
                    if (cor_var2!=null){
                        corVar=cor_var2.split("\\| ");//поделили сплитом струку только с правильными ответами
                        for (String cor:corVar){//цикл для всех элементов массива
                            Log.d("VAR2", cor+"");
                            boolean prov=(cor.equals(var));
                            Log.d("PROV", prov+"");
                            if (cor.equals(var)){//если варианты совпадают, то сделать для их чек бокс
                                checkBox.toggle();
                                correct_variant.add(var);//добавить в изначально пустой массив с правильными вариантами вариант
                            }
                        }
                    }
                    textCreate=view.findViewById(R.id.textCreate);
                    textCreate.setText(var);
                    linLayout = findViewById(R.id.removable);
                    linLayout.addView(view);
                    all_variants.add(var);// добавить в массив со всеми ответами ответ
                    Log.d("MASS_All_VARIANTS1", all_variants+"");
                }
            }if (all_var2==null){// в том случае, если вариант вопроса 2
                if (cor_var2!=null){
                    String[] corVar=cor_var2.split("\\| ");
                    for (String cor:corVar){//цикл для всех элементов массива
                        exactAnswer.setText(cor);
                    }
                }
            }

        //вывод из БД текста для вопроса
        if (all_var2!=null || cor_var2!=null){
            Log.d("Manager txt", manager.getTxt(SecondFragment.text)+"");
            for (String ed_txt:manager.getTxt(SecondFragment.text)){
                editText.setText(ed_txt);
                Log.d("Text for quest", ed_txt+"");
            }
        }

        //переменная, которая считывает текст для дальнейшей проверки для апдейта в методе save.setOnClickListener
        pr_txt = editText.getText().toString();

        manager.closeDb();

        manager.openDb();
        variant=manager.getVar(SecondFragment.text);// получаем вариант вопроса из БД
        Log.d("VAR infrant", variant+" and "+manager.getVar(SecondFragment.text)+"");
        manager.closeDb();
        savedVar=variant;
        Log.d("VAR", ""+savedVar);

        if (variant==1){// при различных условиях варианта ответа, разные View отображаются
            variants_question.setText("variants");
            layoutCreate.removeView(removableLayout);
            layoutCreate.addView(removableLayout);
            Toast.makeText(this, "1", Toast.LENGTH_LONG).show();
        }if (variant==2){
            variants_question.setText("the exact answer");
            layoutCreate.removeView(removableLayout);
            layoutCreate.addView(changeLayout);
            Toast.makeText(this, "2", Toast.LENGTH_LONG).show();
        }

        save.setOnClickListener(new View.OnClickListener() {//при клике на кнопку для сохранения текста вопроса
            @Override
            public void onClick(View v) {
                text_question = editText.getText().toString();//берём существующий текст
            }
        });

        variants_question.setOnClickListener(new View.OnClickListener() {//при изменении варианта ответа
            @Override
            public void onClick(View v) {
                if (variants_question.getText().equals("the exact answer")){//считываем имеющийся на кнопке текст, если текст на кнопке определяет второй вариант, то
                    if (nul==0){
                        variants_question.setText("variants");
                        variant=1;
                        layoutCreate.removeView(changeLayout);
                        layoutCreate.addView(removableLayout);
                    }else {
                            variant1=variant;
                            variants_question.setText("variants");
                            variant=1;
                            layoutCreate.removeView(changeLayout);
                            layoutCreate.addView(removableLayout);
                    }
                }else {
                    if (nul==0){
                        variants_question.setText("the exact answer");
                        variant=2;
                        layoutCreate.removeView(removableLayout);
                        layoutCreate.addView(changeLayout);
                    }else {
                        if (variant==0){
                            nul=variant;
                            variants_question.setText("the exact answer");
                            variant=2;
                            layoutCreate.removeView(removableLayout);
                            layoutCreate.addView(changeLayout);
                        }else {
                            variant1=variant;
                            variants_question.setText("the exact answer");
                            variant=2;
                            layoutCreate.removeView(removableLayout);
                            layoutCreate.addView(changeLayout);
                        }
                    }
                }
                Log.d("VARIANT AT THE MOMENT", variant+"");
            }
        });
        add_variants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCreate=new Dialog(TestCreate.this);
                dialogCreate.setContentView(R.layout.dialog_for_create);
                write_variant=dialogCreate.findViewById(R.id.write_variant);
                createCancel=dialogCreate.findViewById(R.id.createCancel);
                createAdd=dialogCreate.findViewById(R.id.createAdd);
                createCancel.setOnClickListener(TestCreate.this);
                createAdd.setOnClickListener(TestCreate.this);
                dialogCreate.setCancelable(false);
                dialogCreate.show();
            }
        });

        PickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, Pick_image);
            }
        });
        delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (picture!=0){
                    uri=imageView.getResources().toString();
                    manager.openDb();
                    manager.updateImage(uri, "");
                    manager.closeDb();
                    savedUri="";
                    imageView.setImageResource(0);
                    picture--;
                }
            }
        });
        save_variants=findViewById(R.id.save_question);
        save_variants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(TestCreate.this);
                dialog.setContentView(R.layout.dialog_befor_save);
                edPoints=dialog.findViewById(R.id.points);
                manager.openDb();
                points=manager.getPoints(SecondFragment.text);
                Log.d("points", points+"");
                edPoints.setText(points);
                savePoints=points;
                manager.closeDb();
                cancel=dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(TestCreate.this);
                saveAll=dialog.findViewById(R.id.btnSaveAll);
                saveAll.setOnClickListener(TestCreate.this);
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        if (ContextCompat.checkSelfPermission(TestCreate.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(TestCreate.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        }else {
            manager.openDb();
            uri=manager.getUri(SecondFragment.text);
            manager.closeDb();
            Log.d("ТУТ ПРОВЕРКА", uri+"");
            if (uri!=null){
                loadImages();
            }
        }
    }

    public void loadImages(){
        manager.openDb();
        uri=manager.getUri(SecondFragment.text);
        savedUri=uri;
        Log.d("PRINYT", uri+"");
        if (uri!=null){
            imageUri=Uri.parse(uri);
            try {
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Log.d("imageStream", imageStream+"");
                selectedImage = BitmapFactory.decodeStream(imageStream);
                Log.d("selectedImage", selectedImage+"");
                imageView.setImageBitmap(selectedImage);
                picture++;
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        manager.closeDb();
    }

    @Override
    public void onClick(View v) {
        all_var="";
        cor_var="";
        if (variant==0){
            variant=1;
        }
        switch (v.getId()){
            case R.id.cancel:
                dialog.dismiss();
                break;
            case R.id.btnSaveAll:
                Log.d("SAVE", cor_var2+" "+all_var2);
                points=edPoints.getText().toString();
                if (cor_var2==null && all_var2==null){

                    if (variant==2){
                        exactAnswer=findViewById(R.id.editExactAnswer);
                        exactAnswerString=exactAnswer.getText().toString();
                        cor_var=exactAnswerString+"| ";//правильный вариант
                        //text_question текст вопроса
                        //variant вариант
                        //points баллы
                        if (imageUri!=null){
                            uri=imageUri.toString();//ссылка
                        }else {
                            uri=null;
                        }

                        Log.d("name_quest", SecondFragment.text+"");
                        Log.d("text_question", text_question+"");
                        Log.d("variant", variant+"");
                        Log.d("COR_VAR", cor_var+"");
                        Log.d("ALL_VAR", null+"");
                        Log.d("points", points+"");
                        Log.d("uri", uri+"");
                        Toast.makeText(this, ""+variant, Toast.LENGTH_LONG).show();
                        manager.openDb();
                        manager.insertSet(null, SecondFragment.text, text_question, variant, cor_var, null, points, uri);
                        manager.closeDb();
                    }if (variant==1) {
                        for (String cr:correct_variant){
                            cor_var+=cr;
                            cor_var+="| ";
                        }
                        Log.d("MASS_All_VARIANTS", all_variants+"");
                        for (String al:all_variants){
                            Log.d("all_var", all_var);
                            all_var+=al;
                            all_var+="| ";
                        }
                        uri="";
                        if (imageUri!=null){
                            uri=imageUri.toString();
                            Log.d("URI", uri);
                        }else {
                            uri=null;
                        }
                        Log.d("name_quest", SecondFragment.text+"");
                        Log.d("text_question", text_question+"");
                        Log.d("variant", variant+"");
                        Log.d("COR_VAR", cor_var+"");
                        Log.d("ALL_VAR", all_var+"");
                        Log.d("points", points+"");
                        Log.d("uri", uri+"");
                        Toast.makeText(this, ""+variant, Toast.LENGTH_LONG).show();
                        manager.openDb();
                        manager.insertSet(null, SecondFragment.text, text_question, 1, cor_var, all_var, points, uri);
                        manager.closeDb();
                    }
                    dialog.dismiss();
                    Toast.makeText(this, "The properties of the question are saved, you can return to the main page", Toast.LENGTH_LONG).show();

                }else {
                    if (savedVar==variant){
                        if (variant==1){
                            for (String cr:correct_variant){
                                cor_var+=cr;
                                cor_var+="| ";
                            }
                            Log.d("MASS_All_VARIANTS", all_variants+"");
                            for (String al:all_variants){
                                all_var+=al;
                                all_var+="| ";
                            }

                            Log.d("name_quest", SecondFragment.text+"");
                            Log.d("text_question", text_question+"");
                            Log.d("variant", variant+"");
                            Log.d("COR_VAR", cor_var+"");
                            Log.d("ALL_VAR", all_var+"");
                            Log.d("points", points+"");
                            Log.d("uri", savedUri+" update "+uri);
                            manager.openDb();
                            if (pr_txt.equals("")){
                                pr_txt=null;
                            }
                            manager.updateTxt(pr_txt, text_question);
                            manager.updateAll_Var(all_var2, all_var);
                            manager.updateCor_Var(cor_var2, cor_var);
                            manager.updateVar(savedVar, variant);
                            manager.updateImage(savedUri, uri);
                            manager.updatePoints(savePoints, points);
                            manager.closeDb();
                        }if (variant==2){
                            exactAnswer=findViewById(R.id.editExactAnswer);
                            exactAnswerString=exactAnswer.getText().toString();
                            cor_var=exactAnswerString+"| ";

                            Log.d("name_quest", SecondFragment.text+"");
                            Log.d("text_question", text_question+"");
                            Log.d("variant", variant+"");
                            Log.d("COR_VAR", cor_var+"");
                            Log.d("ALL_VAR", all_var+"");
                            Log.d("points", points+"");
                            Log.d("uri", savedUri+" update "+uri);
                            manager.openDb();
                            if (pr_txt.equals("")){
                                pr_txt=null;
                            }
                            manager.updateTxt(pr_txt, text_question);
                            manager.updateCor_Var(cor_var2, cor_var);
                            manager.updateVar(savedVar, variant);
                            manager.updateAll_Var(all_var2, null);
                            manager.updatePoints(savePoints, points);
                            manager.updateImage(savedUri, uri);
                            manager.closeDb();
                        }
                        dialog.dismiss();
                        Toast.makeText(this, "The properties of the question are saved, you can return to the main page", Toast.LENGTH_LONG).show();
                    }else {
                        if (variant==1){
                            for (String cr:correct_variant){
                                cor_var+=cr;
                                cor_var+="| ";
                            }
                            Log.d("MASS_All_VARIANTS", all_variants+"");
                            for (String al:all_variants){
                                all_var+=al;
                                all_var+="| ";
                            }
                            Log.d("name_quest", SecondFragment.text+"");
                            Log.d("text_question", text_question+"");
                            Log.d("variant", savedVar+" update "+variant);
                            Log.d("COR_VAR", cor_var2+" update "+cor_var);
                            Log.d("ALL_VAR", all_var2+" update "+all_var);
                            Log.d("points", points+"");
                            Log.d("uri", savedUri+" update "+uri);
                            manager.openDb();
                            if (pr_txt.equals("")){
                                pr_txt=null;
                            }
                            manager.updateTxt(pr_txt, text_question);
                            manager.updateAll_Var(all_var2, all_var);
                            manager.updateCor_Var(cor_var2, cor_var);
                            manager.updateVar(savedVar, variant);
                            manager.updateImage(savedUri, uri);
                            manager.updatePoints(savePoints, points);
//                            manager.updateTxt(pr_txt, text_question);
                            manager.closeDb();
                            dialog.dismiss();
                            Toast.makeText(this, "The properties of the question are saved, you can return to the main page", Toast.LENGTH_LONG).show();
                        }if (variant==2){
                            exactAnswer=findViewById(R.id.editExactAnswer);
                            exactAnswerString=exactAnswer.getText().toString();
                            cor_var=exactAnswerString+"| ";
                            all_var=null;

                            Log.d("name_quest", SecondFragment.text+"");
                            Log.d("text_question", text_question+"");
                            Log.d("variant", savedVar+" update "+variant);
                            Log.d("COR_VAR", cor_var2+" update "+cor_var);
                            Log.d("ALL_VAR", all_var2+" update "+all_var);
                            Log.d("points", points+"");
                            Log.d("uri", savedUri+" update "+uri);
                            manager.openDb();
                            if (pr_txt.equals("")){
                                pr_txt=null;
                            }
                            manager.updateTxt(pr_txt, text_question);
                            manager.updateAll_Var(all_var2, all_var);
                            manager.updateCor_Var(cor_var2, cor_var);
                            manager.updateVar(savedVar, variant);
                            manager.updateImage(savedUri, uri);
                            manager.updatePoints(savePoints, points);
                            manager.closeDb();
                            dialog.dismiss();
                            Toast.makeText(this, "The properties of the question are saved, you can return to the main page", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
            case R.id.createCancel:
                dialogCreate.dismiss();
                break;
            case R.id.createAdd:
                ltInflater = getLayoutInflater();
                view = ltInflater.inflate(R.layout.edit, null, false);
                view.setOnCreateContextMenuListener(TestCreate.this);
                checkBox=view.findViewById(R.id.checkbox2);
                checkBox.setOnCheckedChangeListener(TestCreate.this);
                textCreate=view.findViewById(R.id.textCreate);
                textCreateString=write_variant.getText().toString();
                textCreate.setText(textCreateString);
                all_variants.add(textCreateString);//добавление в массив для всех вариантов одного варианта
                Log.d("MASS_All_VARIANTS", all_variants+"");
                linLayout = findViewById(R.id.removable);
                linLayout.addView(view);
                dialogCreate.dismiss();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.checkbox2:
                checkBox=(CheckBox) buttonView;
                view=(View)checkBox.getParent();
                textCreate=view.findViewById(R.id.textCreate);
                String sravnenie=textCreate.getText().toString();
                if (answer.equals("")){
                    answer=textCreate.getText().toString();
                    correct_variant.add(answer);
                    check.setText(answer);
                }else {
                    if (answer.equals(sravnenie)){
                        correct_variant.remove(answer);
                        check.setText("");
                    }if (answer!=sravnenie){
                        answer=sravnenie;
                        correct_variant.add(answer);
                        check.setText(answer);
                    }
                }
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        switch (v.getId()){
            case R.id.text2:
                menu.add(0, DELETE, 0, "delete");
            default:
                view=v;
                menu.add(0, DELETE, 0, "delete");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case DELETE:
                linLayout.removeView(view);
                textCreate=view.findViewById(R.id.textCreate);
                answer=textCreate.getText().toString();
                all_variants.remove(answer);
                if (correct_variant.contains(answer)){
                    correct_variant.remove(answer);
                }
                answer="";
                check.setText("");
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case Pick_image:
                if(resultCode == RESULT_OK){
                    try {
                        imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                        picture++;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
