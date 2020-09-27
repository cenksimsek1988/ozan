package cenk.ozan.jpa.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cenk.ozan.jpa.entity.convertion.OzConvertion;
import cenk.ozan.jpa.entity.rate.OzCurrency;

@Repository
public interface OzConvertionRepo extends JpaRepository<OzConvertion, Long> {

	List<OzConvertion> findByDateGreaterThanEqualAndRateFromAndRateTo(LocalDate date, OzCurrency from, OzCurrency to);
	
	List<OzConvertion> findByDateGreaterThanEqualAndDateLessThanEqualAndRateFromAndRateTo(LocalDate since, LocalDate until, OzCurrency from, OzCurrency to);
	
	List<OzConvertion> findByDateGreaterThanEqualAndDateLessThanEqual(LocalDate since, LocalDate until);

	List<OzConvertion> findByIdAndDate(Long tId, LocalDate date);
	
	List<OzConvertion> findByDate(LocalDate date);
}