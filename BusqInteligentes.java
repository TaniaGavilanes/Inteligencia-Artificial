    import java.util.*;

class Node {
    String name;
    int x, y; // Coordenadas para heurística
    Map<Node, Integer> neighbors;

    public Node(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.neighbors = new HashMap<>();
    }

    public void addNeighbor(Node neighbor, int cost) {
        neighbors.put(neighbor, cost);
    }

    @Override
    public String toString() {
        return name;
    }
}

public class BusqInteligentes {

    // ----------------- BFS -----------------
    public static void bfs(Node start, Node goal) {
        Set<Node> visited = new HashSet<>();
        Queue<List<Node>> queue = new LinkedList<>();
        List<Node> path = new ArrayList<>();
        path.add(start);
        queue.add(path);

        while (!queue.isEmpty()) {
            List<Node> currentPath = queue.poll();
            Node currentNode = currentPath.get(currentPath.size() - 1);

            if (visited.contains(currentNode)) continue;
            visited.add(currentNode);

            if (currentNode.equals(goal)) {
                System.out.println("BFS - Camino encontrado: " + currentPath);
                return;
            }

            for (Node neighbor : currentNode.neighbors.keySet()) {
                if (!visited.contains(neighbor)) {
                    List<Node> newPath = new ArrayList<>(currentPath);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }

        System.out.println("BFS - No se encontró un camino.");
    }

    // ----------------- DFS -----------------
    public static void dfs(Node start, Node goal) {
        Set<Node> visited = new HashSet<>();
        Stack<List<Node>> stack = new Stack<>();
        List<Node> path = new ArrayList<>();
        path.add(start);
        stack.push(path);

        while (!stack.isEmpty()) {
            List<Node> currentPath = stack.pop();
            Node currentNode = currentPath.get(currentPath.size() - 1);

            if (visited.contains(currentNode)) continue;
            visited.add(currentNode);

            if (currentNode.equals(goal)) {
                System.out.println("DFS - Camino encontrado: " + currentPath);
                return;
            }

            for (Node neighbor : currentNode.neighbors.keySet()) {
                if (!visited.contains(neighbor)) {
                    List<Node> newPath = new ArrayList<>(currentPath);
                    newPath.add(neighbor);
                    stack.push(newPath);
                }
            }
        }

        System.out.println("DFS - No se encontró un camino.");
    }

    // ----------------- UCS -----------------
    public static void uniformCostSearch(Node start, Node goal) {
        PriorityQueue<List<Node>> queue = new PriorityQueue<>(Comparator.comparingInt(BusqInteligentes::getPathCost));
        Set<Node> visited = new HashSet<>();

        List<Node> path = new ArrayList<>();
        path.add(start);
        queue.add(path);

        while (!queue.isEmpty()) {
            List<Node> currentPath = queue.poll();
            Node currentNode = currentPath.get(currentPath.size() - 1);

            if (visited.contains(currentNode)) continue;
            visited.add(currentNode);

            if (currentNode.equals(goal)) {
                System.out.println("UCS - Camino encontrado: " + currentPath + " con costo: " + getPathCost(currentPath));
                return;
            }

            for (Node neighbor : currentNode.neighbors.keySet()) {
                if (!visited.contains(neighbor)) {
                    List<Node> newPath = new ArrayList<>(currentPath);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }

        System.out.println("UCS - No se encontró un camino.");
    }

    public static int getPathCost(List<Node> path) {
        int cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Node from = path.get(i);
            Node to = path.get(i + 1);
            cost += from.neighbors.get(to);
        }
        return cost;
    }

    // ----------------- Heurística -----------------
    public static int heuristic(Node current, Node goal) {
        double dist = Math.sqrt(Math.pow(current.x - goal.x, 2) + Math.pow(current.y - goal.y, 2));
        return (int) dist;
    }

    // ----------------- Búsqueda Bidireccional -----------------
    public static void bidirectionalSearch(Node start, Node goal) {
        Set<Node> visitedFromStart = new HashSet<>();
        Set<Node> visitedFromGoal = new HashSet<>();

        Queue<Node> queueFromStart = new LinkedList<>();
        Queue<Node> queueFromGoal = new LinkedList<>();

        queueFromStart.add(start);
        queueFromGoal.add(goal);

        while (!queueFromStart.isEmpty() && !queueFromGoal.isEmpty()) {
            Node nodeFromStart = queueFromStart.poll();
            Node nodeFromGoal = queueFromGoal.poll();

            visitedFromStart.add(nodeFromStart);
            visitedFromGoal.add(nodeFromGoal);

            for (Node n : visitedFromStart) {
                if (visitedFromGoal.contains(n)) {
                    System.out.println("Bidireccional - Camino encontrado en nodo: " + n);
                    return;
                }
            }

            for (Node neighbor : nodeFromStart.neighbors.keySet()) {
                if (!visitedFromStart.contains(neighbor)) {
                    queueFromStart.add(neighbor);
                }
            }

            for (Node neighbor : nodeFromGoal.neighbors.keySet()) {
                if (!visitedFromGoal.contains(neighbor)) {
                    queueFromGoal.add(neighbor);
                }
            }
        }

        System.out.println("Bidireccional - No se encontró camino.");
    }

    // ----------------- DFS Limitada -----------------
    public static boolean depthLimitedSearch(Node current, Node goal, int limit, List<Node> path, Set<Node> visited) {
        visited.add(current);
        path.add(current);

        if (current.equals(goal)) {
            return true;
        }

        if (limit <= 0) {
            path.remove(path.size() - 1);
            return false;
        }

        for (Node neighbor : current.neighbors.keySet()) {
            if (!visited.contains(neighbor)) {
                if (depthLimitedSearch(neighbor, goal, limit - 1, path, visited)) {
                    return true;
                }
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    public static void dfsLimitada(Node start, Node goal, int limit) {
        List<Node> path = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        boolean found = depthLimitedSearch(start, goal, limit, path, visited);

        if (found) {
            System.out.println("DFS Limitada - Camino encontrado: " + path);
        } else {
            System.out.println("DFS Limitada - No se encontró camino dentro del límite.");
        }
    }

    // ----------------- DFS Iterativa -----------------
    public static void dfsIterativa(Node start, Node goal, int maxDepth) {
        for (int depth = 0; depth <= maxDepth; depth++) {
            System.out.println("Iterativa - Intentando con profundidad límite: " + depth);
            List<Node> path = new ArrayList<>();
            Set<Node> visited = new HashSet<>();
            boolean found = depthLimitedSearch(start, goal, depth, path, visited);
            if (found) {
                System.out.println("DFS Iterativa - Camino encontrado: " + path);
                return;
            }
        }
        System.out.println("DFS Iterativa - No se encontró camino.");
    }

    // ----------------- MAIN -----------------
    public static void main(String[] args) {
        // Crear nodos con coordenadas (x, y)
        Node A = new Node("A", 0, 0);
        Node B = new Node("B", 1, 1);
        Node C = new Node("C", 2, 0);
        Node D = new Node("D", 3, 1);
        Node E = new Node("E", 4, 0);

        // Conexiones
        A.addNeighbor(B, 1);
        A.addNeighbor(C, 4);
        B.addNeighbor(D, 2);
        C.addNeighbor(D, 1);
        D.addNeighbor(E, 3);

        System.out.println("==== Búsqueda en Anchura ====");
        bfs(A, E);

        System.out.println("\n==== Búsqueda en Profundidad ====");
        dfs(A, E);

        System.out.println("\n==== Búsqueda de Costo Uniforme ====");
        uniformCostSearch(A, E);

        System.out.println("\n==== Búsqueda Bidireccional ====");
        bidirectionalSearch(A, E);

        System.out.println("\n==== Búsqueda DFS Limitada (límite = 3) ====");
        dfsLimitada(A, E, 3);

        System.out.println("\n==== Búsqueda DFS Iterativa (max profundidad = 5) ====");
        dfsIterativa(A, E, 5);
    }
}



