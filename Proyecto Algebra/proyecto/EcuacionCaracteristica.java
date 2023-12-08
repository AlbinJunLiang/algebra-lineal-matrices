package proyecto;

/**
 * Clase para obtener los coeficientes de la ecuacion caracteristica que se
 * forma a la hora de obtener la determinate de una matriz con valores de
 * lambda. Tambien se acumula el procedimiento en una variabe global.
 *
 * @author Albin Liang
 */
public class EcuacionCaracteristica {

  private double matriz[][];
  private String procedimiento;
  public static final String lambda = "\u03BB";
  public static final String superIndice2 = "\u00B2";
  public static final String superIndice3 = "\u00B3";
  public static final String superIndice4 = "\u2074";

  public EcuacionCaracteristica(double matriz[][]) {
    this.matriz = matriz;
    procedimiento = "";

  }

  public String mostrarMatriz() {
    String salida = "A=";
    for (int i = 0; i < matriz.length; i++) {
      for (int j = 0; j < matriz[i].length; j++) {
        if (i == j) {
          salida += "\t" + matriz[i][j];
        } else {
          salida += "\t" + matriz[i][j];
        }
      }
      salida += "\n";
    }

    return salida + "";
  }

  /**
   * Obtiene y devuelve los valores o coeficientes de una matriz 2x2, lo guarda
   * en un vetor y el procedimiento se guarda en una variable global
   *
   * @return
   */
  public double[] ecuacionCaracteristica2X2() {
    double vector[] = new double[3];
    procedimiento = "";

    // paso 1
    procedimiento = "(" + matriz[0][0] + "-" + lambda + ")"
            + "* (" + matriz[1][1] + "-" + lambda + ")"
            + "-" + "(" + matriz[1][0] + "*" + matriz[0][1] + ") = 0\n";
    // paso 2
    procedimiento += matriz[0][0] * matriz[1][1]
            + "-" + matriz[0][0] + lambda + "+"
            + -matriz[1][1] + lambda + "+" + lambda
            + superIndice2 + "-" + (matriz[1][0] * matriz[0][1]) + " = 0\n";

    // paso 3
    procedimiento += lambda + superIndice2 + "+"
            + -(matriz[1][1] + matriz[0][0]) + lambda
            + "+" + (matriz[0][0] * matriz[1][1]
            - matriz[1][0] * matriz[0][1]) + "=0\n";

    procedimiento = procedimiento.replace("+-", "-");
    procedimiento = procedimiento.replace("--", "+");

    vector[0] = 1;
    vector[1] = -(matriz[1][1] + matriz[0][0]);
    vector[2] = (matriz[0][0] * matriz[1][1] - matriz[1][0] * matriz[0][1]);
    return vector;
  }

