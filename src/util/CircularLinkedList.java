package util;

import clinic.Technician;

public class CircularLinkedList {
    private Node head;
    private Node tail;

    public CircularLinkedList() {
        head = null;
        tail = null;
    }

    public void add(Technician data) {
        Node newNode = new Node(data);

        // If the list is empty, make the new node
        // the head and tail
        if (head == null) {
            head = newNode;
            tail = newNode;

            // Point to itself in a circular list
            newNode.next = head;
        } else {
            newNode.next = head;
            tail.next = newNode;
            tail = newNode;
        }
    }

    // Method to delete the node with the given
    // key from the circular linked list
    public void delete(Technician key) {
        if (head == null) {
            return;
        }

        Node curr = head;
        Node prev = null;
        while (curr.next != head) {
            if (curr.data == key) {
                // If the node to be deleted is the head node
                if (prev == null) {
                    Node last = head;
                    while (last.next != head) {
                        last = last.next;
                    }
                    head = curr.next;
                    last.next = head;
                    return;
                } else {
                    prev.next = curr.next;

                    // Update tail if the last node is deleted
                    if (curr == tail) {
                        tail = prev;
                    }
                    return;
                }
            }
            prev = curr;
            curr = curr.next;
        }

        // Check if the node to be deleted is the last node
        if (curr == head && curr.data == key) {
            prev.next = head;

            // Update tail if the last node is deleted
            tail = prev;
        }
    }
}
