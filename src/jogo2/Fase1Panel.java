package jogo2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Fase1Panel extends JPanel {
    private Image fundo, personagem, estrela, estrela2;
    private int personagemX, personagemY, larguraPersonagem, alturaPersonagem;
    private int estrelaX, estrelaY, larguraEstrela, alturaEstrela;
    private int estrelaX2, estrelaY2, larguraEstrela2, alturaEstrela2;
    private int vidas, tempoRestante;
    private boolean perguntaFeita, perguntaFeita2, faseCompleta;
    private Timer timer;
    private JButton btnContinuar;
    private Container container;
    private boolean estrelaVisivel, estrela2Visivel;

    public Fase1Panel(Container container) {
        this.container = container;
        this.personagemX = 50;
        this.personagemY = 460;
        this.larguraPersonagem = 40;
        this.alturaPersonagem = 50;
        this.vidas = 3;
        this.tempoRestante = 600;
        this.perguntaFeita = false;
        this.perguntaFeita2 = false;
        this.faseCompleta = false;

        this.estrelaX = 305;
        this.estrelaY = 400;
        this.larguraEstrela = 150;
        this.alturaEstrela = 150;

        this.estrelaX2 = estrelaX + 400 - 76;
        this.estrelaY2 = 400;
        this.larguraEstrela2 = 150;
        this.alturaEstrela2 = 150;

        this.estrelaVisivel = true;
        this.estrela2Visivel = true;

        carregarImagens();
        configurarTemporizador();
        configurarTeclas();
        configurarBotao();

        setFocusable(true);
        requestFocusInWindow();
        setPreferredSize(new Dimension(1028, 600));
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
    }

    private void carregarImagens() {
        try {
            fundo = new ImageIcon(getClass().getResource("/paleta/model/m1.png")).getImage();
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
                JOptionPane.showMessageDialog(Fase1Panel.this, "Tempo esgotado!");
            }
            repaint();
        });
        timer.start();
    }

    private void configurarTeclas() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    personagemX = Math.max(personagemX - 10, 0);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    personagemX = Math.min(personagemX + 10, getWidth() - larguraPersonagem);
                }

                if (personagemX >= getWidth() - larguraPersonagem - 10) {
                    if (perguntaFeita && perguntaFeita2) {
                        btnContinuar.setVisible(true);
                    }
                }

                verificarColisoes();
                verificarFimDeFase();
                repaint();
            }
        });
    }

    private void configurarBotao() {
        btnContinuar = new JButton("Fase 2");
        btnContinuar.setBounds(414, 500, 200, 50);
        btnContinuar.setBackground(new Color(75, 0, 130));
        btnContinuar.setForeground(Color.WHITE);
        btnContinuar.setFont(new Font("Arial", Font.BOLD, 16));
        btnContinuar.setVisible(false);
        btnContinuar.addActionListener(e -> container.trocarParaFase2());
        add(btnContinuar);
    }

    private void verificarColisoes() {
        if (!perguntaFeita && colisao(personagemX, personagemY, estrelaX, estrelaY, larguraEstrela, alturaEstrela)) {
            exibirPergunta("Quanto é 4 X 4?", "16", () -> {
                perguntaFeita = true;
                estrelaVisivel = false;
            });
        }
        if (!perguntaFeita2 && colisao(personagemX, personagemY, estrelaX2, estrelaY2, larguraEstrela2, alturaEstrela2)) {
            exibirPergunta("Quanto é 3 + 5?", "8", () -> {
                perguntaFeita2 = true;
                estrela2Visivel = false;
            });
        }
    }

    private void verificarFimDeFase() {
        if (!estrelaVisivel && !estrela2Visivel) {
            btnContinuar.setVisible(true);
        }
    }

    private void exibirPergunta(String pergunta, String respostaCorreta, Runnable acaoCorreta) {
        String resposta = JOptionPane.showInputDialog(this, pergunta);
        if (resposta != null && resposta.equalsIgnoreCase(respostaCorreta)) {
            JOptionPane.showMessageDialog(this, "Resposta correta!");
            personagemX += 100;
            acaoCorreta.run();
        } else {
            JOptionPane.showMessageDialog(this, "Resposta errada! Você perdeu uma vida.");
            if (--vidas == 0) gameOver();
        }
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over!");
        System.exit(0);
    }

    private boolean colisao(int x1, int y1, int x2, int y2, int largura2, int altura2) {
        return x1 + larguraPersonagem > x2 && x1 < x2 + largura2 &&
               y1 + alturaPersonagem > y2 && y1 < y2 + altura2;
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

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Vidas: " + vidas, 10, 30);
        g.drawString(String.format("Tempo: %02d:%02d", tempoRestante / 60, tempoRestante % 60), getWidth() - 150, 30);
    }
}
