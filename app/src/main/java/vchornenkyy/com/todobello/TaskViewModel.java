package vchornenkyy.com.todobello;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

public class TaskViewModel extends ViewModel {

    private final MutableLiveData<String> task = new MutableLiveData<>();
    private final LiveData<List<String>> tasks;

    public TaskViewModel(@NonNull TaskRepository taskRepository) {
        tasks = Transformations.switchMap(task, input -> {
            MutableLiveData<List<String>> tasks = taskRepository.getTasks();
            taskRepository.addTask(input);
            return tasks;
        });
    }

    public void addNewTask(@NonNull String taskName) {
        task.setValue(taskName);
    }

    public LiveData<List<String>> getTasks() {
        return tasks;
    }
}
