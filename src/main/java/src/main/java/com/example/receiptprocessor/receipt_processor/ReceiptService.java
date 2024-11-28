package src.main.java.com.example.receiptprocessor.receipt_processor;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReceiptService {
    private final Map<String, Receipt> receipts = new HashMap<>();
    private final Map<String, Integer> pointsMap = new HashMap<>();
    private final Map<String, List<String>> breakdownMap = new HashMap<>();

	public String processReceipt(Receipt receipt) {
        String id = UUID.randomUUID().toString();
        receipts.put(id, receipt);

        List<String> breakdown = new ArrayList<>();
        int points = calculatePoints(receipt, breakdown);

        pointsMap.put(id, points);
        breakdownMap.put(id, breakdown);
        return id;
    }

    public Integer getPoints(String id) {
        return pointsMap.getOrDefault(id, null);
    }

    public List<String> getBreakdown(String id) {
        return breakdownMap.getOrDefault(id, Collections.emptyList());
    }

    private int calculatePoints(Receipt receipt, List<String> breakdown) {
        int points = 0;

        int retailerPoints = countAlphanumeric(receipt.getRetailer());
        points += retailerPoints;
        breakdown.add(retailerPoints + " points - retailer name (" + receipt.getRetailer() + ") has " + retailerPoints + " alphanumeric characters");

        if (receipt.getTotal().endsWith(".00")) {
            points += 50;
            breakdown.add("50 points - total is a round dollar amount");
        }

        double total = Double.parseDouble(receipt.getTotal());
        if (total % 0.25 == 0) {
            points += 25;
            breakdown.add("25 points - total is a multiple of 0.25");
        }

        int itemPairsPoints = (receipt.getItems().size() / 2) * 5;
        points += itemPairsPoints;
        breakdown.add(itemPairsPoints + " points - " + receipt.getItems().size() + " items (" + (receipt.getItems().size() / 2) + " pairs @ 5 points each)");

        for (var item : receipt.getItems()) {
            int length = item.getShortDescription().trim().length();
            if (length % 3 == 0) {
                double price = Double.parseDouble(item.getPrice());
                int itemPoints = (int) Math.ceil(price * 0.2);
                points += itemPoints;
                breakdown.add(itemPoints + " points - \"" + item.getShortDescription() + "\" is " + length + " characters (multiple of 3), price * 0.2 = " + itemPoints + " points");
            }
        }

        String[] dateParts = receipt.getPurchaseDate().split("-");
        int day = Integer.parseInt(dateParts[2]);
        if (day % 2 != 0) {
            points += 6;
            breakdown.add("6 points - purchase day is odd (" + day + ")");
        }

        String[] timeParts = receipt.getPurchaseTime().split(":");
        int hour = Integer.parseInt(timeParts[0]);
        if (hour == 14) {
            points += 10;
            breakdown.add("10 points - purchase time (" + receipt.getPurchaseTime() + ") is between 2:00 PM and 4:00 PM");
        }

        return points;
    }

    private int countAlphanumeric(String input) {
        return (int) input.chars()
                .filter(Character::isLetterOrDigit)
                .count();
    }
}
