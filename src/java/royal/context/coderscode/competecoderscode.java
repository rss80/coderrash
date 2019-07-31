package royal.context.coderscode;

import java.sql.Timestamp;

public class competecoderscode {
    private String coders_code;
    private String unique_compete_code;
    private String coder_name;
    private int question_number;
    private long timestamp;

    public competecoderscode(String coders_code, String unique_compete_code, String coder_name, int question_number) {
        this.coders_code = coders_code;
        this.unique_compete_code = unique_compete_code;
        this.coder_name = coder_name;
        this.question_number = question_number;
        Timestamp current_timestamp = new Timestamp(System.currentTimeMillis());
        long timestamp = current_timestamp.getTime();
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCoders_code() {
        return coders_code;
    }

    public void setCoders_code(String coders_code) {
        this.coders_code = coders_code;
    }

    public String getUnique_compete_code() {
        return unique_compete_code;
    }

    public void setUnique_compete_code(String unique_compete_code) {
        this.unique_compete_code = unique_compete_code;
    }

    public String getCoder_name() {
        return coder_name;
    }

    public void setCoder_name(String coder_name) {
        this.coder_name = coder_name;
    }

    public int getQuestion_number() {
        return question_number;
    }

    public void setQuestion_number(int question_number) {
        this.question_number = question_number;
    }
}
