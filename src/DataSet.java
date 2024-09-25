import java.util.List;
//Humberto Hernández Trejo

public class DataSet {
    private List<Double> x;
    private List<Double> y;

    public DataSet(List<Double> x, List<Double> y) {
        this.x = x;
        this.y = y;
    }

    public List<Double> getX() {
        return x;
    }

    public List<Double> getY() {
        return y;
    }

    // Método para obtener un subconjunto del DataSet
    public DataSet subset(int start, int end) {
        List<Double> subsetX = x.subList(start, end);  // Extrae los elementos de la lista desde start hasta end
        List<Double> subsetY = y.subList(start, end);
        return new DataSet(subsetX, subsetY);  // Devuelve un nuevo DataSet con los subconjuntos
    }
}

