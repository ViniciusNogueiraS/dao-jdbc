package model.dao.impl;

import application.DB;
import application.DbException;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
    
    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public void insert(Seller seller) {
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement("insert into seller(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                + "values(?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            
            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());
            
            int linhasAfetadas = st.executeUpdate();
            
            if(linhasAfetadas > 0){//adicionando a chave primária gerada automaticamente pelo auto_invrement do bd!
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    seller.setId(id);
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
    public void update(Seller seller) {
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement("update seller set Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                    + "where Id = ?");
            
            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());
            st.setInt(6, seller.getId());
            
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
            st = connection.prepareStatement("delete from seller where Id = ?");
            
            st.setInt(1, id);
            
            st.executeUpdate();
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null; 
        try{
            st = connection.prepareStatement("SELECT seller.*, department.Name AS dpName "
                    + "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?");
            
            //select seller.*, department.Name as dpName from seller inner join department on seller.DepartmentId = department.Id where seller.Id = 3
            
            st.setInt(1, id);
            rs = st.executeQuery();
            if(rs.next()){//verifica se veio algum resultado!
                Department dep = instanciaDepartment(rs);
                Seller seller = instanciaSeller(rs, dep);
                return seller;
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
    public List<Seller> findByDepartment(Department department){
        PreparedStatement st = null;
        ResultSet rs = null; 
        try{
            st = connection.prepareStatement("SELECT seller.*, department.Name AS dpName "
                    + "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id "
                    + "WHERE DepartmentId = ? order by Name");
            
            //select seller.*, department.Name as dpName from seller inner join department on seller.DepartmentId = department.Id where DepartmentId = 3 order by Name
            
            st.setInt(1, department.getId());
            rs = st.executeQuery();
            
            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            
            while(rs.next()){//verifica se veio algum resultado!
                
                Department dep = map.get(rs.getInt("DepartmentId"));
                
                if(dep == null){//verifica se o department já foi instanciado!
                    dep = instanciaDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                
                Seller seller = instanciaSeller(rs, dep);
                list.add(seller);
            }
            return list;
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null; 
        try{
            st = connection.prepareStatement("SELECT seller.*, department.Name AS dpName "
                    + "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id "
                    + "order by Name");
            
            //select seller.*, department.Name as dpName from seller inner join department on seller.DepartmentId = department.Id order by Name
            
            rs = st.executeQuery();
            
            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            
            while(rs.next()){//verifica se veio algum resultado!
                
                Department dep = map.get(rs.getInt("DepartmentId"));
                
                if(dep == null){//verifica se o department já foi instanciado!
                    dep = instanciaDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                
                Seller seller = instanciaSeller(rs, dep);
                list.add(seller);
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
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("dpName"));
        return dep;
    }
    
    private Seller instanciaSeller(ResultSet rs, Department dep) throws SQLException{
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        seller.setBirthDate(rs.getDate("BirthDate"));
        seller.setDepartment(dep);
        return seller;
    }
}
