package src.main.java.com.example.receiptprocessor.receipt_processor;

import lombok.Data;

@Data
public class Item {
    private String shortDescription;
    private String price;
    
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
