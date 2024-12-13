package app.neuland;

import jakarta.enterprise.context.ApplicationScoped;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

@ApplicationScoped
public class ReimannsScraper
{
	public String scrape() throws IOException
	{
		Document doc = Jsoup.connect("http://reimanns.in/mittagsgerichte-wochenkarte/").get();
		Element element = doc.select("div.site-content").first();
		if (element == null)
		{
			throw new IllegalStateException("Could not get content from page");
		}
		return element.text();
	}
}
