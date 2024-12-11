package Interface;

import Repository.AlunoRepo;
import Repository.CursoRepo;
import Repository.EnfaseRepo;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final EnfaseRepo enfaseRepo;
    private final CursoRepo cursoRepo;
    private final AlunoRepo alunoRepo;

    public MainFrame() {
        setTitle("CRUD Alunos");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        enfaseRepo = new EnfaseRepo();
        cursoRepo = new CursoRepo();
        alunoRepo = new AlunoRepo(enfaseRepo, cursoRepo);

        JLabel titleLabel = new JLabel("CRUD Alunos");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnGerenciarAlunos = new JButton("Gerenciar Alunos");
        JButton btnGerenciarCursos = new JButton("Gerenciar Cursos");
        JButton btnGerenciarEnfases = new JButton("Gerenciar Ênfases");

        btnGerenciarAlunos.setPreferredSize(new Dimension(200, 50));
        btnGerenciarCursos.setPreferredSize(new Dimension(200, 50));
        btnGerenciarEnfases.setPreferredSize(new Dimension(200, 50));

        btnGerenciarAlunos.addActionListener(e -> new AlunoCrudFrame(alunoRepo, enfaseRepo, cursoRepo).setVisible(true));
        btnGerenciarCursos.addActionListener(e -> new CursoCrudFrame(cursoRepo).setVisible(true));
        btnGerenciarEnfases.addActionListener(e -> new EnfaseCrudFrame(enfaseRepo).setVisible(true));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(btnGerenciarAlunos, gbc);

        gbc.gridx = 1;
        add(btnGerenciarCursos, gbc);

        gbc.gridx = 2;
        add(btnGerenciarEnfases, gbc);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                alunoRepo.saveToCSV();
                cursoRepo.saveToCSV();
                enfaseRepo.saveToCSV();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}