package visao;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.sun.tools.javac.util.List;

import dao.EstudanteDAO;
import modelo.Estudante;

public class JanelaEstudante extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtCurso;
	private JTextField txtNota;
	private JTextField txtBusca;
	private JTable tabela;
	private JLabel lblStatus;
	
	private DefaultTableModel modelo;
	// Ponte com o banco: um unico objeto serve a janela inteira.
	private final EstudanteDAO dao = new EstudanteDAO();
	// Id do estudante selecionado na tabela. Zero = nenhum selecionado.
	private int idSelecionado = 0;
	
	
	
		
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaEstudante frame = new JanelaEstudante();
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
	public JanelaEstudante() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setBounds(68, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Curso:");
		lblNewLabel_1.setBounds(68, 36, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nota:");
		lblNewLabel_2.setBounds(68, 61, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Buscar:");
		lblNewLabel_3.setBounds(68, 86, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		txtNome = new JTextField();
		txtNome.setBounds(124, 8, 86, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		txtCurso = new JTextField();
		txtCurso.setBounds(124, 33, 86, 20);
		contentPane.add(txtCurso);
		txtCurso.setColumns(10);
		
		txtNota = new JTextField();
		txtNota.setBounds(124, 58, 86, 20);
		contentPane.add(txtNota);
		txtNota.setColumns(10);
		
		txtBusca = new JTextField();
		txtBusca.setBounds(124, 83, 86, 20);
		contentPane.add(txtBusca);
		txtBusca.setColumns(10);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrar();
			}
		});
		btnCadastrar.setBounds(25, 125, 89, 23);
		contentPane.add(btnCadastrar);
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		btnAlterar.setBounds(121, 125, 89, 23);
		contentPane.add(btnAlterar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setBounds(25, 159, 89, 23);
		contentPane.add(btnExcluir);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(121, 159, 89, 23);
		contentPane.add(btnLimpar);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtBusca.setText("");
				listar();
			}
		});
		btnListar.setBounds(25, 193, 89, 23);
		contentPane.add(btnListar);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtBusca.setText("");
				buscar();
			}
		});
		btnBuscar.setBounds(121, 193, 89, 23);
		contentPane.add(btnBuscar);
		
		tabela = new JTable();
		tabela.setBounds(252, 11, 172, 200);
		contentPane.add(tabela);
		
		lblStatus = new JLabel("");
		lblStatus.setBounds(252, 222, 46, 14);
		contentPane.add(lblStatus);

	}
	public JTextField getTxtNome() {
		return txtNome;
	}
	public JTextField getTxtCurso() {
		return txtCurso;
	}
	public JTextField getTxtNota() {
		return txtNota;
	}
	public JTextField getTxtBusca() {
		return txtBusca;
	}
	public JTable getTabela() {
		return tabela;
	}
	public JLabel getLblStatus() {
		return lblStatus;
	}
	
	
	
	
	
	
		private void preencherTabela(List<Estudante> lista) {
		modelo.setRowCount(0); // SEM ISTO A TABELA DUPLICA
		for (Estudante e : lista) {
		modelo.addRow(new Object[] {
		e.getId(), e.getNome(), e.getCurso(), e.getNota() });
		}
		lblStatus.setText(lista.size() + " estudante(s) na tabela.");
		}
		
		private void listar() {
			try {
			preencherTabela(dao.listar());
			} catch (SQLException ex) {
			erro("Erro ao listar", ex);
			}
			}
			private void buscar() {
			try {
			preencherTabela(dao.buscarPorNome(txtBusca.getText().trim()));
			} catch (SQLException ex) {
			erro("Erro ao buscar", ex);
			}
			}
		
		private Estudante lerFormulario() {
			String nome = txtNome.getText().trim();
			String curso = txtCurso.getText().trim();
			if (nome.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Preencha o nome!",
			"Aviso", JOptionPane.WARNING_MESSAGE);
			txtNome.requestFocus();
			return null;
			}
			double nota;
			try {
			nota = Double.parseDouble(txtNota.getText().trim().replace(",", "."));
			} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Nota deve ser um numero!",
			"Aviso", JOptionPane.WARNING_MESSAGE);
			txtNota.requestFocus();
			return null;
			}
			if (nota < 0 || nota > 10) {
			JOptionPane.showMessageDialog(this, "A nota deve estar entre 0 e 10.",
			"Aviso", JOptionPane.WARNING_MESSAGE);
			txtNota.requestFocus();
			return null;
			}
			return new Estudante(nome, curso, nota);
			}
			private void cadastrar() {
			Estudante e = lerFormulario();
			if (e == null) return; // invalido: a mensagem ja apareceu
			try {
			dao.inserir(e);
			JOptionPane.showMessageDialog(this,
			"Estudante cadastrado com o id " + e.getId() + ".");
			limpar();
			listar();
			} catch (SQLException ex) {
			erro("Erro ao cadastrar", ex);
			}
			}
			private void limpar() {
			idSelecionado = 0;
			txtNome.setText("");
			txtCurso.setText("");
			txtNota.setText("");
			tabela.clearSelection();
			txtNome.requestFocus();
			lblStatus.setText("Formulario limpo.");
			}
			private void erro(String contexto, SQLException ex) {
			JOptionPane.showMessageDialog(this,
			contexto + ": " + ex.getMessage(),
			"Erro", JOptionPane.ERROR_MESSAGE);
			lblStatus.setText(contexto + ".");
			}
		
			private void alterar() {
				if (idSelecionado == 0) {
				JOptionPane.showMessageDialog(this, "Selecione primeiro uma linha da tabela.",
				"Aviso", JOptionPane.WARNING_MESSAGE);
				return;
				}
				Estudante e = lerFormulario();
				if (e == null) return;
				// O id vem da SELECAO, nao do que esta digitado.
				e.setId(idSelecionado);
				try {
				dao.alterar(e);
				JOptionPane.showMessageDialog(this, "Estudante alterado.");
				limpar();
				listar();
				} catch (SQLException ex) {
				erro("Erro ao alterar", ex);
				}
				}
				private void excluir() {
				if (idSelecionado == 0) {
				JOptionPane.showMessageDialog(this, "Selecione primeiro uma linha da tabela.",
				"Aviso", JOptionPane.WARNING_MESSAGE);
				return;
				}
				int opcao = JOptionPane.showConfirmDialog(this,
				"Excluir o estudante " + txtNome.getText() + "?",
				"Confirmacao", JOptionPane.YES_NO_OPTION);
				if (opcao != JOptionPane.YES_OPTION) return;
				try {
				dao.excluir(idSelecionado);
				JOptionPane.showMessageDialog(this, "Estudante excluido.");
				limpar();
				listar();
				} catch (SQLException ex) {
				erro("Erro ao excluir", ex);
				}
				}
				
			private void carregarSelecionado() {
				int linha = tabela.getSelectedRow();
				if (linha < 0) return; // -1 = nenhuma linha selecionada
				idSelecionado = (int) modelo.getValueAt(linha, 0);
				txtNome.setText(String.valueOf(modelo.getValueAt(linha, 1)));
				txtCurso.setText(String.valueOf(modelo.getValueAt(linha, 2)));
				txtNota.setText(String.valueOf(modelo.getValueAt(linha, 3)));
				lblStatus.setText("Editando o estudante de id " + idSelecionado
				+ ". Altere os campos e clique em Alterar.");
			

			modelo = new DefaultTableModel(new String[] { "ID", "Nome", "Curso", "Nota" }, 0);
			tabela.setModel(modelo);
			tabela.setRowHeight(22);
			// Impede a edicao direta na celula: alterar passa pelo formulario.
			tabela.setDefaultEditor(Object.class, null);
			tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
				carregarSelecionado();
				}
				}
				});
			listar(); // a tabela ja abre preenchida
}}


