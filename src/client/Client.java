package client;

import static server.Server.connectCmd;
import static server.Server.terminationChar;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

  private static DatagramSocket socket;
  private InetAddress address;
  private int port;
  private String name;
  private boolean running;

  public Client(String name, String address, int port) {
    try {
      this.name = name;
      this.address = InetAddress.getByName(address);
      this.port = port;
      socket = new DatagramSocket();
      running = true;
      listen();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    send("\\con:" + name);

  }

  public void send(String message) {
    try {

      //Get rid of name when a command is sent to the server
      if (!message.startsWith("\\")) {
        message = name + ":" + message;
      }

      message += terminationChar;
      byte[] data = message.getBytes();
      DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
      socket.send(packet);
      System.out.println("Send message to, " + address.getHostAddress() + ":" + port);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  //Waits for messages on a separate thread
  private void listen() {

    //socket.receive will pause current thread until it receives a message
    Thread listenThread = new Thread("Chat Listener") {
      public void run() {
        try {
          while (running) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            socket.receive(packet);

            String message = new String(data); //Convert byte[] into String
            message = message.substring(0, message.indexOf(terminationChar)); //Every message needs a termination character

            //TODO: Manage message...
            if (!isCommand(message, packet)) {
              ClientChatWindow.printToConsole(message);
              System.out.println("Message: " + message);
            }

          }
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    };

    listenThread.start();
  }

  /**
   * Server commands:
   *   \con:[name] -> Connects client to server
   *   \dis:[id] -> Disconnect client from server
   * @param message
   * @param packet
   * @return
   */
  private static boolean isCommand(String message, DatagramPacket packet) {
    if (message.startsWith(connectCmd)) {
      System.out.println("Yes, it's a command");
      return true;
    }
    System.out.println("Not a command");
    return false;
  }

}
