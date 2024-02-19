package com.example.nomad

// Backend.kt
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
import org.jetbrains.exposed.sql.jodatime.date
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate


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
  val deliveryDate: DateTime,  // Change the type to java.sql.Date
  val itemName: String,
  val itemCategory: String,
  val itemPrice: Double,
  val paymentType: String
)



object PurchaseOrderTable : IntIdTable("purchase_order") {
  val distributorName = varchar("distributor_name", 255)
  val contactInfo = integer("contact_info")
  val orderId = integer("order_id")
  val deliveryDate = date("delivery_date")
  val itemName = varchar("item_name", 255)
  val itemCategory = varchar("item_category", 255)
  val itemPrice = double("item_price")
  val paymentType = varchar("payment_type", 255)
}


data class Sales(
  val id: Int,
  val numberOfSales: Int,
  val salesAmount: Double,
  val paidAmount: Double,
  val bills: Double,
  val transactionType: String,
  val itemName: String
)

object SalesTable : IntIdTable("sales") {
  val numberOfSales = integer("number_of_sales")
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
    jackson {}
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
            it[PurchaseOrderTable.deliveryDate],
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
            it[SalesTable.numberOfSales],
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