const val messageEn = "hello there my sexy motherfucker this is the secret code that no one will ever know because yes amazing and you are cute btw not you reading just my bubba is cute and maybe the kitty too." //thank you S <3
var messageDe = "" // Set by the code. If you want to just decrypt, put the encrypted message here and change the settings below

// Encryption / Decryption settings
const val maxColumns = 6
const val rounds = 9
const val rowShift = 6
const val columnShift = 9

fun main() {
    encrypt()
    decrypt()
}

fun getTable(message: String): HashMap<Int, MutableList<Char>> {
    val table = HashMap<Int, MutableList<Char>>()

    // Put characters into the table
    var curr = 1
    for (char in message) {
        if (table[curr]?.size == maxColumns) curr++
        if (table[curr] == null) table[curr] = mutableListOf()

        if (char == ' ') table[curr]?.add('*')
        else table[curr]?.add(char)
    }

    // Make sure all is the correct length
    for (entry in table) {
        while (entry.value.size != maxColumns) {
            entry.value.add('*')
        }
    }

    return table
}

fun encrypt() {
    var table = getTable(messageEn)

    println("Performing an AES on \"$messageEn\"")
    for (entry in table) {
        println(entry.value)
    }
    println("\n")

    // Rounds
    for (r in 1..rounds) {
        println("\nGive it up for round $r!")

        // Shift rows
        println("Shifting the rows down $rowShift time(s)!")
        for (i in 1..rowShift) {
            val totalRows = table.size;

            val newTable = HashMap<Int, MutableList<Char>>();
            for (entry in table) {
                var row = entry.key
                row++
                if (row > totalRows)
                    row = 1

                newTable[row] = entry.value
            }

            table = newTable
        }

        for (entry in table) {
            println(entry.value)
        }

        // Shift columns
        println("\nShifting the columns across $columnShift time(s)!")
        for (i in 1..columnShift) {
            val newTable = HashMap<Int, MutableList<Char>>()

            for (entry in table) {

                // Figure out the new layout
                val layout = HashMap<Int, Char>()
                for (c in 0 until entry.value.size) {
                    var column = c;
                    column++;
                    if (column >= maxColumns)
                        column = 0;

                    layout[column] = entry.value[c]
                }

                // Reinsert the new layout into the table
                newTable[entry.key] = mutableListOf()
                for (c in 0..maxColumns) {
                    layout[c]?.let { newTable[entry.key]?.add(it) }
                }
            }

            table = newTable
        }

        for (entry in table) {
            println(entry.value)
        }

    }

    // Put the table back into a string
    var encrypted = ""
    for (entry in table) {
        for (char in entry.value) {
            encrypted += if (char == '*') ' '
            else char
        }
    }

    println("\n\n\nDone!")
    println("The new message is \"$encrypted\"")
    messageDe = encrypted
    for (entry in table) {
        println(entry.value)
    }
}

fun decrypt() {
    var table = getTable(messageDe)

    println("Performing an decryption on \"$messageDe\"")
    for (entry in table) {
        println(entry.value)
    }
    println("\n")

    // Rounds
    for (r in 1..rounds) {
        println("\nGive it up for round $r!")

        // Shift columns
        println("\nShifting the columns back $columnShift time(s)!")
        for (i in 1..columnShift) {
            val newTable = HashMap<Int, MutableList<Char>>()

            for (entry in table) {

                // Figure out the new layout
                val layout = HashMap<Int, Char>()
                for (c in 0 until entry.value.size) {
                    var column = c;
                    column--;
                    if (column < 0)
                        column = maxColumns;

                    layout[column] = entry.value[c]
                }

                // Reinsert the new layout into the table
                newTable[entry.key] = mutableListOf()
                for (c in 0..maxColumns) {
                    layout[c]?.let { newTable[entry.key]?.add(it) }
                }
            }

            table = newTable
        }

        for (entry in table) {
            println(entry.value)
        }

        // Shift rows
        println("Shifting the rows up $rowShift time(s)!")
        for (i in 1..rowShift) {
            val totalRows = table.size;

            val newTable = HashMap<Int, MutableList<Char>>();
            for (entry in table) {
                var row = entry.key
                row--
                if (row <= 0)
                    row = totalRows

                newTable[row] = entry.value
            }

            table = newTable
        }

        for (entry in table) {
            println(entry.value)
        }

    }

    // Put the table back into a string
    var decrypted = ""
    for (entry in table) {
        for (char in entry.value) {
            decrypted += if (char == '*') ' '
            else char
        }
    }

    println("\n\n\nDone!")
    println("The decrypted message is \"${decrypted.trim()}\"")
    for (entry in table) {
        println(entry.value)
    }
}