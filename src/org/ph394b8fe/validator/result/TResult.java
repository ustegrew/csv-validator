package org.ph394b8fe.validator.result;

import java.util.ArrayList;

import org.ph394b8fe.validator.result.TIssue.ESeverity;

public class TResult
{
    private ArrayList<TIssue>           fIssues;
    private int                         fNFatalities;
    
    public TResult ()
    {
        fIssues         = new ArrayList<> ();
        fNFatalities    = 0;
    }
    
    public ArrayList<TIssue> getIssues ()
    {
        return fIssues;
    }

    /**
     * @param string
     */
    public void addFatal (String details)
    {
        TIssue      issue;
        
        issue = new TIssue (TIssue.ESeverity.kFatal, details);
        fIssues.add (issue);
        fNFatalities++;
    }

    /**
     * @param e
     * @param string
     */
    public void addFatal (Exception e, String details)
    {
        TIssue      issue;
        
        issue = new TIssue (TIssue.ESeverity.kFatal, details, e);
        fIssues.add (issue);
        fNFatalities++;
    }

    /**
     * @param string
     */
    public void addWarning (String details)
    {
        TIssue      issue;
        
        issue = new TIssue (TIssue.ESeverity.kWarning, details);
        fIssues.add (issue);
    }

    public void addNotice (String details)
    {
        TIssue      issue;
        
        issue = new TIssue (ESeverity.kNotice, details);
        fIssues.add (issue);
    }
    
    public TIssue getIssue (int i)
    {
        TIssue ret;
        
        ret = fIssues.get (i);
        
        return ret;
    }
    
    public int getNumIssues ()
    {
        int ret;
        
        ret = fIssues.size ();
        
        return ret;
    }
    
    /**
     * @return
     */
    public boolean hasFatalities ()
    {
        boolean ret;
        
        ret = (fNFatalities >= 1); 
        
        return ret;
    }
}