  /**
   * Obtiene los valores o coeficientes de una matriz 3x3 y el procedimiento se
   * guarda en una variable global
   *
   * @return
   */
  public double[] ecuacionCaracteristica3X3() {
    double vector[] = new double[4];
    procedimiento = "";

// paso # 1
//primer +
    procedimiento += "[" + matriz[0][0] + "-" + lambda + "](" + matriz[1][1] + "-" + lambda + ") * ("
            + matriz[2][2] + "-" + lambda + ")-" + "(" + matriz[2][1] + "*" + matriz[1][2] + ")";
// segundo  -
    procedimiento += "  -" + matriz[0][1] + "(" + matriz[1][0] + "*(" + matriz[2][2] + "-"
            + lambda + ")" + ")-" + "(" + matriz[2][0] + "*" + matriz[1][2] + ")";
    // tercero +
    procedimiento += "  +" + matriz[0][2] + "(" + matriz[1][0] + "*" + matriz[2][1]
            + ")-" + "(" + matriz[2][0] + "*" + "(" + matriz[1][1] + "-" + lambda + ")" + ") = 0\n";

    // paso # 2
    // +
    procedimiento += "[" + matriz[0][0] + "-" + lambda + "]" + "("
            + (matriz[1][1] * matriz[2][2] - (matriz[2][1] * matriz[1][2])) + "+"
            + -(matriz[1][1] + matriz[2][2]) + lambda + "+" + lambda + superIndice2
            + ") ";
    // -
    procedimiento += " -" + matriz[0][1] + "(" + (matriz[1][0] * matriz[2][2]
            - (matriz[2][0] * matriz[1][2])) + "-" + matriz[1][0] + lambda + ")";
    //+
    procedimiento += " +" + matriz[0][2] + "(" + (matriz[1][0] * matriz[2][1]
            - matriz[2][0] * matriz[1][1]) + "+" + matriz[2][0] + lambda
            + ") = 0\n";
    // paso # 3
//+
    procedimiento += (matriz[1][1] * matriz[2][2] - (matriz[2][1] * matriz[1][2])) * matriz[0][0]
            + "-" + (matriz[1][1] + matriz[2][2]) * matriz[0][0] + lambda + "+" + matriz[0][0]
            + lambda + superIndice2 + "-" + (matriz[1][1] * matriz[2][2] - (matriz[2][1] * matriz[1][2]))
            + lambda + "+" + (matriz[1][1] + matriz[2][2]) + lambda + superIndice2 + "-"
            + lambda + superIndice3;
    //-
    procedimiento += " -" + (matriz[1][0] * matriz[2][2] - (matriz[2][0] * matriz[1][2])) * matriz[0][1]
            + "+" + (matriz[0][1]) * matriz[1][0] + lambda;

    //+
    procedimiento += "+" + (matriz[0][2] * (matriz[1][0] * matriz[2][1] - matriz[2][0] * matriz[1][1]))
            + "+" + (matriz[2][0] * matriz[0][2]) + lambda + " = 0\n";

    vector[0] = -1; // Grado 3
    vector[1] = ((-1 * (-matriz[1][1])) + matriz[0][0] // grado
            + (-1 * (-matriz[2][2])));
    vector[2] = (-matriz[1][1] * matriz[0][0] // grado 1
            + (-matriz[2][2] * matriz[0][0]) + (matriz[1][1]
            * matriz[2][2] - matriz[2][1] * matriz[1][2]) * -1 + (-matriz[0][1] * -matriz[1][0])
            + matriz[0][2] * -(matriz[2][0] * -1));
    vector[3] = ((matriz[0][0] * (matriz[1][1]
            * matriz[2][2] - matriz[2][1] * matriz[1][2])) // termino indenpendiente
            + (-matriz[0][1] * ((matriz[1][0] * matriz[2][2] - matriz[2][0] * matriz[1][2])))
            + (matriz[0][2] * ((matriz[1][0] * matriz[2][1]) - matriz[2][0] * matriz[1][1])));
    procedimiento += "-" + lambda + superIndice3 + "+" + vector[1] + lambda
            + superIndice2 + vector[2] + lambda + "+" + vector[3] + " = 0";
    procedimiento = procedimiento.replace("+-", "-");
    procedimiento = procedimiento.replace("--", "+");

    return vector;
  }

