package cenk.ozan.jpa.entity.rate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@Entity
@Table
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler"})
public class OzCurrency {
	@Id
	@GeneratedValue
	private long id;

	@Column
	@EqualsAndHashCode.Include
	private String code;
	
}
