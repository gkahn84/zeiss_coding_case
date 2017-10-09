package de.gkahn.zeiss.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import de.gkahn.zeiss.model.Machine;

public class JpaDataService implements DataService {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Machine> getAllMachines() {
		return em.createNamedQuery("findAllMachines", Machine.class).getResultList();
	}

	@Override
	public Optional<Machine> getMachine(String id) {
		return Optional.ofNullable(em.find(Machine.class, id));
	}

	@Override
	@Transactional
	public void saveMachine(Machine machine) {
		em.merge(machine);
	}

}
