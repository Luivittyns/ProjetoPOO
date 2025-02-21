package jogo2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Historia extends JPanel {
    private Image personagem;
    private Image fundo;
    private JButton botaoFase1;
    private String textoCompleto;
    private String textoAtual = "";
    private int indiceTexto = 0;
    private Timer timer;
    private Container container;
    private Font fontePixelada;

    public Historia(Container container) {
        this.container = container;
        carregarImagens();
        carregarFonte();
        iniciarTextoAnimado();
    }

    private void carregarImagens() {
        fundo = new ImageIcon(getClass().getResource("/paleta/model/tela.png")).getImage();
        if (fundo == null) {
            System.err.println("Erro: Imagem de fundo não encontrada!");
        }

        personagem = new ImageIcon(getClass().getResource("/paleta/model/p1.png")).getImage();
        if (personagem == null) {
            System.err.println("Erro: Imagem do personagem não encontrada!");
        }
    }

    private void carregarFonte() {
        try {
            fontePixelada = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/paleta/model/PressStart2P.ttf")).deriveFont(20f);
        } catch (Exception e) {
            System.err.println("Erro ao carregar a fonte pixelada. Usando fonte padrão.");
            fontePixelada = new Font("Serif", Font.PLAIN, 20);
        }
    }

    private void iniciarTextoAnimado() {
        textoCompleto = "Olá, jogador(a). Eu sou o seu guardião, "
                + "estou aqui para te ajudar a reconstituir a escola. "
                + "Você precisa pegar todas as estrelas, para isso tem que "
                + "acertar todas as perguntas antes que o tempo acabe."
                + " Boa Sorte!";

        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indiceTexto < textoCompleto.length()) {
                    textoAtual += textoCompleto.charAt(indiceTexto);
                    indiceTexto++;
                    repaint();
                } else {
                    timer.stop();
                    configurarBotao();
                }
            }
        });

        timer.start();
    }

    private void configurarBotao() {
        botaoFase1 = new JButton("Fase 1");
        botaoFase1.setBackground(Color.RED);
        botaoFase1.setForeground(Color.WHITE);

        int larguraBotao = 150;
        int alturaBotao = 50;
        int x = (getWidth() - larguraBotao) / 2;
        int y = getHeight() - alturaBotao - 40;

        botaoFase1.setBounds(x, y, larguraBotao, alturaBotao);
        this.setLayout(null);
        this.add(botaoFase1);

        botaoFase1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.trocarParaFase1();
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

        if (personagem != null) {
            int xPersonagem = 100;
            int yPersonagem = getHeight() - 450;
            graficos.drawImage(personagem, xPersonagem, yPersonagem, this);
        }

        graficos.setFont(fontePixelada);
        graficos.setColor(Color.RED);

        int xTexto = 300;
        int yTexto = 150;
        int larguraMaxima = getWidth() / 2 - 100;

        String[] palavras = textoAtual.split(" ");
        StringBuilder linhaAtual = new StringBuilder();
        int alturaLinha = graficos.getFontMetrics().getHeight();

        for (String palavra : palavras) {
            if (graficos.getFontMetrics().stringWidth(linhaAtual + palavra) > larguraMaxima) {
                graficos.drawString(linhaAtual.toString(), xTexto, yTexto);
                yTexto += alturaLinha;
                linhaAtual = new StringBuilder(palavra + " ");
            } else {
                linhaAtual.append(palavra).append(" ");
            }
        }
        graficos.drawString(linhaAtual.toString(), xTexto, yTexto);
    }
}