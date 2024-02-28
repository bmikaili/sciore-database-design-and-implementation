package network;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class NetworkDataSource implements DataSource {
  private String serverName;

  public NetworkDataSource(String serverName) {
    this.serverName = serverName;
  }

  public String getServerName() {
    return serverName;
  }

  public void setServerName(String serverName) {
    this.serverName = serverName;
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    throw new UnsupportedOperationException("Unimplemented method 'getParentLogger'");
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    throw new UnsupportedOperationException("Unimplemented method 'unwrap'");
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    throw new UnsupportedOperationException("Unimplemented method 'isWrapperFor'");
  }

  @Override
  public Connection getConnection() throws SQLException {
    String url = "jdbc:simpledb://" + serverName;
    return DriverManager.getConnection(url);
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    throw new UnsupportedOperationException("Unimplemented method 'getConnection'");
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    throw new UnsupportedOperationException("Unimplemented method 'getLogWriter'");
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
    throw new UnsupportedOperationException("Unimplemented method 'setLogWriter'");
  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {
    throw new UnsupportedOperationException("Unimplemented method 'setLoginTimeout'");
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    throw new UnsupportedOperationException("Unimplemented method 'getLoginTimeout'");
  }

}
