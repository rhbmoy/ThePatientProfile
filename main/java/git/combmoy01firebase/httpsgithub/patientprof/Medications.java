package git.combmoy01firebase.httpsgithub.patientprof;

/**
 * Created by Bryan on 4/20/2017.
 */

public class Medications {

    private String medName;
    private String medDose;
    private String medFreq;
    private String medReason;
    private String medPrevention;

    public Medications() {
    }


    public Medications(String medName, String medDose, String medFreq, String medReason, String medPrevention) {
        this.medPrevention = medPrevention;
        this.medReason = medReason;
        this.medName = medName;
        this.medDose = medDose;
        this.medFreq = medFreq;
    }

    public String getMedPrevention() {
        return medPrevention;
    }

    public void setMedPrevention(String medPrevention) {
        this.medPrevention = medPrevention;
    }

    public String getMedReason() {
        return medReason;
    }

    public void setMedReason(String medReason) {
        this.medReason = medReason;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedDose() {
        return medDose;
    }

    public void setMedDose(String medDose) {
        this.medDose = medDose;
    }

    public String getMedFreq() {
        return medFreq;
    }

    public void setMedFreq(String medFreq) {
        this.medFreq = medFreq;
    }
}
