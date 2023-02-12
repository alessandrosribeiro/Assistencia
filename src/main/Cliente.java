package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;
import models.DAO;
import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.Window.Type;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Cursor;

@SuppressWarnings("unused")
public class Cliente extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public JTextField txtCliente;
	public JTextField txtID;
	public JTextField txtTelefone;
	public JTextField txtEmail;
	public JTextField txtNome;
	public JTextField txtCPF;
	public JTextField txtWhatsapp;
	public JTextField txtRazao;
	public JTextField txtCNPJ;
	public JTextField txtFantasia;
	public JTextField txtInscricao;
	public JTextField txtCEP;
	public JTextField txtEndereco;
	public JTextField txtCidade;
	public JTextField txtNumero;
	public JTextField txtComplemento;
	public JTextField txtObservacao;
	public JTextField txtBairro;
	@SuppressWarnings("rawtypes")
	public JComboBox cboUf;
	public JCheckBox chckbxNewCheckBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Cliente dialog = new Cliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cliente() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				((DefaultTableModel) table.getModel()).setRowCount(0);
			}
		});
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Cliente.class.getResource("/img/lojinha.png")));
		setTitle("Cadastro de Clientes");
		setBounds(100, 100, 736, 662);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblCliente = new JLabel("Clientes");
		lblCliente.setBounds(10, 23, 46, 14);
		contentPanel.add(lblCliente);

		txtCliente = new JTextField();
		txtCliente.setToolTipText("Buscar pelo nome do Cliente");
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarFornecedor();
			}
		});
		txtCliente.setBounds(58, 20, 151, 20);
		contentPanel.add(txtCliente);
		txtCliente.setColumns(10);

		txtID = new JTextField();
		txtID.setToolTipText("ID do Cliente");
		txtID.setBounds(664, 20, 46, 20);
		contentPanel.add(txtID);
		txtID.setColumns(10);

		JLabel lblID = new JLabel("ID");
		lblID.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblID.setBounds(641, 23, 24, 14);
		contentPanel.add(lblID);

		JPanel panel = new JPanel();
		panel.setBounds(10, 59, 700, 109);
		contentPanel.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 700, 109);
		panel.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
			}
		});
		table.setModel(new DefaultTableModel(
				new Object[][] { { ' ', ' ' }, { ' ', ' ' }, { ' ', ' ' }, { ' ', ' ' }, { ' ', ' ' }, },
				new String[] { "Nome", "CPF", "Razão", "ID" }));
		scrollPane.setViewportView(table);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 179, 46, 14);
		contentPanel.add(lblNome);

		txtTelefone = new JTextField();
		txtTelefone.setToolTipText("Telefone");
		txtTelefone.setBounds(327, 176, 151, 20);
		contentPanel.add(txtTelefone);
		txtTelefone.setColumns(10);

		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(10, 220, 46, 14);
		contentPanel.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setToolTipText("E-mail");
		txtEmail.setBounds(58, 217, 151, 20);
		contentPanel.add(txtEmail);
		txtEmail.setColumns(10);

		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(262, 179, 63, 14);
		contentPanel.add(lblTelefone);

		txtNome = new JTextField();
		txtNome.setToolTipText("Nome do Cliente");
		txtNome.setBounds(58, 176, 151, 20);
		contentPanel.add(txtNome);
		txtNome.setColumns(10);

		JLabel lblCPF = new JLabel("CPF");
		lblCPF.setBounds(262, 220, 63, 14);
		contentPanel.add(lblCPF);

		txtCPF = new JTextField();
		txtCPF.setToolTipText("CPF");
		txtCPF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321x";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
			}
			}
		});
		txtCPF.setBounds(327, 217, 151, 20);
		contentPanel.add(txtCPF);
		txtCPF.setColumns(10);

		txtWhatsapp = new JTextField();
		txtWhatsapp.setToolTipText("Whatsapp");
		txtWhatsapp.setBounds(559, 176, 151, 20);
		contentPanel.add(txtWhatsapp);
		txtWhatsapp.setColumns(10);

		JLabel lblWhatsapp = new JLabel("");
		lblWhatsapp.setIcon(new ImageIcon(Cliente.class.getResource("/img/whastapp24x24.png")));
		lblWhatsapp.setBounds(529, 175, 24, 24);
		contentPanel.add(lblWhatsapp);

		JLabel lblRazao = new JLabel("Razão");
		lblRazao.setFont(new Font("Dialog", Font.BOLD, 12));
		lblRazao.setBounds(10, 310, 46, 14);
		contentPanel.add(lblRazao);

		txtRazao = new JTextField();
		txtRazao.setToolTipText("Razão Social");
		txtRazao.setEditable(false);
		txtRazao.setBounds(58, 307, 250, 20);
		contentPanel.add(txtRazao);
		txtRazao.setColumns(10);

		JLabel lblCNPJ = new JLabel("CNPJ");
		lblCNPJ.setBounds(10, 344, 46, 14);
		contentPanel.add(lblCNPJ);

		txtCNPJ = new JTextField();
		txtCNPJ.setToolTipText("CNPJ");
		txtCNPJ.setEditable(false);
		txtCNPJ.setBounds(58, 341, 250, 20);
		contentPanel.add(txtCNPJ);
		txtCNPJ.setColumns(10);

		JLabel lblFantasia = new JLabel("Nome Fantasia");
		lblFantasia.setBounds(366, 310, 95, 14);
		contentPanel.add(lblFantasia);

		JLabel lblInscricao = new JLabel("Inscrição");
		lblInscricao.setBounds(366, 344, 84, 14);
		contentPanel.add(lblInscricao);

		txtFantasia = new JTextField();
		txtFantasia.setToolTipText("Nome Fantasia");
		txtFantasia.setEditable(false);
		txtFantasia.setBounds(460, 307, 250, 20);
		contentPanel.add(txtFantasia);
		txtFantasia.setColumns(10);

		txtInscricao = new JTextField();
		txtInscricao.setToolTipText("Inscrição Estadual");
		txtInscricao.setEditable(false);
		txtInscricao.setBounds(460, 341, 250, 20);
		contentPanel.add(txtInscricao);
		txtInscricao.setColumns(10);

		JLabel lblCEP = new JLabel("CEP");
		lblCEP.setBounds(10, 396, 46, 14);
		contentPanel.add(lblCEP);

		txtCEP = new JTextField();
		txtCEP.setToolTipText("CEP");
		txtCEP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					buscarCEP();
					txtCEP.requestFocus();
				}
			}
		});
		txtCEP.setBounds(58, 393, 123, 20);
		contentPanel.add(txtCEP);
		txtCEP.setColumns(10);

		JButton btnBuscaCEP = new JButton("");
		btnBuscaCEP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscaCEP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCEP();
				txtCEP.requestFocus();
			}
		});
		btnBuscaCEP.setToolTipText("Buscar CEP");
		btnBuscaCEP.setIcon(new ImageIcon(Cliente.class.getResource("/img/search.png")));
		btnBuscaCEP.setBounds(191, 386, 46, 35);
		contentPanel.add(btnBuscaCEP);

		JLabel lblEndereco = new JLabel("End");
		lblEndereco.setBounds(10, 437, 46, 14);
		contentPanel.add(lblEndereco);

		txtEndereco = new JTextField();
		txtEndereco.setToolTipText("Endereço");
		txtEndereco.setBounds(58, 434, 151, 20);
		contentPanel.add(txtEndereco);
		txtEndereco.setColumns(10);

		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setBounds(10, 473, 46, 14);
		contentPanel.add(lblCidade);

		txtCidade = new JTextField();
		txtCidade.setToolTipText("Cidade");
		txtCidade.setBounds(58, 473, 151, 20);
		contentPanel.add(txtCidade);
		txtCidade.setColumns(10);

		JLabel lblNumero = new JLabel("Nº");
		lblNumero.setBounds(274, 437, 24, 14);
		contentPanel.add(lblNumero);

		txtNumero = new JTextField();
		txtNumero.setToolTipText("Número do Endereço");
		txtNumero.setBounds(297, 434, 46, 20);
		contentPanel.add(txtNumero);
		txtNumero.setColumns(10);

		JLabel lblComplemento = new JLabel("Complemento");
		lblComplemento.setBounds(366, 437, 95, 14);
		contentPanel.add(lblComplemento);

		txtComplemento = new JTextField();
		txtComplemento.setToolTipText("Complemento");
		txtComplemento.setBounds(460, 434, 250, 20);
		contentPanel.add(txtComplemento);
		txtComplemento.setColumns(10);

		txtObservacao = new JTextField();
		txtObservacao.setToolTipText("Observação");
		txtObservacao.setBounds(58, 510, 638, 22);
		contentPanel.add(txtObservacao);
		txtObservacao.setColumns(10);

		JLabel lblObservacao = new JLabel("Obs");
		lblObservacao.setBounds(10, 513, 46, 16);
		contentPanel.add(lblObservacao);

		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setBounds(253, 472, 46, 16);
		contentPanel.add(lblBairro);

		txtBairro = new JTextField();
		txtBairro.setToolTipText("Bairro");
		txtBairro.setBounds(297, 470, 123, 20);
		contentPanel.add(txtBairro);
		txtBairro.setColumns(10);

		cboUf = new JComboBox();
		cboUf.setToolTipText("UF");
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR",
						"PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setBounds(491, 468, 46, 25);
		contentPanel.add(cboUf);

		JLabel lblUF = new JLabel("UF");
		lblUF.setBounds(460, 472, 31, 16);
		contentPanel.add(lblUF);

		btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setToolTipText("Nova Consulta");
		btnLimpar.setIcon(new ImageIcon(Cliente.class.getResource("/img/search-48.png")));
		btnLimpar.setBounds(375, 567, 45, 45);
		contentPanel.add(btnLimpar);

		btnDelete = new JButton("");
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirClientes();
			}
		});
		btnDelete.setToolTipText("Deletar Cliente");
		btnDelete.setIcon(new ImageIcon(Cliente.class.getResource("/img/delete48x48.png")));
		btnDelete.setBounds(163, 557, 55, 55);
		contentPanel.add(btnDelete);

		btnUpdate = new JButton("");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarClientes();
			}
		});
		btnUpdate.setToolTipText("Atualizar Cliente");
		btnUpdate.setIcon(new ImageIcon(Cliente.class.getResource("/img/update48x48.png")));
		btnUpdate.setBounds(270, 557, 55, 55);
		contentPanel.add(btnUpdate);

		btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarClientes();
			}
		});
		btnAdicionar.setToolTipText("Adicionar Cliente");
		btnAdicionar.setIcon(new ImageIcon(Cliente.class.getResource("/img/add48x48.png")));
		btnAdicionar.setBounds(58, 557, 55, 55);
		contentPanel.add(btnAdicionar);

		RestrictedTextField ID = new RestrictedTextField(txtID);
		ID.setOnlyNums(true);
		ID.setLimit(10);

		RestrictedTextField CEP = new RestrictedTextField(txtCEP);
		CEP.setOnlyNums(true);
		CEP.setLimit(8);

		RestrictedTextField Endereço = new RestrictedTextField(txtEndereco);
		Endereço.setOnlyNums(false);
		Endereço.setLimit(50);

		RestrictedTextField Numero = new RestrictedTextField(txtNumero);
		Numero.setOnlyNums(true);
		Numero.setLimit(6);

		RestrictedTextField Complemento = new RestrictedTextField(txtComplemento);
		Complemento.setOnlyNums(false);
		Complemento.setLimit(20);

		RestrictedTextField Bairro = new RestrictedTextField(txtBairro);
		Bairro.setOnlyNums(false);
		Bairro.setLimit(50);

		RestrictedTextField Cidade = new RestrictedTextField(txtCidade);
		Cidade.setOnlyText(true);
		Cidade.setAcceptSpace(true);
		Cidade.setLimit(50);

		RestrictedTextField nomeContato = new RestrictedTextField(txtNome);
		nomeContato.setOnlyText(true);
		nomeContato.setAcceptSpace(true);
		nomeContato.setLimit(30);

		RestrictedTextField Telefone = new RestrictedTextField(txtTelefone);
		Telefone.setOnlyNums(true);
		Telefone.setLimit(11);

		RestrictedTextField Inscricao = new RestrictedTextField(txtInscricao);
		Inscricao.setOnlyNums(false);
		Inscricao.setLimit(14);

		RestrictedTextField Cpf = new RestrictedTextField(txtCPF);
		Cpf.setLimit(11);

		RestrictedTextField Razao = new RestrictedTextField(txtRazao);
		Razao.setLimit(50);

		RestrictedTextField Fantasia = new RestrictedTextField(txtFantasia);
		Fantasia.setLimit(50);

		RestrictedTextField CNPJ = new RestrictedTextField(txtCNPJ);
		CNPJ.setOnlyNums(true);
		CNPJ.setLimit(14);
		
		RestrictedTextField Clientes = new RestrictedTextField(txtCliente);
		Clientes.setOnlyText(true);
		Clientes.setAcceptSpace(true);
		Clientes.setLimit(30);

		RestrictedTextField Whatsapp = new RestrictedTextField(txtWhatsapp);
		Whatsapp.setOnlyNums(true);
		Whatsapp.setLimit(11);

		RestrictedTextField Email = new RestrictedTextField(txtEmail);
		Email.setLimit(50);

		RestrictedTextField observacao = new RestrictedTextField(txtObservacao);
		observacao.setLimit(250);

		chckbxNewCheckBox = new JCheckBox("Pessoa Juridica");
		chckbxNewCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtRazao.setEditable(true);
				txtFantasia.setEditable(true);
				txtCNPJ.setEditable(true);
				txtInscricao.setEditable(true);
			}
		});
		chckbxNewCheckBox.setBounds(8, 265, 132, 24);
		contentPanel.add(chckbxNewCheckBox);
	}

	void limpar() {
		txtID.setText(null);
		txtCEP.setText(null);
		txtEndereco.setText(null);
		txtNumero.setText(null);
		txtComplemento.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		txtNome.setText(null);
		txtTelefone.setText(null);
		txtWhatsapp.setText(null);
		txtRazao.setText(null);
		txtFantasia.setText(null);
		txtCNPJ.setText(null);
		txtInscricao.setText(null);
		txtCPF.setText(null);
		txtEmail.setText(null);
		txtObservacao.setText(null);
		txtCliente.setText(null);
		txtCliente.requestFocus();
		cboUf.setSelectedItem("AC");
		txtRazao.setEditable(false);
		txtFantasia.setEditable(false);
		txtCNPJ.setEditable(false);
		txtInscricao.setEditable(false);
		((DefaultTableModel) table.getModel()).setRowCount(0);

		txtID.setEditable(false);
		btnAdicionar.setEnabled(true);
		btnDelete.setEnabled(false);
		btnUpdate.setEnabled(false);

		chckbxNewCheckBox.setSelected(false); // desmarcar a caixa check
	}

	void buscarCEP() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCEP.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (@SuppressWarnings("unchecked")
			Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();

				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUf.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {
						txtNumero.requestFocus();
					} else {

						JOptionPane.showMessageDialog(null, "CEP não encontrado");
						txtNumero.setText(null);
						cboUf.setSelectedItem("AC");
					}
				}
			}
			txtCEP.requestFocus();
			txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	DAO dao = new DAO();
	private JButton btnAdicionar;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnLimpar;
	private JTable table;

	void pesquisar() {

		if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o ID");
			txtID.requestFocus();

		} else {
			String read = "select * from clientes where idFor = ?";

			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read);

				pst.setString(1, txtID.getText());

				ResultSet rs = pst.executeQuery();

				if (rs.next()) {

					txtCEP.setText(rs.getString(2));
					txtEndereco.setText(rs.getString(3));
					txtNumero.setText(rs.getString(4));
					txtComplemento.setText(rs.getString(5));
					txtBairro.setText(rs.getString(6));
					txtCidade.setText(rs.getString(7));
					cboUf.setSelectedItem(rs.getString(8));
					txtNome.setText(rs.getString(9));
					txtTelefone.setText(rs.getString(10));
					txtWhatsapp.setText(rs.getString(11));
					txtEmail.setText(rs.getString(12));
					txtCPF.setText(rs.getString(18));
					txtRazao.setText(rs.getString(14));
					txtFantasia.setText(rs.getString(15));
					txtCNPJ.setText(rs.getString(16));
					txtInscricao.setText(rs.getString(17));
					txtObservacao.setText(rs.getString(13));

					txtID.setEditable(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);

					btnAdicionar.setEnabled(false);

				} else {
					JOptionPane.showMessageDialog(null, "Cliente inexistente");
					btnUpdate.setEnabled(false);
					btnDelete.setEnabled(false);
					btnAdicionar.setEnabled(true);
					txtID.setText(null);
					txtNome.requestFocus();

				}
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	void atualizarClientes() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Cliente");
			txtNome.requestFocus();

		} else if (txtTelefone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Telefone");
			txtTelefone.requestFocus();

		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o E-mail");
			txtEmail.requestFocus();

		} else if (txtCPF.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF");
			txtCPF.requestFocus();

		} else {
			String update = "update clientes set cep = ?, endereço = ?, numero = ? , complemento = ?, bairro = ?, cidade = ?, uf = ?, nomeContato = ?, fone = ?, whatsapp = ?, email = ?, cpf = ?, razao = ?, fantasia = ?, CNPJ = ?, inscricao = ?, obs = ? where idFor = ?";

			try {
				// Abrir a conexão
				Connection con = dao.conectar();
				// Preparar a query (instrução SQL)
				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtCEP.getText());
				pst.setString(2, txtEndereco.getText());
				pst.setString(3, txtNumero.getText());
				pst.setString(4, txtComplemento.getText());
				pst.setString(5, txtBairro.getText());
				pst.setString(6, txtCidade.getText());
				pst.setString(7, cboUf.getSelectedItem().toString());
				pst.setString(8, txtNome.getText());
				pst.setString(9, txtTelefone.getText());
				pst.setString(10, txtWhatsapp.getText());
				pst.setString(11, txtEmail.getText());
				pst.setString(12, txtCPF.getText());
				pst.setString(13, txtRazao.getText());
				pst.setString(14, txtFantasia.getText());
				pst.setString(15, txtCNPJ.getText());
				pst.setString(16, txtInscricao.getText());
				pst.setString(17, txtObservacao.getText());
				pst.setString(18, txtID.getText());

				// Executar a query e confirmar as alterações no banco
				int executa = pst.executeUpdate();
				// System.out.println(executa);
				if (executa == 1) {
					JOptionPane.showMessageDialog(null, "Cliente Atualizado com Sucesso!");
					limpar();
					txtCliente.setText(null);
				} else {
					JOptionPane.showMessageDialog(null, "Erro: Cliente não atualizado!");
				}
				// Encerrar a conexão
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Cliente não adicionado - Cliente ja cadastrado");
				txtNome.setText(null);
				txtNome.requestFocus();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	void excluirClientes() {
		// System.out.println("teste do botão excluir");
		// validação (confirmação)
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste Cliente ?", "ATENÇÃO!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from clientes where idFor = ?";
			try {
				// abrir a conexão
				Connection con = dao.conectar();
				// preparar a query
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				// executar o comando sql e confirmar a exclusão
				int confirmaExcluir = pst.executeUpdate();
				if (confirmaExcluir == 1) {
					limpar();
					JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso");

				}
				// encerrar a conexão
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Cliente não Excluido! - Cliente vinculado a uma O.S");
				txtNome.setText(null);
				txtNome.requestFocus();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	void cadastrarClientes() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Cliente");
			txtNome.requestFocus();

		} else if (txtTelefone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Telefone");
			txtTelefone.requestFocus();

		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Email");
			txtEmail.requestFocus();

		} else if (txtCEP.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP");
			txtCEP.requestFocus();

		} else if (txtCPF.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF");
			txtCPF.requestFocus();

		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço");
			txtEndereco.requestFocus();

		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Número");
			txtNumero.requestFocus();

		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Bairro");
			txtBairro.requestFocus();

		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cidade");
			txtCidade.requestFocus();

		} else {
			String create = "insert into clientes (cep,endereço,numero,complemento,bairro,cidade,uf,nomeContato,fone,whatsapp,email,obs,razao,fantasia,cnpj,inscricao,cpf) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, txtCEP.getText());
				pst.setString(2, txtEndereco.getText());
				pst.setString(3, txtNumero.getText());
				pst.setString(4, txtComplemento.getText());
				pst.setString(5, txtBairro.getText());
				pst.setString(6, txtCidade.getText());
				pst.setString(7, cboUf.getSelectedItem().toString());
				pst.setString(8, txtNome.getText());
				pst.setString(9, txtTelefone.getText());
				pst.setString(10, txtWhatsapp.getText());
				pst.setString(11, txtEmail.getText());
				pst.setString(12, txtObservacao.getText());
				pst.setString(13, txtRazao.getText());
				pst.setString(14, txtFantasia.getText());
				pst.setString(15, txtCNPJ.getText());
				pst.setString(16, txtInscricao.getText());
				pst.setString(17, txtCPF.getText());

				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Cliente Adicionado");
					limpar();
					adicionarOs();
					pesquisar();

				}
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Cliente Não Adicionado - Cliente ja cadastrado");
				limpar();
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}

	void setarCampos() {
		int setar = table.getSelectedRow();
		txtID.setText(table.getModel().getValueAt(setar, 3).toString());
		pesquisar();
	}

	private void pesquisarFornecedor() {
		String read3 = "select nomeContato as Nome, cpf as CPF, razao as Razão, idFor as ID from clientes where nomeContato like ?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read3);
			pst.setString(1, txtCliente.getText() + "%"); // Aten��o "%"
			ResultSet rs = pst.executeQuery();
			// Uso da biblioteca rs2xml para "popular" a tabela
			table.setModel(DbUtils.resultSetToTableModel(rs));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void adicionarOs() {

		String read = "SELECT MAX(idFor) FROM clientes;  ";

		try {

			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read);
			// pst.setString(0, txtOs.getText());

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				txtID.setText(rs.getString(1));
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);

		}

	}

}
