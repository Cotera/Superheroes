package com.prueba.superheroe.repository;

import com.prueba.superheroe.model.SuperheroeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperheroeRepository extends JpaRepository<SuperheroeEntity, Long> {
}
