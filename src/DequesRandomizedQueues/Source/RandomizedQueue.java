package DequesRandomizedQueues.Source;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int toq;

    // construct an empty randomized queue
    public RandomizedQueue(){
        this.queue = (Item[]) new Object[10];
        this.toq = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return this.toq == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return this.toq;
    }

    // add the item
    public void enqueue(Item item){
        if (item != null) {
            if (this.toq == this.queue.length) this.resize(2*this.queue.length);
            this.queue[this.toq++] = item;
        } else throw new IllegalArgumentException();
    }

    // remove and return a random item
    public Item dequeue(){
        if (!this.isEmpty()){
            int i = StdRandom.uniform(this.toq);
            Item ret = this.queue[i];
            this.queue[i] = this.queue[--this.toq];
            if (this.toq > 0 && this.toq == this.queue.length/4) this.resize(this.queue.length/2);
            return ret;
        } else throw new NoSuchElementException();
    }

    private void resize(int length){
        Item[] newQueue = (Item[]) new Object[length];
        for(int counter = 0; counter < this.toq; counter++){
            newQueue[counter] = this.queue[counter];
        }
        this.queue = newQueue;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if (!this.isEmpty()){
            return this.queue[StdRandom.uniform(this.toq)];
        } else throw new NoSuchElementException();
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item>{

        private int current;
        private Item[] shuffledQueue;

        public RandomizedQueueIterator(){
            this.current = 0;
            this.shuffledQueue = (Item[])new Object[toq];
            for (int j = 0; j < this.shuffledQueue.length; j++){
                this.shuffledQueue[j] = queue[j];
            }
            StdRandom.shuffle(this.shuffledQueue);
        }

        @Override
        public boolean hasNext() {
            return this.current < toq;
        }

        @Override
        public Item next() {
            if (this.hasNext()) return this.shuffledQueue[this.current++];
            else throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (optional)
    public static void main(String[] args){
        RandomizedQueue<Integer> rQueue = new RandomizedQueue<>();
        rQueue.enqueue(3);
        rQueue.enqueue(2);
        rQueue.enqueue(5);
        rQueue.enqueue(8);
        rQueue.enqueue(9);

        for (int i : rQueue){
            System.out.println(i);
        }
    }
}
