package assgn2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CoarseList {
	private Node head;
	private Lock lock = new ReentrantLock();
	public int originallistlen=0;
	public CoarseList(){
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}
	
	public CoarseList(int len){
		originallistlen=len;
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
		//Node iter=head;
		
		for(int i=0; i<len; i++){
			add(i);
		}
	}
	
	public boolean add(int key){
		Node pred, curr;
		lock.lock();
		try{
			pred = head;
			curr = pred.next;
			while(curr.key < key){
				pred = curr;
				curr = curr.next;
			}
			if (key == curr.key){
				return false;
			} else {
				Node node = new Node(key);
				node.next = curr;
				pred.next = node;
				return true;
			}
		} finally {
			lock.unlock();
		}		
	}
	
	public boolean remove(int key) {
	    Node pred, curr;
	    lock.lock();
	    try {
	      pred = this.head;
	      curr = pred.next;
	      while (curr.key < key) {
	        pred = curr;
	        curr = curr.next;
	      }
	      if (key == curr.key) {
	        pred.next = curr.next;
	        return true;
	      } else {
	        return false;         
	      }
	    } finally {               
	      lock.unlock();
	    }
	  }
	
	public boolean contains(int key) {
	    Node pred, curr;
	    lock.lock();
	    try {
	      pred = head;
	      curr = pred.next;
	      while (curr.key < key) {
	        pred = curr;
	        curr = curr.next;
	      }
	      return (key == curr.key);
	    } finally {lock.unlock();
	    }
	  }


}

class Node{
	Node next;
	Lock lock;
	boolean marked;
	int key;
	Node(int key){
		marked = false;
		lock = new ReentrantLock();
		this.key = key;
	}
}