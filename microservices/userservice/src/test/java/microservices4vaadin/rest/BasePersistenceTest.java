package microservices4vaadin.rest;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.dbunit.DatabaseTestCase;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import microservices4vaadin.UserServiceApplication;

@SpringApplicationConfiguration(classes = {UserServiceApplication.class})
@EnableTransactionManagement
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public abstract class BasePersistenceTest extends DatabaseTestCase {

    protected static final String DATASET = "microservices4vaadin_resource_test_dataset.xml";

    @PostConstruct
    public void init() throws DatabaseUnitException, SQLException, Exception {
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
    }

    @Autowired
    private DataSource datasource;

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        DatabaseConnection connection = new DatabaseConnection(datasource.getConnection());
        DatabaseConfig dbConfig = connection.getConfig();
        dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
        return connection;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        return builder.build(this.getClass().getClassLoader().getResourceAsStream(DATASET));
    }

}
