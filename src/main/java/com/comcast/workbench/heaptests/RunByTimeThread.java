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

public class RunByTimeThread extends Thread {
	private Logger logger = Logger.getLogger("HeapMemoryTestEngine");
	private long SLEEP_TIME = 0l;
	private RunByTimeThreadInterface runByInterface = null;
	
	public RunByTimeThread(long sleepTime, RunByTimeThreadInterface _runByInterface){
		SLEEP_TIME = sleepTime;
		runByInterface = _runByInterface;
	}
	
	public void run(){
		try{
			sleep(SLEEP_TIME);
			runByInterface.euthanize();
		}catch(Exception ex){
			logger.log(Level.SEVERE, "[RunByTimeThread] Experienced a Fatal Error", ex);
		}
		
	}
	
}
