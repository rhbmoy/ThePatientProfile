package git.combmoy01firebase.httpsgithub.patientprof;

/**
 * Created by Bryan on 4/28/2017.
 */

public class Upload {

    public String name;
    public String doctor;
    public String url;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String name, String doctor, String url) {
        this.name = name;
        this.doctor = doctor;
        this.url= url;
    }

    public String getName() {
        return name;
    }

    public String getDoctor() { return doctor; }

    public String getUrl() {
        return url;
    }
}
