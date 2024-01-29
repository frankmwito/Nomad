package com.example.nomad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.nomad.ui.theme.NomadTheme

class ManagementScreen : ComponentActivity() {
  @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      EmployeesManagement()
      NomadTheme {
        val geofenceManager = remember { GeofenceManager(applicationContext) }
        ManagementScreen(geofenceManager)
      }
    }
  }
}

data class Employee(val id: Int, val name: String, val role: String, var sales: Int, var checkedIn: Boolean)

@ExperimentalMaterialApi
@Composable
fun EmployeesManagement() {
  val employees = remember {
    mutableStateListOf(
      Employee(1, "John", "Waiter", 0, false),
      Employee(2, "Jane", "Waiter", 0, false),
      Employee(3, "Mark", "Waiter", 0, false)
    )
  }

  val scaffoldState = rememberScaffoldState()
  val addEmployeeDialogState = remember { mutableStateOf(false) }
  val deleteEmployeeDialogState = remember { mutableStateOf(false) }
  val selectedEmployee = remember { mutableStateOf<Employee?>(null) }

  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      TopAppBar(
        title = { Text("Employees Management") },
        actions = {
          IconButton(onClick = { addEmployeeDialogState.value = true }) {
            Icon(Icons.Filled.Add, "Add Employee")
          }
        }
      )
    },
    floatingActionButton = {
      FloatingActionButton(onClick = { /* Add employee implementation */ }) {
        Icon(Icons.Filled.Add, "Add Employee")
      }
    }
  ) { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      EmployeeList(employees = employees, onCheckInClick = { employeeId, checkedIn ->
        employees.find { it.id == employeeId }?.let { employee ->
          employee.checkedIn = checkedIn
        }
      }, onSalesUpdated = { employeeId, sales ->
        employees.find { it.id == employeeId }?.let { employee ->
          employee.sales = sales
        }
      }, onDeleteClick = { employeeId ->
        selectedEmployee.value = employees.find { it.id == employeeId }
        deleteEmployeeDialogState.value = true
      })
    }

    if (addEmployeeDialogState.value) {
      AddEmployeeDialog(onDismissRequest = { addEmployeeDialogState.value = false }, onAddEmployee = { name, role ->
        val newEmployee = Employee(employees.size + 1, name, role, 0, false)
        employees.add(newEmployee)
      })
    }

    if (deleteEmployeeDialogState.value) {
      DeleteEmployeeDialog(onDismissRequest = { deleteEmployeeDialogState.value = false }, onDeleteEmployee = {
        employees.remove(selectedEmployee.value)
        selectedEmployee.value = null
      })
    }
  }
}

@Composable
fun EmployeeList(
  employees: List<Employee>,
  onCheckInClick: (Int, Boolean) -> Unit,
  onSalesUpdated: (Int, Int) -> Unit,
  onDeleteClick: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(modifier = modifier.padding(8.dp)) {
    items(employees) { employee ->
      EmployeeRow(
        employee = employee,
        onCheckInClick = { checkedIn -> onCheckInClick(employee.id, checkedIn) },
        onSalesUpdated = { sales -> onSalesUpdated(employee.id, sales) },
        onDeleteClick = { onDeleteClick(employee.id) }
      )
    }
  }
}

@Composable
fun EmployeeRow(
  employee: Employee,
  onCheckInClick: (Boolean) -> Unit,
  onSalesUpdated: (Int) -> Unit,
  onDeleteClick: () -> Unit
) {
  var showDialog by remember { mutableStateOf(false) }

  Surface(modifier = Modifier.padding(8.dp)) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(text = employee.name)
      Text(text = "Role: ${employee.role}, Sales: ${employee.sales}")
      Checkbox(checked = employee.checkedIn, onCheckedChange = { checkedIn ->
        onCheckInClick(checkedIn)
      })
      Button(onClick = { showDialog = true }) {
        Text(text = "Update Sales")
      }
      IconButton(onClick = onDeleteClick) {
        Icon(Icons.Filled.Delete, "Delete Employee")
      }
    }
  }

  if (showDialog) {
    SalesDialog(employee = employee, onDismiss = { showDialog = false }, onSalesUpdated = onSalesUpdated)
  }
}
@Composable
fun SalesDialog(employee: Employee, onDismiss: () -> Unit, onSalesUpdated: (Int) -> Unit) {
  var sales by remember { mutableStateOf(employee.sales.toString()) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = MaterialTheme.shapes.medium,
      color = MaterialTheme.colors.surface
    ) {
      Column(modifier = Modifier.padding(10.dp)) {
        Text(text = "Update Sales for ${employee.name}")
        TextField(
          value = sales,
          onValueChange = { sales = it },
          label = { Text("Sales") },
          keyboardActions = KeyboardActions(onDone = {
            onSalesUpdated(sales.toInt())
            onDismiss()
          })
        )
        Row(
          modifier = Modifier.padding(top = 13.dp),
          horizontalArrangement = Arrangement.End
        ) {
          Button(onClick = {
            onSalesUpdated(sales.toInt())
            onDismiss()
          }) {
            Text("OK")
          }
          Button(onClick = onDismiss) {
            Text("Cancel")
          }
        }
      }
    }
  }
}
@Composable
fun AddEmployeeDialog(onDismissRequest: () -> Unit, onAddEmployee: (String, String) -> Unit) {
  var name by remember { mutableStateOf("") }
  var role by remember { mutableStateOf("") }

  Dialog(onDismissRequest = onDismissRequest) {
    Surface(
      shape = MaterialTheme.shapes.medium,
      color = MaterialTheme.colors.surface
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Add Employee")
        TextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("Name") },
          modifier = Modifier.fillMaxWidth()
        )
        TextField(
          value = role,
          onValueChange = { role = it },
          label = { Text("Role") },
          modifier = Modifier.fillMaxWidth()
        )
        Row(
          modifier = Modifier.padding(top = 16.dp),
          horizontalArrangement = Arrangement.End
        ) {
          Button(onClick = {
            onAddEmployee(name, role)
            onDismissRequest()
          }) {
            Text("Add")
          }
        }
      }
    }
  }
}
@Composable
fun DeleteEmployeeDialog(onDismissRequest: () -> Unit, onDeleteEmployee: () -> Unit) {
  Dialog(onDismissRequest = onDismissRequest) {
    Surface(
      shape = MaterialTheme.shapes.medium,
      color = MaterialTheme.colors.surface
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Delete Employee")
        Text(text = "Are you sure you want to delete this employee?")
        Row(
          modifier = Modifier.padding(top = 16.dp),
          horizontalArrangement = Arrangement.End
        ) {
          Button(onClick = {
            onDeleteEmployee()
            onDismissRequest()
          }) {
            Text("Delete")
          }
        }
        ManagementScreen()
      }
    }
  }
}
@Composable
fun ManagementScreen(geofenceManager: GeofenceManager) {
  Column(modifier = Modifier.padding(16.dp)) {
    Text(text = "Management Screen")
    Spacer(modifier = Modifier.width(16.dp))
    Row {
      Button(
        onClick = { geofenceManager.checkInOut() },
        modifier = Modifier.weight(1f)
      ) {
        Text(
          text = if (geofenceManager.isCheckIn.value) "Check Out" else "Check In",
          modifier = Modifier.padding(8.dp)
        )
      }
      Spacer(modifier = Modifier.width(16.dp))

      // Add other UI elements here
    }
  }
}
@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun PreviewEmployeesManagement(){
  EmployeesManagement()
}