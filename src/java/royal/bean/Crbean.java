package royal.bean;

public class Crbean {
private int hid = -1;
private String name;
private int question = 0;
private String unique_code;
private String other_coder;

    public Crbean(int hid, String name) {
        this.hid = hid;
        this.name = name;
    }

    public String getOther_coder() {
        return other_coder;
    }

    public void setOther_coder(String other_coder) {
        this.other_coder = other_coder;
    }
    
    public Crbean(String name, String uniqu_code,String other_coder) {
        this.name = name;
        this.unique_code = uniqu_code;
        this.other_coder = other_coder;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public String getUnique_code() {
        return unique_code;
    }

    public void setUnique_code(String unique_code) {
        this.unique_code = unique_code;
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}