package pxl.be.mobiledevproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
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


public class LoginFragment extends Fragment {
    @BindView(R.id.loginName)
    EditText loginName;

    @BindView(R.id.tvWelcomeUser)
    TextView tvWelcomeUser;

    @BindView(R.id.btnLogin)
    Button btnLogin;

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
            loginName.setVisibility(View.GONE);
            btnLogin.setVisibility(View.GONE);

            String username = sharedPreferences.getString(getString(R.string.username), "Username");

            textViewUserNameNav = getActivity().findViewById(R.id.tvUserNameNav);
            if (textViewUserNameNav != null) {
                textViewUserNameNav.setText(username);
            }

            showNotification("Jarnac Notification", String.format("Welcome, %s", username));
            tvWelcomeUser.setText(String.format("Welcome, %s", username));
        } else {
            loginName.setText("");
        }
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
        editor.putString(getString(R.string.username), name);
        editor.apply();

        checkUsernameExists();
    }


    private void showNotification(String title, String content) {
        NotificationManager mNotificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "Jarnac",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Jarnac_Description");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), "default")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true);
        Intent intent = new Intent(getContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }
}
