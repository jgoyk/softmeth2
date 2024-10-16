package util;

import clinic.Technician;

public class CircularLinkedList {
    private Node head;
    private Node tail;
    private int size;

    public CircularLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    public int getSize(){
        return size;
    }
    public void setHead(Node head){
        this.head = head;
    }
    public Node getHead(){
        return head;
    }
    public void add(Technician data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
            tail = newNode;

            newNode.next = head;
        } else {
            newNode.next = head;
            tail.next = newNode;
            tail = newNode;
        }
        size += 1;
    }

    public void reverse(){
        if (head == null){
            return;
        }

        Node prev = null;
        Node current = head;
        Node next;
        do {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        } while (current != (head));

        (head).next = prev;
        head = prev;
    }


    public void delete(Technician key) {
        if (head == null) {
            return;
        }

        Node curr = head;
        Node prev = null;
        while (curr.next != head) {
            if (curr.data == key) {
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

                    if (curr == tail) {
                        tail = prev;
                    }
                    return;
                }
            }
            prev = curr;
            curr = curr.next;
        }

        if (curr == head && curr.data == key) {
            prev.next = head;
            tail = prev;
        }
        size -= 1;
    }

}
