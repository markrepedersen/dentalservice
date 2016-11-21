/**
 * Created by Theodore Lau on 11/21/16.
 */

public class CountColumn {
    private String column;
    private int count;

    public CountColumn(String column, int count) {
        this.column = column;
        this.count = count;
    }

    public String getColumn() {
        return column;
    }

    public int getCount() {
        return count;
    }
}
