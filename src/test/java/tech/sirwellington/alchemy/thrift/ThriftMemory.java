/*
 * Copyright 2015 Wellington.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tech.sirwellington.alchemy.thrift;

import static com.google.common.base.Charsets.UTF_8;
import com.google.common.base.Preconditions;
import java.io.UnsupportedEncodingException;
import org.apache.thrift.transport.TMemoryBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author SirWellington
 */
class ThriftMemory
{

    private final static Logger LOG = LoggerFactory.getLogger(ThriftMemory.class);

    /**
     * This function properly extracts the Buffer from the {@link  TMemoryBuffer}, since the built-in {@link TMemoryBuffer#getArray()
     * } is broken.
     *
     * @param buffer
     *
     * @return A properly formatted buffer, without the trailing characters.
     *
     * @throws UnsupportedEncodingException
     */
    public static byte[] readBufferFrom(TMemoryBuffer buffer) throws UnsupportedEncodingException
    {
        Preconditions.checkNotNull(buffer);
        return buffer.toString(UTF_8.name()).getBytes(UTF_8);
    }

}
