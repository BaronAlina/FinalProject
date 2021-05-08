package com.example.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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

import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class TestCreate extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener/*, View.OnKeyListener*/ {

    private DBManager manager;
    Context context;

    final int Pick_image=1;
    EditText editT, edittext, edPoints, editText, exactAnswer;
    String answer="", answer2="", edit_variants, variants;
    Button variants_question, save, delete_image, add_variants, save_variants;
    TextView textView, check;

    int picture=0, firstString;
    LinearLayout layoutCreate,
            removableLayout,
            changeLayout, firstLayout, linLayout, forPoint, removeForPoint;
    final int DELETE=1;
    int forIdBox=0, forIdBox2=0;
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
    public String text_question;//текст вопроса
    public int variant=1;//тип ответа 1-по вариантам, 2-точный ответ
    public String exactAnswerString;//точный ответ
    public String points;//количество баллов
    public ArrayList<String> all_variants=new ArrayList<>();//для всех вариантов
    ArrayList<String> correct_variant=new ArrayList<>();//правильные варианты ответов
    Bitmap selectedImage;//путь до изображения
    Uri imageUri;
    public static String cor_var;
    public static String all_var;
    public static String uri;

    //для диалога
    Dialog dialog;
    EditText edDialog;
    Button cancel, saveAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_layout);

        manager=new DBManager(this);

        layoutCreate=findViewById(R.id.beforeRemove);
        removableLayout=findViewById(R.id.removable);
        changeLayout=findViewById(R.id.exactAnswer);
        layoutCreate.removeView(changeLayout);
//        removeForPoint=findViewById(R.id.removeForPoint);
//        forPoint=findViewById(R.id.forPoint);
//        removeForPoint.removeView(forPoint);
//        firstLayout=findViewById(R.id.variantsLayout);
        textView=findViewById(R.id.textV);
        editText=findViewById(R.id.editCreate);
        exactAnswer=findViewById(R.id.editExactAnswer);
//        edPoints=findViewById(R.id.points);
        check=findViewById(R.id.check);
//        elementary=findViewById(R.id.checkbox);
//        elementary.setOnCheckedChangeListener(this);
        save=findViewById(R.id.saveCrate);
        delete_image=findViewById(R.id.deleteCreate);
        variants_question=findViewById(R.id.btnCreate2);
        add_variants=findViewById(R.id.addBtn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_question = editText.getText().toString();
            }
        });
        variants_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (variants_question.getText().equals("the exact answer")){
                    variants_question.setText("variants");
                    variant=1;
                    layoutCreate.removeView(changeLayout);
                    layoutCreate.addView(removableLayout);
                }else {
                    variants_question.setText("the exact answer");
                    variant=2;
                    layoutCreate.removeView(removableLayout);
                    layoutCreate.addView(changeLayout);
                }
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

//                ltInflater = getLayoutInflater();
//                context=ltInflater.getContext();
//                view = ltInflater.inflate(R.layout.edit, null, false);
//                view.setOnCreateContextMenuListener(TestCreate.this);
//                view.setOnClickListener(TestCreate.this);
//                checkBox=view.findViewById(R.id.checkbox2);
//                checkBox.setOnClickListener(TestCreate.this);
//                checkBox.setOnCheckedChangeListener(TestCreate.this);
//                editT=view.findViewById(R.id.editT);
//                editT.setOnKeyListener(TestCreate.this);

//                linLayout = findViewById(R.id.removable);
//                linLayout.setOnClickListener(TestCreate.this);
//                linLayout.addView(view);
//                EditText editText=view.findViewById(R.id.edit);

            }
        });
        imageView = findViewById(R.id.imageView);
        Button PickImage = findViewById(R.id.btnCreate);
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
                    imageView.setImageResource(0);
                    picture--;
                }
            }
        });
        save_variants=findViewById(R.id.save_question);
        save_variants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                removeForPoint.addView(forPoint);
                dialog=new Dialog(TestCreate.this);
                dialog.setContentView(R.layout.dialog_befor_save);
                edDialog=dialog.findViewById(R.id.points);
                cancel=dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(TestCreate.this);
                saveAll=dialog.findViewById(R.id.btnSaveAll);
                saveAll.setOnClickListener(TestCreate.this);
                dialog.setCancelable(false);
                dialog.show();
            }
        });

