/**
 * Created by Theodore Lau on 11/11/16.
 */
/**
 * Created by Theodore on 11/11/16.
 */
public class Medicine {
    private int code;
    private double cost;
    private String description;

    public Medicine(int code, double cost) {
        this.code = code;
        this.cost = cost;
    }

    public Medicine(int code, double cost, String description) {
        this.code = code;
        this.cost = cost;
        this.description = description;

    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public double getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }
}

