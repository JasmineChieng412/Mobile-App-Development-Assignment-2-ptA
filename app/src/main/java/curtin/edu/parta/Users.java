package curtin.edu.parta;

public class Users {

    private String name, id, username, email, address, street,suite,city,zipcode, geo, lat, lng;

    public Users(){

    }

    public Users(String name, String id){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getID(){
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    public String getStreet() {
        return street;
    }

    public String getSuite() {
        return suite;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getGeo() {
        return geo;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
}

