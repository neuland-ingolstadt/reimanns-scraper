package app.neuland;

import app.neuland.model.Menu;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface PdfAiService
{
	@SystemMessage("You are a webscraper that serializes a week menu of meals into objects by day")
	@UserMessage("Scrape the PDFs content {pdfContent} and serialize it into a JSON. Use name_de for the German name of the meal, translate to English for name_en")
	Menu scrapeMeals(String pdfContent);
}
