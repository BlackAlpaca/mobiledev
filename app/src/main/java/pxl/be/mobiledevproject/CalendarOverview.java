package pxl.be.mobiledevproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CalendarOverview extends Fragment {
    private static final String TAG = "CalendarFragment";

    private TextView theDate;
    private Button btnGoCalender;

    private Unbinder unbinder;

    public CalendarOverview() {
    }


    public static CalendarOverview newInstance() {
        return new CalendarOverview();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.calendar_starter, container, false);
        unbinder = ButterKnife.bind(this, view);
        theDate = view.findViewById(R.id.date);
        btnGoCalender = view.findViewById(R.id.btnGoCalendar);

        btnGoCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
