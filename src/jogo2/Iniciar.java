package jogo2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Iniciar extends JPanel {
    private Image fundo;
    private JButton botaoIniciar;
    private Container container;

    public Iniciar(Container container) {
        this.container = container;
        carregarImagens();
        configurarBotao();
    }

    private void carregarImagens() {
        fundo = new ImageIcon(getClass().getResource("/paleta/model/capa.png")).getImage();
        if (fundo == null) {
            System.out.println("Imagem de fundo não encontrada!");
        }
    }

    private void configurarBotao() {
        setLayout(null);
        botaoIniciar = new JButton("Jogar");
        botaoIniciar.setBackground(Color.RED);
        botaoIniciar.setForeground(Color.WHITE);

        int larguraBotao = 150;
        int alturaBotao = 50;
        botaoIniciar.setBounds(440, 500, larguraBotao, alturaBotao); // Centralizado

        botaoIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.trocarParaHistoria();
            }
        });

        add(botaoIniciar);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fundo, 0, 0, getWidth(), getHeight(), this);
    }
}