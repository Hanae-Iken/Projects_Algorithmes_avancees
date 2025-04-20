package ma.ensat.core;

import java.util.ArrayList;
import java.util.List;

public class Vertex<T> {
    private T data;
    private List<Edge<T>> edges;
    private boolean visited;

    public Vertex(T data) {
        this.data = data;
        this.edges = new ArrayList<>();
        this.visited = false;
    }

    // Getters et setters
    public T getData() {
        return data;
    }

    public List<Edge<T>> getEdges() {
        return edges;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void addEdge(Edge<T> edge) {
        edges.add(edge);
    }

    @Override
    public String toString() {
        return data.toString();
    }
}