package project.university.console;
import java.io.*;

public class MailSender {
    public static void send(String email, String password){
        try {
            ProcessBuilder pb = new ProcessBuilder("python","quickstart.py",""+email,""+password);
            Process p = pb.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String ret = in.readLine();
            System.out.println("value is : "+ret);

        }catch (java.io.IOException e){
            System.out.println(e.getMessage());
        }
        /*Register register = new Register();
        register.registration();
*/
    }
}
