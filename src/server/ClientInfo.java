package server;

import java.net.InetAddress;

/**
 * Represents information on each user
 */
public class ClientInfo {

  private InetAddress address;
  private int port;
  private String name;
  private int id;

  public ClientInfo(String name, int id, InetAddress address, int port) {
    this.address = address;
    this.port = port;
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public InetAddress getAddress() {
    return address;
  }

  public int getPort() {
    return port;
  }

}
