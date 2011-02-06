package org.bitbashers.koans

println "Path to Enlightment"

new File(".").eachFileMatch(~/.*Koans.groovy/) { koan ->
    println "Running Koan ${koan}"
    println "groovy ${koan}".execute().text
}
