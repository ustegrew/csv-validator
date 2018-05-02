/**
 * 
 */
package org.ph394b8fe.validator.policy;

import java.io.File;

import org.ph394b8fe.validator.policy.row.TRuleRow;
import org.ph394b8fe.validator.policy.row.layout.TRuleRecordLayout;
import org.ph394b8fe.validator.policy.table.TRuleTable;
import org.ph394b8fe.validator.result.TResult;

/**
 * @author Peter Hoppe
 *
 */
public class TPolicy
{
    private boolean                     fIsClean;
    private TRuleRow                    fRuleRow;
    private TRuleTable                  fRuleTable;
    
    
    public TPolicy ()
    {
        fRuleRow                        = new TRuleRow ();
        fRuleTable                      = new TRuleTable ();
        fIsClean                        = false;
    }

    /**
     * @return
     */
    public TPolicy done ()
    {
        fRuleTable.done ();
        fRuleRow.done ();
        
        fIsClean = true; // will get some exception before this if policy isn't ok to use.
        
        return this;
    }
    
    public boolean isClean ()
    {
        return fIsClean;
    }

    /**
     * @param csvFile
     * @return
     */
    public TResult matchGlobal (File csvFile)
    {
        TResult             ret;
        
        ret = fRuleTable.match (csvFile);
        
        return ret;
    }
    
    public TResult matchGlobal (String data)
    {
        TResult ret;
        
        ret = fRuleTable.match (data);
        
        return ret;
    }

    /**
     * @param line
     * @param iLine
     * @return
     */
    public TResult matchLine (String line, int iLine)
    {
        TResult ret;
        
        ret = fRuleRow.match (line, iLine);
        
        return ret;
    }

    /**
     * @param c
     * @return
     */
    public TPolicy withDelimiter (String delimiter) 
    {
        fRuleRow.setConfDelimiter (delimiter);
        
        return this;
    }

    /**
     * @param string
     * @return
     */
    public TPolicy withEmptyLines (String specs)
    {
        fRuleRow.setConfRuleEmptyLines (specs);

        return this;
    }

    /**
     * @param string
     * @return
     */
    public TPolicy withEncoding (String encodingName)
    {
        fRuleTable.setConfRuleFileEncoding (encodingName);
        
        return this;
    }

    /**
     * @param i
     * @return
     */
    public TPolicy withHeaderAtLine (String iLine)
    {
        fRuleRow.setConfRuleHeaderAtLine (iLine);
        
        return this;
    }

    /**
     * @param withField
     * 
     * @return
     */
    public TPolicy withLayout (TRuleRecordLayout recordLayout)
    {
        fRuleRow.setRecordLayout (recordLayout);
        
        return this;
    }

}
