import static io.netty.util.internal.ObjectUtil.checkPositiveOrZero;

/**
 * @author Peiel
 * @version V1.0
 * @date 2019-04-17
 */
public class ByteBufTest {

    static final int CALCULATE_THRESHOLD = 1048576 * 4; // 4 MiB page

    public static void main(String[] args) {

        int minNewCapacity = 9194304;


        final int threshold = CALCULATE_THRESHOLD; // 4 MiB page

        System.out.println(minNewCapacity);
        System.out.println(threshold);
        int newCapacity = minNewCapacity / threshold * threshold;

        System.out.println(newCapacity);

    }

    public int calculateNewCapacity(int minNewCapacity, int maxCapacity) {
        checkPositiveOrZero(minNewCapacity, "minNewCapacity");
        if (minNewCapacity > maxCapacity) {
            throw new IllegalArgumentException(String.format(
                    "minNewCapacity: %d (expected: not greater than maxCapacity(%d)",
                    minNewCapacity, maxCapacity));
        }
        final int threshold = CALCULATE_THRESHOLD; // 4 MiB page

        if (minNewCapacity == threshold) {
            return threshold;
        }

        // If over threshold, do not double but just increase by threshold.
        /* 改写成三元表达式更好理解
        if (minNewCapacity > threshold) {
            int newCapacity = minNewCapacity / threshold * threshold;
            if (newCapacity > maxCapacity - threshold) {
                newCapacity = maxCapacity;
            } else {
                newCapacity += threshold;
            }
            return newCapacity;
        }
        */

        // 改写方式
        if (minNewCapacity > threshold) {
            int newCapacity = minNewCapacity / threshold * threshold;
            return newCapacity > maxCapacity - threshold ? maxCapacity : newCapacity + threshold;
        }


        // Not over threshold. Double up to 4 MiB, starting from 64.
        int newCapacity = 64;
        while (newCapacity < minNewCapacity) {
            newCapacity <<= 1;
        }

        return Math.min(newCapacity, maxCapacity);
    }



}
