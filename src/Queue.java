
public class Queue<E> extends ListOfLinks<E>{
    public void enqueue(E data){
        add(data);
    }

    public E dequeue(){
        E data = getFirst();
        removeFirst();
        return data;
    }
}
