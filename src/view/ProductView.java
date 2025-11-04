package view;

import controller.ProductController;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductView extends JFrame {
    private final ProductController controller;
    private JTable table;
    private JTextField txtName, txtPrice, txtStock, txtId;

    public ProductView() {
        controller = new ProductController();
        setTitle("ERP - Product Management (PostgreSQL)");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        txtId = new JTextField(); txtId.setEditable(false);
        txtName = new JTextField();
        txtPrice = new JTextField();
        txtStock = new JTextField();

        formPanel.add(new JLabel("ID:"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(txtName);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(txtPrice);
        formPanel.add(new JLabel("Stock:"));
        formPanel.add(txtStock);

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        formPanel.add(btnAdd);
        formPanel.add(btnUpdate);
        formPanel.add(btnDelete);

        add(formPanel, BorderLayout.NORTH);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        refreshTable();

        //
        btnAdd.addActionListener(e -> {
            controller.addProduct(
                    txtName.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtStock.getText())
            );
            clearForm();
            refreshTable();
        });

        btnUpdate.addActionListener(e -> {
            controller.updateProduct(
                    Integer.parseInt(txtId.getText()),
                    txtName.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtStock.getText())
            );
            clearForm();
            refreshTable();
        });

        btnDelete.addActionListener(e -> {
            controller.deleteProduct(Integer.parseInt(txtId.getText()));
            clearForm();
            refreshTable();
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                txtId.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                txtName.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                txtPrice.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
                txtStock.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
            }
        });
    }

    private void refreshTable() {
        List<Product> products = controller.getProducts();
        String[] columns = {"ID", "Name", "Price", "Stock"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Product p : products) {
            model.addRow(new Object[]{p.getId(), p.getName(), p.getPrice(), p.getStock()});
        }
        table.setModel(model);
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtPrice.setText("");
        txtStock.setText("");
    }
}
