package Interface;

import Repository.EnfaseRepo;
import models.Enfase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EnfaseCrudFrame extends JFrame {
    private JTextField txtId;
    private JTextField txtNome;
    private JTable tableEnfases;
    private DefaultTableModel tableModel;
    private EnfaseRepo enfaseRepo;

    public EnfaseCrudFrame(EnfaseRepo enfaseRepo) {
        this.enfaseRepo = enfaseRepo;
        setTitle("Gerenciar Ênfases");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Dados da Ênfase"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField(10);
        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField(20);

        JButton btnIncluir = new JButton("Incluir");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");
        //JButton btnBuscar = new JButton("Buscar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(lblId, gbc);

        gbc.gridx = 1;
        topPanel.add(txtId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        topPanel.add(lblNome, gbc);

        gbc.gridx = 1;
        topPanel.add(txtNome, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        topPanel.add(btnIncluir, gbc);

        gbc.gridy = 1;
        topPanel.add(btnAlterar, gbc);

        gbc.gridy = 2;
        topPanel.add(btnExcluir, gbc);

        //gbc.gridy = 3;
        //topPanel.add(btnBuscar, gbc);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Lista de Ênfases"));

        tableModel = new DefaultTableModel(new String[]{"ID", "Nome"}, 0);
        tableEnfases = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableEnfases);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);

        btnIncluir.addActionListener(e -> {
            String id = txtId.getText().trim();
            String nome = txtNome.getText().trim();
            if (!id.isEmpty() && !nome.isEmpty()) {
                Enfase enfase = new Enfase(id, nome);
                enfaseRepo.addEnfase(enfase);
                updateTable();
                clearFields();
            }
        });

        btnAlterar.addActionListener(e -> {
            String id = txtId.getText().trim();
            String nome = txtNome.getText().trim();
            if (!id.isEmpty() && !nome.isEmpty()) {
                Enfase enfase = new Enfase(id, nome);
                enfaseRepo.updateEnfase(id, enfase);
                updateTable();
                clearFields();
            }
        });

        btnExcluir.addActionListener(e -> {
            String id = txtId.getText().trim();
            if (!id.isEmpty()) {
                enfaseRepo.removeEnfase(id);
                updateTable();
                clearFields();
            }
        });

        tableEnfases.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tableEnfases.getSelectedRow();
                if (selectedRow >= 0) {
                    txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtNome.setText(tableModel.getValueAt(selectedRow, 1).toString());
                }
            }
        });

        updateTable();
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        enfaseRepo.getAllEnfases().values().forEach(enfase -> tableModel.addRow(new Object[]{
                enfase.getId(),
                enfase.getNome()
        }));
    }

    private void clearFields() {
        txtId.setText("");
        txtNome.setText("");
    }
}