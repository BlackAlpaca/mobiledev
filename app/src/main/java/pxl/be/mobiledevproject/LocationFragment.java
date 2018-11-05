package pxl.be.mobiledevproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class LocationFragment extends Fragment {

    private static final int REQUEST_CODE_PERMISSION = 2;
    @BindView(R.id.btnGetLocation)
    Button btnShowLocation;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    @BindView(R.id.tvDistanceToGym)
    TextView tvDistanceToGym;


    GPSTracker gps;
    Unbinder unbinder;

    public LocationFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LocationFragment newInstance() {
        LocationFragment fragment = new LocationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), mPermission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        unbinder = ButterKnife.bind(this, view);
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDistance();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void calculateDistance() {
        gps = new GPSTracker(getActivity());

        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_SHORT).show();

            Location selected_location = new Location("userLocation");
            selected_location.setLatitude(latitude);
            selected_location.setLongitude(longitude);

            Location near_locations = new Location("gymLocation");
            near_locations.setLatitude(51.117430);
            near_locations.setLongitude(5.247550);
            double distance = selected_location.distanceTo(near_locations);

            //Toast.makeText(getContext(), "The distance to the GYM is: \n" + distance + " meters", Toast.LENGTH_LONG).show();

            tvDistanceToGym.setText(String.format("You have to walk: \n%s meters", Math.round(distance * 100.0) / 100.0));

        } else {
            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            gps.showSettingsAlert();
        }
    }
}
