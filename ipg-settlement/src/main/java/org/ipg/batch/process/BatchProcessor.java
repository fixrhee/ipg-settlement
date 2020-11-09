package org.ipg.batch.process;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Component
@Repository
public class BatchProcessor {

	private JdbcTemplate jdbcTemplate;

	@Transactional
	public void updateTransfers(String trxNo, String trxTypeID) {
		jdbcTemplate.update(
				"UPDATE transfers SET transaction_state = 'PROCESSED' WHERE transaction_number = ? AND transfer_type_id = ? AND transaction_state = 'PENDING';",
				trxNo, trxTypeID);
		jdbcTemplate.update(
				"UPDATE transfers SET transaction_state = 'PROCESSED' WHERE parent_id = ? AND transfer_type_id = ? AND transaction_state = 'PENDING';",
				trxNo, trxTypeID);
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
