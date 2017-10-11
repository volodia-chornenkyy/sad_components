package vchornenkyy.com.todobello.task;

public class Task {

    String name;

    /**
     * 0 - in progress
     * 1 - done
     */
    int state;

    public Task(String name) {
        this.name = name;
    }

    public Task(String name, int state) {
        this.name = name;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", state=" + state +
                '}';
    }
}
