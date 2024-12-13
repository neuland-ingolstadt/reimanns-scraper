package app.neuland;

import app.neuland.model.Menu;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class ReimannsGqlRessource
{
	@Inject
	MenuService menuService;

	@Query("menu")
	public Menu getMenu()
	{
		return menuService.getMenu();
	}
}
