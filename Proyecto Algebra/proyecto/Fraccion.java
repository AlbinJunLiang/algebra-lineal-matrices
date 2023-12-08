package proyecto;

public class Fraccion {

  /**
   * El funcionamiento de este metodo utilza el algoritmo de FRACCION CONTINUA
   * GENERALIZADA.
   *
   *
   * EJEMPLO DE FUNCIONAMIENTO
   *
   * En caso que el NUMERO ES 1.5 a convertir en fracccion, como 1.5 no es
   * negativo no se hace la primera condicion de return en caso de que fuera
   * -1.5 se haria como si fuera positivo o con valor ABSOLUTO para despues a lo
   * ultimo agregar un menos.
   *
   * Se obtiene la parte entera de numero al ser 1.5, se agarra el entero 1
   *
   * Calculamos el valor del numerador con la formula de
   *
   * NUMERADOR_ACTUAL= a * NUMERADOR_VIEJO
   *
   * Calculamos el denominador como
   *
   * DENOMINADOR_ACTUAL = a* DENOMINADOR_ACTUAL
   *
   * Actualiza el valor de B como:
   *
   * B = 1 / (B-A)
   *
   * TODO ESTE PROCESO SE HARA MIENTRAS EL VALOR ABSOLUTO DE LA RESTA DEL NUMERO
   * Y LA FRACCION SEA MENOR QUE TOLERANCIA EN CASO DE SER MAYOR SE INTERRUMPE A
   * CAUSA DE UN POSIBLE DESPBORAMIENTO
   *
   *
   *
   *
   *
   *
   *
   *
   * @param numero Numero recibido a procesar
   * @return Devuelve el numero recibido a formato FRACCION
   */
  public static String convertirAFraccion(double numero) {

    if (esEntero(numero)) {
      return numero + "";
    }
    if (numero < 0) {
      return "-" + convertirAFraccion(-numero);
    }
    double limite = 0.000000001; // ajustar la precision y aproximacion del resultado
    double numeradorNuevo = 1;
    double numeradorAnterior = 0;
    double denominadorNuevo = 0;
    double denominadorViejo = 1;
    double numeroDecimal = numero;

    do {

      double parteEntera = Math.floor(numeroDecimal);
      double temporal = numeradorNuevo;
      numeradorNuevo = parteEntera * numeradorNuevo + numeradorAnterior;
      numeradorAnterior = temporal;
      temporal = denominadorNuevo;
      denominadorNuevo = parteEntera * denominadorNuevo + denominadorViejo;
      denominadorViejo = temporal;
      numeroDecimal = 1 / (numeroDecimal - parteEntera);
    } while (Math.abs(numero - numeradorNuevo / denominadorNuevo) > numero * limite);

    return (int) numeradorNuevo + "/" + (int) denominadorNuevo;
  }

  /**
   * Convierte el formato fraccion en formato decimal y lo devuelve como String
   *
   * @param fraccion
   * @return
   */
  public static String convertirDecimal(String fraccion) {
    fraccion = fraccion.replace(" ", "");
    String decimal = fraccion;
    if (fraccion.contains("/")) {
      String numerador = fraccion.substring(0, fraccion.indexOf("/"));
      String denominador = fraccion.substring(fraccion.indexOf("/") + 1, fraccion.length());
      decimal = String.valueOf(Double.parseDouble(numerador) / Double.parseDouble(denominador));
    }
    return decimal;
  }

  /**
   * Valida si un numero es entero solo funciona si el double no termine en .00
   *
   * @param numero
   * @return
   */
  public static boolean esEntero(double numero) {
    return (numero == Math.floor(numero));
  }

  public static void main(String[] args) {
    System.out.println(convertirAFraccion(1.2857142857142858));
    System.out.println(convertirAFraccion(1.333333333333333333333333));
    System.out.println(convertirAFraccion(3.15));
    System.out.println(convertirAFraccion(-8.214));
    System.out.println(convertirAFraccion(1.44444444444444444444));
    System.out.println(convertirAFraccion(-2.33333333333333333333));
    System.out.println(convertirDecimal("-7/3"));
  }
}
