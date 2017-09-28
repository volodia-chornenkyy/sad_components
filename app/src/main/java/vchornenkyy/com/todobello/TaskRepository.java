package vchornenkyy.com.todobello;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskRepository {

    private MutableLiveData<List<String>> tasks = new MutableLiveData<>();

    public TaskRepository() {
        // do initial fetch here
        tasks.setValue(new ArrayList<>(0));
    }

    public void addTask(@NonNull String task) {
        List<String> value = tasks.getValue();
        assert value != null;
        value.add(task);
        tasks.setValue(value);
    }

    public MutableLiveData<List<String>> getTasks() {
        return tasks;
    }
}
