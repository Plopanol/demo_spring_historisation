package fr.plopanol.services;

import fr.plopanol.entite.MaTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MaTableService {
	MaTable save(MaTable nouvelEnregistrement);

	Optional<MaTable> findById(UUID id);

	List<MaTable> findAll();
}
