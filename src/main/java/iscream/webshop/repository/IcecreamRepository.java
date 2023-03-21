package iscream.webshop.repository;

import iscream.webshop.model.Icecream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IcecreamRepository extends JpaRepository<Icecream, Long> {

    @Modifying
    @Transactional
    @Query("update Icecream i set"
            + " i.name = ?1, i.description = ?2,"
            + " i.price= ?3, i.imageURL = ?4"
            + " where i.id = ?5")
    void setIcecream(
            String name,
            String description,
            float price,
            String imageURL,
            Long id
    );

    @Modifying
    @org.springframework.transaction.annotation.Transactional
    @Query("delete from Icecream where id = ?1")
    void deleteIcecreamById(Long id);
}
