package src.main.java.com.example.receiptprocessor.receipt_processor;

import src.main.java.com.example.receiptprocessor.receipt_processor.Receipt;
import src.main.java.com.example.receiptprocessor.receipt_processor.ResponseDTO;
import src.main.java.com.example.receiptprocessor.receipt_processor.ReceiptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public ResponseEntity<ResponseDTO> processReceipt(@RequestBody Receipt receipt) {
        String id = receiptService.processReceipt(receipt);
        return ResponseEntity.ok(new ResponseDTO(id, null));
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<ResponseDTO> getPoints(@PathVariable String id) {
        Integer points = receiptService.getPoints(id);
        if (points == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ResponseDTO(id, points));
    }
}
