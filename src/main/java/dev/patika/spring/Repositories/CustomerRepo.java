package dev.patika.spring.Repositories;

import dev.patika.spring.Entities.Animal;
import dev.patika.spring.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository <Customer, Long> {
    Customer findByCustomerName(String name);

    //Adrese büyük harfli yazıldığında sorun çıkıyordu. Ignorecase ile yapmak için custom query kullandım
    @Query("SELECT c FROM Customer c WHERE LOWER(c.customerName) = LOWER(:name)")
    List<Customer> findByCustomerNameIgnoreCase(@Param("name") String name);

    public List<Customer> findByCustomerNameLikeIgnoreCase(String name);
    boolean existsByCustomerPhone(String customerPhone);
}
