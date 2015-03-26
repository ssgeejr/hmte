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

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.logging.*;

public class HeapMemoryTestEngine extends HeapMemoryTestEngineParent implements RunByTimeThreadInterface{
	
	public HeapMemoryTestEngine(String args[]) {
		try {
//			logger.addHandler(fileHandler);
//			logger.addHandler(consoleHandler);
			
			for (int argKey = 0; argKey < args.length; argKey++) {
				if (args[argKey].equals("-chunk")) {
					argKey++;
					FREE_MEMORY_CHUNK = Integer.parseInt(args[argKey]);
				} else if (args[argKey].equals("-delay")) {
					argKey++;
					STARTUP_DELAY = Integer.parseInt(args[argKey]);
				} else if (args[argKey].equals("-sleep")) {
					argKey++;
					SLEEP_TIME = (Integer.parseInt(args[argKey]) * 1000);
				} else if (args[argKey].equals("-noleak")) {
					FORCE_LEAK = false;
				} else if (args[argKey].equals("-loadthread")) {
					argKey++;
					LOAD_THREAD_COUNT = Integer.parseInt(args[argKey]);
				} else if (args[argKey].equals("-loadthreadsleep")) {
					argKey++;
					LOAD_THREAD_SLEEP_TIME = (Integer.parseInt(args[argKey]) * 1000);
				} else if (args[argKey].equals("-noload")) {
					ENABLE_LOAD_TEST = false;
				} else if (args[argKey].equals("-nosort")) {
					ENABLE_SORTING_LOAD = false;
				} else if (args[argKey].equals("-nocrypt")) {
					ENABLE_ENCRYPTION_LOAD = false;
				} else if (args[argKey].equals("-sortsize")) {
					argKey++;
					SORTING_LIST_SIZE = Integer.parseInt(args[argKey]);
				} else if (args[argKey].equals("-sortbytes")) {
					argKey++;
					SORTING_BYTE_SIZE = Integer.parseInt(args[argKey]);
				} else if (args[argKey].equals("-sortsleep")) {
					argKey++;
					SORTING_SLEEP_TIME = (Integer.parseInt(args[argKey]) * 1000);
				} else if (args[argKey].equals("-cryptbytes")) {
					argKey++;
					ENCRYPTION_BYTE_SIZE = Integer.parseInt(args[argKey]);
				} else if (args[argKey].equals("-cryptsleep")) {
					argKey++;
					ENCRYPTION_SLEEP_TIME = (Integer.parseInt(args[argKey]) * 1000);
				} else if (args[argKey].equals("-silent")) {
					RUN_SILENT_RUN_DEEP = true;
				} else if (args[argKey].equals("-runtime")) {
					argKey++;
					RUN_TIME = (Integer.parseInt(args[argKey]) * 1000);
				} else if (args[argKey].equals("-test")) {
					TEST_MODE_ONLY = true;
				} else if (args[argKey].equals("-threads")) {
					argKey++;
					THREAD_COUNT = Integer.parseInt(args[argKey]);
					if (THREAD_COUNT < 1)
						throw new Exception(
								"INVALID THREAD COUNT. Must be greater than 0");
				} else if (args[argKey].equals("-help")) {
					showHelp();
				}
			}

			echoArgsAndVMSettings();
			
			logger.info(argsAndVMSettings.toString());
			
			logger.info("............................................................" + CR_LF);
			
			if(!TEST_MODE_ONLY){
				
				logger.info("Starting Test in "
						+ STARTUP_DELAY
						+ " seconds, use this time to hook into the JVM with your tool of choice (JVisualVM or Jconsole)");
				for (int i = 0; i < STARTUP_DELAY; i++) {
					pause(1000);
					logger.info(">> " + ((STARTUP_DELAY - 1) - i) + " seconds");
				}
				
				//Kick off the Engine
				startMemoryLeakEngine();
			}

		} catch (Exception ex) {
			logger.log(Level.SEVERE, "[System] Unrecoverable Exception", ex);
		}
	}

