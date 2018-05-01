package org.ph394b8fe.validator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.MissingResourceException;

import org.ph394b8fe.TReceptacleStdOut;
import org.ph394b8fe.validator.policy.TPolicy;
import org.ph394b8fe.validator.result.TResult;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

public class TCSVValidator
{
    public static interface IReceptacle
    {
        public void push (TResult result);
    }

    public static final String                  kDefaultEncoding            = "UTF-8";

    private TPolicy                             fPolicy;
    
    public TCSVValidator (TPolicy policy)
    {
        boolean policyClean;

        policyClean = policy.isClean ();
        if ( policyClean )
        {
            fPolicy = policy;
        }
        else
        {
            fPolicy = null;
            throw new IllegalArgumentException (
                    "Given policy hasn't been closed. Did you call that policy's done () method?");
        }
        
        
    }

    public void process (File csvFile, IReceptacle receptacle) throws IOException
    {
        _process (csvFile, receptacle);
    }

    public void process (String csvData, TReceptacleStdOut receptor)
    {
        _process (csvData, receptor);
    }
    
    private void _process (File csvFile, IReceptacle receptacle) throws IOException
    {
        FileReader              inF;
        BufferedReader          inR;
        int                     iLine;
        String                  line;
        boolean                 hasFatalities;
        boolean                 doContinue;
        TResult                 result;
        
        if (fPolicy == null)
        {
            throw new IllegalStateException ("No policy defined to check file. Aborting validation.");
        }
        
        result = fPolicy.matchGlobal (csvFile);
        receptacle.push (result);
        
        hasFatalities = result.hasFatalities ();
        if (! hasFatalities)
        {
            inF      = null;
            inR      = null;
            try
            {
                inF         = new FileReader     (csvFile);
                inR         = new BufferedReader (inF);
                line        = null;
                iLine       = 0;
                doContinue  = true;
                do
                {   /* This will loop over the entire file, even if a line is found faulty */
                    line = inR.readLine ();
                    if (line ==  null)
                    {
                        doContinue = false;
                    }
                    
                    if (doContinue)
                    {
                        iLine++;
                        result = fPolicy.matchLine (line, iLine);
                        receptacle.push (result);
                    }
                }
                while (doContinue);
            }
            catch (FileNotFoundException e)
            {
                throw new IOException (e);
            }
            finally
            {
                try
                {
                    if (inR != null)
                    {
                        inR.close ();
                    }
                    if (inF != null)
                    {
                        inF.close ();
                    }
                }
                catch (IOException e)
                {
                    TLogger.LogInfo (TCSVValidator.class, "Couldn't close csv file '" + csvFile.getPath () + "'");
                }
            }
        }
    }

    private void _process (String csvData, IReceptacle receptacle)
    {
        TResult             result;
        boolean             hasFatalities;
        String []           lines;
        int                 i;
        int                 n;

        if (fPolicy == null)
        {
            throw new IllegalStateException ("No policy defined to check file. Aborting validation.");
        }
        
        result = fPolicy.matchGlobal (csvData);
        receptacle.push (result);
        
        hasFatalities = result.hasFatalities ();
        if (! hasFatalities)
        {
            lines = csvData.split ("\\r?\\n");
            n     = lines.length;
            if (n >= 1)
            {   /* We test all lines - even if we encounter fatalities */
                for (i = 0; i < n; i++)
                {
                    result = fPolicy.matchLine (lines[i], i);
                    receptacle.push (result);
                }
            }
        }
    }
}
