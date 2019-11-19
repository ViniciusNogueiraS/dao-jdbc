package model.dao;

import application.DB;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    
    public static SellerDaoJDBC createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    }
}
