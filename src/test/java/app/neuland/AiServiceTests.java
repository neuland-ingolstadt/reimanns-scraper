package app.neuland;

import app.neuland.model.Menu;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class AiServiceTests
{
	@Inject
	AiService aiService;

	@Test
	void shouldScrapeMeals() throws IOException
	{
		// given
		ReimannsScraper scraper = new ReimannsScraper();
		String html = scraper.scrape();

		// when
		Menu menu = aiService.scrapeMeals(html);

		// then
		assertNotNull(menu);
		assertThat(menu.days(), iterableWithSize(greaterThan(3)));
	}

	@Test
	void shouldParseSimpleHTML()
	{
		// given
		String html = "Fr. 13.12.24: <span><strong>Tomatensuppe</strong> mit Sahne und Croutons</span>";

		// when
		Menu menu = aiService.scrapeMeals(html);

		// then
		assertNotNull(menu);
		assertEquals("2024-12-13", menu.days().getFirst().date().toString());
		assertEquals("Tomatensuppe mit Sahne und Croutons", menu.days().getFirst().meals().get(0).name_de());
	}
}
