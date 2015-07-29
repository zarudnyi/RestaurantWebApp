package zarudnyi.trials.restaurant.model.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;

public abstract class RestaurantAppSQLiteDao {


    protected JdbcTemplate jdbc;

    @Autowired
    public final void setDataSource(DataSource dataSource) {
        if(this.jdbc == null || dataSource != this.jdbc.getDataSource()) {
            this.jdbc = this.createJdbcTemplate(dataSource);
            this.initTemplateConfig();
        }

    }

    protected Integer genericInsert (String insertQuery, Object... params){
        Integer id = null;
        Connection conn = getConnection();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            int i=0;
            for (Object param:params){
                i++;
                if (param==null)
                    preparedStatement.setNull(i,Types.NULL);
                if (param instanceof Integer)
                    preparedStatement.setInt(i,(Integer) param);
                if (param instanceof String)
                    preparedStatement.setString(i,(String) param);
                if (param instanceof Date)
                    preparedStatement.setDate(i,(Date) param);
            }

            preparedStatement.executeUpdate();

            ResultSet resultSet = conn.prepareStatement("SELECT last_insert_rowid()").executeQuery();
            resultSet.next();
            id = resultSet.getInt(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return id;


    }

    protected JdbcTemplate createJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    public final DataSource getDataSource() {
        return this.jdbc != null?this.jdbc.getDataSource():null;
    }

    public final void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
        this.initTemplateConfig();
    }

    public final JdbcTemplate getJdbcTemplate() {
        return this.jdbc;
    }

    protected void initTemplateConfig() {
    }

    protected void checkDaoConfig() {
        if(this.jdbc == null) {
            throw new IllegalArgumentException("\'dataSource\' or \'jdbcTemplate\' is required");
        }
    }

    protected final SQLExceptionTranslator getExceptionTranslator() {
        return this.getJdbcTemplate().getExceptionTranslator();
    }

    protected final Connection getConnection() throws CannotGetJdbcConnectionException {
        return DataSourceUtils.getConnection(this.getDataSource());
    }

    protected final void releaseConnection(Connection con) {
        DataSourceUtils.releaseConnection(con, this.getDataSource());
    }


}
