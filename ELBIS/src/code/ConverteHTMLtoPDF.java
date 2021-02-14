/**
 * @author Mohammed Ali Anis
 */
package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import com.itextpdf.html2pdf.HtmlConverter;

public class ConverteHTMLtoPDF {

	/**
	 * @use creating file with the attributes and
	 * converting HTML from texteditor to PDF file
	 * @param title
	 * @param content
	 * @param subsection
	 * @param from
	 * @throws IOException
	 */
	public void converte(String title, String content, String subsection, String from) throws IOException {

		String headTitle = "<h1 style=\"text-align: center;\"><span style=\"color: #800000;\"><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/DEU_Elkenroth_COA.svg/800px-DEU_Elkenroth_COA.svg.png?1609379690021\" alt=\"logo\" width=\"57\" height=\"60\" /></span></h1>\n"
				+ "<h1 style=\"text-align: center;\"><span style=\"color: #800000;\">" + title + "</span></h1>\n"
				+ "<h3 style=\"text-align: left;\"><span style=\"color: #800000;\">" + subsection
				+ "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"
				+ from + "</span></h3>\n" + "<hr />\n" + "<p>&nbsp;</p>";
		try {

			String path = "src/pdf";

			File file = new File(path);
			String absolutePath = file.getAbsolutePath();

			File theDir = new File(absolutePath + "/" + subsection);
			if (!theDir.exists()) {
				theDir.mkdirs();
			}
			HtmlConverter.convertToPdf(headTitle + "\n " + content,
					new FileOutputStream(theDir + "/" + title + ".pdf"));
			System.out.println("PDF file has been created !");

			PDDocument doc = PDDocument.load(new File(theDir + "/" + title + ".pdf"));
			int count = doc.getNumberOfPages();
			System.out.println("Before PDF Page # " + count);
			PDDocument pd = null;
			if (count > 1) {

				Splitter splitter = new Splitter();
				List<PDDocument> Pages = splitter.split(doc);
				Iterator<PDDocument> iterator = Pages.listIterator();

				pd = iterator.next();
				pd.save(theDir + "/" + title + ".pdf");

			}
			PDDocument doc2 = PDDocument.load(new File(theDir + "/" + title + ".pdf"));
			int count2 = doc2.getNumberOfPages();
			System.out.println("After PDF Page # " + count2);

			doc.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}