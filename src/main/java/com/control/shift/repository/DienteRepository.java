package com.control.shift.repository;

import com.control.shift.domain.Diente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Diente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DienteRepository extends JpaRepository<Diente, Long> {

}
