package controller;

import dao.ProductDAO;
import model.Product;
import java.util.List;

public class ProductController {
    private final ProductDAO productDAO;

    public ProductController() {
        this.productDAO = new ProductDAO();
    }

    public void addProduct(String name, double price, int stock) {
        Product product = new Product(name, price, stock);
        productDAO.addProduct(product);
    }

    public List<Product> getProducts() {
        return productDAO.getAllProducts();
    }

    public void updateProduct(int id, String name, double price, int stock) {
        Product product = new Product(id, name, price, stock);
        productDAO.updateProduct(product);
    }

    public void deleteProduct(int id) {
        productDAO.deleteProduct(id);
    }
}
