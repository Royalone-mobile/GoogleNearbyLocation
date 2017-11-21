package samzhu.myapplication;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.MyViewHolder>{
    protected List<Object> list = new ArrayList<>();

    GetLocationTask getLocationTask ;

    double latitude, longitude;
    @Override
    public LocationsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.select_location_item, parent, false);
        return new LocationsAdapter.MyViewHolder(v);
    }

    public void clearList() {
        int listSize = getItemCount();

        this.list.clear();
        notifyItemRangeRemoved(0, listSize);
    }

    public void execute(double latitude, double longitude) {
        this.clearList();
        this.latitude = latitude;
        this.longitude = longitude;
        getLocationTask.execute(latitude, longitude);
        // get the Songs from the web asynchronously
    }

    public void appendList(List<Location> l) {
        if (l != null && l.size() > 0) {
            this.list.addAll(l);
        }
        this.notifyDataSetChanged();
    }

    public LocationsAdapter() {
            getLocationTask = new GetLocationTask(this);
    }

    @Override
    public void onBindViewHolder(LocationsAdapter.MyViewHolder holder, int position) {
        Location location = (Location)list.get(position);
        holder.txtLocation1.setText(location.getVincity());
        holder.txtLocation2.setText(location.getName());

        if (position >= getItemCount() - 1) {
            if(getLocationTask != null)
                getLocationTask.execute(latitude, longitude);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_location1) TextView txtLocation1;
        @Bind(R.id.txt_location2) TextView txtLocation2;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

