package com.gfg.product.repository;

import com.gfg.product.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    List<Seller> findByUuid(@Param("uuid") String uuid);
}
