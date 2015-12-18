package parser;

/**
 * Created by Kinga on 12/16/2015.
 */

import csv.writer.CSVWriter;
import datatypes.Airport;
import datatypes.Country;
import datatypes.Runway;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirportFinder {
    private static boolean parsed = false;
    private static List<Map> maps;
    private static CSVWriter writeCsv;

    /* Remove quotes from country code and country names */
    private static String removeQuotes(String quote){
        return quote.substring(1,quote.length()-1);
    }

    public static void closeWriter() throws IOException{
        writeCsv.closeWriter();
    }
    private static List<Map> parse() {

        String[] csvFile = new String[3];
        csvFile[0] = "resources/airports.csv";
        csvFile[1] = "resources/runways.csv";
        csvFile[2] = "resources/countries.csv";

        BufferedReader fileReader = null;
        String line;
        String csvSplitBy = ",";


        Map<String, List<Airport>> airportMap = new HashMap<>();
        Map<String, List<Runway>> runwayMap = new HashMap<>();
        Map<String, Country> countryToCountryMap = new HashMap<>();
        Map<String, Country> codeToCountryMap = new HashMap<>();

        /* To store maps for return */
        List<Map> maps = null;



        try {
            /* Airport parsing*/
            fileReader = new BufferedReader(new FileReader(csvFile[0]));
            line = fileReader.readLine(); /* Header line*/

            int code = 0,id = 0,name = 0;
            String[] currentLine;

            currentLine = line.split(csvSplitBy);
            for (int i = 0; i< currentLine.length; i++){

                if (currentLine[i].equals("\"iso_country\"")){
                    code = i;
                }
                else if (currentLine[i].equals("\"id\"")){
                    id = i;
                }
                else if (currentLine[i].equals("\"name\"")) {
                    name = i;
                }
            }

            line = fileReader.readLine(); /* First line of values */

            while (line != null) {

                currentLine = line.split(csvSplitBy);

                Airport airport = new Airport();
                airport.setCountryCode(removeQuotes(currentLine[code]));
                airport.setId(currentLine[id]);
                airport.setName(removeQuotes(currentLine[name]));

                List<Airport> airportsList = airportMap.get(airport.getCountryCode());

                /* If there's no value assigned to the current key we make one */
                if (airportsList == null) {
                    airportMap.put(airport.getCountryCode(), airportsList = new ArrayList<Airport>());
                }
                airportsList.add(airport);

                line = fileReader.readLine(); /*Next line of values */
            }

            /* Runway parsing */
            fileReader = new BufferedReader(new FileReader(csvFile[1]));

            line = fileReader.readLine(); /* Header line*/
            currentLine = line.split(csvSplitBy);
            code = 0;
            id = 0;

            for (int i = 0; i< currentLine.length; i++){

                if (currentLine[i].equals("\"id\"")){
                    id = i;
                }
                else if (currentLine[i].equals("\"airport_ref\"")){
                    code = i;
                }
            }
            line = fileReader.readLine(); /* First line of values*/

            while (line != null) {

                currentLine = line.split(csvSplitBy);

                Runway runway = new Runway();
                runway.setRunwayId(currentLine[id]);
                runway.setAirportId(currentLine[code]);

                List<Runway> runwaysList = runwayMap.get(runway.getAirportId());

                 /* If there's no value assigned to the current key we make one */
                if (runwaysList == null) {
                    runwayMap.put(runway.getAirportId(), runwaysList = new ArrayList<Runway>());
                }

                runwaysList.add(runway);

                line = fileReader.readLine(); /* Next line of values*/


            }

            /* Country parser */

            fileReader = new BufferedReader(new FileReader(csvFile[2]));

            line = fileReader.readLine(); /* Header line */
            currentLine = line.split(csvSplitBy);
            code = 0;
            name = 0;

            for (int i = 0; i< currentLine.length; i++){

                if (currentLine[i].equals("\"name\"")){
                    name = i;
                }
                else if (currentLine[i].equals("\"code\"")){
                    code = i;
                }
            }
            line = fileReader.readLine(); /* First line of values */

            while (line != null) {

                currentLine = line.split(csvSplitBy);

                Country country = new Country();
                country.setCode(removeQuotes(currentLine[code]));
                country.setName(removeQuotes(currentLine[name]));

                /* Adding values to the two maps */
                countryToCountryMap.put(country.getName(), country);
                codeToCountryMap.put(country.getCode(), country);

                line = fileReader.readLine(); /*Next line of values*/
            }

            /* Preparing values for return */
            maps = new ArrayList<>();
            maps.add(codeToCountryMap);
            maps.add(countryToCountryMap);
            maps.add(airportMap);
            maps.add(runwayMap);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return maps;
    }

    public static boolean find(String countryInput)throws IOException{


        /* If file is already parsed we skip reparsing*/
        if (!parsed) {
            maps = parse(); // 0.codetocountry 1.countrytocountry 2.airportmap 3.runwaymap
            AirportFinder.parsed = true;
            /* Open file writer */
            writeCsv = new CSVWriter();
        }

        Map<String, List<Airport>> airportMap = maps.get(2);
        Map<String, List<Runway>> runwayMap = maps.get(3);
        Map<String, Country> countryToCountryMap = maps.get(1);
        Map<String, Country> codeToCountryMap = maps.get(0);

    try {
            Country country;

            if (countryInput.length() == 2)
            {
                country = codeToCountryMap.get(countryInput);
            } else
            {
                country = countryToCountryMap.get(countryInput);
            }

            if (country != null) {

                String airportKey = country.getCode();
                System.out.println("In " + country.getName()+ " the following airports operate:");
                List<Airport> l = airportMap.get(airportKey);
                int size = l.size();

                for (int i = 0; i < size; i++) {
                    Airport air = l.get(i);

                    List<Runway> r = runwayMap.get(air.getId());

                    if (r == null) { /* If there are no runways on that airport */
                        System.out.print("Airport " + air.getName() + " has no runways.");
                        writeCsv.write(country.getName());
                        writeCsv.write(air.getName());
                        writeCsv.write("N/A");
                        writeCsv.writeLine();
                    }
                    else { /*Prints out all runways for said airport */
                        System.out.print("Airport " + air.getName() + " has the following runways: ");

                        for (int j = 0; j < r.size(); j++) {
                            Runway run = r.get(j);
                            int id = j + 1;
                            System.out.print(id + ".RunwayID: " + run.getRunwayId() + " ");
                            writeCsv.write(country.getName());
                            writeCsv.write(air.getName());
                            writeCsv.write(run.getRunwayId());
                            writeCsv.writeLine();
                        }
                    }
                    System.out.println();

                }
            } else {
                System.out.println("Not a valid country");
                return false;
            }

    } catch (NullPointerException e) {
        e.printStackTrace();
        return false;
    }
        return true;
    }


}
