package assgn2;




public class LazyModifiedTest  {
	  private final static int THREADS = 64;
	  private final static int COUNT = 64*500;
	  Thread[] thread = new Thread[THREADS];
	  volatile int counter = 64*500;
	  
	  LazyModifiedList LazyModifiedInstance = new LazyModifiedList(100);
	  
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
	    		LazyModifiedInstance.add((int)(Math.random()*LazyModifiedInstance.originallistlen*2));	
	    		LazyModifiedInstance.add((int)(Math.random()*LazyModifiedInstance.originallistlen*2));	
	    		LazyModifiedInstance.add((int)(Math.random()*LazyModifiedInstance.originallistlen*2));	
	    		LazyModifiedInstance.add((int)(Math.random()*LazyModifiedInstance.originallistlen*2));	
	    		LazyModifiedInstance.contains((int)(Math.random()*LazyModifiedInstance.originallistlen*2));
	    		
	    		LazyModifiedInstance.remove((int)(Math.random()*LazyModifiedInstance.originallistlen*2));
	    		LazyModifiedInstance.remove((int)(Math.random()*LazyModifiedInstance.originallistlen*2));
	    		LazyModifiedInstance.contains((int)(Math.random()*LazyModifiedInstance.originallistlen*2));
	    		LazyModifiedInstance.remove((int)(Math.random()*LazyModifiedInstance.originallistlen*2));
	    		LazyModifiedInstance.remove((int)(Math.random()*LazyModifiedInstance.originallistlen*2));
	    	
	    	}
	    	
	    	
	    	
	    }
	  }

	  public static void main(String[] args) {
		  
		
		  
		LazyModifiedTest mft = new LazyModifiedTest();
		long start = System.currentTimeMillis();
	    try {
	      mft.testParallel();
	    }
	    catch (Exception e) {}   
	    long time = System.currentTimeMillis() - start; 
	    System.out.println("Time cost is "+ time);
	  }

	}

