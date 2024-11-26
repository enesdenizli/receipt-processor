package src.main.java.com.example.receiptprocessor.receipt_processor;

import src.main.java.com.example.receiptprocessor.receipt_processor.Receipt;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class ReceiptService {
    private final Map<String, Receipt> receipts = new HashMap<>();
    private final Map<String, Integer> pointsMap = new HashMap<>();

    public String processReceipt(Receipt receipt) {
        String id = UUID.randomUUID().toString();
        receipts.put(id, receipt);
        int points = calculatePoints(receipt);
        pointsMap.put(id, points);
        return id;
    }

    public Integer getPoints(String id) {
        return pointsMap.getOrDefault(id, null);
    }

    private int calculatePoints(Receipt receipt) {
        int points = 0;

        // Rule 1: One point for every alphanumeric character in the retailer name
        points += countAlphanumeric(receipt.getRetailer());

        // Rule 2: 50 points if the total is a round dollar amount with no cents
        if (receipt.getTotal().endsWith(".00")) {
            points += 50;
        }

        // Rule 3: 25 points if the total is a multiple of 0.25
        double total = Double.parseDouble(receipt.getTotal());
        if (total % 0.25 == 0) {
            points += 25;
        }

        // Rule 4: 5 points for every two items on the receipt
        points += (receipt.getItems().size() / 2) * 5;

        // Rule 5: Points for item descriptions that are multiples of 3
        for (var item : receipt.getItems()) {
            int length = item.getShortDescription().trim().length();
            if (length % 3 == 0) {
                double price = Double.parseDouble(item.getPrice());
                points += Math.ceil(price * 0.2);
            }
        }

        // Rule 6: 6 points if the day in the purchase date is odd
        String[] dateParts = receipt.getPurchaseDate().split("-");
        int day = Integer.parseInt(dateParts[2]);
        if (day % 2 != 0) {
            points += 6;
        }

        // Rule 7: 10 points if the time is between 2:00 PM and 4:00 PM
        String[] timeParts = receipt.getPurchaseTime().split(":");
        int hour = Integer.parseInt(timeParts[0]);
        if (hour == 14) {
            points += 10;
        }

        return points;
    }

    private int countAlphanumeric(String input) {
        return (int) input.chars()
                .filter(c -> Character.isLetterOrDigit(c))
                .count();
    }
}
