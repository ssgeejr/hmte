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
import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.logging.*;

public class LoadGeneratorEncryptionFlavoredThread extends Thread{
	private Logger logger = Logger.getLogger("HeapMemoryTestEngine");
	private CPULoadTimerIFace timerInterface = null;
    private int THREAD_NUMBER = 0;
	private int ENCRYPTION_BYTE_SIZE = 1500;
	private int ENCRYPTION_SLEEP_TIME = 2000;
	private boolean RUN_SILENT_RUN_DEEP = false;
	private SecureRandom random = new SecureRandom();
	
	public LoadGeneratorEncryptionFlavoredThread(int thisVer, int byteSize, int sleepTime, boolean runSilent, CPULoadTimerIFace timerIFace){
		super.setName("EncryptionFlavoredThread-" + thisVer);
		THREAD_NUMBER = thisVer;
		ENCRYPTION_BYTE_SIZE = byteSize;
		ENCRYPTION_SLEEP_TIME = sleepTime;
		RUN_SILENT_RUN_DEEP = runSilent;
		timerInterface = timerIFace;
		postLog("Encryption Flavored Load Generator Initialzing ...");
	}
	
	public void run(){
		while(true){
			encryptString(new BigInteger(ENCRYPTION_BYTE_SIZE, random).toString(32));
			timerInterface.incrementCryptCounter();
			pause(ENCRYPTION_SLEEP_TIME);
		}
	}
	
     public String asHex (byte buf[]) {
      StringBuffer strbuf = new StringBuffer(buf.length * 2);
      int bufferCounter;
      for (bufferCounter = 0; bufferCounter < buf.length; bufferCounter++) {
       if (((int) buf[bufferCounter] & 0xff) < 0x10) strbuf.append("0");
       strbuf.append(Long.toString((int) buf[bufferCounter] & 0xff, 16));
      }
      return strbuf.toString();
     }

     /**
      * Used to put a heavy load on the CPU
      * @param valueToEncrypt
      * @throws Exception
      */
     public void encryptString(String valueToEncrypt){
    	 try{
       KeyGenerator kgen = KeyGenerator.getInstance("AES");
       kgen.init(128); // 192 and 256 bits may not be available
       SecretKey skey = kgen.generateKey();
       byte[] raw = skey.getEncoded();
       SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
       Cipher cipher = Cipher.getInstance("AES");
       cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
//       byte[] encrypted = cipher.doFinal(valueToEncrypt.getBytes());
       cipher.doFinal(valueToEncrypt.getBytes());
//       logger.info("[Encryption Thread [" + THREAD_NUMBER + "]] Encrypted string [" + asHex(encrypted) + "]");

       /*  Used for decryption
       cipher.init(Cipher.DECRYPT_MODE, skeySpec);
       byte[] original =
         cipher.doFinal(encrypted);
       String originalString = new String(original);
       System.out.println("Original string: " + originalString + " " + asHex(original));
         */
    	 }catch(Exception ex){
    		 logger.log(Level.SEVERE, "[Encryption Thread [" + THREAD_NUMBER + "]] Encryption Engine Has Crashed",ex);
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
		GarbageCollectionCountManager.finalizeEncryptionFlavoredThread();
	}
    
}
