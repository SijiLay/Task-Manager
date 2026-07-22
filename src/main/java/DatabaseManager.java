import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private static final String DATABASE_URL = "jdbc:sqlite:tasks.db";  // The location of the SQLite database file.
                                                                        // SQLite will automatically create tasks.db if it doesn't already exist.

    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DATABASE_URL);
    } // Opens and returns a connection to the SQLite database.
                                                                // Every database operation starts by calling this method.

    public void initializeDatabase() {
        String sql = """
        
                CREATE TABLE IF NOT EXISTS Tasks (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL CHECK (trim(name) <> ''),
            completed INTEGER NOT NULL DEFAULT 0
                CHECK (completed IN (0, 1)),
            priority TEXT NOT NULL DEFAULT 'MEDIUM'
                CHECK (priority IN ('HIGH', 'MEDIUM', 'LOW')),
            category TEXT NOT NULL DEFAULT 'OTHER'
                CHECK (
                    category IN (
                        'SCHOOL',
                        'WORK',
                        'PERSONAL',
                        'FITNESS',
                        'CHURCH',
                        'OTHER'
                    )
                )
        )
        """;

        try (// Opens the database connection and prepares the SQL command. Both resources are automatically closed when finished.
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database.", e);
        }
    }  // Creates the Tasks table if it does not already exist. This method is called once when the application starts.

    public void insertTask(Task task){
        String sql = """
        INSERT INTO Tasks (name, completed, priority, category)
        VALUES (?, ?, ?, ?)
        """;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS) ) {

            statement.setString(1, task.getName());
            statement.setBoolean(2, task.isCompleted());
            statement.setString(3, task.getPriority().name());
            statement.setString(4, task.getCategory().name());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    task.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException(
                            "Task was inserted without a generated ID."
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert task.", e);
        }
    } // Inserts a new Task object into the SQLite database.

    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        String sql = """
        SELECT id, name, completed, priority, category
        FROM Tasks
        ORDER BY id
        """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()){

            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                boolean completed = results.getBoolean("completed");
                Priority priority = Priority.valueOf(results.getString("priority"));
                Category category = Category.valueOf(results.getString("category"));

                Task task = new Task(id,name,completed,priority,category);
                tasks.add(task);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to load tasks.",
                    e
            );
        }
        return tasks;
    }

    public void updateTask(Task task) {
        String sql = """
UPDATE Tasks
SET name = ?,
    completed = ?,
    priority = ?,
    category = ?
WHERE id = ?
""";
        try (Connection connection = getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, task.getName());
            statement.setBoolean(2, task.isCompleted());
            statement.setString(3, task.getPriority().name());
            statement.setString(4, task.getCategory().name());
            statement.setInt(5, task.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to update task.",
                    e
            );
        }
    }

    public void deleteTask(int id) {
        String sql = """
                DELETE FROM Tasks
                WHERE id = ?""";
        try (Connection connection  = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1,id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to delete task.",
                    e
            );
        }
    }
}
