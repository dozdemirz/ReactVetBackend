package dev.patika.spring.Controller;
import dev.patika.spring.Dto.request.CustomerRequest;
import dev.patika.spring.Entities.Animal;
import dev.patika.spring.Entities.Customer;
import dev.patika.spring.Repositories.CustomerRepo;
import dev.patika.spring.Service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerRepo customerRepo;
    private final CustomerService customerService;


    public CustomerController(CustomerRepo customerRepo, CustomerService customerService) {
        this.customerRepo = customerRepo;
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public Customer findbyId(@PathVariable("id") long id){
        return this.customerRepo.findById(id).orElseThrow();
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Customer customer) {
        try {
            if (customer.getCustomerPhone() == null || customer.getCustomerPhone().isEmpty() ||
                    customer.getCustomerAddress() == null ||customer.getCustomerAddress().isEmpty() ||
                    customer.getCustomerName() == null ||customer.getCustomerName().isEmpty() ||
                    customer.getCustomerCity() == null ||customer.getCustomerCity().isEmpty() ||
                    customer.getCustomerMail() == null ||customer.getCustomerMail().isEmpty()
            ) {
                throw new IllegalArgumentException("Müşteriye ait alanlar boş olamaz.");
            }
            if (customerRepo.existsByCustomerPhone(customer.getCustomerPhone())) { //Aynı ada sahip müşteri olabileceği için unique olan telefon numarası üzerinden kontrol etmeyi seçtim
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bu telefon numarasına sahip müşteri zaten mevcut.");
            }

            Customer savedCustomer = customerRepo.save(customer);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Müşteri kaydedilemedi: " + e.getMessage());
        }
    }


    @GetMapping("/find-all")
    public List<Customer> findAll(){
        return this.customerRepo.findAll();
    }


    //Müşteriye ait tüm hayvanları listelemek için
    @GetMapping("/{customerId}/animals")
    public List<Animal> findAnimalsByCustomerId(@PathVariable("customerId") long customerId) {
        Customer customer = customerRepo.findById(customerId).orElseThrow();

        return customer.getAnimals();
    }

    //Ada sahip tüm müşterileri getiriyor (aynı isimde varsa birden çok). Ignorecase de kullanıyor
    @GetMapping("/name/{name}")
    public List<Customer> findByCustomerName(@PathVariable("name") String name) {
        return this.customerRepo.findByCustomerNameLikeIgnoreCase("%"+name+"%");
    }

    //id'ye göre müşteri silmek için
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") long id) {
        try {
            Optional<Customer> optionalCustomer = customerRepo.findById(id);

            if (optionalCustomer.isPresent()) {
                customerRepo.deleteById(id);
                return ResponseEntity.ok(id + " numaralı müşteri silindi.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir müşteri bulunamadı."); // Eğer müşteri bulunamazsa 404 hatası
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip müşteri silinemedi: " + id + ": " + e.getMessage());
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") long id, @RequestBody CustomerRequest customerRequest) {

        try {
            Optional<Customer> optionalCustomer = customerRepo.findById(id);
            if (customerRequest.getCustomerPhone() == null || customerRequest.getCustomerPhone().isEmpty() ||
                    customerRequest.getCustomerAddress() == null ||customerRequest.getCustomerAddress().isEmpty() ||
                    customerRequest.getCustomerName() == null ||customerRequest.getCustomerName().isEmpty() ||
                    customerRequest.getCustomerCity() == null ||customerRequest.getCustomerCity().isEmpty() ||
                    customerRequest.getCustomerMail() == null ||customerRequest.getCustomerMail().isEmpty()
            ) {
                throw new IllegalArgumentException("Müşteriye ait alanlar boş olamaz.");
            }

            if (optionalCustomer.isPresent()) {
                Customer existingCustomer = optionalCustomer.get();
                if (!existingCustomer.getCustomerPhone().equals(customerRequest.getCustomerPhone())) {
                    // Yeni telefon numarasına sahip bir müşteri var mı diye kontrol edelim
                    if (customerRepo.existsByCustomerPhone(customerRequest.getCustomerPhone())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bu telefon numarasına sahip müşteri zaten mevcut.");
                    }
                }

                existingCustomer.setCustomerName(customerRequest.getCustomerName());
                existingCustomer.setCustomerPhone(customerRequest.getCustomerPhone());
                existingCustomer.setCustomerMail(customerRequest.getCustomerMail());
                existingCustomer.setCustomerAddress(customerRequest.getCustomerAddress());
                existingCustomer.setCustomerCity(customerRequest.getCustomerCity());

                Customer savedCustomer = customerRepo.save(existingCustomer);

                return ResponseEntity.ok(savedCustomer);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir müşteri bulunamadı.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip müşteri güncellenemedi: " + id + ": " + e.getMessage());
        }
    }
}
