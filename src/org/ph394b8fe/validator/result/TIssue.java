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
    public static enum EScope
    {
        kGlobal,
        kRow,
        kField
    }
    
    public static enum ESeverity
    {
        kFatal,
        kNotice,
        kWarning
    }

    private String      fDetails;
    private Exception   fException;
    private int         fLocationCol;
    private String      fLocationFieldKey;
    private int         fLocationLine;
    private EScope      fScope;
    private ESeverity   fSeverity;
    
    /**
     * @param kfatal
     * @param details
     */
    public TIssue (ESeverity severity, EScope scope, String fieldKey, int iLine, int iCol, String details)
    {
        fSeverity           = severity;
        fScope              = scope;
        fDetails            = details;
        fException          = null;
        _setLocation (scope, fieldKey, iLine, iCol);
    }
    
    /**
     * @param kfatal
     * @param details
     * @param e
     */
    public TIssue (ESeverity severity, EScope scope, String fieldKey, int iLine, int iCol, String details, Exception e)
    {
        fSeverity           = severity;
        fScope              = scope;
        fDetails            = details;
        fException          = e;
        _setLocation (scope, fieldKey, iLine, iCol);
    }
    
    public String getAsString ()
    {
        String ret;
        
        ret = "";
        switch (fSeverity)
        {
            case kFatal:
                ret += "ERROR";
                break;
            case kNotice:
                ret += "NOTICE";
                break;
            case kWarning:
                ret += "WARN";
                break;
            default:
        }
        
        ret += " ";
        
        switch (fScope)
        {
            case kGlobal:
                ret += "(global)";
                break;
            case kRow:
                ret += "(Line: " + fLocationLine + ")";
                break;
            case kField:
                ret += "(Line: " + fLocationLine + ", Col: " + fLocationCol + ", Key:" + fLocationFieldKey + ")";
                break;
        }
        
        ret += ": " + fDetails;
        
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
    
    public String getKey ()
    {
        return fLocationFieldKey;
    }
    
    public int getLine ()
    {
        return fLocationLine;
    }
    
    public int getRow ()
    {
        return fLocationCol;
    }
    
    public EScope getScope ()
    {
        return fScope;
    }
    
    public ESeverity getSeverity ()
    {
        return fSeverity;
    }
    
    private void _setLocation (EScope scope, String fieldKey, int iLine, int iCol)
    {
        switch (scope)
        {
            case kGlobal:
                fLocationFieldKey   = null;
                fLocationLine       = -1;
                fLocationCol        = -1;
                break;
            case kRow:
                fLocationFieldKey   = fieldKey;
                fLocationLine       = iLine;
                fLocationCol        = iCol;
                break;
        }
    }
}
