package cenk.ozan.jpa.entity.convertion;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import cenk.ozan.jpa.entity.rate.OzRate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "rate", "id"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OzConvertion {
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private LocalDate date;
	
	@Column
	private float amount;
	
	@ManyToOne
	private OzRate rate;
	
	@Transient
	@JsonGetter(value = "exchangeRate")
	public float getExchangeRate() {
		return rate.getRate();
	}
	
	@Transient
	@JsonGetter(value = "from")
	public String getFrom() {
		return rate.getFrom().getCode();
	}
	
	@Transient
	@JsonGetter(value = "to")
	public String getTo() {
		return rate.getTo().getCode();
	}
	
	@Transient
	@JsonGetter(value = "targetAmount")
	public float getTargetAmount() {
		return amount * rate.getRate();
	}
	
}
