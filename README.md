## **Receipt Processor**

A simple Spring Boot application that processes receipt data and calculates reward points based on predefined rules.

### **Features**
- Process receipts via a REST API (`/receipts/process`).
- Retrieve reward points for a specific receipt (`/receipts/{id}/points`).
- Follows predefined point calculation rules based on the receipt details.

---

### **Run Locally**

#### **1. Build the Application**
```bash
./mvnw clean package
```

#### **2. Run the Application**
```bash
java -jar target/receipt-processor-0.0.1-SNAPSHOT.jar
```

---

### **Run with Docker**

#### **1. Build Docker Image**
```bash
docker build -t receipt-processor .
```

#### **2. Run Docker Container**
```bash
docker run -p 8080:8080 receipt-processor
```

The application will be available at `http://localhost:8080`.

---

### **API Endpoints**

#### **Process a Receipt**
- **URL:** `POST /receipts/process`
- **Payload Example:**
  ```json
  {
    "retailer": "Target",
    "purchaseDate": "2022-01-01",
    "purchaseTime": "13:01",
    "items": [
      { "shortDescription": "Mountain Dew 12PK", "price": "6.49" }
    ],
    "total": "35.35"
  }
  ```
- **Response:**
  ```json
  { "id": "unique-receipt-id" }
  ```

#### **Get Points for a Receipt**
- **URL:** `GET /receipts/{id}/points`
- **Response Example:**
  ```json
  { "points": 109 }
  ```

---

### **Docker Hub (Optional)**
Pull the image from Docker Hub:
```bash
docker pull <dockerhub-username>/receipt-processor
docker run -p 8080:8080 <dockerhub-username>/receipt-processor
```
