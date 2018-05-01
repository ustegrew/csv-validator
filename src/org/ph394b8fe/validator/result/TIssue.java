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

package org.ph394b8fe.validator.result;

/**
 * @author Peter Hoppe
 *
 */
public class TIssue
{
    public static enum ESeverity
    {
        kNotice,
        kWarning,
        kFatal
    }

    private String      fDetails;
    private Exception   fException;
    private ESeverity   fSeverity;
    
    /**
     * @param kfatal
     * @param details
     */
    public TIssue (ESeverity severity, String details)
    {
        fSeverity   = severity;
        fDetails    = details;
        fException  = null;
    }

    /**
     * @param kfatal
     * @param details
     * @param e
     */
    public TIssue(ESeverity severity, String details, Exception e)
    {
        fSeverity   = severity;
        fDetails    = details;
        fException  = e;
    }
    
    public String getAsString ()
    {
        String ret;
        
        ret = "";
        switch (fSeverity)
        {
            case kFatal:
                ret += "ERROR: " + fDetails;
                break;
            case kNotice:
                ret += "NOTICE: " + fDetails;
                break;
            case kWarning:
                ret += "WARN: " + fDetails;
                break;
            default:
                ret += fDetails;
        }
        
        if (fException != null)
        {
            ret += " / Exception: " + fException.toString ();
        }
        
        return ret;
    }
    
    public String getDetails ()
    {
        return fDetails;
    }
    
    public Exception getException ()
    {
        return fException;
    }
    
    public ESeverity getSeverity ()
    {
        return fSeverity;
    }
}
