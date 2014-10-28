package assgn2;


public class LazyModifiedList {
	private Node head;
	public int originallistlen=0;
	public LazyModifiedList(){
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
		//head.next.next= null;
	}
	
	public LazyModifiedList(int len){
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
		    boolean result=false;
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
		        	result = validate(pred, curr);
		            if (result) {
		              if (curr.key == key) { 
		                return false;
		              } else {               
		                Node Node = new Node(key);
		                Node.next = curr;
		                pred.next = Node;
		                return true;
		              }
		            }
		            else{
		            	pred.lock.unlock();
		            	curr.lock.unlock();
		            	cleanup();
		            }
		          } finally { 
		        	  if(result){curr.lock.unlock();}
		            
		          }
		        } finally { 
		          if(result){pred.lock.unlock();}
		        }
		      }
		    }
	 
	 public boolean remove(int key) {
		 boolean result=true;
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
					 result=validate(pred,curr);
					 if(result){
						 if(curr.key != key || curr.marked ==true){
							 return false;
						 } else {
							 curr.marked = true;
							 //pred.next = curr.next;
							 return true;				 
						 }
					 }
					 else{
			            	pred.lock.unlock();
			            	curr.lock.unlock();
			            	cleanup();
			            }
					 
				 } finally{
					 if(result){curr.lock.unlock();}
				 }
			 } finally{
				 if(result){pred.lock.unlock();}
			 }
		 }
	 }
	 
	 public void cleanup(){
		 head.lock.lock();
		    Node pred = head;
		    try {
		      Node curr = pred.next;
		      curr.lock.lock();
		      try {
		        while (curr.next != null) {
		        	if(curr.marked==true)
		        	{
		        		pred.next = curr.next;
		        	}
		          pred.lock.unlock();
		          pred = curr;
		          curr = curr.next;
		          curr.lock.lock();
		        }
		        } finally {
		          curr.lock.unlock();
		        }
		      } finally {
		        pred.lock.unlock();
		      }
		 
		 
	 }
	 
	 public boolean contains(int key) {
		    Node curr = this.head;
		    while (curr.key < key)
		      curr = curr.next;
		    return curr.key == key && !curr.marked;
		  }		    
}

