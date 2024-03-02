package tools;

import block.DrawBlock;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;



public class DirectoryTree {
    private final JPanel directoryTreePanel;
    private JTree tree;
    private DefaultMutableTreeNode root;
    private String selectedFilePath;
    private DrawBlock drawBlock;
    private final String[] allowedExtensions = {"dat", "f0a", "f1a", "f2a"};

    public DirectoryTree() {
        directoryTreePanel = createDirectoryTreePanel();
        drawBlock = new DrawBlock();
    }

    public JPanel getDirectoryTreePanel() {
        return directoryTreePanel;
    }

    public DrawBlock getDrawBlock() {
        return drawBlock;
    }

    public void createDirectoryTreeFromPath(String path) {
        root.removeAllChildren();
        addNodes(root, new File(path));
        tree.setModel(new JTree(root).getModel());
        tree.expandRow(0);

        String[] parts = path.split("\\\\");
        root.setUserObject(parts[parts.length - 1]);

        selectedFilePath = path;
    }

    private JPanel createDirectoryTreePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        drawBlock = new DrawBlock();
        root = new DefaultMutableTreeNode("Выберите папку (кн. File)");
        tree = new JTree(root);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                    if (selPath != null) {
                        tree.setSelectionPath(selPath);
                        JPopupMenu menu = new JPopupMenu();

                        JMenuItem createFileItem = new JMenuItem("Создать файл");
                        createFileItem.addActionListener(actionEvent -> {
                            createFile(selPath);
                            refreshTree();
                        });

                        JMenuItem createFolderItem = new JMenuItem("Создать папку");
                        createFolderItem.addActionListener(actionEvent -> {
                            createFolder(selPath);
                            refreshTree();
                        });

                        JMenuItem deleteItem = new JMenuItem("Удалить");
                        deleteItem.addActionListener(actionEvent -> {
                            deleteFileOrFolder();
                            refreshTree();
                        });

                        menu.add(createFileItem);
                        menu.add(createFolderItem);
                        menu.add(deleteItem);
                        menu.show(tree, e.getX(), e.getY());
                    }
                }

                // Обработчик нажатия на файл
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                    if (selPath != null) {
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selPath.getLastPathComponent();
                        File file = new File(getFilePath(selectedNode));
                        if (file.isFile()) {
                            String fileName = file.getName();
                            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);


                            boolean isAllowedExtension = false;
                            for (String extension : allowedExtensions) {
                                if (fileExtension.equals(extension)) {
                                    // передаём файл
                                    drawBlock.putData(FileDataReader.readFileData(String.valueOf(file), fileExtension));
                                    isAllowedExtension = true;
                                    break;
                                }
                            }

                            if (!isAllowedExtension) {
                                JOptionPane.showMessageDialog(tree, "Поддерживаются только файлы с разрешениями: .dat, .f0a, .f1a, .f2a", "Ошибка", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        });

        panel.add(new JScrollPane(tree), BorderLayout.CENTER);
        return panel;
    }


    private void addNodes(DefaultMutableTreeNode rootNode, File parentDirectory) {
        File[] files = parentDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                String name = file.getName();

                // Проверяем, является ли файл папкой
                if (file.isDirectory()) {
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);

                    addNodes(node, file); // Рекурсивный вызов для поддиректорий

                    if (node.getChildCount() > 0) {
                        rootNode.add(node);
                    }
                } else {
                    String fileExtension = name.substring(name.lastIndexOf(".") + 1);

                    // Проверяем, соответствует ли расширение файла одному из разрешенных
                    for (String extension : allowedExtensions) {
                        if (fileExtension.equals(extension)) {
                            DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
                            rootNode.add(node);
                            break; // Прерываем цикл, если файл соответствует одному из разрешенных расширений
                        }
                    }
                }
            }
        }
    }

    private void createFile(TreePath path) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        String filePath = getFilePath(node);

        String newName = JOptionPane.showInputDialog(tree, "Введите имя файла:", "Создание файла", JOptionPane.PLAIN_MESSAGE);
        File newFile = new File(filePath + File.separator + newName);
        try {
            if (newFile.createNewFile()) {
                JOptionPane.showMessageDialog(tree, "Файл успешно создан: " + newFile.getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(tree, "Не удалось создать файл: " + newFile.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFolder(TreePath path) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        String filePath = getFilePath(node);

        String newName = JOptionPane.showInputDialog(tree, "Введите имя папки:", "Создание папки", JOptionPane.PLAIN_MESSAGE);
        File newFolder = new File(filePath + File.separator + newName);
        if (newFolder.mkdir()) {
            JOptionPane.showMessageDialog(tree, "Папка успешно создана: " + newFolder.getAbsolutePath());
        } else {
            JOptionPane.showMessageDialog(tree, "Не удалось создать папку: " + newFolder.getAbsolutePath());
        }
    }

    private String getFilePath(DefaultMutableTreeNode node) {
        if (node.isRoot()) {
            return selectedFilePath;
        }
        TreePath path = new TreePath(node.getPath());
        StringBuilder filePath = new StringBuilder(selectedFilePath);
        for (int i = 1; i < path.getPathCount(); i++) {
            filePath.append(File.separator).append(path.getPathComponent(i));
        }
        return filePath.toString();
    }

    private void deleteFileOrFolder() {
        TreePath path = tree.getSelectionPath();
        if (path != null) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            String filePath = getFilePath(node);

            File fileOrFolderToDelete = new File(filePath);
            if (fileOrFolderToDelete.exists()) {
                if (fileOrFolderToDelete.isDirectory()) {
                    deleteFolder(fileOrFolderToDelete);
                } else {
                    deleteFile(fileOrFolderToDelete);
                }
            } else {
                JOptionPane.showMessageDialog(tree, "Файл или папка не существует: " + filePath);
            }
        }
    }

    private void deleteFile(File fileToDelete) {
        if (fileToDelete.delete()) {
            JOptionPane.showMessageDialog(tree, "Файл успешно удален: " + fileToDelete.getAbsolutePath());
        } else {
            JOptionPane.showMessageDialog(tree, "Не удалось удалить файл: " + fileToDelete.getAbsolutePath());
        }
    }

    private void deleteFolder(File folderToDelete) {
        File[] contents = folderToDelete.listFiles();
        if (contents != null) {
            for (File file : contents) {
                deleteFolder(file);
            }
        }
        if (folderToDelete.delete()) {
            JOptionPane.showMessageDialog(tree, "Папка успешно удалена: " + folderToDelete.getAbsolutePath());
        } else {
            JOptionPane.showMessageDialog(tree, "Не удалось удалить папку: " + folderToDelete.getAbsolutePath());
        }
    }

    private void refreshTree() {
        createDirectoryTreeFromPath(selectedFilePath);
    }
}


