rubyKoansDir = "/Users/esumerfd/Edward/Projects/BitBashers/Ruby/ruby_koans"

new File(rubyKoansDir).eachFileMatch(~/about_arrays\.rb/) { ruby ->

    System.err.println "Converting Ruby Koan ${ruby}"
    System.err.println "NOT A COMPLETE CONVERSION. Follow the following steps to complete the job manually"

    def context = [:]
    context["initialized"] = []

    ruby.eachLine { rubyLine ->

        def variables = ["empty_array", "array"]

        def match = [:]
        match << [/#.*env ruby/:         { line, regex -> } ]
        match << [/#.*ruby/:             { line, regex -> } ]
        match << [/edgecase/:            { line, regex -> "import org.bitbashers.koans.*" } ]
        match << [/^.*end.*$/:           { line, regex -> line.replaceAll("end", "}") } ]

        // FIX CLASS DECLARATION
        match << [/^class /:             { line, regex -> line.replace(" < EdgeCase::Koan", " extends Koans {") } ]

        // CORRECT METHOD SIGNATURE AND START BRACE COUNTING
        match << [/def (.*)/:            { line, regex -> 
            context["start_of_method"] = true
            context["open_brace"] = 0
            context["initialized"] = []
            line.replaceAll(regex, 'void $1() {')
          }
        ]

        // CONVERT TEST UNIT ASSERT NAMES
        match << [/assert_equal *(.*) */:{ line, regex -> line.replaceAll(regex, 'assertEquals( $1 )') } ]
        match << [/assert_not_equal *(.*) */:{ line, regex -> line.replaceAll(regex, 'assertNotEquals( $1 )') } ]

        // CONVERT UNSUPPORTED CLASS
        match << [/Array.new/:           { line, regex -> line.replaceAll(regex, '[]') } ]

        // USE GROOVY NEW INSTANCE SYNTAX
        match << [/\.new/:               { line, regex -> line.replaceAll(/ ([^ .]+)\.new/, ' new $1()') } ]

        // CHECK FOR VARIABLES AND INITIALIZE THEM IF NECESSARY
        match << [/^.*$/:                { line, regex -> 
            variables.each { name ->

                if (line.contains(name) && !context["initialized"].contains(name)) {
                    context["initialized"] << name
                    line = line.replaceAll(" (${name}) ", ' def $1 ')
                }
            }
            line
          } 
        ]

        // COUNT OPEN BRACES
        match << ['\\{': { line, regex -> 
            if (context["start_of_method"]) {
                context["open_brace"]++
            }
            line
          }
        ]

        // COUNT CLOSE BRACES
        match << ['\\}': { line, regex -> 
            context["open_brace"]--
            if (context["open_brace"] == 0) {
                context["start_of_method"] = false
            }
            line
          }
        ]

        // CONVERT SYMBOLS TO STRINGS
        match << [/:([a-zA-Z_]+)/: { line, regex -> line.replaceAll(regex, '"$1"') } ]

        // NOTE SUPPORTED BY GROOVY
        match << [/\.\.\./: { line, regex -> "//$line" } ]

        // match << [//: { line, regex -> }]

        def groovyLine = rubyLine
        System.err.println("## ${groovyLine}")
        match.each { pattern, translate ->

            try {
                if (groovyLine =~ pattern) {
                    groovyLine = translate(groovyLine, pattern)
                    System.err.println("#### TRANSLATED ${pattern}  >>>  ${groovyLine}")

                    System.err.println("#### CONTEXT ${context}")
                }
            }
            catch (java.util.regex.PatternSyntaxException e) {
                println "ERROR: ${pattern} for line ${groovyLine}"
            }
        }

        println groovyLine
    }
}
