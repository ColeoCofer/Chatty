package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import server.Server;

public class ClientChatWindow {

  private JFrame frame;
  private JTextField messageField;
  private static JTextArea textArea = new JTextArea();
  private Client client;

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          ClientChatWindow window = new ClientChatWindow();
          window.frame.setVisible(true);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    });
  }

  public ClientChatWindow() {
    initialize();
    String name = JOptionPane.showInputDialog("Enter name");
    client = new Client(name, "localhost", Server.PORT);
  }

  public static void printToConsole(String message) {
    textArea.setText(textArea.getText() + message + "\n");
  }

  private void initialize() {
    frame = new JFrame();
    frame.setResizable(false);
    frame.setTitle("Chatty");
    frame.setBounds(100, 100, 650, 475);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BorderLayout(0, 0));

    textArea.setEditable(false);

    JScrollPane scrollPane = new JScrollPane(textArea);
    frame.getContentPane().add(scrollPane);

    JPanel panel = new JPanel();
    frame.getContentPane().add(panel, BorderLayout.SOUTH);
    panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

    messageField = new JTextField();
    panel.add(messageField);
    messageField.setColumns(45);

    JButton sendButton = new JButton("Send");
    panel.add(sendButton);

    //Send Button Clicked
    sendButton.addActionListener(e -> {
      if (!messageField.getText().equals("")) {
        client.send(messageField.getText());
        messageField.setText("");
      }
    });

    frame.setLocationRelativeTo(null);
  }

}
