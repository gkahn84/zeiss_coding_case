package de.gkahn.zeiss.business;

import java.io.IOException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import de.gkahn.zeiss.model.Machine;
import de.gkahn.zeiss.model.MachineEvent;
import de.gkahn.zeiss.persistence.DataService;

public class MachineStreamListener {

	private DataService dataService;
	private MachineStreamServerMockup serverMockup = new MachineStreamServerMockup();
	private ObjectMapper mapper = new ObjectMapper();

	private MachineStreamListener(DataService dataService) {
		this.dataService = dataService;
		this.serverMockup = new MachineStreamServerMockup();
		this.mapper = new ObjectMapper();
	}

	public void init() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				String data = serverMockup.getData();
				try {
					MachineEvent event = mapper.readValue(data, MachineEvent.class);
					Optional<Machine> machine = dataService.getMachine(event.getMachineId());

					if (machine.isPresent()) {
						machine.get().addEvent(event);
						machine.get().setStatus(event.getStatus());
						dataService.saveMachine(machine.get());
					} else {
						Machine newMachine = new Machine(event.getMachineId(), event.getStatus(), Lists.newArrayList(event));
						dataService.saveMachine(newMachine);
					}

				} catch (IOException e) {
					// TODO Proper exception hanlding and logging
					e.printStackTrace();
				}
			}
		}, 1000l, 10000l);
	}

}
