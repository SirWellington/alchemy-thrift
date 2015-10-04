package sir.wellington.alchemy.thrift;

import static com.google.common.base.Charsets.UTF_8;
import com.google.common.base.Strings;
import org.apache.thrift.TBase;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import static org.hamcrest.Matchers.is;
import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import sir.wellington.alchemy.test.DataGenerator;
import static sir.wellington.alchemy.test.DataGenerator.booleans;
import static sir.wellington.alchemy.test.DataGenerator.hexadecimalString;
import static sir.wellington.alchemy.test.DataGenerator.oneOf;
import sir.wellington.alchemy.thrift.generated.Android;
import sir.wellington.alchemy.thrift.generated.Iphone;
import sir.wellington.alchemy.thrift.generated.Phone;
import sir.wellington.alchemy.thrift.generated.SampleRequest;

/**
 *
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

        boolean branch = oneOf(booleans());

        Phone phone = new Phone();
        String deviceId = oneOf(DataGenerator.uuids);
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
        assertFalse(Strings.isNullOrEmpty(json));
        assertTrue(json.contains(object.argument));
    }

    @Test
    public void testFromPrettyJson() throws Exception
    {
        System.out.println("testFromPrettyJson");

        object.unsetPhone();

        TMemoryBuffer memory = new TMemoryBuffer(1024);
        TSimpleJSONProtocol protocol = new TSimpleJSONProtocol(memory);
        object.write(protocol);
        String json = memory.toString(UTF_8.name());

        TBase result = ThriftObjects.fromPrettyJson(new SampleRequest(), json);
        assertThat(result, is(object));
    }
    
    @Test
    public void testFromPrettyJsonWithBadArgs() throws Exception
    {
        System.out.println("testFromPrettyJsonWithBadArgs");
        
        SampleRequest request = new SampleRequest();
        String json = null;
        
        SampleRequest result = ThriftObjects.fromPrettyJson(request, json);
        assertThat(result, is(request));

        json = "";
        result = ThriftObjects.fromPrettyJson(request, json);
        assertThat(result, is(request));
    }

    @Test
    public void testToJson() throws Exception
    {
        System.out.println("toJson");

        String json = ThriftObjects.toJson(object);
        assertFalse(Strings.isNullOrEmpty(json));
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
}
