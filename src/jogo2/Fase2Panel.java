package jogo2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Fase2Panel extends JPanel {
    private Image fundo;
    private Image personagem;
    private Image estrela;
    private Image estrela2;
    private int personagemX, personagemY, larguraPersonagem, alturaPersonagem;
    private int estrelaX, estrelaY, larguraEstrela, alturaEstrela;
    private int estrelaX2, estrelaY2, larguraEstrela2, alturaEstrela2;
    private int vidas;
    private Timer timer;
    private int tempoRestante;
    private boolean perguntaFeita;
    private boolean perguntaFeita2;
    private boolean estrelaVisivel;
    private boolean estrela2Visivel;
    private JButton btnContinuar;
    private Container container;

    public Fase2Panel(Container container) {
        this.container = container;
        this.personagemX = 210;
        this.personagemY = 400;
        this.larguraPersonagem = 40;
        this.alturaPersonagem = 50;
        this.vidas = 3;
        this.tempoRestante = 600;
        this.perguntaFeita = false;
        this.perguntaFeita2 = false;
        this.estrelaVisivel = true;
        this.estrela2Visivel = true;

        this.estrelaX = 150;
        this.estrelaY = 300;
        this.larguraEstrela = 150;
        this.alturaEstrela = 150;

        this.estrelaX2 = 524;
        this.estrelaY2 = 300;
        this.larguraEstrela2 = 150;
        this.alturaEstrela2 = 150;

        carregarImagens();
        configurarTemporizador();
        configurarTeclas();
        configurarBotaoContinuar();
        setFocusable(true);
        requestFocusInWindow();
        setPreferredSize(new Dimension(1028, 600));
        setBackground(Color.CYAN);
    }

    private void carregarImagens() {
        try {
            fundo = new ImageIcon(getClass().getResource("/paleta/model/m2.png")).getImage();
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
                JOptionPane.showMessageDialog(Fase2Panel.this, "Tempo esgotado!");
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

    private void configurarBotaoContinuar() {
        btnContinuar = new JButton("ultima fase");
        btnContinuar.setBounds(414, 500, 160, 40); // Movido 1 cm para cima
        btnContinuar.setBackground(new Color(102, 0, 204)); // Roxo escuro
        btnContinuar.setForeground(Color.WHITE);
        btnContinuar.setFont(new Font("Arial", Font.BOLD, 16));
        btnContinuar.setVisible(false); // Só aparece quando o jogador acerta as duas perguntas e chega ao fim da tela
        btnContinuar.addActionListener(e -> container.trocarParaFase3());
        add(btnContinuar);
    }

    private void verificarColisaoComEstrela() {
        if (estrelaVisivel && !perguntaFeita && personagemX + larguraPersonagem >= estrelaX && personagemX <= estrelaX + larguraEstrela &&
            personagemY + alturaPersonagem >= estrelaY && personagemY <= estrelaY + alturaEstrela) {
            exibirPergunta();
        }
    }

    private void verificarColisaoComEstrela2() {
        if (estrela2Visivel && !perguntaFeita2 && personagemX + larguraPersonagem >= estrelaX2 && personagemX <= estrelaX2 + larguraEstrela2 &&
            personagemY + alturaPersonagem >= estrelaY2 && personagemY <= estrelaY2 + alturaEstrela2) {
            exibirPergunta2();
        }
    }

    private void verificarFimDeFase() {
        if (!estrelaVisivel && !estrela2Visivel) {
            btnContinuar.setVisible(true); // Só exibe o botão se as duas estrelas foram coletadas
        }
    }

    private void exibirPergunta() {
        perguntaFeita = true;
        String resposta = JOptionPane.showInputDialog(this, "Qual o plural de gato?");
        if (resposta != null && resposta.equals("gatos")) {
            JOptionPane.showMessageDialog(this, "Resposta correta!");
            estrelaVisivel = false;
        } else {
            JOptionPane.showMessageDialog(this, "Resposta errada! Você perdeu uma vida.");
            vidas--;
            if (vidas == 0) gameOver();
            else perguntaFeita = false;
        }
    }

    private void exibirPergunta2() {
        perguntaFeita2 = true;
        String resposta = JOptionPane.showInputDialog(this, "Quantos estados tem no Brasil?");
        if (resposta != null && resposta.equals("26")) {
            JOptionPane.showMessageDialog(this, "Resposta correta!");
            estrela2Visivel = false;
        } else {
            JOptionPane.showMessageDialog(this, "Resposta errada! Você perdeu uma vida.");
            vidas--;
            if (vidas == 0) gameOver();
            else perguntaFeita2 = false;
        }
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
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Vidas: " + vidas, 10, 30);
        g.drawString("Tempo: " + (tempoRestante / 60) + ":" + (tempoRestante % 60), getWidth() - 150, 30);
    }
}
