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

    NetworkDataSource ds = new NetworkDataSource("localhost");

    try (Connection conn = ds.getConnection();
        Statement stmt = conn.createStatement()) {

      String[] columnNames;
      if ("STUDENT".equalsIgnoreCase(tablename)) {
        columnNames = new String[] { "SId", "SName", "MajorId", "GradYear" };
      } else if ("DEPT".equalsIgnoreCase(tablename)) {
        columnNames = new String[] { "DId", "DName" };
      } else if ("COURSE".equalsIgnoreCase(tablename)) {
        columnNames = new String[] { "CId", "Title", "DeptId" };
      } else if ("SECTION".equalsIgnoreCase(tablename)) {
        columnNames = new String[] { "SectId", "CourseId", "Prof", "YearOffered" };
      } else if ("ENROLL".equalsIgnoreCase(tablename)) {
        columnNames = new String[] { "EId", "StudentId", "SectionId", "Grade" };
      } else {
        throw new IllegalArgumentException("Unrecognized table name: " + tablename);
      }

      Path path = Paths.get(filename);
      try (BufferedReader br = Files.newBufferedReader(path)) {
        br.readLine(); // Skip header line

        String line;
        while ((line = br.readLine()) != null) {
          String[] fields = line.split("\t");
          if (fields.length != columnNames.length) {
            System.err.println("Mismatch between column names and data fields.");
            continue; // Skip this line/mismatched record
          }

          StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
          sqlBuilder.append(tablename);
          sqlBuilder.append(" (");
          sqlBuilder.append(String.join(", ", columnNames));
          sqlBuilder.append(") VALUES (");

          for (int i = 0; i < fields.length; i++) {
            if (fields[i].matches("\\d+")) {
              sqlBuilder.append(fields[i]);
            } else {
              fields[i] = fields[i].replace("'", "''"); // Escape single quote as appropriate for SQL
              sqlBuilder.append("'").append(fields[i]).append("'");
            }
            if (i < fields.length - 1) {
              sqlBuilder.append(", ");
            }
          }
          sqlBuilder.append(")");
          String sql = sqlBuilder.toString();
          stmt.executeUpdate(sql);
        }
      }
    } catch (IOException | SQLException e) {
      e.printStackTrace();
    }
  }
}
