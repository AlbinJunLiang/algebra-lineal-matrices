package Principal;

import javax.swing.SwingUtilities;
import vista.ProyectoGUI;

public class Main {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(ProyectoGUI::new);
  }
}
