import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.PriorityQueue;
import java.util.HashMap;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Gino Doumit
 * @userid gdoumit3
 * @GTID 903665016
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        // Exception if vertex not in graph
        if (!graph.getVertices().contains(start)){
            throw new IllegalArgumentException("Start vertex is not in graph");
        }
        if (start == null) {
            throw new IllegalArgumentException("Start vertex is null");
        }
        List<Vertex<T>> list = new ArrayList<>();
        Queue<Vertex<T>> q = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        q.add(start);
        while (!q.isEmpty()){
            Vertex<T> temp = q.poll();
            if (!visited.contains(temp)){
                list.add(temp);
                visited.add(temp);
                for (VertexDistance<T> vertex : graph.getAdjList().get(temp)) {
                    q.add(vertex.getVertex());   
                }
            }
        }
        return list;
    }


    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
         // Exception if vertex not in graph
        if (!graph.getVertices().contains(start)){
            throw new IllegalArgumentException("Start vertex is not in graph");
        }
        if (start == null) {
            throw new IllegalArgumentException("Start vertex is null");
        }
        List<Vertex<T>> list = new ArrayList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        list.add(start);
        dfsHelper(start, graph, list, visited);
        return list;
    }

     /**
     * Recursive helper method for DFS
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the DFS on (source)
     * @param graph the graph we are applying DFS to
     * @return list in DFS order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    private static <T> void dfsHelper(Vertex<T> start, Graph<T> graph, List<Vertex<T>> list, Set<Vertex<T>> visited) {
        visited.add(start);
        list.add(start);
        for (VertexDistance<T> vertex : graph.getAdjList().get(start)) {
            if (!visited.contains(vertex.getVertex())) {
                dfsHelper(vertex.getVertex(), graph, list, visited);
            } 
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        Map<Vertex<T>, Integer> map = new HashMap<>();
        Queue<VertexDistance<T>> q = new PriorityQueue<>();
        Set<Vertex<T>> visited = new HashSet<>();
      
        for (Vertex<T> vertex : graph.getVertices()) {
            map.put(vertex , Integer.MAX_VALUE);
        }
        q.add(new VertexDistance<T>(start , 0));
        while (!q.isEmpty() && visited.size()!= graph.getVertices().size()){
            VertexDistance<T> temp = q.poll();
            if (!visited.contains(temp.getVertex())){
                visited.add(temp.getVertex());
                map.replace(temp.getVertex(), temp.getDistance());
                for (VertexDistance<T> vertex : graph.getAdjList().get(temp.getVertex())) {
                    if (!visited.contains(vertex.getVertex())){
                        q.add(new VertexDistance<T>(vertex.getVertex() , temp.getDistance() + vertex.getDistance()));
                    }
                }
            }
        }
        return map;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        Set<Edge<T>> mst = new HashSet<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Queue<Edge<T>> q = new PriorityQueue<>();
      
        for (Edge<T> edge : graph.getEdges()) {
            if (edge.getU().equals(edge.getV())) {
                continue;
            }  
            if (edge.getU().equals(start)) {
                q.add(edge);
            }             
        }

        visited.add(start);
        while (!q.isEmpty() && visited.size()!= graph.getVertices().size()){
            Edge<T> currEdge = q.poll();
            if (!visited.contains(currEdge.getV())){
                visited.add(currEdge.getV());
                mst.add(currEdge);
                mst.add(new Edge<T>(currEdge.getV(), currEdge.getU(), currEdge.getWeight()));
                for (Edge<T> edge2 : graph.getEdges()) {
                    if (edge2.getU().equals(edge2.getV())) {
                        continue;
                    } 
                    if (edge2.getU().equals(currEdge.getV()) && (!visited.contains(edge2.getV()))) {
                        q.add(edge2);
                    }             
                }
            }
        }
        if (mst.isEmpty())  return null;
        return mst;
    }
}