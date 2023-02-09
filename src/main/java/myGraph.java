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

        outputDOTGraph("C:\\Users\\sakur\\Downloads\\graph.dot");

        outputGraphics("C:\\Users\\sakur\\Downloads\\image.jpg", "JPG");
    }

    public static void parseGraph(String filepath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filepath)));

        graph = new SimpleDirectedGraph<>(DefaultEdge.class);
        DOTImporter<String, DefaultEdge> importer = new DOTImporter<>();
        importer.setVertexFactory(id->id);
        importer.importGraph(graph, new StringReader(content));
    }

    public static void outputGraph(String filepath) throws IOException {
        String content = (new myGraph(graph)).toString();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath));
        bufferedWriter.write(content);
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

    public static void addEdge(String srcLabel, String dstLabel) {
        if(!graph.containsEdge(srcLabel, dstLabel)) {
            graph.addEdge(srcLabel, dstLabel);
        }
    }

    public static void removeEdge(String srcLabel, String dstLabel) {
        graph.removeEdge(srcLabel, dstLabel);
    }

    public static void outputDOTGraph(String path) throws IOException {
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>();
        Writer writer = new StringWriter();
        exporter.exportGraph(graph, writer);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
        bufferedWriter.write(writer.toString());
        bufferedWriter.close();
    }

    public static void outputGraphics(String path, String format) throws IOException {
        JGraphXAdapter mxGraph = new JGraphXAdapter(graph);
        mxIGraphLayout layout = new mxCircleLayout(mxGraph);
        layout.execute(mxGraph.getDefaultParent());
        BufferedImage image = mxCellRenderer.createBufferedImage(mxGraph, null, 1, Color.WHITE, true, null);
        ImageIO.write(image, format, new File(path));
    }

}
