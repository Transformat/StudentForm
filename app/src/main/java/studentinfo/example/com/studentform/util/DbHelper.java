package studentinfo.example.com.studentform.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sarthak on 04/02/2015.
 */
public class DbHelper extends SQLiteOpenHelper implements DbConstants {
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        version=DATABASE_VERSION;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+"("+STUDENT_ROLL+" INT PRIMARY KEY,"+STUDENT_NAME+" TEXT NOT NULL,"+STUDENT_PHONE+" TEXT NOT NULL,"+STUDENT_ADDRESS+"TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+TABLE_NAME+" IF EXISTS");
    }
}
