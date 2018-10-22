package pxl.be.mobiledevproject.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pxl.be.mobiledevproject.models.Training;

/***
 * For more information abouts DAO's go to: https://developer.android.com/training/data-storage/room/accessing-data
 */

@Dao
public interface TrainingDao {

    @Insert
    void insert(Training training);

    @Update
    void update(Training training);

    @Delete
    void delete(Training training);


    @Query("DELETE FROM training")
    void deleteAllTrainings();

    @Query("SELECT * FROM training ORDER BY localDateTime DESC")
    LiveData<List<Training>> getAllTrainings();
}
