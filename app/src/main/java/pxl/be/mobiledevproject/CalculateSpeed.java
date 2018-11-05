package pxl.be.mobiledevproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class CalculateSpeed extends Fragment implements SensorEventListener {


    @BindView(R.id.tvAccelerometer)
    TextView tvAccelerometer;

    private SensorManager sensorManager;
    Sensor accelerometer;
    private int maxX;
    private int maxY;
    private float maxZ;

    private Unbinder unbinder;

    View rootView;

    public CalculateSpeed() {
        // Required empty public constructor
    }

    public static CalculateSpeed newInstance() {
        CalculateSpeed fragment = new CalculateSpeed();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {

            // success! we have an accelerometer
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        } else {
            tvAccelerometer.setText("Failed to bind");
            // fail! we dont have an accelerometer!
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculate_speed, container, false);
        rootView = view;
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // HOLD PHONE WITH SCREEN TO YOU :)
        if (maxZ < event.values[2]){
            maxZ = event.values[2];
        }

        if (tvAccelerometer != null ){
            tvAccelerometer.setText(String.format("Max Attack Speed : \n%s", Math.round(maxZ * 100.0) / 100.0));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @OnClick(R.id.btnReset)
    public void onButtonPressed() {
        maxZ = 0f;
    }
}
