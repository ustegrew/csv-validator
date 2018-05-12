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

package org.ph394b8fe;

import org.ph394b8fe.validator.TCSVValidator.IReceptacle;
import org.ph394b8fe.validator.result.TIssue;
import org.ph394b8fe.validator.result.TIssue.ESeverity;
import org.ph394b8fe.validator.result.TResult;

/**
 * @author Peter Hoppe
 *
 */
public class TReceptacleStdOut implements IReceptacle
{
    private boolean             fLogAll;
    
    /**
     * 
     */
    public TReceptacleStdOut (boolean logAll)
    {
        fLogAll = logAll;
    }
    
    /* (non-Javadoc)
     * @see org.ph394b8fe.validator.TCSVValidator.IReceptacle#push(org.ph394b8fe.validator.TResult)
     */
    @Override
    public void push (TResult result)
    {
        int                 i;
        int                 n;
        TIssue              is;
        ESeverity           sv;
        String              message;
        
        message         = "";
        n               = result.getNumIssues ();
        if (n >= 1)
        {
            for (i = 0; i < n; i++)
            {
                is = result.getIssue (i);
                if (fLogAll)
                {
                    message = is.getAsString ();
                    System.out.println (message);
                }
                else
                {
                    sv = is.getSeverity ();
                    if (sv == ESeverity.kFatal)
                    {
                        message = is.getAsString ();
                        System.out.println (message);
                    }
                }
            }
        }
    }
}
