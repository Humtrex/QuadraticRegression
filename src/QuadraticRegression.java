import java.util.List;

public class QuadraticRegression {
    private Model model;
    private DataSet dataset;

    public QuadraticRegression(DataSet dataset) {
        this.dataset = dataset;
        this.model = new Model();
    }

    public void calculateParameters() {
        int n = dataset.getX().size();

        // Crear matrices X y Y
        double[][] X = new double[n][3];
        double[] Y = new double[n];

        // Llenar las matrices X e Y
        for (int i = 0; i < n; i++) {
            X[i][0] = 1;            // Columna de 1s para beta_0
            X[i][1] = dataset.getX().get(i); // Columna de x para beta_1
            X[i][2] = Math.pow(dataset.getX().get(i), 2); // Columna de x^2 para beta_2
            Y[i] = dataset.getY().get(i); // Valores de Y
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

        // Mostrar la matriz resuelta
        System.out.println("Matriz Resuelta por Gauss-Jordan:");
        printMatrix(XTX);
        System.out.println("Vector de Resultados (XTY):");
        printArray(XTY);

        // Asignar los coeficientes a nuestro modelo
        model.setBeta_0(B[0]);
        model.setBeta_1(B[1]);
        model.setBeta_2(B[2]);

        // Mostrar la ecuación del modelo
        System.out.println("Ecuación del modelo cuadrático: ");
        System.out.println(model);
    }

    public double calculateRSquared(List<Double> testX, List<Double> testY) {
        double ssTotal = 0.0;
        double ssResidual = 0.0;

        double meanY = calculateMean(testY);

        for (int i = 0; i < testX.size(); i++) {
            double predictedY = model.predict(testX.get(i));
            ssTotal += Math.pow(testY.get(i) - meanY, 2); // Suma total
            ssResidual += Math.pow(testY.get(i) - predictedY, 2); // Residual
        }

        return 1 - (ssResidual / ssTotal);
    }

    public Model getModel() {
        return model;
    }

    private double calculateMean(List<Double> values) {
        double sum = 0.0;
        for (Double value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    private double[][] transpose(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] transposed = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    private double[][] multiply(double[][] A, double[][] B) {
        int rowsA = A.length;
        int colsA = A[0].length;
        int rowsB = B.length;
        int colsB = B[0].length;

        double[][] result = new double[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return result;
    }

    private double[] multiply(double[][] A, double[] B) {
        int rowsA = A.length;
        int colsA = A[0].length;

        double[] result = new double[rowsA];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsA; j++) {
                result[i] += A[i][j] * B[j];
            }
        }
        return result;
    }

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
                    augmentedMatrix[j][k] -= ratio * augmentedMatrix[i][k];
                }
            }
        }

        // Sustitución hacia atrás
        double[] result = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            result[i] = augmentedMatrix[i][n] / augmentedMatrix[i][i];
            for (int j = i - 1; j >= 0; j--) {
                augmentedMatrix[j][n] -= augmentedMatrix[j][i] * result[i];
            }
        }
        return result;
    }

    // Método para imprimir una matriz
    private void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.printf("%10.4f", value);
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
