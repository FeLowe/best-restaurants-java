import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
// import java.time.LocalDateTime;

public class RestaurantTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/best_restaurant_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteRestaurantsQuery = "DELETE FROM restaurants *;";
      String deleteCuisinesQuery = "DELETE FROM cuisines *;";
      con.createQuery(deleteRestaurantsQuery).executeUpdate();
      con.createQuery(deleteCuisinesQuery).executeUpdate();
    }
  }

  @Test
  public void restaurant_instantiatesCorrectly_true() {
    Restaurant myRestaurant = new Restaurant("Olive Garden", 1);
    assertTrue(myRestaurant instanceof Restaurant);
  }
  @Test
  public void restaurant_instantiatesWithName_String() {
    Restaurant myRestaurant = new Restaurant("Olive Garden", 1);
    assertEquals("Olive Garden", myRestaurant.getName());
  }
  @Test
  public void restaurant_getMultiplySameRestaurantName_true() {
    Restaurant firstRestaurant = new Restaurant("Olive Garden", 1);
    Restaurant secondRestaurant = new Restaurant("Olive Garden", 1);
    assertTrue(firstRestaurant.equals(secondRestaurant));
  }

  @Test
  public void restaurant_getRestaurantandSaveIt_true() {
    Restaurant myRestaurant = new Restaurant("Olive Garden", 1);
    myRestaurant.save();
    assertTrue(Restaurant.all().get(0).equals(myRestaurant));
  }
  @Test
  public void restaurant_getRestaurantId_int() {
    Restaurant myRestaurant = new Restaurant("Olive Garden", 1);
    myRestaurant.save();
    Restaurant savedRestaurant = Restaurant.all().get(0);
    assertEquals(myRestaurant.getId(), savedRestaurant.getId());
  }

  @Test
  public void find_findRestaurantInDatabase_true() {
    Restaurant myRestaurant = new Restaurant("Olive Garden", 1);
    myRestaurant.save();
    Restaurant savedRestaurant = Restaurant.find(myRestaurant.getId());
    assertTrue(myRestaurant.equals(savedRestaurant));
  }

  @Test
  public void save_saveCuisineIdIntoDB_true() {
    Cuisine myCuisine = new Cuisine ("Italian");
    myCuisine.save();
    Restaurant myRestaurant = new Restaurant ("Olive Garden", myCuisine.getId());
    myRestaurant.save();
    Restaurant savedRestaurant = Restaurant.find(myRestaurant.getId());
    assertEquals(savedRestaurant.getCuisineId(), myCuisine.getId());
  }
}
