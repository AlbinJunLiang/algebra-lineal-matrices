package proyecto;

import java.util.ArrayList;

/**
 * @author Albin Liang
 *
 * Clase utilizada para obtener las soluciones de una ecuacion por medio del
 * metodo de Gauss-Jordan.
 *
 * Funciona para sistemas de ecuaciones de mas de 5 variables.
 */
public class GaussJordan {



  public String estado = "";
// Guarda el resultado en caso de que no haya solucion
  public int pasos = 0;
// Contador de los pasos
  public double matriz[][];
  public String constantes[] = {"a", "b", "c", "d", "e", "f", "o", "x"};
  public String parametros[] = {"t", "w", "r", "v", "f"};

  public GaussJordan(double[][] matriz) {
    this.matriz = matriz;
  }

  public GaussJordan() {
  }

  public double[][] getMatriz() {
    return matriz;
  }

  public void setMatriz(double[][] matriz) {
    this.matriz = matriz;
  }

  public String[] getConstantes() {
    return constantes;
  }

  public void setConstantes(String[] constantes) {
    this.constantes = constantes;
  }

  public String[] getParametros() {
    return parametros;
  }

  public void setParametros(String[] parametros) {
    this.parametros = parametros;
  }

  /**
   * Metodo principal para el calculo del Gauss - Jordan
   *
   * @return
   */
  public String gaussJordan() {
    String salida = "";
    int filas = matriz.length;
    for (int i = 0; i < filas; i++) {
      // Paso 1: Hacer que el pivote sea igual a 1
      if (matriz[i][i] == 0) {
        // Buscar una fila no nula para intercambiar
        for (int k = i + 1; k < filas; k++) {
          if (matriz[k][i] != 0) {
            // Intercambiar filas i y k
            intercambiarFilas(i, k);
            salida += ("Paso " + (pasos) + ": Intercambio de filas: F"
                    + Fraccion.convertirAFraccion(i + 1) + " <---------> F"
                    + Fraccion.convertirAFraccion(k + 1) + "\n");
            salida += imprimirMatriz() + "\n";
            pasos++;
            break;
          }
        }
      }
      double pivote = matriz[i][i];
      if (pivote != 0) {
        dividirFilas(pivote, i);

        salida += "Paso " + (pasos++) + ": Dividir la fila: F" + Fraccion.convertirAFraccion(i + 1)
                + " / " + Fraccion.convertirAFraccion(pivote) + "\n";
        salida += imprimirMatriz() + "\n";

      }
      // Paso 2: Hacer ceros en las otras filas
      salida += hacerCeros(i) + "\n";
    }
    return salida;
  }

  /**
   * Dividir los valores de la fila de la matriz para obtener el 1, segun donde
   * esta el pivote
   *
   * @param pivote
   * @param fila
   */
  public void dividirFilas(double pivote, int fila) {
    for (int j = 0; j < matriz[fila].length; j++) {
      matriz[fila][j] /= pivote;
    }
  }

  /**
   * Hacer ceros para formar la columna y despues la matriz identidad.
   *
   * @param i
   * @return
   */
  public String hacerCeros(int i) {
    String salida = "";
    for (int k = 0; k < matriz.length; k++) {
      if (k != i && matriz[k][i] != 0) {
        double factor = matriz[k][i];
        for (int j = 0; j < matriz[i].length; j++) {
          matriz[k][j] -= factor * matriz[i][j];
        }
        salida += ("Paso " + (pasos++) + ": Restar " + Fraccion.convertirAFraccion(factor)
                + " veces la fila " + Fraccion.convertirAFraccion(i + 1) + " de la fila "
                + Fraccion.convertirAFraccion(k + 1) + "\n");
        salida += imprimirMatriz() + "\n";

      }
    }
    return salida;
  }

  /**
   * En casos de que sea necesario se hace la operacion de intercambio de filas
   * en el sistema de ecuaciones.
   *
   * @param fila1
   * @param fila2
   */
  public void intercambiarFilas(int fila1, int fila2) {
    double[] temp = matriz[fila1];
    matriz[fila1] = matriz[fila2];
    matriz[fila2] = temp;
  }

  public String imprimirMatriz() {
    String salida = "";
    int filas = matriz.length;
    int columnas = matriz[0].length;

    for (int i = 0; i < filas; i++) {
      for (int j = 0; j < columnas; j++) {
        salida += Fraccion.convertirAFraccion(Double.parseDouble(String.format("%.2f", matriz[i][j]))) + "\t";
      }
      salida += "\n";
    }
    return salida;
  }

