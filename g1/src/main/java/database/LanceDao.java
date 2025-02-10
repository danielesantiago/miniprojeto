package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Lance;

public class LanceDao {

    // Constantes de conexão
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_portal_lances?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "freedb_nicolasbenitiz";
    private static final String DB_PASSWORD = "Qd7ffH&?D5M9a5U";

    // Queries SQL
    private static final String SEARCH_LANCES_SQL = "SELECT * FROM lance ORDER BY valor_lance DESC";
    private static final String INSERT_LANCES_SQL = "INSERT INTO lance (username, codigo_produto, valor_lance) VALUES (?, ?, ?);";
    private static final String SEARCH_USERNAME_SQL = "SELECT username FROM usuario";
    private static final String SEARCH_CODIGO_SQL = "SELECT codigo FROM produto";
    private static final String SEARCH_MAIOR_LANCE = "SELECT MAX(valor_lance) AS maiorLance FROM lance WHERE codigo_produto = ?";

    /**
     * Cria e retorna uma conexão com o banco de dados.
     */
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        // Carrega o driver JDBC
        Class.forName(JDBC_DRIVER);
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * Retorna uma lista de lances ordenados por valor de forma decrescente.
     */
    public List<Lance> buscarLances() throws ClassNotFoundException {
        List<Lance> lances = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SEARCH_LANCES_SQL);
             ResultSet result = preparedStatement.executeQuery()) {

            while (result.next()) {
                Lance lance = new Lance();
                lance.setNome(result.getString("username"));
                lance.setProduto(result.getString("codigo_produto"));
                lance.setValor_lance(result.getFloat("valor_lance"));
                lances.add(lance);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return lances;
    }

    /**
     * Registra um novo lance no banco de dados.
     *
     * @param lance Objeto Lance com os dados a serem inseridos.
     * @return número de linhas afetadas.
     */
    public int registraLance(Lance lance) throws ClassNotFoundException {
        int resultado = 0;

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_LANCES_SQL)) {

            preparedStatement.setString(1, lance.getNome());
            preparedStatement.setString(2, lance.getProduto());
            preparedStatement.setFloat(3, lance.getValor_lance());

            resultado = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return resultado;
    }

    /**
     * Retorna uma lista de nomes de usuários existentes no banco de dados.
     */
    public List<String> usuariosBd() throws ClassNotFoundException {
        List<String> usuarios = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SEARCH_USERNAME_SQL);
             ResultSet result = preparedStatement.executeQuery()) {

            while (result.next()) {
                usuarios.add(result.getString("username"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return usuarios;
    }

    /**
     * Retorna uma lista de códigos de produtos existentes no banco de dados.
     */
    public List<String> produtosBd() throws ClassNotFoundException {
        List<String> produtos = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SEARCH_CODIGO_SQL);
             ResultSet result = preparedStatement.executeQuery()) {

            while (result.next()) {
                produtos.add(result.getString("codigo"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return produtos;
    }

    /**
     * Imprime detalhes de uma SQLException.
     */
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.err.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

     /**
     * Busca o maior lance já realizado para um determinado produto.
     * @param produto Código do produto.
     * @return O valor do maior lance ou null se não houver nenhum.
     */
    public Float buscarMaiorLancePorProduto(String produto) throws ClassNotFoundException {
        Float maiorLance = null;
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SEARCH_MAIOR_LANCE)) {
             
             preparedStatement.setString(1, produto);
             try (ResultSet result = preparedStatement.executeQuery()) {
                 if (result.next()) {
                     maiorLance = result.getFloat("maiorLance");
                     // Se nenhum lance foi encontrado, result.getFloat retorna 0 mas podemos verificar se foi nulo
                     if (result.wasNull()) {
                         maiorLance = null;
                     }
                 }
             }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return maiorLance;
    }
}


