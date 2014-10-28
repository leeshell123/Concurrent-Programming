package assgn2;


public class FineList {
	private Node head;
	public int originallistlen=0;
	public FineList(){
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}
	
	public FineList(int len){
		originallistlen=len;
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
		for(int i=0; i<len; i++){
			add(i);
		}
	}
	
	public boolean add(int key) {
		
	    head.lock.lock();
	    Node pred = head;
	    try {
	      Node curr = pred.next;
	      curr.lock.lock();
	      try {
	        while (curr.key < key) {
	          pred.lock.unlock();
	          pred = curr;
	          curr = curr.next;
	          curr.lock.lock();
	        }
	        
	        if (curr.key == key) {
	            return false;
	          }
	          Node newNode = new Node(key);
	          newNode.next = curr;
	          pred.next = newNode;
	          return true;
	        } finally {
	          curr.lock.unlock();
	        }
	      } finally {
	        pred.lock.unlock();
	      }
	    }
	
	public boolean remove(int key){
		Node pred = null, curr = null;
	    head.lock.lock();
	    try {
	      pred = head;
	      curr = pred.next;
	      curr.lock.lock();
	      try {
	        while (curr.key < key) {
	          pred.lock.unlock();
	          pred = curr;
	          curr = curr.next;
	          curr.lock.lock();
	        }
	        
	        if (curr.key == key) {
	            pred.next = curr.next;
	            return true;
	          }

	          return false;
	        } finally {
	          curr.lock.unlock();
	        }
	      } finally {
	        pred.lock.unlock();
	      }
	    }
	
	public boolean contains(int key) {
	    Node pred = null, curr = null;
	    head.lock.lock();
	    try {
	      pred = head;
	      curr = pred.next;
	      curr.lock.lock();
	      try {
	        while (curr.key < key) {
	          pred.lock.unlock();
	          pred = curr;
	          curr = curr.next;
	          curr.lock.lock();
	        }
	        return (curr.key == key);
	      } finally {
	        curr.lock.unlock();
	      }
	    } finally {
	      pred.lock.unlock();
	    }
	  }

	
   }



