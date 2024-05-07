package org.example.heart_disease_detector;

public class Patient {
    private String firstName;
    private String lastName;
    private String age;
    private String sex;
    private String chestPain;
    private String BP;
    private String Chol;
    private String FBS_over_120;
    private String EKG;
    private String max_HR;
    private String excercise_angina;
    private String ST_depression;
    private String slopST;
    private String fluro;
    private String thallium;

    public Patient(String firstName, String lastName, String age, String sex, String chestPain, String BP,
                        String Chol, String FBS_over_120, String EKG, String max_HR, String excercise_angina, String ST_depression,
                        String slopST, String fluro, String thallium) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
        this.chestPain = chestPain;
        this.BP = BP;
        this.Chol = Chol;
        this.FBS_over_120 = FBS_over_120;
        this.EKG = EKG;
        this.max_HR = max_HR;
        this.excercise_angina = excercise_angina;
        this.ST_depression = ST_depression;
        this.slopST = slopST;
        this.fluro = fluro;
        this.thallium = thallium;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getAge() {
        return age;
    }
    public String getSex(){
        return sex;
    }
    public String getChestPain(){
        return chestPain;
    }
    public String getBP() {
        return BP;
    }
    public String getChol() {
        return Chol;
    }
    public String getFBS_over_120() {
        return FBS_over_120;
    }
    public String getEKG() {
        return EKG;
    }
    public String getMax_HR() {
        return max_HR;
    }
    public String getExcercise_angina() {
        return excercise_angina;
    }
    public String getST_depression() {
        return ST_depression;
    }
    public String getSlopST() {
        return slopST;
    }
    public String getFluro() {
        return fluro;
    }
    public String getThallium() {
        return thallium;
    }
}