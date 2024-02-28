package network;

import java.sql.*;

import simpledb.jdbc.network.NetworkDriver;

public class FindMajors {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Usage: FindMajors <departmentName>");
      return;
    }
    String major = args[0];

    System.out.println("Here are the " + major + " majors");
    System.out.println("Name\tGradYear");

    String qry = "select sname, gradyear "
        + "from student, dept "
        + "where did = majorid "
        + "and dname = '" + major + "'";

    try {
      DriverManager.registerDriver(new NetworkDriver());
    } catch (SQLException e) {
      System.err.println("Error registering driver: " + e);
      e.printStackTrace();
    }

    NetworkDataSource dataSource = new NetworkDataSource("localhost");

    try (Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(qry)) {
      while (rs.next()) {
        String sname = rs.getString("sname");
        int gradyear = rs.getInt("gradyear");
        System.out.println(sname + "\t" + gradyear);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
