package main;

import java.awt.Desktop;
import java.awt.EventQueue;
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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import models.DAO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

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
		setBounds(100, 100, 661, 549);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnRelatorio = new JButton("Pendentes");
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioValores(); 
			}
		});
		btnRelatorio.setBounds(29, 54, 89, 23);
		contentPane.add(btnRelatorio);
		
		JButton btnValor = new JButton("Valores");
		btnValor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioValor();
			}
		});
		btnValor.setBounds(185, 54, 89, 23);
		contentPane.add(btnValor);
		
	}
	DAO dao = new DAO();
	
		private void relatorioValores() {
			// criar objeto para construir a p�gina pdf
			Document document = new Document();
			// gerar o documento pdf
			try {
				// cria um documento pdf em branco de nome clientes.pdf
				PdfWriter.getInstance(document, new FileOutputStream("Pendentes.pdf"));
				document.open();
				// gerar o conte�do do documento
				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
				document.add(new Paragraph(new Paragraph(formatador.format(data))));
				document.add(new Paragraph(" "));
				document.add(new Paragraph("Orçamentos Pendentes"));
				document.add(new Paragraph(" "));
				// ... Demais conte�dos (imagem, tabela, gr�fico, etc)
				PdfPTable tabela = new PdfPTable(5);
				PdfPCell col1 = new PdfPCell(new Paragraph("ID"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Defeito"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Status"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Valor"));
				PdfPCell col5 = new PdfPCell(new Paragraph("Cliente"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);

				// Acessar o banco de dados
				String relClientes = "select idOs as ID, defeito as Defeito, statusreparo as Status, valor as Valor, nomeContato as Cliente from servico inner join clientes on servico.idCliente = clientes.idFor where statusreparo = 'Pendente'";
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
		
		private void relatorioValor() {
			Document document = new Document();
			// gerar o documento pdf
			try {
				// cria um documento pdf em branco de nome clientes.pdf
				PdfWriter.getInstance(document, new FileOutputStream("Valores.pdf"));
				document.open();
				// gerar o conte�do do documento
				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
				document.add(new Paragraph(new Paragraph(formatador.format(data))));
				document.add(new Paragraph(" "));
				document.add(new Paragraph("Orçamentos Concluídos"));
				document.add(new Paragraph(" "));
				// ... Demais conte�dos (imagem, tabela, gr�fico, etc)
				PdfPTable tabela = new PdfPTable(1);
				PdfPCell col1 = new PdfPCell(new Paragraph("valor"));
				//PdfPCell col2 = new PdfPCell(new Paragraph("Defeito"));
				//PdfPCell col3 = new PdfPCell(new Paragraph("Status"));
				//PdfPCell col4 = new PdfPCell(new Paragraph("Valor"));
				//PdfPCell col5 = new PdfPCell(new Paragraph("Cliente"));
				tabela.addCell(col1);
				//tabela.addCell(col2);
				//tabela.addCell(col3);
				//tabela.addCell(col4);
				//tabela.addCell(col5);

				// Acessar o banco de dados
				String relClientes = "SELECT SUM(valor) AS Valor FROM servico";
				try {
					Connection con = dao.conectar();
					PreparedStatement pst = con.prepareStatement(relClientes);
					ResultSet rs = pst.executeQuery();
					while (rs.next()) {
						tabela.addCell(rs.getString(1));
						//tabela.addCell(rs.getString(2));
						//tabela.addCell(rs.getString(3));
						//tabela.addCell(rs.getString(4));
						//tabela.addCell(rs.getString(5));
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
				Desktop.getDesktop().open(new File("Valores.pdf"));
			} catch (Exception e) {
				System.out.println(e);
			}
			
			
		}
	}// fim do codigo

