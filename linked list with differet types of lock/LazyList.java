package assgn2;


public class LazyList {
	private Node head;
	public int originallistlen=0;
	public LazyList(){
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}
	
	public LazyList(int len){
		originallistlen=len;
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
		for(int i=0; i<len; i++){
			add(i);
		}
	}
	
	private boolean validate(Node pred, Node curr){
		return !pred.marked && !curr.marked && pred.next ==curr;
	}
	
	 public boolean add(int key) {
		    while (true) {
		      Node pred = this.head;
		      Node curr = head.next;
		      while (curr.key < key) {
		        pred = curr; curr = curr.next;
		      }
		      pred.lock.lock();
		      try {
		        curr.lock.lock();
		        try {
		            if (validate(pred, curr)) {
		              if (curr.key == key) { 
		                return false;
		              } else {               
		                Node Node = new Node(key);
		                Node.next = curr;
		                pred.next = Node;
		                return true;
		              }
		            }
		          } finally { 
		            curr.lock.unlock();
		          }
		        } finally { 
		          pred.lock.unlock();
		        }
		      }
		    }
	 
	 public boolean remove(int key) {
		 while(true){
			 Node pred = head;
			 Node curr = head.next;
			 while(curr.key<key){
				 pred = curr; curr = curr.next;
			 }
			 pred.lock.lock();
			 try{
				 curr.lock.lock();
				 try{
					 if(validate(pred, curr)){
						 if(curr.key != key){
							 return false;
						 } else {
							 curr.marked = true;
							 pred.next = curr.next;
							 return true;				 
						 }
					 }
				 } finally{
					 curr.lock.unlock();
				 }
			 } finally{
				 pred.lock.unlock();
			 }
		 }
	 }
	 
	 public boolean contains(int key) {
		    Node curr = this.head;
		    while (curr.key < key)
		      curr = curr.next;
		    return curr.key == key && !curr.marked;
		  }		    
}
