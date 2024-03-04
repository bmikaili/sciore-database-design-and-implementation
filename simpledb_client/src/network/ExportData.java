package network;

import java.sql.*;
import java.io.*;
import java.nio.file.*;

public class ExportData {
  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Usage: ExportData <tablename> <filename>");
      return;
    }
    String tablename = args[0];
    String filename = args[1];
    NetworkDataSource ds = new NetworkDataSource("localhost");

    // Query and column definitions based on the table structures in CreateStudentDB
    String query = "";
    if ("STUDENT".equalsIgnoreCase(tablename)) {
      query = "SELECT SId, SName, MajorId, GradYear FROM STUDENT";
    } else if ("DEPT".equalsIgnoreCase(tablename)) {
      query = "SELECT DId, DName FROM DEPT";
    } else if ("COURSE".equalsIgnoreCase(tablename)) {
      query = "SELECT CId, Title, DeptId FROM COURSE";
    } else if ("SECTION".equalsIgnoreCase(tablename)) {
      query = "SELECT SectId, CourseId, Prof, YearOffered FROM SECTION";
    } else if ("ENROLL".equalsIgnoreCase(tablename)) {
      query = "SELECT EId, StudentId, SectionId, Grade FROM ENROLL";
    } else {
      throw new IllegalArgumentException("Table name not recognized: " + tablename);
    }

    try (Connection conn = ds.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        PrintWriter out = new PrintWriter(Files.newBufferedWriter(Paths.get(filename)))) {

      ResultSetMetaData metadata = rs.getMetaData();
      int columnCount = metadata.getColumnCount();

      // Print column headers
      for (int i = 1; i <= columnCount; i++)
        out.print(metadata.getColumnName(i) + "\t");

      out.println(); // Move to next line

      // Print each row in the result set
      while (rs.next()) {
        for (int i = 1; i <= columnCount; i++) {
          if (metadata.getColumnType(i) == Types.INTEGER)
            out.print(rs.getInt(metadata.getColumnName(i)) + "\t");
          else
            out.print(rs.getString(metadata.getColumnName(i)) + "\t");
        }
        out.println(); // Move to next line
      }

    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }
}
