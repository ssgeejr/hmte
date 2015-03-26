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

import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.*;

public class ActiveThreadCounter extends Thread implements ThreadOOMEvent{
	private Logger logger = Logger.getLogger("HeapMemoryTestEngine");
	private final String CR_LF = System.getProperties().getProperty("line.separator");
	private HashMap<String,Thread> threadMap = new HashMap<String,Thread>();
	private CPULoadTimerIFace loadTimer = null;
	private RunByTimeThreadInterface parentRefernce = null;
	private StringBuffer buffer = new StringBuffer();
	private Iterator<String> iter = null;
	private boolean initalized = false;
	private boolean RUN_SILENT_RUN_DEEP = false;
	private int MAX_THREAD_COUNT = 0;
	
	public ActiveThreadCounter(boolean runSilent, CPULoadTimerIFace ltimer, RunByTimeThreadInterface parentIF){
		RUN_SILENT_RUN_DEEP = runSilent;
		loadTimer = ltimer;
		parentRefernce = parentIF;
	}
	
	public void addNewThread(int key, Thread thread){
		MAX_THREAD_COUNT++;
		postLog("[System] Registered [ Thread ID " + key + "] with the Active Thread Monitor");
		threadMap.put(key + "", thread);
		initalized = true;
	}
	
	public void removeThreadByID(int threadID){
		threadMap.remove(threadID + "");
	}
	
	public void run(){
		while(true){
			postLog(fetchResults());
			
			if(initalized && threadMap.size() == 0){
				parentRefernce.euthanize();
			}// if
			pause(1000);
		}//while
	}//run

	
	public String fetchResults(){
		buffer.setLength(0);
		buffer.append(CR_LF).append("===========================================").append(CR_LF);
		buffer.append("Max Thread Count: " + MAX_THREAD_COUNT).append(CR_LF);
		
		buffer.append("Active Thread count: " + threadMap.size()).append(CR_LF);
		buffer.append("Active Thread List").append(CR_LF);
		for(iter = threadMap.keySet().iterator();iter.hasNext();){
			buffer.append("> Name [" + threadMap.get(iter.next()).getName() + "]").append(CR_LF);
		}
		
		buffer.append("===========================================").append(CR_LF);
		
		return buffer.toString();
	}
	
	private void pause(long sleepyTime){
		  try{
			  sleep(sleepyTime);
		  }catch(Exception ex){}
	}
	
	private void postLog(String msg){
		if(!RUN_SILENT_RUN_DEEP)
			logger.info("[ActiveThreadCounter] " + msg);
	}

	
}
