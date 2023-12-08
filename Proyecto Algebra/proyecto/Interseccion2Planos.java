package proyecto;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase para obtener la ecuacion de la recta formado por la interseccion de 2
 * planos, si son paralelos los planos, no existe la interseccion
 *
 * Los planos son de la forma ax+by+cz=d
 *
 * @author Albin Liang
 */
public class Interseccion2Planos {

  /**
   * Divide la ecuacion por terminos es decir si es 5x+3y = 5, se guardara los
   * terminos separado de la cadena en [5x,3y,=5].
   *
   * @param plano
   * @param separadores
   * @return
   */
  private static ArrayList<String> dividirTerminos(String plano, String separadores) {
    ArrayList<String> lista = new ArrayList<>();
    int inicio = 0;
    for (int i = 0; i < plano.length(); i++) {
      char letra = plano.charAt(i);

      if (separadores.indexOf(letra) != -1) {
        if (i > 0 && plano.charAt(i - 1) != '-') {
          lista.add(plano.substring(inicio, i));
          inicio = i;
        }
      }
    }
    lista.add(plano.substring(inicio).trim()); //Con el trim para que elimine espacios en blanco c:
    return lista;
  }

  /**
   * Valida la existencia de una subcadena en una cadena
   *
   * @param cadena
   * @param subCadena
   * @return
   */
  private static boolean siExiste(String cadena, String subCadena) {
    return cadena.indexOf(subCadena) != -1;
  }

  /**
   * Elimina los signos de +,= en cada termino
   *
   * @param terminosDivididos
   * @return
   */
  private static ArrayList<String> limpiarTerminos(ArrayList<String> terminosDivididos) {
    ArrayList<String> salida = new ArrayList<>();
    String temporal = "";
    String token = "";
    for (int i = 0; i < terminosDivididos.size(); i++) {
      temporal = "";
      token = terminosDivididos.get(i);
      for (int j = 0; j < token.length(); j++) {
        if (token.charAt(j) != '=' && token.charAt(j) != '+') {
          temporal += token.charAt(j) + "";
        }
      }
      salida.add(temporal);
    }
    return salida;
  }

  /**
   * Al ser ecuaciones de la forma ax+by+cz=d, si se ingresa como by+c+d=cz se
   * ordena como es en el formato del plano: 0x+by-cz= -d.
   *
   * @param plano
   * @return
   */
  private static String[] ordenarEcuacion(String plano) {
    plano = transposcionTerminos(plano);
    ArrayList<String> terminosDivididos = limpiarTerminos(dividirTerminos(plano, "+-="));
    String vector[] = new String[4];
    String token = "";
    double a = 0;
    for (int i = 0; i < terminosDivididos.size(); i++) {
      token = terminosDivididos.get(i);

      if (siExiste(token, "x")) {
        vector[0] = token;
      } else if (siExiste(token, "y")) {
        vector[1] = token;

      } else if (siExiste(token, "z")) {
        vector[2] = token;

      } else if (!token.trim().equals("")) {
        if (token.contains("/")) {
          token = Fraccion.convertirDecimal(token);
        }
        a = a + Double.parseDouble(token.trim());
        vector[3] = a + "";
      }
    }
    return vector;
  }

  /**
   * Transpone los termino osea si pasa los terminos del otro lado su signo se
   * invierten.
   *
   * Ejemplo 2x-3=4, si quiero para -3 al lado derecho seria 2x=4+3
   *
   * @param plano
   * @return
   */
  public static String transposcionTerminos(String plano) {
    return transposicionIzquierda(plano) + "=" + transposicionDerecha(plano);
  }

  /**
   * Transpone los signos del miembro derecho que van para el lado izquierdo
   *
   * @param plano
   * @return
   */
  private static String transposicionDerecha(String plano) {
    String miembroDerecho = plano.substring(plano.indexOf("=") + 1, plano.length());
    ArrayList<String> terminosDivididos = (dividirTerminos(miembroDerecho, "+-="));
    String salida = "";
    String termino = "";
    for (int i = 0; i < terminosDivididos.size(); i++) {
      termino = terminosDivididos.get(i);
      if (termino.contains("x") || termino.contains("y") || termino.contains("z")) {
        System.out.println("r");
        if (termino.contains("+")) {
          salida += termino.replace("+", "-");
        } else if (termino.contains("-")) {
          salida += termino.replace("-", "+");
        } else {
          salida += "-" + termino;
        }
      } else {
        salida += termino;
      }

    }
    return salida;
  }

