import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Provides a UI to enter inputs into to provide cleaner interactions with the regex engine.
 */
public class UserInterface {
  /**
   * Builds frame, boxes and buttons to interact with regex engine. Takes regex and input strings
   * as inputs for the text boxes.
   *
   * @param args not used
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Simple Regex Engine");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(450, 220);
      frame.setLayout(new GridLayout(5, 1, 5, 5));

      JTextField regexField = new JTextField();
      JTextField inputField = new JTextField();
      JLabel resultLabel = new JLabel("Result: ", SwingConstants.CENTER);
      JButton testButton = new JButton("Test Match");

      // Button click behavior
      testButton.addActionListener(buttonPressed -> {
        String regex = regexField.getText().trim();
        String input = inputField.getText().trim();
        if (!regex.isEmpty()) {
          boolean match = EngineCall.engineCall(regex, input);
          if (match) {
            resultLabel.setText("Result: Match");
          } else {
            resultLabel.setText("Result: No match");
          }
        }
      });

      frame.add(new JLabel("Enter Regex:", SwingConstants.CENTER));
      frame.add(regexField);
      frame.add(new JLabel("Enter Input:", SwingConstants.CENTER));
      frame.add(inputField);
      frame.add(testButton);
      frame.add(resultLabel);

      frame.setVisible(true);
    });
  }
}