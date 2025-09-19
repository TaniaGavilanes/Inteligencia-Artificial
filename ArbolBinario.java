// Arbol Binario
class Nodo {
    String nombre;
    Nodo izquierda;
    Nodo derecha;

    public Nodo(String nombre) {
        this.nombre = nombre;
        this.izquierda = null;
        this.derecha = null;
    }
}

// Clase Arbol
class Arbol {
    private Nodo raiz;

    public Arbol() {
        raiz = null;
    }

    // Método vacio
    public boolean vacio() {
        return raiz == null;
    }

    // Método insertar
    public void insertar(String nombre) {
        raiz = insertarRec(raiz, nombre);
    }

    private Nodo insertarRec(Nodo actual, String nombre) {
        if (actual == null) {
            return new Nodo(nombre);
        }

        // Comparación para árbol binario de búsqueda
        if (nombre.compareToIgnoreCase(actual.nombre) < 0) {
            actual.izquierda = insertarRec(actual.izquierda, nombre);
        } else if (nombre.compareToIgnoreCase(actual.nombre) > 0) {
            actual.derecha = insertarRec(actual.derecha, nombre);
        }
        return actual;
    }

    // Método buscarNodo
    public Nodo buscarNodo(String nombre) {
        return buscarRec(raiz, nombre);
    }

    private Nodo buscarRec(Nodo actual, String nombre) {
        if (actual == null || actual.nombre.equalsIgnoreCase(nombre)) {
            return actual;
        }
        if (nombre.compareToIgnoreCase(actual.nombre) < 0) {
            return buscarRec(actual.izquierda, nombre);
        } else {
            return buscarRec(actual.derecha, nombre);
        }
    }

    // Método imprimirArbol
    public void imprimirArbol() {
        imprimirInOrden(raiz);
        System.out.println();
    }

    private void imprimirInOrden(Nodo actual) {
        if (actual != null) {
            imprimirInOrden(actual.izquierda);
            System.out.print(actual.nombre + " ");
            imprimirInOrden(actual.derecha);
        }
    }
}

// Clase principal 
public class ArbolBinario {
    public static void main(String[] args) {
        Arbol arbol = new Arbol();

        // Insertar nodos
        arbol.insertar("Juan");
        arbol.insertar("Ana");
        arbol.insertar("Pedro");
        arbol.insertar("Maria");
        arbol.insertar("Luis");

        // Imprimir árbol
        System.out.println("Árbol en orden:");
        arbol.imprimirArbol();

        // Buscar nodos
        String nombreBuscar = "Maria";
        Nodo encontrado = arbol.buscarNodo(nombreBuscar);
        if (encontrado != null) {
            System.out.println("Nodo encontrado: " + encontrado.nombre);
        } else {
            System.out.println("Nodo " + nombreBuscar + " no encontrado.");
        }

        // Verificar si está vacío
        System.out.println("¿El árbol está vacío?: " + arbol.vacio());
    }
}

