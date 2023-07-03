package pl.tofix.ipcalc2

import java.net.InetAddress
import java.net.NetworkInterface
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

fun ipcheck(): Triple<String?, String?, String>? {
    try {
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()

        while (networkInterfaces.hasMoreElements()) {
            val networkInterface = networkInterfaces.nextElement()
            println("Nazwa interfejsu: ${networkInterface.displayName}")

            if (networkInterface.isUp) {
                val addresses = networkInterface.interfaceAddresses

                for (address in addresses) {
                    val inetAddress = address.address

                    if (!inetAddress.isLoopbackAddress && inetAddress is InetAddress && inetAddress.hostAddress.indexOf(
                            ':'
                        ) == -1
                    ) {
                        val ipAddress = inetAddress.hostAddress
                        val subnetMask = getSubnetMask(address.networkPrefixLength)
                        return Triple(ipAddress, subnetMask, networkInterface.displayName)
                    }
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}

fun getSubnetMask(networkPrefixLength: Short): String {
    val maskBits = 0xffffffff shl 32 - networkPrefixLength
    val octets = mutableListOf<String>()

    octets.add((maskBits shr 24 and 0xff).toString())
    octets.add((maskBits shr 16 and 0xff).toString())
    octets.add((maskBits shr 8 and 0xff).toString())
    octets.add((maskBits and 0xff).toString())

    return octets.joinToString(".")
}

fun isValidIpAddress(ip: String): Boolean {
    fun inRange(n: Int): Boolean {
        return n in 0..255
    }

    fun hasLeadingZero(n: String): Boolean {
        return n.length > 1 && n[0] == '0'
    }

    val parts = ip.split(".")
    if (parts.size != 4) {
        return false
    }

    for (part in parts) {
        if (hasLeadingZero(part) || part.isEmpty()) {
            return false
        }

        try {
            val num = part.toInt()
            if (!inRange(num)) {
                return false
            }
        } catch (e: NumberFormatException) {
            return false
        }
    }

    return true
}

fun isValidNetmaskAddress(mask: String): Boolean {
    val parts = mask.split(".")
    if (parts.size != 4) {
        return false
    }

    val binary = StringBuilder()

    for (part in parts) {
        try {
            val num = part.toInt()
            if (num < 0 || num > 255) {
                return false
            }

            binary.append(Integer.toBinaryString(num).padStart(8, '0'))
        } catch (e: NumberFormatException) {
            return false
        }
    }

    val binaryString = binary.toString()
    if (binaryString.contains("0")) {
        val index = binaryString.indexOfFirst { it == '0' }
        if (binaryString.substring(index).contains("1")) {
            return false
        }
    }
    return true
}

