package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {

  //Commands
  private final static String terminationChar = "\\e";
  private final static String connectCmd = "\\con";
  private final static String disconnectCmd = "\\dis";

  private static int clientID = 0;
  private static DatagramSocket socket;
  private static boolean running;

  private static ArrayList<ClientInfo> clients = new ArrayList<>();

  //Start the sever
  public static void start(int port) {
    try
    {
      socket = new DatagramSocket(port);
      running = true;
      listen();
      System.out.println("Server started on port, " + port);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  //Sends a message to every client connected to the server
  private static void broadcast(String message) {
    for (ClientInfo info : clients) {
      send(message, info.getAddress(), info.getPort());
    }
  }

  //Sends a message to a specific user
  private static void send(String message, InetAddress address, int port) {
    try {
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
  private static void listen() {

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
              broadcast(message);
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

      String name = message.substring(message.indexOf(":") + 1); //Remove name after colon
      clients.add(new ClientInfo(name, ++clientID, packet.getAddress(), packet.getPort()));
      broadcast("User " + name + " connected!");

      return true;
    }
    return false;
  }

  //Stop the server
  public static void stop() {
    running = false;
  }

}
