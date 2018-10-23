package pxl.be.mobiledevproject.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import pxl.be.mobiledevproject.dao.TrainingDao;
import pxl.be.mobiledevproject.models.Training;

@Database(entities = {Training.class}, version = 1)
public abstract class TrainingDatabase extends RoomDatabase {

    private static TrainingDatabase instance;

    public abstract TrainingDao trainingDao();

    // Only 1 thread has access
    public static synchronized TrainingDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                                                TrainingDatabase.class, "training_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private TrainingDao trainingDao;

        public PopulateDbAsyncTask(TrainingDatabase db) {
            trainingDao = db.trainingDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            LocalDateTime now = LocalDateTime.now();
            String dateTime = String.format("%s/%s/%s", now.getDayOfMonth(), now.getMonthValue(), now.getYear());
            trainingDao.insert(
                    new Training(
                            dateTime,
                            "Schoenen",
                            "Sporthalleke",
                            "doodgaan voor de fun",
                            true));
            trainingDao.insert(
                    new Training(
                            dateTime,
                            "aardappelen",
                            "buiten",
                            "tis koud",
                            true));
            return null;
        }
    }
}
