package app.neuland;

import app.neuland.model.Menu;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface AiService
{
	@SystemMessage("You are a webscraper that serializes a week menu of meals into objects by day")
	@UserMessage("Scrape the HTML {html} and serialize it into a JSON")
	Menu scrapeMeals(String html);
}
