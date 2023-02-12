package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;
import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel contentPane;
	JButton btnClientes;
	JButton btnOrcamentos;
	JButton btnUsuarios;
	JButton btnRelatorios;
	public Object lblUsuario_1;
	private JPanel panelUsuarios;
	private JLabel lblStatus;
	private JLabel lblNewLabel;
	JLabel lblUsuario;
	JButton btnFicha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
				lblStatus.setText(formatador.format(data));
			}
		});
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/img/lojinha.png")));
		setTitle("Assistência Técnica");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 576, 502);
		contentPane = new JPanel();
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnClientes = new JButton("");
		btnClientes.setEnabled(false);
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cliente cliente = new Cliente();
				cliente.setVisible(true);
			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setToolTipText("Cadastro de Cliente");
		btnClientes.setIcon(new ImageIcon(Main.class.getResource("/img/cliente.png")));
		btnClientes.setBounds(42, 37, 135, 135);
		contentPane.add(btnClientes);
		
		btnUsuarios = new JButton("");
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setEnabled(false);
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setToolTipText("Cadastro de Usuario");
		btnUsuarios.setIcon(new ImageIcon(Main.class.getResource("/img/usuario.png")));
		btnUsuarios.setBounds(216, 213, 135, 135);
		contentPane.add(btnUsuarios);
		
		btnOrcamentos = new JButton("");
		btnOrcamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Orcamento orcamento = new Orcamento();
				orcamento.setVisible(true);
			}
		});
		btnOrcamentos.setEnabled(false);
		btnOrcamentos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOrcamentos.setToolTipText("Orçamento");
		btnOrcamentos.setIcon(new ImageIcon(Main.class.getResource("/img/orcamento128x128.png")));
		btnOrcamentos.setBounds(216, 37, 135, 135);
		contentPane.add(btnOrcamentos);
		
		btnRelatorios = new JButton("");
		btnRelatorios.setEnabled(false);
		btnRelatorios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorios.setToolTipText("Relatório");
		btnRelatorios.setIcon(new ImageIcon(Main.class.getResource("/img/relatório.png")));
		btnRelatorios.setBounds(42, 213, 135, 135);
		contentPane.add(btnRelatorios);
		
		panelUsuarios = new JPanel();
		panelUsuarios.setLayout(null);
		panelUsuarios.setBackground(SystemColor.textHighlight);
		panelUsuarios.setBounds(0, 415, 560, 48);
		contentPane.add(panelUsuarios);
		
		lblStatus = new JLabel("New label");
		lblStatus.setToolTipText("Data");
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblStatus.setBounds(362, 0, 198, 48);
		panelUsuarios.add(lblStatus);
		
		lblNewLabel = new JLabel("Usuário:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 20, 78, 14);
		panelUsuarios.add(lblNewLabel);
		
		lblUsuario = new JLabel("");
		lblUsuario.setToolTipText("Usuário Logado");
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsuario.setBounds(66, 21, 201, 14);
		panelUsuarios.add(lblUsuario);
		
		btnFicha = new JButton("");
		btnFicha.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFicha.setEnabled(false);
		btnFicha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ficha ficha = new Ficha();
				ficha.setVisible(true);
			}
		});
		btnFicha.setIcon(new ImageIcon(Main.class.getResource("/img/ficha128x128.png")));
		btnFicha.setToolTipText("Ficha Técnica");
		btnFicha.setBounds(391, 37, 135, 135);
		contentPane.add(btnFicha);
	}
}
