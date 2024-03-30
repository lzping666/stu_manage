package com.liao.window;

/**
 * @author lzp
 * @version 1.0
 * lzpnb!
 */


import com.liao.dialog.AddStudentDialog;
import com.liao.dialog.EditStudentDialog;
import com.liao.pojo.Student;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

public class MainGUI extends JFrame {
    private JTable studentTable;
    private JButton addButton, deleteButton, modifyButton, exitButton;
    private JTextField searchField;
    private JButton searchButton;

    public MainGUI() {
        setTitle("学生管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // 居中显示

        //ImageIcon icon = new ImageIcon("resources/img/student.png"); // 替换为您的图标文件路径
        //setIconImage(icon.getImage());


        InputStream inputStream = AdminLoginGUI.class.getClassLoader().getResourceAsStream("img/student.png");
        if (inputStream != null) {
            try {
                // 从输入流中读取图片
                Image image = ImageIO.read(inputStream);
                // 创建ImageIcon
                ImageIcon icon = new ImageIcon(image);
                // 现在你可以使用icon了，例如将其设置为JLabel的图标

                setIconImage(icon.getImage());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 关闭输入流
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("无法找到图片资源");
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 表格显示学生信息
        String[] columnNames = {"编号", "姓名", "性别", "出生日期", "学号", "QQ号"};
        Student[] students = {
                new Student("001", "张三", "男", "1990-01-01", "1001", "123456"),
                new Student("002", "李四", "女", "1991-02-02", "1002", "234567"),
                new Student("003", "王五", "男", "1992-03-03", "1003", "345678")
        };

        // 将学生信息数组转换为二维对象数组
        Object[][] data = new Object[students.length][6];
        for (int i = 0; i < students.length; i++) {
            data[i][0] = students[i].getId();
            data[i][1] = students[i].getName();
            data[i][2] = students[i].getGender();
            data[i][3] = students[i].getBirthDate();
            data[i][4] = students[i].getStudentId();
            data[i][5] = students[i].getQqNumber();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);


        studentTable = new JTable(model);

        // 添加鼠标监听器以捕获用户在表格中选择的学生信息
        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                // 如果选中了行
                if (selectedRow != -1) {
                    deleteButton.setEnabled(true); // 启用删除按钮
                    modifyButton.setEnabled(true); // 启用修改按钮
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(studentTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 搜索框
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(15);
        searchField.setText("");
        searchPanel.add(new JLabel("请输入关键字:"));
        searchPanel.add(searchField);

        searchButton = new JButton("查找");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 执行查找学生信息模块的逻辑
                //JOptionPane.showMessageDialog(null, "执行查找学生信息模块的逻辑");
                String keyword = searchField.getText().trim(); // 获取搜索框中的关键字
                if (!keyword.isEmpty()) {
                    // 创建一个新的表格模型，用于存储匹配的学生信息
                    DefaultTableModel searchResultModel = new DefaultTableModel(columnNames, 0);
                    // 遍历所有学生信息
                    for (int row = 0; row < model.getRowCount(); row++) {
                        // 遍历该行的每一列
                        for (int col = 0; col < model.getColumnCount(); col++) {
                            // 获取该单元格的值并转换为字符串
                            String cellValue = String.valueOf(model.getValueAt(row, col));
                            // 如果该单元格的值包含关键字，则将该行添加到搜索结果中
                            if (cellValue.equals(keyword)) {
                                Object[] rowData = new Object[model.getColumnCount()];
                                // 将该行的数据添加到搜索结果模型中
                                for (int i = 0; i < model.getColumnCount(); i++) {
                                    rowData[i] = model.getValueAt(row, i);
                                }
                                searchResultModel.addRow(rowData);
                                break; // 结束内层循环
                            }
                        }
                    }
                    // 如果搜索结果不为空，则更新表格的模型为搜索结果模型
                    if (searchResultModel.getRowCount() > 0) {
                        studentTable.setModel(searchResultModel);
                    } else {
                        // 如果搜索结果为空，则显示提示信息
                        JOptionPane.showMessageDialog(MainGUI.this,
                                "未找到匹配的学生信息！", "提示", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
//                    JOptionPane.showMessageDialog(MainGUI.this,
//                            "请输入关键字！", "提示", JOptionPane.WARNING_MESSAGE);
                    // 如果关键字为空，则显示所有学生信息
                    studentTable.setModel(model);
                }

            }
        });
        searchPanel.add(searchButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        // 按钮放在底部
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("添加");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 执行添加学生信息模块的逻辑
                JFrame frame = new JFrame();
                new AddStudentDialog(frame, model);
            }

        });
        deleteButton = new JButton("删除");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 执行删除学生信息模块的逻辑
                int selectedRow = studentTable.getSelectedRow();
                // 如果选中了行
                if (selectedRow != -1) {
                    // 弹出确认对话框
                    int option = JOptionPane.showConfirmDialog(MainGUI.this,
                            "你确定要删除吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                    // 如果用户确认删除
                    if (option == JOptionPane.YES_OPTION) {
                        // 删除选定的行
                        model.removeRow(selectedRow);
                        deleteButton.setEnabled(false); // 删除后禁用删除按钮
                        // 弹出删除成功提示
                        JOptionPane.showMessageDialog(MainGUI.this,
                                "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(MainGUI.this,
                            "请选择要删除的对象！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        modifyButton = new JButton("修改");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 执行修改学生信息模块的逻辑
                int selectedRow = studentTable.getSelectedRow();
                // 如果选中了行
                if (selectedRow != -1) {
                    // 获取选中行的数据
                    Object[] rowData = new Object[model.getColumnCount()];
                    for (int i = 0; i < rowData.length; i++) {
                        rowData[i] = model.getValueAt(selectedRow, i);
                    }
                    // 打开修改学生信息对话框，并传递选中行的数据
                    new EditStudentDialog(MainGUI.this, model, rowData);
                } else {
                    JOptionPane.showMessageDialog(MainGUI.this,
                            "请选择要修改的对象！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        exitButton = new JButton("退出");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 退出程序
                System.exit(0);
            }
        });


        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0))); // 增加间距
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0))); // 增加间距
        buttonPanel.add(modifyButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0))); // 增加间距
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUI();
            }
        });
    }
}




