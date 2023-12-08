
package proyecto;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Clase para obtener los valors o soluciones de una ecuacion igualada a 0,
 * especificamente de grado 2 a 4 . Para su obtencion se utiliza el metodo de
 * formula General, Cero Racionales y Newton-Raphson para ecuaciones de grado
 * superior a 3.
 *
 * @author Albin Liang
 */
public class ValoresExpresion {

  private double ecuacion[];
  public ArrayList<Double> valores = new ArrayList<>();

  public ValoresExpresion() {
  }

  public ValoresExpresion(double[] ecuacion) {
    this.ecuacion = ecuacion;
  }

  public double[] getEcuacion() {
    return ecuacion;
  }

  public void setEcuacion(double[] ecuacion) {
    this.ecuacion = ecuacion;
  }

  public ArrayList<Double> getValores() {
    return valores;
  }

  public void setValores(ArrayList<Double> valores) {
    this.valores = valores;
  }

  /**
   * Busca el ultimo numero de la expresion algebraica, se exluye como ultimo si
   * este mismo es 0. ejemplo si la ecuacion es x+0 elevado a la 2, se ignora
   * este y obtiene el x.
   *
   * @return
   */
  public double buscarUltimoNumero() {
    double numero = 0;
    for (int i = ecuacion.length - 1; i >= 0; i--) {
      if (ecuacion[i] != 0) {
        numero = ecuacion[i];
        break;
      }
    }
    return numero;
  }

  /**
   * Busca el primer numero de la expresion algebraica, se exluye como primero
   * si este mismo es 0. ejemplo si la ecuacion es 0x elevado a la 2 + 3y, se
   * ignora este y obtiene el siguiente (3y).
   *
   * @return
   */
  public double buscarPrimerNumero() {
    double numero = 0;
    for (int i = 0; i < ecuacion.length; i++) {
      if (ecuacion[i] != 0) {
        numero = ecuacion[i];
        break;
      }
    }
    return numero;
  }

  /**
   * Obtiene los posibles devisores de un numero tanto negativo como positivo.
   *
   * @param numero
   * @return
   */
  public ArrayList<Double> obtenerDivisores(double numero) {
    ArrayList<Double> divisores = new ArrayList<>();
    for (int i = 1; i <= Math.abs(numero); i++) {
      if (numero % i == 0) {
        divisores.add((double) i);
        if (i * -1 != i) {
          divisores.add((double) i * -1);
        }
      }
    }
    return divisores;
  }

  /**
   * Obtiene como posibles ceros en una lista la division de los divisores del
   * ultimo numero con los del primero.
   *
   * @return
   */
  public ArrayList<Double> obtenerPosiblesCeros() {
    ArrayList<Double> primerNumero = obtenerDivisores(buscarPrimerNumero());
    ArrayList<Double> ultimoNumero = obtenerDivisores(buscarUltimoNumero());
    HashSet<Double> posiblesCeros = new HashSet<>();
    for (double primeros : primerNumero) {
      for (double ultimos : ultimoNumero) {
        if (primeros != 0) {
          double posibleCero1 = ultimos / primeros;
          double posibleCero2 = -posibleCero1;
          posiblesCeros.add(posibleCero1);
          posiblesCeros.add(posibleCero2);
        }
      }
    }
    return new ArrayList<>(posiblesCeros);
  }

  /**
   * Evalua un polinomio, ejemplo si es x+2= 0 y si x es -2 seria -2+2=0. El
   * polinomio se adapta segun el grado de la ecuacion.
   *
   * @param x
   * @return
   */
  public double evaluarPolinomio(double x) {
    double resultado = 0.0;
    for (int i = 0; i < ecuacion.length; i++) {
      resultado += ecuacion[i] * Math.pow(x, ecuacion.length - 1 - i);
    }
    return resultado;
  }

