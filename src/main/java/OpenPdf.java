import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import javax.swing.JOptionPane;

public class OpenPdf {

    HashMap<String, String> category = new HashMap<String, String>() {
        {
            put("아빠용_배우자", "03_");
            put("출산전_환경", "05_");
            put("6개월_증상", "07_");
            put("6개월_환경", "08_");
            put("6개월_추적", "09_");
            put("1세_증상", "11_");
            put("1세_환경", "12_");
            put("1세_추적", "13_");
            put("2세_증상", "21_");
            put("2세_환경", "22_");
            put("2세_추적", "23_");
            put("3세_증상", "31_");
            put("3세_환경", "32_");
            put("3세_추적", "33_");
            put("4세_증상", "41_");
            put("4세_환경", "42_");
            put("4세_추적", "43_");
            put("5세_증상", "51_");
            put("5세_환경", "52_");
            put("5세_추적", "53_");
            put("6세_증상", "61_");
            put("6세_환경", "62_");
            put("6세_추적", "63_");
            put("7세_증상", "71_");
            put("7세_환경", "72_");
            put("7세_추적", "73_");
            put("8세_증상", "81_");
            put("8세_환경", "82_");
            put("8세_추적", "83_");
            put("9세_증상", "91_");
            put("9세_환경", "92_");
            put("9세_추적", "93_");
        }
    };
    private String sampleID;
    private String nowCategory;

    public OpenPdf(String sampleID, String nowCategory) {
        this.sampleID = sampleID;
        this.nowCategory = nowCategory;
    }

    public void extracted() {
        try {
            long beforeTime = System.currentTimeMillis();

            ArrayList<String> path_list = SavePath.path_map.get(sampleID.toString());
            if (path_list != null) {
                for (String path : path_list) {
                    System.out.println("(파일 열기) 경로 검색 중 : "+path);
                    Event.textarea.append("(파일 열기) 경로 검색 중 : "+path+"\n");

                    File folder = new File(path);
                    for (File finding_files : Objects.requireNonNull(folder.listFiles())) {
                        System.out.println("(파일 열기) 파일 검색 중 : "+finding_files.getName());
                        Event.textarea.append("(파일 열기) 파일 검색 중 : "+finding_files.getName()+"\n");

                        if (finding_files.getName().substring(0, 3).equals(category.get(nowCategory))) {
                            window_open(finding_files);
                            long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
                            long secDiffTime = (afterTime - beforeTime) / 1000; //두 시간 차 계산
                            System.out.println("시간(m) : " + secDiffTime);
                            Event.textarea.append("시간(m) : " + secDiffTime+"\n");

                            return;
                        }
                    }
                }
            }

            JOptionPane.showMessageDialog(null,
                    "no such file or folder", "Message",
                    JOptionPane.ERROR_MESSAGE);
            long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
            long secDiffTime = (afterTime - beforeTime) / 1000; //두 시간 차 계산
            System.out.println("시간(m) : " + secDiffTime);
            Event.textarea.append("시간(m) : " + secDiffTime+"\n");


        } catch (IOException ex) {

            JOptionPane.showMessageDialog(null,
                    ex, "Message",
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(ex);
        }
    }
    private void window_open(File file) throws IOException {

        if(!Desktop.isDesktopSupported()){
            JOptionPane.showMessageDialog(null,
                    "Desktop is not supported", "Message",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        if(file.exists()) {
            System.out.println(file.getName()+"여는 중");
            Event.textarea.append(file.getName()+"여는 중"+"\n");

            desktop.open(file);
        }

    }
}
