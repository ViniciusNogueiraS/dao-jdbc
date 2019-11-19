package model.dao.impl;

import application.DB;
import application.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
            
            /*
            select seller.*, department.Name as dpName from seller inner join department on seller.DepartmentId = department.Id where seller.Id = 3
            */
            
            st.setInt(1, id);
            rs = st.executeQuery();
            if(rs.next()){//verifica se veio algum resultado!
                Department dep = new Department();
                dep.setId(rs.getInt("DepartmentId"));
                dep.setName(rs.getString("dpName"));
                
                Seller seller = new Seller();
                seller.setId(rs.getInt("Id"));
                seller.setName(rs.getString("Name"));
                seller.setEmail(rs.getString("Email"));
                seller.setBaseSalary(rs.getDouble("BaseSalary"));
                seller.setBirthDate(rs.getDate("BirthDate"));
                seller.setDepartment(dep);
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
    public List<Seller> findAll() {
        return null;
        
    }
    
}
