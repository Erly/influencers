package com.dp.influencers.core.dataaccess

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

abstract class MariaDbDataAccess {
    private var connection: Connection? = null
    private var connectionString: String = ""

    protected fun MariaDbDataAccess() {};

    @Throws(SQLException::class)
    protected fun MariaDbDataAccess(props: Properties) {
        this.connectionString = getConnectionString(props.getProperty("maria.host"), Integer.parseInt(props.getProperty("maria.port")), props.getProperty("maria.database"), props.getProperty("maria.user"),
                props.getProperty("maria.password"))
    }

    @Throws(SQLException::class)
    protected fun MariaDbDataAccess(host: String, port: Int?, databaseName: String, user: String, password: String) {
        this.connectionString = getConnectionString(host, port!!, databaseName, user, password)
    }

    protected fun getConnectionString(host: String, port: Int, databaseName: String, username: String, password: String): String {
        return "jdbc:mariadb://$host:$port/$databaseName?user=$username&password=$password"
    }

    @Throws(SQLException::class)
    protected fun getConnection(autoCommit: Boolean): Connection {
        if (this.connection == null || !this.connection!!.isValid(1)) {
            this.connection = DriverManager.getConnection(this.connectionString)
        }
        this.connection!!.autoCommit = autoCommit
        return this.connection!!
    }

    @Throws(SQLException::class)
    protected fun getConnection(): Connection {
        return getConnection(true)
    }

    @Throws(SQLException::class)
    protected fun close() {
        if (this.connection != null && !this.connection!!.isClosed()) {
            this.connection!!.close()
        }
    }


}