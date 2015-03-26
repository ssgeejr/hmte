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

import java.util.logging.Logger;

public class HeapMemoryTestEngineParent {
	protected Logger logger = Logger.getLogger("HeapMemoryTestEngine");
//	FileHandler fileHandler = new FileHandler("my.log");
//	ConsoleHandler consoleHandler = new ConsoleHandler();
	protected final String CR_LF = System.getProperties().getProperty("line.separator");
	protected StringBuffer argsAndVMSettings = new StringBuffer();
	protected ActiveThreadCounter activeThreadCounter = null;
	protected CPULoadTimer loadTimer = new CPULoadTimer();
	protected RunByTimeThread timerThread = null;
	protected StringBuffer inBuffer = new StringBuffer();
	//------------------------------------------------------
	protected int FREE_MEMORY_CHUNK = 100000;
	protected int STARTUP_DELAY = 10;
	protected int SLEEP_TIME = 3000;
	protected boolean FORCE_LEAK = true;
	protected int THREAD_COUNT = 3;
	protected int LOAD_THREAD_COUNT = 100;
	protected int LOAD_THREAD_SLEEP_TIME = 3000;
	protected boolean RUN_SILENT_RUN_DEEP = false;
	protected boolean ENABLE_LOAD_TEST = true;
	protected boolean ENABLE_SORTING_LOAD = true;
	protected boolean ENABLE_ENCRYPTION_LOAD = true;
	protected int SORTING_LIST_SIZE = 2500;
	protected int SORTING_BYTE_SIZE = 500;
	protected int SORTING_SLEEP_TIME = 2000;
	protected int ENCRYPTION_BYTE_SIZE = 1500;
	protected int ENCRYPTION_SLEEP_TIME = 1500;
	protected int RUN_TIME = -1;
	protected boolean TEST_MODE_ONLY = false;
	//------------------------------------------------------
	protected int cryptCounter = 0;
	protected int encryptedThreadCount = 0;
	protected int sortThreadCount = 0;
	protected int leakThreadCount = 0;
	//------------------------------------------------------
	
	
	public int getCryptCounter() {
		return cryptCounter;
	}

	public int getEncryptedThreadCount() {
		return encryptedThreadCount;
	}

	public void setEncryptedThreadCount(int encryptedThreadCount) {
		this.encryptedThreadCount = encryptedThreadCount;
	}

	public int getSortThreadCount() {
		return sortThreadCount;
	}

	public void setSortThreadCount(int sortThreadCount) {
		this.sortThreadCount = sortThreadCount;
	}

