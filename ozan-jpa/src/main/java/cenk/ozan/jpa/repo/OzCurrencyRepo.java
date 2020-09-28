package cenk.ozan.jpa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cenk.ozan.jpa.entity.convertion.OzConvertion;
import cenk.ozan.jpa.entity.rate.OzCurrency;

@Repository
public interface OzCurrencyRepo extends JpaRepository<OzCurrency, Long> {

	List<OzCurrency> findAll();
}