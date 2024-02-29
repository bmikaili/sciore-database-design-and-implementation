package network;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MajorTableModel extends AbstractTableModel {
  private List<String> columnNames = Arrays.asList("Name", "GradYear");
  private List<List<Object>> data = new ArrayList<>();

  public MajorTableModel(ResultSet rs) throws SQLException {
    while (rs.next()) {
      List<Object> row = new ArrayList<>();
      row.add(rs.getString("sname"));
      row.add(rs.getInt("gradyear"));
      data.add(row);
    }
  }

  @Override
  public int getRowCount() {
    return data.size();
  }

  @Override
  public int getColumnCount() {
    return columnNames.size();
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return data.get(rowIndex).get(columnIndex);
  }

  @Override
  public String getColumnName(int column) {
    return columnNames.get(column);
  }
}
