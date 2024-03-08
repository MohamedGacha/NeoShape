import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SimpleWindow {
    private static Canvas canvas; // Declare canvas as a class variable to make it accessible
    private static Color chosenColor = Color.WHITE; // Store the chosen color

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleWindow::createAndShowWindow);
    }

    private static void createAndShowWindow() {
        JFrame frame = new JFrame("NeoShape");

        // Set the dimensions of the window
        int width = 800;
        int height = 600;
        frame.setSize(new Dimension(width, height));

        // Set the window to close the application when closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        JButton squareButton1 = new JButton("Form 1");
        JButton squareButton2 = new JButton("Form 2");
        JButton squareButton3 = new JButton("Form 3");

        // Set button dimensions and appearance
        Dimension buttonDimension = new Dimension(50, 50); // Increase the dimensions for larger buttons
        squareButton1.setPreferredSize(buttonDimension);
        squareButton2.setPreferredSize(buttonDimension);
        squareButton3.setPreferredSize(buttonDimension);
        squareButton1.setFont(new Font("Arial", Font.PLAIN, 12));
        squareButton2.setFont(new Font("Arial", Font.PLAIN, 12));
        squareButton3.setFont(new Font("Arial", Font.PLAIN, 12));

        // Create a panel for the left buttons
        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // Left-aligned flow layout with 5px horizontal and vertical gaps
        leftButtonPanel.add(squareButton1);
        leftButtonPanel.add(squareButton2);
        leftButtonPanel.add(squareButton3);

        // Create a panel for the right color chooser button
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5)); // Right-aligned flow layout with 5px horizontal and vertical gaps

        // Create a button for choosing canvas color
        JButton colorChooserButton = new JButton();
        colorChooserButton.setPreferredSize(new Dimension(50, 50)); // Set dimensions for the color chooser button
        colorChooserButton.setBackground(chosenColor); // Set initial background color
        colorChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(frame, "Choose Canvas Color", chosenColor);
                if (selectedColor != null) {
                    chosenColor = selectedColor;
                    canvas.setBackground(chosenColor); // Change the background color of the canvas
                    colorChooserButton.setBackground(chosenColor); // Change the background color of the button
                }
            }
        });
        rightButtonPanel.add(colorChooserButton);

        // Create the canvas
        canvas = new Canvas();
        canvas.setBackground(chosenColor); // Set the initial background color of the canvas

        // Add left and right button panels to the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(leftButtonPanel, BorderLayout.WEST);
        mainPanel.add(rightButtonPanel, BorderLayout.EAST);

        // Add components to the frame
        frame.add(mainPanel, BorderLayout.NORTH);
        frame.add(canvas, BorderLayout.CENTER);

        // Make the window visible
        frame.setVisible(true);
    }
}
