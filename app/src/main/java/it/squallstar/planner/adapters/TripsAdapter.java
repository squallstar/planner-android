package it.squallstar.planner.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import it.squallstar.planner.R;
import it.squallstar.planner.models.Trip;

/**
 * Created by nicholas on 22/02/2016.
 */
public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder> {

    private List<Trip> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;
        public TextView nameTextView;
        public TextView locationTextView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            nameTextView = (TextView) mView.findViewById(R.id.tripTitle);
            locationTextView = (TextView) mView.findViewById(R.id.tripLocation);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TripsAdapter(List<Trip> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TripsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_trip, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Trip trip = mDataset.get(position);

        holder.nameTextView.setText(trip.name);
        holder.locationTextView.setText(trip.location);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
