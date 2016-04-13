package co.there4.hexagon.util

import org.testng.annotations.Test
import java.time.LocalDateTime
import java.util.*

@Test class UtilPackageTest {
    fun time_nanos_gets_the_elapsed_nanoseconds () {
        val millisDelay = 45L
        val millisThreshold = 5L

        resetTimes()
        pushTime()
        Thread.sleep (millisDelay)
        val nanos = popTime()
        pushTime()
        val timeNanos = formatTime(nanos)

        val ceil = (millisDelay + millisThreshold) * 1e6
        val floor = millisDelay * 1e6
        assert (nanos > floor && nanos < ceil)
        assert (timeNanos.endsWith("ms") && timeNanos.contains("."))
    }

    fun a_local_date_time_returns_a_valid_int_timestamp () {
        assert (LocalDateTime.of (2015, 12, 31, 23, 59, 59).asInt() == 20151231235959)
    }

    fun filtering_an_exception_with_an_empty_string_do_not_change_the_stack () {
        val t = RuntimeException ()
        assert (Arrays.equals (t.stackTrace, t.filterStackTrace ("")))
    }

    fun filtering_an_exception_with_a_package_only_returns_frames_of_that_package () {
        val t = RuntimeException ()
        t.filterStackTrace ("co.there4").forEach {
            assert (it.className.startsWith ("co.there4"))
        }
    }

    fun printing_an_exception_returns_its_stack_trace_in_the_string () {
        val e = RuntimeException ("Runtime error", IllegalStateException ("invalid state"))
        val trace = e.toText ()
        assert (trace.startsWith ("java.lang.RuntimeException"))
        assert (trace.contains ("\tat ${UtilPackageTest::class.java.name}"))
    }
}
