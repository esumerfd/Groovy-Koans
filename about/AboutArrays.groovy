import org.bitbashers.koans.*

class AboutArrays extends Koans {
  void test_creating_arrays() {
    def empty_array = []
    assertEquals( __, empty_array.class )
    assertEquals( __, empty_array.size )
  }

  void test_array_literals() {
    def array = []
    assertEquals( [], array )

    array[0] = 1
    assertEquals( [1], array )

    array[1] = 2
    assertEquals( [1, __], array )

    array << 333
    assertEquals( __, array )
  }

  void test_accessing_array_elements() {
    def array = ["peanut", "butter", "and", "jelly"]

    assertEquals( __, array[0] )
    assertEquals( __, array.first )
    assertEquals( __, array[3] )
    assertEquals( __, array.last )
    assertEquals( __, array[-1] )
    assertEquals( __, array[-3] )
  }

  void test_slicing_arrays() {
    def array = ["peanut", "butter", "and", "jelly"]

    assertEquals( __, array[0,1] )
    assertEquals( __, array[0,2] )
    assertEquals( __, array[2,2] )
    assertEquals( __, array[2,20] )
    assertEquals( __, array[4,0] )
    assertEquals( __, array[4,100] )
    assertEquals( __, array[5,0] )
  }

  void test_arrays_and_ranges() {
    assertEquals( __, (1..5).class )
    assertNotEquals( [1,2,3,4,5], (1..5) )
    assertEquals( __, (1..5).to_a )
//    assertEquals( __, (1...5).to_a )
  }

  void test_slicing_with_ranges() {
    def array = ["peanut", "butter", "and", "jelly"]

    assertEquals( __, array[0..2] )
//    assertEquals( __, array[0...2] )
    assertEquals( __, array[2..-1] )
  }

  void test_pushing_and_popping_arrays() {
    def array = [1,2]
    array.push("last")

    assertEquals( __, array )

    popped_value = array.pop
    assertEquals( __, popped_value )
    assertEquals( __, array )
  }

  void test_shifting_arrays() {
    def array = [1,2]
    array.unshift("first")

    assertEquals( __, array )

    def shifted_value = array.shift
    assertEquals( __, shifted_value )
    assertEquals( __, array )
  }

}
