/*
 *     Copyright 2020 Stefán Freyr Stefánsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stfs.yummybytes;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class UTestByteSize {
    @Test
    public void testBasics() {
        assertEquals(1, new ByteSize(1, ByteSizeUnit.BYTES).getBytesAsLong());

        BigInteger siBytes = BigInteger.valueOf(1000);
        BigInteger iecBytes = BigInteger.valueOf(1024);

        for (ByteSizeUnit bsu : ByteSizeUnit.values()) {
            if (bsu == ByteSizeUnit.BYTES) {
                assertEquals(1, new ByteSize(1, bsu).getBytesAsLong());
            } else if (bsu.isIEC()) {
                assertEquals(iecBytes, new ByteSize(1, bsu).getBytes());
                iecBytes = iecBytes.multiply(BigInteger.valueOf(1024));
            } else if (bsu.isSI()) {
                assertEquals(siBytes, new ByteSize(1, bsu).getBytes());
                siBytes = siBytes.multiply(BigInteger.valueOf(1000));
            }
        }
    }

    @Test
    public void testSameSizeDifferentUnits() {
        assertEquals(new ByteSize(0.2, ByteSizeUnit.GIGABYTES), new ByteSize(200, ByteSizeUnit.MEGABYTES));
        assertEquals(new ByteSize(800, ByteSizeUnit.KILOBYTES), new ByteSize(0.8, ByteSizeUnit.MEGABYTES));
        assertEquals(new ByteSize(80, ByteSizeUnit.BYTES), new ByteSize(0.08, ByteSizeUnit.KILOBYTES));

        assertEquals(new ByteSize(80*1024, ByteSizeUnit.KIBIBYTES), new ByteSize(80, ByteSizeUnit.MEBIBYTES));
        assertEquals(new ByteSize(80, ByteSizeUnit.KIBIBYTES), new ByteSize(80/1024.0, ByteSizeUnit.MEBIBYTES));
    }

    @Test
    public void testSameSizeSameUnitsNotConverted() {
        int[] integers = {2, 42, 666};
        double[] floats = {0.1, 42.42, 1000.0};

        for (int i : integers) {
            for (ByteSizeUnit bsu : ByteSizeUnit.values()) {
                assertEquals(new ByteSize(i, bsu), new ByteSize(i, bsu));
            }
        }

        for (double d : floats) {
            for (ByteSizeUnit bsu : ByteSizeUnit.values()) {
                assertEquals(new ByteSize(d, bsu), new ByteSize(d, bsu));
            }
        }
    }

    @Test
    public void testConversion() {
        assertEquals(new ByteSize(0.5, ByteSizeUnit.GIGABYTES), new ByteSize(500, ByteSizeUnit.MEGABYTES).convertTo(ByteSizeUnit.GIGABYTES));
        assertEquals(new ByteSize(9.765625, ByteSizeUnit.KIBIBYTES), new ByteSize(10, ByteSizeUnit.KILOBYTES).convertTo(ByteSizeUnit.KIBIBYTES));
        assertEquals(new ByteSize(10, ByteSizeUnit.MEGABYTES), new ByteSize(10, ByteSizeUnit.MEGABYTES).convertTo(ByteSizeUnit.MEGABYTES));
        ByteSize bs = new ByteSize(1, ByteSizeUnit.BYTES).convertTo(ByteSizeUnit.ZETTABYTES);
        assertEquals(1, bs.getBytesAsLong());
        assertEquals(new ByteSize(1, ByteSizeUnit.BYTES), bs.convertTo(ByteSizeUnit.BYTES));
    }

    @Test
    public void testEquality() {
        assertEquals(new ByteSize(500, ByteSizeUnit.MEGABYTES), new ByteSize(0.5, ByteSizeUnit.GIGABYTES));
        assertEquals(new ByteSize(500, ByteSizeUnit.MEBIBYTES), new ByteSize("0.48828125", ByteSizeUnit.GIBIBYTES));
    }
}
