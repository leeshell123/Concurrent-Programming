package assgn2;



public class FineTest  {
	  private final static int THREADS = 8;
	  private final static int COUNT = 64*500;
	  Thread[] thread = new Thread[THREADS];
	  volatile int counter = 64*500;
	  
	  FineList FineInstance = new FineList(100);
	  
	  //java.util.concurrent.locks.Lock lock = new java.util.concurrent.locks.ReentrantLock();   
	  
	  public void testParallel() throws Exception {
	    System.out.println("test parallel");
	    //ThreadID.reset();
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
	    		
	    		FineInstance.add((int)(Math.random()*FineInstance.originallistlen*2));	
	    		FineInstance.add((int)(Math.random()*FineInstance.originallistlen*2));
	    		FineInstance.contains((int)(Math.random()*FineInstance.originallistlen*2));
	    		FineInstance.add((int)(Math.random()*FineInstance.originallistlen*2));
	    		FineInstance.add((int)(Math.random()*FineInstance.originallistlen*2));
	    		
	    		FineInstance.remove((int)(Math.random()*FineInstance.originallistlen*2));
	    		FineInstance.contains((int)(Math.random()*FineInstance.originallistlen*2));
	    		FineInstance.remove((int)(Math.random()*FineInstance.originallistlen*2));
	    		FineInstance.remove((int)(Math.random()*FineInstance.originallistlen*2));
	    		FineInstance.remove((int)(Math.random()*FineInstance.originallistlen*2));
	    		
	    		
	    	}
	    	
	    	
	    	
	    }
	  }

	  public static void main(String[] args) {
		  
		
		  
		FineTest mft = new FineTest();
		long start = System.currentTimeMillis();
	    try {
	      mft.testParallel();
	    }
	    catch (Exception e) {}   
	    long time = System.currentTimeMillis() - start; 
	    System.out.println("Time cost is "+ time);
	  }

	}
