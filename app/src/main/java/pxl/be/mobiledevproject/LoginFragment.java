package pxl.be.mobiledevproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pxl.be.mobiledevproject.models.JarnacRole;


public class LoginFragment extends Fragment {
    @BindView(R.id.loginName)
    EditText editTextloginName;

    @BindView(R.id.tvWelcomeUser)
    TextView textViewWelcomeUser;

    @BindView(R.id.btnLogin)
    Button buttonLogin;

    TextView textViewUserNameNav;

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
        checkUsernameExists();
        return view;
    }

    private void checkUsernameExists() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getPreferences(Context.MODE_PRIVATE);
        if (sharedPreferences.contains(getString(R.string.username))) {
            editTextloginName.setVisibility(View.GONE);
            buttonLogin.setVisibility(View.GONE);

            String username = sharedPreferences.getString(getString(R.string.username), "Username");

            textViewUserNameNav = getActivity().findViewById(R.id.tvUserNameNav);
            if (textViewUserNameNav != null) {
                textViewUserNameNav.setText(username);
            }
            textViewWelcomeUser.setText(String.format("Welcome, %s", username));
        } else {
            editTextloginName.setText("");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnLogin)
    public void onButtonPressed() {
        String name = editTextloginName.getText().toString();

        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.username), name);

        if (name.equalsIgnoreCase(getString(R.string.trainerLoginName))){
            editor.putString(getString(R.string.jarnacRole), JarnacRole.TRAINER.toString());
        } else {
            editor.putString(getString(R.string.jarnacRole), JarnacRole.MEMBER.toString());
        }

        editor.apply();

        checkUsernameExists();
    }
}
