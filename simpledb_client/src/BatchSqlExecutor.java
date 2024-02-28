import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import network.NetworkDataSource;

public class BatchSqlExecutor {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: java BatchSqlExecutor <sql_commands_file_path>");
      System.exit(1);
    }

    String filePath = args[0];

    NetworkDataSource dataSource = new NetworkDataSource("localhost");

    try (Connection conn = dataSource.getConnection()) {
      executeSqlCommandsFromFile(filePath, conn);
    } catch (SQLException ex) {
      System.err.println("Error obtaining connection from NetworkDataSource.");
      ex.printStackTrace();
    }
  }

  private static void executeSqlCommandsFromFile(String filePath, Connection conn) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
        Statement stmt = conn.createStatement()) {
      String line;

      while ((line = reader.readLine()) != null) {
        if (!line.startsWith("--") && !line.isEmpty()) {
          try {
            boolean isResultSet = stmt.execute(line);

            if (isResultSet) {
              System.out.println("Executed query: " + line);
            } else {
              int updateCount = stmt.getUpdateCount();

              System.out.println("Executed update: " + line + ". " + updateCount + " rows affected.");
            }
          } catch (SQLException e) {
            System.err.println("Error executing SQL command: " + line);
            e.printStackTrace();
          }
        }
      }

    } catch (IOException e) {
      System.err.println("Error reading file from: " + filePath);
      e.printStackTrace();
    } catch (SQLException e) {
      System.err.println("Error closing statement.");
      e.printStackTrace();
    }
  }
}
