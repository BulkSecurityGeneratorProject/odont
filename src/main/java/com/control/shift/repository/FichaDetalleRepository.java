package com.control.shift.repository;

import com.control.shift.domain.FichaDetalle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FichaDetalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FichaDetalleRepository extends JpaRepository<FichaDetalle, Long> {

}
