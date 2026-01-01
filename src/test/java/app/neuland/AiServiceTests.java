package app.neuland;

import app.neuland.model.Menu;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class AiServiceTests
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
		assertThat(menu.days().getFirst().date().toString(), containsString("2026"));
		assertEquals("Tomatensuppe mit Sahne und Croutons", menu.days().getFirst().meals().getFirst().name_de());
	}
}
