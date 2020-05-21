package project.triply.triply_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private String[] locations;
    private LayoutInflater layoutInflater;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvLocationName);
        }
    }

    public RecyclerViewAdapter(Context context, String[] locations) {
        this.layoutInflater = LayoutInflater.from(context);
        this.locations = locations;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.text_view, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder vh, int position) {
        vh.textView.setText(locations[position]);
    }

    @Override
    public int getItemCount() {
        return locations.length;
    }
}
