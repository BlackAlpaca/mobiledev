package pxl.be.mobiledevproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pxl.be.mobiledevproject.database.DatabaseHelper;
import pxl.be.mobiledevproject.models.Training;

public class MessageFragment extends Fragment {

    DatabaseHelper databaseHelper;
    Context context;

    public MessageFragment() {
    }

    @BindView(R.id.textViewTrainings)
    TextView textViewTrainings;

    private Unbinder unbinder;

    public static MessageFragment newInstance(Context context) {
        MessageFragment fragment = new MessageFragment();

        fragment.setContext(context);
        return fragment;
    }

    private void setContext(Context context) {
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message, container, false);

        unbinder = ButterKnife.bind(this, view);

        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getPreferences(Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(getString(R.string.username), "John Doe");
        textViewTrainings.setText("Hello " + name + "\n");


        databaseHelper = new DatabaseHelper(context);

        for (Training t: databaseHelper.getAllTrainings()) {
            textViewTrainings.append("\n");
            textViewTrainings.append("Training:" + t.getTitle() + "Location: " + t.getLocation());
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
