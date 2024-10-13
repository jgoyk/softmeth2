package clinic;
public abstract class Provider extends Person{
    private Location location;
    public abstract int rate();

    /**
     * Constructs a Provider with a null profile and visits.
     */
    public Provider() {
        super();  // Calls the default constructor of Person (initializes profile as null)
        location = null;
    }

    public Provider(Profile profile, Location location) {
        super(profile);  // Calls the default constructor of Person (initializes profile as null)
        this.location = location;
    }

    public Location getLocation() {
        return this.location;
    }

    @Override
    public String toString(){
        return ("[" + super.toString() + ", " + location.name() + ", " + location.getCounty() + " " + location.getZip() + "]");
    }




}
