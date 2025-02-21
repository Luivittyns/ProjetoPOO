package jogo2;

import javax.swing.*;
import java.awt.*;

public class Container extends JFrame {

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Fase1Panel fase1Panel;
    private Fase2Panel fase2Panel;
    private Fase3Panel fase3Panel;
    private FinalPanel finalPanel;

    public Container() {
        setTitle("Meu Jogo");
        setSize(1028, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Inicializa o CardLayout e o mainPanel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Inicializa os painéis
        Iniciar iniciarPanel = new Iniciar(this);
        Historia historiaPanel = new Historia(this);
        fase1Panel = new Fase1Panel(this);
        fase2Panel = new Fase2Panel(this);
        fase3Panel = new Fase3Panel(this);
        finalPanel = new FinalPanel(this);

        // Adiciona os painéis ao layout depois de inicializados
        mainPanel.add(iniciarPanel, "Iniciar");
        mainPanel.add(historiaPanel, "Historia");
        mainPanel.add(fase1Panel, "Fase1");
        mainPanel.add(fase2Panel, "Fase2");
        mainPanel.add(fase3Panel, "Fase3");
        mainPanel.add(finalPanel, "Fase Final");

        // Adiciona o mainPanel ao JFrame
        add(mainPanel);

        // Torna a interface visível
        setVisible(true);
    }

    // Métodos para trocar entre as fases
    public void trocarParaHistoria() {
        cardLayout.show(mainPanel, "Historia");
    }

    public void trocarParaFase1() {
        cardLayout.show(mainPanel, "Fase1");
        fase1Panel.requestFocusInWindow();
    }

    public void trocarParaFase2() {
        cardLayout.show(mainPanel, "Fase2");
        fase2Panel.requestFocusInWindow();
    }

    public void trocarParaFase3() {
        cardLayout.show(mainPanel, "Fase3");
        fase3Panel.requestFocusInWindow();
    }
    
    public void trocarParaFinalPanel() {
        cardLayout.show(mainPanel, "Fase Final");
        finalPanel.requestFocusInWindow(); // Corrigido para o painel correto
    }

    // Método para acessar o mainPanel (caso precise em outras partes do código)
    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Container::new);
    }
}
