package de.gkahn.zeiss.business;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.gkahn.zeiss.model.MachineEvent;
import de.gkahn.zeiss.model.MachineStatus;

public class MachineStreamServerMockup {

	private List<String> oldMachineIds = new ArrayList<>();
	private MachineStatus[] possibleStati = MachineStatus.values();
	private Random random = new Random();

	public String getData() {
		MachineEvent event =
				new MachineEvent(UUID.randomUUID().toString(), createMachineId(), Instant.now().toString(), randomStatus().toString());
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(event);
		} catch (JsonProcessingException e) {
			return "";
		}
	}

	private MachineStatus randomStatus() {
		return possibleStati[random.nextInt(possibleStati.length)];
	}

	private String randomExistingId() {
		return oldMachineIds.get(random.nextInt(oldMachineIds.size()));
	}

	private String createMachineId() {
		String machineId;
		int i = random.nextInt(10);
		if (oldMachineIds.size() == 0 || i > 6) {
			machineId = UUID.randomUUID().toString();
			oldMachineIds.add(machineId);
		} else {
			machineId = randomExistingId();
		}

		return machineId;
	}

}
