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

/**
 * Represents the possible standards that a {@link ByteSizeUnit} can have. Different standards represent different
 * "power of" values for which byte sizes are defined in.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Binary_prefix">https://en.wikipedia.org/wiki/Binary_prefix</a>
 */
public enum ByteSizeStandard {

    /**
     * The International System of Units (SI) standard. Base of 1000.
     */
    SI(1000),

    /**
     * The International Electrotechnical Commission (IEC) standard. Base of 1024.
     */
    IEC(1024);

    final int powerOf;

    ByteSizeStandard(int powerOf) {
        this.powerOf = powerOf;
    }
}
