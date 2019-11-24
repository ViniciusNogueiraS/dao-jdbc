package model.dao.impl;

import application.DB;
import application.DbException;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
    
    private Connection connection;

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public void insert(Department dep) {
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement("insert into department(Name) "
                + "values(?)",
                Statement.RETURN_GENERATED_KEYS);
            
            st.setString(1, dep.getName());
            
            int linhasAfetadas = st.executeUpdate();
            
            if(linhasAfetadas > 0){//adicionando a chave primária gerada automaticamente pelo auto_invrement do bd!
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    dep.setId(id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbException("Erro inesperado! Nenhuma linha alterada!");
            }
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Department dep) {
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement("update department set Name = ? "
                    + "where Id = ?");
            
            st.setString(1, dep.getName());
            st.setInt(2, dep.getId());
            
            st.executeUpdate();
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement("delete from department where Id = ?");
            
            st.setInt(1, id);
            
            st.executeUpdate();
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null; 
        try{
            st = connection.prepareStatement("select * from department where department.Id = ?");
            
            //select * from department where department.Id = ?
            
            st.setInt(1, id);
            rs = st.executeQuery();
            if(rs.next()){//verifica se veio algum resultado!
                Department dep = instanciaDepartment(rs);
                return dep;
            }
            return null;
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null; 
        try{
            st = connection.prepareStatement("SELECT * FROM department ORDER BY Name");
            
            //SELECT * FROM department ORDER BY Name
            
            rs = st.executeQuery();
            
            List<Department> list = new ArrayList<>();
            
            while(rs.next()){//verifica se veio algum resultado!
                Department dep = instanciaDepartment(rs);
                list.add(dep);
            }
            return list;
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    private Department instanciaDepartment(ResultSet rs) throws SQLException{
        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));
        return dep;
    }
}
