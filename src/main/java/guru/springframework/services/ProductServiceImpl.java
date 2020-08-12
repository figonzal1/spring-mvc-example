package guru.springframework.services;

import guru.springframework.domain.DomainObject;
import guru.springframework.domain.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductServiceImpl extends AbstractMapService implements ProductService {

    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public Product getById(Integer id) {
        return (Product) super.getById(id);
    }

    @Override
    public Product saveOrUpdate(Product domainObject) {
        return (Product) super.saveOrUpdate(domainObject);
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }

    @Override
    protected void loadDomainObjects() {
        Product p1 = new Product();
        p1.setId(1);
        p1.setDescription("Product 1");
        p1.setPrice(new BigDecimal("2.99"));
        p1.setImageUrl("https://example.com/product1");
        domainMap.put(1, p1);

        Product p2 = new Product();
        p2.setId(2);
        p2.setDescription("Product 2");
        p2.setPrice(new BigDecimal("39.99"));
        p2.setImageUrl("https://example.com/product2");
        domainMap.put(2, p2);

        Product p3 = new Product();
        p3.setId(3);
        p3.setDescription("Product 3");
        p3.setPrice(new BigDecimal("14.99"));
        p3.setImageUrl("https://example.com/product3");
        domainMap.put(3, p3);

        Product p4 = new Product();
        p4.setId(4);
        p4.setDescription("Product 4");
        p4.setPrice(new BigDecimal("44.99"));
        p4.setImageUrl("https://example.com/product4");
        domainMap.put(4, p4);
    }
}
