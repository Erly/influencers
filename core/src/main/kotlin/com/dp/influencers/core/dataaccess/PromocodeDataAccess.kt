package com.dp.influencers.core.dataaccess

import com.dp.influencers.core.dataaccess.interfaces.IPromocodeDataAccess
import com.dp.influencers.core.model.Promocode
import com.dp.influencers.core.use
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Types
import java.util.ArrayList

class PromocodeDataAccess : MariaDbDataAccess(), IPromocodeDataAccess {
    private val ID = "Id"
    private val OFFERGUID = "offerGUID"
    private val CODE = "code"
    private val LINK = "link"
    private val SHOWN = "shown"
    private val DISABLED = "disabled"
    private val CREATIONDATE= "creationDate"
    protected var EXECUTED_WITHOUT_CHANGES: Int = 0


    @Throws(SQLException::class)
    override fun getPromocodes(guid: String): List<Promocode> {

        val promocodeList = ArrayList<Promocode>()

        getConnection().use { cnx ->
            cnx.prepareCall("{call Promocode_Get (?)}").use { stat ->
                stat.setString(1, guid)
                val res = stat.executeQuery()
                while (res.next()) {
                    promocodeList.add(getPromocodeFromResultSet(res))
                }
            }
        }

       return promocodeList
    }

    @Throws(SQLException::class)
    override fun setPromocode(promocode: Promocode): Boolean {
        var promocodeSetStatus = false

        var cnx: Connection? = null
        try {
            cnx = getConnection(false)
            cnx.autoCommit = false

            val stat = cnx.prepareCall("{call Promocode_Set (?, ?, ?, ?, ?, ?)}")
            stat.setLong(1, promocode.offerGUID!!)
            stat.setString(2, promocode.code)
            stat.setString(3, promocode.link)
            stat.setBoolean(4, promocode.shown)
            stat.setBoolean(5, promocode.disabled)
            stat.setLong(6, promocode.creationDate)

            stat.execute()
            promocodeSetStatus = stat.getInt(5) > EXECUTED_WITHOUT_CHANGES
            cnx.commit()
        } catch (e: Exception) {
            cnx!!.rollback()
        } finally {
            cnx!!.close()
        }

        return promocodeSetStatus
    }


    @Throws(SQLException::class)
    private fun getPromocodeFromResultSet(res: ResultSet): Promocode {
        val promocode = Promocode(
                res.getObject(ID) as Long,
                res.getObject(OFFERGUID) as Long,
                res.getObject(CODE) as String,
                res.getObject(LINK) as String,
                res.getObject(SHOWN) as Boolean,
                res.getObject(DISABLED) as Boolean,
                res.getObject(CREATIONDATE) as Long
        )
        return promocode
    }
}
