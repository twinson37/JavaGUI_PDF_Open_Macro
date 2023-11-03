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

public class ErrorInvestigationTab extends JPanel implements ActionListener {

    private JFileChooser jfc = new JFileChooser();
    private JButton jbt_open = new JButton("선택");
    private JButton jbt_openPdf = new JButton("열기");
    private JButton next = new JButton("다음");
    private JButton before = new JButton("이전");
    private JLabel varNamelabel = new JLabel("변수명");
    private JTextField id = new JTextField(11);
    private JTextField fileName = new JTextField("파일을 선택해주세요");
    private JTextField varName = new JTextField(9);
    private JLabel valueLabel = new JLabel("값");
    private JTextField value = new JTextField(20);
    private JLabel errorTypeLabel = new JLabel("오류 타입");
    private JTextField errorType = new JTextField(2);
    private String sampleID;
    JComboBox<String> year;
    JComboBox<String> test_category;
    JComboBox<String> hospital;
    String[] name = {"A", "C", "Y", "S", "N"};
    ErrorCoordinate errorCoordinate;
    OpenPdf openPdf;

    JComboBox<String> sibiling;
    String[] order = {"C", "C21", "C22"};

    String[] first_numbers = {"07", "08", "09", "10", "11", "12", "13", "14", "15", "16",};

    JComboBox<String> first;
    JTextField second;
    JLabel label1;
    JLabel label2;
    private JButton search = new JButton("검색");


    JPanel panel1 = new JPanel();
    JPanel panel2= new JPanel();
    JPanel panel3= new JPanel();
    JPanel panel4= new JPanel();
    JPanel panel5= new JPanel();
    JPanel panel6= new JPanel();