  /**
   * Transpone los signos del miembro izquierdo que van para el lado derecho
   *
   * @param plano
   * @return
   */
  private static String transposicionIzquierda(String plano) {
    String miembroIzquierdo = plano.substring(0, plano.indexOf("="));
    ArrayList<String> terminosDivididos = (dividirTerminos(miembroIzquierdo, "+-="));
    String salida = "";
    String termino = "";
    for (int i = 0; i < terminosDivididos.size(); i++) {
      termino = terminosDivididos.get(i);
      if (!termino.contains("x") && !termino.contains("y") && !termino.contains("z")) {
        if (termino.contains("+")) {
          salida += termino.replace("+", "-");
        } else if (termino.contains("-")) {
          salida += termino.replace("-", "+");
        } else {
          salida += "-" + termino;
        }
      } else {
        salida += termino;
      }

    }
    return salida;
  }

  /**
   * Obtiene el coeficiente de cada expresion.
   *
   * Ejemplo si tengo la expresion 5x, se obtiene el 5 y lo retorna como double
   *
   * @param termino
   * @return
   */
  public static double obtenerCoeficiente(String termino) {
    String numero = "";
    char temp = ' ';
    if (termino.equals("-x") || termino.equals("-y") || termino.equals("-z")) {
      termino = "-1" + termino.charAt(1);
    }

    if (termino.equals("x") || termino.equals("y") || termino.equals("z")) {
      termino = "1" + termino;
    }
    for (int i = 0; i < termino.length(); i++) {
      temp = (termino.charAt(i));
      if (Character.isDigit(temp) || temp == '-' || temp == '.' || temp == '/') {
        numero += temp;
      }
    }
    if (numero.contains("/")) {
      numero = Fraccion.convertirDecimal(numero);
    }
    return !numero.trim().equals("") ? Double.parseDouble(numero) : 1;
  }

  /**
   * Obtiene el vector que hay en el plano, primero se ordena la ecuacion y
   * luego se obtiene los valores a,b,c de la ecuacion ax+by+cz=d.
   *
   * @param plano
   * @return
   */
  public static double[] obtenerVectorPlano(String plano) {
    String vector[] = ordenarEcuacion(plano);
    double vectorPlano[] = new double[vector.length - 1];
    for (int i = 0; i < vector.length - 1; i++) {
      if (vector[i] == null) {
        vectorPlano[i] = 0;
      } else {
        vectorPlano[i] = obtenerCoeficiente(vector[i]);
      }
    }
    return vectorPlano;
  }

  /**
   * Obtiene los coeficientes del aecuacion en el orden ax+by+cz=d.
   *
   * @param plano
   * @return
   */
  public static double[] obtenerEcuacionOrdenada(String plano) {
    String vector[] = ordenarEcuacion(plano);
    double vectorPlano[] = new double[vector.length];
    for (int i = 0; i < vector.length; i++) {
      if (vector[i] == null) {
        vectorPlano[i] = 0;
      } else {
        vectorPlano[i] = obtenerCoeficiente(vector[i]);
      }
    }
    return vectorPlano;
  }

  /**
   * Realiza el producto punto entre dos vector V*V
   *
   * @param vectorA
   * @param vectorB
   * @return
   */
  public static double productoPunto(double vectorA[], double vectorB[]) {
    double resultado = 0;
    for (int i = 0; i < vectorA.length; i++) {
      resultado += vectorA[i] * vectorB[i];
    }
    return resultado;
  }

  /**
   * Calcula la norma de un vector
   *
   * @param vector
   * @return
   */
  public static double calcularNormaVector(double vector[]) {
    double resultado = 0;
    for (int i = 0; i < vector.length; i++) {
      resultado += Math.pow(Math.abs(vector[i]), 2);
    }
    return Math.sqrt(resultado);
  }

