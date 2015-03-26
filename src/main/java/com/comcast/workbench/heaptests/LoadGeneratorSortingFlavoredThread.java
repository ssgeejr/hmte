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

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;
import java.util.logging.*;

public class LoadGeneratorSortingFlavoredThread extends Thread{
	private Logger logger = Logger.getLogger("HeapMemoryTestEngine");
	private CPULoadTimerIFace timerInterface = null;
    private int THREAD_NUMBER = 0;
	private SecureRandom random = new SecureRandom();
	private List<String> sortableList = new ArrayList<String>();
	private long startTime = 0l;
	private boolean RUN_SILENT_RUN_DEEP = false;
	private int SORTING_LIST_SIZE = 2500;
	private int SORTING_BYTE_SIZE = 500;
	private int SORTING_SLEEP_TIME = 2000;

	public LoadGeneratorSortingFlavoredThread(){}
	
	public LoadGeneratorSortingFlavoredThread(int thisVer, int sortListSize, int byteSize, int sleepTime, boolean runSilent, CPULoadTimerIFace timerIFace){
		super.setName("SortingFlavoredThread-" + thisVer);
		THREAD_NUMBER = thisVer;
		SORTING_LIST_SIZE = sortListSize;
		SORTING_BYTE_SIZE = byteSize;
		SORTING_SLEEP_TIME = sleepTime;
		RUN_SILENT_RUN_DEEP = runSilent;
		timerInterface = timerIFace;
		postLog("Sorting Thread Initialzing ...");		
	}
	
	public void run(){
		
		while(true){
			sortableList.clear();
			for(int iterCount = 0;iterCount < SORTING_LIST_SIZE;iterCount++){
				sortableList.add(new BigInteger(SORTING_BYTE_SIZE, random).toString(32));
			}
//			long startTime = System.nanoTime();
			startTime = System.currentTimeMillis();
			Collections.sort(sortableList);	
			timerInterface.incrementSortCounter();
//			long estimatedTime = System.nanoTime() - startTime;
			
			postLog("Estimated Sort Time [" + (System.currentTimeMillis() - startTime) + "]");
			pause(SORTING_SLEEP_TIME);
		}
	}

	private void pause(long sleepyTime){
		  try{
			  sleep(sleepyTime);
		  }catch(Exception ex){}
	}

/*
	public static void main(String args[]){
//		String uuid = UUID.randomUUID().toString();
//		System.out.println("uuid = " + uuid);
//		SecureRandom Xrandom = new SecureRandom();
//		for(int i = 0;i < 20;i++){
//		System.out.println(">> " + new BigInteger(500, Xrandom).toString(32));
//		}
		try{
		LoadGeneratorSortingFlavoredThread lgsf = new LoadGeneratorSortingFlavoredThread();
		lgsf.run();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
*/
	
	private void postLog(String msg){
		if(!RUN_SILENT_RUN_DEEP)
			logger.info("[Sorting Thread [" + THREAD_NUMBER + "]] " + msg);
	}
	
	protected void finalize( ){
		GarbageCollectionCountManager.finalizeSortingFlavoredThread();
	}

}
