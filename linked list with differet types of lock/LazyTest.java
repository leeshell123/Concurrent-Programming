package assgn2;




public class LazyTest  {
	  private final static int THREADS = 64;
	  private final static int COUNT = 64*500;
	  Thread[] thread = new Thread[THREADS];
	  volatile int counter = 64*500;
	  
	  LazyList LazyInstance = new LazyList(20000);
	  
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
	    		LazyInstance.add((int)(Math.random()*LazyInstance.originallistlen*2));	
	    		LazyInstance.add((int)(Math.random()*LazyInstance.originallistlen*2));
	    		LazyInstance.contains((int)(Math.random()*LazyInstance.originallistlen*2));
	    		LazyInstance.add((int)(Math.random()*LazyInstance.originallistlen*2));
	    		LazyInstance.add((int)(Math.random()*LazyInstance.originallistlen*2));
	    		
	    		LazyInstance.remove((int)(Math.random()*LazyInstance.originallistlen*2));
	    		LazyInstance.contains((int)(Math.random()*LazyInstance.originallistlen*2));
	    		LazyInstance.remove((int)(Math.random()*LazyInstance.originallistlen*2));
	    		LazyInstance.remove((int)(Math.random()*LazyInstance.originallistlen*2));
	    		LazyInstance.remove((int)(Math.random()*LazyInstance.originallistlen*2));
	    		
	    	}
	    	
	    	
	    	
	    }
	  }

	  public static void main(String[] args) {
		  
		LazyTest mft = new LazyTest();
		long start = System.currentTimeMillis();
	    try {
	      mft.testParallel();
	    }
	    catch (Exception e) {}   
	    long time = System.currentTimeMillis() - start; 
	    System.out.println("Time cost is "+ time);
	  }

	}

