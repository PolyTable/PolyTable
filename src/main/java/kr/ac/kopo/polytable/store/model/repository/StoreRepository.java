package kr.ac.kopo.polytable.store.model.repository;

import kr.ac.kopo.polytable.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByCrn(String crn);
    Optional<Store> findByStoreTelNo(String storeTelNo);
}
