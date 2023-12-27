package tools;

import javax.swing.JFileChooser;
import java.io.File;

public class OpenDirectory {
    public static String main() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите директорию");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }

        return null;
    }
}
