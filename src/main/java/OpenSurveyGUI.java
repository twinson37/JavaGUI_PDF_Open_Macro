import java.io.IOException;

public class OpenSurveyGUI {

    public static void main(String[] args) throws IOException {

        System.out.println("프로그램 시작");
        Event.textarea.append("프로그램 시작"+"\n");

        new SavePath();
        new Event();
    }
}

