import java.util.logging.Level;
import java.util.logging.Logger; // classes outside java.base still need manual imports

List<String> tasks = List.of("Learn Java", "Write Lesson", "Go Running", "Learn Streams");

void main() {
    IO.println("Tasks that start with 'Learn':");
    var foundTasks = findTasksStartingWith("Learn");
    foundTasks.forEach(IO::println);

    Logger log = Logger.getLogger("Task_Finder");
    log.log(Level.INFO, "Log something");
}

List<String> findTasksStartingWith(String prefix) {
    return tasks.stream().filter(task -> task.startsWith(prefix)).toList();
} 