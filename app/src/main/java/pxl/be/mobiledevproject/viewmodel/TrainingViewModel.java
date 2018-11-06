package pxl.be.mobiledevproject.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import pxl.be.mobiledevproject.models.Training;
import pxl.be.mobiledevproject.repository.TrainingRepository;

public class TrainingViewModel extends AndroidViewModel {
    private TrainingRepository repository;
    private LiveData<List<Training>> allTrainings;

    public TrainingViewModel(@NonNull Application application) {
        super(application);
        repository = new TrainingRepository(application);
        allTrainings = repository.getAllTrainings();
    }

    public void insert(Training training) {
        repository.insert(training);
    }


    public void update(Training training) {
        repository.update(training);
    }


    public void delete(Training training) {
        repository.delete(training);
    }


    public void deleteAllTrainings() {
        repository.deleteAllTrainings();
    }

    public LiveData<List<Training>> getAllTrainings() {
        return allTrainings;
    }
}
