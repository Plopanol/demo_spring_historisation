package fr.plopanol.rest;

import fr.plopanol.entite.MaTable;
import fr.plopanol.services.MaTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class MaTableController {

	@Autowired
	MaTableService maTableService;

	@GetMapping("/{id}")
	public Optional<MaTable> get(@PathVariable(name = "id") final UUID id) {
		return maTableService.findById(id);
	}

	@GetMapping
	public List<MaTable> getAll() {
		return maTableService.findAll();
	}

	@PostMapping
	public MaTable add(@RequestBody final MaTable nouvelEnregistrement) {
		return maTableService.save(nouvelEnregistrement);
	}
}
