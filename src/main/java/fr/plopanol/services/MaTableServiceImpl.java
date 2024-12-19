package fr.plopanol.services;

import fr.plopanol.entite.MaTable;
import fr.plopanol.repositories.MaTableRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MaTableServiceImpl implements MaTableService{

	@Autowired
	MaTableRepository maTableRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public MaTable save(MaTable nouvelEnregistrement){
		MaTable newEntite = null;
		if ( nouvelEnregistrement.getId() != null ){
			Optional<MaTable> enBdd = findById(nouvelEnregistrement.getId());
			if ( enBdd.isPresent() ){
				MaTable entiteEnBdd = enBdd.get();
				newEntite.setDateCre(entiteEnBdd.getDateCre());

			}
		}else{
			newEntite = maTableRepository.save(nouvelEnregistrement);
		}
		// La date est enregistrer uniquement au moment du flush....
		entityManager.flush();
		return newEntite;
	}

	@Override
	public Optional<MaTable> findById(UUID id) {
		return maTableRepository.findById(id);
	}

	@Override
	public List<MaTable> findAll() {
		return maTableRepository.findAll();
	}
}
