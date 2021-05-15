package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBManager(Context context) {
        this.context = context;
        dbHelper=new DBHelper(context);
    }

    public void openDb(){
        database=dbHelper.getWritableDatabase();//для записи и для считывания
    }

    //пишем в БД название теста
    public void insertNameTest(String test_name){
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constants.COLUMN_TEST_NAME, test_name);
        database.insert(Constants.TABLE_NAME, null, contentValues);
    }

    //пишем в БД название вопроса, текст вопроса, вариант, правильный вариант, все варианты, балл, ссылка
    public void insertSet(String name_test, String name_question, String text_question, int variant, String correct_variants, String all_variants, String points, String uri){
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constants.COLUMN_TEST_NAME, name_test);
        contentValues.put(Constants.COLUMN_NAME_QUESTION, name_question);
        contentValues.put(Constants.COLUMN_TEXT_QUESTION, text_question);
        contentValues.put(Constants.COLUMN_VARIANT, variant);
        contentValues.put(Constants.COLUMN_CORRECT_VARIANT, correct_variants);
        contentValues.put(Constants.COLUMN_ALL_VARIANTS, all_variants);
        contentValues.put(Constants.COLUMN_POINTS, points);
        contentValues.put(Constants.COLUMN_URI, uri);
        database.insert(Constants.TABLE_NAME, null, contentValues);
    }

    //пишем в БД
//    public void insertToDb(String name_test, String name_question, String text_question, int variant, String correct_variant, String all_variant, String points, String uri){
//        ContentValues contentValues=new ContentValues();
//        contentValues.put(Constants.COLUMN_TEST_NAME, name_test);
//        contentValues.put(Constants.COLUMN_NAME_QUESTION, name_question);
//        contentValues.put(Constants.COLUMN_TEXT_QUESTION, text_question);
//        contentValues.put(Constants.COLUMN_VARIANT, variant);
//        contentValues.put(Constants.COLUMN_CORRECT_VARIANT, correct_variant);
//        contentValues.put(Constants.COLUMN_ALL_VARIANTS, all_variant);
//        contentValues.put(Constants.COLUMN_POINTS, points);
//        contentValues.put(Constants.COLUMN_URI, uri);
//        database.insert(Constants.TABLE_NAME, null, contentValues);
//    }

    public void insertSettings(String name_test, String name_question /*, int variant, String correct_variant, String all_variant, String points, String uri*/){
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constants.COLUMN_TEST_NAME, name_test);
        contentValues.put(Constants.COLUMN_NAME_QUESTION, name_question);
//        contentValues.put(Constants.COLUMN_VARIANT, variant);
//        contentValues.put(Constants.COLUMN_CORRECT_VARIANT, correct_variant);
//        contentValues.put(Constants.COLUMN_ALL_VARIANTS, all_variant);
//        contentValues.put(Constants.COLUMN_POINTS, points);
//        contentValues.put(Constants.COLUMN_URI, uri);
        database.insert(Constants.TABLE_NAME, null, contentValues);
    }



    //считываем с БД имена вопросов
//    public List<String> getFromDb(){
//        List<String> tempList = new ArrayList<>();
//        Cursor cursor = database.query(Constants.TABLE_NAME, null, null, null, null, null, null);
//
//        while (cursor.moveToNext()){
//            String name_question = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME_QUESTION));
//            tempList.add(name_question);
//        }
//        cursor.close();
//        return tempList;
//    }

    public String getPoints(String name_question){
        String point =null;
        Cursor cursor=database.query(Constants.TABLE_NAME, null, null, null, null, null, null);

        ArrayList<String > nameT=new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String n = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME_QUESTION));
            nameT.add(n);
        }

        ArrayList<String> pointA = new ArrayList<>();
        pointA.clear();
        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            Log.d("GETNQ", "start");
            String i1 = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_POINTS));
            pointA.add(i1);
            Log.d("GETNQ", "final");
        }
        for (String i : nameT/*int d=0; d<nameT.size(); d++*/) {
//                        String i=nameT.get(d);
            Log.d("VHOD", ""+i);
            if (i != null) {
                if (i.equals(name_question)) {
                    int index = nameT.indexOf(i);
                    for (String n1 : pointA) {
                        int index2 = pointA.indexOf(n1);
                        Log.d("In index1", "" + index + " "+nameT.size());
                        Log.d("For index1", "1. " + n1+ " index: "+index2);
                        if (index == index2) {

                            if (n1 != null) {
                                point=n1;
                            }

                        }
                        Log.d("For index1", "2. " + i+" index: "+ index);
                                    for (int n=1; n<=index2; n++){
                                        pointA.set(n-1, "");
                                    }
                        Log.d("In index2", "" + index2 + " " + pointA);
                    }
                    nameT.set(index, "");
                    for (int m=0; m<index; m++){
                        nameT.set(m, "");
                    }
                }
            }
            Log.d("VHOD", ""+nameT.size());
        }

        cursor.close();
        return point;
    }

    //считываем с БД имена тестов
    public List<String> getNameTest(){
        List<String> nameList = new ArrayList<>();
        Cursor cursor=database.query(Constants.TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()){
            String name_test = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TEST_NAME));
            nameList.add(name_test);
            Log.d("Name Test", name_test+" ");
        }

        ArrayList<String> arr=new ArrayList<>();
        for (String name:nameList) {
            if (name!=null){
                arr.add(name);
            }
        }
        int i=0;
        ArrayList<String> arr2=new ArrayList<>();
        for (String name:arr){
            if (i==0){
                arr2.add(name);
            }else {
                if (!(arr2.contains(name))){
                    arr2.add(name);
                }
            }
            i=1;
        }

