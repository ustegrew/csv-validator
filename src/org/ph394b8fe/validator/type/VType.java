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

package org.ph394b8fe.validator.type;

import org.ph394b8fe.validator.type.impl.date_t.TTypeDate;
import org.ph394b8fe.validator.type.impl.int_t.TTypeInt;
import org.ph394b8fe.validator.type.impl.regex_t.TTypeRx;
import org.ph394b8fe.validator.type.impl.void_t.TTypeVoid;

/**
 * @author Peter Hoppe
 *
 */
public abstract class VType
{
    public static final String                  kKeyOptionCanBeEmpty    = "canBeEmpty";
    public static final String                  kValueFalse             = "false";
    public static final String                  kValueTrue              = "true";

    public static VType createInstance (String key)
    {
        VType ret;
        
        if (key.equals (TTypeDate.kKey))
        {
            ret = new TTypeDate ();
        }
        else if (key.equals (TTypeInt.kKey))
        {
            ret = new TTypeInt ();
        }
        else if (key.equals (TTypeRx.kKey))
        {
            ret = new TTypeRx ();
        }
        else if (key.equals (TTypeVoid.kKey))
        {
            ret = new TTypeVoid ();
        }
        else
        {
            throw new IllegalArgumentException ("Unknown type: " + key);
        }
        
        return ret;
    }
    
    private boolean fCanBeEmpty;
    
    /**
     * 
     */
    protected VType ()
    {
        fCanBeEmpty = false;
    }
    
    public abstract boolean doesMatch (String value);
    
    public abstract void done ();
    
    /**
     * @param string
     * @param string2
     * @return
     */
    public VType withOption (String key, String value)
    {
        if (key.equals (kKeyOptionCanBeEmpty))
        {
            _setConstraintCanBeEmpty (value);
        }
        else
        {
            _withOption (key, value);
        }
        
        return this;
    }
    
    protected void _croak (String msg)
    {
        throw new IllegalArgumentException (msg);
    }
    
    protected void _croakMissingOption (String key)
    {
        throw new IllegalArgumentException ("Missing option '" + key + "'. Did you call withOption (\"" + key + "\", \"someValue\") ?");
    }
    
    protected void _croakUnknownOptionKey (String key)
    {
        throw new IllegalArgumentException ("Unknown option key: " + key);
    }
    
    protected abstract void _withOption (String key, String value);

    private void _setConstraintCanBeEmpty (String b)
    {
        if (b.equals (kValueFalse))
        {
            fCanBeEmpty = false;
        }
        else if (b.equals (kValueTrue))
        {
            fCanBeEmpty = true;
        }
        else
        {
            _croak ("Unknown value: " + b);
        }
    }
}