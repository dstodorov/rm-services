package kotlin_fundamentals

open class FoldablePhone() : Phone() {
    private var isFolded: Boolean = true
    override fun switchOn() {
        super.isScreenLightOn = false
    }

    fun unFoldPhone() {
        isFolded = true
        super.isScreenLightOn = true
    }

    fun foldPhone() {
        isFolded = false
        super.isScreenLightOn = false
    }
}


open class Phone(var isScreenLightOn: Boolean = false) {
    open fun switchOn() {
        isScreenLightOn = true
    }

    open fun switchOff() {
        isScreenLightOn = false
    }

    fun checkPhoneScreenLight() {
        val phoneScreenLight = if (isScreenLightOn) "on" else "off"
        println("The phone screen's light is $phoneScreenLight.")
    }
}

fun main() {
    val regularPhone = Phone()
    regularPhone.checkPhoneScreenLight()
    regularPhone.switchOn()
    regularPhone.checkPhoneScreenLight()
    println()
    val foldablePhone = FoldablePhone()

    foldablePhone.checkPhoneScreenLight()
    foldablePhone.switchOn()
    foldablePhone.checkPhoneScreenLight()
    foldablePhone.unFoldPhone()
    foldablePhone.checkPhoneScreenLight()
    foldablePhone.foldPhone()
    foldablePhone.checkPhoneScreenLight()
}