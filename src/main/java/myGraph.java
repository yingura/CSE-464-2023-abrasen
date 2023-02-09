import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.view.mxGraph;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.dot.DOTImporter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class myGraph {
    static Graph<String, DefaultEdge> graph;

    // Output the number of nodes, the label of the nodes, the number of edges,
    // the nodes and the edge direction of edges
    @Override
    public String toString() {
        return ("Number of nodes: " + graph.vertexSet().size() + "\n" +
                "Label of the nodes: " + graph.vertexSet() + "\n" +
                "Number of edges: " + graph.edgeSet().size() + "\n" +
                "Nodes and edge directions: " + (graph.edgeSet() + "").replace(":", "->"));
    }

    // Empty main function
    public static void main(String[] args) throws IOException {
//        myGraph myGraph = new myGraph();
//
//        parseGraph("src/graphFiles/testGraph.dot");
//        System.out.println("parseGraph:\n" + myGraph);
//
//        outputGraph("src/graphFiles/output.txt");
//
//        addNode("test");
//        System.out.println("\naddNode:\n" + myGraph);
//
//        removeNode("test");
//        System.out.println("\nremoveNode:\n" + myGraph);
//
//        addNodes(new String[]{"test", "test1", "test2", "test3"});
//        System.out.println("\naddNodes:\n"myGraph);
//
//        removeNodes(new String[]{"test1", "test2", "test3"});
//        System.out.println("\nremoveNodes:\n" + myGraph);
//
//        addEdge("0", "10");
//        System.out.println("\naddEdge:\n" + myGraph);
//
//        removeEdge("0", "10");
//        System.out.println("\nremoveEdge:\n" + myGraph);
//
//        outputDOTGraph("src/graphFiles/output.dot");
//
//        outputGraphics("src/graphFilesTest/graphic.jpg", "JPG");


    }

    // Accept a DOT graph file to create a graph
    public static void parseGraph(String filepath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filepath)));

        graph = new SimpleDirectedGraph<>(DefaultEdge.class);
        DOTImporter<String, DefaultEdge> importer = new DOTImporter<>();
        importer.setVertexFactory(id->id);
        importer.importGraph(graph, new StringReader(content));
    }

    // Output graph to file
    public static void outputGraph(String filepath) throws IOException {
        String content = (new myGraph()).toString();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath));
        bufferedWriter.write(content);
        bufferedWriter.close();
    }

    // Add a node and check for duplicate labels
    public static void addNode(String label) {
        if(!graph.vertexSet().contains(label)) {
            graph.addVertex(label);
        }
    }

    // Remove a node
    public static void removeNode(String label) {
        graph.removeVertex(label);
    }

    // Add a list of nodes
    public static void addNodes(String[] label) {
        for(int i = 0; i < label.length; i++){
            addNode(label[i]);
        }
    }

    // Remove a list of nodes
    public static void removeNodes(String[] label) {
        for(int i = 0; i < label.length; i++) {
            removeNode(label[i]);
        }
    }

    // Add an edge and check for duplicate edges
    public static void addEdge(String srcLabel, String dstLabel) {
        if(!graph.containsEdge(srcLabel, dstLabel)) {
            graph.addEdge(srcLabel, dstLabel);
        }
    }

    // Remove an edge
    public static void removeEdge(String srcLabel, String dstLabel) {
        graph.removeEdge(srcLabel, dstLabel);
    }

    // Output the imported graph into a DOT file
    public static void outputDOTGraph(String path) throws IOException {
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>();
        Writer writer = new StringWriter();
        exporter.exportGraph(graph, writer);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
        bufferedWriter.write(writer.toString());
        bufferedWriter.close();
    }

    // Output the imported graph into a graphics
    public static void outputGraphics(String path, String format) throws IOException {
        JGraphXAdapter mxGraph = new JGraphXAdapter(graph);
        mxIGraphLayout layout = new mxCircleLayout(mxGraph);
        layout.execute(mxGraph.getDefaultParent());
        BufferedImage image = mxCellRenderer.createBufferedImage(mxGraph, null, 1, Color.WHITE, true, null);
        ImageIO.write(image, format, new File(path));
    }

}
