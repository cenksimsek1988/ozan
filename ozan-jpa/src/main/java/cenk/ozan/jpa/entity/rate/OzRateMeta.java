package cenk.ozan.jpa.entity.rate;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OzRateMeta {
	private LocalDate date;
	private OzCurrency from;
	private OzCurrency to;
	
}