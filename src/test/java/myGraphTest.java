import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class myGraphTest {

    myGraph myGraph;

    @BeforeEach
    void setUp() throws IOException {
        myGraph = new myGraph();
        myGraph.parseGraph("src/graphFiles/testGraph.dot");
    }

    @Test
    @DisplayName("Feature 1")
    void featureOneTest() throws IOException {

        // Test parseGraph()
        assertEquals(
                "Number of nodes: 11\n" +
                "Label of the nodes: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]\n" +
                "Number of edges: 13\n" +
                "Nodes and edge directions: [(0 -> 4), (0 -> 1), (1 -> 2), (2 -> 7), " +
                "(2 -> 3), (3 -> 7), (4 -> 5), (5 -> 6), (6 -> 7), (7 -> 9), (7 -> 8), " +
                "(8 -> 10), (9 -> 10)]",
                myGraph.toString()
        );

        // Test outputGraph()
        File file = new File("src/graphFiles/output.txt");
        myGraph.outputGraph("src/graphFiles/output.txt");
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    @DisplayName("Feature 2")
    void featureTwoTest() throws IOException {
        // Test addNode() for new and repeated labels
        myGraph.addNode("0");
        assertEquals(11, myGraph.graph.vertexSet().size());
        myGraph.addNode("test");
        assertEquals(12, myGraph.graph.vertexSet().size());

        // Test removeNode()
        myGraph.removeNode("0");
        assertEquals(11, myGraph.graph.vertexSet().size());

        // Test addNodes() for new and repeated labels
        myGraph.addNodes(new String[]{"test", "test1", "test2", "test3"});
        assertEquals(
                "Number of nodes: 14\n" +
                        "Label of the nodes: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, test, test1, test2, test3]\n" +
                        "Number of edges: 11\n" +
                        "Nodes and edge directions: [(1 -> 2), (2 -> 7), (2 -> 3), (3 -> 7), (4 -> 5), " +
                        "(5 -> 6), (6 -> 7), (7 -> 9), (7 -> 8), (8 -> 10), (9 -> 10)]",
                myGraph.toString()
        );

        // Test removeNodes()
        myGraph.removeNodes(new String[]{"test", "test1", "test2", "test3"});
        assertEquals(
                "Number of nodes: 10\n" +
                        "Label of the nodes: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]\n" +
                        "Number of edges: 11\n" +
                        "Nodes and edge directions: [(1 -> 2), (2 -> 7), " +
                        "(2 -> 3), (3 -> 7), (4 -> 5), (5 -> 6), (6 -> 7), (7 -> 9), (7 -> 8), " +
                        "(8 -> 10), (9 -> 10)]",
                myGraph.toString()
        );
    }

    @Test
    @DisplayName("Feature 3")
    void featureThreeTest() throws IOException {
        // Test addEdge()
        myGraph.addEdge("0", "10");
        assertEquals(14, myGraph.graph.edgeSet().size());

        // Test removeEdge()
        myGraph.removeEdge("0", "10");
        assertEquals(13, myGraph.graph.edgeSet().size());
    }

    @Test
    @DisplayName("Feature 4")
    void featureFourTest() throws IOException {
        // Test outputDOTGraph()
        File file = new File("src/graphFiles/output.dot");
        myGraph.outputDOTGraph("src/graphFiles/output.dot");
        assertTrue(file.exists());
        file.delete();

        // Test outputGraphics() for PNG format
        File file1 = new File("src/graphFiles/graphic.png");
        myGraph.outputGraphics("src/graphFiles/graphic.png", "PNG");
        assertTrue(file1.exists());
        file1.delete();

        // Test outputGraphics() for JPG format
        File file2 = new File("src/graphFiles/graphic.jpg");
        myGraph.outputGraphics("src/graphFiles/graphic.jpg", "JPG");
        assertTrue(file2.exists());
        file2.delete();
    }

}
