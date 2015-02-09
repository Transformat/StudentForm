package studentinfo.example.com.studentform.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import studentinfo.example.com.studentform.entities.Student;


public class DbController implements DbConstants {

    Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DbController(Context context) {
        this.context = context;
    }

    public void dbOpen() {
        dbHelper = new DbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public void dbClose() {
        db.close();
    }

    public long dbInsert(Student student) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(STUDENT_ROLL, Integer.parseInt(student.rollno));
            cv.put(STUDENT_NAME, student.name);
            cv.put(STUDENT_PHONE, student.phoneNumber);
            cv.put(STUDENT_ADDRESS, student.address);
            return db.insert("student_info", null, cv);
        } catch (Exception e) {

        }

        return 0;
    }

    public List<Student> dbView() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + "student_info", null);
        List<Student> allStudents = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                allStudents.add(new Student(cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("roll_no")),
                        cursor.getString(cursor.getColumnIndex("phone_number")),
                        cursor.getString(cursor.getColumnIndex("student_address"))
                ));
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());


        }

        return allStudents;
    }

    public int dbDelete(Student student) {

        return db.delete("student_info", "roll_no =?", new String[]{String.valueOf(student.rollno)});

    }


    public void dbUpdate(Student student) {
        ContentValues cv = new ContentValues();
        cv.put(STUDENT_ROLL, Integer.parseInt(student.rollno));
        cv.put(STUDENT_NAME, student.name);
        cv.put(STUDENT_PHONE, student.phoneNumber);
        cv.put(STUDENT_ADDRESS, student.address);
        db.update("student_info", cv, STUDENT_ROLL + "=?", new String[]{String.valueOf(student.rollno)});

    }

}
