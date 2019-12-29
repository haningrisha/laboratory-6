package project.university.GUI;

import java.util.Locale;
import java.util.ResourceBundle;

public class GUISample {
    public static void main(String[] args) {
        ResourceBundle rs = ResourceBundle.getBundle("project.university.characters", new Locale("cs"));
        LoginActivity loginActivity = new LoginActivity(rs);
        loginActivity.setVisible(true);
    }
}
