package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;

import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import Atxy2k.CustomTextField.RestrictedTextField;
import models.DAO;
import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;

public class Orcamento extends JDialog {

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	public final JPanel contentPanel = new JPanel();
	JTextField txtCliente;
	JTextField txtID;
	JTextField txtOs;
	JTextField txtEquip;
	JTextField txtIMEI;
	JTextField txtValor;
	JTextField txtPagamento;
	JTextField txtSolucao;
	JTextField txtDefeito;
	JTextField txtDetectado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Orcamento dialog = new Orcamento();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Orcamento() {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				((DefaultTableModel) table.getModel()).setRowCount(0);
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				adicionarCliente();
			}
		});

		setTitle("Orçamento");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Orcamento.class.getResource("/img/lojinha.png")));
		setModal(true);
		setBounds(100, 100, 751, 670);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Cliente");
		lblNewLabel.setBounds(20, 27, 57, 14);
		contentPanel.add(lblNewLabel);

		txtCliente = new JTextField();
		txtCliente.setToolTipText("Pesquisar Cliente");
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarCliente();
			}
		});
		txtCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		txtCliente.setBounds(76, 24, 173, 20);
		contentPanel.add(txtCliente);
		txtCliente.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Cliente (ID)");
		lblNewLabel_1.setBounds(607, 27, 63, 14);
		contentPanel.add(lblNewLabel_1);

		txtID = new JTextField();
		txtID.setToolTipText("ID do Cliente");
		txtID.setEditable(false);
		txtID.setBounds(675, 24, 47, 20);
		contentPanel.add(txtID);
		txtID.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Orçamento");
		lblNewLabel_2.setBounds(314, 186, 97, 33);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPanel.add(lblNewLabel_2);

		btnCliente = new JButton("");
		btnCliente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCliente.setToolTipText("Cadastrar Cliente");
		btnCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cliente cliente = new Cliente();
				cliente.setVisible(true);
			}
		});
		btnCliente.setIcon(new ImageIcon(Orcamento.class.getResource("/img/busca32x32.png")));
		btnCliente.setBounds(259, 16, 32, 32);
		contentPanel.add(btnCliente);

		JLabel lblNewLabel_3 = new JLabel("Nº O.S");
		lblNewLabel_3.setBounds(20, 247, 57, 14);
		contentPanel.add(lblNewLabel_3);

		txtOs = new JTextField();
		txtOs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					pesquisar();
				}
				if (e.getKeyCode() == KeyEvent.VK_F1) {
					Cliente cliente = new Cliente();
					cliente.setVisible(true);
				}
			}
		});
		txtOs.setToolTipText("Número da O.S");
		txtOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		txtOs.setBounds(76, 244, 86, 20);
		contentPanel.add(txtOs);
		txtOs.setColumns(10);

		dataEntrada = new JDateChooser();
		dataEntrada.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataEntrada.setToolTipText("Data de Entrada do Equipamento");
		dataEntrada.setEnabled(false);
		dataEntrada.setBounds(514, 600, 134, 20);
		contentPanel.add(dataEntrada);

		JLabel lblNewLabel_4 = new JLabel("Entrada");
		lblNewLabel_4.setBounds(459, 603, 57, 14);
		contentPanel.add(lblNewLabel_4);

		JLabel lblNewLabel_6 = new JLabel("Técnico");
		lblNewLabel_6.setBounds(20, 456, 57, 14);
		contentPanel.add(lblNewLabel_6);

		comboStatus = new JComboBox();
		comboStatus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboStatus.setToolTipText("Status do Orçamento");
		comboStatus.setBounds(552, 382, 170, 22);
		comboStatus.setModel(new DefaultComboBoxModel(new String[] { "Pendente", "Em Analise", "Aguardando Aprovação",
				"Não Aprovado", "Aprovado", "Em Manutenção", "Em Teste", "Concluido", "Retirado" }));
		contentPanel.add(comboStatus);

		JLabel lblNewLabel_7 = new JLabel("Equip");
		lblNewLabel_7.setBounds(20, 301, 57, 14);
		contentPanel.add(lblNewLabel_7);

		txtEquip = new JTextField();
		txtEquip.setToolTipText("Equipamento");
		txtEquip.setBounds(76, 298, 430, 20);
		contentPanel.add(txtEquip);
		txtEquip.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("IMEI");
		lblNewLabel_8.setBounds(20, 329, 57, 14);
		contentPanel.add(lblNewLabel_8);

		txtIMEI = new JTextField();
		txtIMEI.setToolTipText("IMEI do Equipamento");
		txtIMEI.setBounds(76, 326, 430, 20);
		contentPanel.add(txtIMEI);
		txtIMEI.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("Solução");
		lblNewLabel_9.setBounds(20, 507, 57, 14);
		contentPanel.add(lblNewLabel_9);

		JLabel lblNewLabel_2_1 = new JLabel("Ficha Técnica");
		lblNewLabel_2_1.setBounds(313, 408, 115, 33);
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPanel.add(lblNewLabel_2_1);

		JLabel lblNewLabel_10 = new JLabel("Valor");
		lblNewLabel_10.setBounds(193, 456, 37, 14);
		contentPanel.add(lblNewLabel_10);

		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtValor.setToolTipText("Valor do Reparo");
		txtValor.setBounds(228, 453, 86, 20);
		contentPanel.add(txtValor);
		txtValor.setColumns(10);

		txtPagamento = new JTextField();
		txtPagamento.setToolTipText("Status do Pagamento");
		txtPagamento.setBounds(392, 244, 114, 20);
		contentPanel.add(txtPagamento);
		txtPagamento.setColumns(10);

		JLabel lblNewLabel_11 = new JLabel("Status Pagamento");
		lblNewLabel_11.setBounds(276, 247, 106, 14);
		contentPanel.add(lblNewLabel_11);

		btnImprimir = new JButton("");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOs();
			}
		});
		btnImprimir.setEnabled(false);
		btnImprimir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImprimir.setToolTipText("Imprimir Orçamento");
		btnImprimir.setIcon(new ImageIcon(Orcamento.class.getResource("/img/printer64x64.png")));
		btnImprimir.setBounds(658, 556, 64, 64);
		contentPanel.add(btnImprimir);

		txtSolucao = new JTextField();
		txtSolucao.setToolTipText("Solução Necessária");
		txtSolucao.setEditable(false);
		txtSolucao.setText("");
		txtSolucao.setBounds(76, 504, 646, 20);
		contentPanel.add(txtSolucao);
		txtSolucao.setColumns(10);

		btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setToolTipText("Adicionar Novo Orçamento");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarOs();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(Orcamento.class.getResource("/img/add48x48.png")));
		btnAdicionar.setBounds(76, 565, 55, 55);
		contentPanel.add(btnAdicionar);

		btnDelete = new JButton("");
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setToolTipText("Deletar Orçamento");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirOs();
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setIcon(new ImageIcon(Orcamento.class.getResource("/img/delete48x48.png")));
		btnDelete.setBounds(175, 565, 55, 55);
		contentPanel.add(btnDelete);

		btnUpdate = new JButton("");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setToolTipText("Atualizar Orçamento");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarOs();
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setIcon(new ImageIcon(Orcamento.class.getResource("/img/update48x48.png")));
		btnUpdate.setBounds(275, 565, 55, 55);
		contentPanel.add(btnUpdate);

		JLabel lblNewLabel_12 = new JLabel("Defeito");
		lblNewLabel_12.setBounds(20, 357, 57, 14);
		contentPanel.add(lblNewLabel_12);

		txtDefeito = new JTextField();
		txtDefeito.setToolTipText("Defeito Relatado");
		txtDefeito.setBounds(76, 354, 430, 20);
		contentPanel.add(txtDefeito);
		txtDefeito.setColumns(10);

		btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Orcamento.class.getResource("/img/search-48.png")));
		btnLimpar.setToolTipText("Fazer Nova Busca");
		btnLimpar.setBounds(373, 575, 45, 45);
		contentPanel.add(btnLimpar);

		btnBusca = new JButton("");
		btnBusca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

				}
			}
		});
		btnBusca.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar();
			}
		});
		btnBusca.setIcon(new ImageIcon(Orcamento.class.getResource("/img/search.png")));
		btnBusca.setToolTipText("Buscar O.S");
		btnBusca.setBounds(172, 237, 46, 35);
		contentPanel.add(btnBusca);

		txtDetectado = new JTextField();
		txtDetectado.setToolTipText("Defeito Detectado");
		txtDetectado.setEditable(false);
		txtDetectado.setColumns(10);
		txtDetectado.setBounds(76, 383, 430, 20);
		contentPanel.add(txtDetectado);

		JLabel lblNewLabel_13 = new JLabel("Detectado");
		lblNewLabel_13.setBounds(10, 386, 67, 14);
		contentPanel.add(lblNewLabel_13);

		comboNota = new JComboBox();
		comboNota.setToolTipText("Nota Fiscal");
		comboNota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboNota.setModel(new DefaultComboBoxModel(new String[] { "Não", "Sim" }));
		comboNota.setBounds(552, 325, 64, 22);
		contentPanel.add(comboNota);

		JLabel lblNewLabel_14 = new JLabel("Nota Fiscal");
		lblNewLabel_14.setBounds(552, 301, 64, 14);
		contentPanel.add(lblNewLabel_14);

		JLabel lblNewLabel_15 = new JLabel("Garantia");
		lblNewLabel_15.setBounds(663, 301, 55, 14);
		contentPanel.add(lblNewLabel_15);

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 712, 177);
		contentPanel.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 53, 712, 124);
		panel.add(scrollPane);

		table = new JTable();
		table.setToolTipText("Selecione o Cliente");
		table.setCellSelectionEnabled(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
			}
		});
		table.setModel(new DefaultTableModel(
				new Object[][] { { ' ', ' ' }, { ' ', ' ' }, { ' ', ' ' }, { ' ', ' ' }, { ' ', ' ' }, { ' ', ' ' }, },
				new String[] { "Nome", "CPF", "Razão", "ID" }));
		scrollPane.setViewportView(table);

		comboGarantia = new JComboBox();
		comboGarantia.setToolTipText("Garantia");
		comboGarantia.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboGarantia.setModel(new DefaultComboBoxModel(new String[] { "Não", "Sim" }));
		comboGarantia.setBounds(658, 325, 64, 22);
		contentPanel.add(comboGarantia);

		txtTecnico = new JTextField();
		txtTecnico.setToolTipText("Técnico Responsável");
		txtTecnico.setBounds(76, 453, 107, 20);
		contentPanel.add(txtTecnico);
		txtTecnico.setColumns(10);

		RestrictedTextField Clientes = new RestrictedTextField(txtCliente);
		//Clientes.setOnlyText(true);
		//Clientes.setAcceptSpace(true);
		Clientes.setLimit(30);

		RestrictedTextField IDCliente = new RestrictedTextField(txtID);
		IDCliente.setOnlyNums(true);
		IDCliente.setLimit(5);

		RestrictedTextField Pagamento = new RestrictedTextField(txtPagamento);
		Pagamento.setOnlyText(true);
		Pagamento.setAcceptSpace(true);
		Pagamento.setLimit(15);

		RestrictedTextField Equipamento = new RestrictedTextField(txtEquip);
		Equipamento.setLimit(50);

		RestrictedTextField IMEI = new RestrictedTextField(txtIMEI);
		IMEI.setLimit(50);

		RestrictedTextField Defeito = new RestrictedTextField(txtDefeito);
		Defeito.setLimit(50);

		RestrictedTextField Relatado = new RestrictedTextField(txtDetectado);
		Relatado.setLimit(50);

		RestrictedTextField Tecnico = new RestrictedTextField(txtTecnico);
		Tecnico.setOnlyText(true);
		Tecnico.setAcceptSpace(true);
		Tecnico.setLimit(20);

		RestrictedTextField Valor = new RestrictedTextField(txtValor);
		Valor.setLimit(10);

		RestrictedTextField Solucao = new RestrictedTextField(txtSolucao);
		Solucao.setLimit(100);

		RestrictedTextField OS = new RestrictedTextField(txtOs);
		OS.setOnlyNums(true);
		OS.setLimit(5);
	}

	DAO dao = new DAO();
	public JButton btnAdicionar;
	public JButton btnDelete;
	public JButton btnUpdate;
	public JButton btnLimpar;
	public JButton btnBusca;
	public JButton btnCliente;
	public JComboBox comboNota;
	public JComboBox comboStatus;
	public JDateChooser dataEntrada;
	public JTable table;
	public JComboBox comboGarantia;
	public JTextField txtTecnico;
	private JButton btnImprimir;
	private PdfPTable foot;

	void setarCampos() {
		int setar = table.getSelectedRow();
		txtID.setText(table.getModel().getValueAt(setar, 0).toString());
		btnCliente.setEnabled(false);
		txtOs.setEditable(false);
		btnBusca.setEnabled(false);
		txtEquip.requestFocus();
		txtPagamento.setText("Pendente");

	}

	private void pesquisarCliente() {
		String read3 = "select idFor as ID, cpf as CPF, razao as Razão, nomeContato as Nome from clientes where idFor like ?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read3);
			pst.setString(1, txtCliente.getText() + "%"); // Atenção "%"
			ResultSet rs = pst.executeQuery();
			// Uso da biblioteca rs2xml para "popular" a tabela
			table.setModel(DbUtils.resultSetToTableModel(rs));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void pesquisar() {
		if (txtOs.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o número da O.S");
			txtOs.requestFocus();
		} else {
			String read = "select * from servico where idOs = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read);
				pst.setString(1, txtOs.getText());
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					txtID.setText(rs.getString(2));
					txtPagamento.setText(rs.getString(3));
					txtEquip.setText(rs.getString(4));
					txtIMEI.setText(rs.getString(5));
					txtDefeito.setText(rs.getString(6));
					txtDetectado.setText(rs.getString(7));
					comboGarantia.setSelectedItem(rs.getString(8));
					comboNota.setSelectedItem(rs.getString(9));
					txtTecnico.setText(rs.getString(10));
					txtValor.setText(rs.getString(11));
					comboStatus.setSelectedItem(rs.getString(12));
					String setarData = rs.getString(13);
					Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
					dataEntrada.setDate(dataFormatada);
					txtSolucao.setText(rs.getString(15));

					txtOs.requestFocus();
					btnBusca.setEnabled(false);
					txtOs.setEditable(false);
					txtCliente.setEditable(false);
					btnCliente.setEnabled(false);
					btnAdicionar.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
					btnImprimir.setEnabled(true);

					
					int excluir = JOptionPane.showConfirmDialog(null, "Imprimir Orçamento?", "",
							JOptionPane.YES_NO_OPTION);
					if (excluir == JOptionPane.YES_OPTION)
					imprimirOs();
				} else {
					JOptionPane.showMessageDialog(null, "O.S inexistente");
					limpar();
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	void atualizarOs() {

		if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Selecione o Cliente");
			txtID.requestFocus();

		} else if (txtEquip.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Equipamento");
			txtEquip.requestFocus();

		} else if (txtPagamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Status do Pagamento");
			txtPagamento.requestFocus();

		} else if (txtIMEI.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o IMEI");
			txtIMEI.requestFocus();

		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Defeito Informado");
			txtDefeito.requestFocus();

		} else {
			String update = "update servico set idCliente = ?, pagamento = ?, equipamento = ? , IMEI = ?, defeito = ?, detectado = ?, garantia = ?, nota = ?, tecnico = ?, valor = ?, statusreparo = ?, solucao = ? where idOs = ?";

			try {
				// Abrir a conexão
				Connection con = dao.conectar();
				// Preparar a query (instrução SQL)
				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtID.getText());
				pst.setString(2, txtPagamento.getText());
				pst.setString(3, txtEquip.getText());
				pst.setString(4, txtIMEI.getText());
				pst.setString(5, txtDefeito.getText());
				pst.setString(6, txtDetectado.getText());
				pst.setString(7, comboGarantia.getSelectedItem().toString());
				pst.setString(8, comboNota.getSelectedItem().toString());
				pst.setString(9, txtTecnico.getText());
				pst.setString(10, txtValor.getText());
				pst.setString(11, comboStatus.getSelectedItem().toString());
				pst.setString(12, txtSolucao.getText());
				pst.setString(13, txtOs.getText());
				int executa = pst.executeUpdate();
				if (executa == 1) {
					JOptionPane.showMessageDialog(null, "O.S Atualizado com Sucesso!");
					limpar();
				} else {
					JOptionPane.showMessageDialog(null, "Erro: O.S não atualizado!");
					limpar();
				}
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Erro - Tente Novamente!");
				limpar();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void cadastrarOs() {
		if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Selecione o Cliente");
			txtCliente.requestFocus();
		} else if (txtEquip.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Equipamento ");
			txtEquip.requestFocus();
		} else if (txtPagamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Status de Pagamento");
			txtPagamento.requestFocus();
		} else if (txtIMEI.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o IMEI");
			txtIMEI.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Defeito Informado");
			txtDefeito.requestFocus();
		} else {
			String insert = "insert into servico (idCliente,pagamento,equipamento,IMEI,defeito,detectado,garantia,nota,tecnico,valor,statusreparo,solucao) values (?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(insert);
				pst.setString(1, txtID.getText());
				pst.setString(2, txtPagamento.getText());
				pst.setString(3, txtEquip.getText());
				pst.setString(4, txtIMEI.getText());
				pst.setString(5, txtDefeito.getText());
				pst.setString(6, txtDetectado.getText());
				pst.setString(7, comboGarantia.getSelectedItem().toString());
				pst.setString(8, comboNota.getSelectedItem().toString());
				pst.setString(9, txtTecnico.getText());
				pst.setString(10, txtValor.getText());
				pst.setString(11, comboStatus.getSelectedItem().toString());
				pst.setString(12, txtSolucao.getText());
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "O.S Cadastrado com Sucesso");
					limpar();
					adicionarOs();
					pesquisar();
					
					
				}
				con.close();
			} catch (java.sql.SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro ao Cadastrar O.S, Tente Novamente");
				limpar();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	void excluirOs() {
		// System.out.println("teste do botão excluir");
		// validação (confirmação)
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste Orçamento ?",
				"ATENÇÃO, Não Recomendado!!", JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from servico where idOs = ?";
			try {

				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtOs.getText());

				int confirmaExcluir = pst.executeUpdate();
				if (confirmaExcluir == 1) {
					limpar();
					JOptionPane.showMessageDialog(null, "Orçamento excluído com sucesso");

				}
				// encerrar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	void limpar() {
		((DefaultTableModel) table.getModel()).setRowCount(0);
		txtCliente.setText(null);
		txtPagamento.setText(null);
		txtID.setText(null);
		txtOs.setText(null);
		txtEquip.setText(null);
		txtIMEI.setText(null);
		txtDefeito.setText(null);
		txtDetectado.setText(null);
		comboNota.setSelectedItem("Não");
		comboGarantia.setSelectedItem("Não");
		txtTecnico.setText(null);
		txtValor.setText(null);
		comboStatus.setSelectedItem("Pendente");
		txtSolucao.setText(null);
		txtCliente.setEditable(true);

		btnCliente.setEnabled(true);
		btnAdicionar.setEnabled(true);
		btnDelete.setEnabled(false);
		btnUpdate.setEnabled(false);
		((DefaultTableModel) table.getModel()).setRowCount(0);
		dataEntrada.setDate(null);

		txtOs.setEditable(true);
		btnBusca.setEnabled(true);
		btnImprimir.setEnabled(false);

		txtOs.requestFocus();
	}

	void adicionarOs() {

		String read = "SELECT MAX(idOs) FROM servico;  ";

		try {

			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read);
			// pst.setString(0, txtOs.getText());

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				txtOs.setText(rs.getString(1));
				// txtOs.setText("");
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);

		}

	}

	private void imprimirOs() {
		// criar objeto para construir a p�gina pdf
		Document document = new Document();

		// gerar o documento pdf
		try {
			// cria um documento pdf em branco de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("Orcamento.pdf"));
			document.open();

			Image figura = Image.getInstance("C:\\Users\\CPASEDE02\\Downloads\\logo.png");
			figura.scaleAbsolute(100, 100);
			figura.setAbsolutePosition(472, 730);
			document.add(figura);
			
			Date data = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(new Paragraph(formatador.format(data))));

			document.add(new Paragraph("■"));
			
			Paragraph paragraph2 = new Paragraph(20F, "ORÇAMENTO",
					FontFactory.getFont(FontFactory.HELVETICA, 20F, Font.BOLD));
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph2);

			document.add(new Paragraph("■"));
			document.add(new Paragraph("■"));



			// ... Demais conte�dos (imagem, tabela, gr�fico, etc)
			Paragraph paragraph = new Paragraph(14);

			// Acessar o banco de dados
			String relClientes = "select servico.idOs as IdServiço, servico.defeito as Defeito, nomeContato as Nome, servico.pagamento as StatusPagamento, servico.imei as imei, \r\n"
					+ "servico.garantia as Garantia, servico.nota as Nota, servico.valor as Valor, whatsapp as Whatsapp, email as Email, razao as Razão, cpf as CPF , servico.equipamento as Equipamento \r\n"
					+ "from servico inner join clientes on servico.idCliente = clientes.idFor where idOs = ?;";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(relClientes);
				pst.setString(1, txtOs.getText());
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {

					Paragraph col1 = new Paragraph(new Paragraph(13F, "NÚMERO: " + rs.getString(1), FontFactory.getFont(FontFactory.HELVETICA, 13F, Font.BOLD)));
					col1.setAlignment(Element.ALIGN_RIGHT);

					Paragraph col2 = new Paragraph(new Paragraph("Defeito: " + rs.getString(2)));
					
					Paragraph col3 = new Paragraph(new Paragraph(rs.getString(3), FontFactory.getFont(FontFactory.HELVETICA, 17F, Font.BOLD)));
					col3.setAlignment(Element.ALIGN_CENTER);
					
					Paragraph col4 = new Paragraph(new Paragraph("Pagamento: " + rs.getString(4)));
					
					Paragraph col5 = new Paragraph(new Paragraph("IMEI: " + rs.getString(5)));
					
					Paragraph col6 = new Paragraph(new Paragraph("Garantia: " + rs.getString(6)));

					Paragraph col7 = new Paragraph(new Paragraph("Nota: " + rs.getString(7)));

					Paragraph col8 = new Paragraph(new Paragraph("Valor: "  + rs.getString(8)));

					Paragraph col9 = new Paragraph(new Paragraph("Whatsapp: " + rs.getString(9)));

					Paragraph col10 = new Paragraph(new Paragraph("E-mail: " + rs.getString(10)));
					
					Paragraph col11 = new Paragraph(new Paragraph(rs.getString(11), FontFactory.getFont(FontFactory.HELVETICA, 20F, Font.BOLD, BaseColor.GRAY)));
					col11.setAlignment(Element.ALIGN_CENTER);

					Paragraph col12 = new Paragraph(new Paragraph("CPF: " + rs.getString(12)));
					
					Paragraph col13 = new Paragraph(new Paragraph("Equipamento: " + rs.getString(13)));
					

					Paragraph col14 = new Paragraph(new Paragraph("■"));
										
					//id do orçamento
					paragraph.add(col1);

					//linha em branco
					paragraph.add(col14);
					
					//linha da razão social se tiver e linha do nome do cliente
					paragraph.add(col11);
					paragraph.add(col3);

					//linha em branco
					paragraph.add(col14);
					
					paragraph.add(col12);
					
					paragraph.add(col10);
					paragraph.add(col9);
					
					paragraph.add(col14);
					paragraph.add(col14);
					
					paragraph.add(col13);
					paragraph.add(col5);
					paragraph.add(col6);
					paragraph.add(col7);
					
					paragraph.add(col14);
					
					paragraph.add(col2);
					
					paragraph.add(col14);
					
					paragraph.add(col4);
					paragraph.add(col8);
					
					

				}

			} catch (Exception e) {
				System.out.println(e);
			}
			// Adicionar a tabela ao documento pdf
			document.add(paragraph);

		} catch (Exception e) {
			System.out.println(e);
		} finally { // executa o c�digo independente do resultado OK ou n�o
			document.close();
		}

		// abrir o documento que foi gerado no leitor padr�o de pdf do sistema (PC)
		try {
			Desktop.getDesktop().open(new File("Orcamento.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void adicionarCliente() {

		String read = "SELECT MAX(idFor) FROM clientes;  ";

		try {

			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read);
			// pst.setString(0, txtOs.getText());

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				txtCliente.setText(rs.getString(1));
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);

		}

	}

}
