package datatypes;

/**
 * Created by Kinga on 12/16/2015.
 */
public class Airport {
    private String name;
    private String countryCode;
    private String Id;

    public String getName(){
        return this.name;
    }

    public String getCountryCode(){
        return this.countryCode;
    }

    public String getId(){
        return this.Id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCountryCode(String countryCode){
        this.countryCode = countryCode;
    }

    public void setId(String Id){
        this.Id = Id;
    }

}
