package myduke;

import myduke.command.Command;
import myduke.command.CommandParser;
import myduke.command.InitialiseDukeCommand;
import myduke.type.MessageFormatType;
import myduke.ui.Ui;
import myduke.storage.StorageManager;
import myduke.task.TaskList;

import java.util.function.Consumer;

/**
 * Main class of Duke.
 */
public class Duke {

    //Constants
    private static final String DATABASE_LOCATION = System.getProperty("user.dir") + "/data/duke.csv";

    //Class Variables
    private StorageManager storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructor for the class Duke.
     */
    public Duke(Consumer<String> logger, MessageFormatType format) {

        if (logger == null) {
            logger = System.out::print;
        }

        ui = new Ui(logger, format);
        tasks = new TaskList();
        storage = new StorageManager(DATABASE_LOCATION, tasks, ui::log);
    }

    /**
     * Finds and gives a response to a user query.
     *
     * @param query The user query
     *
     * @return A boolean indicating whether to exit the chat.
     */
    public boolean respondToQuery(String query) {
        boolean shouldContinueChat = true;

        //Find and give Response
        try {
            Command cmd = CommandParser.create(query);
            cmd.execute(tasks, ui, storage);
            shouldContinueChat = !cmd.shouldExit();
        } catch (Exception ex) {
            ui.logError(ex.getMessage());
        }
        ui.displayMessage();

        return shouldContinueChat;
    }

    /**
     * To run Duke's program.
     */
    public void spin() {
        String userQuery = "reinitialise";
        do {
            //Get query from user
            if (ui.isConsoleScannerInitialised()) {
                userQuery = ui.waitForQuery();
            } else {
                ui.initScanner();
            }

            //Find and give Response
        } while (respondToQuery(userQuery));

        ui.shutdown();
    }

    public static void main(String[] args) {
        Duke myObj = new Duke(null, MessageFormatType.MESSAGE_FORMAT_WITH_BOUNDARY_AND_INDENT);
        myObj.spin();
    }
}
