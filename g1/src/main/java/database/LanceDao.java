package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Lance;

public class LanceDao{

    public List<Lance> buscarLances() throws ClassNotFoundException {
        String SEARCH_LANCES_SQL = "SELECT * FROM lance ORDER BY valor_lance DESC";
        List<Lance> lances = new ArrayList<>();
        ResultSet result;
    
        try {
            // Carrega o driver
            Class.forName("com.mysql.cj.jdbc.Driver");
    
            // Conectar ao banco de dados
            //String connectionUrl = "jdbc:mysql://sql.freedb.tech:3306/freedb_portal_lances?useSSL=false&serverTimezone=UTC";
            //String user = "freedb_nicolasbenitiz";
            //String password = "N7QFUh87habd$WC";
            String connectionUrl = "jdbc:mysql://localhost/portal_lances", user = "root", password = "";
    
            Connection conn = DriverManager.getConnection(connectionUrl, user, password);
    
            PreparedStatement preparedStatement = conn.prepareStatement(SEARCH_LANCES_SQL);
            result = preparedStatement.executeQuery();
            
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
    
    
    public int registraLance(Lance lance) throws ClassNotFoundException{
        String INSERT_LANCES_SQL = "INSERT INTO lance" +
        " (username, codigo_produto, valor_lance) VALUES (?, ?, ?);";
        int result = 0;
    
        try{
            // Carrega driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conectar ao banco de dados
            //String connectionUrl = "jdbc:mysql://sql.freedb.tech:3306/freedb_portal_lances?useSSL=false&serverTimezone=UTC";
            //String user = "freedb_nicolasbenitiz";
            //String password = "N7QFUh87habd$WC";
            String connectionUrl = "jdbc:mysql://localhost/portal_lances", user = "root", password = "";

            Connection conn = DriverManager.getConnection(connectionUrl, user, password);

        
            // Cria statement para executar comando SQL
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_LANCES_SQL);
        
            // Insere na composição da string os dados que estarão no Bean
            preparedStatement.setString(1, lance.getNome());
            preparedStatement.setString(2, lance.getProduto());
            preparedStatement.setFloat(3, lance.getValor_lance());
            
            // Executa statement
            result = preparedStatement.executeUpdate();
        } catch(SQLException e){
            printSQLException(e);
        }
        return result;
    }

    public List<String> usuariosBd() throws ClassNotFoundException{
        String SEARCH_USERNAME_SQL = "SELECT username FROM usuario";
        List<String> usuarios = new ArrayList<>();
        ResultSet result;
    
        try{
            // Carrega driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conectar ao banco de dados
            //String connectionUrl = "jdbc:mysql://sql.freedb.tech:3306/freedb_portal_lances?useSSL=false&serverTimezone=UTC";
            //String user = "freedb_nicolasbenitiz";
            //String password = "N7QFUh87habd$WC";
            String connectionUrl = "jdbc:mysql://localhost/portal_lances", user = "root", password = "";

            Connection conn = DriverManager.getConnection(connectionUrl, user, password);

        
            // Cria statement para executar comando SQL
            PreparedStatement preparedStatement = conn.prepareStatement(SEARCH_USERNAME_SQL);

            // Executa statement
            result = preparedStatement.executeQuery();
            while(result.next()){
                usuarios.add(result.getString("username"));
            }

        } catch(SQLException e){
            printSQLException(e);
        }
        return usuarios;

    }

    public List<String> produtosBd() throws ClassNotFoundException{
        String SEARCH_CODIGO_SQL = "SELECT codigo FROM produto";
        List<String> produtos = new ArrayList<>();
        ResultSet result;
    
        try{
            // Carrega driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Dados da conexão
            //String connection = "jdbc:mysql://sql.freedb.tech:3306/freedb_portal_lances";
            //String user = "freedb_nicolasbenitiz";
            //String senha = "N7QFUh87habd$WC";
            String connectionUrl = "jdbc:mysql://localhost/portal_lances", user = "root", password = "";
        
            // Criando conexão
            Connection conn =  DriverManager.getConnection(connectionUrl, user, password);
        
            // Cria statement para executar comando SQL
            PreparedStatement preparedStatement = conn.prepareStatement(SEARCH_CODIGO_SQL);

            // Executa statement
            result = preparedStatement.executeQuery();
            while(result.next()){
                produtos.add(result.getString("codigo"));
            }

        } catch(SQLException e){
            printSQLException(e);
        }
        return produtos;

    }
    private void printSQLException(SQLException ex){
        for(Throwable e : ex){
            if(e instanceof SQLException){
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while(t != null){
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }// printSQLException
}