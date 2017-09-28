package vchornenkyy.com.todobello;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import vchornenkyy.com.todobello.add.AddTaskFragment;
import vchornenkyy.com.todobello.list.TasksAdapter;

public class MainActivity extends AppCompatActivity {

    private TasksAdapter tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameAddTask, new AddTaskFragment(), AddTaskFragment.class.getName())
                    .commit();
        }

        tasksAdapter = new TasksAdapter();

        RecyclerView listTasks = findViewById(R.id.listTasks);
        listTasks.setLayoutManager(new LinearLayoutManager(this));
        listTasks.setAdapter(tasksAdapter);

        TaskViewModel taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getTasks().observe(this, tasks -> tasksAdapter.update(tasks));
    }
}
