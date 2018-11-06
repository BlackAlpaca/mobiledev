package pxl.be.mobiledevproject;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pxl.be.mobiledevproject.adapter.TrainingAdapter;
import pxl.be.mobiledevproject.database.RequestHandler;
import pxl.be.mobiledevproject.models.JarnacRole;
import pxl.be.mobiledevproject.models.Training;
import pxl.be.mobiledevproject.viewmodel.TrainingViewModel;

public class Training_management extends Fragment {

    public static final int ADD_TRAINING_REQUEST = 1;
    @BindView(R.id.button_add_training)
    FloatingActionButton buttonAddTraining;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private TrainingViewModel trainingViewModel;
    private TrainingAdapter mAdapter;

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


        RequestHandler.getTrainingsData(getActivity(), getContext());

        FloatingActionButton buttonAddTraining = this.buttonAddTraining;
        buttonAddTraining.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getPreferences(Context.MODE_PRIVATE);
            JarnacRole role = JarnacRole.valueOf(sharedPreferences.getString(getString(R.string.jarnacRole), JarnacRole.MEMBER.toString()));

            if (role == JarnacRole.TRAINER){
                Intent intent = new Intent(getActivity(), AddTrainingActivity.class);
                startActivityForResult(intent, ADD_TRAINING_REQUEST);
            } else {
                Toast.makeText(this.getContext(), getString(R.string.noPermissionMakeTraining), Toast.LENGTH_SHORT).show();
            }


        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        mAdapter = new TrainingAdapter();
        recyclerView.setAdapter(mAdapter);

        trainingViewModel = ViewModelProviders.of(this).get(TrainingViewModel.class);
        trainingViewModel.getAllTrainings().observe(this, mAdapter::setTrainings);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int pos = viewHolder.getAdapterPosition();
                Training selected = mAdapter.getNoteAt(pos);
                TextView itemDetailNecessities = ((ViewGroup) viewHolder.itemView.getParent()).getChildAt(pos).findViewById(R.id.text_view_necessities);
                TextView itemDetailLocation = ((ViewGroup) viewHolder.itemView.getParent()).getChildAt(pos).findViewById(R.id.text_view_location);
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                    mAdapter.notifyDataSetChanged();
//                    ViewGroup row = (ViewGroup) viewHolder.itemView.getParent();
//                    for (int itemPos = 0; itemPos < row.getChildCount(); itemPos++) {
//                        View view = row.getChildAt(itemPos);
//                        view.findViewById(R.id.text_view_location).setVisibility(View.VISIBLE);
//                        view.findViewById(R.id.text_view_necessities).setVisibility(View.VISIBLE);
//                    }

                    mAdapter.notifyDataSetChanged();
                    // In landscape
                    itemDetailNecessities.setVisibility(View.VISIBLE);
                    itemDetailNecessities.setText(selected.getNecessities());
                    itemDetailLocation.setVisibility(View.VISIBLE);
                    itemDetailLocation.setText(selected.getLocation());
                } else {
                    // In portrait
                    mAdapter.notifyDataSetChanged();
                    itemDetailNecessities.setVisibility(View.INVISIBLE);
                    itemDetailLocation.setVisibility(View.INVISIBLE);

                    String dataToSend = String.format("Necessities: \n %s \n \n Location: %s", selected.getNecessities(), selected.getLocation());

                    Class destinationActivity = DetailActivity.class;
                    Intent startChildActivityIntent = new Intent(itemDetailNecessities.getContext(), destinationActivity);
                    startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, dataToSend);
                    startActivity(startChildActivityIntent, null);
                }
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        getActivity();
        if (requestCode == ADD_TRAINING_REQUEST && resultCode == Activity.RESULT_OK) {
            String title = data.getStringExtra(AddTrainingActivity.EXTRA_TITLE);
            String necessities = data.getStringExtra(AddTrainingActivity.EXTRA_NECESSITIES);
            String location = data.getStringExtra(AddTrainingActivity.EXTRA_LOCATION);
            String date = data.getStringExtra(AddTrainingActivity.EXTRA_DATE);
            boolean isAdult = Boolean.valueOf(data.getStringExtra(AddTrainingActivity.EXTRA_ISADULT));

            //TODO: IM F***ING STUPID AND FORGET TO ADD ISADULT == HARDCODE FIX
            Training training = new Training(date, necessities, location, title, isAdult);
            trainingViewModel.insert(training);

            RequestHandler.getTrainingsData(getActivity(), getActivity());

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
