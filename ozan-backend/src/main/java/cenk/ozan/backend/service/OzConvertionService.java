package cenk.ozan.backend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cenk.ozan.backend.exception.OzConvertionListRequestFormatError;
import cenk.ozan.backend.exception.OzDateParsingException;
import cenk.ozan.backend.pojo.OzConvertionListRequest;
import cenk.ozan.jpa.common.OzConstants;
import cenk.ozan.jpa.entity.convertion.OzConvertion;
import cenk.ozan.jpa.repo.OzConvertionRepo;

@Service
public class OzConvertionService implements OzConstants{
	
	@Autowired
	protected OzConvertionRepo convRepo;
	
	public List<OzConvertion> list(OzConvertionListRequest req) throws OzDateParsingException, OzConvertionListRequestFormatError{
		Long tId = req.getTransactionId();
		String dateString = req.getTransactionDate();
		if(dateString!=null) {
			LocalDate date = null;
			try {
				date = LocalDate.parse(dateString, RATES_API_DATE_FORMAT);
			} catch (Exception e){
				throw new OzDateParsingException("REQUESTED", e);
			}
			if(tId!=null) {
				return convRepo.findByIdAndDate(tId, date);			
			} else {
				return convRepo.findByDate(date);			
			}
		} else if (tId!=null) {
			Optional<OzConvertion> convOpt = convRepo.findById(tId);
			List<OzConvertion> answer = new ArrayList<>();
			if(convOpt.isPresent()) {
				answer.add(convOpt.get());
			}
			return answer;
		} else {
			throw new OzConvertionListRequestFormatError();
		}
	}
	
	
}