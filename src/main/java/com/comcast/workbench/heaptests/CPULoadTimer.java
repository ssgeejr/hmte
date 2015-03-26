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

public class CPULoadTimer implements CPULoadTimerIFace{
	private Logger logger = Logger.getLogger("HeapMemoryTestEngine");
	private long startTime = 0l;
	
	private long cryptoCounter = 0l;
	private long sortCounter = 0l;
	
	public CPULoadTimer(){ }
	
	public void setStartTime(long stime){
		startTime = stime;
	}
	
	public synchronized void incrementCryptCounter(){cryptoCounter++;}
	public synchronized void incrementSortCounter(){sortCounter++;}

	public void printResults(){
		StringBuffer buffer = new StringBuffer();
		buffer.append(CR_LF).append("=====================================================================").append(CR_LF);
		buffer.append("[CPULoadTimer] Total Test Runtime [" + (System.currentTimeMillis() - startTime) + "] in milliseconds").append(CR_LF);
		buffer.append("[CPULoadTimer] Total # of Crypto Iterations [" + cryptoCounter + "]").append(CR_LF);
		buffer.append("[CPULoadTimer] Total # of Sorting Iterations [" + sortCounter + "]").append(CR_LF);
		buffer.append("=====================================================================").append(CR_LF);
		logger.info(buffer.toString());
	}
	
	
}
