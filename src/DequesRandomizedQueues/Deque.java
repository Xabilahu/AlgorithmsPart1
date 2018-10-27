package DequesRandomizedQueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first, last;
    private int count;

    private class Node<Item>{
        Item data;
        Node<Item> next;
        Node<Item> prev;
    }

    // construct an empty deque
    public Deque(){
        this.first = null;
        this.last = null;
        this.count = 0;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return this.first == null;
    }

    // return the number of items on the deque
    public int size(){
        return this.count;
    }

    // add the item to the front
    public void addFirst(Item item){
        if (item != null) {
            Node<Item> newNode = new Node<>();
            newNode.data = item;
            newNode.next = first;
            newNode.prev = null;

            if (this.isEmpty()) this.last = newNode;
            else this.first.prev = newNode;

            this.first = newNode;
            this.count++;
        } else throw new IllegalArgumentException();
    }

    // add the item to the end
    public void addLast(Item item){
        if (item != null) {
            Node<Item> newNode = new Node<>();
            newNode.data = item;
            newNode.next = null;

            if (!this.isEmpty()) this.last.next = newNode;
            else this.first = newNode;

            newNode.prev = this.last;
            this.last = newNode;
            this.count++;
        } else throw new IllegalArgumentException();
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if (!this.isEmpty()) {
            Item ret = this.first.data;
            if (this.size() == 1) {
                this.first = null;
                this.last = null;
            } else {
                this.first = this.first.next;
                this.first.prev = null;
            }
            this.count--;
            return ret;
        } else throw new NoSuchElementException();
    }

    // remove and return the item from the end
    public Item removeLast(){
        if (!this.isEmpty()) {
            Item ret = this.last.data;
            if (this.size() == 1){
                this.first = null;
                this.last = null;
            } else {
                this.last = this.last.prev;
                this.last.next = null;
            }
            this.count--;
            return ret;
        } else throw new NoSuchElementException();
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator(){
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>{

        Node<Item> current;

        public DequeIterator(){
            this.current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (this.hasNext()) {
                Item ret = current.data;
                current = current.next;
                return ret;
            } else throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (optional)
    public static void main(String[] args){
        Deque<Integer> list = new Deque<>();
        list.addFirst(2);
        list.addFirst(3);
        list.addLast(5);
        list.addLast(8);
        list.addLast(9);
        list.removeFirst();

        for (Integer i : list){
            System.out.println(i);
        }
    }
}
