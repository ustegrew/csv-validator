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

package org.ph394b8fe.validator.policy.row;

import java.util.ArrayList;
import org.ph394b8fe.validator.policy.row.global.empty_rows.TRuleEmptyLines;
import org.ph394b8fe.validator.policy.row.global.header_at_line.TRuleHeaderAtLine;
import org.ph394b8fe.validator.policy.row.layout.TRuleRecordLayout;
import org.ph394b8fe.validator.result.TResult;

/**
 * @author Peter Hoppe
 *
 */
public class TRuleRow
{
    private String                              fConfDelimiter;
    private String                              fConfRuleEmptyLines;
    private String                              fConfRuleHeaderAtLine;
    private TRuleEmptyLines                     fRuleEmptyLines;
    private TRuleHeaderAtLine                   fRuleHeaderAtLine;
    private TRuleRecordLayout                   fRuleRecordLayout;

    public TRuleRow ()
    {
        fConfDelimiter              = null;
        fConfRuleEmptyLines         = null;
        fConfRuleHeaderAtLine       = null;
        
        fRuleRecordLayout           = null;
        fRuleEmptyLines             = new TRuleEmptyLines   ();
        fRuleHeaderAtLine           = new TRuleHeaderAtLine ();
    }
    
    public void done ()
    {
        ArrayList<String>       headerKeys;
        
        if (fRuleRecordLayout == null)
        {
            throw new IllegalArgumentException ("No record layout defined. Did you call TPolicy::withLayout (...)?");
        }
        
        if (fConfDelimiter == null)
        {
            throw new IllegalArgumentException ("No delimiter defined. Did you call TPolicy::withDelimiter (...)?");
        }
        
        if (fConfRuleEmptyLines == null)
        {
            throw new IllegalArgumentException ("No empty line policy defined. Did you call TPolicy::withEmptyLines (...)?");
        }
        
        if (fConfRuleHeaderAtLine != null)
        {
            fRuleHeaderAtLine.setLine           (fConfRuleHeaderAtLine);
            fRuleRecordLayout.setHeaderLineNum  (fConfRuleHeaderAtLine);
        }
        
        fRuleHeaderAtLine.setDelimiter          (fConfDelimiter);
        fRuleRecordLayout.setDelimiter          (fConfDelimiter);
        
        fRuleEmptyLines.setSpecifics            (fConfRuleEmptyLines);
        fRuleRecordLayout.setSpecsEmptyLines    (fConfRuleEmptyLines);

        fRuleRecordLayout.done ();
        headerKeys = fRuleRecordLayout.getHeaderKeys ();
        fRuleHeaderAtLine.setHeader (headerKeys);
        fRuleHeaderAtLine.done ();
        fRuleEmptyLines.done ();
    }
    
    /**
     * @param line
     * @param iLine
     * @return
     */
    public TResult match (String line, int iLine)
    {
        boolean             canContinue;
        TResult             ret;
        
        ret             = new TResult ();
        canContinue     = true;
        if (canContinue)
        {
            fRuleHeaderAtLine.match (line, iLine, ret);
            if (ret.hasFatalities ())
            {
                canContinue = false;
            }
        }
        if (canContinue)
        {
            fRuleEmptyLines.match (line, iLine, ret);
            if (ret.hasFatalities ())
            {
                canContinue = false;
            }
        }
        if (canContinue)
        {
            fRuleRecordLayout.match (line, iLine, ret);
        }
        
        return ret;
    }
    
    /**
     * @param delimiter
     */
    public void setConfDelimiter (String delimiter)
    {
        fConfDelimiter = delimiter;
    }

    public void setConfRuleEmptyLines (String specs)
    {
        fConfRuleEmptyLines = specs;
    }

    public void setConfRuleHeaderAtLine (String iLineHeader)
    {
        fConfRuleHeaderAtLine = iLineHeader;
    }

    /**
     * @param recordLayout
     */
    public void setRecordLayout (TRuleRecordLayout recordLayout)
    {
        fRuleRecordLayout = recordLayout;
    }
}
