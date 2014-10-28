package mutex;

//import mutex.FilterTest.MyThread;


public class TreelockTest  {
  private final static int THREADS = 64;
  private final static int COUNT = 64*500;
  private final static int PER_THREAD = COUNT / THREADS;
  Thread[] thread = new Thread[THREADS];
  volatile int counter = 0;
  
  Treelock instance = new Treelock(THREADS);
  java.util.concurrent.locks.Lock lock = new java.util.concurrent.locks.ReentrantLock();   
  
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
	      for (int i = 0; i < PER_THREAD; i++) {
		  instance.lock();
	        try {
	          counter = counter + 1;
	        } finally {
		    instance.unlock();
	        }
	      }
	      //System.out.println("ThreadID: "+ThreadID.get());
	    }
	  }
  

  public static void main(String[] args) {
	  long start = System.currentTimeMillis();
    TreelockTest mft = new TreelockTest();
    try {
      mft.testParallel();
    }
    catch (Exception e) {}   
    long time = System.currentTimeMillis() - start; 
    System.out.println(time);
  }

}