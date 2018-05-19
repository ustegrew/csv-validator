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

package org.ph394b8fe.validator_test.type;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

import org.junit.Test;

import com.sourceclear.gramtest.TestRunner;

public class GramtestRunTest
{
    @Test
    public void test () throws IOException, InterruptedException
    {
        final BlockingQueue<String> queue = new SynchronousQueue<String> ();
        TestRunner continuousRunner = new TestRunner (getClass ().getResourceAsStream ("/org/ph394b8fe/url.bnf"), queue, 10, 8, 32);
        new Thread (continuousRunner).start ();
        while (true)
        {
            String testCase = (String) queue.take ();
            System.out.println (testCase);
        }
    }
}
