package DrawApp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public void logInfo( String className,String message) {
        System.out.printf("%s [%s] %s %s\n", "[INFO]",getTime(), className, message);
    }

    public void logWarning( String className,String message) {
        System.out.printf("%s [%s] %s %s\n", "[WARNING]",getTime(), className, message);
    }

    public void logError( String className,String message) {
        String formattedString = String.format("%s %s %s %s\n", "[ERROR]",getTime(), className, message);
        System.err.println(formattedString);
    }

    public String getTime(){
        Date currentTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = dateFormat.format(currentTime);
        return formattedTime;
    }
}
