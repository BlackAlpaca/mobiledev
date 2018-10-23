package pxl.be.mobiledevproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pxl.be.mobiledevproject.models.Training;


public class DataCallSingleton {

    private RequestQueue mQueue;
    private List<Training> trainings;

    public DataCallSingleton() {
    }

    private void jsonParse(){
        String url = "https://api.myjson.com/bins/enznw";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null
        , response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("trainings");

                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject training = jsonArray.getJSONObject(i);

                    int id = training.getInt("id");
                    String localDateTime = training.getString("localDateTime");
                    String necessities = training.getString("necessities");
                    String location = training.getString("location");
                    String title = training.getString("title");
                    boolean isAdult = training.getBoolean("isAdult");


                    //TODO ADD ID TO CTOR
                    trainings.add(new Training( localDateTime, necessities, location, title, isAdult));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
    }


    public List<Training> getTrainings(Context context){
        mQueue = Volley.newRequestQueue(context);
        trainings = new ArrayList<>();

        jsonParse();

        return trainings;
    }

}
