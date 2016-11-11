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
}
