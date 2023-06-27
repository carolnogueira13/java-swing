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

public class Time {
	
	private int id;
	private String nome;
	private String tecnico;
	private String estado;
	private String cidade;
	public static String[] siglas = {"","AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MS","MT","MG","PA","PB","PR","PE",
							  "PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
	
	public Time() {
	}

	public Time(int id, String nome, String tecnico, String estado, String cidade) {
		this.id = id;
		this.nome = nome;
		this.tecnico = tecnico;
		this.estado = estado;
		this.cidade = cidade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTecnico() {
		return tecnico;
	}

	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

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
				System.out.println("AtualizaÃ§Ãµes realizadas");
				return true;
			}else {
				System.out.println("Erro ao fazer atualizaÃ§Ã£o");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar o time: " + e.toString());
			return false;
		}finally {
			Conexao.fechaConexao(conexao);
		}
	}
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
	
	public static List<Time> obterListaTimesDoBanco() {
        List<Time> times = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "select * from time";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) { // Verifica se há registros
                System.out.println("Não há times cadastrados!");
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
