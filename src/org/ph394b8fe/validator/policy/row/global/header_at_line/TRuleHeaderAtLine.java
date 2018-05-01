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

package org.ph394b8fe.validator.policy.row.global.header_at_line;

import java.util.ArrayList;

import org.ph394b8fe.validator.TLogger;
import org.ph394b8fe.validator.result.TResult;

/**
 * @author Peter Hoppe
 *
 */
public class TRuleHeaderAtLine
{
    private String              fConfDelimiter;
    private ArrayList<String>   fConfHeaderKeys;
    private String              fConfILineHeader;
    private char                fDelimiter;
    private boolean             fDoRun;
    private ArrayList<String>   fHeaderKeys;
    private int                 fILineHeader;
    
    /**
     * 
     */
    public TRuleHeaderAtLine ()
    {
        fConfDelimiter      = null;
        fConfHeaderKeys     = null;
        fConfILineHeader    = null;
        fDoRun              = false;
        fDelimiter          = '\0';
        fILineHeader        = -1;
        fHeaderKeys         = null;
    }
    
    /**
     * 
     */
    public void done ()
    {
        int n;
        int x;
        
        if (fConfILineHeader != null)
        {
            fDoRun = true;
        }
        else
        {
            TLogger.LogInfo (getClass(), "No line for header specified. Assuming this CSV file has no header. Rule deactivated.");
        }
        
        if (fDoRun)
        {
            if (fConfDelimiter == null)
            {
                throw new IllegalArgumentException ("No delimiter defined");
            }

            n = fConfDelimiter.length();
            if (n != 1)
            {
                throw new IllegalArgumentException
                (
                    "Delimiter information must be a single character. " + 
                    "This one has " + n + " characters: '" + fConfDelimiter + "'"
                );
            }

            fDelimiter = fConfDelimiter.charAt (0);
            if (fDelimiter == '\0')
            {
                throw new IllegalArgumentException ("Given delimiter is a null char ('\\0'), can't use it");
            }
        }
        
        if (fDoRun)
        {
            x = Integer.parseInt (fConfILineHeader);
            if (x <= 0)
            {
                throw new IllegalArgumentException ("Line number for CSV header must be an integer with minimum of 1. Given: " + x);
            }
            fILineHeader = x;
        }
        
        if (fDoRun)
        {
            if (fConfHeaderKeys == null)
            {
                throw new IllegalArgumentException ("No header keys defined, even though we have a valid line number for the header");
            }
            
            n = fConfHeaderKeys.size();
            if (n <= 0)
            {
                throw new IllegalArgumentException ("Empty header key list");
            }
            
            fHeaderKeys = new ArrayList<> ();
            fHeaderKeys.addAll (fConfHeaderKeys);
        }
    }

    /**
     * @param line
     * @param iLine
     * @param ret
     */
    public void match (String line, int iLine, TResult result)
    {
        int         i;
        int         n;
        String      rx;
        String[]    fields;
        String      key;
        int         iCol;
        boolean     doesMatch;
        boolean     doContinue;
        
        if (iLine == fILineHeader)
        {
            rx     = "" + fDelimiter;
            fields = line.split (rx);
            n      = fHeaderKeys.size ();
            
            doContinue = true;
            if (doContinue)
            {
                if (n != fields.length)
                {
                    doContinue = false;
                    result.addFatal ("For header line (" + iLine + "): Number of keys is " + fields.length + ", but should be " + n);
                }
            }
            
            if (doContinue)
            {
                if (n >= 1)
                {
                    for (i = 0; i < n; i++)
                    {
                        key         = fHeaderKeys.get (i);
                        doesMatch   = key.equals (fields [i]);
                        iCol        = i + 1;
                        if (doesMatch)
                        {
                            result.addNotice ("Header, column " + iCol + ": Got expected key '" + key + "'");
                        }
                        else
                        {
                            result.addFatal ("Header, column " + iCol + ": Expect key '" + key + "', but got '" + fields [i] + "'");
                        }
                    }
                }
            }
        }
    }

    /**
     * @param fDelimiter
     */
    public void setDelimiter (String delimiter)
    {
        fConfDelimiter = delimiter;
    }

    public void setHeader (ArrayList<String> keys)
    {
        fConfHeaderKeys = keys;
    }

    /**
     * @param iLineHeader
     */
    public void setLine (String iLineHeader)
    {
        fConfILineHeader = iLineHeader;
    }
}
