package csv.writer;


import org.junit.Assert;
import org.junit.Test;


/**
 * Created by Thuis on 12/17/2015.
 */
public class CSVWriterTest {

    @Test
    public void testWriteWritelnAndClose() throws Exception {
        try {
            CSVWriter writeCsv = new CSVWriter();
            writeCsv.write("Test");
            writeCsv.writeLine();
            writeCsv.closeWriter();
            System.out.println("CSV File handling test complete");
        }
        catch(Exception e)
        {
            Assert.fail();
        }
    }


}