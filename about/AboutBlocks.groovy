import org.bitbashers.koans.*

class AboutBlocks extends Koans {
  def method_with_block(closure) {
    def result  = closure()
    result
  }

  void test_methods_can_take_blocks() {
    def yielded_result  = method_with_block { 1 + 2 }
    assertEquals( __, yielded_result )
  }

  void test_blocks_can_be_defined_with_do_end_too() {
    def yielded_result  = method_with_block { 1 + 2 }
    assertEquals( __, yielded_result )
  }

  // ------------------------------------------------------------------

  def method_with_block_arguments(closure) {
     closure("Jim")
  }

  void test_blocks_can_take_arguments() {
    def result  = method_with_block_arguments { argument ->
      assertEquals( __, argument )
    }
  }

  // ------------------------------------------------------------------

  void many_yields(closure) {
     closure("peanut")
     closure("butter")
     closure("and")
     closure("jelly")
  }

  void test_methods_can_call_yield_many_times() {
    def result  = []
    many_yields { item -> result << item }
    assertEquals( __, result )
  }

  // ------------------------------------------------------------------

  def yield_tester(closure) {
    if (closure) {
      closure()
    } else {
      "no_block"
    }
  }

  void test_methods_can_see_if_they_have_been_called_with_a_block() {
    assertEquals( __, yield_tester { "with_block" } )
    assertEquals( __, yield_tester(null) )
  }

  // ------------------------------------------------------------------

  void test_block_can_affect_variables_in_the_code_where_they_are_created() {
    def value  = "initial_value"
    method_with_block { value = "modified_in_a_block" }
    assertEquals( __, value )
  }

  void test_blocks_can_be_assigned_to_variables_and_called_explicitly() {
    def add_one  = { n -> n + 1 }
    assertEquals( __, add_one.call(10) )

    // Alternative calling sequence
    assertEquals( __, add_one(10) )
  }

  void test_stand_alone_blocks_can_be_passed_to_methods_expecting_blocks() {
    def make_upper  = { n -> n.toUpperCase() }
    def result  = method_with_block_arguments(make_upper)
    assertEquals( __, result )
  }

  // ------------------------------------------------------------------

  void method_with_explicit_block(block) {
    block.call(10)
  }

  void test_methods_can_take_an_explicit_block_argument() {
    assertEquals( __, method_with_explicit_block { n -> n * 2 } )

    def add_one  = { n -> n + 1 }
    assertEquals( __, method_with_explicit_block(add_one) )
  }
 }
