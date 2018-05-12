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

package org.ph394b8fe.validator.policy.row.global.empty_rows;

import org.ph394b8fe.validator.result.TIssue.EScope;
import org.ph394b8fe.validator.result.TResult;
import org.ph394b8fe.validator.type.VType;

/**
 * Checks for empty lines and raises a validation error 
 * for any empty line if empty lines are forbidden.
 * 
 * @TODO Initial version. Later versions will allow for
 *       a more flexible definition which lines must be / 
 *       are allowed to be empty - i.e. the spec would be 
 *       a string of ranges, separated by a delimiter 
 *       (e.g. colon). Needs careful planning! For now
 *       we just allow for empty lines or not.
 *       Example: [1-5]:[6]?:[20-]?
 * 
 * @author Peter Hoppe
 */
public class TRuleEmptyLines
{
    private String          fConfSpecsEmptyLines; // Later dev includes more complex rules, e.g. line 2-4 can be empty, line 5 must be empty...
    private boolean         fIsAllowedEmptyLine;
    
    /**
     * @param details
     */
    public TRuleEmptyLines ()
    {
        fConfSpecsEmptyLines    = null;
        fIsAllowedEmptyLine     = false;
    }

    public void setSpecifics (String specs)
    {
        fConfSpecsEmptyLines = specs;
    }

    /**
     * 
     */
    public void done ()
    {
        if (fConfSpecsEmptyLines == null)
        {
            throw new IllegalArgumentException ("No specifics given. Did you call TPolicy::withEmptyLines (String) ?");
        }

        if (fConfSpecsEmptyLines.equals(VType.kValueFalse))
        {
            fIsAllowedEmptyLine = false;
        }
        else if (fConfSpecsEmptyLines.equals(VType.kValueTrue))
        {
            fIsAllowedEmptyLine = true;
        }
        else
        {
            throw new IllegalArgumentException ("Unknown value: " + fConfSpecsEmptyLines);
        }
    }

    /**
     * @param line
     * @param iLine
     * @param ret
     */
    public void match (String line, int iLine, TResult result)
    {
        int             len;
        
        len = line.length ();
        if (len <= 0)
        {
            if (fIsAllowedEmptyLine)
            {
                result.addNotice (EScope.kRow, null, iLine, 0, "Empty (OK)");
            }
            else
            {
                result.addFatal (EScope.kRow, null, iLine, 0, "Empty (not allowed here)");
            }
        }
    }
}
