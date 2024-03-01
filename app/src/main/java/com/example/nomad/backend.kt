package com.example.nomad

// Backend.kt
/*import com.example.nomad.PurchaseOrderTable.nullable*/
import com.fasterxml.jackson.datatype.joda.JodaModule
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

data class StockManagement(
  val id: Int,
  val itemName: String,
  val currentStock: Int,
  val minStockLevel: Int,
  val maxStockLevel: Int,
  val itemPrice: Double,
  val supplierName: String,
  val category: String
)

object StockManagementTable : IntIdTable("stock_management") {
  val itemName = varchar("item_name", 255)
  val currentStock = integer("current_stock")
  val minStockLevel = integer("min_stock_level")
  val maxStockLevel = integer("max_stock_level")
  val itemPrice = double("item_price")
  val supplierName = varchar("supplier_name", 255)
  val category = text("category")
}
data class Requisition(
  val requesterName: String,
  val requisitionId: Int,
  val requesterContact: String,
  val itemDescription: String,
  val quantity: Int,
  val unitOfMeasure: String,
  val costCenter: String,
  val budgetAllocation: Double,
  val deliveryAddress: String,
  val preferredVendor: String,
  val justification: String,
  val requisitionStatus: String,
  val purchaseOrderId: Int?,
)

object RequisitionTable : IntIdTable("requisition") {
  val requesterName = varchar("requester_name", 255)
  val requisitionId = integer("requisition_id")
  val requesterContact = varchar("requester_contact", 20)
  val itemDescription = varchar("item_description", 255)
  val quantity = integer("quantity")
  val unitOfMeasure = varchar("unit_of_measure", 20)
  val costCenter = varchar("cost_center", 50)
  val budgetAllocation = decimal("budget_allocation", 10, 2)
  val deliveryAddress = varchar("delivery_address", 255)
  val preferredVendor = varchar("preferred_vendor", 255)
  val justification = text("justification")
  val requisitionStatus = varchar("requisition_status", 20)
  val purchaseOrderId = integer("purchase_order_id").nullable()
}
data class ReceiveGoods(
  val id: Int,
  /*val receivedDate: String,*/
  val itemName: String,
  val receivedQuantity: Int,
  val supplierName: String,
  val invoiceNumber: String
)

object ReceiveGoodsTable : IntIdTable("receivegoods") {
  /*val receivedDate = date("received_date")*/
  val itemName = varchar("item_name", 255)
  val receivedQuantity = integer("received_quantity")
  val supplierName = varchar("supplier_name", 255)
  val invoiceNumber = varchar("invoice_number", 255)
}

data class InventoryManagement(
  val itemName: String,
  val itemPrice: Double,
  val itemDescription: String,
  val itemCategory: String
)

object InventoryManagementTable : IntIdTable("inventory_management") {
  val itemName = varchar("item_name", 255)
  val itemPrice = double("item_price")
  val itemDescription = text("item_description")
  val itemCategory = text("item_category")
  // Add 'id' column explicitly

  // Other columns...
}



data class PurchaseOrder(
  val id: Int,
  val distributorName: String,
  val contactInfo: Int,
  val orderId: Int,
 /* val deliveryDate: Long?, // nullable java.sql.Date*/
  val itemName: String,
  val itemCategory: String,
  val itemPrice: Double,
  val paymentType: String
)

object PurchaseOrderTable : IntIdTable("purchase_order") {
  val distributorName = varchar("distributor_name", 255)
  val contactInfo = integer("contact_info")
  val orderId = integer("order_id")
  /*val deliveryDate = long("delivery_date").nullable() // nullable Long column*/
  val itemName = varchar("item_name", 255)
  val itemCategory = varchar("item_category", 255)
  val itemPrice = double("item_price")
  val paymentType = varchar("payment_type", 255)
}

data class Sales(
  val id: Int,
  val noofSales: Int,
  val salesAmount: Double,
  val paidAmount: Double,
  val bills: Double,
  val transactionType: String,
  val itemName: String
)

object SalesTable : IntIdTable("sales") {
  val noofsales = integer("no_of_sales")
  val salesAmount = double("sales_amount")
  val paidAmount = double("paid_amount")
  val bills = double("bills")
  val transactionType = varchar("transaction_type", 255)
  val itemName = varchar("item_name", 255)
}

data class Users(
  val id: Int,
  val username: String,
  val password: String
)

object UsersTable : IntIdTable("users") {
  val username = varchar("username", 255)
  val password = varchar("password", 255)
}

