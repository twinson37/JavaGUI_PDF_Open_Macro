import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class OpenPdfTab extends JPanel implements ActionListener {

    JComboBox<String> hospital;
    String[] name = {"A", "C", "Y", "S", "N"};

    JComboBox<String> sibiling;
    String[] order = {"C", "C21", "C22"};

    String[] first_numbers = {"07", "08", "09", "10", "11", "12", "13", "14", "15", "16",};

    JComboBox<String> first;
    JTextField second;
    JComboBox<String> year;
    JComboBox<String> test_category;
    JLabel label1;
    JLabel label2;
    JButton btn;
    private OpenPdf openPdf;
    JPanel panel1 = new JPanel();
    JPanel panel2= new JPanel();
    JPanel panel3= new JPanel();

    public OpenPdfTab(){
        super(new GridLayout(3,1));

        label1 = new JLabel("-");
        label2 = new JLabel("-");

        hospital = new JComboBox<String>(name);
        JScrollPane s = new JScrollPane(hospital);
//        this.add(s);
        // 텍스트 필드에 "너구리 해적단" 쓰기
        first = new JComboBox<String>(first_numbers);
        JScrollPane f = new JScrollPane(first);
//        this.add(f);
//        this.add(label1);

        second = new JTextField(3);
        second.setFont(new Font("굴림", Font.PLAIN, 14));
//        this.add(second);
//        this.add(label2);

        sibiling = new JComboBox<>(order);
        JScrollPane y = new JScrollPane(sibiling);
//        this.add(y);
        String[] arr2 = {"아빠용", "출산전", "6개월", "1세", "2세", "3세", "4세", "5세", "6세", "7세", "8세", "9세"};
        year = new JComboBox<String>(arr2);
//        this.add(year);
        String[] arr1 = {"증상", "환경", "추적", "배우자"};
        test_category = new JComboBox<String>(arr1);
//        this.add(test_category);

        btn = new JButton("열기");
//        this.add(btn);
        panel1.add(s);
        panel1.add(f);
        panel1.add(label1);
        panel1.add(second);
        panel1.add(label2);
        panel1.add(y);
        panel2.add(year);
        panel2.add(test_category);
        panel3.add(btn);
        add(panel1);
        add(panel2);
        add(panel3);


        // 체크박스 핸들러 생성
        btn.addActionListener(this);
        setSize(600, 600);
        setLocation(500, 400);
        setVisible(true);

        Action ok = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Event.textarea.append("엔터키 입력");
                System.out.println("엔터키 입력"+"\n");
                // 엔터치면 실행되는 부분
                openPdf = makeArg();
                openPdf.extracted();
            }
        };

        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
//        JTextField tf = new JTextField();
        second.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, "ENTER");
        second.getActionMap().put("ENTER", ok);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn) {
            Event.textarea.append("버튼 입력");

            System.out.println("버튼 입력"+"\n");
            openPdf = makeArg();
            openPdf.extracted();
        }
    }

    private OpenPdf makeArg(){
        Event.textarea.append("makeArg() 실행");

        System.out.println("makeArg() 실행"+"\n");
        StringBuilder sb = new StringBuilder();
        String sample_id = String.valueOf(sb.append(hospital.getSelectedItem())
                .append(first.getSelectedItem())
                .append("-")
                .append(second.getText())
                .append("-")
                .append(sibiling.getSelectedItem()));

        StringBuilder sb2 = new StringBuilder();

        String survey_pdf = String.valueOf(sb2.append(year.getSelectedItem())
                .append("_")
                .append(test_category.getSelectedItem()));

        return new OpenPdf(sample_id, survey_pdf);

    }

}
