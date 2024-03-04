package network;

import java.sql.*;

public class ResetStudentDB {
  public static void main(String[] args) {
    NetworkDataSource ds = new NetworkDataSource("localhost");

    try (Connection conn = ds.getConnection();
        Statement stmt = conn.createStatement()) {

      // Delete all records from tables
      stmt.executeUpdate("DELETE FROM ENROLL");
      System.out.println("Deleted all records from table ENROLL.");

      stmt.executeUpdate("DELETE FROM SECTION");
      System.out.println("Deleted all records from table SECTION.");

      stmt.executeUpdate("DELETE FROM COURSE");
      System.out.println("Deleted all records from table COURSE.");

      stmt.executeUpdate("DELETE FROM DEPT");
      System.out.println("Deleted all records from table DEPT.");

      stmt.executeUpdate("DELETE FROM STUDENT");
      System.out.println("Deleted all records from table STUDENT.");

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
