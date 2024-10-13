package clinic;

public class Technician extends Provider {
    private final int RATE_NOT_SET = -1;
    private int ratePerVisit;


    public Technician() {
        super();
        ratePerVisit = RATE_NOT_SET;
    }

    public Technician(Profile profile, Location location, int ratePerVisit) {
        super(profile, location);
        this.ratePerVisit = ratePerVisit;
    }

    @Override
    public int rate() {
        return ratePerVisit;
    }

    @Override
    public String toString() {
        return (super.toString() + "[rate: $" + ratePerVisit + ".00]");
    }
}
