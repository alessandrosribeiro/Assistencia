package main;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import models.DAO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Cursor;

public class Relatorio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Relatorio frame = new Relatorio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Relatorio() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Relatorio.class.getResource("/img/lojinha.png")));
		setTitle("Relatórios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 374, 414);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnRelatorio = new JButton("");
		btnRelatorio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorio.setToolTipText("Relatório de Pendentes");
		btnRelatorio.setIcon(new ImageIcon(Relatorio.class.getResource("/img/pendente128x128.png")));
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioValores(); 
			}
		});
		btnRelatorio.setBounds(24, 210, 135, 135);
		contentPane.add(btnRelatorio);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setToolTipText("Relatório de Concluidos");
		btnNewButton.setIcon(new ImageIcon(Relatorio.class.getResource("/img/concluido128x128.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					relatorioConcluidos();
			}
		});
		btnNewButton.setBounds(199, 23, 135, 135);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioCliente();
			}
		});
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setToolTipText("Relatório de Clientes");
		btnNewButton_1.setIcon(new ImageIcon(Relatorio.class.getResource("/img/cliente128x128.png")));
		btnNewButton_1.setBounds(24, 23, 135, 135);
		contentPane.add(btnNewButton_1);
		
	}
	DAO dao = new DAO();
	
	private void relatorioConcluidos() {
		// criar objeto para construir a p�gina pdf
		Document document = new Document();
		// gerar o documento pdf
		try {
			// cria um documento pdf em branco de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("Concluidos.pdf"));
			document.setPageSize(PageSize.A4.rotate());
			document.open();

			Image figura = Image.getInstance("C:\\Users\\alessandro.sribeiro5\\Desktop\\Assistencia\\logo.png");
			figura.scaleAbsolute(100, 100);
			figura.setAbsolutePosition(730, 485);
			document.add(figura);	

			Date data = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(new Paragraph(formatador.format(data))));

			document.add(new Paragraph("■"));
			document.add(new Paragraph("■"));
			document.add(new Paragraph("■"));

			Paragraph paragraph2 = new Paragraph(17F, "Chamados Concluídos",
					FontFactory.getFont(FontFactory.HELVETICA, 17F, Font.BOLD));
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph2);

			document.add(new Paragraph("■"));
			document.add(new Paragraph("■"));
			// ... Demais conte�dos (imagem, tabela, gr�fico, etc)
			PdfPTable tabela = new PdfPTable(5);
			PdfPCell col1 = new PdfPCell(new Paragraph("ID"));
			col1.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell col2 = new PdfPCell(new Paragraph("Cliente"));
			col2.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell col3 = new PdfPCell(new Paragraph("Equipamento"));
			col3.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell col4 = new PdfPCell(new Paragraph("Defeito"));
			col4.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell col5 = new PdfPCell(new Paragraph("Valor"));
			col5.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			tabela.addCell(col4);
			tabela.addCell(col5);

			// Acessar o banco de dados
			String relClientes = "select idOs as ID, nomeContato as Cliente, equipamento as Equipamento, defeito as Defeito, valor as Valor from servico inner join clientes on servico.idCliente = clientes.idFor where statusreparo = 'Concluido"
					+ "'";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(relClientes);		
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					col1.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
				}

			} catch (Exception e) {
				System.out.println(e);
			}
			// Adicionar a tabela ao documento pdf
			document.add(tabela);
		} catch (Exception e) {
			System.out.println(e);
		} finally { // executa o c�digo independente do resultado OK ou n�o
			document.close();
		}

		// abrir o documento que foi gerado no leitor padr�o de pdf do sistema (PC)
		try {
			Desktop.getDesktop().open(new File("Concluidos.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
	private void relatorioCliente() {
		// criar objeto para construir a p�gina pdf
		Document document = new Document();
		// gerar o documento pdf
		try {
			// cria um documento pdf em branco de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("Clientes.pdf"));
			document.setPageSize(PageSize.A4.rotate());
			document.open();

			Image figura = Image.getInstance("C:\\Users\\alessandro.sribeiro5\\Desktop\\Assistencia\\logo.png");
			figura.scaleAbsolute(100, 100);
			figura.setAbsolutePosition(730, 485);
			document.add(figura);	

			Date data = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(new Paragraph(formatador.format(data))));

			document.add(new Paragraph("■"));
			document.add(new Paragraph("■"));
			document.add(new Paragraph("■"));

			Paragraph paragraph2 = new Paragraph(17F, "Clientes",
					FontFactory.getFont(FontFactory.HELVETICA, 17F, Font.BOLD));
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph2);

			document.add(new Paragraph("■"));
			document.add(new Paragraph("■"));
			// ... Demais conte�dos (imagem, tabela, gr�fico, etc)
			PdfPTable tabela = new PdfPTable(6);
			PdfPCell col1 = new PdfPCell(new Paragraph("ID"));
			col1.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell col2 = new PdfPCell(new Paragraph("Cliente"));
			col2.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell col3 = new PdfPCell(new Paragraph("Telefone"));
			col3.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell col4 = new PdfPCell(new Paragraph("CPF"));
			col3.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell col5 = new PdfPCell(new Paragraph("Empresa"));
			col4.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell col6 = new PdfPCell(new Paragraph("CNPJ"));
			col5.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			tabela.addCell(col4);
			tabela.addCell(col5);
			tabela.addCell(col6);

			// Acessar o banco de dados
			String relClientes = "select idFor as ID, nomeContato as Cliente, cpf as CPF, fone as Telefone, razao as Razao, cnpj as CNPJ from clientes";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(relClientes);		
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
				}

			} catch (Exception e) {
				System.out.println(e);
			}
			// Adicionar a tabela ao documento pdf
			document.add(tabela);
		} catch (Exception e) {
			System.out.println(e);
		} finally { // executa o c�digo independente do resultado OK ou n�o
			document.close();
		}

		// abrir o documento que foi gerado no leitor padr�o de pdf do sistema (PC)
		try {
			Desktop.getDesktop().open(new File("Clientes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
		private void relatorioValores() {
			// criar objeto para construir a p�gina pdf
			Document document = new Document();
			// gerar o documento pdf
			try {
				// cria um documento pdf em branco de nome clientes.pdf
				PdfWriter.getInstance(document, new FileOutputStream("Pendentes.pdf"));
				document.setPageSize(PageSize.A4.rotate());
				document.open();

				Image figura = Image.getInstance("C:\\Users\\alessandro.sribeiro5\\Desktop\\Assistencia\\logo.png");
				figura.scaleAbsolute(100, 100);
				figura.setAbsolutePosition(730, 485);
				document.add(figura);	

				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
				document.add(new Paragraph(new Paragraph(formatador.format(data))));

				document.add(new Paragraph("■"));
				document.add(new Paragraph("■"));
				document.add(new Paragraph("■"));

				Paragraph paragraph2 = new Paragraph(17F, "Chamados Pendentes",
						FontFactory.getFont(FontFactory.HELVETICA, 17F, Font.BOLD));
				paragraph2.setAlignment(Element.ALIGN_CENTER);
				document.add(paragraph2);

				document.add(new Paragraph("■"));
				document.add(new Paragraph("■"));
				// ... Demais conte�dos (imagem, tabela, gr�fico, etc)
				PdfPTable tabela = new PdfPTable(5);
				PdfPCell col1 = new PdfPCell(new Paragraph("ID"));
				col1.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				PdfPCell col2 = new PdfPCell(new Paragraph("Cliente"));
				col2.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				PdfPCell col3 = new PdfPCell(new Paragraph("Equipamento"));
				col3.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				PdfPCell col4 = new PdfPCell(new Paragraph("Defeito"));
				col4.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				PdfPCell col5 = new PdfPCell(new Paragraph("Técnico"));
				col5.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);

				// Acessar o banco de dados
				String relClientes = "select idOs as ID, nomeContato as Cliente, equipamento as Equipamento, defeito as Defeito, tecnico as Técnico from servico inner join clientes on servico.idCliente = clientes.idFor where statusreparo = 'Pendente'";
				try {
					Connection con = dao.conectar();
					PreparedStatement pst = con.prepareStatement(relClientes);		
					ResultSet rs = pst.executeQuery();
					while (rs.next()) {
						tabela.addCell(rs.getString(1));
						tabela.addCell(rs.getString(2));
						tabela.addCell(rs.getString(3));
						tabela.addCell(rs.getString(4));
						tabela.addCell(rs.getString(5));
					}

				} catch (Exception e) {
					System.out.println(e);
				}
				// Adicionar a tabela ao documento pdf
				document.add(tabela);
			} catch (Exception e) {
				System.out.println(e);
			} finally { // executa o c�digo independente do resultado OK ou n�o
				document.close();
			}

			// abrir o documento que foi gerado no leitor padr�o de pdf do sistema (PC)
			try {
				Desktop.getDesktop().open(new File("Pendentes.pdf"));
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
	}// fim do codigo

