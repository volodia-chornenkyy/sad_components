package vchornenkyy.com.todobello.add;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import vchornenkyy.com.todobello.R;
import vchornenkyy.com.todobello.TaskViewModel;
import vchornenkyy.com.todobello.ViewModelInjector;

public class AddTaskFragment extends Fragment {

    private TaskViewModel taskViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_add_task, container, false);
        inject();

        view.findViewById(R.id.btnAdd).setOnClickListener(v ->
                taskViewModel.addNewTask(((EditText) view.findViewById(R.id.etTask)).getText().toString()));

        return view;
    }

    private void inject() {
        taskViewModel = ViewModelInjector.get(getActivity(), TaskViewModel.class);
    }
}
