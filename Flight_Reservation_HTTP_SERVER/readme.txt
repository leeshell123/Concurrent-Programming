Although PYTHON supports multithreads, these threads are still running on only 

one processor, so GIL makes PYTHON don't have real concurrency. Also, PYTHON

don't have primitives like CompareAndSet(), so we must use locks to synchronize 

data in PYTHON.

 JAVA's multithreads mechanism makes threads running on multi processors so 

multithreads in JAVA could bring considerable performance improvement. Also, 

JAVA have primitives to help synchronize, we could have lock-free program in 

JAVA using these primitives and atomic types. 

 So, if one really cares about the performance under concurrent environment, 

one should use languages like JAVA instead of PYTHON.