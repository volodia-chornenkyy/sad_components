package vchornenkyy.com.todobello;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vchornenkyy.com.todobello.task.TaskRepository;
import vchornenkyy.com.todobello.task.TaskViewModel;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings({"ResultOfMethodCallIgnored", "unchecked", "WeakerAccess", "ConstantConditions"})
public class TaskViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    TaskRepository taskRepository;

    TaskViewModel viewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        viewModel = new TaskViewModel(taskRepository);
    }

    @Test
    public void testNoDataAfterInit() {
        assertThat(viewModel.getTasks(), notNullValue());
        viewModel.getTasks().observeForever(mock(Observer.class));
        assertThat(viewModel.getTasks().getValue(), nullValue());
    }

    @Test
    public void testExistingDataAfterInit() {
        String testTask = "testTask";
        MutableLiveData<List<String>> tasks = new MutableLiveData<>();
        List<String> tasksList = Collections.singletonList(testTask);
        tasks.setValue(tasksList);
        when(taskRepository.getTasks()).thenReturn(tasks);
        Observer observer = mock(Observer.class);
        viewModel.getTasks().observeForever(observer);

        verify(taskRepository, times(1)).getTasks();
        verify(observer, times(1)).onChanged(Collections.singletonList(testTask));
    }

    @Test
    public void testAddValidTask() {
        String testTask = "testTask";
        viewModel.getTasks().observeForever(mock(Observer.class));

        viewModel.addNewTask(testTask);

        verify(taskRepository, times(1)).addTask(testTask);
    }

    @Test
    public void testSkipNotValidTask() {
        viewModel.getTasks().observeForever(mock(Observer.class));

        viewModel.addNewTask(null);
        viewModel.addNewTask("");

        verify(taskRepository, never()).addTask(any());
    }

    @Test
    public void testUpdateTasksAfterAddTask() {
        MutableLiveData<List<String>> tasks = new MutableLiveData<>();
        tasks.setValue(new ArrayList<>(0));
        when(taskRepository.getTasks()).thenReturn(tasks);
        String testTask = "testTask";
        Observer observer = mock(Observer.class);
        viewModel.getTasks().observeForever(observer);
        doAnswer(invocation -> {
            tasks.getValue().add((String) invocation.getArguments()[0]);
            return null;
        }).when(taskRepository).addTask(testTask);

        viewModel.addNewTask(testTask);

        verify(taskRepository, times(1)).addTask(testTask);
        verify(observer, times(1)).onChanged(Collections.singletonList(testTask));
    }
}