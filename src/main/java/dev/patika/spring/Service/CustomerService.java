package dev.patika.spring.Service;

import dev.patika.spring.Entities.Animal;
import dev.patika.spring.Entities.Customer;
import dev.patika.spring.Repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer findById(long id) {
        return customerRepo.findById(id).orElseThrow(() -> new RuntimeException("Müşteri bulunamadı: " + id));
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public List<Customer> findAllCustomers() {
        return customerRepo.findAll();
    }

    public List<Animal> findAnimalsByCustomerId(long customerId) {
        Customer customer = findById(customerId);
        return customer.getAnimals();
    }

    public List<Customer> findByCustomerName(String name) {
        return customerRepo.findByCustomerNameIgnoreCase(name);
    }

    public void deleteCustomer(long id) {
        Optional<Customer> optionalCustomer = customerRepo.findById(id);

        if (optionalCustomer.isPresent()) {
            customerRepo.deleteById(id);
        } else {
            throw new RuntimeException("Bu ID'de bir müşteri bulunamadı: " + id);
        }
    }
}
