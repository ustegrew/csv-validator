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

package org.ph394b8fe.validator.type.impl.regex_t;

import java.util.regex.Pattern;

import org.ph394b8fe.validator.type.VType;

/**
 * @author Peter Hoppe
 *
 */
public class TTypeRx extends VType
{
    public static final String                  kKey                    = "rx";
    public static final String                  kKeyOptionRegx          = "regx";
    
    private Pattern                             fPattern;
    
    /**
     * @param key
     */
    public TTypeRx ()
    {
        fPattern = null;
    }

    /* (non-Javadoc)
     * @see org.ph394b8fe.validator.policy.type.VType#done()
     */
    @Override
    public void done ()
    {
        if (fPattern == null)
        {
            _croakMissingOption (kKeyOptionRegx);
        }
    }

    /* (non-Javadoc)
     * @see org.ph394b8fe.validator.policy.type.VType#withOption(java.lang.String, java.lang.String)
     */
    @Override
    protected void _withOption (String key, String value)
    {
        if (key.matches (kKeyOptionRegx))
        {
            fPattern = Pattern.compile (value);
        }
        else
        {
            _croakUnknownOptionKey (key);
        }
    }
}
