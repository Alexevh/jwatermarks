/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procedimientos;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import watermarks.Principal;

/**
 *
 * @author alex
 */
public class marcasPdf {

    public static void marcarPDF(String SRC, String DEST, String IMG, String MARK, Color COLOR, Float transparencia) {

        try {

            PdfReader reader = new PdfReader(SRC);

            int n = reader.getNumberOfPages();

            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(DEST));

            // TEXTO PARA MARCA DE AGUA
            Font f = new Font(Font.FontFamily.HELVETICA, 30);
            f.setColor(COLOR.getRed(), COLOR.getGreen(), COLOR.getBlue());
            Phrase p = new Phrase(MARK, f);

            // Imagen
            Image img = Image.getInstance(IMG);
            float w = img.getScaledWidth();
            float h = img.getScaledHeight();

            // Transparencia
            PdfGState gs1 = new PdfGState();
            gs1.setFillOpacity(transparencia);

            // Propiedades
            PdfContentByte over;
            Rectangle pagesize;

            float x, y;
            // Bucle por pagina
            for (int i = 1; i <= n; i++) {

                pagesize = reader.getPageSizeWithRotation(i);

                x = (pagesize.getLeft() + pagesize.getRight()) / 2;
                y = (pagesize.getTop() + pagesize.getBottom()) / 2;
                over = stamper.getOverContent(i);
                over.saveState();
                over.setGState(gs1);
                if (i % 2 == 1) {
                    ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 0);
                    over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));

                } //ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 0);
                else {
                    ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 0);
                    over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));
                }
                over.restoreState();
            }

            stamper.close();
            reader.close();
            JOptionPane.showMessageDialog(null, "Everything seems to be done.");

        } catch (IOException ex) {

            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);

            JOptionPane.showMessageDialog(null, "Error: You MUST select valid data in every field", "Error", JOptionPane.ERROR_MESSAGE);

        } catch (DocumentException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error: You MUST select valid data in every field", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }
}
