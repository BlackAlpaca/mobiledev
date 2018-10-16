package pxl.be.mobiledevproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public LoginFragment() {
    }


    public static LoginFragment newInstance() {
        return new LoginFragment();
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
        String name = loginName.getText().toString();

        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.username),name);
        editor.apply();
    }
}
