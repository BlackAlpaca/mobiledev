package pxl.be.mobiledevproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pxl.be.mobiledevproject.database.RequestHandler;

public class AddTrainingActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE =
            "pxl.be.mobiledevproject.EXTRA_TITLE";
    public static final String EXTRA_NECESSITIES =
            "pxl.be.mobiledevproject.EXTRA_NECESSITIES";
    public static final String EXTRA_LOCATION =
            "pxl.be.mobiledevproject.EXTRA_LOCATION";
    public static final String EXTRA_DATE =
            "pxl.be.mobiledevproject.EXTRA_DATE";
    public static final String EXTRA_ISADULT =
            "pxl.be.mobiledevproject.EXTRA_ISADULT";

    private EditText editTextTitle;
    private EditText editTextNecessities;
    private EditText editTextLocation;
    private CalendarView calendarView;
    private Switch switchIsAdult;
    private long selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextNecessities = findViewById(R.id.edit_text_necessities);
        editTextLocation = findViewById(R.id.edit_text_location);
        calendarView = findViewById(R.id.calendar_view_create);
        switchIsAdult = findViewById(R.id.swIsAdult);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Training");

        //show the selected date as a toast
        calendarView.setOnDateChangeListener((view, year, month, day) -> {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            selectedDate = c.getTimeInMillis(); //this is what you want to use later
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_training_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_training:
                saveTraining();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveTraining() {
        String title = editTextTitle.getText().toString();
        String necessities = editTextNecessities.getText().toString();
        String location = editTextLocation.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date(selectedDate));
        boolean isAdult = switchIsAdult.isChecked();

        if (title.trim().isEmpty() || necessities.trim().isEmpty() || location.trim().isEmpty()) {
            Toast.makeText(this, "Please insert all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_NECESSITIES, necessities);
        data.putExtra(EXTRA_LOCATION, location);
        data.putExtra(EXTRA_DATE, date);
        data.putExtra(EXTRA_ISADULT, isAdult);

        RequestHandler.postTrainingsData(this, title, necessities, location, date, isAdult);

        setResult(RESULT_OK, data);
        finish();
    }
}
