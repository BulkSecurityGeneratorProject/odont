package com.control.shift.repository;

import com.control.shift.domain.Precio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Precio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrecioRepository extends JpaRepository<Precio, Long> {

}
