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

import org.ph394b8fe.validator.result.TResult;
import org.ph394b8fe.validator.type.VType;

/**
 * @author Peter Hoppe
 *
 */
public class TRuleField
{
    private int                     fIColumn;
    private String                  fKey;
    private String                  fMsgIfValidationFails;
    private VType                   fType;
    
    public TRuleField (String key, int iColumn, VType type, String msgIfValidationFails)
    {
        fKey                    = key;
        fIColumn                = iColumn;
        fType                   = type;
        fMsgIfValidationFails   = msgIfValidationFails;
    }

    /**
     * 
     */
    public void done ()
    {
        fType.done ();
    }

    /**
     * @return
     */
    public String getKey()
    {
        return fKey;
    }

    /**
     * @param string
     * @param result
     * @param iLine 
     */
    public void match (String fieldValue, TResult result, int iLine)
    {
        fType.match (fieldValue, result, iLine, fIColumn, fKey);
    }
}
