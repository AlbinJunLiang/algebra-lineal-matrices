package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import proyecto.Determinantes;
import proyecto.Fraccion;
import proyecto.Interseccion2Planos;
import proyecto.ValoresVectoresPropios;

/**
 * Clase de la interfaz grafica del proyecto, no se esta utilizando MVC como
 * surgerencia para otros desarrolladores.
 *
 * @author Albin Liang
 */
public class ProyectoGUI extends JFrame {

  private int contador = 2;
  // Crear un panel para contener los botones
  JPanel panel = new JPanel(new GridBagLayout());
  JPanel panelPiazarra = new JPanel(new GridBagLayout());
  JPanel entrada = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
  JPanel panelM = new JPanel();
  JPanel panelC = new JPanel();
  JScrollPane scrollPane;
  JTextArea pizarra = new JTextArea();
  private int botonSeleccionado;
  private JTextField[][] matrizTextField;
  private JTextField entradaPlanoA, entradaPlanoB;

  // Crear botones y agregarlos al panel
  JButton boton1 = new JButton("Interseccion entre dos planos");
  JButton boton2 = new JButton("Determinantes");
  JButton boton3 = new JButton("Valores y vectores propios");
  JButton botonAumentar = new JButton("Aumentar (NxN)");
  JButton botonDisminuir = new JButton("Disminuir (NxN)");
  JButton botonCalc = new JButton("Calcular");
  JButton botonPrincipal = new JButton("Salir");
  JButton botonLimpiar = new JButton("Limpiar");
  JLabel simbolo = new JLabel("<X,Y,Z,?>");
  JLabel etiqueta;

