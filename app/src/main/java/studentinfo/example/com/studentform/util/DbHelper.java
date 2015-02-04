package studentinfo.example.com.studentform.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sarthak on 04/02/2015.
 */
public class DbHelper extends SQLiteOpenHelper implements DbConstants {

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + STUDENT_ROLL + " INT PRIMARY KEY," +
                    STUDENT_NAME + " TEXT NOT NULL," + STUDENT_PHONE + " TEXT NOT NULL," + STUDENT_ADDRESS + " TEXT NOT NULL);");
        }catch (Exception e){
            Log.d("ex",e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
}
