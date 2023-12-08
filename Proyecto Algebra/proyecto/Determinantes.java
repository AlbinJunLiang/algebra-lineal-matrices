package proyecto;

import java.util.ArrayList;

/**
 * Clase para el calculo de determinates, solo esta para 2x2, 3x3 (ya sea con o
 * sin Sarrus) y para el 4x4.
 *
 * @author Albin Liang
 */
public class Determinantes {

  private double matriz[][];
  private String procedimiento;

  public Determinantes(double[][] matriz) {
    procedimiento = "Calculo de determinate: " + matriz.length + "x" + matriz.length + "\n";
    this.matriz = matriz;
  }

  public double[][] getMatriz() {
    return matriz;
  }

  public void setMatriz(double[][] matriz) {
    this.matriz = matriz;
  }

  public String getProcedimiento() {
    return procedimiento;
  }

  public void setProcedimiento(String procedimiento) {
    this.procedimiento = procedimiento;
  }

  /**
   * Calcula el determinante de las matrices 2x2 y guarda el procedimiento en
   * una variable global de tipo String
   *
   * @param matriz
   * @return
   */
  public double calcularDeterminante2x2(double matriz[][]) {

    double valorDet = 0;
    if (matriz.length == 2) {
      procedimiento += "(" + matriz[0][0] + " * " + matriz[1][1] + ") - (" + matriz[0][1] + " * " + matriz[1][0] + ")\n";
      valorDet = matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0];

      procedimiento += "Valor del determinante 2x2: " + Fraccion.convertirAFraccion(valorDet) + "\n";
      procedimiento += "-----------------------------------------------------------------------------------2X2\n";
    }
    return valorDet;
  }

  /**
   * Calcula la determinante de la matriz 3x3, funciona recorriendo la primera
   * fila y en cada elemento de esa fila se alterna de +-+ para luego obtener
   * una submatriz al excluir los elementos de fila 0 y columna 0, y por ultimo
   * cada submatriz obtenida se utilizara en el metodo de
   * calcularDeterminate2x2.
   *
   * @param matriz
   * @return
   */
  public double calcularDeterminante3x3(double matriz[][]) {
    double signo = 1;
    double valorDet = 0;
    double temp = 0;
    double m[][];
    for (int i = 0; i < matriz.length; i++) {
      for (int j = 0; j < matriz.length; j++) {
        if (i == 0) {
          if (j % 2 == 0) {
            signo = matriz[i][j];
          } else {
            signo = -matriz[i][j];
          }
          //      procedimiento += "" + 
          temp = calcularDeterminante2x2(m = obtSubMatriz(matriz, j));
          procedimiento += "" + mostrarMatriz(m) + "\n";
          procedimiento += "Con el cofactor:\n";
          procedimiento += matriz[i][j] + "*(" + temp + ")\n";
          valorDet += signo * temp;
        }
      }
      procedimiento += "\n";
    }
    procedimiento += "Resultado del determinante 3x3: " + Fraccion.convertirAFraccion(valorDet)
            + "\n";
    procedimiento += "+++++++++++++++++++++++++++++++++++++++++++++++++++3X3\n";

    return valorDet;
  }

  /**
   * Se selecciona la primera fila, luego se obtiene una submatriz que exluye
   * los valores de la fila y columna que esta en el valor de la primera fila,
   * este proceso aplica para los 4 valores, aunque sus signo se alternan de
   * (+-+-). Luego se llama el metodo calcularDeterminante3x3 para calcular la
   * determinate de la submatriz.
   *
   * @param matriz
   * @return
   */
  public double calcularDeterminante4x4(double matriz[][]) {
    double signo = 1;
    double valorDet = 0;
    double temp = 0;
    double m[][];
    procedimiento += mostrarMatriz(matriz) + "\n";
    for (int i = 0; i < matriz.length; i++) {
      for (int j = 0; j < matriz.length; j++) {
        if (i == 0) {
          if (j % 2 == 0) {
            signo = matriz[i][j];
          } else {
            signo = -matriz[i][j];

          }
          //      procedimiento += "" + 
          temp = calcularDeterminante3x3(m = obtSubMatriz(matriz, j));
          procedimiento += "Matriz 3x3:\n" + mostrarMatriz(m) + "\n";
          procedimiento += "Con el cofactor:\n";
          procedimiento += Fraccion.convertirAFraccion(matriz[i][j]) + "*(" + Fraccion.convertirAFraccion(temp)
                  + ")\n";
          valorDet += signo * temp;
        }
      }
      procedimiento += "\n";
    }
    procedimiento += "Resultado del determinante 4x4: " + Fraccion.convertirAFraccion(valorDet)
            + "\n";
    return valorDet;
  }

  /**
   *
   * @param matriz
   * @param columna Al escoger simepre la primera fila para obtener el
   * determinate, la columna es para especificar a cual elemento se va iniciar
   * el proceso de obtencion de la submatriz. Digamos si columna es 3 entonces a
   * partir del valor [0][3] se excluye su fila y columna y el resto de valores
   * van para la submatriz.
   * @return
   */
  public double[][] obtSubMatriz(double matriz[][], int columna) {
    double subMatriz[][] = new double[matriz.length - 1][matriz.length - 1];
    ArrayList<Double> lista = new ArrayList<>();
    for (int i = 0; i < matriz.length; i++) {
      for (int j = 0; j < matriz.length; j++) {
        if (i != 0 && j != columna) {
          lista.add(matriz[i][j]);
        }
      }
    }
    int k = 0;
    for (int i = 0; i < subMatriz.length; i++) {
      for (int j = 0; j < subMatriz.length; j++) {
        subMatriz[i][j] = lista.get(k);
        k++;
      }
    }
    return subMatriz;
  }

  /**
   * Calcular la determinante de matrices 3x3 por medio de Sarrus
   *
   * @param matriz
   * @return
   */
  public double detSarrus(double matriz[][]) {
    double valorDet = 0;
    procedimiento += "Determinante por el metodo Sarrus:\n";
    procedimiento += mostrarMatriz(matriz) + "\n";
    valorDet = ((matriz[0][0] * matriz[1][1] * matriz[2][2])
            + (matriz[0][1] * matriz[1][2] * matriz[2][0])
            + (matriz[0][2] * matriz[1][0] * matriz[2][1]))
            - ((matriz[2][0] * matriz[1][1] * matriz[0][2])
            + (matriz[2][1] * matriz[1][2] * matriz[0][0])
            + (matriz[2][2] * matriz[1][0] * matriz[0][1]));

    procedimiento += " ((" + matriz[0][0] + " * " + matriz[1][1] + " * " + matriz[2][2] + ") + "
            + "(" + matriz[0][1] + " * " + matriz[1][2] + " * " + matriz[2][0] + ") + "
            + " (" + matriz[0][2] + " * " + matriz[1][0] + " * " + matriz[2][1] + "))";

    procedimiento += " - \n"
            + "((" + matriz[2][0] + " * " + matriz[1][1] + " * " + matriz[0][2] + ") + "
            + "(" + matriz[2][1] + " * " + matriz[1][2] + " * " + matriz[0][0] + ") + "
            + " (" + matriz[2][2] + " * " + matriz[1][0] + " * " + matriz[0][1] + ")) = " + valorDet;
    return valorDet;
  }

  /**
   * Metodo principal del calculo de determinantes
   *
   * @param matriz
   * @return
   */
  public double det(double matriz[][]) {
    double valorDet = 0;
    if (matriz.length == 2) {
      valorDet = calcularDeterminante2x2(matriz);
    } else if (matriz.length == 3) {
      valorDet = calcularDeterminante3x3(matriz);
    } else if (matriz.length == 4) {
      valorDet = calcularDeterminante4x4(matriz);
    }
    return valorDet;
  }

  public String mostrarMatriz(double matriz[][]) {
    String salida = "";
    for (double i[] : matriz) {
      for (double j : i) {
        salida += j + "\t";
      }
      salida += "\n";
    }
    return salida;
  }
}
