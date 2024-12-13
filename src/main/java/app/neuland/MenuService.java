package app.neuland;

import app.neuland.model.Menu;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;

@ApplicationScoped
public class MenuService
{
	@Inject
	AiService aiService;

	@Inject
	ReimannsScraper scraper;

	public Menu getMenu()
	{
		String html = getHtml();
		return getMenu(html);
	}

	@CacheResult(cacheName = "menu")
	Menu getMenu(@CacheKey String html)
	{
		return aiService.scrapeMeals(html);
	}

	@CacheResult(cacheName = "html")
	String getHtml()
	{
		try
		{
			return scraper.scrape();
		}
		catch (IOException e)
		{
			throw new IllegalStateException(e);
		}
	}
}
