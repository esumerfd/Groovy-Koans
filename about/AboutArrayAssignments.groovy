import org.bitbashers.koans.*

class ArrayAssignmentKoans extends Koans {

  void test_non_parallel_assignment() {
    def names = ["John", "Smith"]
    assertEquals __, names
  }

  void test_parallel_assignments() {
    def (first_name, last_name) = ["John", "Smith"]
    assertEquals __, first_name
    assertEquals __, last_name
  }

  void test_parallel_assignments_with_extra_values() {
    def (first_name, last_name) = ["John", "Smith", "III"]
    assertEquals __, first_name
    assertEquals __, last_name
  }

//   void test_parallel_assignments_with_splat_operator() {
//     def first_name, *last_name = ["John", "Smith", "III"]
//     assertEquals __, first_name
//     assertEquals __, last_name
//   }

  void test_parallel_assignments_with_too_few_variables() {
    def (first_name, last_name) = ["Cher"]
    assertEquals __, first_name
    assertEquals __, last_name
  }

  void test_parallel_assignments_with_subarrays() {
    def (first_name, last_name) = [["Willie", "Rae"], "Johnson"]
    assertEquals __, first_name
    assertEquals __, last_name
  }

//   void test_parallel_assignment_with_one_variable() {
//     def first_name, = ["John", "Smith"]
//     assertEquals __, first_name
//   }

  void test_swapping_with_parallel_assignment() {
    def first_name = "Roy"
    def last_name = "Rob"
    (first_name, last_name) = [last_name, first_name]
    assertEquals __, first_name
    assertEquals __, last_name
  }
}
