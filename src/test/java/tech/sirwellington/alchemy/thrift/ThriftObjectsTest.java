/*
 * Copyright © 2019. Sir Wellington.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.sirwellington.alchemy.thrift;


import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import sir.wellington.alchemy.thrift.generated.*;
import tech.sirwellington.alchemy.generator.StringGenerators;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static tech.sirwellington.alchemy.generator.BooleanGenerators.booleans;
import static tech.sirwellington.alchemy.generator.StringGenerators.hexadecimalString;

/**
 * @author JMoreno
 */
@RunWith(MockitoJUnitRunner.class)
public class ThriftObjectsTest
{

    private SampleRequest object;

    @Before
    public void setUp()
    {
        object = new SampleRequest();
        object.argument = hexadecimalString(30).get();

        boolean branch = booleans().get();

        Phone phone = new Phone();
        String deviceId = StringGenerators.uuids.get();
        if (branch)
        {
            phone.setAndroid(new Android(deviceId));
        }
        else
        {
            phone.setIphone(new Iphone(deviceId));
        }
        object.setPhone(phone);
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void testToPrettyJson() throws Exception
    {
        System.out.println("testToPrettyJson");
        String json = ThriftObjects.toPrettyJson(object);
        assertFalse(isNullOrEmpty(json));
        assertTrue(json.contains(object.argument));
    }

    @Test
    public void testToJson() throws Exception
    {
        System.out.println("toJson");

        String json = ThriftObjects.toJson(object);
        assertFalse(isNullOrEmpty(json));
        assertTrue(json.contains(object.argument));
    }

    @Test
    public void testFromJson() throws Exception
    {
        System.out.println("fromJson");

        TMemoryBuffer memoryTransport = new TMemoryBuffer(1024);
        TJSONProtocol protocol = new TJSONProtocol(memoryTransport);
        object.write(protocol);
        memoryTransport.close();

        byte[] data = ThriftMemory.readBufferFrom(memoryTransport);
        String json = new String(data, UTF_8);

        SampleRequest result = ThriftObjects.fromJson(object, json);

        assertThat(result, is(object));

    }

    @Test
    public void testFromJsonWithBadArgs() throws Exception
    {
        System.out.println("testFromJsonWithBadArgs");

        SampleRequest request = new SampleRequest();
        String json = null;

        SampleRequest result = ThriftObjects.fromJson(request, json);
        assertThat(result, is(request));
    }

    @Test
    public void testToBinary() throws Exception
    {
        System.out.println("testToBinary");

        TMemoryBuffer memory = new TMemoryBuffer(32);
        TBinaryProtocol protocol = new TBinaryProtocol(memory);

        object.write(protocol);
        memory.close();
        byte[] expected = ThriftMemory.readBufferFrom(memory);

        byte[] result = ThriftObjects.toBinary(object);
        assertThat(result, is(expected));
    }

    @Test
    public void testFromBinary() throws Exception
    {
        System.out.println("testFromBinary");

        TMemoryBuffer memory = new TMemoryBuffer(1024);
        TBinaryProtocol protocol = new TBinaryProtocol(memory, true, true);

        object.write(protocol);
        byte[] binary = ThriftMemory.readBufferFrom(memory);

        SampleRequest result = ThriftObjects.fromBinary(new SampleRequest(), binary);
        assertThat(result, is(object));
    }

    @Test
    public void testFromBinaryWithBadArgs() throws Exception
    {
        System.out.println("testFromBinaryWithBadArgs");

        SampleRequest request = new SampleRequest();
        byte[] binary = null;

        SampleRequest result = ThriftObjects.fromBinary(request, binary);
        assertThat(result, is(request));

        binary = new byte[0];
        result = ThriftObjects.fromBinary(request, binary);
        assertThat(result, is(request));

    }

    private boolean isNullOrEmpty(String string)
    {
        return string == null || string.isEmpty();
    }
}
