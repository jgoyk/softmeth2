package util;

import clinic.Technician;

public class Node {
    Technician data;
    Node next;

    // Constructor to initialize a node with data
    public Node(Technician data) {
        this.data = data;
        this.next = null;
    }
    public Technician getTechnician(){
        return data;
    }
    public Node getNext(){
        return next;
    }
}
