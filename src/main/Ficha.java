package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.Blob;
import com.toedter.calendar.JDateChooser;

import Atxy2k.CustomTextField.RestrictedTextField;
import models.DAO;
import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Ficha extends JDialog {

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	public final JPanel contentPanel = new JPanel();
	public JTextField txtID;
	public JTextField txtOs;
	public JTextField txtEquip;
	public JTextField txtIMEI;
	public JTextField txtValor;
	public JTextField txtSolucao;
	public JTextField txtDefeito;
	public JTextField txtDetectado;
	private int tamanho;
	private FileInputStream fis;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Ficha dialog = new Ficha();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Ficha() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				((DefaultTableModel) table.getModel()).setRowCount(0);
				adicionarOs();
			}
		});
		setTitle("Ficha Técnica");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Ficha.class.getResource("/img/lojinha.png")));
		setModal(true);
		setBounds(100, 100, 751, 767);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Cliente (ID)");
		lblNewLabel_1.setBounds(607, 27, 63, 14);
		contentPanel.add(lblNewLabel_1);

		txtID = new JTextField();
		txtID.setToolTipText("ID do Cliente");
		txtID.setEditable(false);
		txtID.setBounds(675, 24, 47, 20);
		contentPanel.add(txtID);
		txtID.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Nº O.S");
		lblNewLabel_3.setBounds(20, 27, 57, 14);
		contentPanel.add(lblNewLabel_3);

		txtOs = new JTextField();
		txtOs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					pesquisar();
					pesquisarCliente();
				}
				if (e.getKeyCode() == KeyEvent.VK_F1) {
					Cliente cliente = new Cliente();
					cliente.setVisible(true);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarFornecedor();
			}
		});
		txtOs.setToolTipText("Número da O.S");
		txtOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		txtOs.setBounds(62, 24, 86, 20);
		contentPanel.add(txtOs);
		txtOs.setColumns(10);

		dataEntrada = new JDateChooser();
		dataEntrada.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dataEntrada.setToolTipText("Data de Entrada do Equipamento");
		dataEntrada.setEnabled(false);
		dataEntrada.setBounds(591, 699, 134, 20);
		contentPanel.add(dataEntrada);

		JLabel lblNewLabel_4 = new JLabel("Entrada");
		lblNewLabel_4.setBounds(528, 705, 57, 14);
		contentPanel.add(lblNewLabel_4);

		JLabel lblNewLabel_6 = new JLabel("Técnico");
		lblNewLabel_6.setBounds(357, 263, 50, 14);
		contentPanel.add(lblNewLabel_6);

		comboStatus = new JComboBox();
		comboStatus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboStatus.setToolTipText("Status do Orçamento");
		comboStatus.setBounds(552, 413, 170, 22);
		comboStatus.setModel(new DefaultComboBoxModel(new String[] { "Pendente", "Em Analise", "Aguardando Aprovação",
				"Não Aprovado", "Aprovado", "Em Manutenção", "Em Teste", "Concluido", "Retirado" }));
		contentPanel.add(comboStatus);

		JLabel lblNewLabel_7 = new JLabel("Equip");
		lblNewLabel_7.setBounds(20, 342, 57, 14);
		contentPanel.add(lblNewLabel_7);

		txtEquip = new JTextField();
		txtEquip.setEditable(false);
		txtEquip.setToolTipText("Equipamento");
		txtEquip.setBounds(76, 339, 430, 20);
		contentPanel.add(txtEquip);
		txtEquip.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("IMEI");
		lblNewLabel_8.setBounds(20, 367, 57, 14);
		contentPanel.add(lblNewLabel_8);

		txtIMEI = new JTextField();
		txtIMEI.setEditable(false);
		txtIMEI.setToolTipText("IMEI do Equipamento");
		txtIMEI.setBounds(76, 364, 430, 20);
		contentPanel.add(txtIMEI);
		txtIMEI.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("Solução");
		lblNewLabel_9.setBounds(18, 449, 59, 14);
		contentPanel.add(lblNewLabel_9);

		JLabel lblNewLabel_2_1 = new JLabel("Ficha Técnica");
		lblNewLabel_2_1.setBounds(302, 295, 115, 33);
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPanel.add(lblNewLabel_2_1);

		JLabel lblNewLabel_10 = new JLabel("Valor");
		lblNewLabel_10.setBounds(602, 263, 37, 14);
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
		txtValor.setBounds(639, 260, 86, 20);
		contentPanel.add(txtValor);
		txtValor.setColumns(10);

		txtSolucao = new JTextField();
		txtSolucao.setToolTipText("Solução Necessária");
		txtSolucao.setText("");
		txtSolucao.setBounds(76, 446, 646, 20);
		contentPanel.add(txtSolucao);
		txtSolucao.setColumns(10);

		btnUpdate = new JButton("");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setToolTipText("Atualizar Orçamento");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarOs();
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setIcon(new ImageIcon(Ficha.class.getResource("/img/update48x48.png")));
		btnUpdate.setBounds(372, 664, 55, 55);
		contentPanel.add(btnUpdate);

		JLabel lblNewLabel_12 = new JLabel("Defeito");
		lblNewLabel_12.setBounds(20, 392, 57, 14);
		contentPanel.add(lblNewLabel_12);

		txtDefeito = new JTextField();
		txtDefeito.setEditable(false);
		txtDefeito.setToolTipText("Defeito Relatado");
		txtDefeito.setBounds(76, 389, 430, 20);
		contentPanel.add(txtDefeito);
		txtDefeito.setColumns(10);

		btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Ficha.class.getResource("/img/search-48.png")));
		btnLimpar.setToolTipText("Fazer Nova Busca");
		btnLimpar.setBounds(461, 674, 45, 45);
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
		btnBusca.setIcon(new ImageIcon(Ficha.class.getResource("/img/search.png")));
		btnBusca.setToolTipText("Buscar O.S");
		btnBusca.setBounds(155, 17, 46, 35);
		contentPanel.add(btnBusca);

		txtDetectado = new JTextField();
		txtDetectado.setToolTipText("Defeito Detectado");
		txtDetectado.setColumns(10);
		txtDetectado.setBounds(76, 414, 430, 20);
		contentPanel.add(txtDetectado);

		JLabel lblNewLabel_13 = new JLabel("Detectado");
		lblNewLabel_13.setBounds(10, 417, 67, 14);
		contentPanel.add(lblNewLabel_13);

		JLabel lblNewLabel_15 = new JLabel("Garantia");
		lblNewLabel_15.setBounds(552, 342, 55, 14);
		contentPanel.add(lblNewLabel_15);

		comboGarantia = new JComboBox();
		comboGarantia.setToolTipText("Garantia");
		comboGarantia.setEnabled(false);
		comboGarantia.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboGarantia.setModel(new DefaultComboBoxModel(new String[] { "Não", "Sim" }));
		comboGarantia.setBounds(552, 363, 64, 22);
		contentPanel.add(comboGarantia);


		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(20, 228, 46, 14);
		contentPanel.add(lblNome);

		txtNome = new JTextField();
		txtNome.setToolTipText("Nome do Cliente");
		txtNome.setEditable(false);
		txtNome.setColumns(10);
		txtNome.setBounds(62, 225, 168, 20);
		contentPanel.add(txtNome);

		JLabel lblWhatsapp = new JLabel("");
		lblWhatsapp.setIcon(new ImageIcon(Ficha.class.getResource("/img/whastapp24x24.png")));
		lblWhatsapp.setBounds(270, 225, 24, 24);
		contentPanel.add(lblWhatsapp);

		txtWhatsapp = new JTextField();
		txtWhatsapp.setToolTipText("Whatsapp do Cliente");
		txtWhatsapp.setEditable(false);
		txtWhatsapp.setColumns(10);
		txtWhatsapp.setBounds(302, 225, 151, 20);
		contentPanel.add(txtWhatsapp);

		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(528, 228, 46, 14);
		contentPanel.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setToolTipText("E-mail do Cliente");
		txtEmail.setEditable(false);
		txtEmail.setColumns(10);
		txtEmail.setBounds(571, 225, 151, 20);
		contentPanel.add(txtEmail);

		JLabel lblRazao = new JLabel("Razão");
		lblRazao.setFont(new Font("Dialog", Font.BOLD, 12));
		lblRazao.setBounds(20, 262, 46, 14);
		contentPanel.add(lblRazao);

		txtRazao = new JTextField();
		txtRazao.setToolTipText("Razão Social");
		txtRazao.setEditable(false);
		txtRazao.setColumns(10);
		txtRazao.setBounds(62, 260, 250, 20);
		contentPanel.add(txtRazao);

		txtTecnico = new JTextField();
		txtTecnico.setToolTipText("Técnico");
		txtTecnico.setBounds(414, 260, 145, 20);
		contentPanel.add(txtTecnico);
		txtTecnico.setColumns(10);

		txtPagamento = new JTextField();
		txtPagamento.setEditable(false);
		txtPagamento.setToolTipText("Status do Pagamento");
		txtPagamento.setColumns(10);
		txtPagamento.setBounds(368, 24, 114, 20);
		contentPanel.add(txtPagamento);

		lblNewLabel = new JLabel("Status Pagamento");
		lblNewLabel.setBounds(255, 27, 106, 14);
		contentPanel.add(lblNewLabel);

		comboNota = new JComboBox();
		comboNota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboNota.setToolTipText("Equipamento");
		comboNota.setEnabled(false);
		comboNota.setModel(new DefaultComboBoxModel(new String[] { "Não", "Sim" }));
		comboNota.setBounds(658, 363, 64, 22);
		contentPanel.add(comboNota);

		JLabel lblNewLabel_14 = new JLabel("Nota Fiscal");
		lblNewLabel_14.setBounds(658, 342, 64, 14);
		contentPanel.add(lblNewLabel_14);

		lblNewLabel_2 = new JLabel("Foto do Equipamento");
		lblNewLabel_2.setBounds(42, 493, 159, 14);
		contentPanel.add(lblNewLabel_2);

		btnFoto = new JButton("");
		btnFoto.setToolTipText("Adicionar Foto");
		btnFoto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFoto.setEnabled(false);
		btnFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarFoto();

			}
		});
		btnFoto.setIcon(new ImageIcon(Ficha.class.getResource("/img/busca48x48.png")));
		btnFoto.setBounds(195, 664, 55, 55);
		contentPanel.add(btnFoto);

		lblFoto = new JLabel("");
		lblFoto.setToolTipText("Foto do Equipamento");
		lblFoto.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblFoto.setAlignmentX(0.5f);
		lblFoto.setBounds(21, 515, 164, 204);
		contentPanel.add(lblFoto);

		JPanel panel = new JPanel();
		panel.setBounds(20, 62, 705, 152);
		contentPanel.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 705, 152);
		panel.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
			}
		});
		table.setModel(new DefaultTableModel(
				new Object[][] { { ' ', ' ' }, { ' ', ' ' }, { ' ', ' ' }, { ' ', ' ' }, { ' ', ' ' }, { ' ', ' ' },
						{ ' ', ' ' }, { ' ', ' ' }, },
				new String[] { "Orçamento", "Equipamento", "Defeito", "ID Cliente" }));
		scrollPane.setViewportView(table);

		
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
		
		RestrictedTextField nomeContato = new RestrictedTextField(txtNome);
		nomeContato.setOnlyText(true);
		nomeContato.setAcceptSpace(true);
		nomeContato.setLimit(30);
		
		RestrictedTextField Whatsapp = new RestrictedTextField(txtWhatsapp);
		Whatsapp.setOnlyNums(true);
		Whatsapp.setLimit(11);
	
		RestrictedTextField Email = new RestrictedTextField(txtEmail);
		Email.setLimit(50);
		
		RestrictedTextField Razao = new RestrictedTextField(txtRazao);
		Razao.setLimit(50);
		
	}

	DAO dao = new DAO();
	public JButton btnUpdate;
	public JButton btnLimpar;
	public JButton btnBusca;
	public JComboBox comboStatus;
	public JDateChooser dataEntrada;
	public JComboBox comboGarantia;
	private JTextField txtNome;
	private JTextField txtWhatsapp;
	private JTextField txtEmail;
	private JTextField txtRazao;
	private JTextField txtTecnico;
	private JTextField txtPagamento;
	private JLabel lblNewLabel;
	private JComboBox comboNota;
	private JLabel lblNewLabel_2;
	private JButton btnFoto;
	private JLabel lblFoto;
	private JTable table;

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

					btnBusca.setEnabled(false);
					txtOs.setEditable(false);

					btnUpdate.setEnabled(true);
					pesquisarCliente();
					buscarFoto();
					btnFoto.setEnabled(true);

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

	void atualizarFoto() {

		if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Selecione o Cliente");
			txtID.requestFocus();
		} else {
			String update = "update servico set foto = ? where idOs = ?";

			try {
				// Abrir a conexão
				Connection con = dao.conectar();
				// Preparar a query (instrução SQL)
				PreparedStatement pst = con.prepareStatement(update);
				pst.setBlob(1, fis, tamanho);
				pst.setString(2, txtOs.getText());

				int executa = pst.executeUpdate();
				if (executa == 1) {

				} else {
					JOptionPane.showMessageDialog(null, "Erro: Foto não atualizado!");
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

	void pesquisarCliente() {

		if (txtID.getText().isEmpty()) {

		} else {
			String read = "select * from clientes where idFor = ?";

			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read);

				pst.setString(1, txtID.getText());

				ResultSet rs = pst.executeQuery();

				if (rs.next()) {

					txtNome.setText(rs.getString(9));
					txtWhatsapp.setText(rs.getString(11));
					txtEmail.setText(rs.getString(12));
					txtRazao.setText(rs.getString(14));

				} else {
				}
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}

	private void selecionarFoto() {
		// JFileChooser classe modelo qye gera um explorador de arquivos
		JFileChooser jfc = new JFileChooser();
		// titulo do explorador de arquivo
		jfc.setDialogTitle("Selecionar Arquivo");
		// a linha abaixo cria um filtro para selecionar o tipo de arquivo
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de Imagens(*.PNG, *.JPG, *.JPEG", "png", "jpg", "jpeg"));

		// showOpenDialog(this) abre explorador de arquivos
		// int resultado é para escolher o arquivo
		int resultado = jfc.showOpenDialog(this);
		// se o usuario escolher uma opção setar a JLabel
		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {
				fis = new FileInputStream(jfc.getSelectedFile());
				// a linha abaixo seleciona o tamanho do arquivo
				tamanho = (int) jfc.getSelectedFile().length();
				// convertendo o arquivo e setando a largura e altura para preencher a JLabel
				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(),
						lblFoto.getHeight(), Image.SCALE_SMOOTH);
				// setar a JLabel
				lblFoto.setIcon(new ImageIcon(foto));
				lblFoto.updateUI();
				atualizarFoto();
			} catch (Exception e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Foto não cadastrado");
			}
		}
	}

// aqui
	private void buscarFoto() {

		// if (lblFoto.setIcon().isEmpty()){
		// int excluir = JOptionPane.showConfirmDialog(null, "Quer cadastrar a foto do
		// Equipamento agora??","", JOptionPane.YES_NO_OPTION);
		// if (excluir == JOptionPane.YES_OPTION)
		// selecionarFoto();
		// } else {

		String read = "select * from servico where idOs = ?";

		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, txtOs.getText());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				Blob blob = (Blob) rs.getBlob(14);
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));
				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblFoto.setIcon(foto);
			}
			con.close();
		} catch (Exception e) {
			((DefaultTableModel) table.getModel()).setRowCount(0);
			int excluir = JOptionPane.showConfirmDialog(null, "Foto não cadastrada, Cadastrar agora?", "",
					JOptionPane.YES_NO_OPTION);
			if (excluir == JOptionPane.YES_OPTION)
				selecionarFoto();

		}

	}

	void setarCampos() {
		int setar = table.getSelectedRow();
		txtOs.setText(table.getModel().getValueAt(setar, 0).toString());
		pesquisar();
	}

	private void pesquisarFornecedor() {
		String read3 = "select idOs as Orçamento, equipamento as Equipamento, defeito as Defeito, idCliente as Cliente from servico where idOs like ?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read3);
			pst.setString(1, txtOs.getText() + "%"); // Aten��o "%"
			ResultSet rs = pst.executeQuery();
			// Uso da biblioteca rs2xml para "popular" a tabela
			table.setModel(DbUtils.resultSetToTableModel(rs));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
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

	void limpar() {
		txtID.setText(null);
		txtOs.setText(null);
		txtEquip.setText(null);
		txtIMEI.setText(null);
		txtDefeito.setText(null);
		txtDetectado.setText(null);
		comboGarantia.setSelectedItem("Não");
		comboNota.setSelectedItem("Não");
		txtTecnico.setText(null);
		txtValor.setText(null);
		comboStatus.setSelectedItem("Pendente");
		txtSolucao.setText(null);
		btnUpdate.setEnabled(false);
		dataEntrada.setDate(null);
		lblFoto.setIcon(null);
		txtPagamento.setText(null);
		txtNome.setText(null);
		txtWhatsapp.setText(null);
		txtEmail.setText(null);
		txtRazao.setText(null);

		txtOs.setEditable(true);
		btnBusca.setEnabled(true);

		lblFoto.setIcon(null);
		txtOs.requestFocus();
		btnFoto.setEnabled(false);

		((DefaultTableModel) table.getModel()).setRowCount(0);
	}
}
