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

import com.sourceclear.gramtest.TestWorker;

/**
 * @author Peter Hoppe
 *
 */
public class TGramtest
{
    /**
     * @param args
     */
    public static void main (String [] args)
    {
        TGramtest           testAppl;
        
        testAppl = new TGramtest ();
        testAppl._showUI ();
    }

    private TGramtestUI                     fGUI;
    private TGramtestRunner                 fRunner;
    private String                          fPathGrammarFile;
    private int                             fRecursionDepth;
    private int                             fMinLength;
    private int                             fMaxLength;
    
    /**
     * 
     */
    public TGramtest ()
    {
        fGUI                = new TGramtestUI       (this);
        fRunner             = new TGramtestRunner   (this);
        fPathGrammarFile    = null;
        fRecursionDepth     = 0;
        fMinLength          = 0;
        fMaxLength          = 0;
    }

    /**
     * 
     */
    public void notifyChangeRunState ()
    {
        boolean isRunning;
        
        isRunning = fRunner.isRunning ();
        if (isRunning)
        {
            fRunner.terminate ();
        }
        else
        {
            if (fPathGrammarFile != null)
            {
                fRunner.exec (fPathGrammarFile, fRecursionDepth, fMinLength, fMaxLength);
            }
        }
    }

    /**
     * @param chosenFile
     */
    public void notifyFileChanged (File chosenFile)
    {
        fPathGrammarFile = chosenFile.getAbsolutePath ();
        
    }

    /**
     * @param testCase
     */
    public void notifyNewTestCase (String testCase)
    {
        System.out.println (testCase);
        
    }

    /**
     * @param value
     */
    public void notifyParamChangeMaxLength (int value)
    {
        fMaxLength = value;
        
    }

    /**
     * @param value
     */
    public void notifyParamChangeMinLength (int value)
    {
        fMinLength = value;
        
    }

    /**
     * @param value
     */
    public void notifyParamChangeRecursionDepth (int value)
    {
        fRecursionDepth = value;
        
    }

    private void _showUI ()
    {
        fGUI.setVisible (true);
    }
    
    
}
