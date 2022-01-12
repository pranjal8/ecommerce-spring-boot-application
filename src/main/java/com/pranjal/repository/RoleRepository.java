package com.pranjal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pranjal.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
