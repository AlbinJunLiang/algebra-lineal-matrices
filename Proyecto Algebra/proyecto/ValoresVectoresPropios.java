package proyecto;

import java.util.ArrayList;

/**
 * Clase para obtene los valores y vectores propios de una matriz.
 *
 * @author Albin Liang
 */
public class ValoresVectoresPropios {

  public double matriz[][];
  public EcuacionCaracteristica ecuacionCaracteristica;
  public ValoresExpresion valoresExpresion;
  public GaussJordan gaussJordan;
  public String vector[] = {"a", "b", "c", "d", "e", "f", "o", "x"};

  public ValoresVectoresPropios(double[][] matriz) {
    this.matriz = matriz;
    ecuacionCaracteristica = new EcuacionCaracteristica(matriz);
    valoresExpresion = new ValoresExpresion();
    gaussJordan = new GaussJordan();

  }

  public String[] getVector() {
    return vector;
  }

  public void setVector(String[] vector) {
    this.vector = vector;
  }

  /**
   * Metodo principal para la obtencion de valores propios de [2,4]
   *
   * @return
   */
  public String obtenerValoresPropios() {
    double coeficientes[];
    String salida = "";
    if (matriz.length == 2) {
      coeficientes = ecuacionCaracteristica.ecuacionCaracteristica2X2();
      valoresExpresion.setEcuacion(coeficientes);
    } else if (matriz.length == 3) {
      coeficientes = ecuacionCaracteristica.ecuacionCaracteristica3X3();
      valoresExpresion.setEcuacion(coeficientes);
    } else if (matriz.length == 4) {
      coeficientes = ecuacionCaracteristica.ecuacionCaracteristica4X4();
      valoresExpresion.setEcuacion(coeficientes);
    } else {
      System.out.println("Solucion inexistente en este sistema");
    }

    return salida;
  }

  public String mostrarMatriz(String texto) {
    String salida = "";
    for (int i = 0; i < matriz.length; i++) {
      for (int j = 0; j < matriz[i].length; j++) {
        if (i == j) {
          salida += String.format("%.2f", matriz[i][j]) + texto + "\t";

        } else {
          salida += String.format("%.2f\t", matriz[i][j]);

        }
      }
      salida += "\n";
    }
    return salida;
  }

  /**
   * Muestra una cadena en forma de matriz identidad de acuerdo al tamano de la
   * matriz a calcular
   *
   * @param texto
   * @return
   */
  public String mostrarIdentidad(String texto) {
    String salida = "";
    for (int i = 0; i < matriz.length; i++) {
      for (int j = 0; j < matriz[i].length; j++) {
        if (i == j) {
          salida += "1" + texto + "\t";
        } else {
          salida += "0" + "\t";

        }
      }
      salida += "\n";
    }
    return salida;
  }

  /**
   * Muestra la cadena del sistema de ecuacion formado a calcular con
   * Gauss-Jordan
   *
   * @param lambda
   * @return
   */
  public String verSistemaEcuaVectorPropio(double lambda) {
    String salida = "";
    String valor = "";
    for (int i = 0; i < matriz.length; i++) {
      for (int j = 0; j < matriz[i].length; j++) {
        valor = Fraccion.convertirAFraccion(matriz[i][j]);
        if (j == 0) {
          salida += "{+" + valor + vector[j] + "\t";

        } else {
          salida += "+" + valor + vector[j] + "\t";

        }
      }
      salida += "= " + lambda + vector[i] + "\n";
    }
    return salida;
  }

  /**
   * Obtiene el sistema de ecuacion para obtener vectores propios pero los
   * valores de la columna resultante se para al lado de izquierso de la
   * ecuacion por despeje y se resta su constante perteneciente.
   *
   *
   * Osea si la fila 1 es 1,0,0 = 3x. Se pasa el 3 al otro lado restando o
   * sumando ya sea su signo.
   *
   * @param lambda
   * @return
   */
  public double[][] obtSistemaRestaConLambda(double lambda) {
    double a[][] = new double[matriz.length][matriz[0].length + 1];
    for (int i = 0; i < matriz.length; i++) {
      for (int j = 0; j < matriz[i].length; j++) {
        if (j < matriz[0].length) {
          if (i == j) {
            a[i][j] = matriz[i][j] - lambda;
          } else {
            a[i][j] = matriz[i][j];
          }
        } else {
          a[i][j] = 0;
        }
      }
    }
    return a;
  }

  /**
   * Obtiene los vectores propios de cada valor de lambda encontrado.
   *
   * @return
   */
  public String obtenerVectoresPropios() {
    String salida = "";
    ArrayList<Double> valores = valoresExpresion.getValores();
    double lambda = 0;
    for (int i = 0; i < valores.size(); i++) {
      lambda = valores.get(i);
      gaussJordan.setMatriz(obtSistemaRestaConLambda(lambda));
      salida += "Vector propio con Lambda (\u03BB) = " + lambda + "\n";
      salida += verSistemaEcuaVectorPropio(lambda) + "\n";
      salida += gaussJordan + "\n";
    }
    return salida;
  }

  public String toString() {
    String procedimiento = "|A-" + "\u03BBI"
            + "| = 0\n";
    procedimiento += "Matriz original:\n" + mostrarMatriz("");
    procedimiento += "\nMatriz identidad:\n" + mostrarIdentidad("");
    procedimiento += "\nIdentidad x lambda: \n" + mostrarIdentidad("\u03BB") + "\n";
    procedimiento += "det (|A-\u03BBI|)\n" + mostrarMatriz("-" + "\u03BB")
            + "\n";
    obtenerValoresPropios();
    procedimiento += "Ecuacion caracteristica:\n" + ecuacionCaracteristica;
    procedimiento += "\n\nValores de \u03BB:\n"
            + "" + valoresExpresion;
    procedimiento += "\n>Vectores propios (Av = \u03BBv):\n";
    procedimiento += obtenerVectoresPropios();
    return procedimiento;
  }
}
