package pxl.be.mobiledevproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class trainingDetailFragment extends Fragment {

    public trainingDetailFragment() {
        // Required empty public constructor
    }

    public static trainingDetailFragment newInstance() {
        trainingDetailFragment fragment = new trainingDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_training_detail, container, false);
    }
}
