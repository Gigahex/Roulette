package gigahex.roulette.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userstore.db";
    public static final int DATABASE_VERSION = 1;

    private static DatabaseHelper databaseHelper = null;

    private DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static DatabaseHelper getInstance(Context context){
        if(databaseHelper == null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + ResultPoints.TABLE_NAME + "(" +
                ResultPoints.Columns.COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                ResultPoints.Columns.COLUMN_NAME + " TEXT NOT NULL, " +
                ResultPoints.Columns.COLUMN_POINTS + " INTEGER)";

        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ResultPoints.TABLE_NAME);
        onCreate(db);
    }
}
