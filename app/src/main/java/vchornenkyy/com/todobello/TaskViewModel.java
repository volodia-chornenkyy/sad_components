package vchornenkyy.com.todobello;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

public class TaskViewModel extends ViewModel {

    private final LiveData<List<String>> tasks;

    private TaskRepository taskRepository;

    public TaskViewModel() {
        this.taskRepository = new TaskRepository();
        tasks = taskRepository.getTasks();
    }

    public void addNewTask(@NonNull String task) {
        taskRepository.addTask(task);
    }

    public LiveData<List<String>> getTasks() {
        return tasks;
    }
}
