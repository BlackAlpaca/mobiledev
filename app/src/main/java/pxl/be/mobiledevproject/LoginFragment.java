package pxl.be.mobiledevproject;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.time.LocalDateTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pxl.be.mobiledevproject.database.DatabaseHelper;
import pxl.be.mobiledevproject.models.Training;


public class LoginFragment extends Fragment {
    @BindView(R.id.loginName)
    EditText loginName;

    private Unbinder unbinder;
    private Context context;
    private NavigationView navigationView;
    private DatabaseHelper databaseHelper;


    public LoginFragment() {
    }


    public static LoginFragment newInstance(NavigationView navigationView, Context context) {
        LoginFragment fragment = new LoginFragment();
        fragment.setNavigationView(navigationView);
        fragment.setContext(context);
        return fragment;

    }

    private void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnLogin)
    public void onButtonPressed() {
        databaseHelper = new DatabaseHelper(context);

        Training training = new Training(1, LocalDateTime.now().toString(), "WatMeerBenodigdheden", "Sporthal Heppen", loginName.getText().toString(), 1);

        long id = databaseHelper.insertTraining(training);



        loginName.setText(databaseHelper.getTraining(id).getLocation());

        databaseHelper.close();
    }


    private void setNavigationView(NavigationView navigationView) {
        this.navigationView = navigationView;
    }
}
