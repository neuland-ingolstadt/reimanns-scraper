package app.neuland.model;

import java.time.LocalDate;
import java.util.List;

public record Day(LocalDate date, List<Meal> meals)
{
}
