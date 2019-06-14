package com.control.shift.repository;

import com.control.shift.domain.Dentista;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Dentista entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DentistaRepository extends JpaRepository<Dentista, Long> {

}
