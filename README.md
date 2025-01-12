# Buy Recipes Feature - README

## **Project Details**

## **Overview**
Ecommerce platform having new feature called “Buy Recipes.” Previously, users could only buy individual products. This feature enables users to purchase an entire recipe, where each recipe consists of multiple products already available in the database. The application extends the existing cart functionality to support recipes in addition to products.

1. **GET /recipes**
    - Lists all the recipes available in the database.

2. **GET /carts/:id**
    - Retrieves a cart by its ID, including products and recipes within it.

3. **POST /carts/:id/add_recipe**
    - Adds a recipe to a cart specified by its ID.

4. **DELETE /carts/:id/recipes/:id**
    - Removes a recipe from a cart specified by its cart ID and recipe ID.

---

## **Schema**

### **Tables**
1. **carts**
   ```sql
   id: INT,
   total_in_cents: INT
   ```

2. **cart_items**
   ```sql
   id: INT,
   cart_id: INT,
   product_id: INT
   ```

3. **products**
   ```sql
   id: INT,
   name: VARCHAR,
   price_in_cents: INT
   ```
4. **recipes**
   ```sql
   id: INT,
   name: VARCHAR NOT NULL
   ```

5. **recipe_products**
   ```sql
   id: INT,
   recipe_id: INT,
   product_id: INT
   ```

---

## **Key Features**
- **Clean and Modular Code**: The application follows best practices and conscious design decisions to ensure maintainability and scalability.
- **Unit and Integration Tests**: Comprehensive test coverage ensures the reliability of service, repository, and controller layers.
- **Database Persistence**: Recipes and their ingredients are persisted in a relational database using JPA and Hibernate.
- **No Inventory Management**: Assumes all products are always available.

---

## **How to Run the Application Locally**

### **Prerequisites**
- Java 17+
- Kotlin 1.8+
- Spring Boot 3.0+
- PostgreSQL or H2 (for testing)
- Maven or Gradle (build tool)

### **Steps**
1. Clone the repository:
   ```bash
   git clone https://github.com/sandeepjak2007/ecommerce-be
   cd ecommerce-be
   ```

2. Set up the database:
    - Use PostgreSQL or H2 (in-memory) for testing.
    - Update the `application.properties` file with database credentials.

3. Build and run the application:
   ```bash
   ./gradlew bootRun
   ```

4. Seed the database:
    - Insert sample recipes and products using SQL scripts or application bootstrap.

---

## **Testing**

### **Unit Tests**
- Cover repository, service, and controller layers to validate individual components.

### **Integration Tests**
- Test end-to-end scenarios, ensuring proper interaction between APIs and the database.

### **Command**
Run the tests using:
```bash
./gradlew test
```

## **Endpoints**

1. **GET /recipes**
    - Retrieves all available recipes.
2. **GET /carts/:id**
    - Retrieves a specific cart with products and recipes.

3. **POST /carts/:id/add_recipe**
    - Adds a recipe to a cart.

4. **DELETE /carts/:id/recipes/:id**
    - Removes a recipe from a cart.
---


