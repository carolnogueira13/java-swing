package br.senac.rj.banco.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
/**
 * 
 * A classe Time representa um time de futebol
 *
 */

public class Time {
	
	private int id; // ID do time
	private String nome; // O nome do time
	private String tecnico; // O nome do técnico do time
	private String estado; // O estado onde o time está localizado
	private String cidade; // A cidade do time
	public static String[] siglas = {"","AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MS","MT","MG","PA","PB","PR","PE",
							  "PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
	/**
	 * Construtor padrão da classe Time
	 */
	public Time() {
	}
	/**

	Construtor da classe Time que recebe os valores iniciais
	@param id O ID do time
	@param nome O nome do time
	@param tecnico O nome do técnico do time
	@param estado O estado onde o time está localizado
	@param cidade A cidade  do time
	*/

	public Time(int id, String nome, String tecnico, String estado, String cidade) {
		this.id = id;
		this.nome = nome;
		this.tecnico = tecnico;
		this.estado = estado;
		this.cidade = cidade;
	}
	/**
	 * Obtém o ID do time
	 * @return o ID do time
	 */
	public int getId() {
		return id;
	}
	/**
	 * Define o ID do time
	 * @param id o ID do time a ser definido
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Obtém o nome do time
	 * @return o nome do time
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * Define o nome do time
	 * @param nome o nome do time a ser definido
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * Obtém o nome do técnico do time
	 * @return o nome do técnico do time
	 */
	public String getTecnico() {
		return tecnico;
	}
	/**
	 * Define o nome do técnico do time
	 * @param tecnico o nome do técnico do time a ser definido
	 */
	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
	}
	/**
	 * Obtém o estado onde o time está localizado
	 * @return o estado onde o time está localizado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * Define o estado onde o time está localizado
	 * @param estado o estado onde o time está localizado a ser definido
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * Obtém a cidade do time
	 * @return a cidade do time
	 */
	public String getCidade() {
		return cidade;
	}
	/**
	 * Define a cidade do time
	 * @param cidade a cidade do time a ser definida
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Override
	public String toString() {
		return this.nome;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cidade, estado, id, nome, tecnico);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Time other = (Time) obj;
		return Objects.equals(cidade, other.cidade) && Objects.equals(estado, other.estado) && id == other.id
				&& Objects.equals(nome, other.nome) && Objects.equals(tecnico, other.tecnico);
	}
	/**
	 * Consulta um time no banco de dados pelo seu ID
	 * @param id O ID do time a ser consultado
	 * @return true se o time for encontrado e os dados forem atribuídos corretamente,
	 *  false caso contrário
	 */
	public boolean consultarTime(int id) {
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			String sql = "select * from time where id=?;";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(!rs.isBeforeFirst()) {
				return false;
			}
			else {
				while(rs.next()) {
					this.id = rs.getInt("id");
					this.nome = rs.getString("nome");
					this.tecnico = rs.getString("tecnico");
					this.cidade = rs.getString("cidade");
					this.estado = rs.getString("estado");
				}
				return true;
			}
			
		} catch (SQLException e) {
			System.out.println("Erro sql");
			return false;
		}finally {
			Conexao.fechaConexao(conexao);
		}
	}
