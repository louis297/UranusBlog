package UranusBlog.Utils;

public class Utils {
    public static boolean validateAccountInfo(){
        return true;
    }

    public static String contentPrepare(String rawContent){
        String content = rawContent;
        content = content.replace("<", "&lt;");
        content = content.replace(">", "&gt;");
        content = content.replace("\n", "<br/>");
        content = content.replace("\r", "<br/>");

        return content;
    }
}
