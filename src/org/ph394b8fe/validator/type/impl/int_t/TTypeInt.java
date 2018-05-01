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

package org.ph394b8fe.validator.type.impl.int_t;

import org.ph394b8fe.validator.type.VType;

/**
 * @author Peter Hoppe
 *
 */
public class TTypeInt extends VType
{
    public static final String                  kKey                    = "int";
    public static final String                  kKeyOptionMax           = "max";
    public static final String                  kKeyOptionMin           = "min";
    
    private TConstraint_Range                   fRange;
    
    /**
     * @param key
     */
    public TTypeInt ()
    {
        fRange = new TConstraint_Range ();
    }
    
    /* (non-Javadoc)
     * @see org.ph394b8fe.validator.policy.type.VType#done()
     */
    @Override
    public void done ()
    {
    }
    
    /* (non-Javadoc)
     * @see org.ph394b8fe.validator.policy.type.VType#withOption(java.lang.String, java.lang.String)
     */
    @Override
    protected void _withOption (String key, String value)
    {
        if (key.equals (kKeyOptionMin))
        {
            _setConstraintMin (value);
        }
        else if (key.equals (kKeyOptionMax))
        {
            _setConstraintMax (value);
        }
        else
        {
            _croakUnknownOptionKey (key);
        }
    }
    
    private void _setConstraintMax (String x)
    {
        int _x;
        
        _x = Integer.parseInt (x);
        fRange.setMax (_x);
    }

    private void _setConstraintMin (String x)
    {
        int _x;
        
        _x = Integer.parseInt (x);
        fRange.setMin (_x);
    }
}