//        for (int i=0; i<nameList.size()-1; i++){
//            String nL1=nameList.get(i);
//            if (nL1==null){
//                nL1="";
//            }
//            for (int i1=1; i1<nameList.size(); i1++) {
//                String nL2=nameList.get(i1);
//                if (nL2==null){
//                    nL2="";
//                }
//                if (nL1.equals(nL2)){
//                    nameList.remove(i1);
//                }
//            }
//        }

        cursor.close();
        return arr2;
    }

    //считываем с БД имена вопросов
    public List<String> getNameQuestion(){
        List<String> nameList = new ArrayList<>();
        Cursor cursor=database.query(Constants.TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()){
            String name_question = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME_QUESTION));
            nameList.add(name_question);
        }
        cursor.close();
        return nameList;
    }

    //метод для передачи в класс Test настроек вопросов
    public List<String> getTxt(String name_question){
        List<String> nameL = new ArrayList<>();
        Cursor cursor=database.query(Constants.TABLE_NAME, null, null, null, null, null, null);

        ArrayList<String > nameT=new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String n = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME_QUESTION));
            nameT.add(n);
        }

        ArrayList<String> nameQ = new ArrayList<>();
        nameQ.clear();
        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            Log.d("GETNQ", "start");
            String i1 = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TEXT_QUESTION));
            nameQ.add(i1);
            Log.d("GETNQ", "final");
        }
        for (String i : nameT/*int d=0; d<nameT.size(); d++*/) {
//                        String i=nameT.get(d);
            Log.d("VHOD", ""+i);
            if (i != null) {
                if (i.equals(name_question)) {
                    int index = nameT.indexOf(i);
                    for (String n1 : nameQ) {
                        int index2 = nameQ.indexOf(n1);
                        Log.d("In index1", "" + index + " "+nameT.size());
                        Log.d("For index1", "1. " + n1+ " index: "+index2);
                        if (index == index2) {

                            if (n1 != null) {
                                nameL.add(n1);
                            }

                        }
                        Log.d("For index1", "2. " + i+" index: "+ index);
                                    for (int n=1; n<=index2; n++){
                                        nameQ.set(n-1, "");
                                    }
                        Log.d("In index2", "" + index2 + " " + nameQ);
                    }
                    nameT.set(index, "");
                    for (int m=0; m<index; m++){
                        nameT.set(m, "");
                    }
                }
            }
            Log.d("VHOD", ""+nameT.size());
        }

        cursor.close();
        return nameL;
    }

    public int getVar(String name_question){
        int variant=0;
//        List<Integer> variants=new ArrayList<>();
        Cursor cursor=database.query(Constants.TABLE_NAME, null, null, null, null, null, null);

        ArrayList<String > nameT=new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String n = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME_QUESTION));
            nameT.add(n);
        }

        ArrayList<Integer> variants = new ArrayList<>();
