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
		The current date is {current_date} and the dates on the menu are always the current week, so set the year to the current year.""")
	Menu scrapeMeals(String html);
}
