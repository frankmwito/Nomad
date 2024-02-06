package com.example.nomad

// Backend.kt

import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

data class PurchaseOrder(
  val id: Int,
  val distributorName: String,
  val contactInfo: String,
  val orderId: Int,
  val deliveryDate: String,
  val itemName: String,
  val itemCategory: String,
  val itemPrice: Double,
  val paymentType: String
)

object PurchaseOrders : IntIdTable() {
  val distributorName = varchar("distributorName", 255)
  val contactInfo = varchar("contactInfo", 255)
  val orderId = integer("orderId")
  val deliveryDate = varchar("deliveryDate", 255)
  val itemName = varchar("itemName", 255)
  val itemCategory = varchar("itemCategory", 255)
  val itemPrice = double("itemPrice")
  val paymentType = varchar("paymentType", 255)
}

fun Application.module() {
  install(ContentNegotiation) {
    Json
  }

  install(StatusPages) {
    exception<Throwable> { call, cause ->
      call.respond(HttpStatusCode.InternalServerError, cause.localizedMessage)
    }
  }
  Database.connect(
    "jdbc:postgresql://ep-delicate-star-a3etpqim.il-central-1.aws.neon.tech:5432/Mango",
    driver = "org.postgresql.Driver",
    user = "mfranklyne039@gmail.com",
    password = "Y2rDapH1zkPg"
  )

  transaction {
    SchemaUtils.create(PurchaseOrders)
  }

  routing {
    route("/purchase-orders") {
      get {
        call.respond(getAllPurchaseOrders())
      }
      }
    }
  }

fun getAllPurchaseOrders(): List<PurchaseOrder> {
  return transaction {
    PurchaseOrders.selectAll().map {
      PurchaseOrder(
        it[PurchaseOrders.id].value,
        it[PurchaseOrders.distributorName],
        it[PurchaseOrders.contactInfo],
        it[PurchaseOrders.orderId],
        it[PurchaseOrders.deliveryDate],
        it[PurchaseOrders.itemName],
        it[PurchaseOrders.itemCategory],
        it[PurchaseOrders.itemPrice],
        it[PurchaseOrders.paymentType]
      )
    }
  }
}

fun main() {
  embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}
