import com.github.superzer0.BarcodesEntropy;
import edu.stanford.math.plex4.homology.barcodes.Interval;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EntropyComputingTests {

    @Test
    public void simpleTest1() {

        List<Interval<Double>> input = new ArrayList<>();
        input.add(Interval.makeFiniteClosedInterval(0d, 20d));
        input.add(Interval.makeFiniteClosedInterval(0d, 10d));
        input.add(Interval.makeRightInfiniteClosedInterval(0d));

        assertEquals(0.90, BarcodesEntropy.computeDiagramEntropy(50, input), 0.01);
    }

    @Test
    public void simpleTest2() {

        List<Interval<Double>> input = new ArrayList<>();
        input.add(Interval.makeFiniteClosedInterval(1d, 3d));
        input.add(Interval.makeFiniteClosedInterval(2d, 10d));
        input.add(Interval.makeFiniteClosedInterval(4d, 19d));

        assertEquals(0.87, BarcodesEntropy.computeDiagramEntropy(50, input), 0.01);
    }

    @Test
    public void simpleTest3() {

        List<Interval<Double>> input = new ArrayList<>();
        input.add(Interval.makeFiniteClosedInterval(1d, 3d));
        input.add(Interval.makeFiniteClosedInterval(1d, 3d));
        input.add(Interval.makeFiniteClosedInterval(1d, 4d));

        assertEquals(1.07, BarcodesEntropy.computeDiagramEntropy(50, input), 0.01);
    }
}



