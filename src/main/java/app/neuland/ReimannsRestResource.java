package app.neuland;

import app.neuland.model.Menu;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/menu")
@Produces(MediaType.APPLICATION_JSON)
public class ReimannsRestResource
{
	@Inject
	MenuService menuService;

	@GET
	public Menu getMenu()
	{
		return menuService.getMenu();
	}
}
