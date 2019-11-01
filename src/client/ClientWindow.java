package client;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class ClientWindow {

  private static JFrame frame;
  private JPanel panel1;
  private JTextArea textArea1;
  private JButton sendButton;
  private JTextField chatTextField;

  public ClientWindow() {
    initialize();
  }

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      frame = new JFrame("Chatty");
      frame.setContentPane(new ClientWindow().panel1);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void initialize() {
    frame.setLocationRelativeTo(null);
  }

}
