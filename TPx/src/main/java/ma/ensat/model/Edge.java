package ma.ensat.model;

/**
 * Représente une arête dans un graphe avec un sommet de destination et un poids
 * @param <T> Type de données des sommets
 */
public class Edge<T> {
    private T destination;
    private int weight;

    public Edge(T destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public T getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return destination + " (poids: " + weight + ")";
    }
}