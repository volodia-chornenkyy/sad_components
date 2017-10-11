package vchornenkyy.com.todobello;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import vchornenkyy.com.todobello.task.TaskRepository;
import vchornenkyy.com.todobello.task.TaskViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (TaskViewModel.class.equals(modelClass)) {
            return (T) new TaskViewModel(new TaskRepository(App.getApp()));
        } else {
            return super.create(modelClass);
        }
    }
}
