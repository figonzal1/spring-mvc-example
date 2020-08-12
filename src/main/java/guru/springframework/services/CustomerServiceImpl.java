package guru.springframework.services;

import guru.springframework.domain.Customer;
import guru.springframework.domain.DomainObject;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceImpl extends AbstractMapService implements CustomerService {

    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public Customer getById(Integer id) {
        return (Customer) super.getById(id);
    }

    @Override
    public Customer saveOrUpdate(Customer domainObject) {
        return (Customer) super.saveOrUpdate(domainObject);
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }


    public void loadDomainObjects() {
        domainMap = new HashMap<>();

        Customer c1 = new Customer();
        c1.setId(1);
        c1.setFirstName("Felipe");
        c1.setLastName("González");
        c1.setEmail("felipe.gonzalezalarcon94@gmail.com");
        c1.setAddressLine1("Direccion 1");
        c1.setAddressLine2("Direccion 2");
        c1.setCity("Valparaíso");
        c1.setState("Valparaíso");
        c1.setPhoneNumber("xxx-xxxx-xxx");
        c1.setZipCode("xxxxxxxx");

        domainMap.put(1, c1);

        Customer c2 = new Customer();
        c2.setId(2);
        c2.setFirstName("Felipe");
        c2.setLastName("Alarcon");
        c2.setEmail("felipe.al@gmail.com");
        c2.setAddressLine1("Direccion 1");
        c2.setAddressLine2("Direccion 2");
        c2.setCity("La Serena");
        c2.setState("La Serena");
        c2.setPhoneNumber("xxx-xxxx-xxx");
        c2.setZipCode("xxxxxxxx");

        domainMap.put(2, c2);
    }
}
