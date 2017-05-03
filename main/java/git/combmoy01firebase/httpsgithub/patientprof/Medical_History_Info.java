package git.combmoy01firebase.httpsgithub.patientprof;

/**
 * Created by Bryan on 4/18/2017.
 */

public class Medical_History_Info {

    private String user_allergies;
    private String spinner_data;
    private String past_surgeries;
    private String past_fam_illness;
    private String smoker_answer;
    private String alcoholic_answer;
    private String drug_answer;

    public Medical_History_Info() {
    }

    public Medical_History_Info(String user_allergies, String spinner_data, String past_surgeries, String past_fam_illness, String smoker_answer, String alcoholic_answer, String drug_answer) {
        this.user_allergies = user_allergies;
        this.spinner_data = spinner_data;
        this.past_surgeries = past_surgeries;
        this.past_fam_illness = past_fam_illness;
        this.smoker_answer = smoker_answer;
        this.alcoholic_answer = alcoholic_answer;
        this.drug_answer = drug_answer;
    }

    public String getUser_allergies() {
        return user_allergies;
    }

    public void setUser_allergies(String user_allergies) {
        this.user_allergies = user_allergies;
    }

    public String getSpinner_data() {
        return spinner_data;
    }

    public void setSpinner_data(String spinner_data) {
        this.spinner_data = spinner_data;
    }

    public String getPast_surgeries() {
        return past_surgeries;
    }

    public void setPast_surgeries(String past_surgeries) {
        this.past_surgeries = past_surgeries;
    }

    public String getPast_fam_illness() {
        return past_fam_illness;
    }

    public void setPast_fam_illness(String past_fam_illness) {
        this.past_fam_illness = past_fam_illness;
    }

    public String getSmoker_answer() {
        return smoker_answer;
    }

    public void setSmoker_answer(String smoker_answer) {
        this.smoker_answer = smoker_answer;
    }

    public String getAlcoholic_answer() {
        return alcoholic_answer;
    }

    public void setAlcoholic_answer(String alcoholic_answer) {
        this.alcoholic_answer = alcoholic_answer;
    }

    public String getDrug_answer() {
        return drug_answer;
    }

    public void setDrug_answer(String drug_answer) {
        this.drug_answer = drug_answer;
    }

}
