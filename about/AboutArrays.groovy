import org.bitbashers.koans.*

class AboutArrays extends Koans {
  void test_creating_arrays() {
    def empty_array = []
    assertEquals( ArrayList, empty_array.class )
    assertEquals( 0, empty_array.size )
  }

  void test_array_literals() {
    def array = []
    assertEquals( [], array )

    array[0] = 1
    assertEquals( [1], array )

    array[1] = 2
    assertEquals( [1, 2], array )

    array << 333
    assertEquals( [1,2,333], array )
  }

  void test_accessing_array_elements() {
    def array = ["peanut", "butter", "and", "jelly"]

    assertEquals( "peanut", array[0] )
    assertEquals( "peanut", array.first() )
    assertEquals( "jelly", array[3] )
    assertEquals( "jelly", array.last())
    assertEquals( "jelly", array[-1] )
    assertEquals( "butter", array[-3] )
  }

  void test_slicing_arrays() {
    def array = ["peanut", "butter", "and", "jelly"]

    assertEquals( ["peanut","butter"], array[0,1] )
    assertEquals( ["peanut","and"], array[0,2] )
    assertEquals( ["and","and"], array[2,2] )
    assertEquals( ["and",null], array[2,20] )
    assertEquals( [null,"peanut"], array[4,0] )
    assertEquals( [null,null], array[4,100] )
    assertEquals( [null, "peanut"], array[5,0] )
  }

  void test_arrays_and_ranges() {
    assertEquals( IntRange, (1..5).class )
    assertEquals( [1,2,3,4,5], (1..5) )
//    assertEquals( __, (1..5).to_a )
    assertEquals( [1,2,3,4], (1..<5) )
  }

  void test_slicing_with_ranges() {
    def array = ["peanut", "butter", "and", "jelly"]

    assertEquals( ["peanut", "butter", "and"], array[0..2] )
    assertEquals( ["peanut", "butter"], array[0..<2] )
    assertEquals( ["and", "jelly"], array[2..-1] )
  }

  void test_pushing_and_popping_arrays() {
    def array = [1,2]
    array.push("last")

    assertEquals( [1,2,"last"], array )

    def popped_value = array.pop()
    assertEquals( "last", popped_value )
    assertEquals( [1,2], array )
  }

//   void test_shifting_arrays() {
//     def array = [1,2]
//     array.unshift("first")

//     assertEquals( __, array )

//     def shifted_value = array.shift
//     assertEquals( __, shifted_value )
//     assertEquals( __, array )
//   }

}
