package jogo2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Fase3Panel extends JPanel {
    private Image fundo, personagem, estrela, estrela2;
    private int personagemX, personagemY, larguraPersonagem, alturaPersonagem;
    private int estrelaX, estrelaY, larguraEstrela, alturaEstrela;
    private int estrelaX2, estrelaY2, larguraEstrela2, alturaEstrela2;
    private int vidas;
    private Timer timer;
    private int tempoRestante;
    private boolean estrelaVisivel, estrela2Visivel;
    private JButton btnConcluirJogo, btnVerMapa;
    private Container container;

    public Fase3Panel(Container container) {
        this.container = container;
        this.personagemX = 10;
        this.personagemY = 310;
        this.larguraPersonagem = 40;
        this.alturaPersonagem = 50;
        this.vidas = 3;
        this.tempoRestante = 600;
        this.estrelaVisivel = true;
        this.estrela2Visivel = true;

        this.estrelaX = 100;
        this.estrelaY = 210;
        this.larguraEstrela = 150;
        this.alturaEstrela = 150;

        this.estrelaX2 = estrelaX + 400;
        this.estrelaY2 = 210;
        this.larguraEstrela2 = 150;
        this.alturaEstrela2 = 150;

        carregarImagens();
        configurarTemporizador();
        configurarTeclas();
        configurarBotoes();

        setFocusable(true);
        requestFocusInWindow();
        setPreferredSize(new Dimension(1028, 600));
        setBackground(Color.CYAN);
        setLayout(null);
    }

    private void carregarImagens() {
        try {
            fundo = new ImageIcon(getClass().getResource("/paleta/model/m7.jpg")).getImage();
            personagem = new ImageIcon(getClass().getResource("/paleta/model/p6.png")).getImage();
            estrela = new ImageIcon(getClass().getResource("/paleta/model/251338.gif")).getImage();
            estrela2 = new ImageIcon(getClass().getResource("/paleta/model/251338.gif")).getImage();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar imagens!", "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void configurarTemporizador() {
        timer = new Timer(1000, e -> {
            tempoRestante--;
            if (tempoRestante <= 0) {
                timer.stop();
                JOptionPane.showMessageDialog(Fase3Panel.this, "Tempo esgotado!");
            }
            repaint();
        });
        timer.start();
    }

    private void configurarTeclas() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_A) {
                    personagemX -= 10;
                    if (personagemX < 0) personagemX = 0;
                } else if (code == KeyEvent.VK_D) {
                    personagemX += 10;
                    if (personagemX + larguraPersonagem > getWidth()) personagemX = getWidth() - larguraPersonagem;
                }
                verificarColisaoComEstrela();
                verificarColisaoComEstrela2();
                verificarFimDeFase();
                repaint();
            }
        });
    }

    private void configurarBotoes() {
        btnConcluirJogo = new JButton("Concluir o Jogo");
        btnConcluirJogo.setBounds(200, 500, 160, 40);
        btnConcluirJogo.setBackground(new Color(128, 0, 128)); // Roxo
        btnConcluirJogo.setForeground(Color.WHITE);
        btnConcluirJogo.setFont(new Font("Arial", Font.BOLD, 16));
        btnConcluirJogo.addActionListener(e -> System.exit(0));
        btnConcluirJogo.setVisible(false); // Começa invisível
        add(btnConcluirJogo);

        btnVerMapa = new JButton("Ver Mapa");
        btnVerMapa.setBounds(600, 500, 160, 40);
        btnVerMapa.setBackground(new Color(128, 0, 128)); // Roxo
        btnVerMapa.setForeground(Color.WHITE);
        btnVerMapa.setFont(new Font("Arial", Font.BOLD, 16));
        btnVerMapa.addActionListener(e -> container.trocarParaFinalPanel());
        btnVerMapa.setVisible(false); // Começa invisível
        add(btnVerMapa);
    }

    private void verificarFimDeFase() {
        if (!estrelaVisivel && !estrela2Visivel) {
            btnConcluirJogo.setVisible(true);
            btnVerMapa.setVisible(true);
        }
    }

    private void verificarColisaoComEstrela() {
        if (estrelaVisivel) {
            Rectangle personagemRect = new Rectangle(personagemX, personagemY, larguraPersonagem, alturaPersonagem);
            Rectangle estrelaRect = new Rectangle(estrelaX, estrelaY, larguraEstrela, alturaEstrela);
            if (personagemRect.intersects(estrelaRect)) {
                while (!exibirPergunta("Quem descobriu o Brasil?", "Pedro Alvares Cabral")) {
                    perderVida();
                    if (vidas == 0) return;
                }
                estrelaVisivel = false;
            }
        }
    }

    private void verificarColisaoComEstrela2() {
        if (estrela2Visivel) {
            Rectangle personagemRect = new Rectangle(personagemX, personagemY, larguraPersonagem, alturaPersonagem);
            Rectangle estrela2Rect = new Rectangle(estrelaX2, estrelaY2, larguraEstrela2, alturaEstrela2);
            if (personagemRect.intersects(estrela2Rect)) {
                while (!exibirPergunta("Quem é o autor da pintura Céu Estrelado?", "Vincent van Gogh")) {
                    perderVida();
                    if (vidas == 0) return;
                }
                estrela2Visivel = false;
            }
        }
    }

    private boolean exibirPergunta(String pergunta, String respostaCorreta) {
        String resposta = JOptionPane.showInputDialog(this, pergunta);
        if (resposta != null && resposta.equalsIgnoreCase(respostaCorreta)) {
            JOptionPane.showMessageDialog(this, "Resposta correta!");
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Resposta errada! Tente novamente.");
            return false;
        }
    }

    private void perderVida() {
        JOptionPane.showMessageDialog(this, "Você perdeu uma vida!");
        vidas--;
        if (vidas == 0) gameOver();
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over!");
        System.exit(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fundo, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(personagem, personagemX, personagemY, larguraPersonagem, alturaPersonagem, this);
        if (estrelaVisivel) {
            g.drawImage(estrela, estrelaX, estrelaY, larguraEstrela, alturaEstrela, this);
        }
        if (estrela2Visivel) {
            g.drawImage(estrela2, estrelaX2, estrelaY2, larguraEstrela2, alturaEstrela2, this);
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Vidas: " + vidas, 20, 60);
    }
}
