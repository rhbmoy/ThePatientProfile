package git.combmoy01firebase.httpsgithub.patientprof;

/**
 * Created by Bryan on 3/30/2017.
 */

public class PatientInfo {

    private String Patient_Name, Email, Health_Care_Provider, Date_of_Birth, Patient_Address, Phone_Number, Gender, Image, Pharmacy, Blood_Type;

    public PatientInfo(){

    }

    public PatientInfo(String Patient_Name, String Email, String Health_Care_Provider, String Date_of_Birth, String Patient_Address, String Phone_Number, String Gender, String Image, String Pharmacy, String Blood_Type) {
        this.Patient_Name = Patient_Name;
        this.Email = Email;
        this.Health_Care_Provider = Health_Care_Provider;
        this.Date_of_Birth = Date_of_Birth;
        this.Patient_Address = Patient_Address;
        this.Gender = Gender;
        this.Image = Image;
        this.Pharmacy = Pharmacy;
        this.Blood_Type = Blood_Type;
    }


    public String getPatient_Name() {
        return Patient_Name;
    }

    public void setPatient_Name(String patient_Name) {
        Patient_Name = patient_Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getHealth_Care_Provider() {
        return Health_Care_Provider;
    }

    public void setHealth_Care_Provider(String health_Care_Provider) {
        Health_Care_Provider = health_Care_Provider;
    }

    public String getDate_of_Birth() {
        return Date_of_Birth;
    }

    public void setDate_of_Birth(String date_of_Birth) {
        Date_of_Birth = date_of_Birth;
    }

    public String getPatient_Address() {
        return Patient_Address;
    }

    public void setPatient_Address(String patient_Address) {
        Patient_Address = patient_Address;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPharmacy() {
        return Pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        Pharmacy = pharmacy;
    }

    public String getBlood_Type() {
        return Blood_Type;
    }

    public void setBlood_Type(String blood_type) {
        Blood_Type = blood_type;
    }
}
