package com.dp.influencers.core.dataaccess

import com.dp.influencers.core.dataaccess.interfaces.ICategoryDataAccess
import com.dp.influencers.core.model.Category
import com.dp.influencers.core.model.enum.CategoryTypeEnum
import com.dp.influencers.core.use
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.util.ArrayList

class CategoryDataAccess : MariaDbDataAccess(), ICategoryDataAccess {
    private val ID = "Id"
    private val PARENTID = "parentId"
    private val NAME = "name"
    private val CATEGORYTYPE = "categoryType"
    private val FRIENDLYNAME = "friendlyName"
    private val DESCRIPTION = "description"
    private val METATITLE = "metaTitle"
    private val METADESCRIPTION = "metaDescription"
    private val METAKEYWORDS = "metaKeywords"
    private val IMAGE = "image"
    private val ISMENU = "isMenu"
    private val CREATIONDATE= "creationDate"
    protected var EXECUTED_WITHOUT_CHANGES: Int = 0


    @Throws(SQLException::class)
    override fun getCategories(isMenu: Boolean): List<Category> {

        val categoryList = ArrayList<Category>()

        getConnection().use { cnx ->
            cnx.prepareCall("{call Category_Get (?)}").use { stat ->
                stat.setBoolean(1, isMenu)
                val res = stat.executeQuery()
                while (res.next()) {
                    categoryList.add(getCategoryFromResultSet(res))
                }
            }
        }

        return categoryList
    }

    @Throws(SQLException::class)
    override fun setCategory(category: Category): Boolean {
        var categorySetStatus = false

        var cnx: Connection? = null
        try {
            cnx = getConnection(false)
            cnx.autoCommit = false

            val stat = cnx.prepareCall("{call Category_Set (?, ?, ?, ?, ?, ?, ?, ?, ?)}")
            stat.setLong(1, category.parentId!!)
            stat.setString(2, category.name)
            stat.setString(3, category.friendlyName)
            stat.setString(4, category.description)
            stat.setString(5, category.metaTitle)
            stat.setString(6, category.metaDescription)
            stat.setString(7, category.metaKeywords)
            stat.setString(8, category.image)
            stat.setLong(9, category.creationDate)

            stat.execute()
            categorySetStatus = stat.getInt(5) > EXECUTED_WITHOUT_CHANGES
            cnx.commit()
        } catch (e: Exception) {
            cnx!!.rollback()
        } finally {
            cnx!!.close()
        }

        return categorySetStatus
    }


    @Throws(SQLException::class)
    private fun getCategoryFromResultSet(res: ResultSet): Category {
        val category = Category(
                res.getObject(ID) as Long,
                res.getObject(PARENTID) as Long,
                res.getObject(NAME) as String,
                res.getObject(CATEGORYTYPE) as CategoryTypeEnum,
                res.getObject(FRIENDLYNAME) as String,
                res.getObject(DESCRIPTION) as String,
                res.getObject(METATITLE) as String,
                res.getObject(METADESCRIPTION) as String,
                res.getObject(METAKEYWORDS) as String,
                res.getObject(IMAGE) as String,
                res.getObject(ISMENU) as Boolean,
                res.getObject(CREATIONDATE) as Long
        )
        return category
    }
}
