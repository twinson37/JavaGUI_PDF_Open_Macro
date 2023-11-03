import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


public class FullInvestigationTab extends JPanel implements ActionListener {

    private JFileChooser jfc = new JFileChooser();
    private JButton jbt_open = new JButton("선택");
    private JButton jbt_openPdf = new JButton("열기");
    private JButton next = new JButton("다음");
    private JButton before = new JButton("이전");
    private JLabel varNamelabel = new JLabel("변수명 입력");
    private JTextField fileName = new JTextField("파일을 선택해주세요");
    private JTextField varName = new JTextField(9);
    private String sampleID;
    private JLabel valueLabel = new JLabel("값");
    private JTextField value = new JTextField(2);
    private JButton search = new JButton("검색");
    JComboBox<String> year;
    JComboBox<String> test_category;
    JComboBox<String> hospital;
    String[] name = {"A", "C", "Y", "S", "N"};

    JComboBox<String> sibiling;
    String[] order = {"C", "C21", "C22"};

    String[] first_numbers = {"07", "08", "09", "10", "11", "12", "13", "14", "15", "16",};

    JComboBox<String> first;
    JTextField second;
    JTextField id=new JTextField(11);
    JLabel label1;
    JLabel label2;

    private Coordinate coordinate;
    private File file;
    static ExcelSheetHandler excelSheetHandler;
    static List<List<String>> excelDatas;
    OpenPdf openPdf;

    JPanel panel1 = new JPanel();
    JPanel panel2= new JPanel();
    JPanel panel3= new JPanel();
    JPanel panel4= new JPanel();
    JPanel panel5= new JPanel();
    JPanel panel6= new JPanel();

