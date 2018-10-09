package pxl.be.mobiledevproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import pxl.be.mobiledevproject.models.Training;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Training.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Training.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertTraining() {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them

        //TODO
       // values.put(Note.COLUMN_NOTE, note);

        // insert row
        long id = db.insert(Training.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Training getTraining(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Training.TABLE_NAME,
                new String[]{Training.COLUMN_ID, Training.COLUMN_TITLE, Training.COLUMN_LOCALDATETIME, Training.COLUMN_NECESSITIES, Training.COLUMN_ISADULT},
                Training.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Training training = new Training(
                cursor.getInt(cursor.getColumnIndex(Training.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Training.COLUMN_LOCALDATETIME)),
                cursor.getString(cursor.getColumnIndex(Training.COLUMN_NECESSITIES)),
                cursor.getString(cursor.getColumnIndex(Training.COLUMN_LOCATION)),
                cursor.getString(cursor.getColumnIndex(Training.COLUMN_TITLE)),
                cursor.getInt(cursor.getColumnIndex(Training.COLUMN_ISADULT))
                );

        // close the db connection
        cursor.close();

        return training;
    }

    public List<Training> getAllTrainings() {
        List<Training> trainings = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Training.TABLE_NAME + " ORDER BY " +
                Training.COLUMN_LOCALDATETIME + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Training training = new Training(
                        cursor.getInt(cursor.getColumnIndex(Training.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Training.COLUMN_LOCALDATETIME)),
                        cursor.getString(cursor.getColumnIndex(Training.COLUMN_NECESSITIES)),
                        cursor.getString(cursor.getColumnIndex(Training.COLUMN_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(Training.COLUMN_TITLE)),
                        cursor.getInt(cursor.getColumnIndex(Training.COLUMN_ISADULT))
                );

                trainings.add(training);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return trainings;
    }

    public int getTrainingCount() {
        String countQuery = "SELECT  * FROM " + Training.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateTraining(Training training) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Training.COLUMN_ID, training.getId());

        // updating row
        return db.update(Training.TABLE_NAME, values, Training.COLUMN_ID + " = ?",
                new String[]{String.valueOf(training.getId())});
    }

    public void deleteTraining(Training training) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Training.TABLE_NAME, Training.COLUMN_ID + " = ?",
                new String[]{String.valueOf(training.getId())});
        db.close();
    }
}