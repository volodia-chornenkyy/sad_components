package vchornenkyy.com.todobello.task;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

public class TaskViewModel extends ViewModel {

    private final MutableLiveData<String> task = new MutableLiveData<>();
    private final MediatorLiveData<Boolean> loading = new MediatorLiveData<>();
    private final LiveData<List<Task>> tasks;

    public TaskViewModel(@NonNull TaskRepository taskRepository) {
        tasks = Transformations.switchMap(task, input -> {
            loading.setValue(true);
            if (input != null && input.length() > 0) {
                taskRepository.addTask(input);
            }
            return taskRepository.getTasks();
        });
        loading.addSource(tasks, tasks -> loading.setValue(false));
        task.setValue("");
    }

    public void addNewTask(@NonNull String taskName) {
        task.setValue(taskName);
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }
}