/**
 * 
 * @param nome O nome do time a ser cadastrado
 * @param tecnico O nome do técnico do time
 * @param cidade A cidade do time
 * @param estado O estado do time
 * @return true se o cadastro for realizado com sucesso, false caso contrário
 */
	public boolean cadastrarTime(String nome, String tecnico, String cidade, String estado) {
		if(!Arrays.asList(Time.siglas).contains(estado)) {
			return false;
		}else if(nome.isBlank() || cidade.isBlank() || estado.isBlank()) {
			return false;
		}
		else {
			Connection conexao = null;
			try {
				conexao = Conexao.conectaBanco();
				String sql = "insert into time(nome,tecnico,cidade,estado) values (?, ?, ?, ?);";
				PreparedStatement ps = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, nome);
				ps.setString(2, tecnico);
				ps.setString(3, cidade);
				ps.setString(4, estado);
				int totalRegistrosModificados = ps.executeUpdate();
				if(totalRegistrosModificados>=1) {
					System.out.println("Cadastro realizado");
					return true;
				}else {
					System.out.println("Nao foi feito o cadastro");
					return false;
				}
			} catch (SQLException e) {
				System.out.println("Erro ao cadastrar o time: " + e.toString());
				return false;
			}finally {
				Conexao.fechaConexao(conexao);
			}
		}
	}
	/**
	 * 
	 * @param nome O nome do time a ser cadastrado
	 * @param cidade A cidade do time
	 * @param estado O estado do time
	 * @return true se o cadastro for realizado com sucesso, false caso contrário
	 */
	public boolean cadastrarTime(String nome, String cidade, String estado) {
		if(!Arrays.asList(Time.siglas).contains(estado)) {
			return false;
		}else if(nome.isBlank() || cidade.isBlank() || estado.isBlank()) {
			return false;
		}
		else {
			Connection conexao = null;
			try {
				conexao = Conexao.conectaBanco();
				String sql = "insert into time(nome,cidade,estado) values (?, ?, ?);";
				PreparedStatement ps = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, nome);
				ps.setString(2, cidade);
				ps.setString(3, estado);
				int totalRegistrosModificados = ps.executeUpdate();
				if(totalRegistrosModificados>=1) {
					System.out.println("Cadastro realizado");
					return true;
				}else {
					System.out.println("Nao foi feito o cadastro");
					return false;
				}
			} catch (SQLException e) {
				System.out.println("Erro ao cadastrar o time: " + e.toString());
				return false;
			}finally {
				Conexao.fechaConexao(conexao);
			}
		}
	}
	/**
	 * Atualiza as informações do time
	 * @return true se a atualização for realizada com sucesso,
	 *  false caso contrário
	 */
	public boolean atualizarTime() {
		Connection conexao= null;
		try {
			conexao = Conexao.conectaBanco();
			String sql = "update time set nome=?, tecnico =?,cidade=?, estado=? where id =?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, this.nome);
			ps.setString(2, this.tecnico);
			ps.setString(3, this.cidade);
			ps.setString(4, this.estado);
			ps.setInt(5, this.id);
			int totalRegistrosModificados = ps.executeUpdate();
			if(totalRegistrosModificados>=1) {
				System.out.println("Atualizações realizadas");
				return true;
			}else {
				System.out.println("Erro ao fazer atualização");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar o time: " + e.toString());
			return false;
		}finally {
			Conexao.fechaConexao(conexao);
		}
	}
	/**
	 * Deleta o time 
	 * @return true se o time for deletado com sucesso, false caso contrário
	 */
	public boolean deletarTime() {
		Connection conexao = null;
		
		try {
			conexao = Conexao.conectaBanco();
			String sql = "Delete from time where id =?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, this.id);
			int totalRegistrosModificados = ps.executeUpdate();
			if(totalRegistrosModificados>=1) {
				System.out.println("Time deletado");
				return true;
			}else {
				System.out.println("Erro ao fazer delete");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao deletar o time: " + e.toString());
			return false;
		}finally {
			Conexao.fechaConexao(conexao);
		}
		
	}
	/**
	 * Obtém a lista de todos os times cadastrados no banco de dados
	 * @return a lista de times cadastrados, ou null se não houver times cadastrados
	 */
	public static List<Time> obterListaTimesDoBanco() {
        List<Time> times = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "select * from time";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) { // Verifica se ha registros
                System.out.println("N�o h� times cadastrados!");
                return null;
            } else {
                // Efetua a leitura dos registros da tabela
                while (rs.next()) {
                	int id = rs.getInt("id");
                	String nome = rs.getString("nome");
                    String tecnico = rs.getString("tecnico");
                    String estado = rs.getString("estado");
                    String cidade = rs.getString("cidade");
                    Time time = new Time(id, nome, tecnico, estado, cidade);
                    times.add(time);
                }
                return times;
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao consultar times: " + erro.toString());
            return null;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }
	
	
	
	
	

}