//        variants.clear();
//        cursor.moveToFirst();
//        while (cursor.moveToNext()) {
//            Log.d("GETNQ", "start");
//            int i1 = cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_VARIANT));
//            variants.add(i1);
//            Log.d("GETNQ", "final");
//        }

        for (String i : nameT) {
            Log.d("VHOD", ""+i);
            if (i != null) {
                if (i.equals(name_question)) {
                    int index = nameT.indexOf(i);

                    variants.clear();
                    cursor.moveToFirst();
                    while (cursor.moveToNext()) {
                        Log.d("GETNQ", "start");
                        int i1 = cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_VARIANT));
                        variants.add(i1);
                        Log.d("GETNQ", "final");
                    }

                    for (int n1 : variants) {
                        int index2 = variants.indexOf(n1);
                        Log.d("In index1", "" + index + " "+nameT.size());
                        Log.d("For index1", "1. " + n1+ " index: "+index2);
                        if (index == index2) {

                            if (n1 != 0) {
                                variant= n1;

                            }

                        }
                        Log.d("For index1", "2. " + i+" index: "+ index);
//                        if (index2!=0){
//                            variants.set(index2, 0);
//                        }
                                    for (int n=1; n<=index2; n++){
                                        Log.d("Null for ", n-1+" index");
                                        variants.set(n, 0);
                                    }
                        Log.d("In index2", "" + index2 + " " + variants);
                    }
                    nameT.set(index, "");
                    for (int m=0; m<index; m++){
                        nameT.set(m, "");
                    }
                }
            }
            Log.d("VHOD", ""+nameT.size());
        }

        cursor.close();
        return variant;
    }

    public String getAll_Var(String name_question){
        String all=null;
        Cursor cursor=database.query(Constants.TABLE_NAME, null, null, null, null, null, null);
        ArrayList<String> nameT=new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String n = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME_QUESTION));
            nameT.add(n);
        }

        ArrayList<String> allVar=new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            String n =cursor.getString(cursor.getColumnIndex(Constants.COLUMN_ALL_VARIANTS));
            allVar.add(n);
        }

        for (String i : nameT/*int d=0; d<nameT.size(); d++*/) {
//                        String i=nameT.get(d);
            Log.d("VHOD", ""+i);
            if (i != null) {
                if (i.equals(name_question)) {
                    int index = nameT.indexOf(i);
                    for (String n1 : allVar) {
                        int index2 = allVar.indexOf(n1);
                        Log.d("In index1", "" + index + " "+nameT.size());
                        Log.d("For index1", "1. " + n1+ " index: "+index2);
                        if (index == index2) {

                            if (n1 != null) {
                                all=n1;
                            }

                        }
                        Log.d("For index1", "2. " + i+" index: "+ index);
//                                    nameQ.set(index2, "");
                                    for (int n=1; n<=index2; n++){
                                        allVar.set(n-1, "");
                                    }
                        Log.d("In index2", "" + index2);
                    }
                    nameT.set(index, "");
                    for (int m=0; m<index; m++){
                        nameT.set(m, "");
                    }
                }
            }
            Log.d("VHOD", ""+nameT.size());
        }

        Log.d("All", all+"");
        return all;
    }

    public String getCor_Var(String name_question){
        String correct=null;
        Cursor cursor=database.query(Constants.TABLE_NAME, null, null, null, null, null, null);
        ArrayList<String> nameT=new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String n = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME_QUESTION));
            nameT.add(n);
        }

        ArrayList<String> corVar=new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            String n =cursor.getString(cursor.getColumnIndex(Constants.COLUMN_CORRECT_VARIANT));
            corVar.add(n);
        }

        for (String i : nameT/*int d=0; d<nameT.size(); d++*/) {
//                        String i=nameT.get(d);
            Log.d("VHOD", ""+i);
            if (i != null) {
                if (i.equals(name_question)) {
                    int index = nameT.indexOf(i);
                    for (String n1 : corVar) {
                        int index2 = corVar.indexOf(n1);
                        Log.d("In index1", "" + index + " "+nameT.size());
                        Log.d("For index1", "1. " + n1+ " index: "+index2);
                        if (index == index2) {

                            if (n1 != null) {
                                correct=n1;
                            }

                        }
                        Log.d("For index1", "2. " + i+" index: "+ index);
//                                    corVar.set(index2, "");
//                                    for (int n=1; n<=index2; n++){
//                                        corVar.set(n, "");
//                                    }
                        Log.d("In index2", "" + index2);
                    }
                    nameT.set(index, "");
                    for (int m=0; m<index; m++){
                        nameT.set(m, "");
                    }
                }
            }
            Log.d("VHOD", ""+nameT.size());
        }

        Log.d("CORRECT", correct+"");
        return correct;
    }

    public String getUri(String name_question){
        String uri=null;
        Cursor cursor=database.query(Constants.TABLE_NAME, null, null, null, null, null, null);
        ArrayList<String> nameT=new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String n = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME_QUESTION));
            nameT.add(n);
        }

        ArrayList<String> uriArr=new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            String n =cursor.getString(cursor.getColumnIndex(Constants.COLUMN_URI));
            uriArr.add(n);
        }

        for (String i : nameT/*int d=0; d<nameT.size(); d++*/) {
//                        String i=nameT.get(d);
            Log.d("VHOD", ""+i);
            if (i != null) {
                if (i.equals(name_question)) {
                    int index = nameT.indexOf(i);
                    for (String n1 : uriArr) {
                        int index2 = uriArr.indexOf(n1);
                        Log.d("In index1", "" + index + " "+nameT.size());
                        Log.d("For index1", "1. " + n1+ " index: "+index2);
                        if (index == index2) {

                            if (n1 != null) {
                                uri=n1;
                            }

                        }
                        Log.d("For index1", "2. " + i+" index: "+ index);
//                                    corVar.set(index2, "");
//                                    for (int n=1; n<=index2; n++){
//                                        uriArr.set(n-1, "");
//                                    }
                        Log.d("In index2", "" + index2);
                    }
                    nameT.set(index, "");
                    for (int m=0; m<index; m++){
                        nameT.set(m, "");
                    }
                }
            }
            Log.d("VHOD", ""+nameT.size());
        }

        return uri;
    }

    public List<String> getNQ(String name_test){
        List<String> nameListF=new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        Cursor cursor=database.query(Constants.TABLE_NAME, null, null, null, null, null, null);
//        List<String> nameT = new ArrayList<>();
        ArrayList<String > nameT=new ArrayList<>();
        nameT.clear();
        String[] nameTest= new String[1000];
        String[] nameQuest=new String[1000];
//        String [] nameT;
//        while (cursor.moveToNext()){
//            String name_testC = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TEST_NAME));
//            if (name_testC==null){
//                name_testC="";
//            }
////            nameList.add(name_testC);
//            Log.d("PROVERKA", "start "+name_testC);
//            if (name_testC.equals(name_test)){
//                Log.d("PROVERKA", "start");
//                Cursor cur=cursor;
//                String name_question = "";
//                int c=cursor.getPosition();
                //


        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String n = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TEST_NAME));
            nameT.add(n);
        }

        ArrayList<String> nameQ = new ArrayList<>();
        nameQ.clear();
        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            Log.d("GETNQ", "start");
            String i1 = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME_QUESTION));
            nameQ.add(i1);
            Log.d("GETNQ", "final");
        }
                    for (String i : nameT/*int d=0; d<nameT.size(); d++*/) {
//                        String i=nameT.get(d);
                        Log.d("VHOD", ""+i);
                        if (i != null) {
                            if (i.equals(name_test)) {
                                int index = nameT.indexOf(i);
                                for (String n1 : nameQ) {
                                    int index2 = nameQ.indexOf(n1);
                                    Log.d("In index1", "" + index + " "+nameT.size());
                                    Log.d("For index1", "1. " + n1+ " index: "+index2);
                                    if (index == index2) {

                                        if (n1 != null) {
                                            nameList.add(n1);
                                        }

                                    }
                                    Log.d("For index1", "2. " + i+" index: "+ index);
//                                    nameQ.set(index2, "");
//                                    for (int n=0; n<index2; n++){
//                                        nameQ.set(n, "");
//                                    }
                                    Log.d("In index2", "" + index2);
                                }
                                nameT.set(index, "");
                                for (int m=0; m<index; m++){
                                    nameT.set(m, "");
                                }
                            }
                        }
                        Log.d("VHOD", ""+nameT.size());
                    }



