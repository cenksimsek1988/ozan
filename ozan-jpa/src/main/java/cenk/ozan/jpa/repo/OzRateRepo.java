package cenk.ozan.jpa.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cenk.ozan.jpa.entity.rate.OzCurrency;
import cenk.ozan.jpa.entity.rate.OzRate;

@Repository
public interface OzRateRepo extends JpaRepository<OzRate, Long> {

	OzRate findById(long id);

	OzRate findByDateAndFromAndTo(LocalDate date, OzCurrency from, OzCurrency to);
	
	List<OzRate> findByDateGreaterThanEqualAndDateLessThanEqualAndFromAndTo(LocalDate since, LocalDate until, OzCurrency from, OzCurrency to);
	
	List<OzRate> findByDateGreaterThanEqualAndDateLessThanEqual(LocalDate since, LocalDate until);
}