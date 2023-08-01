fun main() {
    val celsius = 27.0
    val kelvin = 350.0
    val fahrenheit = 10.0

    printFinalTemperature(celsius, "Celsius", "Fahrenheit") {
        9.0 / 5.0 * celsius + 32
    }

    printFinalTemperature(kelvin, "Kelvin", "Celsius") {
        kelvin - 273.15
    }

    printFinalTemperature(fahrenheit, "Fahrenheit", "Kelvin") {
        5.0 / 9.0 * (fahrenheit - 32) + 273.15
    }
}


fun printFinalTemperature(
        initialMeasurement: Double,
        initialUnit: String,
        finalUnit: String,
        conversionFormula: (Double) -> Double
) {
    val finalMeasurement = String.format("%.2f", conversionFormula(initialMeasurement)) // two decimal places
    println("$initialMeasurement degrees $initialUnit is $finalMeasurement degrees $finalUnit.")
}