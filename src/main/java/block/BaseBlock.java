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

        // Список директорий:
        directoryTree = new DirectoryTree(); // Инициализируем directoryTree

        // Создание объекта FileDataReader
        fileDataReader = new FileDataReader();

        JPanel directoryPanel = directoryTree.getDirectoryTreePanel();

        // Создание объекта DrawBlock
        drawBlock = new DrawBlock();

        // Создание разделителя JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, directoryPanel, drawBlock);
        splitPane.setResizeWeight(0.2); // Установка начальной пропорции ширины дерева директорий и области графика
        splitPane.setContinuousLayout(true);

        add(splitPane, BorderLayout.CENTER);

        // Панель управления:
        SettingsBlock settingsBlock = new SettingsBlock(directoryTree); // Передаем инициализированный directoryTree
        settingsBlock.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        settingsBlock.setBackground(Color.LIGHT_GRAY);
        add(settingsBlock, BorderLayout.NORTH);

        // Обработчик события выбора файла в DirectoryTree
        directoryTree.setFileSelectedListener(new DirectoryTree.FileSelectedListener() {
            @Override
            public void onFileSelected(String filePath) {
                double[][] data = fileDataReader.readFile(filePath);
                drawBlock.drawGraph(data);
            }
        });
    }
}