package pxl.be.mobiledevproject;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pxl.be.mobiledevproject.database.DatabaseHelper;
import pxl.be.mobiledevproject.models.Training;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    @BindView(R.id.helloWorld) TextView helloWorldView;

    @BindView(R.id.button)
    Button testButton;

    private  List<Training> trainingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        ButterKnife.bind(this);

        Training training = new Training(1, LocalDateTime.now().toString(), "WatMeerBenodigdheteden", "Sporthal Heppe", "Training 1", 1);
        Training training2 = new Training(2, LocalDateTime.now().toString(), "dasdasdasdas", "Sporthal KAMP", "Training 2", 0);

        long id1 = databaseHelper.insertTraining(training);
        long id2 = databaseHelper.insertTraining(training2);


        getData();

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helloWorldView.setText("");
            }
        });
    }



    private void getData(){
        helloWorldView.setText("");
        trainingList = databaseHelper.getAllTrainings();
        for (Training training: trainingList) {
            helloWorldView.append(String.format("Id: %d, Title: %s, IsAdult: %b \n", training.getId(), training.getTitle() , training.isAdult()));
        }
    }

}
