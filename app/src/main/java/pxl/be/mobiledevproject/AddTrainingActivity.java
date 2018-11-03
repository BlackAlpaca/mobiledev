package pxl.be.mobiledevproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    private EditText editTextTitle;
    private EditText editTextNecessities;
    private EditText editTextLocation;
    private CalendarView calendarView;
    private long selectedDate;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextNecessities = findViewById(R.id.edit_text_necessities);
        editTextLocation = findViewById(R.id.edit_text_location);
        calendarView = findViewById(R.id.calendar_view_create);



        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Training");

        //show the selected date as a toast
        calendarView.setOnDateChangeListener((view, year, month, day) -> {
            Calendar c = Calendar.getInstance();
            c.set(year, month , day);
            selectedDate =  c.getTimeInMillis(); //this is what you want to use later
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
        
        if (title.trim().isEmpty() || necessities.trim().isEmpty() || location.trim().isEmpty()){
            Toast.makeText(this, "Please insert all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_NECESSITIES, necessities);
        data.putExtra(EXTRA_LOCATION, location);
        data.putExtra(EXTRA_DATE, date);

        RequestHandler.postTrainingsData(this,title, necessities, location, date);

        setResult(RESULT_OK, data);
        finish();
    }




}
