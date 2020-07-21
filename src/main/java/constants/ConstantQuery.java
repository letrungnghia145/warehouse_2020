package constants;

public class ConstantQuery {
	public static final String LOAD_CONFIG_BY_ID = "SELECT * FROM CONFIG WHERE id_config = ?";
	public static final String LOAD_ALL_CONFIGS = "SELECT * FROM CONFIG";
	public static final String GET_LOG_BY_ID = "SELECT * FROM LOG WHERE id_log = ?";
	public static final String GET_LOG_BY_IDCONFIG_ACTION_STATUS = "SELECT * FROM LOG WHERE id_config = ? and current_action like ? and status like ? limit 1";
	public static final String GET_ALL_LOGS = "SELECT * FROM LOG";
	public static final String LOAD_CONFIG_BY_STATUS = "SELECT * FROM CONFIG WHERE status like ?";
	public static final String INSERT_LOG = "INSERT INTO LOG VALUES(?, ?, ?, ?, ?, ?, ?)";
	public static final String GET_NAME_TABLE_FROM_DB = "SELECT TABLE_NAME FROM information_schema.tables WHERE TABLE_SCHEMA = ?";
	public static final String UPDATE_LOG_ACTION_STATUS_LOG_BY_ID = "UPDATE LOG SET CURRENT_ACTION = ?, STATUS = ? WHERE ID_LOG = ?";
	public static final String UPDATE_CONFIG_STATUS_BY_CONFIG_ID = "UPDATE CONFIG SET STATUS = ? WHERE ID_CONFIG = ?";
}
