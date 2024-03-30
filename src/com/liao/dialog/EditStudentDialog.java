package com.liao.dialog;

/**
 * @author lzp
 * @version 1.0
 * lzpnb!
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditStudentDialog extends JDialog {
    private JTextField idField, nameField, genderField, birthDateField, studentIdField, qqNumberField;
    private JButton editButton, cancelButton;
    private DefaultTableModel tableModel;

    public EditStudentDialog(JFrame parent, DefaultTableModel model, Object[] rowData) {
        super(parent, "修改学生信息", true);
        setSize(300, 250);
        setLocationRelativeTo(parent);

        this.tableModel = model;

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5)); // 设置行列之间的间距为5

        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 14); // 设置文本框的字体

        JLabel idLabel = new JLabel("编号:");
        idField = new JTextField((String) rowData[0]);
        idField.setFont(font); // 设置文本框的字体
        idField.setEditable(false); // 编号不可编辑
        panel.add(idLabel);
        panel.add(idField);

        JLabel nameLabel = new JLabel("姓名:");
        nameField = new JTextField((String) rowData[1]);
        nameField.setFont(font); // 设置文本框的字体
        panel.add(nameLabel);
        panel.add(nameField);

        JLabel genderLabel = new JLabel("性别:");
        genderField = new JTextField((String) rowData[2]);
        genderField.setFont(font); // 设置文本框的字体
        panel.add(genderLabel);
        panel.add(genderField);

        JLabel birthDateLabel = new JLabel("出生日期:");
        birthDateField = new JTextField((String) rowData[3]);
        birthDateField.setFont(font); // 设置文本框的字体
        panel.add(birthDateLabel);
        panel.add(birthDateField);

        JLabel studentIdLabel = new JLabel("学号:");
        studentIdField = new JTextField((String) rowData[4]);
        studentIdField.setFont(font); // 设置文本框的字体
        panel.add(studentIdLabel);
        panel.add(studentIdField);

        JLabel qqNumberLabel = new JLabel("QQ号:");
        qqNumberField = new JTextField((String) rowData[5]);
        qqNumberField.setFont(font); // 设置文本框的字体
        panel.add(qqNumberLabel);
        panel.add(qqNumberField);

        editButton = new JButton("修改");
        editButton.setPreferredSize(new Dimension(80, 25)); // 设置按钮大小
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取修改后的学生信息
                String name = nameField.getText();
                String gender = genderField.getText();
                String birthDate = birthDateField.getText();
                String studentId = studentIdField.getText();
                String qqNumber = qqNumberField.getText();

                // 更新表格中的数据
                int selectedRow = tableModel.getRowCount();
                for (int i = 0; i < selectedRow; i++) {
                    if (tableModel.getValueAt(i, 0).equals(rowData[0])) {
                        tableModel.setValueAt(name, i, 1);
                        tableModel.setValueAt(gender, i, 2);
                        tableModel.setValueAt(birthDate, i, 3);
                        tableModel.setValueAt(studentId, i, 4);
                        tableModel.setValueAt(qqNumber, i, 5);
                        break;
                    }
                }

                // 关闭对话框
                dispose();
                JOptionPane.showMessageDialog(EditStudentDialog.this, "修改成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        cancelButton = new JButton("取消");
        cancelButton.setPreferredSize(new Dimension(80, 25)); // 设置按钮大小
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭对话框
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 设置按钮面板布局
        buttonPanel.add(editButton);
        buttonPanel.add(cancelButton);

        // 添加面板到对话框
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}

