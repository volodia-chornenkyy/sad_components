package vchornenkyy.com.todobello;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import vchornenkyy.com.todobello.add.AddTaskFragment;
import vchornenkyy.com.todobello.list.TasksAdapter;
import vchornenkyy.com.todobello.task.TaskViewModel;

public class MainActivity extends AppCompatActivity {

    private TasksAdapter tasksAdapter;

    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inject();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameAddTask, new AddTaskFragment(), AddTaskFragment.class.getName())
                    .commit();
        }

        tasksAdapter = new TasksAdapter();

        RecyclerView listTasks = findViewById(R.id.listTasks);
        listTasks.setLayoutManager(new LinearLayoutManager(this));
        listTasks.setAdapter(tasksAdapter);
        taskViewModel.getTasks().observe(this, tasks -> tasksAdapter.update(tasks));
    }

    private void inject() {
        taskViewModel = ViewModelInjector.get(this, TaskViewModel.class);
    }
}
