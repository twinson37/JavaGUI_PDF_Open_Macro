import javax.swing.*;
import java.awt.*;


public class Event extends JFrame {
    Container c;
    JPanel panel;
    JPanel panel2;
    JPanel panel3;

    JButton button1;
    JButton button2;
    JButton button3;
    static JTextArea textarea = new JTextArea(10, 50);

    private final OpenPdfTab openPdfTab = new OpenPdfTab();
    private final ErrorInvestigationTab errorInvestigationTab = new ErrorInvestigationTab();
    private final FullInvestigationTab fullInvestigationTab = new FullInvestigationTab();
    public Event() {
        super("설문지 열기");

        JTabbedPane tab = new JTabbedPane();

        tab.addTab("pdf 열기", openPdfTab);
        tab.addTab("에러 정제", errorInvestigationTab);
        tab.addTab("전수 조사", fullInvestigationTab);

        add(tab);
        setSize(600, 600);
        setLocation(500, 400);
        setLayout(new GridLayout(2,1));
        setVisible(true);
        setResizable(false);
        setAlwaysOnTop(true);
        textarea.setEditable(false);
        JScrollPane sp = new JScrollPane(textarea);
        add(sp);

        pack();

        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
