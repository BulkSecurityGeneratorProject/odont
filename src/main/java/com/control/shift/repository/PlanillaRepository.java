package com.control.shift.repository;

import com.control.shift.domain.Planilla;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Planilla entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanillaRepository extends JpaRepository<Planilla, Long> {

}
