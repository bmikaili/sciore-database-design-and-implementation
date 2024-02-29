package network;

import java.sql.*;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import simpledb.jdbc.network.NetworkDriver;

public class FindMajors {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Usage: FindMajors <departmentName>");
      return;
    }
    String major = args[0];

    String qry = "select sname, gradyear "
        + "from student, dept "
        + "where did = majorid "
        + "and dname = '" + major + "'";

    try {
      // Register JDBC driver
      DriverManager.registerDriver(new NetworkDriver());
    } catch (SQLException e) {
      e.printStackTrace();
      return;
    }

    try (Connection conn = DriverManager.getConnection("jdbc:simpledb://localhost");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(qry)) {

      MajorTableModel model = new MajorTableModel(rs);

      JTable table = new JTable(model);
      JScrollPane scrollPane = new JScrollPane(table);
      JFrame frame = new JFrame("Find Majors Application");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new BorderLayout());
      frame.add(scrollPane, BorderLayout.CENTER);
      frame.pack(); // Adjusts window size to fit the content
      frame.setVisible(true);
    } catch (SQLException e) {
      System.err.println("SQL Exception: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
