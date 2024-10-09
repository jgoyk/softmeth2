package clinic;

public class Doctor extends Provider{
    // extends the Provider class
    private Specialty specialty; // encapsulate the rate per visit based on specialty
    private String npi; //  National Provider Identification unique to the doctor

    public Doctor() {
        super();
        specialty = null;
        npi = null;
    }

    public Doctor(Profile profile, Location location, String npi, Specialty specialty){
        super(profile,location);
        this.specialty = specialty;
        this.npi = npi;
    }


    @Override
    public int rate() {
        return specialty.getCharge();
    }
}