  /**
   * Calculo en angulo entre 2 planos y lo devuelve en grados
   *
   * @param vectorA
   * @param vectorB
   * @return
   */
  public static double anguloEntre2Planos(double vectorA[], double vectorB[]) {
    double cosenoInverso = Math.acos(productoPunto(vectorA, vectorB)
            / (calcularNormaVector(vectorA) * calcularNormaVector(vectorB)));

    return Math.toDegrees(cosenoInverso);
  }

  /**
   * Valida que no sean paralelos el angulo
   *
   * @param angulo
   * @return
   */
  public static boolean seIntersecan2Planos(double angulo) {
    return angulo > 0 && angulo < 180;
  }

  /**
   * Metodo principal que obtiene el resultado del calculo de los angulos y la
   * ecuacion de la interseccion de dos planos, que por ultimos estos se guarda
   * como procedimiento en una variable global.
   *
   * @param planoA
   * @param planoB
   * @return
   */
  public static String obtenerRecta2Planos(String planoA, String planoB) {
    GaussJordan gaussJordan = new GaussJordan();
    String procedimiento = "Angulo entre vectores de dos planos y su ecuaciones de rectas:\n";

    double ecuacionPlanoA[] = obtenerEcuacionOrdenada(planoA);
    double ecuacionPlanoB[] = obtenerEcuacionOrdenada(planoB);
    double sistemaEcuacion[][] = {ecuacionPlanoA, ecuacionPlanoB};
    double vectorA[] = obtenerVectorPlano(planoA);
    double vectorB[] = obtenerVectorPlano(planoB);
    double anguloEntreVec = anguloEntre2Planos(ecuacionPlanoA, ecuacionPlanoB);
    if (!seIntersecan2Planos(anguloEntreVec)) {
      procedimiento = "Los vectores son paralelos, no se intersecan.";
      return procedimiento;
    }
    gaussJordan.setConstantes(new String[]{"x", "y", "z"});
    gaussJordan.setMatriz(sistemaEcuacion);

    procedimiento += "\n1-Formula para obtener el angulo: Arcocoseno ("
            + "VectorA * VectorB / Norma A * Norma B )\n";
    procedimiento += "El producto punto es de: " + productoPunto(vectorA, vectorB)
            + "\n";
    procedimiento += "\nEl producto de la norma del vector A y B es de "
            + (calcularNormaVector(vectorA) * calcularNormaVector(vectorB)) + "\n";
    procedimiento += ("El angulo entre el vector  A: " + Arrays.toString(vectorA) + ""
            + " y el vector B " + Arrays.toString(vectorB)).replace("[", "<").replace("]", ">");
    procedimiento += " es de " + anguloEntreVec + "°.\n";
    procedimiento += "--------------------------------------------------------------------------------------------------------------->\n";

    String x, y;
    try {
      procedimiento += "\n\n2-Obteniendo la interseccion: \n" + gaussJordan.toString() + "\n";
      procedimiento += "\n-------------------------------------------------------------------------------------------------------------->\n";

      procedimiento += "\n\nLa ecuacion parametrica es: \n\n";
      procedimiento += "{ x = " + (x = gaussJordan.obtConjuntoSoluciones().get(0).replace("z", "t")) + "\n";
      procedimiento += "{ y = " + (y = gaussJordan.obtConjuntoSoluciones().get(1).replace("z", "t")) + "\n";
      procedimiento += "{ z = t";

      procedimiento += "\n\nLa ecuacion vectorial es:\n";
      procedimiento += "\n(x,y,z) = " + "(" + dividirTerminos(x, "+-=").get(0)
              + "," + dividirTerminos(y, "+-=").get(0) + ",0) + t<"
              + obtenerCoeficiente(dividirTerminos(x, "+-=").get(1).trim()) + ","
              + obtenerCoeficiente(dividirTerminos(y, "+-=").get(1).trim()) + ",1> / t \u2208 \u211D";
    } catch (Exception e) {
      procedimiento += "\nNo se posible obtener las ecuaciones de la recta (Error del programa)\n";
    }
    return procedimiento;
  }

  /**
   * Valida si la ecuacion tiene el formato de la ecuacion del plano ax+by+cz=d.
   *
   * @param cadena
   * @return
   */
  public static boolean validarEcuacion(String cadena) {
    cadena = cadena.replace(" ", "");
    String regex = "^[\\d\\+\\-\\/xXyYzZ=\\.]+$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(cadena);
    return matcher.matches();
  }

}
