package pl.tofix.ipcalc2

import kotlin.math.pow

fun calculateNetworkAddress(ip: String, mask: String): String {
    val ipParts = ip.split(".")
    val maskParts = mask.split(".")

    val networkParts = mutableListOf<String>()
    for (i in ipParts.indices) {
        val ipValue = ipParts[i].toInt()
        val maskValue = maskParts[i].toInt()
        val networkValue = ipValue and maskValue
        networkParts.add(networkValue.toString())
    }

    return networkParts.joinToString(separator = ".")
}

fun calculateAvailableHosts(mask: String): Int {
    val binmask = convertMaskToBinary(mask)
    val hostBits = binmask.count { it == '0' }
    val availableHosts = 2.0.pow(hostBits.toDouble()) - 2
    return availableHosts.toInt()
}

fun calculateBroadcast(ip: String, subnetMask: String): String {
    val ipParts = ip.split('.').map { it.toInt() }
    val subnetMaskParts = subnetMask.split('.').map { it.toInt() }

    val broadcastAddressParts = mutableListOf<Int>()
    for (i in 0 until 4) {
        val broadcastPart = ipParts[i] or (subnetMaskParts[i] xor 255)
        broadcastAddressParts.add(broadcastPart)
    }

    return broadcastAddressParts.joinToString(".")
}

fun calculateMinHost(ip: String, subnetMask: String): String {
    val ipParts = ip.split('.').map { it.toInt() }
    val maskParts = subnetMask.split('.').map { it.toInt() }

    val minHostParts = mutableListOf<Int>()
    for (i in 0 until 3) {
        minHostParts.add(ipParts[i] and maskParts[i])
    }
    minHostParts.add(1)

    return minHostParts.joinToString(".")
}

fun calculateMaxHost(ip: String, subnetMask: String): String {
    val broadcastAddress = calculateBroadcast(ip, subnetMask)
    val maxHostParts = broadcastAddress.split('.').map { it.toInt() }.toMutableList()
    maxHostParts[3]--

    return maxHostParts.joinToString(".")
}

fun convertMaskToBinary(mask: String): String {
    val maskParts = mask.split(".")
    val binaryParts = maskParts.map { Integer.toBinaryString(it.toInt()).padStart(8, '0') }
    return binaryParts.joinToString(separator = ".")
}

fun calculateMaxSubnets(mask: String): Int {
    val binmask = convertMaskToBinary(mask)
    val hostBits = binmask.count { it == '0' }
    val maxSubnets = 2.toDouble().pow(hostBits - 2)
    return maxSubnets.toInt()
}
fun validateTextValue(value: String): Boolean {
    val ipAddressPattern = Regex("""^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$""")

    val subnetMaskPattern = Regex("""^(\\/([0-9]|[12]\\d|3[0-2]))$""")

    return ipAddressPattern.matches(value) || subnetMaskPattern.matches(value)
}