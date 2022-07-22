import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradoraDeFigurinhas {

        public void cria(InputStream inputStream, String nomeArquivo, String nota) throws Exception {

                // Obtem o texto e a imagem que será sobreposta no sticker
                String[] dadosSticker = textoImagemPorNota(nota);

                // leitura das imagens
                BufferedImage imagemOriginal = resizeImage(ImageIO.read(inputStream));
                BufferedImage sticker = ImageIO.read(new File(dadosSticker[1]));

                // cria nova imagem com transparencia e com tamanho novo
                int largura = imagemOriginal.getWidth();
                int altura = imagemOriginal.getHeight();
                int novaAltura = altura + 150;
                BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

                // copiar a imagem original para a nova imagem (em memória)
                Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
                graphics.drawImage(imagemOriginal, 0, 0, null);
                graphics.drawImage(sticker, largura - sticker.getWidth(), novaAltura - sticker.getHeight(),
                                null);

                // configurar fonte
                Font font = new Font("Comic Sans MS", Font.BOLD, 75);
                graphics.setFont(font);

                TextLayout textLayout = new TextLayout(dadosSticker[0], graphics.getFont(), graphics.getFontRenderContext());

                // Definindo o posicionamento do texto
                double alturaFrase = textLayout.getBounds().getHeight();
                double larguraFrase = textLayout.getBounds().getWidth();
                double posicaoFraseX = ((largura - sticker.getWidth()) - larguraFrase) / 2;
                double posicaoFraseY = (novaAltura - 75) + (alturaFrase / 2);

                // escrever uma frase na nova imagem com contorno

                Shape shape = textLayout.getOutline(null);

                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
                                RenderingHints.VALUE_RENDER_QUALITY);

                graphics.setStroke(new BasicStroke(50 * 0.2f));
                graphics.translate(posicaoFraseX, posicaoFraseY);
                graphics.setColor(Color.BLACK);
                graphics.draw(shape);

                graphics.setColor(Color.YELLOW);
                graphics.fill(shape);

                // escrever a nova imagem em um arquvivo e cria o novo diretório caso não
                // exista.
                File figurinha = new File("saida/" + nomeArquivo);
                figurinha.getParentFile().mkdirs();
                ImageIO.write(novaImagem, "png", figurinha);
        }

        private BufferedImage resizeImage(BufferedImage imagem) {
                if (imagem.getHeight() <= 1000)
                        return imagem;

                double altura = imagem.getHeight();
                double largura = imagem.getWidth();

                double proporcao = largura / altura;
                altura = 1000.0;
                largura = (proporcao * altura);

                BufferedImage novaImagem = new BufferedImage((int) largura, (int) altura, imagem.getType());

                Graphics2D g2d = novaImagem.createGraphics();
                g2d.drawImage(imagem, 0, 0, (int) largura, (int) altura, null);
                g2d.dispose();

                return novaImagem;
        }

        private String[] textoImagemPorNota(String nota) {
                String[] retorno = new String[2];

                if (Double.parseDouble(nota) > 8) {
                        retorno[0] = "TOPZERA";
                        retorno[1] = "self-sticker/chuck.png";
                        return retorno;
                }

                if (Double.parseDouble(nota) >= 6) {
                        retorno[0] = "+ OU -";
                        retorno[1] = "self-sticker/poderoso.png";
                        return retorno;
                }

                retorno[0] = "VISHHH";
                retorno[1] = "self-sticker/vish.png";
                return retorno;
        }
}
