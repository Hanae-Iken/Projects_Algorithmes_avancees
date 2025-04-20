package ma.ensat.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph<T> {
    private List<Vertex<T>> vertices;
    private List<Edge<T>> edges;
    private Map<T, Vertex<T>> vertexMap;

    public Graph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.vertexMap = new HashMap<>();
    }

    public void addVertex(T data) {
        if (!vertexMap.containsKey(data)) {
            Vertex<T> vertex = new Vertex<>(data);
            vertices.add(vertex);
            vertexMap.put(data, vertex);
        }
    }

    public void addEdge(T sourceData, T destData, double weight) {
        if (!vertexMap.containsKey(sourceData)) {
            addVertex(sourceData);
        }
        if (!vertexMap.containsKey(destData)) {
            addVertex(destData);
        }

        Vertex<T> source = vertexMap.get(sourceData);
        Vertex<T> dest = vertexMap.get(destData);

        Edge<T> edge = new Edge<>(source, dest, weight);
        edges.add(edge);
        source.addEdge(edge);
    }

    public Vertex<T> getVertex(T data) {
        return vertexMap.get(data);
    }

    public List<Vertex<T>> getVertices() {
        return new ArrayList<>(vertices);
    }

    public List<Edge<T>> getEdges() {
        return new ArrayList<>(edges);
    }

    public void resetVisited() {
        for (Vertex<T> vertex : vertices) {
            vertex.setVisited(false);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph:\n");
        for (Vertex<T> vertex : vertices) {
            sb.append(vertex.getData()).append(" -> ");
            for (Edge<T> edge : vertex.getEdges()) {
                sb.append(edge.getDestination().getData())
                        .append("(").append(edge.getWeight()).append(") ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}