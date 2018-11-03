package pxl.be.mobiledevproject;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pxl.be.mobiledevproject.adapter.TrainingAdapter;
import pxl.be.mobiledevproject.database.TrainingDatabase;
import pxl.be.mobiledevproject.models.Training;
import pxl.be.mobiledevproject.viewmodel.TrainingViewModel;


public class Training_management extends Fragment {

    public static final int ADD_TRAINING_REQUEST = 1;
    private TrainingViewModel trainingViewModel;


    @BindView(R.id.button_add_training)
    FloatingActionButton buttonAddTraining;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Unbinder unbinder;


    public Training_management() {
        // Required empty public constructor
    }


    public static Training_management newInstance() {
        return new Training_management();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        FloatingActionButton buttonAddTraining = this.buttonAddTraining;
        buttonAddTraining.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddTrainingActivity.class);
            startActivityForResult(intent, ADD_TRAINING_REQUEST);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        TrainingAdapter adapter = new TrainingAdapter();
        recyclerView.setAdapter(adapter);

        trainingViewModel = ViewModelProviders.of(this).get(TrainingViewModel.class);
        trainingViewModel.getAllTrainings().observe(this, adapter::setTrainings);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                trainingViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Training deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        getActivity();
        if (requestCode == ADD_TRAINING_REQUEST && resultCode == Activity.RESULT_OK){
            String title = data.getStringExtra(AddTrainingActivity.EXTRA_TITLE);
            String necessities = data.getStringExtra(AddTrainingActivity.EXTRA_NECESSITIES);
            String location = data.getStringExtra(AddTrainingActivity.EXTRA_LOCATION);
            String date = data.getStringExtra(AddTrainingActivity.EXTRA_DATE);

            //TODO: IM F***ING STUPID AND FORGET TO ADD ISADULT == HARDCODE FIX
            Training training = new Training(date, necessities, location, title, true);
            trainingViewModel.insert(training);

            Toast.makeText(getActivity(), "Training saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Training cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_training_management, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



}
