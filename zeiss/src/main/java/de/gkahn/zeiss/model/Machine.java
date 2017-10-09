package de.gkahn.zeiss.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

import com.google.common.collect.ImmutableList;

@Entity
@Table(name = "machines")
@NamedQueries({@NamedQuery(name = "findAllMachines", query = "SELECT m FROM Machine m",
		hints = {@QueryHint(name = "org.hibernate.readOnly", value = "true")})})
public class Machine {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private MachineStatus status;

	@Column(name = "events")
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "machine_events", joinColumns = @JoinColumn(name = "join_id"))
	private List<MachineEvent> events;

	public Machine() {
		// Needed for Hibernate
	};

	public Machine(String id, MachineStatus status, List<MachineEvent> events) {
		this.id = id;
		this.status = status;
		this.events = events;
	}

	public void addEvent(MachineEvent event) {
		events.add(event);
	}

	public String getId() {
		return id;
	}

	public MachineStatus getStatus() {
		return status;
	}

	public void setStatus(MachineStatus status) {
		this.status = status;
	}

	public List<MachineEvent> getEvents() {
		return ImmutableList.copyOf(events);
	}

}