fun Application.module() {

  Database.connect("jdbc:mysql://127.0.0.1:3306/mangodatabase",
    driver = "com.mysql.cj.jdbc.Driver",
    user = "root",
    password = "Frank@1919")

  install(ContentNegotiation) {
    jackson {
      registerModule(JodaModule())
    }
  }

  install(StatusPages) {
    exception<Throwable> { call,  cause ->
      call.respond(HttpStatusCode.InternalServerError, "Internal Server Error: ${cause.localizedMessage}")
      call.application.log.error("Unhandled exception", cause)
    }
  }



  routing {
     get("/inventory-management") {
      val inventoryManagementList = transaction {
        InventoryManagementTable.selectAll().map {
          InventoryManagement(
            it[InventoryManagementTable.itemName],
            it[InventoryManagementTable.itemPrice],
            it[InventoryManagementTable.itemDescription],
            it[InventoryManagementTable.itemCategory]
          )
        }
      }
      call.respond(inventoryManagementList)
      this@module.log.info("Fetched ${inventoryManagementList.size} inventory management items")
    }

    get("/purchase-order") {
      val purchaseOrderList = transaction {
        PurchaseOrderTable.selectAll().map {
          PurchaseOrder(
            it[PurchaseOrderTable.id].value,
            it[PurchaseOrderTable.distributorName],
            it[PurchaseOrderTable.contactInfo],
            it[PurchaseOrderTable.orderId],
            it[PurchaseOrderTable.itemName],
            it[PurchaseOrderTable.itemCategory],
            it[PurchaseOrderTable.itemPrice],
            it[PurchaseOrderTable.paymentType]
          )
        }
      }
      call.respond(purchaseOrderList)
      this@module.log.info("Fetched ${purchaseOrderList.size} purchase orders")
    }

    get("/sales") {
      val salesList = transaction {
        SalesTable.selectAll().map {
          Sales(
            it[SalesTable.id].value,
            it[SalesTable.noofsales],
            it[SalesTable.salesAmount],
            it[SalesTable.paidAmount],
            it[SalesTable.bills],
            it[SalesTable.transactionType],
            it[SalesTable.itemName]
          )
        }
      }
      call.respond(salesList)
      this@module.log.info("Fetched ${salesList.size} sales")
    }
    get("/receivegoods") {
      val receivegoodsList = transaction {
        ReceiveGoodsTable.selectAll().map {
          ReceiveGoods(
            it[ReceiveGoodsTable.id].value,
           /*row[ReceiveGoodsTable.receivedDate].toString(),*/
            it[ReceiveGoodsTable.itemName],
            it[ReceiveGoodsTable.receivedQuantity],
            it[ReceiveGoodsTable.supplierName],
            it[ReceiveGoodsTable.invoiceNumber]
          )
        }
      }
      call.respond(receivegoodsList)
      this@module.log.info("Fetched ${receivegoodsList.size} sales")
    }
    get("/stock_management") {
      val stockmanagementList = transaction {
        StockManagementTable.selectAll().map {
          StockManagement(
            it[StockManagementTable.id].value,
            it[StockManagementTable.itemName],
            it[StockManagementTable.currentStock],
            it[StockManagementTable.minStockLevel],
            it[StockManagementTable.maxStockLevel],
            it[StockManagementTable.itemPrice],
            it[StockManagementTable.supplierName],
            it[StockManagementTable.category]
          )
        }
      }
      call.respond(stockmanagementList)
      this@module.log.info("Fetched ${stockmanagementList.size} sales")
    }
    get("/requisition") {
      val requisitionList = transaction {
        RequisitionTable .selectAll().map {
          Requisition(
            it[RequisitionTable.requesterName],
            it[RequisitionTable.requisitionId],
            it[RequisitionTable.requesterContact],
            it[RequisitionTable.itemDescription],
            it[RequisitionTable.quantity],
            it[RequisitionTable.unitOfMeasure],
            it[RequisitionTable.costCenter],
            it[RequisitionTable.budgetAllocation].toDouble(),
            it[RequisitionTable.deliveryAddress],
            /*row[RequisitionTable.requiredDeliveryDate].toString(),*/
            it[RequisitionTable.preferredVendor],
            it[RequisitionTable.justification],
            it[RequisitionTable.requisitionStatus],
            it[RequisitionTable.purchaseOrderId]
            /*row[RequisitionTable.createdAt].toString()*/
          )
        }
      }
      call.respond(requisitionList)
      this@module.log.info("Fetched ${requisitionList.size} sales")
    }

    get("/users") {
      val usersList = transaction {
        UsersTable.selectAll().map {
          Users(
            it[UsersTable.id].value,
            it[UsersTable.username],
            it[UsersTable.password]
          )
        }
      }
      call.respond(usersList)
      this@module.log.info("Fetched ${usersList.size} users")
    }
  }
}



fun main() {

  embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
    module()
  }.start(wait = true)
}