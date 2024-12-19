package app.neuland;

import app.neuland.model.Menu;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface AiService
{
	@SystemMessage("You are a webscraper that serializes a week menu of meals into objects by day")
	@UserMessage("""
		Scrape the HTML {html} and serialize it into a JSON. Use name_de for the German name of the meal, translate to English for name_en.
		The dates on the menu are always the current week, so set the year to the year of {current_date}.""")
	Menu scrapeMeals(String html);
}
