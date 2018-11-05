package pxl.be.mobiledevproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pxl.be.mobiledevproject.models.Training;

public class TrainingsListAdapter extends RecyclerView.Adapter<TrainingsListAdapter.MyViewHolder> {

    private static Training[] dataset;

    public TrainingsListAdapter(List<Training> myDataset) {
        dataset = myDataset.toArray(new Training[0]);
    }

    @Override
    public TrainingsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainings_list_item, parent, false);

        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.trainingsText.setText(setTrainingText(dataset[position]));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.length;
    }

    private String setTrainingText(Training training) {
        return String.format("%s - Date: %s - Location: %s", training.getTitle(), training.getLocalDateTime(), training.getLocation());
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView trainingsText;

        public MyViewHolder(View v) {
            super(v);
            trainingsText = (TextView) v.findViewById(R.id.textViewTrainings);
        }
    }
}
