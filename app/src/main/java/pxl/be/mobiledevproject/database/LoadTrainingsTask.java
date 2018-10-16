package pxl.be.mobiledevproject.database;

import android.content.Context;
import android.os.AsyncTask;

import java.time.LocalDateTime;

import butterknife.ButterKnife;
import pxl.be.mobiledevproject.models.Training;

public class LoadTrainingsTask extends AsyncTask<Context, Integer, Boolean> {
    private DatabaseHelper databaseHelper;

    @Override
    protected Boolean doInBackground(Context... context) {
        databaseHelper = new DatabaseHelper(context[0]);

        Training training = new Training(1, LocalDateTime.now().toString(), "WatMeerBenodigdheden", "Sporthal Heppen", "Training 1", 1);
        Training training2 = new Training(2, LocalDateTime.now().toString(), "dasdasdasdas", "Sporthal KAMP", "Training 2", 0);

        databaseHelper.insertTraining(training);
        databaseHelper.insertTraining(training2);

        databaseHelper.close();
        return true;
    }

}