  /**
   * Obtiene los valores o coeficientes de una matriz 4x4 y el procedimiento se
   * guarda en una variable global
   *
   * @return
   */
  public double[] ecuacionCaracteristica4X4() {
    double vector[] = new double[5];
    procedimiento = "";
    // Parte 1
    procedimiento
            += "A" + superIndice4 + "+" + "-" + (matriz[0][0] + ((matriz[1][1] + (matriz[2][2]
            + matriz[3][3])))) + "A" + superIndice3 + "+" + (matriz[0][0] * (matriz[1][1]
            + (matriz[2][2] + matriz[3][3])) + -1 * (matriz[1][1] * -(matriz[2][2] + matriz[3][3])
            + -(matriz[2][2] * matriz[3][3] - matriz[3][2] * matriz[2][3]) + matriz[1][3] * matriz[3][1]
            + -matriz[1][2] * -matriz[2][1])) + "A" + superIndice2;

    procedimiento
            += "+" + (matriz[0][0] * (matriz[1][1] * -(matriz[2][2] + matriz[3][3])
            + -(matriz[2][2] * matriz[3][3] - matriz[3][2] * matriz[2][3]) + matriz[1][3]
            * matriz[3][1] + -matriz[1][2] * -matriz[2][1]) - ((matriz[1][3] * (matriz[2][1]
            * matriz[3][2] - matriz[3][1] * matriz[2][2])) + matriz[1][1] * (matriz[2][2]
            * matriz[3][3] - matriz[3][2] * matriz[2][3]) + -matriz[1][2] * (matriz[2][1]
            * matriz[3][3] - matriz[3][1] * matriz[2][3]))) + "A+" + matriz[0][0]
            * ((matriz[1][3] * (matriz[2][1] * matriz[3][2] - matriz[3][1] * matriz[2][2]))
            + matriz[1][1] * (matriz[2][2] * matriz[3][3] - matriz[3][2] * matriz[2][3])
            + -matriz[1][2] * (matriz[2][1] * matriz[3][3] - matriz[3][1] * matriz[2][3]));

    procedimiento += "+";

    // Parte 2
    procedimiento += (-matriz[0][1] * ((matriz[1][0] * (matriz[2][2] * matriz[3][3] - matriz[3][2]
            * matriz[2][3]) + matriz[1][3] * ((matriz[2][0] * matriz[3][2]) - (matriz[3][0] * matriz[2][2]))
            + -matriz[1][2] * (matriz[2][0] * matriz[3][3] - matriz[3][0] * matriz[2][3]))) + "+"
            + -matriz[0][1] * matriz[1][0] + "A" + superIndice2) + "" + "+" + matriz[0][1]
            * (-(matriz[1][2] * matriz[2][0] + matriz[1][3] * matriz[3][0]) + matriz[1][0]
            * (matriz[2][2] + matriz[3][3])) + "A";
    // Parte 3
    procedimiento += "+";
    procedimiento += "" + matriz[0][2] * ((matriz[1][0] * (matriz[2][1] * matriz[3][3] - matriz[3][1]
            * matriz[2][3]) - matriz[1][1] * (matriz[2][0] * matriz[3][3] - matriz[3][0] * matriz[2][3]))
            + matriz[1][3] * (matriz[2][0] * matriz[3][1] - matriz[3][0] * matriz[2][1]))
            + "+" + matriz[0][2] * (-matriz[1][0] * matriz[2][1] + matriz[1][1] * matriz[2][0]
            + (matriz[2][0] * matriz[3][3] - matriz[3][0] * matriz[2][3])) + "A"
            + matriz[0][2] * -matriz[2][0]
            + "A" + superIndice2 + " ";

// Parte 4
    procedimiento += "+";

    procedimiento += -matriz[0][3] * (matriz[1][0] * (matriz[2][1] * matriz[3][2]
            + -matriz[3][1] * matriz[2][2]) + -matriz[1][1] * (matriz[2][0] * matriz[3][2]
            - matriz[3][0] * matriz[2][2]) + matriz[1][2] * (matriz[2][0] * matriz[3][1]
            - matriz[3][0] * matriz[2][1])) + "+" + -matriz[0][3] * (-matriz[1][0] * -matriz[3][1]
            + (-matriz[1][1] * matriz[3][0] + (matriz[2][0] * matriz[3][2] - matriz[3][0] * matriz[2][2])))
            + "A" + "+" + -matriz[0][3] * matriz[3][0] + "A" + superIndice2;

    procedimiento += " = 0\n";

    vector[0] = 1; // Grado 4
    // Grado 3
    vector[1] = -(matriz[0][0] + ((matriz[1][1] + (matriz[2][2] + matriz[3][3]))));
    // Grado 2
    vector[2] = (((matriz[0][0] * (matriz[1][1] + (matriz[2][2] + matriz[3][3]))
            + -1 * (matriz[1][1] * -(matriz[2][2] + matriz[3][3]) + -(matriz[2][2]
            * matriz[3][3] - matriz[3][2] * matriz[2][3]) + matriz[1][3] * matriz[3][1]
            + -matriz[1][2] * -matriz[2][1]))) + (-matriz[0][1] * matriz[1][0]) + matriz[0][2]
            * -matriz[2][0] + matriz[0][3] * (-1 * matriz[3][0]));
// Grado 1
    vector[3] = ((matriz[0][0] * (matriz[1][1] * -(matriz[2][2] + matriz[3][3]) + -(matriz[2][2] * matriz[3][3]
            - matriz[3][2] * matriz[2][3]) + matriz[1][3] * matriz[3][1] + -matriz[1][2] * -matriz[2][1])
            - ((matriz[1][3] * (matriz[2][1] * matriz[3][2] - matriz[3][1] * matriz[2][2]))
            + matriz[1][1] * (matriz[2][2] * matriz[3][3] - matriz[3][2] * matriz[2][3])
            + -matriz[1][2] * (matriz[2][1] * matriz[3][3] - matriz[3][1] * matriz[2][3])))
            + matriz[0][1] * (-(matriz[1][2] * matriz[2][0] + matriz[1][3] * matriz[3][0]) + matriz[1][0]
            * (matriz[2][2] + matriz[3][3])) + +matriz[0][2] * (-matriz[1][0] * matriz[2][1]
            + matriz[1][1] * matriz[2][0] + (matriz[2][0] * matriz[3][3] - matriz[3][0] * matriz[2][3]))
            + -matriz[0][3] * (-matriz[1][0] * -matriz[3][1] + (-matriz[1][1] * matriz[3][0]
            + (matriz[2][0] * matriz[3][2] - matriz[3][0] * matriz[2][2]))));
    // Grado 0
    vector[4] = (((matriz[0][0] * ((matriz[1][3] * (matriz[2][1] * matriz[3][2] - matriz[3][1] * matriz[2][2]))
            + matriz[1][1] * (matriz[2][2] * matriz[3][3] - matriz[3][2] * matriz[2][3])
            + -matriz[1][2] * (matriz[2][1] * matriz[3][3] - matriz[3][1] * matriz[2][3]))
            + -matriz[0][1] * ((matriz[1][0] * (matriz[2][2] * matriz[3][3] - matriz[3][2] * matriz[2][3])
            + matriz[1][3] * ((matriz[2][0] * matriz[3][2]) - (matriz[3][0] * matriz[2][2]))
            + -matriz[1][2] * (matriz[2][0] * matriz[3][3] - matriz[3][0] * matriz[2][3]))))
            + matriz[0][2] * ((matriz[1][0] * (matriz[2][1] * matriz[3][3] - matriz[3][1]
            * matriz[2][3]) - matriz[1][1] * (matriz[2][0] * matriz[3][3] - matriz[3][0] * matriz[2][3]))
            + matriz[1][3] * (matriz[2][0] * matriz[3][1] - matriz[3][0] * matriz[2][1])))
            + -matriz[0][3] * (matriz[1][0] * (matriz[2][1] * matriz[3][2] - (matriz[3][1] * matriz[2][2]))
            + -matriz[1][1] * (matriz[2][0] * matriz[3][2] + -1 * matriz[3][0] * matriz[2][2])
            + matriz[1][2] * (matriz[2][0] * matriz[3][1] - matriz[3][0] * matriz[2][1])));
    procedimiento += lambda + superIndice4
            + "+" + vector[1] + lambda + superIndice3 + "+"
            + vector[2] + lambda + superIndice2 + "+"
            + vector[3] + lambda + "+" + vector[4] + " = 0\n";
    procedimiento = procedimiento.replace("+-", "-");
    procedimiento = procedimiento.replace("--", "+");
    procedimiento = procedimiento.replace("A", lambda);
    return vector;
  }

  public String toString() {
    return procedimiento;
  }
}
