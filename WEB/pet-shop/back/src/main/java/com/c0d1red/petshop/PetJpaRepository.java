package com.c0d1red.petshop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "pets", collectionResourceRel = "pets")
public interface PetJpaRepository extends JpaRepository<Pet, Long> {
}
