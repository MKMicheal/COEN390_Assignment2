package com.example.coen390_assignmen2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private Context context =null;

    public DatabaseHelper(@Nullable Context context) {
        super(context, Config.DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create student table
        String CREATE_STUDENT_TABLE = "CREATE TABLE " + Config.TABLE_STUDENT
                + " ("
                + Config.COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY,"
                + Config.COLUMN_STUDENT_SURNAME + " TEXT NOT NULL,"
                + Config.COLUMN_STUDENT_NAME + " TEXT NOT NULL,"
                + Config.COLUMN_STUDENT_GPA + " TEXT NOT NULL,"
                + Config.COLUMN_STUDENT_CREATION_DATE + " TEXT NOT NULL"
                + ");";
        sqLiteDatabase.execSQL(CREATE_STUDENT_TABLE);
        //create acess table
        String CREATE_ACCESS_TABLE = "CREATE TABLE " + Config.TABLE_ACCESS
                + " ("
                + Config.COLUMN_ACCESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Config.COLUMN_ACCESS_STUDENT_ID + " TEXT NOT NULL,"
                + Config.COLUMN_ACCESS_TYPE + " TEXT NOT NULL,"
                + Config.COLUMN_ACCESS_TIMESTAMP + " TEXT NOT NULL"
                + ");";
        sqLiteDatabase.execSQL(CREATE_ACCESS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_STUDENT+ ";");
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_ACCESS+ ";");
        onCreate(sqLiteDatabase);

    }

    public long insertStudent(Student student)
    {
        long id =-1;


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_STUDENT_ID, student.getId());
        contentValues.put(Config.COLUMN_STUDENT_SURNAME, student.getSurname());
        contentValues.put(Config.COLUMN_STUDENT_NAME, student.getName());
        contentValues.put(Config.COLUMN_STUDENT_GPA, student.getGPA());
        contentValues.put(Config.COLUMN_STUDENT_CREATION_DATE,student.getCreationDate());

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(Config.COLUMN_ACCESS_STUDENT_ID,student.getId());
        contentValues1.put(Config.COLUMN_ACCESS_TYPE,"created");
        contentValues1.put(Config.COLUMN_ACCESS_TIMESTAMP, student.getCreationDate());

        try{
            id=db.insertOrThrow(Config.TABLE_STUDENT, null, contentValues);
            db.insertOrThrow(Config.TABLE_ACCESS,null,contentValues1);
        }
        catch (SQLException e)
        {
            Toast.makeText(context, "DB error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            db.close();
        }
        return id;
    }
    public List<Student> getAllStudents()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =null;
        List<Student> studentList = new ArrayList<>();

        try {
            cursor = db.query(Config.TABLE_STUDENT,null,null,null,null,null,null);
            if(cursor != null)
                if(cursor.moveToFirst())
                {
                    do {
                        @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_STUDENT_ID));
                        @SuppressLint("Range") String surname = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_SURNAME));
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_NAME));
                        @SuppressLint("Range") double gpa = cursor.getDouble(cursor.getColumnIndex(Config.COLUMN_STUDENT_GPA));
                        @SuppressLint("Range") String creationdate = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_CREATION_DATE));

                        studentList.add(new Student(id,surname,name,gpa,creationdate));
                    }while (cursor.moveToNext());
                }
        }catch (Exception e){
            Toast.makeText(context, "DB error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
        }
        return studentList;
    }
    public List<Access> getAllAccess()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =null;
        List<Access> accessList = new ArrayList<>();
        try {
            cursor = db.query(Config.TABLE_ACCESS,null,null,null,null,null,null);
            if(cursor != null)
                if(cursor.moveToFirst())
                {
                    do {
                        @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_ACCESS_ID));
                        @SuppressLint("Range") String studentId = cursor.getString(cursor.getColumnIndex(Config.COLUMN_ACCESS_STUDENT_ID));
                        @SuppressLint("Range") String access_type = cursor.getString(cursor.getColumnIndex(Config.COLUMN_ACCESS_TYPE));
                        @SuppressLint("Range") String timestamp = cursor.getString(cursor.getColumnIndex(Config.COLUMN_ACCESS_TIMESTAMP));


                        accessList.add(new Access(id,studentId,access_type,timestamp));
                    }while (cursor.moveToNext());
                }
        }catch (Exception e){
            Toast.makeText(context, "DB error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
        }
        return accessList;
    }
    public void insertAccess(Access access)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(Config.COLUMN_ACCESS_STUDENT_ID,access.getId());
        contentValues1.put(Config.COLUMN_ACCESS_TYPE,access.getaccessType());
        contentValues1.put(Config.COLUMN_ACCESS_TIMESTAMP, access.getTimeStamp());
        try{

            db.insertOrThrow(Config.TABLE_ACCESS,null,contentValues1);
        }
        catch (SQLException e)
        {
            Toast.makeText(context, "DB error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            db.close();
        }
    }
    public void deleteStudent(long id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Config.TABLE_STUDENT,Config.COLUMN_STUDENT_ID+"="+ Long.toString(id), null);

    }

}
