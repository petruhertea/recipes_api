# CookCraft API — Spring Boot Backend

A RESTful API that powers the CookCraft Android app. It provides recipe data, ingredient-based recipe suggestions, and beverage pairings, backed by a MySQL database.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5.7 |
| ORM | Spring Data JPA (Hibernate) |
| Database | MySQL |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Caching | Spring Cache (in-memory) |
| Build | Maven 3.9.5 |

---

## Project Structure

```
src/
├── main/
│   ├── java/com/example/recipeapi/
│   │   ├── RecipeapiApplication.java      # Entry point, enables caching
│   │   ├── dao/
│   │   │   ├── RecipeRepository.java      # JPA repo with JPQL queries
│   │   │   └── BeverageRepository.java    # JPA repo for beverage suggestions
│   │   ├── dto/
│   │   │   ├── BeverageDTO.java           # response DTO used by the beverage suggestion endpoint
│   │   │   ├── IngredientDetails.java     # request DTO used by the recipe suggestions endpoint
│   │   │   └── RecipeDTO.java             # response DTO used by the recipe related endpoints   
│   │   ├── entity/
│   │   │   ├── Recipe.java                # Recipe entity / projection
│   │   │   ├── Beverage.java              # Beverage entity / projection
│   │   │   ├── RecipeIngredient.java      # Join Table entity to represent the relationship between Recipe and Ingredient
│   │   │   ├── RecipeIngredientId.java    # Composite Key representation for the RecipeIngredient entity
│   │   │   └── IngredientDetails.java     # Ingredient entity / projection
│   │   ├── mapper/
│   │   │   └── RecipeMapper.java          # Mapper class used to map entities responses to their respective DTO representations 
│   │   ├── rest/
│   │   │   ├── RecipeRestController.java  # Recipe endpoints
│   │   │   └── BeverageRestController.java# Beverage endpoints
│   │   └── service/
│   │       ├── RecipeService.java         # Recipe service interface
│   │       ├── RecipeServiceImpl.java     # Recipe business logic + matching
│   │       ├── BeverageService.java       # Beverage service interface
│   │       └── BeverageServiceImpl.java   # Beverage business logic
│   └── resources/
│       └── application.properties         # DB config (imports env.properties)
```

---

## Prerequisites

- Java 17+
- Maven 3.9+
- MySQL database running and accessible
- An `env.properties` file in `src/main/resources/` (excluded from version control)

---

## Configuration

Create `src/main/resources/env.properties` with your database credentials:

```properties
DB_URL=jdbc:mysql://localhost:3306/your_database_name
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

This file is gitignored — never commit it.

---

## Running the Application

```bash
# Using Maven Wrapper
./mvnw spring-boot:run

# Or build a JAR first
./mvnw clean package
java -jar target/recipe-suggestions.jar
```

The server starts on **http://localhost:8080** by default.

Swagger UI is available at: **http://localhost:8080/swagger-ui.html**

---

## API Endpoints

### Recipes

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/recipes` | Get all recipes with ingredients |
| `GET` | `/api/v1/recipes/{recipeID}` | Get a single recipe by ID |
| `POST` | `/api/v1/recipes/byIngredients` | Get recipes matching available ingredients |

### Beverages

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/recipes/{recipeID}/beverages` | Get beverage suggestions for a recipe |

---

### POST `/api/v1/recipes/byIngredients`

Send a JSON body mapping ingredient names to their available quantities and units.

**Request body example:**
```json
{
  "flour": { "weight": 500, "unit": "g" },
  "eggs":  { "weight": 3,   "unit": "" },
  "milk":  { "weight": 250, "unit": "ml" }
}
```

**Matching logic:**
- Ingredient names are compared using Unicode normalization (accent-insensitive, case-insensitive).
- Unit conversion is applied automatically (g ↔ kg, ml ↔ l, etc.).
- Returns **fully matched** recipes first (user has enough of every ingredient). If none exist, returns **partially matched** recipes (at least one ingredient is present in sufficient quantity).

---

## Caching

Spring Cache is enabled globally via `@EnableCaching` on the main application class. The following caches are active:

| Cache Name | Scope |
|---|---|
| `allRecipes` | Result of `getAllRecipeDetails()` |
| `recipeById` | Per `recipeID` |
| `beverageSuggestions` | Per `recipeId` |

Cache is in-memory (ConcurrentHashMap) and lives for the lifetime of the process. Restart the server to invalidate.

---

## Database Schema (expected)

The API expects the following tables:

- `recipe` — core recipe data (title, description, instructions, times, image)
- `ingredient` — ingredient catalog
- `recipeingredient` — join table with quantity and measure_unit
- `beverage` — beverage catalog
- `recipebeverage` — join table linking recipes to beverages

---

## Building for Production

```bash
./mvnw clean package -DskipTests
```

The output JAR is `target/recipe-suggestions.jar`. Run with:

```bash
java -jar target/recipe-suggestions.jar
```

---

## Notes

- The server uses `localhost` in image URLs. Android emulators access the host machine via `10.0.2.2` — the mobile app handles this substitution automatically.
- SQL logging can be enabled by uncommenting `spring.jpa.show-sql=true` in `application.properties`.
