package benchmark

/**
 * Interface for a general benchmark.
 */
interface Benchmark {
    /**
     * Method responsible for writing the test result in a given location.
     */
    fun write(filename: String?)
}
