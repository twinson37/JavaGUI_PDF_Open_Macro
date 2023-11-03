import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public final class SavePath {
    static HashMap<String,ArrayList<String>> path_map = new HashMap<>();

    public SavePath() {
        long beforeTime = System.currentTimeMillis();
        System.out.println("path 추가 중..");
        Event.textarea.append("path 추가 중..");

        String osName = System.getProperty("os.name").toLowerCase();
        String scan_folder_name;
        if (osName.contains("win"))
        {
            System.out.println("OS : Windows");
            Event.textarea.append("OS : Windows");

            scan_folder_name ="\\\\172.25.13.151\\코코아과제관련공유\\14_코코아 국가정보화\\설문지_전자파일화\\설문지 스캔 파일";
        }
        else if (osName.contains("mac"))
        {
            System.out.println("OS : Mac");
            Event.textarea.append("OS : Mac");

            scan_folder_name = "/Users/kimjungi/Downloads/새 폴더";

        }else return;

        File scan_folder = new File(scan_folder_name);

        scanDir(scan_folder);
        System.out.println("path done");
        Event.textarea.append("path done"+"\n");

        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime)/1000; //두 시간 차 계산
        System.out.println("시간차이(m) : "+secDiffTime+"\n");
        Event.textarea.append("시간차이(m) : "+secDiffTime);


    }

    private static void scanDir(File scan_folder) {

        File[] files = scan_folder.listFiles();
        for(File f : files) {

            if(f.isDirectory()&&!f.getName().equals("새 폴더")){


                if(!path_map.containsKey(f.getName())){
                    path_map.put(f.getName(),new ArrayList<>());
                }
                path_map.get(f.getName()).add(f.getAbsolutePath());
                scanDir(f);
            }

        }
    }
}
