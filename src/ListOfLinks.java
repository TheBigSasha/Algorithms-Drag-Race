
import java.util.Iterator;
import java.util.List;

public class ListOfLinks<E> implements Iterable<E>{
    private ListNode<E> head;
    private ListNode<E> tail;
    private int size;

    public ListOfLinks() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0 || head == null;
    }


    public E getFirst() {
        return head.data;
    }

    public int size() {                                          //Returns the number of elements in the list by iterating thru and counting
        return size;
        //Also works if you have a while loop going through the list and counting how many times you need to go to count the whole list
        //If you have a system where you can combine lists directly by manipulating nodes, you may not always be able to trust int size.
    }

    public E get(int index) throws IndexOutOfBoundsException {   //Gets a node at an index
        if(index > this.size() - 1 || index < 0) throw new IndexOutOfBoundsException("Index " + index + " out of bounds for list of size " + size());
        int counter = 0;
        E data = null;
        ListNode<E> temp = head;
        while(temp != null){
            if(counter == index){
                return temp.getData();
            }
            temp = temp.next;
            counter++;
        }
        return null;
    }

    public E getFaster(int index) throws IndexOutOfBoundsException {   //Gets a node at an index
        if(index > this.size() - 1 || index < 0) throw new IndexOutOfBoundsException("Index " + index + " out of bounds for list of size " + size());
        if(index > size/2){ //start from end because closer to end
            int counter = size - 1;
            E data = null;
            ListNode<E> temp = tail;
            while(temp != null){
                if(counter == index){
                    return temp.getData();
                }
                temp = temp.previous;
                counter--;
            }
            return null;
        }else{ //start from start because start is closer to index
            int counter = 0;
            E data = null;
            ListNode<E> temp = head;
            while(temp != null){
                if(counter == index){
                    return temp.getData();
                }
                temp = temp.next;
                counter++;
            }
            return null;
        }

    }


    private ListNode<E> getNode(int index) throws IndexOutOfBoundsException {   //Gets a node at an index

        if(index > this.size() - 1 || index < 0) throw new IndexOutOfBoundsException("Index " + index + " out of bounds for list of size " + size());

        int counter = 0;
        E data = null;
        ListNode<E> temp = head;
        while(temp != null){
            if(counter == index){
                return temp;
            }
            temp = temp.next;
            counter++;
        }
        return null;
    }

    /**
     * Adds to the end of the list
     * @param data stuff to add
     */
    public void add(E data) {                                    //sets head if head is null, else adds a node'
        ListNode<E> newNode = new ListNode<>(data);         // We create a new node from the data we are given
        if(this.isEmpty() || this.head == null){
            this.head = newNode;
            this.head.next = tail;
            this.tail = head;
        }else if(this.size == 1){
            this.head.next = newNode;
            newNode.previous = head;
            tail = newNode;
        }else{
            this.tail.next = newNode;
            newNode.previous = this.tail;
            this.tail = newNode;
        }

        size++;                                             // We track our size
    }


    private ListNode<E> getLastNode() {                               //iterates thru the list till the node's next is null (last node)
        ListNode<E> temp = head;
        while(temp.next()!=null){
            temp = temp.next();
        }
        return temp;
    }

    public E getLast(){
        return tail.getData();
    }

    public void removeFirst(){
        if(this.size <=1){
            head = null;
            tail = null;
        }else{
            this.head = this.head.next;
            this.head.previous = null;
        }
        size--;
    }

    public E pop(){     //Slow and bad, you should use a doubly linked list :)
        E data = getLast();
        removeLast();
        return data;
    }
    //TODO: We fixed this method! Maybe test and fix the rest if you want practice, and notice which ones are O(1), O(N), etc.
    public void removeLast() {
        if(this.size == 0){
            return;
        }
        if(this.size == 1){
            this.head = null;
        }else {
            tail.previous.next = null;
            tail = tail.previous;

        }
        size--;
    }

/*    public String toString() {            // we are replacing this with a recursive solution
        //Print all the stuff in this list, comma separated!
        if(this.isEmpty()) return "";               //This caused a crash because we did not consider null when pritning
        StringBuilder sb = new StringBuilder();     //We create a mutable string
        ListNode<E> temp = head;                    //We set our reference to head
        while(temp != null){                        //We loop through
            sb.append(temp.data.toString() + ", ");
            temp = temp.next;

        }
        return sb.toString();                              //return our thing
    }*/

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<E> iterator() { //TODO: Speed optimize
        return new Iterator<E>() {

            ListNode<E> current = head;

            /**
             * Returns {@code true} if the iteration has more elements.
             * (In other words, returns {@code true} if {@link #next} would
             * return an element rather than throwing an exception.)
             *
             * @return {@code true} if the iteration has more elements
             */
            @Override
            public boolean hasNext() {
                return current != null;
            }

            /**
             * Returns the next element in the iteration.
             *
             * @return the next element in the iteration
             */
            @Override
            public E next() {
                E data = current.getData();
                current = current.next();
                return data;
            }
        };
    }


    private class ListNode<E> {                                  //List node: stores a bit of data and a pointer to next
        private ListNode<E> next;


        public ListNode<E> previous() {
            return previous;
        }

        public void setPrevious(ListNode<E> previous) {
            this.previous = previous;
        }

        private ListNode<E> previous;
        E data;

        public ListNode(E data) {
            this.data = data;
            this.next = null;
        }

        public ListNode<E> next() {
            return next;
        }

        public void setNext(ListNode<E> next) {
            this.next = next;
        }

        public E getData() {
            return data;
        }

        public void setData(E data) {
            this.data = data;
        }

    }

    public int recursiveSizeCheck(){
        return recursiveSizeCheckHelper(0, head);

    }

    private int recursiveSizeCheckHelper(int size, ListNode<E> node){
        size++;
        if(node.next != null) {
            return recursiveSizeCheckHelper(size, node.next);
        }else{
            return size;
        }

    }


    public int indexOf(E data){
        return recursiveIndexHelper(0, data, head);
    }

    private int recursiveIndexHelper(int index, E data, ListNode<E> node){
        if(node.data.equals(data)) return index;
        if(node.next != null){
            index++;
            return recursiveIndexHelper(index, data, node.next);
        }else{
            return -1;
        }

    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        return recursiveToStringHelper(sb, head);
    }

    private String recursiveToStringHelper(StringBuilder sb, ListNode<E> node){
        sb.append(node.data);
        if(node.next != null){
            sb.append(", ");
            return recursiveToStringHelper(sb, node.next);
        }else{
            return sb.toString();
        }

    }
}