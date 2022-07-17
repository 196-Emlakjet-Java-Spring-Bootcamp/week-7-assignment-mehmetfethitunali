package com.emlakjet.finalproject.advertisement.dao;

import com.emlakjet.finalproject.advertisement.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AdvertisementRepository extends JpaRepository <Advertisement, Long>{

    @Query(value = "select id from users where username like %:username%",nativeQuery = true)
    Long getIdByUserName(String username);
    @Query(value = "select * from advertisements where status = 'active' and user_id = ?1 order by created_at desc ", nativeQuery = true)
    List<Advertisement> findAllByUser(Long id);

    @Query(value = "select * from advertisements where status = 'active' and price between ?1 and ?2 order by price asc", nativeQuery = true)
    List<Advertisement> findByPriceBetween(Long min, Long max);

    @Query(value = "select * from advertisements where status = 'active' and title like %:text% or detailed_message like %:text% order by created_at desc", nativeQuery = true)
    List<Advertisement> findByTitleOrDetailedMessageLike(@Param("text") String text);

    @Query(value = "select * from advertisements where status = 'active' order by created_at desc", nativeQuery = true)
    List<Advertisement> getAllOrderByCreatedAt();

    @Query(value = "select * from advertisements where status = 'passive' order by created_at asc", nativeQuery = true)
    List<Advertisement> getAllByStatusPassive();



}
