package vchornenkyy.com.todobello.task;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskRepository {

    private final SharedPreferences storage;

    private MutableLiveData<List<Task>> tasks = new MutableLiveData<>();

    public TaskRepository(Context context) {
        storage = context.getSharedPreferences("tasks", Context.MODE_PRIVATE);

        // do initial fetch here
        new LoadTask(tasks).execute();
    }

    public void addTask(@NonNull String task) {
        new SaveAndNotifyTask(tasks).execute(task);
    }

    public MutableLiveData<List<Task>> getTasks() {
        return tasks;
    }

    // ATTENTION: don't do like this
    private class SaveAndNotifyTask extends AsyncTask<String, Void, Boolean> {

        private WeakReference<MutableLiveData<List<Task>>> data;

        SaveAndNotifyTask(MutableLiveData<List<Task>> data) {
            this.data = new WeakReference<>(data);
        }

        @Override
        protected Boolean doInBackground(String... voids) {
            // simulate noticeable loading
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return storage.edit().putInt(voids[0], 0).commit();

        }

        @Override
        protected void onPostExecute(Boolean updated) {
            if (updated) {
                MutableLiveData<List<Task>> listMutableLiveData = data.get();
                if (listMutableLiveData != null) {
                    new LoadTask(listMutableLiveData).execute();
                }
            }
        }
    }

    private class LoadTask extends AsyncTask<Void, Void, List<Task>> {

        private WeakReference<MutableLiveData<List<Task>>> data;

        LoadTask(MutableLiveData<List<Task>> data) {
            this.data = new WeakReference<>(data);
        }

        @Override
        protected List<Task> doInBackground(Void... voids) {
            Map<String, ?> all = storage.getAll();
            List<Task> tasks = new ArrayList<>(all.size());
            for (Map.Entry<String, ?> entry : all.entrySet()) {
                tasks.add(new Task(entry.getKey(), Integer.valueOf(entry.getValue().toString())));
            }
            return tasks;
        }

        @Override
        protected void onPostExecute(List<Task> tasks) {
            if (tasks != null) {
                MutableLiveData<List<Task>> listMutableLiveData = data.get();
                if (listMutableLiveData != null) {
                    listMutableLiveData.setValue(tasks);
                }
            }
        }
    }
}
