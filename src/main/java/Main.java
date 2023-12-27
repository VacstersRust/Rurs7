import block.BaseBlock;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Создание окна приложения:
        JFrame frame = new JFrame("Graph Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720); // Размеры окна

        // Добавление базового блока:
        BaseBlock baseBlock = new BaseBlock();
        frame.add(baseBlock);
//        frame.pack();
        frame.setVisible(true);
    }
}