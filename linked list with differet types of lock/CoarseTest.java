package assgn2;


public class CoarseTest  {
	  private final static int THREADS = 64;
	  private final static int COUNT = 64*500;
	  Thread[] thread = new Thread[THREADS];
	  volatile int counter = 64*500;
	  
	  CoarseList CoarseInstance = new CoarseList(100);
	  
	  //java.util.concurrent.locks.Lock lock = new java.util.concurrent.locks.ReentrantLock();   
	  
	  public void testParallel() throws Exception {
	    System.out.println("test parallel");
	    
	    //for(Node iter=CoarseInstance.head; iter!=null; iter=iter.next)
	    //{
	    	//System.out.println(iter.key);
	    //}
	    
	    
	    for (int i = 0; i < THREADS; i++) {
	      thread[i] = new MyThread();
	    }
	    for (int i = 0; i < THREADS; i++) {
	      thread[i].start();
	    }
	    for (int i = 0; i < THREADS; i++) {
	      thread[i].join();
	    }
	    if (counter != COUNT) {
		System.out.println("Wrong! " + counter + " " + COUNT);
	    }
	  }
	  
	  class MyThread extends Thread {
	    public void run() {
	    	for(int i=0; i<10; i++){
	    		CoarseInstance.add((int)(Math.random()*CoarseInstance.originallistlen*2));	
	    		CoarseInstance.add((int)(Math.random()*CoarseInstance.originallistlen*2));
	    		CoarseInstance.contains((int)(Math.random()*CoarseInstance.originallistlen*2));
	    		CoarseInstance.add((int)(Math.random()*CoarseInstance.originallistlen*2));
	    		CoarseInstance.add((int)(Math.random()*CoarseInstance.originallistlen*2));
	    		
	    		CoarseInstance.remove((int)(Math.random()*CoarseInstance.originallistlen*2));
	    		CoarseInstance.remove((int)(Math.random()*CoarseInstance.originallistlen*2));
	    		CoarseInstance.contains((int)(Math.random()*CoarseInstance.originallistlen*2));
	    		CoarseInstance.remove((int)(Math.random()*CoarseInstance.originallistlen*2));
	    		CoarseInstance.remove((int)(Math.random()*CoarseInstance.originallistlen*2));
	    		
	    	}
	    	
	    	
	    	
	    }
	  }

	  public static void main(String[] args) {
		  
		CoarseTest mft = new CoarseTest();		
		long start = System.currentTimeMillis();
	    try {
	      mft.testParallel();
	    }
	    catch (Exception e) {}   
	    long time = System.currentTimeMillis() - start; 
	    System.out.println("Time cost is "+ time);
	  }

	}
