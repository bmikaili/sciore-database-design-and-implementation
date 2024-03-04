package network;

import java.sql.*;
import java.io.*;
import java.nio.file.*;

public class ImportData {
  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Usage: ImportData <filename> <tablename>");
      return;
    }
    String filename = args[0];
    String tablename = args[1];

    // Let's build up database URL, username and password but have them ready for
    // your actual database connection. Make sure to modify these variables.
    NetworkDataSource ds = new NetworkDataSource("localhost");

    try (Connection conn = ds.getConnection();
        Statement stmt = conn.createStatement()) {
      Path path = Paths.get(filename);
      try (BufferedReader br = Files.newBufferedReader(path)) {
        br.readLine(); // Skip the first line with column names
        String line;

        while ((line = br.readLine()) != null) {
          String[] fields = line.split("\t");

          if (fields.length < 4) {
            throw new IllegalArgumentException(
                "Invalid input format. Line should contain exactly 4 fields separated by tabs.");
          }

          String sql = String.format(
              "INSERT INTO %s (SId, SName, MajorId, GradYear) VALUES (%s, '%s', %s, %s)",
              tablename,
              fields[0], // Assuming SId is an integer
              fields[1], // Assuming SName is a string requiring quote wrap
              fields[2], // Assuming MajorId is an integer
              fields[3] // Assuming GradYear is an integer
          );

          stmt.executeUpdate(sql);
        }
      }
    } catch (IOException | SQLException e) {
      e.printStackTrace();
    }
  }
}
