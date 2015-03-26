package com.comcast.workbench.heaptests;
/*************************************************************************
Copyright (C) 2010  Steve S Gee Jr (ioexcept@yahoo.com)
 
https://sourceforge.net/projects/hmte/
 
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.     

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.     

You should have received a copy of the GNU General Public License
along with this program.  If not, see http://www.gnu.org/licenses/
*************************************************************************/

import java.util.logging.*;

public class MemoryLeakThread extends Thread {
	private Logger logger = Logger.getLogger("HeapMemoryTestEngine");
	private int FREE_MEMORY_CHUNK = 100000;
	private int SLEEP_TIME = 3000;
	private boolean FORCE_LEAK = true;
	private int THREAD_NUMBER = 0;
	private ThreadOOMEvent threadOOMEvent = null;
	private boolean RUN_SILENT_RUN_DEEP = false;

	public MemoryLeakThread(int freeMemChunk, int sleepTime, boolean forceLeak, int threadNumber, boolean runSilent, ThreadOOMEvent _threadOOMEvent){
		super.setName("MemoryLeakThread-" + threadNumber);
		FREE_MEMORY_CHUNK = freeMemChunk;
		SLEEP_TIME = sleepTime;
		FORCE_LEAK = forceLeak;
		THREAD_NUMBER = threadNumber;
		RUN_SILENT_RUN_DEEP = runSilent;
		threadOOMEvent = _threadOOMEvent;
	}
	
	public void run(){
		try{
			postLog("Thread ID [" + THREAD_NUMBER + "] is starting ... ");
		Runtime rt = Runtime.getRuntime();
	    java.util.Vector<byte[]> leakingObject = new java.util.Vector<byte[]>();
	    long freeMemory = 0;
	    while (true) {
	      freeMemory = rt.freeMemory();
	      postLog("Thread ID [" + THREAD_NUMBER + "] Total memory = " + rt.totalMemory() + ", free memory = " + freeMemory);
	      byte[] buffer = new byte[FREE_MEMORY_CHUNK];
	      if(FORCE_LEAK){
	    	  leakingObject.addElement(buffer);
	    	  postLog("Thread ID [" + THREAD_NUMBER + "] sleeping " + (SLEEP_TIME / 1000) + " seconds ... keep watching for the memory leak");
	      }else{
	    	  postLog("Thread ID [" + THREAD_NUMBER + "] sleeping " + (SLEEP_TIME / 1000) + " seconds ... memory leak disabled, watch GC and Heap");
	      }
	      pause(SLEEP_TIME);
	    }
		}catch(OutOfMemoryError oome){
			logger.info("*** Thread ID [" + THREAD_NUMBER + "] has ran out of memory ***");
			threadOOMEvent.removeThreadByID(THREAD_NUMBER);
		}
	}
	
	private void pause(long sleepyTime){
		  try{
			  sleep(sleepyTime);
		  }catch(Exception ex){}
	}
	
	private void postLog(String msg){
		if(!RUN_SILENT_RUN_DEEP)
			logger.info("[Encryption Thread [" + THREAD_NUMBER + "]] " + msg);
	}
	
	protected void finalize( ){
		GarbageCollectionCountManager.finalizeMemoryLeakThread();
	}
	
}
