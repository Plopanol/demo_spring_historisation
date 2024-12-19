package fr.plopanol.repositories;

import fr.plopanol.entite.MaTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.UUID;

public interface MaTableRepository extends JpaRepository<MaTable, UUID>, RevisionRepository<MaTable, UUID, Long> {
}