//        saveAll=findViewById(R.id.btnSaveAll);
//        saveAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                exactAnswerString=exactAnswer.getText().toString();
//                points=edPoints.getText().toString();
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.openDb();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                dialog.dismiss();
                break;
            case R.id.btnSaveAll:
                edPoints=dialog.findViewById(R.id.points);
                points=edPoints.getText().toString();
                if (variant==2){
                    exactAnswerString=exactAnswer.getText().toString();
                    cor_var=exactAnswerString;//правильный вариант
                    //text_question текст вопроса
                    //variant вариант
                    //points баллы
                    if (imageUri!=null){
                        uri=imageUri.toString();//ссылка
                    }else {
                        uri=null;
                    }
                    manager.insertSet(SecondFragment.text, text_question, variant, cor_var, null, points, uri);
                }if (variant==1) {
                    cor_var= correct_variant.toString();
                    all_var=all_variants.toString();
                    uri="";
                    if (imageUri!=null){
                        uri=imageUri.toString();
                    }else {
                        uri=null;
                    }
                    manager.insertSet(SecondFragment.text, text_question, variant, cor_var, all_var, points, uri);
//                    manager.insertSettings(text_question, variant, cor_var, all_var, points, uri);
//                    Test test =new Test();
//                    test.all_variantsT=all_variants;
//                    test.correct_variantsT=correct_variant;
//                    test.pointT=points;
//                    test.variantT=variant;
//                    test.text_questionT=text_question;
//                    Intent intent=new Intent();
//                    intent.putExtra("variant", variant);
//                    intent.putExtra("all_variants", all_variants);
//                    intent.putExtra("correct_variant", correct_variant);
//                    intent.putExtra("points", points);
//                    intent.putExtra("text_question", text_question);
//                    intent.setClass(TestCreate.this, Test.class);


//                startActivity(intent);
                }
                dialog.dismiss();
                Toast.makeText(this, "The properties of the question are saved, you can return to the main page", Toast.LENGTH_LONG).show();
                manager.closeDb();
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
                all_variants.add(textCreateString);
                linLayout = findViewById(R.id.removable);
                linLayout.addView(view);
                dialogCreate.dismiss();
                break;
        }
//        Log.d("PROVERKA", "start");
//        switch (v.getId()){
////            case R.id.removable:
////                check.setText("+");
////                break;
//            case R.id.text:
//                view=v;
//                editT=view.findViewById(R.id.editT);
//            default:
//                editLayout=(LinearLayout) v;
//                editT=editLayout.findViewById(R.id.editT);
////                checkBox=editLayout.findViewById(R.id.checkbox2);
//                if (answer.equals("")){
//                    answer=editT.getText().toString();
//                    correct_variant.add(answer);
//                    check.setText(answer);
//                    forIdBox2++;
//                }else {
//                    correct_variant.remove(answer);
//                    check.setText("");
//                    answer="";
//                    forIdBox2--;
//                }
//        }
//        Log.d("PROVERKA", "finish");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
//            case R.id.checkbox:
//                edittext=findViewById(R.id.edittext);
//                answer2=edittext.getText().toString();
//                if (forIdBox==0){
//                    correct_variant.add(answer2);
//                    check.setText(answer2);
//                    forIdBox++;
//                }else {
//                    correct_variant.remove(answer2);
//                    check.setText("");
//                    forIdBox--;
//                }
//                break;
            case R.id.checkbox2:
                checkBox=(CheckBox) buttonView;
                view=(View)checkBox.getParent();
//                onClick(view);
//                view.callOnClick();
//                context=view.getContext();
//                context=view.getContext();
//                editT=new EditText(context);
//                view=ltInflater.inflate(R.layout.edit, null, false);
//                editT=view.findViewById(R.id.editT);
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
//                if (answer!="" && answer.equals(sravnenie)){
//                    correct_variant.remove(answer);
//                    check.setText("");
//                    answer="";
//                }if (answer!="" && answer!=sravnenie){
//                    correct_variant.remove(answer);
//                    answer=textCreate.getText().toString();
//                    correct_variant.add(answer);
//                    check.setText(answer);
//            }
                break;
        }
    }

//    @Override
//    public boolean onKey(View v, int keyCode, KeyEvent event) {
//        if (event.getAction()==KeyEvent.ACTION_DOWN && (keyCode==KeyEvent.KEYCODE_ENTER)){
//            String random;
//            view=v;
//            editT=view.findViewById(R.id.editT);
//            random=editT.getText().toString();
//            all_variants.add(random);
//            check.setText("");
//            check.setText(random);
//        }
//        return false;
//    }

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
