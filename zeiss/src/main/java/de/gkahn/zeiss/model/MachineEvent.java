package de.gkahn.zeiss.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.Type;
import org.joda.time.Instant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class MachineEvent {

	@Column(name = "id")
	private String id;

	@Column(name = "machine_id")
	private String machineId;

	@Column(name = "timestamp")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentInstantAsMillisLong")
	private Instant timestamp;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private MachineStatus status;

	public MachineEvent() {
		// Needed for Hibernate
	}

	@JsonCreator
	public MachineEvent(@JsonProperty("id") String id, @JsonProperty("machine_id") String machineId,
			@JsonProperty("timestamp") String timestamp, @JsonProperty("status") String status) {
		this.id = id;
		this.machineId = machineId;
		this.timestamp = Instant.parse(timestamp);
		this.status = MachineStatus.valueOf(status);
	}

	public String getId() {
		return id;
	}

	public String getMachineId() {
		return machineId;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public MachineStatus getStatus() {
		return status;
	}

	@JsonGetter(value = "timestamp")
	public String getJsonTimestamp() {
		return timestamp.toString();
	}

	@JsonGetter(value = "status")
	public String getJsonStatus() {
		return status.toString();
	}

}
