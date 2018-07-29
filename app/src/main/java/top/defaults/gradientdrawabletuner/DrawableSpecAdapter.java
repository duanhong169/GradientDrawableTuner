package top.defaults.gradientdrawabletuner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import top.defaults.gradientdrawabletuner.db.DrawableSpec;

public class DrawableSpecAdapter extends RecyclerView.Adapter<DrawableSpecAdapter.ViewHolder> {

    private List<DrawableSpec> drawableSpecList;

    public DrawableSpecAdapter(List<DrawableSpec> drawableSpecList) {
        this.drawableSpecList = drawableSpecList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawable_spec, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DrawableSpec drawableSpec = drawableSpecList.get(position);
        holder.nameTextView.setText(drawableSpec.getName());
        holder.imageView.setImageDrawable(drawableSpec.getProperties().materialization());
    }

    @Override
    public int getItemCount() {
        return drawableSpecList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(v, getAdapterPosition()));
            nameTextView = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
