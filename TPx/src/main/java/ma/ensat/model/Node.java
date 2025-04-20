package ma.ensat.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un sommet dans le graphe
 * @param <T> Type de données du sommet
 */
public class Node<T> {
    private T data;
    private List<Edge<T>> edges;

    public Node(T data) {
        this.data = data;
        this.edges = new ArrayList<>();
    }

    public T getData() {
        return data;
    }

    public List<Edge<T>> getEdges() {
        return edges;
    }

    public void addEdge(T destination, int weight) {
        edges.add(new Edge<>(destination, weight));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(data).append(" -> ");
        for (Edge<T> edge : edges) {
            sb.append(edge.getDestination()).append("(").append(edge.getWeight()).append(") ");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Node)) return false;

        @SuppressWarnings("unchecked")
        Node<T> other = (Node<T>) obj;
        return data.equals(other.data);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }
}