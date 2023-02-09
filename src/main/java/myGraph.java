import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.dot.DOTImporter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class myGraph {
    static Graph<String, DefaultEdge> graph;
    static String nodeCount;
    static String nodeLabels;
    static String edgeCount;
    static String direction;

    myGraph(Graph<String, DefaultEdge> graph){
        this.graph = graph;
        this.nodeCount = graph.vertexSet().size() + "";
        this.nodeLabels = graph.vertexSet() + "";
        this.edgeCount = graph.edgeSet().size() + "";
        this.direction = (graph.edgeSet() + "").replace(":", "->");
    }

    @Override
    public String toString() {
        return ("Number of nodes: " + nodeCount + "\n" +
                "Label of the nodes: " + nodeLabels + "\n" +
                "Number of edges: " + edgeCount + "\n" +
                "Nodes and edge directions: " + direction);
    }

    public static void main(String[] args) throws IOException {
        parseGraph("C:\\Users\\sakur\\Downloads\\test.txt");

        System.out.println(new myGraph(graph));

        outputGraph("C:\\Users\\sakur\\Downloads\\output.txt");
    }

    public static void parseGraph(String filepath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filepath)));

        graph = new SimpleDirectedGraph<>(DefaultEdge.class);
        DOTImporter<String, DefaultEdge> importer = new DOTImporter<>();
        importer.setVertexFactory(id->id);
        importer.importGraph(graph, new StringReader(content));
    }

    public static void outputGraph(String filepath) throws IOException {
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>();
        Writer writer = new StringWriter();
        exporter.exportGraph(graph, writer);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath));
        bufferedWriter.write(writer.toString());
        bufferedWriter.close();
    }

    public static void addNode(String label) {
        if(!graph.vertexSet().contains(label)) {
            graph.addVertex(label);
        }
    }

    public static void removeNode(String label) {
        graph.removeVertex(label);
    }

    public static void addNodes(String[] label) {
        for(int i = 0; i < label.length; i++){
            addNode(label[i]);
        }
    }

    public static void removeNodes(String[] label) {
        for(int i = 0; i < label.length; i++) {
            removeNode(label[i]);
        }
    }

}
