# Gulosh

Stand alone Java application used simulate a memory leak in order to generate profile signatures. The application has the ability to generate system (CPU) load in order to observe profile signature changes.

Before starting, please be warned! This application can hard-crash big systems if you set your Heap Max [-Xmx12m] too high.
I have brought down test server by setting it to 3 gigs, please be careful.

First off we need to Compile the source using the maven pom file
from the root directory, execute:
mvn clean package

The project has no external dependencies for compiling or executing

Once the package has completed, run the application by executing
java -jar target\heapMemoryTestEngine-<version>.jar [ARG_LIST]

ARG_LIST
ARGS:
 -chunk <int_value> Size of Heap memory to persist, default is 100000
 -sleep <int_value> Time in seconds to pause before consuming the next chunk of heap memory, default is 3 seconds
 -delay <int_value> Time in seconds to pause before starting the test, default is 10 (this is used to give developers a chance to connect a memory leaking tool to the application)
 -noleak Diabled the memory leak (this is best used if you want to observe normal garbage collection and heap management
 -loadthread <int_value> How many Threads to initialize to apply CPU Load (Default is 100)
 -loadthreadsleep <int_value> Delay between starting the CPU Load Threads (default is 3 seconds)
 -silent Disable the thread output, JVM and Garbage Collection messages will continue to dispay
 -noload Disable the CPU load test (default is enabled)
 -nosort Disable the CPU sorting threads (default is enabled)
 -nocrypt Disable the CPU encryption threads (default is enabled)
 -sortsize <int_value> Number of entries in the list to sort (default is 2500)
 -sortbytes <int_value> Number of bytes to sort (default is 500)
 -sortsleep <int_value> Time in seconds to sleep when sorting (default is 2000)
 -cryptbytes <int_value> Number of bytes to encrypt (default is 500)
 -cryptsleep <int_value> Time in seconds to sleep when sorting (default is 2000)
 -test set the system to run in test mode only. The engine will not start, but the variables will be initialized and objects created

[EXAMPLE]
java -Xms8m -Xmx12m -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails -jar target\heapMemoryTestEngine-2.7.jar -chunk 250000 -sleep 1 -threads 10 -delay 5 -loadthread 100

In this example, the JVM settinigs are set to:
[-Xms8m] set the initial Heap Memory space to 8 megs 
[-Xmx12m] set the max Heap space to 12 megs
[-XX:+HeapDumpOnOutOfMemoryError] We tell the JVM to dump out a binary heap map when it crashes
[-XX:+PrintGCDetails] Write to the console every time GC is executed. This is great for understanding how the JVM panics as it suffocates for more memory

and the Engine settings are set to:
[-chunk 250000] set the memory leak to consume 250,000 bytes every iteration (-sleep <time>)
[-sleep 1] Execute the memory leak every 1 second
[-threads 10] Start 10 memory leak threads
[-delay 5] wait 5 seconds before starting, so the engineer can attach a monitoring tool to test engine
[-loadthread 100] how many CPU load threads to start (these alternate between Encryption and Array Sorting, you can control each of these individually)

             
ALTERNALTE STARTING
Edit the two cmd files to point to your JDK (I work with no JDK defined since I use so many different ones) and set your default settings
