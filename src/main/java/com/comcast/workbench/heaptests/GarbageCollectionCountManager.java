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

public class GarbageCollectionCountManager {
	
	private static long memoryLeakThread = 0;
	private static long sortingFlavoredThread = 0;
	private static long encryptionFlavoredThread = 0;
	
	public static synchronized void finalizeMemoryLeakThread(){memoryLeakThread++;}
	public static synchronized void finalizeSortingFlavoredThread(){sortingFlavoredThread++;}
	public static synchronized void finalizeEncryptionFlavoredThread(){encryptionFlavoredThread++;}
	
	public static synchronized long getMemoryLeakThreadCount(){return memoryLeakThread;}
	public static synchronized long getSortingFlavoredThreadCount(){return sortingFlavoredThread;}
	public static synchronized long getEncryptionFlavoredThreadCount(){return encryptionFlavoredThread;}
	
}
