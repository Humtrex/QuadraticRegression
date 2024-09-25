import java.util.List; // Importa la clase List, utilizada para trabajar con listas de elementos

public class DiscreteMaths {

    // Método para calcular la suma de los elementos de una lista de números decimales (Double)
    public static double sum(List<Double> values) {
        // Usa la API de Streams para convertir los valores a double y calcular la suma
        return values.stream().mapToDouble(Double::doubleValue).sum();
    }

    // Método para calcular la suma de los productos de dos listas de números decimales (Double)
    // Ambas listas deben tener el mismo tamaño
    public static double sumOfProducts(List<Double> values1, List<Double> values2) {
        double sum = 0; // Variable para almacenar la suma
        // Recorre ambas listas, multiplicando los elementos correspondientes y sumando los resultados
        for (int i = 0; i < values1.size(); i++) {
            sum += values1.get(i) * values2.get(i);
        }
        return sum; // Devuelve la suma total de los productos
    }

    // Método para calcular la suma de los cuadrados de los elementos de una lista de números decimales (Double)
    public static double sumOfSquares(List<Double> values) {
        // Usa la API de Streams para mapear cada valor al cuadrado y luego calcular la suma
        return values.stream().mapToDouble(x -> x * x).sum();
    }

    public static double correlation(List<Double> x, List<Double> y) {
        double sumX = sum(x);
        double sumY = sum(y);
        double sumX2 = sumOfSquares(x);
        double sumY2 = sumOfSquares(y);
        double n = x.size();

        return (n * sumOfProducts(x, y) - sumX * sumY) /
                Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY));
    }

}