	public void setCryptCounter(int cryptCounter) {
		this.cryptCounter = cryptCounter;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public StringBuffer getArgsAndVMSettings() {
		return argsAndVMSettings;
	}

	public void setArgsAndVMSettings(StringBuffer argsAndVMSettings) {
		this.argsAndVMSettings = argsAndVMSettings;
	}

	public ActiveThreadCounter getActiveThreadCounter() {
		return activeThreadCounter;
	}

	public void setActiveThreadCounter(ActiveThreadCounter activeThreadCounter) {
		this.activeThreadCounter = activeThreadCounter;
	}

	public CPULoadTimer getLoadTimer() {
		return loadTimer;
	}

	public void setLoadTimer(CPULoadTimer loadTimer) {
		this.loadTimer = loadTimer;
	}

	public RunByTimeThread getTimerThread() {
		return timerThread;
	}

	public void setTimerThread(RunByTimeThread timerThread) {
		this.timerThread = timerThread;
	}

	public StringBuffer getInBuffer() {
		return inBuffer;
	}

	public void setInBuffer(StringBuffer inBuffer) {
		this.inBuffer = inBuffer;
	}

	public int getFREE_MEMORY_CHUNK() {
		return FREE_MEMORY_CHUNK;
	}

	public void setFREE_MEMORY_CHUNK(int fREE_MEMORY_CHUNK) {
		FREE_MEMORY_CHUNK = fREE_MEMORY_CHUNK;
	}

	public int getSTARTUP_DELAY() {
		return STARTUP_DELAY;
	}

	public void setSTARTUP_DELAY(int sTARTUP_DELAY) {
		STARTUP_DELAY = sTARTUP_DELAY;
	}

	public int getSLEEP_TIME() {
		return SLEEP_TIME;
	}

	public void setSLEEP_TIME(int sLEEP_TIME) {
		SLEEP_TIME = sLEEP_TIME;
	}

	public boolean isFORCE_LEAK() {
		return FORCE_LEAK;
	}

	public void setFORCE_LEAK(boolean fORCE_LEAK) {
		FORCE_LEAK = fORCE_LEAK;
	}

	public int getTHREAD_COUNT() {
		return THREAD_COUNT;
	}

	public void setTHREAD_COUNT(int tHREAD_COUNT) {
		THREAD_COUNT = tHREAD_COUNT;
	}

	public int getLOAD_THREAD_COUNT() {
		return LOAD_THREAD_COUNT;
	}

	public void setLOAD_THREAD_COUNT(int lOAD_THREAD_COUNT) {
		LOAD_THREAD_COUNT = lOAD_THREAD_COUNT;
	}

	public int getLOAD_THREAD_SLEEP_TIME() {
		return LOAD_THREAD_SLEEP_TIME;
	}

	public void setLOAD_THREAD_SLEEP_TIME(int lOAD_THREAD_SLEEP_TIME) {
		LOAD_THREAD_SLEEP_TIME = lOAD_THREAD_SLEEP_TIME;
	}

	public boolean isRUN_SILENT_RUN_DEEP() {
		return RUN_SILENT_RUN_DEEP;
	}

	public void setRUN_SILENT_RUN_DEEP(boolean rUN_SILENT_RUN_DEEP) {
		RUN_SILENT_RUN_DEEP = rUN_SILENT_RUN_DEEP;
	}

	public boolean isENABLE_LOAD_TEST() {
		return ENABLE_LOAD_TEST;
	}

	public void setENABLE_LOAD_TEST(boolean eNABLE_LOAD_TEST) {
		ENABLE_LOAD_TEST = eNABLE_LOAD_TEST;
	}

	public boolean isENABLE_SORTING_LOAD() {
		return ENABLE_SORTING_LOAD;
	}

	public void setENABLE_SORTING_LOAD(boolean eNABLE_SORTING_LOAD) {
		ENABLE_SORTING_LOAD = eNABLE_SORTING_LOAD;
	}

	public boolean isENABLE_ENCRYPTION_LOAD() {
		return ENABLE_ENCRYPTION_LOAD;
	}

	public void setENABLE_ENCRYPTION_LOAD(boolean eNABLE_ENCRYPTION_LOAD) {
		ENABLE_ENCRYPTION_LOAD = eNABLE_ENCRYPTION_LOAD;
	}

	public int getSORTING_LIST_SIZE() {
		return SORTING_LIST_SIZE;
	}

	public void setSORTING_LIST_SIZE(int sORTING_LIST_SIZE) {
		SORTING_LIST_SIZE = sORTING_LIST_SIZE;
	}

	public int getSORTING_BYTE_SIZE() {
		return SORTING_BYTE_SIZE;
	}

	public void setSORTING_BYTE_SIZE(int sORTING_BYTE_SIZE) {
		SORTING_BYTE_SIZE = sORTING_BYTE_SIZE;
	}

	public int getSORTING_SLEEP_TIME() {
		return SORTING_SLEEP_TIME;
	}

	public void setSORTING_SLEEP_TIME(int sORTING_SLEEP_TIME) {
		SORTING_SLEEP_TIME = sORTING_SLEEP_TIME;
	}

	public int getENCRYPTION_BYTE_SIZE() {
		return ENCRYPTION_BYTE_SIZE;
	}

	public void setENCRYPTION_BYTE_SIZE(int eNCRYPTION_BYTE_SIZE) {
		ENCRYPTION_BYTE_SIZE = eNCRYPTION_BYTE_SIZE;
	}

	public int getENCRYPTION_SLEEP_TIME() {
		return ENCRYPTION_SLEEP_TIME;
	}

	public void setENCRYPTION_SLEEP_TIME(int eNCRYPTION_SLEEP_TIME) {
		ENCRYPTION_SLEEP_TIME = eNCRYPTION_SLEEP_TIME;
	}

	public int getRUN_TIME() {
		return RUN_TIME;
	}

	public void setRUN_TIME(int rUN_TIME) {
		RUN_TIME = rUN_TIME;
	}

	public boolean isTEST_MODE_ONLY() {
		return TEST_MODE_ONLY;
	}

	public void setTEST_MODE_ONLY(boolean tEST_MODE_ONLY) {
		TEST_MODE_ONLY = tEST_MODE_ONLY;
	}

	public String getCR_LF() {
		return CR_LF;
	}

}
