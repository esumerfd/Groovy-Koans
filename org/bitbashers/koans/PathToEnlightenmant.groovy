package org.bitbashers.koans

println "Path to Enlightment"

new File(".").eachFileMatch(~/.*Koans.groovy/) { koan ->
    println "Running Koan ${koan}"
    def process = "groovy ${koan}".execute()
    def output = process.text
    println output
    if (output.size() > 1024) {
        throw new RuntimeException("Try again")
    }
}
