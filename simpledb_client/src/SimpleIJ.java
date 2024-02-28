import java.sql.*;
import java.util.Scanner;
import simpledb.jdbc.embedded.EmbeddedDriver;
import simpledb.jdbc.network.NetworkDriver;

public class SimpleIJ {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Connect> ");
    String connectionString = sc.nextLine();
    Driver d = (connectionString.contains("//")) ? new NetworkDriver() : new EmbeddedDriver();

    try (Connection conn = d.connect(connectionString, null);
        Statement stmt = conn.createStatement()) {
      StringBuilder commandBuilder = new StringBuilder();
      System.out.print("\nSQL> ");
      while (sc.hasNextLine()) {
        // process one line of input
        String line = sc.nextLine().trim();
        if (line.equals("exit")) {
          break;
        }
        // Skip comments and empty lines
        if (!line.startsWith("--") && !line.isEmpty()) {
          commandBuilder.append(line);
          // End of SQL statement
          if (line.endsWith(";")) {
            String cmd = commandBuilder.toString();
            // Remove the semicolon
            cmd = cmd.substring(0, cmd.length() - 1).trim();
            if (cmd.toLowerCase().startsWith("select")) {
              doQuery(stmt, cmd);
            } else {
              doUpdate(stmt, cmd);
            }
            // Reset the command builder for the next SQL statement
            commandBuilder.setLength(0);
            System.out.print("\nSQL> ");
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      sc.close();
    }
  }

  private static void doQuery(Statement stmt, String cmd) {
    try (ResultSet rs = stmt.executeQuery(cmd)) {
      ResultSetMetaData md = rs.getMetaData();
      int numcols = md.getColumnCount();
      int totalwidth = 0;

      // print header
      for (int i = 1; i <= numcols; i++) {
        String fldname = md.getColumnName(i);
        int width = md.getColumnDisplaySize(i);
        totalwidth += width;
        String fmt = "%" + width + "s";
        System.out.format(fmt, fldname);
      }
      System.out.println();
      for (int i = 0; i < totalwidth; i++)
        System.out.print("-");
      System.out.println();

      // print records
      while (rs.next()) {
        for (int i = 1; i <= numcols; i++) {
          String fldname = md.getColumnName(i);
          int fldtype = md.getColumnType(i);
          String fmt = "%" + md.getColumnDisplaySize(i);
          if (fldtype == Types.INTEGER) {
            int ival = rs.getInt(fldname);
            System.out.format(fmt + "d", ival);
          } else {
            String sval = rs.getString(fldname);
            System.out.format(fmt + "s", sval);
          }
        }
        System.out.println();
      }
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getMessage());
    }
  }

  private static void doUpdate(Statement stmt, String cmd) {
    try {
      int howmany = stmt.executeUpdate(cmd);
      System.out.println(howmany + " records processed");
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getMessage());
    }
  }
}
