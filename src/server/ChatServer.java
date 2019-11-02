package server;

/**
 * Start Chat Server First -> Then the client window
 */

public class ChatServer {

  public static void main(String[] args) {
    Server.start(Server.PORT);
  }

}
