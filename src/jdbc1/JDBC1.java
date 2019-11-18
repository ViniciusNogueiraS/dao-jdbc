package jdbc1;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*CONECTANDO E RESGATANDO DADOS
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        
        try{
            connection = DB.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery("select * from department");
            
            while(rs.next()){
                System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            DB.closeResultSet(rs);
            DB.closeStatement(st);
            DB.closeConnection();
        }*/
        
        /*CONECTANDO E INSERINDO DADOS
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Connection connection = null;
        PreparedStatement st = null;
        
        try{
            connection = DB.getConnection();
            st = connection.prepareStatement(
                    "INSERT INTO seller"
                    +"(Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES"
                    +"(?, ?, ?, ?, ?)");
            
            st.setString(1, "Caroline Purple");
            st.setString(2, "caroline@gmail.com");
            st.setDate(3, new java.sql.Date(sdf.parse("22/04/1988").getTime()));
            st.setDouble(4, 3000.0);
            st.setInt(5, 4);
            
            int linhasAlteradas = st.executeUpdate();
            
            System.out.print("Pronto! LINHAS ALTERADAS = " + linhasAlteradas);
            
        }catch(SQLException e){
            e.printStackTrace();
        }catch(ParseException e){
            e.printStackTrace();
        }finally{
            DB.closeStatement(st);
            DB.closeConnection();
        }
        */
        
        /*CONECTANDO E ATUALIZANDO DADOS
        Connection connection = null;
        PreparedStatement st = null;
        try{
            connection = DB.getConnection();
            st = connection.prepareStatement("update seller "
                    + "set BaseSalary = BaseSalary + ? "
                    + "where DepartmentId = ?");
            
            st.setDouble(1, 200.0);
            st.setInt(2, 2);
            
            int linhasAfetadas = st.executeUpdate();
            
            System.out.print("Pronto! LINHAS ALTERADAS = " + linhasAfetadas);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            DB.closeStatement(st);
            DB.closeConnection();
        }
        */
        
        /*CONECTANDO E DELETANDO DADOS
        Connection connection = null;
        PreparedStatement st = null;
        try{
            connection = DB.getConnection();
            st = connection.prepareStatement("delete from department"
                    + "where Id = ?");
            st.setInt(1, 3);
            
            int linhasAfetadas = st.executeUpdate();
            
            System.out.print("Pronto! REGISTROS ALTERADOS = " + linhasAfetadas);
        }catch(SQLException e){
            throw new DbIntegrityException(e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeConnection();
        }
        */
        Connection connection = null;
        Statement st = null;
        try{
            connection = DB.getConnection();
            
            connection.setAutoCommit(false);
            
            st = connection.createStatement();
            
            int linhas1 = st.executeUpdate("update seller set BaseSalary = 2090 where DepartmentId = 1");
            
            /*Fake Error
            int x = 1;
            if(x < 2){
                throw new SQLException("Fake Error!");
            }
            */
            
            int linhas2 = st.executeUpdate("update seller set BaseSalary = 3090 where DepartmentId = 2");
            
            connection.commit();
            
            System.out.println("linhas1: " + linhas1);
            System.out.println("linhas2: " + linhas2);
            
        }catch(SQLException e){
            try {
                connection.rollback();
                throw new DbException("Transação não realizada! " + e.getMessage());
            } catch (SQLException ex) {
                throw new DbException("Erro ao retornar a transação! " + e.getMessage());
            }
        }finally{
            DB.closeStatement(st);
            DB.closeConnection();
        }
        
    }
}
