class Coordinate {

    private final FullInvestigationTab fullInvestigationTab;
    private int varColumnNum;
    private int idColumnNum;
    private int idRowNum;

    public String getNowValue() {
        return nowValue;
    }

    private String nowValue;

    public Coordinate(FullInvestigationTab fullInvestigationTab, int varColumnNum, int idColumnNum, int idRowNum) {
        this.fullInvestigationTab = fullInvestigationTab;
        this.idRowNum = idRowNum;
        this.varColumnNum = varColumnNum;
        this.idColumnNum = idColumnNum;
        this.nowValue = fullInvestigationTab.excelDatas.get(idRowNum).get(varColumnNum);
    }

    public String getSampleId() {

        return fullInvestigationTab.excelDatas.get(idRowNum).get(idColumnNum);
    }

    public int getVarColumnNum() {
        return varColumnNum;
    }

    public int getIdColumnNum() {
        return idColumnNum;
    }


    public int getIdRowNum() {
        return idRowNum;
    }

    public String nextIdRowNum() {
        if (fullInvestigationTab.excelDatas.size()>idRowNum) {
            idRowNum += 1;
            try{
                nowValue = fullInvestigationTab.excelDatas.get(idRowNum).get(varColumnNum);
            }catch (Exception ex){
                System.out.println(ex+"\n");
                Event.textarea.append(String.valueOf(ex));

                nextIdRowNum();
            }
            return nowValue;
        }
        return "nope";
    }

    public String beforeIdRowNum() {
        if (idRowNum>0){

            idRowNum -= 1;
            try{
                nowValue = fullInvestigationTab.excelDatas.get(idRowNum).get(varColumnNum);
            }catch (Exception ex){
                System.out.println(ex+"\n");
                Event.textarea.append(String.valueOf(ex));

                beforeIdRowNum();
            }
            return nowValue;
        }
        return "nope";
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "varColumnNum=" + varColumnNum +
                ", idColumnNum=" + idColumnNum +
                ", idRowNum=" + idRowNum +
                '}';
    }
}
