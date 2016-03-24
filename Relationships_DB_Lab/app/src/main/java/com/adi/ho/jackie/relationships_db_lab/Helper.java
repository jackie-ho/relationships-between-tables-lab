package com.adi.ho.jackie.relationships_db_lab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


public class Helper extends SQLiteOpenHelper {

    public Helper(Context context) {
        super(context, "db", null, 1);
    }

    private static Helper INSTANCE;

    public static synchronized Helper getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new Helper(context.getApplicationContext());
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_COMPANY);
        db.execSQL(SQL_CREATE_ENTRIES_DEPARTMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_COMPANY);
        db.execSQL(SQL_DELETE_ENTRIES_DEPARTMENT);
        onCreate(db);
    }

    public static abstract class DataEntryCompany implements BaseColumns {
        public static final String TABLE_NAME = "employee";
        public static final String COLUMN_SSN = "_id";
        public static final String COLUMN_FIRSTNAME = "firstname";
        public static final String COLUMN_LASTNAME = "lastname";
        public static final String COLUMN_YEAROFBIRTH = "yearofbirth";
        public static final String COLUMN_CITY = "city";
    }

    public static abstract class DataEntryDepartment implements BaseColumns {
        public static final String TABLE_NAME = "job";
        public static final String COLUMN_SSN = "_id";
        public static final String COLUMN_COMPANY = "company";
        public static final String COLUMN_SALARY = "salary";
        public static final String COLUMN_EXPERIENCE = "experience";
    }

    private static final String SQL_CREATE_ENTRIES_COMPANY = "CREATE TABLE " +
            DataEntryCompany.TABLE_NAME + " (" +
            DataEntryCompany.COLUMN_SSN + " TEXT PRIMARY KEY," +
            DataEntryCompany.COLUMN_FIRSTNAME + " TEXT," +
            DataEntryCompany.COLUMN_LASTNAME + " TEXT," +
            DataEntryCompany.COLUMN_YEAROFBIRTH + " INTEGER," +
            DataEntryCompany.COLUMN_CITY + " TEXT )";

    private static final String SQL_CREATE_ENTRIES_DEPARTMENT = "CREATE TABLE " +
            DataEntryDepartment.TABLE_NAME + " (" +
            DataEntryDepartment.COLUMN_SSN + " TEXT PRIMARY KEY," +
            DataEntryDepartment.COLUMN_COMPANY + " TEXT," +
            DataEntryDepartment.COLUMN_SALARY + " INTEGER," +
            DataEntryDepartment.COLUMN_EXPERIENCE + " INTEGER" + ")";

    private static final String SQL_DELETE_ENTRIES_COMPANY = "DROP TABLE IF EXISTS " +
            DataEntryCompany.TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES_DEPARTMENT = "DROP TABLE IF EXISTS " +
            DataEntryDepartment.TABLE_NAME;

    public void insertRow(Employee employee) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataEntryCompany.COLUMN_FIRSTNAME, employee.getFirstName());
        values.put(DataEntryCompany.COLUMN_LASTNAME, employee.getLastName());
        values.put(DataEntryCompany.COLUMN_YEAROFBIRTH, employee.getYearOfBirth());
        values.put(DataEntryCompany.COLUMN_CITY, employee.getAddress());
        values.put(DataEntryCompany.COLUMN_SSN, employee.getSsn());
        db.insertOrThrow(DataEntryCompany.TABLE_NAME, null, values);
    }

    public void insertRowDepartment(Job job) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataEntryDepartment.COLUMN_SSN, job.getSsn() );
        values.put(DataEntryDepartment.COLUMN_COMPANY, job.getCompany());
        values.put(DataEntryDepartment.COLUMN_SALARY, job.getSalary());
        values.put(DataEntryDepartment.COLUMN_EXPERIENCE, job.getExperience());
        db.insertOrThrow(DataEntryDepartment.TABLE_NAME, null, values);
    }

    public Cursor companiesInBoston(){
        SQLiteDatabase db = getReadableDatabase();
       Cursor cursor = db.rawQuery("SELECT JOB._id, company FROM job INNER JOIN EMPLOYEE ON EMPLOYEE._id = JOB._id WHERE CITY = ?",new String[]{"Boston"});
        return cursor;
    }
//
    public Cursor employeesInSameCompany(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT EMPLOYEE._id, employee.firstname, employee.lastname FROM EMPLOYEE INNER JOIN JOB ON EMPLOYEE._id = JOB._id WHERE job.company = ?", new String[]{"Macy's"});
        return cursor;
    }

    public String getHighestPaying(){
        SQLiteDatabase db = getReadableDatabase();
        String [] columns = new String[]{DataEntryDepartment.COLUMN_COMPANY, DataEntryDepartment.COLUMN_SALARY};
        Cursor cursor = db.query(DataEntryDepartment.TABLE_NAME, columns, null, null, null, null, DataEntryDepartment.COLUMN_SALARY+" DESC");
        cursor.moveToFirst();
        String highestPaying = cursor.getString(cursor.getColumnIndex(DataEntryDepartment.COLUMN_COMPANY));
        cursor.close();
        return highestPaying;
    }
}

