import org.bitbashers.koans.*

class AboutAsserts extends Koans {

  // We shall contemplate truth by testing reality, via asserts.
  void test_assert_truth() {
    assert true                // This should be true
  }

  // Enlightenment may be more easily achieved with appropriate
  // messages.
  void test_assert_with_message() {
    assert true, "This should be true -- Please fix this"
  }

  // To understand reality, we must compare our expectations against
  // reality.
  void test_assert_equality() {
    def expected_value = 2
    def actual_value = 1 + 1

    assert expected_value == actual_value
  }

  // Some ways of asserting equality are better than others.
  void test_a_better_way_of_asserting_equality() {
    def expected_value = 2
    def actual_value = 1 + 1

    assertEquals( expected_value, actual_value )
  }

  // Sometimes we will ask you to fill in the values
  void test_fill_in_values() {
    assertEquals( 2, 1 + 1 )
  }
}
