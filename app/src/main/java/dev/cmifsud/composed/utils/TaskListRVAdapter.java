package dev.cmifsud.composed.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.cmifsud.composed.R;
import dev.cmifsud.composed.models.Task;
import dev.cmifsud.composed.views.TaskListFragment;

public class TaskListRVAdapter extends RecyclerView.Adapter<TaskListRVAdapter.ViewHolder> {

    private final List<Task> _items;
    private final TaskListFragment.IOnInteractionListener _listener;
    private final Context _context;

    public TaskListRVAdapter(List<Task> items , Context context, TaskListFragment.IOnInteractionListener listener) {
        _items = items;
        _listener = listener;
        _context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() )
            .inflate(R.layout.fragment_task_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pos) {
        // Bind data
        holder.Item = _items.get(pos);
        holder.tvIdView.setText(String.valueOf(holder.Item.getTaskId()));
        holder.tvContentView.setText(holder.Item.getName());
        holder.updateActiveUIStatus();
        holder.cbComplete.setChecked(holder.Item.getComplete());
//        holder.tvSubtasks.setText(String.valueOf(item.getSubtasks().size()));

        // Bind interation listeners
        holder.View.setOnClickListener( (View v) -> _listener.openTask(holder.Item.getTaskId()) );
        holder.cbComplete.setOnClickListener( (View v) -> {
            holder.Item.setComplete(!holder.Item.getComplete());
            holder.updateActiveUIStatus();
            _listener.saveTask(holder.Item);
        });
    }

    @Override
    public int getItemCount()
    {
        return _items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View View;
        public final TextView tvIdView;
        public final TextView tvContentView;
        public final CheckBox cbComplete;
        public final TextView tvSubtasks;
        public Task Item;

        public ViewHolder(View view) {
            super(view);
            View = view;
            tvIdView = view.findViewById(R.id.item_number);
            tvContentView = view.findViewById(R.id.name);
            cbComplete = view.findViewById(R.id.complete_box);
            tvSubtasks = view.findViewById(R.id.subtasks);
        }

        public void updateActiveUIStatus() {
            if (Item.getComplete()) {
                tvIdView.setTextColor(_context.getColor(R.color.darkMedium));
                tvContentView.setTextColor(_context.getColor(R.color.darkMedium));
                tvSubtasks.setTextColor(_context.getColor(R.color.darkMedium));
            }
            else {
                tvIdView.setTextColor(_context.getColor(R.color.darkAccent));
                tvContentView.setTextColor(_context.getColor(R.color.darkAccent));
                tvSubtasks.setTextColor(_context.getColor(R.color.darkAccent));
            }
        }
    }
}