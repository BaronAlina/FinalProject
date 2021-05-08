package com.example.finalproject;

public class Constants {
    public static final String DATABASE_NAME = "test.db";
    public static final int DATABASE_VERSION = 9;
    public static final String TABLE_NAME = "question";

    //названия столбцов
    public static final String COLUMN_ID="_id";//передать INTEGER
    public static final String COLUMN_TEST_NAME = "test";
    public static final String COLUMN_NAME_QUESTION = "name"; //передать TEXT
    public static final String COLUMN_TEXT_QUESTION = "text"; // передать TEXT
    public static final String COLUMN_VARIANT = "variant"; //передать INTEGER
    public static final String COLUMN_CORRECT_VARIANT = "correct_variant";//преобразовать ArrayList<String> correct_variant в String, его отправить в TEXT
    public static final String COLUMN_ALL_VARIANTS = "all_variants";//преобразовать ArrayList<String> all_variants в String, его в TEXT
    public static final String COLUMN_POINTS = "points";//передать TEXT или INTEGER
    public static final String COLUMN_URI="uri";//преобразовать в String, передать TEXT

    public static final int NUM_COLUMN_ID = 0;
    public static final int NUM_COLUMN_NAME_QUESTION = 1;
    public static final int NUM_COLUMN_TEXT_QUESTION = 2;
    public static final int NUM_COLUMN_VARIANT = 3;
    public static final int NUM_COLUMN_CORRECT_VARIANT = 4;
    public static final int NUM_COLUMN_ALL_VARIANTS = 5;
    public static final int NUM_COLUMN_POINTS = 6;
    public static final int NUM_COLUMN_URI = 7;

    public static final String TABLE_STRUCTURE="CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " ("+
            COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_TEST_NAME + " TEXT, " +
            COLUMN_NAME_QUESTION + " TEXT, " +
            COLUMN_TEXT_QUESTION + " TEXT, " +
            COLUMN_VARIANT + " INTEGER, " +
            COLUMN_CORRECT_VARIANT + " TEXT, " +
            COLUMN_ALL_VARIANTS + " TEXT, " +
            COLUMN_POINTS + " TEXT, " +
            COLUMN_URI + " TEXT);";

    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
