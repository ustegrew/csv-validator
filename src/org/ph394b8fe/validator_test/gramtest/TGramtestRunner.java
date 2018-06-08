/* *************************************************************************
   Copyright 2018 Peter Hoppe

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
************************************************************************* */

package org.ph394b8fe.validator_test.gramtest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import com.sourceclear.gramtest.TestWorker;

public class TGramtestRunner
{
    private BlockingQueue<String>   fQueue;
    private TestWorker              fWorker;
    private TGramtest               fHost;
        
    public TGramtestRunner (TGramtest host)
    {
        fQueue  = new SynchronousQueue<String> ();
        fWorker = null;
        fHost   = host;
    }
    
    public void terminate ()
    {
        if (fWorker != null)
        {
            fWorker.terminate ();
        }
    }
    
    public boolean isRunning ()
    {
        boolean ret;
        
        ret = false;
        if (fWorker != null)
        {
            ret = ! (fWorker.hasTerminated ());
        }
        
        return ret;
    }
    
    public void exec (String pathGrammarFile, int recursionDepth, int minLength, int maxLength) throws Exception
    {
        InputStream                     inStr;
        Runnable                        observer;
        
        inStr       = new FileInputStream               (new File (pathGrammarFile));
        fQueue      = new SynchronousQueue<String>      ();
        fWorker     = new TestWorker                    (inStr, fQueue, recursionDepth, minLength, maxLength);

        observer = new Runnable ()
        {
            @Override
            public void run ()
            {
                boolean hasTerminated;
                String  testCase;
                
                hasTerminated = false;
                do
                {
                    try
                    {
                        testCase = (String) fQueue.take ();
                        fHost.notifyNewTestCase (testCase); 
                        hasTerminated = fWorker.hasTerminated ();
                    }
                    catch (InterruptedException e)
                    {
                    }
                }
                while (! hasTerminated);

                fWorker = null;
            }
        };
        new Thread (observer).start ();            
        new Thread (fWorker).start ();
    }
}
