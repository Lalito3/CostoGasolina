package com.mexiti.costogasolina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mexiti.costogasolina.ui.theme.CostoGasolinaTheme
import java.text.NumberFormat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CostoGasolinaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize().background(color = Color.LightGray),
                    color = Color.LightGray
                ) {
                    CostGasLayout("Android")
                }
            }
        }
    }
}
//*** fUNCIONES COMPOSIBLES
private fun  calcularMonto(precio: Double, cantLitros:Double, propina:Double, propina_incluida:Boolean):String{
    val monto = if(propina_incluida){ precio * cantLitros + propina}
    else{precio * cantLitros}
    return NumberFormat.getCurrencyInstance().format(monto)/*Asigna a string ademas de*/
/*de aplicar un signo de $*/
}



@Composable
fun CostGasLayout(name: String) {
    var precioLitroEntrada by remember {
        mutableStateOf("")
    }
    var cantLitrosEntrada  by remember {
        mutableStateOf("")
    }
    var propinaEntrada by remember {
        mutableStateOf("")
    }
    var propina_incluida by remember {
        mutableStateOf(false) /*Se pone falso para que el switch esté en off*/
    }
    val precioLitro= precioLitroEntrada.toDoubleOrNull() ?: 0.0 /*Para ingresar algo numerico y si no null*/
    val cantLitros=cantLitrosEntrada.toDoubleOrNull() ?:0.0 /*en caso de que el usuario no introduce nada entonces se refleja como 0..0*/
    val propina=propinaEntrada.toDoubleOrNull() ?: 0.0
    val total= calcularMonto(precioLitro,cantLitros, propina, propina_incluida)
    Column (
        modifier = Modifier
        .fillMaxSize()
        .padding(10.dp) /*dp por la columna*/,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally /*Centra los elemetos en el column dentro del eje horizontal*/){
        Text(
            text = stringResource(R.string.calcular_monto),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,

            )
       EditNumberField(
           label = R.string.ingresa_gasolina,
           leadingIcon = R.drawable.money_gas ,
           modifier = Modifier.fillMaxWidth(),
           keyboardsOptions = KeyboardOptions.Default.copy(
               keyboardType = KeyboardType.Number,
               imeAction = ImeAction.Next
           ),
           value = precioLitroEntrada,
           onValueChanged = {
               precioLitroEntrada=it
           }
       )
        EditNumberField(
            label=R.string.litros,
            leadingIcon=R.drawable.gasolina,
            modifier= Modifier.fillMaxWidth(),
            keyboardsOptions = KeyboardOptions.Default.copy(
                keyboardType =  KeyboardType.Number, /*genera teclado numerico*/
                imeAction=ImeAction.Next
            ),
            value=cantLitrosEntrada,
            onValueChanged  =  {
                cantLitrosEntrada=it /*sincroniza con la variable y cambia segun lo que integre el usuario*/
            }
        )
       /* TextField(
            value = stringResource(R.string.litros),
            onValueChange = {} /*Funcion sin nombres {} zona donde se pueden hacer operaciones*/
        )*/
       EditNumberField(
           label=R.string.propina,
           leadingIcon = R.drawable.baseline_monetization_on_24,
           modifier=Modifier.fillMaxWidth(

           ),
           keyboardsOptions = KeyboardOptions.Default.copy(
               keyboardType = KeyboardType.Number,
               imeAction = ImeAction.Next
           ),
           value= propinaEntrada,
           onValueChanged= {
               propinaEntrada=it
           }
       )
        /*para stringR es en el foco al lado deextract*/
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .padding(vertical=8.dp)

        ){
        Text(
            text= stringResource(R.string.PropinarAgregar)
        )
            Spacer(modifier = Modifier.width(20.dp))
            Switch(
                checked = propina_incluida,
                onCheckedChange = { propina_incluida = it }
            )
        }
        Text(
            text = stringResource(R.string.total, total),
            modifier=Modifier.offset(x=-70.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp

        )

    }

}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardsOptions:KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        label = { Text(text = stringResource(id = label))  },
        value = value,
        singleLine = true,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon) , contentDescription = null) },
        keyboardOptions = keyboardsOptions, /*Da un tipo de teclado numerico*/
        modifier = modifier,
        onValueChange = onValueChanged
    )

}

@Preview(showBackground = true)
@Composable
fun CostGasLayoutPreview() {
    CostoGasolinaTheme {
        CostGasLayout("Android")
    }
}