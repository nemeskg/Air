package datatypes;

/**
 * Created by Kinga on 12/16/2015.
 */
public class Runway {
    private String runwayId;
    private String airportId;

    public String getRunwayId(){
        return this.runwayId;
    }
    public String getAirportId(){
        return this.airportId;
    }

    public void setRunwayId(String id){
        this.runwayId = id;
    }
    public void setAirportId(String id){
        this.airportId = id;
    }
}
