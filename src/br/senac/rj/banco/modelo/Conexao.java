package br.senac.rj.banco.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Classe responsavel por estabelecer a conexao com o banco de dados
 */
public class Conexao {
	/**
     * Metodo utilizado para estabelecer a conexao com o banco de dados
     *
     * @return A conexao estabelecida com o banco de dados
     */
	public static Connection conectaBanco() {
		Connection conexao = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/campeonato"; // URL do banco de dados
			String user = "root"; // nome do usuario do banco
			String password = ""; // senha do banco
			conexao = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException erro) {
			System.out.println("Driver nao encontrado: " + erro);
		} catch (SQLException erro) {
			System.out.println("Erro de conexao ao banco de dados: " + erro.toString());
		} catch (Exception erro) {
			System.out.println("Erro nao identificado: " + erro.toString());
		} 
		return conexao;
	}
	/**
	 * Metodo utilizado para fechar a conexao com o banco de dados
	 * @param conexao A conexao a ser fechada
	 */
	public static void fechaConexao(Connection conexao) {
		try {
			conexao.close();
		} catch (Exception erro) {
			System.out.println("Erro ao fechar a conexao: " + erro.toString());
		}
	}
}