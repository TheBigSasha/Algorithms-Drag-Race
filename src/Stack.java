
public class Stack<E> extends ListOfLinks<E>{
    public E pop(){
        E data = getLast();
        removeLast();
        return data;
    }

    public void push(E data){
        add(data);
    }
}
