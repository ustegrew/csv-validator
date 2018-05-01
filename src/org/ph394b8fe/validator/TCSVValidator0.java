package org.ph394b8fe.validator;

/**
 * Dump yard for old validator source code.
 * 
 * @author Reputaion.com
 * @deprecated
 */
@Deprecated
public class TCSVValidator0
{
    public static final String kDefaultEncoding = "UTF-8";

    public TCSVValidator0 ()
    {
    }

    /**
     * Parses a CSV file, skipping faulty lines. Constraints, for this demo:
     * <ul>
     * <li>CSV file must have a header</li>
     * 
     * </ul>
     * 
     * 
     * @param csvFile
     *            The CSV file we parse.
     * @param descriptor
     *            Customer specific descriptor specifying the expected field layout.
     * @return Result descriptor, suitable for storage and passing back to
     *         uploader's UI.
     */
//    public TResult validate (File csvFile, TDescriptor descriptor)
//    {
//        String filePath;
//        String fileName;
//        ByteOrderMark bom;
//        BOMInputStream bOMIStream;
//        String charsetName;
//        InputStreamReader reader;
//        CSVParser parser;
//        Iterator<CSVRecord> iter;
//        TResult ret;
//
//        ret = new TResult ();
//        parser = null;
//        reader = null;
//        fileName = csvFile.getName ();
//        filePath = csvFile.getPath ();
//        _log ("BEGIN: Process file: " + fileName);
//        try
//        {
//            bOMIStream = new BOMInputStream (new FileInputStream (filePath));
//            bom = bOMIStream.getBOM ();
//            charsetName = (bom == null) ? kDefaultEncoding : bom.getCharsetName ();
//            ret.setBaseInfo (filePath, fileName, charsetName, bom.toString ());
//
//            reader = new InputStreamReader (new BufferedInputStream (bOMIStream), charsetName);
//            parser = new CSVParser (reader, CSVFormat.DEFAULT.withIgnoreEmptyLines (true)
//                    .withIgnoreSurroundingSpaces (true).withDelimiter (';'));
//
//            iter = parser.iterator ();
//
//        }
//        catch (Exception e)
//        {
//            ret.addException (e);
//        }
//        finally
//        {
//            if (parser != null)
//            {
//                try
//                {
//                    parser.close ();
//                }
//                catch (Exception e)
//                {
//                }
//                try
//                {
//                    reader.close ();
//                }
//                catch (Exception e)
//                {
//                }
//            }
//        }
//
//        return ret;
//    }

    /**
     * Applies the defined testing policies to the given CSV file.
     * 
     * @param csvFile
     *            The CSV file we parse.
     * @param descriptor
     *            Specific descriptor specifying the expected field layout.
     * @return Result descriptor, suitable for storage and passing back to
     *         uploader's UI.
     */
//    public TResult validate (File csvFile, TPolicy0 policy)
//    {
//        String filePath;
//        String fileName;
//        ByteOrderMark bom;
//        BOMInputStream bOMIStream;
//        String charsetName;
//        BufferedReader reader;
//        String line;
//        TResult ret;
//
//        ret = new TResult ();
//        reader = null;
//        fileName = csvFile.getName ();
//        filePath = csvFile.getPath ();
//        _log ("BEGIN: Process file: " + fileName);
//        try
//        {
//            bOMIStream = new BOMInputStream (new FileInputStream (filePath));
//            bom = bOMIStream.getBOM ();
//            charsetName = (bom == null) ? kDefaultEncoding : bom.getCharsetName ();
//            ret.setBaseInfo (filePath, fileName, charsetName, bom.toString ());
//
//            reader = new BufferedReader (new InputStreamReader (bOMIStream, charsetName));
//            do
//            {
//                line = reader.readLine ();
//            }
//            while (line != null);
//        }
//        catch (Exception e)
//        {
//            ret.addException (e);
//        }
//        finally
//        {
//            try
//            {
//                reader.close ();
//            }
//            catch (Exception e)
//            {
//            }
//        }
//
//        return ret;
//    }
//
//    /**
//     * Poor man's logging replacement. Production code sends line to the logging
//     * framework.
//     * 
//     * @param line
//     *            Entry to log
//     */
//    private void _log (String line)
//    {
//        System.out.println ();
//    }

    /**
     * Poor man's logging replacement. Production code sends line to the logging
     * framework.
     * 
     * @param line
     *            Entry to log
     */
    private void _log (String line)
    {
        System.out.println ();
    }
}
