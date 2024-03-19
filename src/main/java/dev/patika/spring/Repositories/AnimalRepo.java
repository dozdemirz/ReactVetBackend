package dev.patika.spring.Repositories;

import dev.patika.spring.Entities.Animal;
import dev.patika.spring.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository <Animal, Long>{
        List<Animal> findByAnimalName(String animalName);


        //Adrese büyük harfli yazıldığında sorun çıkıyordu. Ignorecase ile yapmak için custom query kullandım
        @Query("SELECT a FROM Animal a WHERE LOWER(a.animalName) = LOWER(:name)")
        List<Animal> findByAnimalNameIgnoreCase(@Param("name") String name);
        List<Animal> findByAnimalId(Long animalId);
        List<Animal> findByCustomer_CustomerId(Long customerId);

        boolean existsByAnimalNameAndCustomer_CustomerId(String animalName, Long customerId);

        boolean existsByAnimalNameAndCustomer(String animalName, Customer customer);


}
