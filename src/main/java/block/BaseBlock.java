package block;

import tools.DirectoryTree;
import tools.FileDataReader;

import javax.swing.*;
import java.awt.*;

public class BaseBlock extends JPanel {
    private DirectoryTree directoryTree;
    private DrawBlock drawBlock;
    private FileDataReader fileDataReader;

    public BaseBlock() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setBackground(Color.WHITE);

        // Инициализация DirectoryTree и DrawBlock
        directoryTree = new DirectoryTree();
        drawBlock = directoryTree.getDrawBlock(); // Получаем объект DrawBlock из DirectoryTree


        // Создание разделителя JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, directoryTree.getDirectoryTreePanel(), drawBlock);
        splitPane.setResizeWeight(0.2);
        splitPane.setContinuousLayout(true);
        add(splitPane, BorderLayout.CENTER);

        // Панель управления:
        SettingsBlock settingsBlock = new SettingsBlock(directoryTree);
        settingsBlock.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        settingsBlock.setBackground(Color.LIGHT_GRAY);
        add(settingsBlock, BorderLayout.NORTH);
    }
}
