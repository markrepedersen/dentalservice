/**
 * Created by Theodore Lau on 11/21/16.
 */

public class CountColumn {
    private String column;
    private double count;

    public CountColumn(String column, Double count) {
        this.column = column;
        this.count = count;
    }

    public String getColumn() {
        return column;
    }

    public double getCount() {
        return count;
    }
}
