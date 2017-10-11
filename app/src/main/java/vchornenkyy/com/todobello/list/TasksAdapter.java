package vchornenkyy.com.todobello.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vchornenkyy.com.todobello.R;
import vchornenkyy.com.todobello.task.Task;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.Holder> {

    private List<Task> data = new ArrayList<>(0);

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // TODO: 28.09.2017 change to Room or at least DiffUtil
    public void update(@NonNull List<Task> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private TextView tvTaskName;

        public Holder(View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
        }

        public void bind(Task task) {
            tvTaskName.setText(task.toString());
        }
    }
}
