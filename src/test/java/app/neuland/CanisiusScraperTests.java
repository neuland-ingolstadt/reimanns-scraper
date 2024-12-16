package app.neuland;

import app.neuland.model.Menu;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.tika.TikaContent;
import io.quarkus.tika.TikaParser;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@QuarkusTest
class CanisiusScraperTests
{
	@Inject
	TikaParser parser;

	@Inject
	PdfAiService aiService;

	@Test
	void shouldScrapeCanisius() throws Exception
	{
		String text;
		try (HttpClient client = HttpClient.newHttpClient())
		{
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://www.canisiusstiftung.de/wp-content/uploads/Speiseplan/speiseplan.pdf"))
				.GET()
				.build();

			HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
			TikaContent content = parser.parse(response.body());
			text = content.getText();
		}

		Menu menu = aiService.scrapeMeals(text);
		System.out.println(menu);
	}
}
