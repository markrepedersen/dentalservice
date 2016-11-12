import java.sql.Timestamp;

/**
 * Created by mark on 2016-11-07.
 */
public class Appointment {
    private int num;
    private Timestamp fromTime;
    private Timestamp toTime;
    private String type;

    public Appointment(int num, String type, Timestamp fromTime, Timestamp toTime) {
        this.num = num;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.type = type;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setFromTime(Timestamp fromTime) {
        this.fromTime = fromTime;
    }

    public void setToTime(Timestamp toTime) {
        this.toTime = toTime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public Timestamp getFromTime() {
        return fromTime;
    }

    public Timestamp getToTime() {
        return toTime;
    }

    public String getType() {
        return type;
    }
}
