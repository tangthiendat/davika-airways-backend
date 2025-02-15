package com.dvk.ct250backend.domain.country.repository;

import com.dvk.ct250backend.domain.country.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByCountryName(String countryName);
}
