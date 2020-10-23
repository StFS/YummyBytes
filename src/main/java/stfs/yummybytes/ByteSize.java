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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * A unit of byte size, such as "512 kilobytes".
 * <p>
 * This class models a two part byte count size, one part being a value and the other part being a
 * {@link ByteSizeUnit}.
 * <p>
 * This class supports converting to another {@link ByteSizeUnit}.
 */
public class ByteSize {
    private final BigDecimal value;
    private final ByteSizeUnit unit;

    /**
     * Creates a byte size value from two parts, a value and a {@link ByteSizeUnit}.
     *
     * @param value the value part of this byte size.
     * @param unit  the unit part of this byte size.
     */
    public ByteSize(BigDecimal value, ByteSizeUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    /**
     * Creates a byte size value from a <code>long</code> value representing the number of bytes.
     * <p>
     * The unit part of this byte size will be {@link ByteSizeUnit#BYTES}.
     *
     * @param bytes the number of bytes this {@link ByteSize} instance should represent
     */
    public ByteSize(long bytes) {
        this(bytes, ByteSizeUnit.BYTES);
    }

    /**
     * Creates a byte size value from a <code>String</code> value and a {@link ByteSizeUnit}.
     *
     * @param value the value part of this byte size
     * @param unit  the unit part of this byte size
     */
    public ByteSize(String value, ByteSizeUnit unit) {
        this(new BigDecimal(value), unit);
    }

    /**
     * Creates a byte size value from a <code>long</code> value and a {@link ByteSizeUnit}.
     *
     * @param value the value part of this byte size
     * @param unit  the unit part of this byte size
     */
    public ByteSize(long value, ByteSizeUnit unit) {
        this(BigDecimal.valueOf(value), unit);
    }

    /**
     * Creates a byte size value from a <code>double</code> value and a {@link ByteSizeUnit}.
     *
     * @param value the value part of this byte size
     * @param unit  the unit part of this byte size
     */
    public ByteSize(double value, ByteSizeUnit unit) {
        this(BigDecimal.valueOf(value), unit);
    }

    /**
     * Returns the number of bytes that this byte size represents after multiplying the unit factor with the value.
     * <p>
     * Since the value part can be a represented by a decimal, there is some possibility of a rounding error. Therefore,
     * the result of multiplying the value and the unit factor are always rounded towards positive infinity to the
     * nearest integer value (see {@link RoundingMode#CEILING}) to make sure that this method never gives values that
     * are too small.
     *
     * @return number of bytes this byte size represents after factoring in the unit.
     */
    public BigInteger getBytes() {
        return value.multiply(unit.getFactor()).setScale(0, RoundingMode.CEILING).toBigIntegerExact();
    }

    /**
     * Returns the number of bytes that this byte size represents as a <code>long</code> after multiplying the unit
     * factor with the value, throwing an exception if the result overflows a <code>long</code>.
     *
     * @return the number of bytes that this byte size represents after factoring in the unit.
     * @throws ArithmeticException if the result overflows a <code>long</code>
     */
    public long getBytesAsLong() {
        return getBytes().longValueExact();
    }

    /**
     * Returns the number of bytes that this byte size represents as an <code>int</code> after multiplying the unit
     * factor with the value, throwing an exception if the result overflows an <code>int</code>.
     *
     * @return the number of bytes that this byte size represents after factoring in the unit.
     * @throws ArithmeticException if the result overflows an <code>int</code>
     */
    public int getBytesAsInt() {
        return getBytes().intValueExact();
    }

    /**
     * Creates a new {@link ByteSize} representing the same byte size but in a different unit.
     * <p>
     * Scale of the value (number of decimal points) is handled automatically but if a non-terminating decimal expansion
     * occurs, an {@link ArithmeticException} is thrown.
     *
     * @param unit the unit for the new {@link ByteSize}.
     * @return a new {@link ByteSize} instance representing the same byte size as this but using the specified unit.
     * @throws ArithmeticException if a non-terminating decimal expansion occurs during calculation.
     */
    public ByteSize convertTo(ByteSizeUnit unit) {
        BigDecimal bytes = this.value.multiply(this.unit.getFactor()).setScale(0, RoundingMode.CEILING);
        return new ByteSize(bytes.divide(unit.getFactor()), unit);
    }

    @Override
    public String toString() {
        return value.toString() + " " + unit.toStringShortForm();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ByteSize byteSize = (ByteSize) o;

        return getBytes().equals(byteSize.getBytes());
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + unit.hashCode();
        return result;
    }
}