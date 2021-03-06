// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;
import org.junit.Test;

import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T>  extends AbstractBoundedQueue<T>{
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        fillCount=first=last=0;
        this.capacity=capacity;
        rb=(T[]) new Object[capacity];
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if(isFull()){throw new RuntimeException("Ring Buffer Overflow");}
            fillCount = fillCount + 1;
            rb[last] = x;
            last = resize(last);
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if(isEmpty()){throw new RuntimeException("Ring Buffer Underflow");}
        fillCount = fillCount - 1;
        T rv = rb[first];
        first = resize(first);
        return rv;
    }

    private int resize(int x){
        if(x == capacity -1){
            return 0;
        }
        else {
            return x+1;
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
      return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ringBufferIterator<T>();
    }

    private class ringBufferIterator<T> implements Iterator<T>{
        private int ptr;

        public ringBufferIterator() {
            ptr = first;
        }

        @Override
        public boolean hasNext() {
            if(ptr == last ){return false;}
            else {
                return true;
            }
        }

        @Override
        public T next() {
            int x = ptr;
            ptr = resize(ptr);
            return (T) rb[x];
        }
    }




}
