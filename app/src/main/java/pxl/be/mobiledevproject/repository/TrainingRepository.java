package pxl.be.mobiledevproject.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import pxl.be.mobiledevproject.dao.TrainingDao;
import pxl.be.mobiledevproject.database.TrainingDatabase;
import pxl.be.mobiledevproject.models.Training;

public class TrainingRepository {
    private TrainingDao trainingDao;
    private LiveData<List<Training>> allTrainings;

    public TrainingRepository(Application application) {
        TrainingDatabase database = TrainingDatabase.getInstance(application);
        trainingDao = database.trainingDao();
        allTrainings = trainingDao.getAllTrainings();
    }

    public void insert(Training training){
        new InsertTrainingAsyncTask(trainingDao).execute(training);
    }


    public void update(Training training){
        new UpdateTrainingAsyncTask(trainingDao).execute(training);

    }


    public void delete(Training training){
        new DeleteTrainingAsyncTask(trainingDao).execute(training);
    }

    public void deleteAllTrainings(){
        new DeleteAllTrainingAsyncTask(trainingDao).execute();
    }

    public LiveData<List<Training>> getAllTrainings() {
        return allTrainings;
    }

    private static class InsertTrainingAsyncTask extends AsyncTask<Training, Void, Void> {
        private TrainingDao trainingDao;

        public InsertTrainingAsyncTask(TrainingDao trainingDao) {
            this.trainingDao = trainingDao;
        }

        @Override
        protected Void doInBackground(Training... trainings) {
            trainingDao.insert(trainings[0]);
            return null;
        }
    }

    private static class UpdateTrainingAsyncTask extends AsyncTask<Training, Void, Void> {
        private TrainingDao trainingDao;

        public UpdateTrainingAsyncTask(TrainingDao trainingDao) {
            this.trainingDao = trainingDao;
        }

        @Override
        protected Void doInBackground(Training... trainings) {
            trainingDao.update(trainings[0]);
            return null;
        }
    }

    private static class DeleteTrainingAsyncTask extends AsyncTask<Training, Void, Void> {
        private TrainingDao trainingDao;

        public DeleteTrainingAsyncTask(TrainingDao trainingDao) {
            this.trainingDao = trainingDao;
        }

        @Override
        protected Void doInBackground(Training... trainings) {
            trainingDao.delete(trainings[0]);
            return null;
        }
    }


    private static class DeleteAllTrainingAsyncTask extends AsyncTask<Void, Void, Void> {
        private TrainingDao trainingDao;

        public DeleteAllTrainingAsyncTask(TrainingDao trainingDao) {
            this.trainingDao = trainingDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            trainingDao.deleteAllTrainings();
            return null;
        }
    }
}
