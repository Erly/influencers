package com.dp.influencers.core.dataaccess

import com.dp.influencers.core.dataaccess.interfaces.IOfferDataAccess
import com.dp.influencers.core.model.OfferData
import com.dp.influencers.core.model.OfferType
import com.dp.influencers.core.use
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.time.Instant
import java.util.ArrayList

class OfferDataAccess : MariaDbDataAccess(), IOfferDataAccess {
    private val ID = "Id"
    private val BRANDID = "brandId"
    private val CATEGORYID = "categoryId"
    private val CATEGORYNAME = "categoryName"
    private val PARENTCATEGORYID = "parentCategoryId"
    private val PARENTCATEGORYNAME = "parentCategoryName"
    private val OFFERID = "offerId"
    private val NAME = "name"
    private val DESCRIPTION = "description"
    private val CLAIM = "claim"
    private val TERMS = "terms"
    private val IMAGE = "image"
    private val CODESNIPPET = "codeSnippet"
    private val DISABLE = "disable"
    private val STARTDATE = "startDate"
    private val ENDDATE = "endDate"
    private val METATITLE = "metaTitle"
    private val METADESCRIPTION = "metaDescription"
    private val METAKEYWORD = "metaKeywords"
    private val SPECIALCATEGORYID = "specialCategoryId"
    private val SPECIALCATEGORYNAME = "specialCategoryName"
    private val CREATIONDATE = "creationDate"
    protected var EXECUTED_WITHOUT_CHANGES: Int = 0


    @Throws(SQLException::class)
    override fun getOffers(brandId: Long): List<OfferData> {

        val offerDataList = ArrayList<OfferData>()

        getConnection().use { cnx ->
            cnx.prepareCall("{call Offer_Get (?)}").use { stat ->
                stat.setLong(1, brandId)
                val res = stat.executeQuery()
                while (res.next()) {
                    offerDataList.add(getOfferFromResultSet(res))
                }
            }
        }

        return offerDataList
    }

    @Throws(SQLException::class)
    override fun setOffer(offer: OfferData): Boolean {
        var offerSetStatus = false

        var cnx: Connection? = null
        try {
            cnx = getConnection(false)
            cnx.autoCommit = false

            val stat = cnx.prepareCall("{call Offer_Set (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")
            stat.setLong(1, offer.brandId)
            stat.setLong(2, offer.categoryId)
            stat.setLong(3, offer.offerType.id)
            stat.setString(4, offer.name)
            stat.setString(5, offer.description)
            stat.setString(6, offer.claim)
            stat.setString(7, offer.terms)
            stat.setString(8, offer.image)
            stat.setString(9, offer.codeSnippet)
            stat.setLong(10, offer.startDate)
            stat.setLong(11, offer.endDate)
            stat.setString(12, offer.metaTitle)
            stat.setString(13, offer.metaDescription)
            stat.setString(14, offer.metaKeywords)
            stat.setBoolean(15, offer.disabled)
            stat.setLong(16, offer.creationDate)

            stat.execute()
            offerSetStatus = stat.getInt(5) > EXECUTED_WITHOUT_CHANGES
            cnx.commit()
        } catch (e: Exception) {
            cnx!!.rollback()
        } finally {
            cnx!!.close()
        }

        return offerSetStatus
    }


    @Throws(SQLException::class)
    private fun getOfferFromResultSet(res: ResultSet): OfferData {
        val offerData = OfferData(
                res.getObject(ID) as Long,
                res.getObject(BRANDID) as Long,
                res.getObject(CATEGORYID) as Long,
                res.getObject(CATEGORYNAME) as String,
                res.getObject(PARENTCATEGORYID) as Long,
                res.getObject(PARENTCATEGORYNAME) as String,
                res.getObject(OFFERID) as OfferType,
                res.getObject(NAME) as String,
                res.getObject(DESCRIPTION) as String,
                res.getObject(CLAIM) as String,
                res.getObject(TERMS) as String,
                res.getObject(IMAGE) as String,
                res.getObject(CODESNIPPET) as String,
                res.getObject(DISABLE) as Boolean,
                res.getObject(STARTDATE) as Long,
                res.getObject(ENDDATE) as Long,
                res.getObject(METATITLE) as String,
                res.getObject(METADESCRIPTION) as String,
                res.getObject(METAKEYWORD) as String,
                res.getObject(CREATIONDATE) as Long,
                res.getObject(SPECIALCATEGORYID) as Long,
                res.getObject(SPECIALCATEGORYNAME) as String
        )
        return offerData
    }
}