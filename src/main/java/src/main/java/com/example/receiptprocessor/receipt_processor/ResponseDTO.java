package src.main.java.com.example.receiptprocessor.receipt_processor;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ResponseDTO {

	private String id;
    private int points;
    private List<String> breakdown;

    public ResponseDTO(String id) {
        this.id = id;
    }
    
    public ResponseDTO(String id, int points) {
        this.id = id;
        this.points = points;
    }
    
    public ResponseDTO(String id, int points, List<String> breakdown) {
        this.id = id;
        this.points = points;
        this.breakdown = breakdown;
    }
    
}
