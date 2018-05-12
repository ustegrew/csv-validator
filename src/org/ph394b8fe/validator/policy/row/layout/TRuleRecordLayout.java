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

package org.ph394b8fe.validator.policy.row.layout;

import java.util.ArrayList;

import org.ph394b8fe.validator.result.TIssue.EScope;
import org.ph394b8fe.validator.result.TResult;
import org.ph394b8fe.validator.type.VType;

/**
 * @author Peter Hoppe
 */
public class TRuleRecordLayout
{
    private String                fConfDelimiter;
    private String                fConfILineHeader;
    private String                fConfSpecsEmptyLines;
    private char                  fDelimiter;
    private boolean               fIsAllowedEmptyLine;
    private int                   fILineHeader;
    private ArrayList<TRuleField> fRulesFields;

    public TRuleRecordLayout()
    {
        fConfDelimiter              = null;
        fConfILineHeader            = null;
        fConfSpecsEmptyLines        = null;
        fIsAllowedEmptyLine         = false;
        fILineHeader                = -1;
        fDelimiter                  = '\0';
        fRulesFields                = new ArrayList<>();
    }

    /**
     * 
     */
    public void done()
    {
        int             i;
        int             n;
        int             x;
        TRuleField      rule;

        if (fConfDelimiter == null)
        {
            throw new IllegalArgumentException ("No delimiter defined");
        }

        if (fConfILineHeader != null)
        {
            x = Integer.parseInt (fConfILineHeader);
            if (x <= 0)
            {
                throw new IllegalArgumentException ("Line number for CSV header must be an integer with minimum of 1. Given: " + x);
            }
            fILineHeader = x;
        }

        if (fConfSpecsEmptyLines == null)
        {
            if (fConfSpecsEmptyLines.equals (VType.kValueFalse))
            {
                fIsAllowedEmptyLine = false;
            }
            else if (fConfSpecsEmptyLines.equals (VType.kValueTrue))
            {
                fIsAllowedEmptyLine = true;
            }
            else
            {
                throw new IllegalArgumentException ("Unknown value: " + fConfSpecsEmptyLines);
            }
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

        n = fRulesFields.size();
        if (n <= 0)
        {
            throw new IllegalArgumentException("No fields defined");
        }

        if (n >= 1)
        {
            for (i = 0; i < n; i++)
            {
                rule = fRulesFields.get(i);
                rule.done();
            }
        }
    }
    
    public char getDelimiter ()
    {
        return fDelimiter;
    }

    /**
     * @return
     */
    public ArrayList<String> getHeaderKeys()
    {
        int i;
        int n;
        TRuleField rule;
        String key;
        ArrayList<String> ret;

        ret = new ArrayList<>();
        n = fRulesFields.size();
        if (n >= 1)
        {
            for (i = 0; i < n; i++)
            {
                rule = fRulesFields.get(i);
                key = rule.getKey();
                ret.add(key);
            }
        }

        return ret;
    }

    /**
     * @param line
     * @param iLine
     * @param ret
     */
    public void match (String line, int iLine, TResult result)
    {
        String                  rx;
        boolean                 doContinue;
        int                     len;
        String                  fields [];
        int                     i;
        int                     n;
        TRuleField              rule;
        
        doContinue = true;
        fields     = null;
        
        /* Abort line validation if empty lines allowed and line empty */
        if (doContinue)
        {
            if (fIsAllowedEmptyLine)
            {
                len = line.length ();
                if (len <= 0)
                {
                    doContinue = false;
                }
            }
        }
        
        /* Abort line validation if this is a header line */
        if (doContinue)
        {
            if (iLine == fILineHeader)
            {
                doContinue = false;
            }
        }
        
        /* Validate record geometry */
        if (doContinue)
        {
            rx     = "" + fDelimiter;
            fields = line.split (rx);
            n      = fRulesFields.size ();
            if (n != fields.length)
            {
                doContinue = false;
                result.addFatal
                (
                    EScope.kRow,
                    null,
                    iLine,
                    0,
                    "Number of fields is " + fields.length + ", but should be " + n
                );
            }
        }
        
        /* Validate field types */
        if (doContinue)
        {
            n = fRulesFields.size ();
            if (n >= 1)
            {
                for (i = 0; i < fields.length; i++)
                {
                    rule = fRulesFields.get (i);
                    rule.match (fields [i], result, iLine);
                }
            }
        }
    }

    /**
     * @param delimiter
     */
    public void setDelimiter (String delimiter)
    {
        fConfDelimiter = delimiter;
    }

    /**
     * @param iLineHeader
     */
    public void setHeaderLineNum (String iLineHeader)
    {
        fConfILineHeader = iLineHeader;
    }

    /**
     * @param specs
     */
    public void setSpecsEmptyLines (String specs)
    {
        fConfSpecsEmptyLines = specs;
    }

    /**
     * @param string
     * @param withOption
     * @param string2
     * @return
     */
    public TRuleRecordLayout withField (String key, VType type, String msgIfValidationFails)
    {
        int iCol;
        TRuleField rule;

        iCol = fRulesFields.size() + 1; /* columns are one-based */
        rule = new TRuleField (key, iCol, type, msgIfValidationFails);
        fRulesFields.add(rule);

        return this;
    }
}
