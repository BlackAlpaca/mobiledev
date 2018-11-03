package pxl.be.mobiledevproject;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pxl.be.mobiledevproject.models.Training;
import pxl.be.mobiledevproject.viewmodel.TrainingViewModel;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private RequestQueue requestQueue;
    private TrainingViewModel trainingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
           navigationView.setCheckedItem(R.id.nav_members);

            trainingViewModel = ViewModelProviders.of(this).get(TrainingViewModel.class);
            trainingViewModel.deleteAllTrainings();

            requestQueue = Volley.newRequestQueue(this);
            getTrainingsData();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_trainings:
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Training_management()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,
                                LoginFragment.newInstance()).commit();
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Share clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_calendar:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,CalendarOverview.newInstance())
                        .commit();
                       
                break;
            case R.id.nav_accelerometer:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, CalculateSpeed.newInstance()).commit();

        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void getTrainingsData(){
        String url = "https://api.myjson.com/bins/1g0gae";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null
                , response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("trainings");

                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject training = jsonArray.getJSONObject(i);

                    int id = training.getInt("id");
                    String localDateTime = training.getString("localDateTime");
                    String necessities = training.getString("necessities");
                    String location = training.getString("location");
                    String title = training.getString("title");
                    String isAdult = training.getString("isAdult");

                    trainingViewModel.insert(new Training( localDateTime, necessities, location, title, Boolean.valueOf(isAdult) ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        requestQueue.add(request);
    }


}
