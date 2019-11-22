package model.dao.impl;

import application.DB;
import application.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static javafx.scene.input.KeyCode.I;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
    
    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public void insert(Seller obj) {
        return;
    }

    @Override
    public void update(Seller obj) {
        return;
    }

    @Override
    public void deleteById(Integer id) {
        return;
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
