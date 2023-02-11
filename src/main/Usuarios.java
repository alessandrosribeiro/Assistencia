package main;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDialog;

import models.DAO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Atxy2k.CustomTextField.RestrictedTextField;

import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Cursor;

public class Usuarios extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuarios dialog = new Usuarios();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Usuarios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/lojinha.png")));
		setModal(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// setarIp();
			}
		});
		setResizable(false);
		setTitle("Cadastro de Usuários");
		setBounds(100, 100, 534, 349);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(400, 27, 21, 14);
		getContentPane().add(lblNewLabel);

		txtId = new JTextField();
		txtId.setToolTipText("ID do Usuário");
		txtId.setEditable(false);
		txtId.setBounds(419, 24, 46, 20);
		getContentPane().add(txtId);
		txtId.setColumns(10);

		btnBuscar = new JButton("");
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/search.png")));
		btnBuscar.setToolTipText("Buscar Usuário");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarUsuario();
			}
		});
		btnBuscar.setBounds(216, 16, 46, 35);
		getContentPane().add(btnBuscar);

		JLabel lblNewLabel_1 = new JLabel("Usu\u00E1rio");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(31, 75, 46, 14);
		getContentPane().add(lblNewLabel_1);

		txtUsuario = new JTextField();
		txtUsuario.setToolTipText("Usuario");
		txtUsuario.setBounds(87, 72, 378, 20);
		getContentPane().add(txtUsuario);
		txtUsuario.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Login");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(31, 27, 46, 14);
		getContentPane().add(lblNewLabel_2);

		txtLogin = new JTextField();
		txtLogin.setToolTipText("Login");
		txtLogin.setBounds(88, 24, 118, 20);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Senha");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setBounds(31, 123, 46, 14);
		getContentPane().add(lblNewLabel_3);

		btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setToolTipText("Adicionar Usuário");
		btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/add48x48.png")));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarUsuario();
			}
		});
		btnAdicionar.setBounds(409, 235, 89, 63);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setEnabled(false);
		btnEditar.setToolTipText("Atualizar Usuário");
		btnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/update48x48.png")));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Verificar se o checkbox est� selecionado
				// Para verificar se n�o est� selecionado use NOT (!)
				if (chckSenha.isSelected()) {
					alterarUsuarioSenha();
				} else {
					alterarUsuario();
				}
			}
		});
		btnEditar.setBounds(308, 235, 89, 63);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setEnabled(false);
		btnExcluir.setToolTipText("Deletar Usuário");
		btnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/img/delete48x48.png")));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});
		btnExcluir.setBounds(207, 235, 89, 63);
		getContentPane().add(btnExcluir);

		txtPassword = new JPasswordField();
		txtPassword.setToolTipText("Senha");
		txtPassword.setBounds(87, 120, 263, 20);
		getContentPane().add(txtPassword);

		JLabel lblNewLabel_4 = new JLabel("Perfil");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setBounds(31, 170, 38, 14);
		getContentPane().add(lblNewLabel_4);

		cboPerfil = new JComboBox();
		cboPerfil.setToolTipText("Perfil do Usuário");
		cboPerfil.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] {"Recepcionista", "Tecnico", "Gerente", "Administrador"}));
		cboPerfil.setBounds(87, 166, 119, 22);
		getContentPane().add(cboPerfil);

		chckSenha = new JCheckBox("Alterar senha");
		chckSenha.setToolTipText("Selecione para Alterar a Senha");
		chckSenha.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// fazer o check na caixa Jcheckbox
				txtPassword.setEditable(true);
				txtPassword.setText(null);
				txtPassword.requestFocus();
				txtPassword.setBackground(Color.LIGHT_GRAY);
			}
		});
		chckSenha.setVisible(false);
		chckSenha.setBounds(378, 119, 120, 23);
		getContentPane().add(chckSenha);
		
		btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setToolTipText("Nova Consulta");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
			
		});
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/search-48.png")));
		btnLimpar.setBounds(33, 243, 55, 55);
		getContentPane().add(btnLimpar);

		RestrictedTextField usuario = new RestrictedTextField(txtUsuario);
		usuario.setOnlyNums(false);
		usuario.setLimit(50);
		
		RestrictedTextField login = new RestrictedTextField(txtLogin);
		login.setOnlyNums(false);
		login.setLimit(20);
		

	}// fim do construtor

	DAO dao = new DAO();

	/*
	 * private void setarIp() {
	 * dao.setUrl("jdbc:mysql://10.26.49.222:3306/dbestoque"); }
	 */

	/**
	 * Pesquisa do usuario por id
	 */
	private void pesquisarUsuario() {
		// System.out.println("Teste do bot�o pesquisar usu�rio");
		String read = "select * from usuario where login = ?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, txtLogin.getText());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				txtUsuario.setText(rs.getString(2));
				txtId.setText(rs.getString(1));
				txtPassword.setText(rs.getString(4));
				cboPerfil.setSelectedItem(rs.getString(5));
				// exibir a caixa checkbox
				chckSenha.setVisible(true);
				// desativar a edi��o da senha
				txtPassword.setEditable(false);
				txtLogin.setEditable(false);
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnAdicionar.setEnabled(false);
				btnBuscar.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(null, "Usuario inexistente");
				limparCampos();
				txtLogin.requestFocus();
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}// fim do m�todo pesquisarUsuario()

	@SuppressWarnings("deprecation")
	private void adicionarUsuario() {
		// valida��o
		// System.out.println("teste do bot�o adicionar");
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login");
			txtLogin.requestFocus();
		} else if (txtUsuario.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Usuario");
			txtUsuario.requestFocus();
		} else if (txtPassword.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Senha");
			txtPassword.requestFocus();
		} else {
		
			String create = "insert into usuario (usuario,login,senha,perfil) values (?,?,md5(?),?)";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, txtUsuario.getText());
			pst.setString(2, txtLogin.getText());
			// captura segura de senha
			String capturaSenha = new String(txtPassword.getPassword());
			pst.setString(3, capturaSenha);
			pst.setString(4, cboPerfil.getSelectedItem().toString());
			// execu��o e confirma��o da query
			int confirma = pst.executeUpdate();
			// apoio ao entendimento da l�gica
			// System.out.println(confirma);
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Usuario adicionado com sucesso");
			} else {
				JOptionPane.showMessageDialog(null, "ERRO - Usuario nao adicionado");
			}
			limparCampos();
			con.close();

		} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
			JOptionPane.showMessageDialog(null, "Usuario nao adicionado - Login existente");
			txtLogin.setText(null);
			txtLogin.requestFocus();
		} catch (Exception e2) {
			System.out.println(e2);
		}
	}
	}

	private void alterarUsuario() {
		// System.out.println("teste do bot�o editar");
		// valida��o de campos
		String update = "update usuario set usuario = ?, login = ?, perfil = ? where id = ?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(update);
			pst.setString(1, txtUsuario.getText());
			pst.setString(2, txtLogin.getText());
			pst.setString(3, cboPerfil.getSelectedItem().toString());
			pst.setString(4, txtId.getText());
			// execu��o e confirma��o
			int confirma = pst.executeUpdate();
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Dados do usuario alterados com sucesso");
			} else {
				JOptionPane.showMessageDialog(null, "ERRO - Dados do usuario nao foram alterados");
			}
			limparCampos();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void alterarUsuarioSenha() {
		// System.out.println("teste do bot�o editar");
		// valida��o de campos
		String update = "update usuario set usuario = ?, login = ?, senha = md5(?), perfil = ? where id = ?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(update);
			pst.setString(1, txtUsuario.getText());
			pst.setString(2, txtLogin.getText());
			// captura segura de senha
			String capturaSenha = new String(txtPassword.getPassword());
			pst.setString(3, capturaSenha);
			pst.setString(4, cboPerfil.getSelectedItem().toString());
			pst.setString(5, txtId.getText());
			// execu��o e confirma��o
			int confirma = pst.executeUpdate();
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Dados do usuario alterados com sucesso");
			} else {
				JOptionPane.showMessageDialog(null, "ERRO - Dados do usuario nao foram alterados");
			}
			limparCampos();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void excluirUsuario() {
		// System.out.println("teste do bot�o excluir");
		// valida��o (confirma��o de exclus�o)
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusao deste usuario ?", "ATENCAO!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// l�gica principal
			String delete = "delete from usuario where id = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtId.getText());
				// executar e confirmar a exclus�o
				int excluido = pst.executeUpdate();
				if (excluido == 1) {
					limparCampos();
					JOptionPane.showMessageDialog(null, "Usuario excluido com sucesso");
				} else {
					JOptionPane.showMessageDialog(null, "Erro na exclusao do usuario");
				}
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void limparCampos() {
		txtId.setText(null);
		txtUsuario.setText(null);
		txtLogin.setText(null);
		txtPassword.setText(null);
		txtPassword.setBackground(Color.WHITE);
		txtPassword.setEditable(true);
		cboPerfil.setSelectedItem("Recepcionista");
		chckSenha.setSelected(false); // desmarcar a caixa check
		chckSenha.setVisible(false);
		txtLogin.setEditable(true);
		txtLogin.requestFocus();
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnAdicionar.setEnabled(true);
		btnBuscar.setEnabled(true);
	}

	private JTextField txtId;
	private JTextField txtUsuario;
	private JTextField txtLogin;
	private JPasswordField txtPassword;
	@SuppressWarnings("rawtypes")
	private JComboBox cboPerfil;
	private JCheckBox chckSenha;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JButton btnAdicionar;
	private JButton btnLimpar;
	private JButton btnBuscar;
}// fim do c�digo
