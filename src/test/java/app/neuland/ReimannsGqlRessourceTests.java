package app.neuland;

import app.neuland.model.Day;
import app.neuland.model.Meal;
import app.neuland.model.Menu;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@QuarkusTest
class ReimannsGqlRessourceTests
{
	@InjectMock
	AiService aiService;

	@BeforeEach
	void setupMocks()
	{
		Meal maultaschen = new Meal("Maultaschen", "mouth bags");
		Meal spaetzle = new Meal("Spätzle", "little sparrows");

		Day monday = new Day(LocalDate.of(2024, 12, 12), List.of(maultaschen));
		Day tuesday = new Day(LocalDate.of(2024, 12, 13), List.of(spaetzle));

		List<Day> days = Arrays.asList(monday, tuesday);
		Menu testMenu = new Menu(days);
		when(aiService.scrapeMeals(anyString())).thenReturn(testMenu);
	}

	@Test
	void shouldReturnMeals()
	{
		String graphqlQuery = "query { menu { days {  date meals { name_de } } }}";

		given()
			.body("{\"query\":\"" + graphqlQuery + "\"}")
			.when()
			.post("graphql")
			.then()
			.statusCode(200)
			.body("data.menu.days.meals.name_de.flatten()", hasItems("Maultaschen", "Spätzle"));
	}
}
