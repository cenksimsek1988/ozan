package cenk.ozan.jpa.entity.rate;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import cenk.ozan.jpa.common.OzEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "id", "date", "from", "to"})
public class OzRate implements OzEntity {
	
	public OzRate() {}
	
	public OzRate(LocalDate date, OzCurrency from, OzCurrency to, float rate) {
		this.date = date;
		this.from = from;
		this.to = to;
		this.rate = rate;
	}

	@Id
	@GeneratedValue
	private long id;
	
	@Column
	@EqualsAndHashCode.Include
	private LocalDate date;
	
	@ManyToOne
	@EqualsAndHashCode.Include
	private OzCurrency from;
	
	@ManyToOne
	@EqualsAndHashCode.Include
	private OzCurrency to;
	
	@Column
	private float rate;
	
}