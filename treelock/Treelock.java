package mutex;
//Using a heap to represent the binary tree structure
//the heap index starts from 1, and array index starts from 0, so the heapindex=arrayindex+1


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
//import mutex.FilterTest.MyThread;


public class Treelock implements Lock
{
	final private int n; //thread #
	private int petersonNo;   //peterson lock # needed
	final private AtomicBoolean[] flag ;   //flag for each thread and each peterson lock
    final private AtomicInteger[] victim ;   //victim for each peterson lock
    private int thread_instances = 0;
    //public path[] AllPath;       //record the path a thread go through to get the root lock
	
	final private ThreadLocal<Integer> THREAD_ID = new ThreadLocal<Integer>(){
        final private AtomicInteger id = new AtomicInteger(0);

        protected Integer initialValue(){
            return id.getAndIncrement();
        }
    };
    
    public Treelock (int n)
    {
    	this.n = n;
    	this.petersonNo= n-1;
    	flag = new AtomicBoolean[n+petersonNo];  //flag for each thread and each peterson lock
        victim = new AtomicInteger[petersonNo];  //victim for each peterson lock
        for (int i = 0; i < (n+petersonNo); i++) {    //initialize flags
            flag[i] = new AtomicBoolean(false);

        }
        
        for (int i = 0; i < petersonNo; i++) {   //initialize victims
            victim[i] = new AtomicInteger(-1);
        }
        int temp=n;
        while(temp != 1)
        {
            temp /=2;
            thread_instances++;
        }
        



    	
    }
    
    public int getsibling(int heapindex)
    {
    	if (heapindex%2==0)
    		return heapindex+1;
    	else
    		return heapindex-1;
    }

	@Override
	public void lock() 
	{
		int me = THREAD_ID.get() % n;
		int heapindex = me+petersonNo+1;
		while(heapindex>1)
		{
			flag[heapindex-1].set(true);   //set my flag
			victim[heapindex/2-1].set(me);   //set me as the current lock victim
			while((flag[getsibling(heapindex)-1].get())&&(victim[heapindex/2-1].get()==me)){};  //wait to get the current lock
			heapindex = heapindex/2; //try the next lock

		}
		
	}
	
	@Override
	public void unlock() 
	{
		int me = THREAD_ID.get() % n;
		int[] path=new int[thread_instances];		
		int heapindex = me+petersonNo+1;
		int i=0;
		while(heapindex>1)
		{
			//flag[heapindex-1].set(false);   //set my flag
			path[i]=heapindex;
			heapindex/=2;
			i++;
		}
		
		for(int j=(path.length-1); j>=0; j--)
		{
			flag[path[j]-1].set(false); 
		}
		

		
	}




    public Condition newCondition() {
	throw new java.lang.UnsupportedOperationException();
    }
    public boolean tryLock(long time,
			   TimeUnit unit)
	throws InterruptedException {
	throw new java.lang.UnsupportedOperationException();
    }
    public boolean tryLock() {
	throw new java.lang.UnsupportedOperationException();
    }
    public void lockInterruptibly() throws InterruptedException {
	throw new java.lang.UnsupportedOperationException();
    }

}



