package src.main.java.com.example.receiptprocessor.receipt_processor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {
	private String id;
    private Integer points;
    
	public ResponseDTO(String id, Integer points) {
		super();
		this.id = id;
		this.points = points;
	}
    
    
}
