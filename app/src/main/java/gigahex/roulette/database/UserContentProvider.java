package gigahex.roulette.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class UserContentProvider extends ContentProvider {

    private DatabaseHelper mOpenHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    public static final int USERS = 100;
    public static final int USERS_ID = 101;

    private static UriMatcher buildUriMatcher(){

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(ResultPoints.CONTENT_AUTHORITY, ResultPoints.TABLE_NAME, USERS);

        matcher.addURI(ResultPoints.CONTENT_AUTHORITY, ResultPoints.TABLE_NAME + "/#", USERS_ID);
        return matcher;
    }

    public UserContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        String selectionCriteria = selection;

        if(match != USERS && match != USERS_ID)
            throw new IllegalArgumentException("Unknown URI: "+ uri);

        if(match==USERS_ID) {
            long taskId = ResultPoints.getUserId(uri);
            selectionCriteria = ResultPoints.Columns.COLUMN_ID + " = " + taskId;
            if ((selection != null) && (selection.length() > 0)) {
                selectionCriteria += " AND (" + selection + ")";
            }
        }
        return db.delete(ResultPoints.TABLE_NAME, selectionCriteria, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch(match){
            case USERS:
                return ResultPoints.CONTENT_TYPE;
            case USERS_ID:
                return ResultPoints.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI: "+ uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db;
        Uri returnUri;
        long recordId;

        switch(match){
            case USERS:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(ResultPoints.TABLE_NAME, null, values);
                if(recordId > 0){
                    returnUri = ResultPoints.buildUserUri(recordId);
                }
                else{
                    throw new android.database.SQLException("Failed to insert: " + uri.toString());
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: "+ uri);
        }
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = DatabaseHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final int match = sUriMatcher.match(uri);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch(match){
            case USERS:
                queryBuilder.setTables(ResultPoints.TABLE_NAME);
                break;
            case USERS_ID:
                queryBuilder.setTables(ResultPoints.TABLE_NAME);
                long taskId = ResultPoints.getUserId(uri);
                queryBuilder.appendWhere(ResultPoints.Columns.COLUMN_ID + " = " + taskId);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: "+ uri);
        }
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        return queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        String selectionCriteria = selection;

        if(match != USERS && match != USERS_ID)
            throw new IllegalArgumentException("Unknown URI: "+ uri);

        if(match==USERS_ID) {
            long taskId = ResultPoints.getUserId(uri);
            selectionCriteria = ResultPoints.Columns.COLUMN_ID + " = " + taskId;
            if ((selection != null) && (selection.length() > 0)) {
                selectionCriteria += " AND (" + selection + ")";
                Log.d("MYTAG", selectionCriteria + " privet");
            }
        }
        return db.update(ResultPoints.TABLE_NAME, values, selectionCriteria, selectionArgs);
    }
}
