package app.neuland;

import app.neuland.model.Menu;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.io.IOException;

@GraphQLApi
public class ReimannsGqlRessource
{
	@Inject
	AiService aiService;

	@Inject
	ReimannsScraper scraper;

	@Query("menu")
	public Menu getMenu() throws IOException
	{
		String html = scraper.scrape();
		return aiService.scrapeMeals(html);
	}
}