  /**
   * Valida si hay una fila de ceros para saber si hay una variable libre o de
   * infinita solucion
   *
   * @param matriz
   * @param fila
   * @return
   */
  public boolean filaCeros(double matriz[][], int fila) {
    boolean salida = false;
    for (int i = 0; i < matriz[0].length; i++) {
      if (matriz[fila][i] == 0) {
        salida = true;
      } else {
        salida = false;
        break;
      }
    }
    return salida;
  }

  /**
   * Valida si el sistema de ecuacion no tiene solucion
   *
   * @return
   */
  public boolean validarSistemaIncosistente() {
    boolean incosistente = false;
    for (int i = 0; i < matriz.length; i++) {
      if (noHaySolucion(matriz, i)) {
        incosistente = true;
        break;
      }
    }
    return incosistente;
  }

  /**
   * Valida si en la fila hay una desigualdad como 6 = 0, en el cual representa
   * como sistema sin solucion.
   *
   * @param matriz
   * @param fila
   * @return
   */
  public boolean noHaySolucion(double matriz[][], int fila) {
    boolean salida = false;
    boolean noHaySolucion = false;
    for (int i = 0; i < matriz[0].length; i++) {
      if (i < matriz[0].length - 1) {
        if (matriz[fila][i] == 0) {
          salida = true;
        } else {
          salida = false;
          break;
        }
      }
    }

    if (salida) {
      if (matriz[fila][matriz[0].length - 2] != matriz[fila][matriz[0].length - 1]) {
        noHaySolucion = true;
      }
    }
    return noHaySolucion;
  }

  /**
   * Obtiene la solucion de la constantes, se especifca en la fila como
   * parametro
   *
   * @param fila
   * @param conjuntosConstantes
   * @return
   */
  public String obtSolucionConstante(int fila, String conjuntosConstantes[]) {
    String salida = "";
    String letra = "";
    for (int j = 0; j < matriz[0].length - 1; j++) {
      if (j < matriz[0].length - 1) {

        if (matriz[fila][fila] == 1 || matriz[fila][fila] == 0) {
          letra = conjuntosConstantes[fila];
        }
        if (matriz[fila][fila] != matriz[fila][j]) {
          if (matriz[fila][j] != 0) {
            salida += "+" + Fraccion.convertirAFraccion(matriz[fila][j] * -1) + conjuntosConstantes[j];
          }

        }
      }
    }
    if (!salida.trim().equals("")) {
      salida = letra + " = " + Fraccion.convertirAFraccion(matriz[fila][matriz[0].length - 1]) + "" + salida;
    } else if (salida.trim().equals("") && !filaCeros(matriz, fila)) {
      salida += letra + " = " + Fraccion.convertirAFraccion(matriz[fila][matriz[0].length - 1]);
    } else if (filaCeros(matriz, fila)) {
      salida += letra + " = " + parametros[fila];
    }

    salida = salida.replace("+-", "-");
    salida = salida.replace("--", "+");
    return salida;
  }

  /**
   * Concatena la solucion de cada constantes del sistema de ecuaciones en una
   * sola cadena de texto.
   *
   * @return
   */
  public String obtenerSoluciones() {
    String salida = "";
    if (validarSistemaIncosistente()) {
      return "El sistema es incosistente";
    }
    for (int i = 0; i < matriz.length; i++) {
      salida += obtSolucionConstante(i, constantes) + "\n";
    }
    return salida;
  }

  /**
   * Obtiene el conjunto de soluciones en una lista o ArrayList por si se desea
   * utilizarlo.
   *
   * @return
   */
  public ArrayList<String> obtConjuntoSoluciones() {
    ArrayList lista = new ArrayList<String>();
    String temporal = "";
    if (validarSistemaIncosistente()) {
      lista.add("\u2204 Solutions");
      return lista;
    }
    for (int i = 0; i < matriz.length; i++) {
      temporal = obtSolucionConstante(i, constantes).replace(" ", "");
      temporal = temporal.substring(temporal.indexOf("=") + 1, temporal.length());
      lista.add(temporal);
    }
    return lista;
  }

  public String toString() {
    String salida = "Matriz original:\n";
    salida += imprimirMatriz() + "\n";
    salida += gaussJordan();
    salida += "\n";
    salida += obtenerSoluciones() + "\n O "
            + obtConjuntoSoluciones() + "\\"
            + '\u2208' + '\u211D' + "\n";
    return salida;
  }
}
