package app.neuland;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

class ReimannsScraperTests
{
	@Test
	void shouldProvideHTML() throws IOException
	{
		// given
		ReimannsScraper scraper = new ReimannsScraper();

		// when
		String html = scraper.scrape();

		// then
		assertThat(html, containsString("WOCHENKARTE"));
	}
}
