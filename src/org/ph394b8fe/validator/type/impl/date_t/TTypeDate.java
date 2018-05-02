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

package org.ph394b8fe.validator.type.impl.date_t;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import org.ph394b8fe.validator.result.TResult;
import org.ph394b8fe.validator.type.VType;

/**
 * 
 * 
 * @author Peter Hoppe
 *
 */
public class TTypeDate extends VType
{
    public static final String                  kKey                = "date";
    public static final String                  kKeyOptionFormat    = "format";
    
    private DateTimeFormatter                   fFormatter;
    
    /**
     * @param key
     */
    public TTypeDate ()
    {
        fFormatter = null;
    }
    
    

    /* (non-Javadoc)
     * @see org.ph394b8fe.validator.policy.type.VType#done()
     */
    @Override
    public void done ()
    {
        if (fFormatter == null)
        {
            _croakMissingOption (kKeyOptionFormat);
        }
    }

    /* (non-Javadoc)
     * @see org.ph394b8fe.validator.type.VType#doesMatch(java.lang.String)
     */
    @Override
    protected void _match (String value, TResult result, int iLine, int iColumn, String key, String msgIfValidationFails)
    {
        try
        {
            LocalDate.parse (value, fFormatter);
            _addNotice (result, iLine, iColumn, key, "Date OK");
        }
        catch (DateTimeParseException e)
        {
            _addFatal (result, iLine, iColumn, key, msgIfValidationFails + ". Details: " + e.getMessage());
        }
    }



    /* (non-Javadoc)
     * @see org.ph394b8fe.validator.policy.type.VType#withOption(java.lang.String, java.lang.String)
     */
    @Override
    protected void _withOption (String key, String value)
    {
        if (key.equals (kKeyOptionFormat))
        {
            fFormatter = DateTimeFormatter.ofPattern (value).withResolverStyle(ResolverStyle.STRICT);
        }
        else
        {
            _croakUnknownOptionKey (key);
        }
    }
}

/*
Example code: how to parse dates (Java tutorial)
String input = ...;
try {
    DateTimeFormatter formatter =
                      DateTimeFormatter.ofPattern("MMM d yyyy").withResolverStyle ( ResolverStyle.STRICT );
                      
    LocalDate date = LocalDate.parse(input, formatter);
    System.out.printf("%s%n", date);
}
catch (DateTimeParseException exc) {
    System.out.printf("%s is not parsable!%n", input);
    throw exc;      // Rethrow the exception.
}
// 'date' has been successfully parsed

*/