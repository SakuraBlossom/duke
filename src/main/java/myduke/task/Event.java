package myduke.task;

import java.util.Scanner;

import myduke.exception.DukeException;
import myduke.exception.DukeEmptyDescriptionException;
import myduke.task.parameters.DukeDateTime;

/**
 * A Task representing an Event.
 */
public class Event extends Task {
    protected final DukeDateTime at;

    public Event(String description, String at) {
        super(description);
        this.at = new DukeDateTime(at);
    }

    /**
     * Parses the query as a Event Task.
     * @param in A query from the user.
     * @return A Event task
     * @throws DukeException representing any checked exceptions
     */
    public static Task parse(Scanner in) throws DukeException {

        String delimiter = "/at ";
        in.useDelimiter(delimiter);
        if (!in.hasNext()) {
            throw new DukeEmptyDescriptionException("The description of an event cannot be empty.");
        }
        String description = in.next().trim();

        in.useDelimiter("\\z");
        if (!in.hasNext()) {
            throw new DukeEmptyDescriptionException("The duration of a event cannot be empty.");
        }
        String at = in.next().substring(delimiter.length()).trim();

        return new Event(description, at);
    }

    @Override
    public String getDataBaseFormat() {
        return String.format("E | %d | %s | %s |\r\n", (isDone ? 1 : 0), description, at);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at + ")";
    }
}