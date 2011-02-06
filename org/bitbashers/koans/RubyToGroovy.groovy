rubyKoansDir = "/Users/esumerfd/Edward/Projects/BitBashers/Ruby/ruby_koans"

aboutFile = args[0]

new File(rubyKoansDir).eachFileMatch(~/${aboutFile}\.rb/) { ruby ->

    System.err.println "Converting Ruby Koan ${ruby}"
    System.err.println "NOT A COMPLETE CONVERSION. Follow the following steps to complete the job manually"
    System.err.println "- Anything to do with block_given?, yield or block arguments"

    def context = [:]
    context["initialized"] = []

    ruby.eachLine { rubyLine ->

        def variables = [
            "empty_array", "array", "shifted_value", "expected_value", "actual_value",
            "result", "yielded_result", "value", "add_one", "make_upper"
        ]

        def match = [:]
        match << [/#.*env ruby/:         { line, regex -> "" } ]
        match << [/#.*ruby/:             { line, regex -> "" } ]
        match << [/#(.*)$/:              { line, regex -> line.replaceAll(regex, '//$1') } ]
        match << [/edgecase/:            { line, regex -> "import org.bitbashers.koans.*" } ]

        // CLOSURES WORK DIFFERENTY. LAMBDA IS UNNECESSARY, AMPERSAND IS DIFFERNT AND yeild IS NOT SUPPORTED
        match << [/ do /:                { line, regex -> line.replaceAll(regex, " { ") } ]
        match << [/\|([^|]+)\|/:         { line, regex -> line.replaceAll(regex, '$1 ->') } ]
        match << [/^end| end.*$/:        { line, regex -> line.replaceAll(regex, ' }') } ]
        match << [/lambda */:            { line, regex -> line.replaceAll(regex, "") } ]
        match << [/&([a-zA-Z_])/:        { line, regex -> line.replaceAll(regex, '$1') } ]
        match << [/ yield /:             { line, regex -> line.replaceAll(regex, ' closure() ') } ]
        match << [/ yield$/:             { line, regex -> line.replaceAll(regex, ' closure()') } ]
        match << [/yield\(/:             { line, regex -> line.replaceAll(regex, ' closure(') } ]
        match << [/block_given\?/:       { line, regex -> line.replaceAll(regex, 'closure') } ]

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

        // IF ELSE END BLOCKS
        match << [/if (.*)/:             { line, regex -> line.replaceAll(regex, 'if ($1) {') } ]
        match << [/ elsif (.*)/:              { line, regex -> line.replaceAll(regex, ' } else if ($1) {') } ]
        match << [/ else/:               { line, regex -> line.replaceAll(regex, ' } else {') } ]

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
        match << [/ assert_equal *(.*) */:{ line, regex -> line.replaceAll(regex, ' assertEquals( $1 )') } ]
        match << [/ assert_not_equal *(.*) */:{ line, regex -> line.replaceAll(regex, ' assertNotEquals( $1 )') } ]

        // CONVERT UNSUPPORTED CLASS
        match << [/Array.new/:           { line, regex -> line.replaceAll(regex, '[]') } ]

        // USE GROOVY NEW INSTANCE SYNTAX
        match << [/\.new/:               { line, regex -> line.replaceAll(/ ([^ .]+)\.new/, ' new $1()') } ]

        // CHECK FOR VARIABLES AND INITIALIZE THEM IF NECESSARY
        match << [/^.*$/:                { line, regex -> 
            def name = variables.find { name ->
                def foundName = null
                if (line =~ /^(\s+)(${name} )/ && !context["initialized"].contains(name)) {
                    foundName = name
                }
                foundName
            }
            if (name) {
                context["initialized"] << name
                line = line.replaceAll(/^(\s+)(${name} )/, '$1def $2 ')
            }
            line
          } 
        ]

        // CONVERT SYMBOLS TO STRINGS
        match << [/:([a-zA-Z_]+)/: { line, regex -> line.replaceAll(regex, '"$1"') } ]

        // NOTE SUPPORTED BY GROOVY
        match << [/\.\.\./: { line, regex -> "//$line" } ]

        // SPECIAL CASE TRANSLATIONS SPECIFIC TO RUBY_KOANS
        match << [/(many_yields\()/:                 { line, regex -> line.replaceAll(regex, '$1closure') } ]
        match << [/(method_with_block\()/:           { line, regex -> line.replaceAll(regex, '$1closure') } ]
        match << [/(void method_with_block_arguments\()/: { line, regex -> line.replaceAll(regex, '$1closure') } ]
        match << [/(method_with_explicit_block)\(block\)\(/: { line, regex -> line.replaceAll(regex, '$1(block') } ]

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
                System.err.println "ERROR: '${e.message}': ${pattern} for line ${groovyLine}"
            }
        }

        println groovyLine
    }
}
