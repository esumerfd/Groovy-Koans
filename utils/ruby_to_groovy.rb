require "rubygems"
require "parse_tree"

# Prevent the Ruby Koans auto-starting on require.
class Prepare
  
  def initialize
    @ruby_dir="../../Ruby/ruby_koans"
    @dir = "#{File.dirname(__FILE__)}/cache"
  end
  
  def filter(filename)
    cache_file = File.new("#{@dir}/#{filename}", "w")
    File.open("#{@ruby_dir}/#{filename}") do |file| 
      file.each_line do |line|
        changed = yield(line, cache_file)
        cache_file.write(line) if (!changed)
      end
    end
    cache_file.close
  end

  def go
    filter("edgecase.rb") do |line, cache_file|
      line =~ /EdgeCase::ThePath/
    end
    
    filter("about_arrays.rb") do  |line, cache_file|
      changd = false
      if (line =~ /require/)
        cache_file.write("require '#{@dir}/edgecase'")
        changed = true
      end
      changed
    end
  end

  def load(filename)
    require "#{@dir}/#{filename}"
  end

  def convert(clazz)

    sexp = ParseTree.translate(AboutArrays)

    traverse(sexp, 0)
  end

  def traverse(root, depth)
    
    if (root.is_a?(Array))
      root.each do |node|
        traverse(node, 2 + depth)
      end
    else
      STDOUT.puts "#{" " * depth}#{root.class} : #{root}"
    end
  end
end

p = Prepare.new
p.go
p.load("about_arrays.rb")

p.convert(AboutArrays)
