package git.combmoy01firebase.httpsgithub.patientprof;

/**
 * Created by Bryan on 5/1/2017.
 */

public class Visits {

    private String doctor_name;
    private String doctor_specialty;
    private String doctor_location;
    private String visit_date;
    private String visit_synopsis;

    public Visits(String doctor_name, String doctor_specialty, String doctor_location, String visit_date, String visit_synopsis) {
        this.doctor_name = doctor_name;
        this.doctor_specialty = doctor_specialty;
        this.doctor_location = doctor_location;
        this.visit_date = visit_date;
        this.visit_synopsis = visit_synopsis;
    }

    public Visits() {
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_specialty() {
        return doctor_specialty;
    }

    public void setDoctor_specialty(String doctor_specialty) {
        this.doctor_specialty = doctor_specialty;
    }

    public String getDoctor_location() {
        return doctor_location;
    }

    public void setDoctor_location(String doctor_location) {
        this.doctor_location = doctor_location;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getVisit_synopsis() {
        return visit_synopsis;
    }

    public void setVisit_synopsis(String visit_synopsis) {
        this.visit_synopsis = visit_synopsis;
    }


}
