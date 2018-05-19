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
 * Demonstrates an example how to use the CSV validator. 
 * 
 * Example file, courtesy https://mockaroo.com
 * id,first_name,last_name,email,gender,ip_address
 * 1,Pavel,Sturgeon,psturgeon0@infoseek.co.jp,Male,17.168.21.94
 * Path: /home/peter/Documents/dev/csv-validator-assets/csv/mock-data-no-bom.csv
 * Path: /home/peter/Documents/dev/csv-validator-assets/csv/mock-data-with-bom.csv
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
                .withEncoding       ("ISO-8859-1")
                .withDelimiter      (",")
                .withEmptyLines     ("false")
                .withHeaderAtLine   ("1")
                .withLayout 
                (
                    new TRuleRecordLayout ()
                        .withField      
                        (
                            "id",
                            VType.createInstance ("int").withOption ("min", "1"),
                            "ID must be an integer >= 1"
                        )
                        .withField
                        (
                            "first_name",
                            VType.createInstance ("rx").withOption ("regx", "^[A-Z][a-zA-Z \\-.]+$"),
                            "First name must start with an upper case letter and continue with lower case letters"
                        )
                        .withField
                        (
                            "last_name",
                            VType.createInstance ("rx").withOption ("regx", "^['a-zA-Z \\-.]+$"),
                            "Last name must start with an upper case letter and continue with lower case letters"
                        )
                        .withField
                        (
                            "email",
                            VType.createInstance ("email"),
                            "Must be a valid email address"
                        )
                        .withField
                        (
                            "gender",
                            VType.createInstance ("rx").withOption ("regx", "^(Male)|(Female)$"),
                            "Gender must be either 'Male' or 'Female'"
                        )
                        .withField
                        (
                            "ip_address",
                            VType.createInstance ("rx").withOption ("regx", "^\\d+\\.\\d+\\.\\d+\\.\\d+$"), // bad regex, but I'm impatient to see something. Need an IP address type.
                            "IP address must be in quad format"
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
        receptor  = new TReceptacleStdOut (false);
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
        receptor  = new TReceptacleStdOut (false);
        validator.process (csvData, receptor);
    }
}