    static ExcelSheetHandler excelSheetHandler;
    static List<List<String>> excelDatas;
    public ErrorInvestigationTab(){
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
        panel2.add(id);
        panel2.add(varNamelabel);
        panel2.add(varName);
        panel4.add(errorTypeLabel);
        panel4.add(errorType);
        panel4.add(valueLabel);
        panel4.add(value);
        panel5.add(before);
        panel5.add(next);
        panel5.add(jbt_openPdf);
        panel6.add(year);
        panel6.add(test_category);
        panel6.add(search);



        add(panel1);
        add(panel3);
        add(panel6);
        add(panel2);

        add(panel4);
        add(panel5);

        varName.setEditable(false);
        fileName.setEditable(false);
        jbt_open.addActionListener(this);
        search.addActionListener(this);
        before.addActionListener(this);
        next.addActionListener(this);
        jbt_openPdf.addActionListener(this);
        value.setEditable(false);
        errorType.setEditable(false);
        id.setEditable(false);
        jfc.setFileFilter(new FileNameExtensionFilter("xlsx", "xlsx"));
        jfc.setMultiSelectionEnabled(false);//다중 선택 불가
        this.setSize(400,200);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        Object o = e.getSource();
        sampleID = String.valueOf(sb.append(hospital.getSelectedItem())
                .append(first.getSelectedItem())
                .append("-")
                .append(second.getText())
                .append("-")
                .append(sibiling.getSelectedItem()));

        String category = String.valueOf(sb2.append(year.getSelectedItem()).append("_").append(test_category.getSelectedItem()));
        if(o == jbt_open){

            System.out.println("파일 선택");
            Event.textarea.append("파일 선택"+"\n");

            if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                // showopendialog 열기 창을 열고 확인 버튼을 눌렀는지 확인
                fileName.setText(jfc.getSelectedFile().getName());
                File file = new File(jfc.getSelectedFile().toString());
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
                    Event.textarea.append("파일 선택 완료"+"\n");

                    long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
                    long secDiffTime = (afterTime - beforeTime) / 1000; //두 시간 차 계산
                    System.out.println("시간(m) : " + secDiffTime);
                    Event.textarea.append("시간(m) : " + secDiffTime);

                    errorCoordinate = new ErrorCoordinate(0);
                    id.setText(excelDatas.get(0).get(0));
                    value.setText(excelDatas.get(0).get(2));
                    errorType.setText(excelDatas.get(0).get(3));
                    varName.setText(excelDatas.get(0).get(1));
                    setCategory();

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if(o==search){

            if(varName.getText()!=null) setCategory();

            readErrorExcel();
        }
        if(o==next){
            id.setText(excelDatas.get(errorCoordinate.nextRowNum()).get(0));
            value.setText(excelDatas.get(errorCoordinate.getRowNum()).get(2));
            errorType.setText(excelDatas.get(errorCoordinate.getRowNum()).get(3));
            varName.setText(excelDatas.get(errorCoordinate.getRowNum()).get(1));
            setCategory();

            System.out.println("파일 열기 : "+id.getText());
            Event.textarea.append("파일 열기 : "+id.getText());

            String s = id.getText();
            String s1;
            if(s.length()>10){
                s1 = s.substring(0,9)+s.substring(10);
            }else s1 = s;
            System.out.println("설문지 파일 : "+s1+" "+category);
            Event.textarea.append("설문지 파일 : "+s1+" "+category+"\n");


            openPdf = new OpenPdf(s1,category);
            openPdf.extracted();

        }
        if(o==before){
            if(errorCoordinate.getRowNum()!=0){
                id.setText(excelDatas.get(errorCoordinate.beforeRowNum()).get(0));
                value.setText(excelDatas.get(errorCoordinate.getRowNum()).get(2));
                errorType.setText(excelDatas.get(errorCoordinate.getRowNum()).get(3));
                varName.setText(excelDatas.get(errorCoordinate.getRowNum()).get(1));
                setCategory();

                System.out.println("파일 열기 : "+id.getText());
                Event.textarea.append("파일 열기 : "+id.getText()+"\n");

                String s = id.getText();
                String s1;
                if(s.length()>10){
                    s1 = s.substring(0,9)+s.substring(10);
                }else s1 = s;
                System.out.println("설문지 파일 : "+s1+" "+category);
                Event.textarea.append("설문지 파일 : "+s1+" "+category+"\n");


                openPdf = new OpenPdf(s1,category);
                openPdf.extracted();
            }

        }
        if(o==jbt_openPdf){
            System.out.println("파일 열기 : "+id.getText());
            Event.textarea.append("파일 열기 : "+id.getText()+"\n");

            String s = id.getText();
            String s1;
            if(s.length()>10){
                s1 = s.substring(0,9)+s.substring(10);
            }else s1 = s;
            System.out.println("설문지 파일 : "+s1+" "+category);
            Event.textarea.append("설문지 파일 : "+s1+" "+category+"\n");


            openPdf = new OpenPdf(s1,category);
            openPdf.extracted();
        }
    }

    private void readErrorExcel() {

        StringBuilder sb = new StringBuilder();
        String realSampleId = sampleID.substring(0,9);
        String sibling = sampleID.substring(9);
        if(sibling.length()>0){
            sb.append(realSampleId).append("-").append(sibling);
        }else sb.append(realSampleId);
        System.out.println(sb);
        Event.textarea.append(String.valueOf(sb)+"\n");


        for (int i = 0; i<excelDatas.size();i++){

            if(excelDatas.get(i).get(0).equals(sb.toString())&&excelDatas.get(i).get(1).equals(varName.getText())){

                errorCoordinate = new ErrorCoordinate(i);
                value.setText(excelDatas.get(i).get(2));
                System.out.println(excelDatas.get(i).get(2));
                Event.textarea.append(excelDatas.get(i).get(2)+"\n");

                System.out.println(excelDatas.get(i).get(3));
                Event.textarea.append(excelDatas.get(i).get(3)+"\n");

                errorType.setText(excelDatas.get(i).get(3));
                id.setText(excelDatas.get(i).get(0)+"\n");
                break;
            }
        }
    }

    public void setCategory(){

        String var = varName.getText();
        int index=4;
        if(var.charAt(0)=='f'){
            year.setSelectedIndex(0);
            test_category.setSelectedIndex(3);
        }
        if(var.charAt(0)=='m'){
            year.setSelectedIndex(1);
            test_category.setSelectedIndex(1);
        }
        if((var.charAt(0)=='c')){

            if(var.charAt(2)=='d'){
                year.setSelectedIndex(0);
                index = 5;
            }
            if(var.charAt(2)=='f'){
                year.setSelectedIndex(2);
            }
            if(var.charAt(2)=='e'){
                year.setSelectedIndex(1);
            }
            for(int i = 1;i<10;i++){
                if(var.charAt(index)== String.valueOf(i).charAt(0)){
                    if(i!=6) {
                        year.setSelectedIndex(i + 2);
                        break;
                    }
                    if (i == 6) {

                        if(var.charAt(index+1)=='m') year.setSelectedIndex(2);
                        if(var.charAt(index+1)=='y') year.setSelectedIndex(i+2);
                        break;
                    }
                }
            }
        }
    }
}