    public FullInvestigationTab(){
        super(new GridLayout(6,1));
        label1 = new JLabel("-");
        label2 = new JLabel("-");
        hospital = new JComboBox<String>(name);
        JScrollPane s = new JScrollPane(hospital);
        first = new JComboBox<String>(first_numbers);
        JScrollPane f = new JScrollPane(first);
        second = new JTextField(3);
        second.setFont(new Font("굴림", Font.PLAIN, 14));
        sibiling = new JComboBox<>(order);
        JScrollPane y = new JScrollPane(sibiling);
        panel3.add(s);
        panel3.add(f);
        panel3.add(label1);
        panel3.add(second);
        panel3.add(label2);
        panel3.add(y);

        String[] arr2 = {"아빠용", "출산전", "6개월", "1세", "2세", "3세", "4세", "5세", "6세", "7세", "8세", "9세"};
        year = new JComboBox<String>(arr2);
        String[] arr1 = {"증상", "환경", "추적", "배우자"};
        test_category = new JComboBox<String>(arr1);

        panel1.add(fileName);
        panel1.add(jbt_open);
        panel2.add(varNamelabel);
        panel2.add(varName);
        panel2.add(search);
        panel4.add(id);
        panel4.add(valueLabel);
        panel4.add(value);
        panel5.add(before);
        panel5.add(next);
        panel5.add(jbt_openPdf);
        panel6.add(year);
        panel6.add(test_category);

        add(panel1);
        add(panel3);
        add(panel6);
        add(panel2);
        add(panel4);
        add(panel5);

        id.setEditable(false);
        fileName.setEditable(false);
        jbt_open.addActionListener(this);
        search.addActionListener(this);
        next.addActionListener(this);
        before.addActionListener(this);
        jbt_openPdf.addActionListener(this);
        value.setEditable(false);
        jfc.setFileFilter(new FileNameExtensionFilter("xlsx", "xlsx"));
        jfc.setMultiSelectionEnabled(false);//다중 선택 불가
        this.setSize(400,200);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        sampleID = String.valueOf(sb.append(hospital.getSelectedItem())
                .append(first.getSelectedItem())
                .append("-")
                .append(second.getText())
                .append("-")
                .append(sibiling.getSelectedItem()));

        String category = String.valueOf(sb2.append(year.getSelectedItem()).append("_").append(test_category.getSelectedItem()));



        Object o = e.getSource();
        if(o == jbt_open){

            System.out.println("파일 선택");
            Event.textarea.append("파일 선택\n");

            if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                // showopendialog 열기 창을 열고 확인 버튼을 눌렀는지 확인
                fileName.setText(jfc.getSelectedFile().getName());
                file = new File(jfc.getSelectedFile().toString());
                long beforeTime = System.currentTimeMillis();
                if(!file.exists()) {

                    JOptionPane.showMessageDialog(null,
                            "no such file or folder", "Message",
                            JOptionPane.ERROR_MESSAGE);
                }

                try {
                    excelSheetHandler = ExcelSheetHandler.readExcel(file);
                    excelDatas = excelSheetHandler.getRows();
                    System.out.println("파일 선택 완료");
                    Event.textarea.append("파일 선택 완료\n");

                    long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
                    long secDiffTime = (afterTime - beforeTime) / 1000; //두 시간 차 계산
                    System.out.println("시간(m) : " + secDiffTime);
                    Event.textarea.append("시간(m) : " + secDiffTime+"\n");

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }


            }
        } else if (o == search) {
            System.out.println("검색 버튼");
            Event.textarea.append("검색 버튼\n");

            if (file.getName() == null) {
                JOptionPane.showMessageDialog(null,
                        "null", "Message",
                        JOptionPane.ERROR_MESSAGE);
            }
            try {
                readExcel();
                System.out.println("좌표 : "+coordinate);
                Event.textarea.append("좌표 : " + coordinate+"\n");

                value.setText(coordinate.getNowValue());
                id.setText(coordinate.getSampleId());

            } catch (Exception e1) {

                JOptionPane.showMessageDialog(null,
                        e1, "Message",
                        JOptionPane.ERROR_MESSAGE);

                throw new RuntimeException(e1);
            }
        } else if (o == next) {

            String tmp;
            do {
                tmp = coordinate.nextIdRowNum();
            }while (tmp.length()==0);
            System.out.println(coordinate);
            Event.textarea.append(String.valueOf(coordinate)+"\n");


            value.setText(tmp);
            id.setText(coordinate.getSampleId());

            System.out.println("파일 열기 : "+sampleID);
            Event.textarea.append("파일 열기 : " + sampleID+"\n");

            String s = id.getText();
            String s1;
            if(s.length()>10){
                s1 = s.substring(0,9)+s.substring(10);
            }else s1 = s;
            System.out.println("설문지 파일 : "+s1+" "+category);
            Event.textarea.append("설문지 파일 : " + s1 + " " + category+"\n");


            openPdf = new OpenPdf(s1,category);
            openPdf.extracted();

        } else if (o ==before) {

            String tmp;

            do {
                tmp = coordinate.beforeIdRowNum();
            }while (tmp.length()==0);
            System.out.println(coordinate);
            Event.textarea.append(String.valueOf(coordinate)+"\n");

            value.setText(tmp);
            id.setText(coordinate.getSampleId());

            System.out.println("파일 열기 : "+sampleID);
            Event.textarea.append("파일 열기 : " + sampleID+"\n");

            String s = id.getText();
            String s1;
            if(s.length()>10){
                s1 = s.substring(0,9)+s.substring(10);
            }else s1 = s;
            System.out.println("설문지 파일 : "+s1+" "+category);
            Event.textarea.append("설문지 파일 : " + s1 + " " + category+"\n");


            openPdf = new OpenPdf(s1,category);
            openPdf.extracted();


        } else if (o == jbt_openPdf) {

            System.out.println("파일 열기 : "+sampleID);
            Event.textarea.append("파일 열기 : " + sampleID+"\n");

            String s = id.getText();
            String s1;
            if(s.length()>10){
                s1 = s.substring(0,9)+s.substring(10);
            }else s1 = s;
            System.out.println("설문지 파일 : "+s1+" "+category);
            Event.textarea.append("설문지 파일 : " + s1 + " " + category+"\n");


            openPdf = new OpenPdf(s1,category);
            openPdf.extracted();

        }
    }


    private void readExcel() {

        List<String> firstRow = excelDatas.get(0);
        int varColumnNum=0;
        int idColumnNum = 0;
        int idRowNum=0;

        for(int i = 0; i<firstRow.size();i++){
            System.out.println("id_c 인덱스 구하는 중 : "+firstRow.get(i));
            Event.textarea.append("id_c 인덱스 구하는 중 : " + firstRow.get(i)+"\n");

            if(firstRow.get(i).equals("id_c")){

                idColumnNum = i;
                continue;
            }


            if(firstRow.get(i).equals(varName.getText())){
                System.out.println(varName.getText()+" 인덱스 구하는 중 : "+firstRow.get(i));
                Event.textarea.append(varName.getText() + " 인덱스 구하는 중 : " + firstRow.get(i)+"\n");

                varColumnNum = i;
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        String realSampleId = sampleID.substring(0,9);
        String sibling = sampleID.substring(9);
        if(sibling.length()>0){
            sb.append(realSampleId).append("-").append(sibling);
        }else sb.append(realSampleId);

        System.out.println("sample id : "+sb);
        Event.textarea.append("sample id : " + sb+"\n");

        for(int i = 0; i<excelDatas.size();i++){
            if(excelDatas.get(i).get(idColumnNum)!=null){

                System.out.println("sample id와 비교 : "+excelDatas.get(i).get(idColumnNum));
                Event.textarea.append("sample id와 비교 : " + excelDatas.get(i).get(idColumnNum)+"\n");

            }

            if(excelDatas.get(i).get(idColumnNum).equals(String.valueOf(sb))){
                idRowNum = i;
                break;
            }
        }

        coordinate = new Coordinate(this, varColumnNum, idColumnNum, idRowNum);

    }


}
