package jogo2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinalPanel extends JPanel {
    private Image fundo;
    private JButton botaoFecharJogo;
    private Container container;

    public FinalPanel(Container container) {
        this.container = container;
        carregarImagens();
        configurarBotao();
        setLayout(null); // Define o layout como nulo
    }

    private void carregarImagens() {
        try {
            fundo = new ImageIcon(getClass().getResource("/paleta/model/bg_main_darkDracula01_col.png")).getImage();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar imagens!", "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void configurarBotao() {
        botaoFecharJogo = new JButton("Fechar Mapa");
        botaoFecharJogo.setBackground(new Color(128, 0, 128)); // Roxo
        botaoFecharJogo.setForeground(Color.WHITE);
        botaoFecharJogo.setFont(new Font("Arial", Font.BOLD, 16));

        int larguraBotao = 150;
        int alturaBotao = 50;
        int x = (getWidth() - larguraBotao) / 2;
        int y = getHeight() - alturaBotao - 40;

        botaoFecharJogo.setBounds(x, y, larguraBotao, alturaBotao);
        this.add(botaoFecharJogo);

        botaoFecharJogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Fecha o jogo
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graficos = (Graphics2D) g;

        if (fundo != null) {
            graficos.drawImage(fundo, 0, 0, getWidth(), getHeight(), this);
        }

        // Atualiza a posição do botão ao redimensionar o painel
        int larguraBotao = 150;
        int alturaBotao = 50;
        int x = (getWidth() - larguraBotao) / 2;
        int y = getHeight() - alturaBotao - 40;
        botaoFecharJogo.setBounds(x, y, larguraBotao, alturaBotao);
    }
}
