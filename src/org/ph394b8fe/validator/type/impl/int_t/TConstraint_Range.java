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

/**
 * @author Peter Hoppe
 *
 */
class TConstraint_Range
{
    private int     fMax;
    private int     fMin;

    /**
     * 
     */
    public TConstraint_Range ()
    {
        fMax = Integer.MAX_VALUE;
        fMin = Integer.MIN_VALUE;
    }
    
    public boolean doesFulfillWith (int x)
    {
        boolean ret;
        
        ret =        (x >= fMin);   /* [1] */
        ret = ret && (x <= fMax);
        
        return ret;
    }
    
    public void setMax (int max)
    {
        if (max > fMin)
        {
            fMax = max;
        }
        else
        {
            _croak (max);
        }
    }
    
    public void setMin (int min)
    {
        if (min < fMax)
        {
            fMin = min;
        }
        else
        {
            _croak (min);
        }
    }
    
    private void _croak (int x)
    {
        throw new IllegalArgumentException 
        (
            "parameter introduces boundary error: min >= max. fMin=" + fMin + ", fMax=" + fMax + ", given x=" + x
        );
    }
}

/*
[1] Split range test, easier to follow value when debugging;
    the equivalent ret = (x >= fMin) && (x <= fMax) can be a teeny
    bit harder to follow when stepping through the code. 
*/