//                while (cursor.moveToNext()){
//                    name_question = cur.getString(cur.getColumnIndexOrThrow(Constants.COLUMN_NAME_QUESTION));
//                    if (name_question!=null){
//                        nameList.add(name_question);
//                    }
//                }
//                Log.d("PROVERKA", "finish" + name_question);
//            }
//            Log.d("PROVERKA", "finish "+name_test);
//        }


        cursor.close();
        return nameList;

    }

    //удаляем из БД имена тестов
    public void deleteTest(String name_test){
        database.delete(Constants.TABLE_NAME, Constants.COLUMN_TEST_NAME + " = ?", new String[] {String.valueOf(name_test)});
    }

    //удаляем из БД имена вопросов
    public void deleteQuestion(String name_question){
        database.delete(Constants.TABLE_NAME, Constants.COLUMN_TEXT_QUESTION + " = ?", new String[] {String.valueOf(name_question)});
    }

    //переименновываем в БД имена тестов
    public void updateTest(String name_test1, String name_test2){
        Log.d("Success", "false");
        boolean success = false;
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constants.COLUMN_TEST_NAME, name_test2);
        success=database.update(Constants.TABLE_NAME, contentValues, Constants.COLUMN_TEST_NAME + " = '" + name_test1+"'", null)>0;
        Log.d("Success", ""+success);
    }

    //переименновываем в БД имена вопросов
    public void updateQuestion(String name_question1, String name_question2){
        Log.d("Success", "false");
        boolean success = false;
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constants.COLUMN_NAME_QUESTION, name_question2);
        success=database.update(Constants.TABLE_NAME, contentValues, Constants.COLUMN_NAME_QUESTION + " = '" + name_question1 + "'", null)>0;
        Log.d("Success", ""+success);
    }

    public void updateTxt(String text_question1, String text_question2){
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constants.COLUMN_TEXT_QUESTION, text_question2);
        database.update(Constants.TABLE_NAME, contentValues, Constants.COLUMN_TEXT_QUESTION + " = '" + text_question1 + "'", null);
    }

    public void updateVar(int variant1, int variant2){
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constants.COLUMN_VARIANT, variant2);
        database.update(Constants.TABLE_NAME, contentValues, Constants.COLUMN_VARIANT+" = '"+variant1+"'", null);
    }

    public void updateAll_Var(String all_var1, String all_var2){
        Log.d("up", all_var1+" "+all_var2);
        Log.d("Success", "false");
        boolean success = false;
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constants.COLUMN_ALL_VARIANTS, all_var2);
        success=database.update(Constants.TABLE_NAME, contentValues, Constants.COLUMN_ALL_VARIANTS+" = '"+all_var1+"'", null)>0;
        Log.d("Success", ""+success);
    }

    public void  updateCor_Var(String cor_var1, String cor_var2){
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constants.COLUMN_CORRECT_VARIANT, cor_var2);
        database.update(Constants.TABLE_NAME, contentValues, Constants.COLUMN_CORRECT_VARIANT+" = '"+cor_var1+"'", null);
    }

    public void updateImage(String uri1, String uri2){
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constants.COLUMN_URI, uri2);
        database.update(Constants.TABLE_NAME, contentValues, Constants.COLUMN_URI+" = '"+uri1+"'", null);
    }

    public void updatePoints(String points1, String points2){
        Log.d("up", points1+" "+points2);
        Log.d("Success", "false");
        boolean success = false;
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constants.COLUMN_POINTS, points2);
        database.update(Constants.TABLE_NAME, contentValues, Constants.COLUMN_POINTS+" = '"+points1+"'", null);
    }

    //delete in COLUMN_TEST_NAME
    public boolean delete(String name_text){
        Log.d("Success", "false");
        boolean success = false;
        success=database.delete(Constants.TABLE_NAME, Constants.COLUMN_TEST_NAME + " ='" + name_text + "'", null)>0;
        Log.d("Success", ""+success);
        return success;

    }

    //delete in COLUMN_NAME_QUESTION
    public boolean deleteQ(String name_question){
        Log.d("Success", "false");
        boolean success = false;
        success=database.delete(Constants.TABLE_NAME, Constants.COLUMN_NAME_QUESTION + " ='"+name_question+"'", null)>0;
        Log.d("Success", ""+success);
        return success;
    }

    public void deleteTextQ(String text_question){
        database.delete(Constants.TABLE_NAME, Constants.COLUMN_TEXT_QUESTION+" ='"+text_question+"'", null);
    }

    public void deleteString(String name_question){
        database.delete(Constants.TABLE_NAME, Constants.COLUMN_NAME_QUESTION+" = ?", new String[] {String.valueOf(name_question)});
    }

    public void closeDb(){
        dbHelper.close();
    }
}