	private void echoArgsAndVMSettings(){
		argsAndVMSettings.append(CR_LF).append("============================================================").append(CR_LF);
		argsAndVMSettings.append(setLen("STARTUP_DELAY") + " [" + STARTUP_DELAY + "] in seconds").append(CR_LF);
		argsAndVMSettings.append(setLen("SLEEP_TIME") + " [" + (SLEEP_TIME / 1000) + "] in seconds").append(CR_LF);
		argsAndVMSettings.append(setLen("FREE_MEMORY_CHUNK") + " [" + FREE_MEMORY_CHUNK + "] in kbytes").append(CR_LF);
		argsAndVMSettings.append(setLen("FORCE_MEMEORY_LEAK") + " [" + FORCE_LEAK + "]").append(CR_LF);
		argsAndVMSettings.append(setLen("THREAD_COUNT") + " [" + THREAD_COUNT + "]").append(CR_LF);
		argsAndVMSettings.append(setLen("LOAD_THREAD_COUNT") + " [" + LOAD_THREAD_COUNT + "]").append(CR_LF);
		argsAndVMSettings.append(setLen("LOAD_THREAD_SLEEP_TIME") + " [" + (LOAD_THREAD_SLEEP_TIME / 1000) + "] in seconds").append(CR_LF);
		argsAndVMSettings.append(setLen("FORCE_MEMEORY_LEAK ") + " [" + FORCE_LEAK + "]").append(CR_LF);

		argsAndVMSettings.append(setLen("ENABLE_LOAD_TEST") + " [" + ENABLE_LOAD_TEST + "]").append(CR_LF);
		argsAndVMSettings.append(setLen("ENABLE_SORTING_LOAD") + " [" + ENABLE_SORTING_LOAD + "]").append(CR_LF);
		argsAndVMSettings.append(setLen("ENABLE_ENCRYPTION_LOAD") + " [" + ENABLE_ENCRYPTION_LOAD + "]").append(CR_LF);
		argsAndVMSettings.append(setLen("SORTING_LIST_SIZE") + " [" + SORTING_LIST_SIZE + "]").append(CR_LF);
		argsAndVMSettings.append(setLen("SORTING_BYTE_SIZE") + " [" + SORTING_BYTE_SIZE + "]").append(CR_LF);
		argsAndVMSettings.append(setLen("SORTING_SLEEP_TIME") + " [" + (SORTING_SLEEP_TIME / 1000) + "] in seconds").append(CR_LF);
		argsAndVMSettings.append(setLen("ENCRYPTION_BYTE_SIZE") + " [" + ENCRYPTION_BYTE_SIZE + "]").append(CR_LF);
		argsAndVMSettings.append(setLen("ENCRYPTION_SLEEP_TIME") + " [" + (ENCRYPTION_SLEEP_TIME / 1000) + "] in seconds").append(CR_LF);
		
		argsAndVMSettings.append(setLen("ENABLE_RUN_TIME") + " [" + ((RUN_TIME > 0)?true:false) + "]").append(CR_LF);
		argsAndVMSettings.append(setLen("RUN_TIME") + " [" + (RUN_TIME / 1000) + "] in seconds").append(CR_LF);
		
		argsAndVMSettings.append("............................................................").append(CR_LF);

		RuntimeMXBean RuntimemxBean = ManagementFactory.getRuntimeMXBean();
		List<String> argList = RuntimemxBean.getInputArguments();
		argsAndVMSettings.append(CR_LF).append("== VM Arguments ==").append(CR_LF);
		for (int i = 0; i < argList.size(); i++)
			argsAndVMSettings.append(" vm-arg[" + i + "] [" + argList.get(i) + "]").append(CR_LF);
		
		argsAndVMSettings.append("============================================================").append(CR_LF);

	}
	
	
	private void showHelp() {
		System.out.println("java -jar target\\heapMemoryTestEngine-<version>.jar <args>");
		System.out.println("ARGS:");
		System.out.println("\t-chunk <int_value>\tSize of Heap memory to persist, default is 100000");
		System.out.println("\t-sleep <int_value>\tTime in seconds to pause before consuming the next chunk of heap memory, default is 3 seconds");
		System.out.println("\t-delay <int_value>\tTime in seconds to pause before starting the test, default is 10 (this is used to give developers a chance to connect a memory leaking tool to the application)");
		System.out.println("\t-noleak \tDiabled the memeory leak (this is best used if you want to observe normal garbage collection and heap management");
		System.out.println("\t-loadthread  <int_value>\tHow many Threads to initialize to apply CPU Load (Default is 100)");
		System.out.println("\t-loadthreadsleep  <int_value>\tDelay between starting the CPU Load Threads (default is 3 seconds)");
		System.out.println("\t-silent \tDisable the thread output, JVM and Garbage Collection messages will continue to dispay");

		System.out.println("\t-noload \tDisable the CPU load test (default is enabled)");
		System.out.println("\t-nosort \tDisable the CPU sorting threads (default is enabled)");
		System.out.println("\t-nocrypt \tDisable the CPU encryption threads (default is enabled)");
		System.out.println("\t-sortsize  <int_value>\tNumber of entries in the list to sort (default is 2500)");
		System.out.println("\t-sortbytes  <int_value>\tNumber of bytes to sort (default is 500)");
		System.out.println("\t-sortsleep  <int_value>\tTime in seconds to sleep when sorting (default is 2000)");
		System.out.println("\t-cryptbytes  <int_value>\tNumber of bytes to encrypt (default is 500)");
		System.out.println("\t-cryptsleep  <int_value>\tTime in seconds to sleep when sorting (default is 2000)");

		System.out.println("\t-test \tset the system to run in test mode only. The engine will not start, but the variables will be initialized and objects created");
		System.exit(0);
	}

