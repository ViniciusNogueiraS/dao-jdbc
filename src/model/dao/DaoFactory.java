package model.dao;

import application.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    
    public static SellerDaoJDBC createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    }
    
    public static DepartmentDaoJDBC createDepartmentDao(){
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
