package ma.ensat.view;


import ma.ensat.core.Graph;
import ma.ensat.core.Vertex;
import ma.ensat.core.Edge;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphViewer {

    public static <T> void generateHTML(Graph<T> graph, String outputPath) throws IOException {
        String htmlContent = buildHTML(graph);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write(htmlContent);
        }
    }

    private static <T> String buildHTML(Graph<T> graph) {
        StringBuilder nodes = new StringBuilder();
        StringBuilder edges = new StringBuilder();

        // Positionnement des nœuds en cercle
        Map<Vertex<T>, int[]> positions = calculateCircularPositions(graph);

        // Génération des nœuds
        for (Vertex<T> vertex : graph.getVertices()) {
            int[] pos = positions.get(vertex);
            nodes.append(String.format(
                    "{ id: '%s', x: %d, y: %d },\n",
                    escapeJS(vertex.getData().toString()), pos[0], pos[1]
            ));
        }

        // Génération des arêtes
        for (Edge<T> edge : graph.getEdges()) {
            edges.append(String.format(
                    "{ source: '%s', target: '%s', weight: %.1f },\n",
                    escapeJS(edge.getSource().getData().toString()),
                    escapeJS(edge.getDestination().getData().toString()),
                    edge.getWeight()
            ));
        }

        return String.format("""
<!DOCTYPE html>
<html>
<head>
    <title>Graph Visualization</title>
    <style>
        .node { fill: #4CAF50; stroke: #388E3C; stroke-width: 2px; }
        .edge { stroke: #757575; stroke-width: 2px; }
        .label { font-family: Arial; font-size: 12px; fill: #333; }
        .weight { font-family: Arial; font-size: 10px; fill: #E91E63; }
        svg { background-color: #f5f5f5; }
    </style>
    <script src="https://d3js.org/d3.v7.min.js"></script>
</head>
<body>
    <h1>Graph Visualization</h1>
    <svg width="800" height="600"></svg>
    <script>
        const graphData = {
            nodes: [
                %s
            ],
            edges: [
                %s
            ]
        };

        const svg = d3.select("svg");
        const width = +svg.attr("width");
        const height = +svg.attr("height");
        
        // Draw edges
        svg.selectAll(".edge")
            .data(graphData.edges)
            .enter()
            .append("line")
            .attr("class", "edge")
            .attr("x1", d => graphData.nodes.find(n => n.id === d.source).x)
            .attr("y1", d => graphData.nodes.find(n => n.id === d.source).y)
            .attr("x2", d => graphData.nodes.find(n => n.id === d.target).x)
            .attr("y2", d => graphData.nodes.find(n => n.id === d.target).y);

        // Draw nodes
        svg.selectAll(".node")
            .data(graphData.nodes)
            .enter()
            .append("circle")
            .attr("class", "node")
            .attr("cx", d => d.x)
            .attr("cy", d => d.y)
            .attr("r", 20);

        // Add labels
        svg.selectAll(".label")
            .data(graphData.nodes)
            .enter()
            .append("text")
            .attr("class", "label")
            .attr("x", d => d.x)
            .attr("y", d => d.y + 5)
            .attr("text-anchor", "middle")
            .text(d => d.id);

        // Add weights
        svg.selectAll(".weight")
            .data(graphData.edges)
            .enter()
            .append("text")
            .attr("class", "weight")
            .attr("x", d => (graphData.nodes.find(n => n.id === d.source).x + 
                            graphData.nodes.find(n => n.id === d.target).x) / 2)
            .attr("y", d => (graphData.nodes.find(n => n.id === d.source).y + 
                            graphData.nodes.find(n => n.id === d.target).y) / 2)
            .attr("text-anchor", "middle")
            .text(d => d.weight);
    </script>
</body>
</html>
""", nodes.toString(), edges.toString());
    }

    private static <T> Map<Vertex<T>, int[]> calculateCircularPositions(Graph<T> graph) {
        Map<Vertex<T>, int[]> positions = new HashMap<>();
        int centerX = 400, centerY = 300;
        int radius = 200;
        double angleStep = 2 * Math.PI / graph.getVertices().size();
        double currentAngle = 0;

        for (Vertex<T> vertex : graph.getVertices()) {
            int x = centerX + (int)(radius * Math.cos(currentAngle));
            int y = centerY + (int)(radius * Math.sin(currentAngle));
            positions.put(vertex, new int[]{x, y});
            currentAngle += angleStep;
        }

        return positions;
    }

    private static String escapeJS(String input) {
        return input.replace("'", "\\'")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
    }
}