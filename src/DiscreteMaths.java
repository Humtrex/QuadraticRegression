import java.util.List;
//Humberto Hernández Trejo

public class DiscreteMaths {

    // Método para calcular la suma de los elementos de una lista de números decimales (Double)
    public static double sum(List<Double> values) {
        // API de Streams para convertir los valores a double y calcular la suma
        return values.stream().mapToDouble(Double::doubleValue).sum();
    }

    // Método para calcular la suma de los productos de dos listas de números decimales (Double)
    public static double sumOfProducts(List<Double> values1, List<Double> values2) {
        double sum = 0;
        // Recorre ambas listas, multiplicando los elementos correspondientes y sumando los resultados
        for (int i = 0; i < values1.size(); i++) {
            sum += values1.get(i) * values2.get(i);
        }
        return sum;
    }

    // Método para calcular la suma de los cuadrados de los elementos de una lista de números decimales (Double)
    public static double sumOfSquares(List<Double> values) {
        // API de Streams para mapear cada valor al cuadrado y luego calcular la suma
        return values.stream().mapToDouble(x -> x * x).sum();
    }

    // Método para calcular la correlación entre dos listas de números decimales (Double)
    public static double correlation(List<Double> x, List<Double> y) {
        double sumX = sum(x);
        double sumY = sum(y);
        double sumX2 = sumOfSquares(x); // Suma de los cuadrados
        double sumY2 = sumOfSquares(y);
        double n = x.size();

        // Fórmula de correlación de Pearson:
        // (n * Σ(X * Y) - ΣX * ΣY) / sqrt((n * ΣX^2 - (ΣX)^2) * (n * ΣY^2 - (ΣY)^2))
        return (n * sumOfProducts(x, y) - sumX * sumY) /
                Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY));
    }

}
