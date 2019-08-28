package myduke.task;

/**
 * An abstract class to represent a Task.
 */
public abstract class Task {
    protected final String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public abstract String getDataBaseFormat();

    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), this.description);
    }
}