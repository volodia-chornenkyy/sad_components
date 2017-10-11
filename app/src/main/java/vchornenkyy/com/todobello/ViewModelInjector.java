package vchornenkyy.com.todobello;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

public class ViewModelInjector {

    private static ViewModelFactory viewModelFactory = new ViewModelFactory();

    public static <T extends ViewModel> T get(FragmentActivity activity, Class<T> clazz) {
        if (TaskViewModel.class.getName().equals(clazz.getName())) {
            return ViewModelProviders.of(activity, viewModelFactory).get(clazz);
        } else {
            return ViewModelProviders.of(activity).get(clazz);
        }
    }
}
