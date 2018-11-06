package pxl.be.mobiledevproject.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import pxl.be.mobiledevproject.dao.TrainingDao;
import pxl.be.mobiledevproject.models.Training;

@Database(entities = {Training.class}, version = 1)
public abstract class TrainingDatabase extends RoomDatabase {

    private static TrainingDatabase instance;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    // Only 1 thread has access
    public static synchronized TrainingDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TrainingDatabase.class, "training_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    public abstract TrainingDao trainingDao();

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TrainingDao trainingDao;

        private PopulateDbAsyncTask(TrainingDatabase db) {
            trainingDao = db.trainingDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
