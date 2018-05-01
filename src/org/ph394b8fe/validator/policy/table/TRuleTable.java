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

package org.ph394b8fe.validator.policy.table;

import java.io.File;

import org.ph394b8fe.validator.policy.table.encoding.TRuleDataEncoding;
import org.ph394b8fe.validator.result.TResult;

/**
 * @author Peter Hoppe
 *
 */
public class TRuleTable
{
    private TRuleDataEncoding                   fRuleDataEncoding;
    
    /**
     * 
     */
    public TRuleTable ()
    {
        fRuleDataEncoding = new TRuleDataEncoding ();
    }
    
    public void done ()
    {
        fRuleDataEncoding.done  ();
    }
    
    public TResult match (String data)
    {
        TResult             ret;
        
        ret = new TResult ();
        fRuleDataEncoding.match (data, ret);

        return ret;
    }

    /**
     * @param inFile
     */
    public TResult match (File inFile)
    {
        TResult             ret;
        
        ret = new TResult ();
        fRuleDataEncoding.match (inFile, ret);

        return ret;
    }
    
    public void setConfRuleFileEncoding (String encodingName)
    {
        fRuleDataEncoding.setEncoding (encodingName);
    }
}
