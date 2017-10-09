package de.gkahn.zeiss.persistence;

import java.util.List;
import java.util.Optional;

import de.gkahn.zeiss.model.Machine;

public interface DataService {

	List<Machine> getAllMachines();

	Optional<Machine> getMachine(String id);

	void saveMachine(Machine machine);

}