  /**
   * Obtiene las soluciones de un polinomio de cuarto grado por medio de
   * Nwwton-Raphson
   *
   * @param a
   * @param b
   * @param c
   * @param d
   * @param e
   * @return
   */
  public ArrayList<Double> encontrarSolucionesCuartica(double a, double b, double c, double d, double e) {
    ArrayList<Double> valores = new ArrayList<>();

    // Aisla las raices reales en intervalos
    for (double x = -10; x < 10; x += 0.1) {
      double y1 = evaluarEcuacionCuartica(a, b, c, d, e, x);
      double y2 = evaluarEcuacionCuartica(a, b, c, d, e, x + 0.1);

      if (y1 * y2 < 0) {
        // Intervalo encontrado, aplica Newton-Raphson
        valores.add(aplicarNewtonRaphsonCuartica(a, b, c, d, x, x + 0.1, e));
      }
    }

    return valores;
  }

  /**
   * Evalua el polinomio de cuarto grado con el valor dado de x
   *
   * @param x Valor a evaluar
   * @return
   */
  public double evaluarEcuacionCuartica(double a, double b, double c, double d, double e, double x) {
    return a * Math.pow(x, 4) + b * Math.pow(x, 3) + c * Math.pow(x, 2) + d * x + e;
  }

  /**
   * Evalua el polinomio derivado del polinomio de cuarto grado con x
   *
   * @param x
   * @return
   */
  public double derivadaEcuacionCuartica(double a, double b, double c, double d, double x) {
    return 4 * a * Math.pow(x, 3) + 3 * b * Math.pow(x, 2) + 2 * c * x + d;
  }

  /**
   *
   * Se aplica el metodo de Newton-Raphson para buscar aproximaciones de 0 o las
   * raices de alguna funcion real.
   *
   * @return
   */
  public double aplicarNewtonRaphsonCuartica(double a, double b, double c, double d, double x0, double x1, double e) {
    double epsilon = 1e-10;

    double x = x0;
    while (Math.abs(evaluarEcuacionCuartica(a, b, c, d, e, x)) > epsilon) {
      x = x - evaluarEcuacionCuartica(a, b, c, d, e, x) / derivadaEcuacionCuartica(a, b, c, d, x);

      // Asegura que x este dentro del intervalo [x0, x1]
      x = Math.max(x, x0);
      x = Math.min(x, x1);
    }

    return x;
  }

  /**
   * Evalua el polinomio de grado 3 para elmetodo de Newton-Raphson
   *
   * @return
   */
  public double evaluarEcuacionCubica(double a, double b, double c, double d, double x) {
    return a * Math.pow(x, 3) + b * Math.pow(x, 2) + c * x + d;
  }

  /**
   * Evalua la derivada del polinomio de grado 3
   *
   * @return
   */
  public double derivadaEcuacionCubica(double a, double b, double c, double x) {
    return 3 * a * Math.pow(x, 2) + 2 * b * x + c;
  }

  /**
   *
   * Se aplica el metodo de Newton-Raphson para buscar aproximaciones de 0 o las
   * raices de alguna funcion real.
   *
   * @return
   */
  public double aplicarNewtonRaphsonCubica(double a, double b, double c, double d, double x0, double x1) {
    double epsilon = 1e-10;
    double x = x0;
    while (Math.abs(evaluarEcuacionCubica(a, b, c, d, x)) > epsilon) {
      x = x - evaluarEcuacionCubica(a, b, c, d, x) / derivadaEcuacionCubica(a, b, c, x);

      // Asegura que x este dentro del intervalo [x0, x1]
      x = Math.max(x, x0);
      x = Math.min(x, x1);
    }
    return x;
  }

