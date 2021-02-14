package code;
//package cmsProjekt;
//
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.PdfWriter;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//
//public class CreatePDF {
//
//	public static void run(String title, String from, String content, String section)
//			throws FileNotFoundException, DocumentException {
//		String file_path = "/Users/mohammedalianis/Desktop/pdf/" + title + ".pdf";
//		File blog_file;
//		Document doc;
//		Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
//		Font blog_text_font = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
//		Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
//		Font smallBold_blue = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
//		blog_file = new File(file_path);
//		doc = new Document();
//		PdfWriter.getInstance(doc, new FileOutputStream(blog_file));
//		doc.open();
//		Paragraph preface = new Paragraph();
//
//		if (content.length() < 2100) {
//
//			preface.add(new Paragraph(title, catFont));
//			preface.add(new Paragraph(("Generated at: " + from), smallBold));
//			preface.add(new Paragraph(("--------------------------------------"), smallBold));
//			preface.add(new Paragraph(content, blog_text_font));
//			preface.add(new Paragraph(("--------------------------------------"), smallBold));
//			preface.add(new Paragraph((section), smallBold_blue));
//
//			preface.add(new Paragraph(" "));
//			preface.add(new Paragraph(" "));
//			doc.add(preface);
//			doc.close();
//		} else if(content.length()>2100) {
//			char[] a = content.toCharArray();
//			String s = "";
//			for (int i = 0; i < 2100; i++) {
//				s = s + a[i];
//			}
//			preface.add(new Paragraph(title, catFont));
//			preface.add(new Paragraph(("Generated at: " + from), smallBold));
//			preface.add(new Paragraph(("--------------------------------------"), smallBold));
//			preface.add(new Paragraph(s, blog_text_font));
//			preface.add(new Paragraph(("--------------------------------------"), smallBold));
//			preface.add(new Paragraph("For more info Please visit or Webpage", smallBold_blue));
//			preface.add(new Paragraph(("--------------------------------------"), smallBold));
//			preface.add(new Paragraph((section), smallBold_blue));
//
//			preface.add(new Paragraph(" "));
//			preface.add(new Paragraph(" "));
//			doc.add(preface);
//			doc.close();
//
//		}
//
//	}
//
//}
