package pxl.be.mobiledevproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pxl.be.mobiledevproject.R;
import pxl.be.mobiledevproject.models.Training;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.TrainingHolder> {
    private List<Training> trainings = new ArrayList<>();

    @NonNull
    @Override
    public TrainingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                                        .inflate(R.layout.training_item, viewGroup, false);
        return new TrainingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingHolder trainingHolder, int i) {
        Training currentTraining = trainings.get(i);
        trainingHolder.textViewTitle.setText(currentTraining.getTitle());
        trainingHolder.textViewNecessities.setText(currentTraining.getNecessities());
        trainingHolder.textViewLocation.setText(currentTraining.getLocation());
        trainingHolder.textViewDateTime.setText(currentTraining.getLocalDateTime());
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    public void setTrainings(List<Training> trainings){
        this.trainings = trainings;
        //TODO: outdated
        notifyDataSetChanged();
    }

    public Training getNoteAt(int position){
        return trainings.get(position);
    }

    class TrainingHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewNecessities;
        private TextView textViewLocation;
        private TextView textViewDateTime;

        public TrainingHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewNecessities = itemView.findViewById(R.id.text_view_necessities);
            textViewLocation = itemView.findViewById(R.id.text_view_location);
            textViewDateTime = itemView.findViewById(R.id.text_view_datetime);
        }
    }
}
