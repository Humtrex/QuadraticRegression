import java.util.List;
//Humberto Hernández Trejo

public class QuadraticRegression {
    private Model model;
    private DataSet dataset;

    public QuadraticRegression(DataSet dataset) {
        this.dataset = dataset;
        this.model = new Model();
    }

    // Método que calcula los parámetros del modelo (Beta0, Beta1, Beta2) usando el método de mínimos cuadrados
    public void calculateParameters() {
        int n = dataset.getX().size();

        // Crear la matriz X (con 3 columnas) y el vector Y
        double[][] X = new double[n][3];
        double[] Y = new double[n];

        // Llenar las matrices X (con términos cuadráticos) e Y
        for (int i = 0; i < n; i++) {
            X[i][0] = 1;            // Columna de 1s para beta_0
            X[i][1] = dataset.getX().get(i); // Columna de x para beta_1
            X[i][2] = Math.pow(dataset.getX().get(i), 2); // Columna de x^2 para beta_2
            Y[i] = dataset.getY().get(i); // Valores de Y correspondientes
        }

        // Imprimir la matriz X
        System.out.println("Matriz X:");
        printMatrix(X);

        // Calcular la transpuesta de X
        double[][] XT = transpose(X);

        // Calcular la matriz XT * X
        double[][] XTX = multiply(XT, X);

        // Calcular la matriz XT * Y
        double[] XTY = multiply(XT, Y);

        // Resolver el sistema de ecuaciones XTX * B = XTY para obtener los coeficientes
        double[] B = solveLinearEquations(XTX, XTY);

        // Mostrar la matriz resultante tras Gauss-Jordan
        System.out.println("\nMatriz Resuelta por Gauss-Jordan:");
        printMatrix(XTX);
        System.out.println("\nVector de Resultados (XTY):");
        printArray(XTY);

        // Asignar los coeficientes B al modelo
        model.setBeta_0(B[0]);
        model.setBeta_1(B[1]);
        model.setBeta_2(B[2]);

        // Mostrar la ecuación del modelo
        System.out.println("\nEcuación del modelo cuadrático: ");
        System.out.println(model);
    }

    // Método que calcula el coeficiente de determinación (R²) usando los datos de prueba
    public double calculateRSquared(List<Double> testX, List<Double> testY) {
        double ssTotal = 0.0; // Suma total de los cuadrados
        double ssResidual = 0.0; // Suma residual de los cuadrados

        double meanY = calculateMean(testY); // Calcular la media de Y

        // Recorrer los datos de prueba y calcular la suma total y la suma residual
        for (int i = 0; i < testX.size(); i++) {
            double predictedY = model.predict(testX.get(i)); // Obtener la predicción
            ssTotal += Math.pow(testY.get(i) - meanY, 2); // Diferencia entre el valor real y la media de Y
            ssResidual += Math.pow(testY.get(i) - predictedY, 2); // Diferencia entre el valor real y el predicho
        }

        return 1 - (ssResidual / ssTotal);  // Retornar el coeficiente de determinación (R²)
    }

    public Model getModel() {
        return model;
    }

    // Método para calcular la media de una lista de valores
    private double calculateMean(List<Double> values) {
        double sum = 0.0;
        for (Double value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    // Método que calcula la transpuesta de una matriz
    private double[][] transpose(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] transposed = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j]; // Intercambiar filas y columnas
            }
        }
        return transposed;
    }

    // Método para multiplicar dos matrices
    private double[][] multiply(double[][] A, double[][] B) {
        int rowsA = A.length;
        int colsA = A[0].length;
        int rowsB = B.length;
        int colsB = B[0].length;

        double[][] result = new double[rowsA][colsB];

        // Multiplicar las matrices A y B
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += A[i][k] * B[k][j]; // Multiplicar y sumar los valores correspondientes
                }
            }
        }
        return result;
    }

    // Método para multiplicar una matriz por un vector
    private double[] multiply(double[][] A, double[] B) {
        int rowsA = A.length;
        int colsA = A[0].length;

        double[] result = new double[rowsA];

        // Multiplicar la matriz A por el vector B
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsA; j++) {
                result[i] += A[i][j] * B[j]; // Multiplicar y sumar los valores correspondientes
            }
        }
        return result;
    }

    // Método para resolver un sistema de ecuaciones lineales usando eliminación de Gauss
    private double[] solveLinearEquations(double[][] A, double[] B) {
        // Implementación simple de la eliminación de Gauss
        int n = B.length;

        // Crear una copia de A y B para trabajar
        double[][] augmentedMatrix = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i], 0, augmentedMatrix[i], 0, n);
            augmentedMatrix[i][n] = B[i];
        }

        // Eliminar filas
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double ratio = augmentedMatrix[j][i] / augmentedMatrix[i][i];
                for (int k = i; k < n + 1; k++) {
                    augmentedMatrix[j][k] -= ratio * augmentedMatrix[i][k]; // Eliminar la variable de las filas inferiores
                }
            }
        }

        // Sustitución hacia atrás
        double[] result = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            result[i] = augmentedMatrix[i][n] / augmentedMatrix[i][i]; // Calcular el valor de la incógnita
            for (int j = i - 1; j >= 0; j--) {
                augmentedMatrix[j][n] -= augmentedMatrix[j][i] * result[i]; // Eliminar las variables de las filas superiores
            }
        }
        return result; // Retornar el vector de resultados
    }

    // Método para imprimir una matriz
    private void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.printf("%17.4f", value); // Alineación: 17 caracteres, 4 decimales
            }
            System.out.println();
        }
    }

    // Método para imprimir un vector
    private void printArray(double[] array) {
        for (double value : array) {
            System.out.printf("%10.4f", value);
        }
        System.out.println();
    }
}
