import app.ui.GuiController;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            GuiController.init();
        });


    }
}