	private void startMemoryLeakEngine() throws Exception{
		MemoryLeakThread leakyThread = null;
		activeThreadCounter = new ActiveThreadCounter(RUN_SILENT_RUN_DEEP, loadTimer, this);
		activeThreadCounter.start();
		loadTimer.setStartTime(System.currentTimeMillis());
		
		if(RUN_TIME > 0){
			timerThread = new RunByTimeThread(RUN_TIME,this);
			timerThread.start();
		}
		
		Thread memoryConsumerThread = null;
		
		for (int threadCounter = 0; threadCounter < THREAD_COUNT; threadCounter++) {
			postLog("[System] Initialzing Thread ID [" + threadCounter + "]");
			
			leakyThread = new MemoryLeakThread(FREE_MEMORY_CHUNK, SLEEP_TIME, FORCE_LEAK, threadCounter, RUN_SILENT_RUN_DEEP, activeThreadCounter);
			activeThreadCounter.addNewThread(threadCounter, leakyThread);
			
			postLog("[System] Starting Thread ID [" + threadCounter + "]");
			leakyThread.start();
			postLog("[System] Thread ID [" + threadCounter + "] has been registered with the thread controller");
			leakThreadCount++;
		}
		
		if(ENABLE_LOAD_TEST){
			for (cryptCounter = 0; cryptCounter < LOAD_THREAD_COUNT; cryptCounter++) {
				//it is possible that cryptCounter != (sortThreadCount + encryptedThreadCount)
				//this is due to exactly WHEN the application crashes
				
				if(ENABLE_SORTING_LOAD){
					memoryConsumerThread = new LoadGeneratorSortingFlavoredThread(cryptCounter, SORTING_LIST_SIZE, SORTING_BYTE_SIZE, SORTING_SLEEP_TIME, RUN_SILENT_RUN_DEEP, loadTimer);
					memoryConsumerThread.start();
					sortThreadCount++;
				}
				
				if(ENABLE_ENCRYPTION_LOAD && cryptCounter < LOAD_THREAD_COUNT){
					memoryConsumerThread = new LoadGeneratorEncryptionFlavoredThread(cryptCounter, ENCRYPTION_BYTE_SIZE, ENCRYPTION_SLEEP_TIME, RUN_SILENT_RUN_DEEP, loadTimer);
					memoryConsumerThread.start();
					encryptedThreadCount++;
				}
				
				if(ENABLE_SORTING_LOAD && ENABLE_ENCRYPTION_LOAD) cryptCounter++;
				Thread.sleep(LOAD_THREAD_SLEEP_TIME);
			}
		}
	}
	
	private String setLen(String in){
		inBuffer.setLength(0);
		inBuffer.append(in);
		while(inBuffer.length() < 25){
			inBuffer.append(" ");
		}
		return inBuffer.toString();
	}

	private void postLog(String msg){
		if(!RUN_SILENT_RUN_DEEP)
			logger.info("[System] " + msg);
	}
	
	public void euthanize(){
		logger.info(argsAndVMSettings.toString());
		logger.info(activeThreadCounter.fetchResults());
		loadTimer.printResults();
		StringBuffer finalizedCount = new StringBuffer();
		finalizedCount.append(CR_LF).append("=============================================").append(CR_LF);
		finalizedCount.append("Total Leaking Thread Created: " + leakThreadCount).append(CR_LF);
		finalizedCount.append("Total Load Threads Created: " + (cryptCounter + 1)).append(CR_LF);
		finalizedCount.append("Total Load Sorting Threads Created: " + sortThreadCount).append(CR_LF);
		finalizedCount.append("Total Load Encryption Threads Created: " + encryptedThreadCount).append(CR_LF);
		finalizedCount.append("=============================================").append(CR_LF);
		finalizedCount.append("Total Leaking Threads Collected: " + GarbageCollectionCountManager.getMemoryLeakThreadCount()).append(CR_LF);
		finalizedCount.append("Total Sorting Threads Collected: " + GarbageCollectionCountManager.getSortingFlavoredThreadCount()).append(CR_LF);
		finalizedCount.append("Total Encryption Threads Collected: " + GarbageCollectionCountManager.getEncryptionFlavoredThreadCount()).append(CR_LF);
		finalizedCount.append("=============================================").append(CR_LF);
		logger.info(finalizedCount.toString());
		System.exit(0);
	}
	
	private void pause(long sleepyTime) {
		try {Thread.sleep(sleepyTime);} catch (Exception ex) {}
	}
	
	public static void main(String args[]) {
		System.out.println("[System] HeapMemoryTestEngine initializing ...");
		new HeapMemoryTestEngine(args);
	}
}
