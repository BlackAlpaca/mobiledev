package pxl.be.mobiledevproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.Objects;

import pxl.be.mobiledevproject.database.RequestHandler;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_settings);
            RequestHandler.getTrainingsData(this, this);

            View headerView = navigationView.getHeaderView(0);
            SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
            String username = sharedPreferences.getString(getString(R.string.username), "");

            TextView textViewUserNameNav = headerView.findViewById(R.id.tvUserNameNav);
            if (textViewUserNameNav != null) {
                textViewUserNameNav.setText(username);
            }

            if ((username != null ? username.length() : 0) > 1){
                showNotification("Jarnac Notification", String.format("Welcome back, %s", username));
            }


        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_trainings:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new Training_management()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,
                                LoginFragment.newInstance()).commit();
                break;
            case R.id.nav_website:
                String url = "http://www.schermclubjarnac.org/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.nav_calendar:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, CalendarOverview.newInstance())
                        .commit();

                break;
            case R.id.nav_accelerometer:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, CalculateSpeed.newInstance()).commit();
                break;
            case R.id.nav_location:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LocationFragment()).commit();
                break;
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

    private void showNotification(String title, String content) {
        NotificationManager mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "Jarnac",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Jarnac_Description");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }

}
