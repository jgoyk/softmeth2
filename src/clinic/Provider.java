package clinic;
abstract class Provider extends Person{
    private Location location;
    public abstract int rate();

    /**
     * Constructs a Provider with a null profile and visits.
     */
    public Provider() {
        super();  // Calls the default constructor of Person (initializes profile as null)
        location = null;
    }
}
