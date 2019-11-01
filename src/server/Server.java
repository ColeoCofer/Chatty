package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {

  private final static String terminationChar = "\\e";
  private static DatagramSocket socket;
  private static boolean running;

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

  }

  //Sends a message to a specific user
  private static void send(String message, InetAddress address, int port) {
    try {
      message += terminationChar;
      byte[] data = message.getBytes();

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
            broadcast(message);

          }
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    };

    listenThread.start();
  }

  //Stop the server
  public static void stop() {

  }

}
