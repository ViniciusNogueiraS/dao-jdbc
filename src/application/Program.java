package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;

public class Program {

    public static void main(String[] args) {
        /*
        SellerDao sellerDao = DaoFactory.createSellerDao();
        
        System.out.println("=== TESTE 1: Seller findByID ===");
        Seller seller = sellerDao.findById(2);
        System.out.println(seller);
        
        System.out.println("\n=== TESTE 2: Seller findByDepartment ===");
        Department dep = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(dep);
        for(Seller obj : list){
            System.out.println(obj);
        }
        
        System.out.println("\n=== TESTE 3: Seller findAll ===");
        list = sellerDao.findAll();
        for(Seller obj : list){
            System.out.println(obj);
        }
       
        System.out.println("\n=== TESTE 4: Seller insert ===");
        seller = new Seller(null, "Greg White", "greg@gmail.com", new Date(), 4200.0, dep);
        sellerDao.insert(seller);
        System.out.println("Inserido! Novo ID = "+seller.getId());
        
        System.out.println("\n=== TESTE 5: Seller update ===");
        seller = sellerDao.findById(1);
        seller.setName("Martha Wine");
        sellerDao.update(seller);
        System.out.println("Registro atualizado!");
        
        
        System.out.println("\n=== TESTE 6: Seller delete ===");
        System.out.print("Entre com o id do vendedor a ser deletado: ");
        int n = sc.nextInt();
        sellerDao.deleteById(n);
        System.out.println("Registro deletado!");
        
        sc.close();
        */
        
        DepartmentDao depDao = DaoFactory.createDepartmentDao();
        /*
        System.out.println("=== TESTE 1: Department findByID ===");
        Department dep = depDao.findById(2);
        System.out.println(dep);
        
        System.out.println("=== TESTE 2: Department findAll ===");
        List<Department> list = depDao.findAll();
        System.out.println(list);
        
        System.out.println("=== TESTE 3: Department insert ===");
        Department dep = new Department(null, "Sports");
        depDao.insert(dep);
        System.out.println("Inserido! Novo ID = "+dep.getId());
        
        System.out.println("=== TESTE 4: Department update ===");
        dep.setName("Fitness");
        depDao.update(dep);
        System.out.println("Registro atualizado!");
        */
        
        System.out.println("=== TESTE 5: Department delete ===");
        int n = 5;
        depDao.deleteById(5);
        System.out.println("Registro deletado!");
        
    }
}
