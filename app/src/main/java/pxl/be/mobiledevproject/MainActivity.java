package pxl.be.mobiledevproject;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import pxl.be.mobiledevproject.adapter.TrainingAdapter;
import pxl.be.mobiledevproject.models.Training;
import pxl.be.mobiledevproject.viewmodel.TrainingViewModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int ADD_TRAINING_REQUEST = 1;

    private DrawerLayout drawer;
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
            //new LoadTrainingsTask().execute(this);
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MessageFragment.newInstance(this)).commit();
            navigationView.setCheckedItem(R.id.nav_members);
        }

        FloatingActionButton buttonAddTraining = findViewById(R.id.button_add_training);
        buttonAddTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTrainingActivity.class);
                startActivityForResult(intent, ADD_TRAINING_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        TrainingAdapter adapter = new TrainingAdapter();
        recyclerView.setAdapter(adapter);

        trainingViewModel = ViewModelProviders.of(this).get(TrainingViewModel.class);
        trainingViewModel.getAllTrainings().observe(this, new Observer<List<Training>>() {
            @Override
            public void onChanged(@Nullable List<Training> trainings) {
                adapter.setTrainings(trainings);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                trainingViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Training deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TRAINING_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddTrainingActivity.EXTRA_TITLE);
            String necessities = data.getStringExtra(AddTrainingActivity.EXTRA_NECESSITIES);
            String location = data.getStringExtra(AddTrainingActivity.EXTRA_LOCATION);
            String date = data.getStringExtra(AddTrainingActivity.EXTRA_DATE);

            //TODO: IM FUCKING STUPID AND FORGET TO ADD ISADULT == HARDCODE FIX
            Training training = new Training(date, necessities, location, title, true);
            trainingViewModel.insert(training);

            Toast.makeText(this, "Training saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Training cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            /*case R.id.nav_trainings:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, MessageFragment.newInstance(this)).commit();
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
                */
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
}
