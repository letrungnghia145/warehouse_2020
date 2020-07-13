package model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Log {
	private int id_log;
	private int id_config;
	private String source_dir;
	private String source_name;
	private Timestamp time_insert;
	private String current_action;
	private String status;
}
