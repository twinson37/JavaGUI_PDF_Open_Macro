public class ErrorCoordinate {

    int rowNum;

    public ErrorCoordinate(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int nextRowNum() {

        this.rowNum += 1;
        return rowNum;
    }

    public int beforeRowNum() {
        if (rowNum != 0) {
            this.rowNum -= 1;
        }
        return rowNum;
    }
}
