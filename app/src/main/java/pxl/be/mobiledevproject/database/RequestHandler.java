package pxl.be.mobiledevproject.database;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pxl.be.mobiledevproject.R;
import pxl.be.mobiledevproject.models.Training;
import pxl.be.mobiledevproject.viewmodel.TrainingViewModel;

public class RequestHandler {

    public static void getTrainingsData(FragmentActivity activity, Context context) {
        //String url = "https://api.myjson.com/bins/15hali";
        TrainingViewModel trainingViewModel;
        RequestQueue requestQueue;
        String url = context.getString(R.string.getURL);


        trainingViewModel = ViewModelProviders.of(activity).get(TrainingViewModel.class);
        trainingViewModel.deleteAllTrainings();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null
                , response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("trainings");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject training = jsonArray.getJSONObject(i);

                    int id = training.getInt("id");
                    String localDateTime = training.getString("localDateTime");
                    String necessities = training.getString("necessities");
                    String location = training.getString("location");
                    String title = training.getString("title");
                    String isAdult = training.getString("adult");

                    trainingViewModel.insert(new Training(localDateTime, necessities, location, title, Boolean.valueOf(isAdult)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        requestQueue.add(request);
    }


    public static void postTrainingsData(Context context, String title, String necessities, String location, String date) {
        //String url = "https://api.myjson.com/bins/15hali";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = context.getString(R.string.postURL);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // response
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // error
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("necessities", necessities);
                params.put("location", location);
                params.put("localDateTime", date);
                params.put("adult", "true");

                return params;
            }
        };
        requestQueue.add(postRequest);
    }

}
