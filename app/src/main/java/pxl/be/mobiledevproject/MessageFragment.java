package pxl.be.mobiledevproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import pxl.be.mobiledevproject.database.DatabaseHelper;
import pxl.be.mobiledevproject.models.Training;

public class MessageFragment extends Fragment {


    private Context context;
    private DatabaseHelper databaseHelper;
    private List<Training> trainings;

    @BindView(R.id.textViewTrainings)
    TextView textViewTrainings;

    public static MessageFragment newInstance(Context context) {
        MessageFragment fragment = new MessageFragment();
        fragment.setContext(context);

        return fragment;

    }

    private void seedData() {
        databaseHelper = new DatabaseHelper(context);

        StringBuilder data = new StringBuilder();

        for (Training t: databaseHelper.getAllTrainings()) {
           trainings.add(t);
        }
    }

    private void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && trainings == null ) {
            seedData();
        }

        for (Training t: trainings) {
            textViewTrainings.append(t.getId() + " " + t.getLocation() + " " + t.getTitle());
        }
    }
}
