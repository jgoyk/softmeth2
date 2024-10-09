package clinic;

public class Doctor extends Provider{
    // extends the Provider class
    private Specialty specialty; // encapsulate the rate per visit based on specialty
    private String npi; //  National Provider Identification unique to the doctor



    @Override
    public int rate() {
        return 0;
    }
}
