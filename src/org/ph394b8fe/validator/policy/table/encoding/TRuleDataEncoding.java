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

package org.ph394b8fe.validator.policy.table.encoding;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.ph394b8fe.validator.result.TIssue.EScope;
import org.ph394b8fe.validator.result.TResult;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

/**
 * @author Peter Hoppe
 */
public class TRuleDataEncoding
{
    private String fEncodingName;
    
    public TRuleDataEncoding ()
    {
        fEncodingName   = null; 
    }
    
    public void match (String data, TResult result)
    {
        CharsetDetector                         detector;
        CharsetMatch                            match;
        String                                  encFound;
        boolean                                 hasEncoding;
        
        detector = new CharsetDetector ();
        detector.setText (data.getBytes ());
        
        match       = detector.detect ();
        encFound    = match.getName ();
        hasEncoding = false;
        if (encFound != null)
        {
            hasEncoding = encFound.equals (fEncodingName); 
        }

        if (! hasEncoding)
        {
            result.addFatal 
            (
                EScope.kGlobal,
                null,
                0,
                0,
                "For given data: expected " +
                "and actual encoding don't " +
                "match: Expected: '" + fEncodingName + "'" + 
                ", actual: '" + encFound + "'"
            );
        }
    }
    
    public void match (File inFile, TResult result)
    {
        InputStream                     inStream;
        CharsetDetector                 detector;
        CharsetMatch                    match;
        String                          encFound;
        String                          fileName;
        String                          filePath;
        boolean                         hasEncoding;
        
        fileName = inFile.getName ();
        filePath = inFile.getAbsolutePath ();
        inStream = null;
        try
        {
            inStream = new BufferedInputStream (new FileInputStream (inFile));
            detector = new CharsetDetector ();
            detector.setText (inStream);
            
            match       = detector.detect ();
            encFound    = match.getName ();
            hasEncoding = false;
            if (encFound != null)
            {
                hasEncoding = encFound.equals (fEncodingName);
            }
            
            if (! hasEncoding)
            {
                result.addFatal 
                (
                    EScope.kGlobal,
                    null,
                    0,
                    0,
                    "For file '" + fileName + 
                    "': Expected and actual encoding " + 
                    "don't match: Expected: '" + fEncodingName + 
                    "', actual: '" + encFound + "'"
                );
            }
        }
        catch (FileNotFoundException e)
        {
            result.addFatal 
            (
                EScope.kGlobal,
                null,
                0,
                0,
                e,
                "Could not find source file: '" + filePath + "'"
            );
        }
        catch (IOException e)
        {
            result.addFatal
            (
                EScope.kGlobal,
                null,
                0,
                0,
                e,
                "Could not read source file: '" + filePath + "'"
            );
        }
        finally
        {
            try
            {
                if (inStream != null)
                {
                    inStream.close();
                }
            }
            catch (IOException e)
            {
                result.addWarning
                (
                    EScope.kGlobal,
                    null,
                    0,
                    0,
                    "Failed to close source file: '" + filePath + "'"
                );
            }
        }
    }

    public void setEncoding (String encodingName)
    {
        fEncodingName = encodingName;
    }

    public void done ()
    {
        if (fEncodingName == null)
        {
            throw new IllegalArgumentException ("No encoding specified. Did you call TPolicy::withEncoding (String) ?");
        }
    }
}