  public ProyectoGUI() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Proyecto de Algebra Lineal");
    setSize(900, 600);
    setLocationRelativeTo(null);  // Centrar en la pantalla
    inicializarComponentes();
    setVisible(true);
    agregarEventoBoton1();
    agregarEventoBoton2();
    agregarEventoBoton3();
    agregarEventoBtnPrincipal();
    agregarEventoBtnCalc();
    agregarEventoBtnAumentar();
    agregarEventoBtnDisminuir();
    agregarEventoBtnLimpiar();
  }

  private void agregarEventoBtnLimpiar() {
    botonLimpiar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        entradaPlanoA.setText("");
        entradaPlanoB.setText("");
      }
    });
  }

  private void agregarEventoBtnDisminuir() {
    botonDisminuir.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (contador > 2) {
          contador--;
        } else {
          contador = 4;
        }

        actualizarPanelMatriz();
        inicializarEntradaMatriz(contador, contador);
      }
    });
  }

  private void agregarEventoBtnAumentar() {
    botonAumentar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (contador < 4) {
          contador++;
        } else {
          contador = 2;
        }

        actualizarPanelMatriz();
        inicializarEntradaMatriz(contador, contador);
      }
    });

  }

  private void agregarEventoBtnCalc() {

    botonCalc.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (botonSeleccionado == 1) {
          opcionInters2Planos();
        }

        if (botonSeleccionado == 2) {
          opcionDeterminantes();
        }

        if (botonSeleccionado == 3) {
          opcionVVPropios();
        }
      }
    });
  }

  private void agregarEventoBtnPrincipal() {
    botonPrincipal.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setTitle("Proyecto de Algebra Lineal");
        actualizarPanelControlador();
        actualizarPanelMatriz();
        getContentPane().removeAll();
        setLayout(new GridLayout(1, 2));
        inicializarVistaPrincipal();
        setLayout(new GridLayout(1, 3));
        add(panel);
        add(panelPiazarra);
        repaint();
        revalidate();
      }
    });

  }

  private void agregarEventoBoton1() {
    boton1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setTitle("Ecuacion entre la interseccion de 2 planos");
        botonSeleccionado = 1;
        actualizarPanelMatriz();
        inicializarEntradaPlanos();
        getContentPane().removeAll();
        setLayout(new GridLayout(1, 2));
        add(entrada);
        add(scrollPane);
        entrada.add(panelM);
        entrada.add(panelC);
        panelC.add(botonCalc);
        panelC.add(botonPrincipal);
        panelC.add(botonLimpiar);
        repaint();
        revalidate();
      }
    });
  }

  private void agregarEventoBoton2() {
    boton2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setTitle("Calculo de determinantes");
        botonSeleccionado = 2;
        getContentPane().removeAll();
        setLayout(new GridLayout(1, 2));
        add(entrada);
        add(scrollPane);
        entrada.add(panelM);
        entrada.add(panelC);
        panelC.add(botonAumentar);
        panelC.add(botonDisminuir);
        panelC.add(botonCalc);
        panelC.add(botonPrincipal);
        inicializarEntradaMatriz(contador, contador);
        repaint();
        revalidate();
      }
    });
  }

  private void agregarEventoBoton3() {
    boton3.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setTitle("Valores y vectores propios");
        botonSeleccionado = 3;
        getContentPane().removeAll();
        setLayout(new GridLayout(1, 2));
        add(entrada);
        add(scrollPane);
        entrada.add(panelM);
        entrada.add(panelC);
        panelC.add(botonAumentar);
        panelC.add(botonDisminuir);
        panelC.add(botonCalc);
        panelC.add(botonPrincipal);
        inicializarEntradaMatriz(contador, contador);
        repaint();
        revalidate();
      }
    });
  }

  private void inicializarComponentes() {
    simbolo.setFont(new Font("Centhury Gotic", 3, 60));
    simbolo.setSize(300, 400);
    configurarBtnPrincipales();
    inicializarVistaPrincipal();
    setLayout(new GridLayout(1, 3));
    agregarPanelPrincipal();
    establecerFondoBtnControlador();
    inicializarPanelPizarra();
    configuarTamPanelM();
    configuarTamPanelC();
    entrada.setBackground(new Color(248, 248, 248));
    establecerPizarra(10);
    establecerIcono();
  }

  private void agregarPanelPrincipal() {
    panel.setBackground(new Color(248, 248, 248));
    add(panel);
  }

  private void inicializarPanelPizarra() {
    panelPiazarra.setBackground(new Color(137, 152, 203));
    add(panelPiazarra);
  }

  private void establecerIcono() {
    ImageIcon icono2 = null;
    icono2 = new ImageIcon(getClass().getResource("/recursos/iconn.png")); // Quita "src/" de la ruta
    java.awt.Image imagenIcono = icono2.getImage();
    setIconImage(imagenIcono);
  }

  private void configurarBtnPrincipales() {
    Dimension tamanioBtnPrincipal = new Dimension(300, 50); // Ajusta el tamaño según tus necesidades
    boton1.setPreferredSize(tamanioBtnPrincipal);
    boton1.setFont(new Font("Arial", 1, 18));
    boton1.setBackground(new Color(197, 187, 230));
    boton2.setPreferredSize(tamanioBtnPrincipal);
    boton2.setFont(new Font("Arial", 1, 18));
    boton2.setBackground(new Color(197, 187, 230));
    boton3.setPreferredSize(tamanioBtnPrincipal);
    boton3.setFont(new Font("Arial", 1, 18));
    boton3.setBackground(new Color(197, 187, 230));
  }

  private void establecerFondoBtnControlador() {
    botonAumentar.setBackground(new Color(197, 187, 230));
    botonDisminuir.setBackground(new Color(197, 187, 230));
    botonCalc.setBackground(new Color(197, 187, 230));
    botonPrincipal.setBackground(new Color(197, 187, 230));
    botonLimpiar.setBackground(new Color(197, 187, 230));
  }

  private void actualizarPanelMatriz() {
    panelM.removeAll();
    panelM.revalidate();
    panelM.repaint();
  }

  private void actualizarPanelControlador() {
    panelC.removeAll();
    panelC.revalidate();
    panelC.repaint();
  }

  private void inicializarVistaPrincipal() {
    ImageIcon icono = null;
    icono = new ImageIcon(getClass().getResource("/recursos/logoucr.png"));

    etiqueta = new JLabel(icono);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre los botones

    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(etiqueta, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(boton1, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(boton2, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    panel.add(boton3, gbc);
    GridBagConstraints gbc2 = new GridBagConstraints();
    gbc2.gridx = 0;
    gbc2.gridy = 0;
    gbc2.insets = new Insets(10, 10, 10, 10); // Espaciado entre los botones
    panelPiazarra.add(simbolo, gbc2);
  }

  private void inicializarEntradaPlanos() {
    GridBagConstraints gbcN = new GridBagConstraints();
    panelM.setLayout(new GridBagLayout());

    entradaPlanoA = new JTextField();
    entradaPlanoA.setPreferredSize(new Dimension(250, 40));
    entradaPlanoA.setFont(new Font("Dialog", 0, 20));
    entradaPlanoB = new JTextField();
    entradaPlanoB.setPreferredSize(new Dimension(250, 40));
    entradaPlanoB.setFont(new Font("Dialog", 0, 20));
    actualizarPanelMatriz();
    actualizarPanelControlador();
    gbcN.insets = new Insets(10, 10, 10, 10); // Espaciado entre los botones

    gbcN.gridx = 0;
    gbcN.gridy = 0;
    JLabel etiquetaA = new JLabel("Ingrese la ecuacion #1:");
    etiquetaA.setForeground(Color.LIGHT_GRAY);
    etiquetaA.setFont(new Font("Centhury Gothic", 1, 15));
    panelM.add(etiquetaA, gbcN);

    gbcN.gridx = 0;
    gbcN.gridy = 2;
    panelM.add(entradaPlanoA, gbcN);
    gbcN.gridx = 0;
    gbcN.gridy = 3;
    JLabel etiquetaB = new JLabel("Ingrese la ecuacion #2:");
    etiquetaB.setForeground(Color.LIGHT_GRAY);
    etiquetaB.setFont(new Font("Centhury Gothic", 1, 15));
    panelM.add(etiquetaB, gbcN);
    gbcN.gridx = 0;
    gbcN.gridy = 4;
    panelM.add(entradaPlanoB, gbcN);
  }

  private void inicializarEntradaMatriz(int filas, int columnas) {
    matrizTextField = new JTextField[filas][columnas];
    panelM.setLayout(new GridLayout(matrizTextField.length, matrizTextField[0].length));

    for (int i = 0; i < matrizTextField.length; i++) {
      for (int j = 0; j < matrizTextField[0].length; j++) {
        matrizTextField[i][j] = new JTextField();
        matrizTextField[i][j].setHorizontalAlignment(JTextField.CENTER);
        matrizTextField[i][j].setFont(new Font("Century Gothic", 0, 35));
        panelM.add(matrizTextField[i][j]);
      }
    }

    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void configuarTamPanelM() {
    panelM.setBackground(Color.DARK_GRAY);
    panelM.setPreferredSize(new Dimension(300, 300));

  }

  private void configuarTamPanelC() {
    panelC.setBackground(Color.DARK_GRAY);
    panelC.setPreferredSize(new Dimension(700, 200));
  }

  private void establecerPizarra(int margen) {
    scrollPane = new JScrollPane(pizarra);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    pizarra.setBorder(new EmptyBorder(margen, margen, margen, margen));
    pizarra.setFont(new Font("Dialog", 0, 20));
    pizarra.setEditable(false);
  }

  private void opcionVVPropios() {
    double[][] matrizDouble = new double[matrizTextField.length][matrizTextField[0].length];

    for (int i = 0; i < matrizTextField.length; i++) {
      for (int j = 0; j < matrizTextField[0].length; j++) {
        try {
          matrizDouble[i][j] = Double.parseDouble(Fraccion.convertirDecimal(matrizTextField[i][j].getText()));
        } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(this, "Ingrese números válidos en todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }
      }
    }
    ValoresVectoresPropios v = new ValoresVectoresPropios(matrizDouble);
    pizarra.setText(v.toString());
  }

  private void opcionDeterminantes() {
    double[][] matrizDouble = new double[matrizTextField.length][matrizTextField[0].length];

    for (int i = 0; i < matrizTextField.length; i++) {
      for (int j = 0; j < matrizTextField[0].length; j++) {
        try {
          matrizDouble[i][j] = Double.parseDouble(Fraccion.convertirDecimal(matrizTextField[i][j].getText()));
        } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(this, "Ingrese números válidos en todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }
      }
    }
    Determinantes d = new Determinantes(matrizDouble);

    if (matrizDouble.length == 3) {
      int opcion = JOptionPane.showConfirmDialog(null, "¿Quieres utilizar el metodo de Sarrus?", "Confirmación", JOptionPane.YES_NO_OPTION);

      // Verificar la respuesta del usuario
      if (opcion == JOptionPane.YES_OPTION) {
        d.detSarrus(d.getMatriz());
        pizarra.setText(d.getProcedimiento());

      } else {
        d.det(d.getMatriz());
        pizarra.setText(d.getProcedimiento());

      }
    } else {
      d.det(d.getMatriz());
      pizarra.setText(d.getProcedimiento());
    }
  }

  private void opcionInters2Planos() {
    Interseccion2Planos i = new Interseccion2Planos();
    if (!i.validarEcuacion(entradaPlanoA.getText())) {
      JOptionPane.showMessageDialog(this, "Verifique bien si la ecuacion #1 tiene la forma ax+by+cz=d.",
              "Notificacion", JOptionPane.INFORMATION_MESSAGE);

    }
    if (!i.validarEcuacion(entradaPlanoB.getText())) {
      JOptionPane.showMessageDialog(this, "Verifique bien si la ecuacion #2 tiene la forma ax+by+cz=d.",
              "Notificacion", JOptionPane.INFORMATION_MESSAGE);
    }
    try {
      pizarra.setText(i.obtenerRecta2Planos(entradaPlanoA.getText().replace(" ", ""),
              entradaPlanoB.getText().replace(" ", "")));
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Ingrese la ecuacion con valores correctos.",
              "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}
