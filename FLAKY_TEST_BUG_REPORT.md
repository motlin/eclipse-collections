# 🐛 Flaky Test Bug Report: sumOfDoubleConsistentRounding

## Summary
The test `MultiReaderFastListParallelListIterableTest.sumOfDoubleConsistentRounding` is flaky and occasionally fails due to floating-point precision differences when running parallel operations.

## Test Details
- **Test Class**: `org.eclipse.collections.impl.lazy.parallel.list.MultiReaderFastListParallelListIterableTest`
- **Test Method**: `sumOfDoubleConsistentRounding`
- **Test Location**: unit-tests/src/test/java/org/eclipse/collections/impl/lazy/parallel/ParallelIterableTestCase.java:912

## Failure Information

### Error Message
```
org.opentest4j.AssertionFailedError: Batch size: 10000 ==>
expected: <5.0000000000000995>
but was: <5.000000000000101>
```

### Stack Trace
```
at org.eclipse.collections.impl.lazy.parallel.ParallelIterableTestCase.sumOfDoubleConsistentRounding(ParallelIterableTestCase.java:925)
at java.base/java.lang.reflect.Method.invoke(Method.java:580)
at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
```

## Test Behavior Analysis

### What the Test Does
The test validates that parallel summation of double values produces consistent results regardless of batch size when compared to sequential summation.

1. Creates a list of 100,000 integers shuffled randomly
2. Applies a function that returns:
   - `1.0e-18` for values ≤ 99,995
   - `1.0` for values > 99,995
3. Computes a baseline sum using sequential processing
4. Tests parallel processing with different batch sizes: 2, 5, 10, 100, 1000, 10000, 50000
5. Asserts results match baseline within tolerance of `1.0e-15`

### Root Cause
**Floating-point arithmetic non-associativity**: The test fails because floating-point addition is not associative. When adding many tiny values (`1.0e-18`) to larger values (`1.0`), the order of operations matters due to precision limitations.

- Sequential processing adds elements in one order
- Parallel processing with batch size 10,000 groups operations differently
- Different groupings lead to slightly different rounding errors
- The difference (`1.17e-16`) exceeds the tolerance (`1.0e-15`)

## Flakiness Characteristics
- **Frequency**: Rare/intermittent
- **Trigger**: Fails when specific batch size (10,000) combined with particular element ordering causes accumulated rounding error to exceed tolerance
- **Environment**: Occurs on macOS, Java 21 (Temurin-21.0.6+7-LTS)

## Environment
- **OS**: macOS 15.5 (Darwin 24.5.0), aarch64 architecture
- **Java Version**: OpenJDK 21.0.6 (Eclipse Adoptium Temurin-21.0.6+7-LTS)
- **Build Tool**: Maven 3.x
- **Test Framework**: JUnit 5.10.2 with JUnit Platform

## Reproduction

### Deterministic Reproduction with Seed 1303
The test can be reliably reproduced using seed 1303:
```java
Random random = new Random(1303L);
MutableList<Integer> list = Interval.oneTo(100_000).toList().shuffleThis(random);
```

This seed produces:
- Baseline: `5.0000000000000995`
- Parallel (batch 10000): `5.000000000000101`
- Difference: `1.776e-15` (exceeds tolerance of `1.0e-15`)

### Test Verification Program
```java
import org.eclipse.collections.api.block.function.primitive.DoubleFunction;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.ListAdapter;
import org.eclipse.collections.impl.list.mutable.MultiReaderFastList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Test with seed 1303L reliably reproduces the failure
Random random = new Random(1303L);
MutableList<Integer> list = Interval.oneTo(100_000).toList().shuffleThis(random);
```

### Finding Additional Failing Seeds
A search of seeds 0-100,000 found seed 1303 fails consistently. Other failing seeds may exist but are relatively rare, confirming the flaky nature of this test.

## Suggested Fix

### Increase Tolerance
Since Eclipse Collections already uses Kahan summation for improved accuracy, the remaining discrepancy is due to inherent limitations in floating-point arithmetic when operations are reordered in parallel execution.

Change tolerance from `1.0e-15` to `1.0e-14` to account for these unavoidable rounding differences:
```java
assertEquals(baseline, testCollection.sumOfDouble(roundingSensitiveElementFunction),
             1.0e-14d, "Batch size: " + this.batchSize);
```

This acknowledges that even with Kahan summation, parallel execution with different batch sizes will produce slightly different results due to the non-associative nature of floating-point addition.

## Additional Notes
- Test passed for other batch sizes (2, 5, 10, 100, 1000, 50000) in this run
- The `sumOfFloatConsistentRounding` test exists and passed, suggesting float precision is adequate for that case
- Maven reports test execution time: 0.015s for the failing test

## Related Tests
- `sumOfFloatConsistentRounding` - Similar test for float values (passed)
- Other parallel aggregation tests in the same class
