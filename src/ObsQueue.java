import java.util.Iterator;
import java.util.NoSuchElementException;

public class ObsQueue<Item> implements Iterable<Item>{
	private int N;
	private Node first;
	private Node last;
	
	private class Node{
		Item item;
		Node next;
	}
	
	public void enqueue(Item item){
		Node oldLast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		if(isEmpty()){
			first = last;
		}else{
			oldLast.next = last;
		}
		N++;
	}
	
	public Item dequeue(){
		if(!isEmpty()){
			Item item = first.item;
			first = first.next;
			if(isEmpty()){
				last = null;
			}
			if(N > 0){
				N--;
			}
			return item;
		}
		return null;
	}
	
	private boolean isEmpty(){
		return N == 0;
	}
	
	public int getSize(){
		return N;
	}
	
	public Item getFirst(){
		if(N == 0){
			return null;
		}
		return first.item;
	}

	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new ListIterator();
	}
	
	public void dequeueAll(){
		for(int i = N; i > 0; i--){
			dequeue();
		}
	}
	
	private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
}
