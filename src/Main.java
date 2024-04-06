import app.ui.GuiController;

import javax.swing.*;
import javax.swing.UIManager.*;


public class Main {
    public static void main(String[] args){

        try {
            System.out.println("available themes in this system:");
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                System.out.print(info.getName() + " ");
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    //break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        System.out.println("");
        SwingUtilities.invokeLater(GuiController::init);


    }
}
