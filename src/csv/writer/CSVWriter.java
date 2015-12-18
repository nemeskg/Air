package csv.writer;

/**
 * Created by Thuis on 12/17/2015.
 */
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {

    private FileWriter writer;


    /* Open the writer file, write header */
    public CSVWriter() throws IOException {
        writer = new FileWriter("resources/test.csv");
        write("Country");
        write("AirportName");
        write("Runway");
        writeLine();
    }

    /* Write value into file, adding a "," */
    public void write(String word) throws IOException
    {
        if (writer != null)
        {
            writer.append(word);
            writer.append(',');
        }
    }

    /* Jump to new line for new values*/
    public void writeLine() throws IOException{
        writer.append('\n');
    }


    /* Close writer*/
    public void closeWriter() throws IOException
    {
            writer.flush();
            writer.close();
    }


}
