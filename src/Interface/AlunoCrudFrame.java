package Interface;

import Repository.AlunoRepo;
import Repository.CursoRepo;
import Repository.EnfaseRepo;
import models.Aluno;
import models.Curso;
import models.Enfase;
import models.Turno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlunoCrudFrame extends JFrame {
    private JTextField txtMatricula;
    private JTextField txtNome;
    private JComboBox<Turno> cbTurno;
    private JTextField txtPeriodo;
    private JComboBox<Enfase> cbEnfase;
    private JComboBox<Curso> cbCurso;
    private JTable tableAlunos;
    private DefaultTableModel tableModel;
    private AlunoRepo alunoRepo;
    private EnfaseRepo enfaseRepo;
    private CursoRepo cursoRepo;

    public AlunoCrudFrame(AlunoRepo alunoRepo, EnfaseRepo enfaseRepo, CursoRepo cursoRepo) {
        this.alunoRepo = alunoRepo;
        this.enfaseRepo = enfaseRepo;
        this.cursoRepo = cursoRepo;

        setTitle("Gerenciar Alunos");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Dados do Aluno"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblMatricula = new JLabel("Matrícula:");
        txtMatricula = new JTextField(15);
        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField(20);
        JLabel lblTurno = new JLabel("Turno:");
        cbTurno = new JComboBox<>(Turno.values());
        JLabel lblPeriodo = new JLabel("Período:");
        txtPeriodo = new JTextField(10);
        JLabel lblEnfase = new JLabel("Ênfase:");
        cbEnfase = new JComboBox<>();
        JLabel lblCurso = new JLabel("Curso:");
        cbCurso = new JComboBox<>();

        enfaseRepo.getAllEnfases().values().forEach(cbEnfase::addItem);
        cursoRepo.getAllCursos().values().forEach(cbCurso::addItem);

        JButton btnIncluir = new JButton("Incluir");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");

        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(lblMatricula, gbc);
        gbc.gridx = 1;
        topPanel.add(txtMatricula, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        topPanel.add(lblNome, gbc);
        gbc.gridx = 1;
        topPanel.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        topPanel.add(lblTurno, gbc);
        gbc.gridx = 1;
        topPanel.add(cbTurno, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        topPanel.add(lblPeriodo, gbc);
        gbc.gridx = 1;
        topPanel.add(txtPeriodo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        topPanel.add(lblEnfase, gbc);
        gbc.gridx = 1;
        topPanel.add(cbEnfase, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        topPanel.add(lblCurso, gbc);
        gbc.gridx = 1;
        topPanel.add(cbCurso, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        topPanel.add(btnIncluir, gbc);
        gbc.gridy = 1;
        topPanel.add(btnAlterar, gbc);
        gbc.gridy = 2;
        topPanel.add(btnExcluir, gbc);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Lista de Alunos"));

        tableModel = new DefaultTableModel(new String[]{"Matrícula", "Nome", "Turno", "Período", "Ênfase", "Curso"}, 0);
        tableAlunos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableAlunos);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);

        btnIncluir.addActionListener(e -> {
            String matricula = txtMatricula.getText().trim();
            String nome = txtNome.getText().trim();
            Turno turno = (Turno) cbTurno.getSelectedItem();
            String periodo = txtPeriodo.getText().trim();
            Enfase enfase = (Enfase) cbEnfase.getSelectedItem();
            Curso curso = (Curso) cbCurso.getSelectedItem();

            if (!matricula.isEmpty() && !nome.isEmpty() && turno != null && enfase != null && curso != null) {
                Aluno aluno = new Aluno(matricula, nome, turno, periodo, enfase, curso);
                alunoRepo.addAluno(aluno);
                updateTable();
                clearFields();
            }
        });

        btnAlterar.addActionListener(e -> {
            String matricula = txtMatricula.getText().trim();
            String nome = txtNome.getText().trim();
            Turno turno = (Turno) cbTurno.getSelectedItem();
            String periodo = txtPeriodo.getText().trim();
            Enfase enfase = (Enfase) cbEnfase.getSelectedItem();
            Curso curso = (Curso) cbCurso.getSelectedItem();

            if (!matricula.isEmpty() && !nome.isEmpty() && turno != null && enfase != null && curso != null) {
                Aluno aluno = new Aluno(matricula, nome, turno, periodo, enfase, curso);
                alunoRepo.updateAluno(matricula, aluno);
                updateTable();
                clearFields();
            }
        });

        btnExcluir.addActionListener(e -> {
            String matricula = txtMatricula.getText().trim();
            if (!matricula.isEmpty()) {
                alunoRepo.removeAluno(matricula);
                updateTable();
                clearFields();
            }
        });

        tableAlunos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tableAlunos.getSelectedRow();
                if (selectedRow >= 0) {
                    txtMatricula.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtNome.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    cbTurno.setSelectedItem(Turno.valueOf(tableModel.getValueAt(selectedRow, 2).toString()));
                    txtPeriodo.setText(tableModel.getValueAt(selectedRow, 3).toString());

                    // Atualiza os JComboBox de Ênfase e Curso
                    String enfaseNome = tableModel.getValueAt(selectedRow, 4).toString();
                    for (int i = 0; i < cbEnfase.getItemCount(); i++) {
                        if (cbEnfase.getItemAt(i).getNome().equals(enfaseNome)) {
                            cbEnfase.setSelectedIndex(i);
                            break;
                        }
                    }

                    String cursoNome = tableModel.getValueAt(selectedRow, 5).toString();
                    for (int i = 0; i < cbCurso.getItemCount(); i++) {
                        if (cbCurso.getItemAt(i).getNome().equals(cursoNome)) {
                            cbCurso.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });

        updateTable();
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        alunoRepo.getAll().values().forEach(aluno -> tableModel.addRow(new Object[]{
                aluno.getMatricula(),
                aluno.getNome(),
                aluno.getTurno(),
                aluno.getPeriodo(),
                aluno.getEnfase().getNome(),
                aluno.getCurso().getNome()
        }));
    }

    private void clearFields() {
        txtMatricula.setText("");
        txtNome.setText("");
        txtPeriodo.setText("");
        cbTurno.setSelectedIndex(0);
        cbEnfase.setSelectedIndex(0);
        cbCurso.setSelectedIndex(0);
    }
}