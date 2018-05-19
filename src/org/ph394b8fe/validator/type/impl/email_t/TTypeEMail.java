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

package org.ph394b8fe.validator.type.impl.email_t;

import org.ph394b8fe.validator.result.TResult;
import org.ph394b8fe.validator.type.VType;

/**
 * An email address. Follows the pattern stipulated on
 * https://www.w3.org/TR/2012/WD-html-markup-20120320/input.email.html
 * 
 * @author Peter Hoppe
 *
 */
public class TTypeEMail extends VType
{
    public static final String                  kKey                    = "email";
    private static final String                 kPattern                = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
    
    /**
     * @param key
     */
    public TTypeEMail ()
    {
    }

    /* (non-Javadoc)
     * @see org.ph394b8fe.validator.policy.type.VType#done()
     */
    @Override
    public void done ()
    {
    }

    /* (non-Javadoc)
     * @see org.ph394b8fe.validator.type.VType#_match(java.lang.String, org.ph394b8fe.validator.result.TResult, int, int, java.lang.String)
     */
    @Override
    protected void _match (String value, TResult result, int iLine, int iColumn, String key, String msgIfValidationFails)
    {
        boolean doesMatch;
        
        doesMatch = value.matches (kPattern);
        if (doesMatch)
        {
            _addNotice (result, iLine, iColumn, key, "Field matches rule (OK)");
        }
        else
        {
            _addFatal (result, iLine, iColumn, key, msgIfValidationFails);
        }
    }

    /* (non-Javadoc)
     * @see org.ph394b8fe.validator.policy.type.VType#withOption(java.lang.String, java.lang.String)
     */
    @Override
    protected void _withOption (String key, String value)
    {
        _croakUnknownOptionKey (key);
    }
}
