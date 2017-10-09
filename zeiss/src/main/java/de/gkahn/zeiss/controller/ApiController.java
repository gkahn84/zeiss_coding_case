package de.gkahn.zeiss.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.gkahn.zeiss.model.Machine;
import de.gkahn.zeiss.persistence.DataService;

@RestController
@RequestMapping(value = "/api/v1")
public class ApiController {

	private DataService dataService;

	private ApiController(DataService dataService) {
		this.dataService = dataService;
	}

	@RequestMapping(value = "/machines", method = RequestMethod.GET)
	public List<Machine> machines() {
		return dataService.getAllMachines();
	}

	@RequestMapping(value = "/machine/{id}", method = RequestMethod.GET)
	public Machine machine(@PathVariable("id") String id) {
		return dataService.getMachine(id).orElseGet(null);
	}

}