  /**
   * Busca las soluciones posibles de un polinomo de grado 3 con el metodo de
   * Newton-Raphson
   *
   * @return
   */
  public ArrayList<Double> encontrarSolucionesCubica(double a, double b, double c, double d) {
    ArrayList<Double> valores = new ArrayList<>();
    // Aisla las raices reales en intervalos
    for (double x = -10; x < 10; x += 0.1) {
      double y1 = evaluarEcuacionCubica(a, b, c, d, x);
      double y2 = evaluarEcuacionCubica(a, b, c, d, x + 0.1);
      if (y1 * y2 < 0) {
        // Intervalo encontrado, aplica Newton-Raphson
        valores.add(aplicarNewtonRaphsonCubica(a, b, c, d, x, x + 0.1));
      }
    }
    return valores;
  }

  /**
   * Metodo principal del metodo de obtencion de ceso racionales se agrega los
   * valores en la lista a retornar los que al evaluar en el polinomio sean
   * cero.
   *
   * @return
   */
  public ArrayList<Double> obtValoresZeroRacional() {
    ArrayList<Double> posiblesCeros = obtenerPosiblesCeros();
    ArrayList<Double> valores = new ArrayList<>();

    for (double posibleCero : posiblesCeros) {
      if (evaluarPolinomio(posibleCero) == 0) {
        valores.add(posibleCero);
      }
    }
    if (valores.isEmpty()) {
      System.err.println("Ecuacion si soluciones validas");
    }
    return valores;
  }

  /**
   * Obtiene las soluciones de un polinomio de grado 2 por medio de la formula
   * general
   *
   * @return
   */
  public ArrayList<Double> obtValoresFormulaGeneral() {
    ArrayList<Double> valores = new ArrayList<>();
    double discriminante = ecuacion[1] * ecuacion[1] - 4 * ecuacion[0] * ecuacion[2];
    if (discriminante >= 0) {
      valores.add((-ecuacion[1] + Math.sqrt(discriminante)) / (2 * ecuacion[0]));
      valores.add((-ecuacion[1] - Math.sqrt(discriminante)) / (2 * ecuacion[0]));
    } else {
      System.out.println("La ecuación no tiene soluciones reales.");
    }
    return valores;
  }

  /**
   * Meto prinicipal para obtener los valores de un polinomio de grado 2 a 4.
   *
   * @return
   */
  public ArrayList<Double> obtValores() {
    valores = obtValoresZeroRacional();
    if (valores.isEmpty()) {
      if (ecuacion.length == 3) {
        valores = obtValoresFormulaGeneral();
      }
      if (ecuacion.length == 4) {
        valores = encontrarSolucionesCubica(ecuacion[0], ecuacion[1],
                ecuacion[2], ecuacion[3]);
      }
      if (ecuacion.length == 5) {
        valores = encontrarSolucionesCuartica(ecuacion[0],
                ecuacion[1], ecuacion[2], ecuacion[3], ecuacion[4]);
      }
    }
    return valores;
  }

  public String toString() {
    String procedimiento = "";
    if (!valores.isEmpty()) {
      procedimiento += "Se utilizara el metodo de cero racionales para obtener los valores:\n"
              + "Primer numero de la expresion :" + buscarPrimerNumero()
              + "\nUltimo numero de la expresion :" + buscarUltimoNumero() + "\n"
              + "Los divisores del primer numero: " + obtenerDivisores(buscarPrimerNumero()) + ""
              + "\nLos divisores del ultimo numero: " + obtenerDivisores(buscarUltimoNumero()) + ""
              + "\nLos posibles Ceros (Divsores Ultimos/Divisores Primeros) : " + obtenerPosiblesCeros()
              + "\nLos valores obtenidos por evaluacion son: ";
    } else if (ecuacion.length == 3) {
      procedimiento += "Valores obtenidos por medio de la formula general:\n";
    } else if (ecuacion.length >= 4) {
      procedimiento += "Valores obtneidos por medio del Metodo de Newton - Raphson:\n";
    }
    procedimiento += obtValores() + "\n";
    if (valores.isEmpty()) {
      procedimiento = "No ha solucion real para la expresion";
    }
    return procedimiento;
  }

}
