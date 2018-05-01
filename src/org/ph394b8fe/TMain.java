/**
 * 
 */
package org.ph394b8fe;

import java.io.File;
import java.io.IOException;

import org.ph394b8fe.validator.TCSVValidator;
import org.ph394b8fe.validator.policy.TPolicy;
import org.ph394b8fe.validator.policy.row.layout.TRuleRecordLayout;
import org.ph394b8fe.validator.type.VType;

/**
 *
 *
 */
public class TMain
{
    /**
     * @param args
     */
    public static void main (String [] args)
    {
        TPolicy                 policy;

        policy = new TPolicy ()
                .withEncoding       ("UTF-8")
                .withDelimiter      (",")
                .withEmptyLines     ("true")
                .withHeaderAtLine   ("1")
                .withLayout 
                (
                    new TRuleRecordLayout ()
                        .withField      
                        (
                            "ID",
                            VType.createInstance ("int").withOption ("min", "1"),
                            "ID must be an integer >= 1"
                        )
                        .withField
                        (
                            "",
                            VType.createInstance ("void"),
                            "Second field must be empty"
                        )
                        .withField
                        (
                            "FIRST_NAME",
                            VType.createInstance ("rx").withOption ("canBeEmpty", "true").withOption ("regx", "[a-zA-Z]+"),
                            "FIRST_NAME: Only lower and upper case characters, can be empty"
                        )
                        .withField
                        (
                            "SECOND_NAME",
                            VType.createInstance ("rx").withOption ("regx", "[a-zA-Z]+").withOption ("canBeEmpty", "true"),
                            "SECOND_NAME: Only lower and upper case characters, can be empty"
                        )
                        .withField
                        (
                            "AGE",
                            VType.createInstance ("int").withOption ("min", "1").withOption ("canBeEmpty", "true"),
                            "AGE: Must be an integer >= 1, can be empty"
                        )
                ).done ();

        validateFile (args[0], policy);
    }
    
    private static void validateFile (String canonicalPath, TPolicy policy)
    {
        File                    inFile;
        TCSVValidator           validator;
        TReceptacleStdOut       receptor;

        
        System.out.println("Validating file: " + canonicalPath);
        inFile    = new File (canonicalPath);
        validator = new TCSVValidator (policy);
        receptor  = new TReceptacleStdOut ();
        try
        {
            validator.process (inFile, receptor);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private static void validateString (String csvData, TPolicy policy)
    {
        TCSVValidator           validator;
        TReceptacleStdOut       receptor;
        
        System.out.println("Validating csv data.");
        validator = new TCSVValidator (policy);
        receptor  = new TReceptacleStdOut ();
        validator.process (csvData, receptor);
    }
}
