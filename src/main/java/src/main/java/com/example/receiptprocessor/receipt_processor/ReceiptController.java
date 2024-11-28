package src.main.java.com.example.receiptprocessor.receipt_processor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping(value = "/process", produces = "application/json")
    public ResponseEntity<ResponseDTO> processReceipt(@RequestBody Receipt receipt) {
        String id = receiptService.processReceipt(receipt);
        ResponseDTO response = new ResponseDTO(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping(value = "/{id}/points", produces = "application/json")
    public ResponseEntity<ResponseDTO> getPoints(@PathVariable String id) {
        Integer points = receiptService.getPoints(id);
        if (points == null) {
            return ResponseEntity.notFound().build();
        }

        List<String> breakdown = receiptService.getBreakdown(id);
        ResponseDTO response = new ResponseDTO(id, points, breakdown);
        return ResponseEntity.ok(response);
    }
}
