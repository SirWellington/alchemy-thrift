/*
 * Copyright 2015 SirWellington.
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
package sir.wellington.alchemy.thrift.clients;

import org.apache.thrift.TException;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import static sir.wellington.alchemy.test.junit.ThrowableAssertion.assertThrows;

/**
 *
 * @author SirWellington
 */
@RunWith(MockitoJUnitRunner.class)
public class ClientsTest
{

    @Mock
    private TServiceClient client;

    @Mock
    private TProtocol protocol;

    @Mock
    private TTransport transport;

    @Before
    public void setUp()
    {
        when(client.getInputProtocol()).thenReturn(protocol);
        when(client.getOutputProtocol()).thenReturn(protocol);
        when(protocol.getTransport()).thenReturn(transport);
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testClose() throws Exception
    {
        System.out.println("testClose");
        Clients.close(client);
        verify(transport, atLeastOnce()).close();

        Clients.close(null);

        doThrow(new IllegalStateException()).when(transport).close();
        assertThrows(() -> Clients.close(client))
                .isInstanceOf(TException.class);
    }

    @Test
    public void testCloseSilently()
    {
        System.out.println("closeSilently");
        Clients.closeSilently(client);
        verify(transport, atLeastOnce()).close();
        Clients.closeSilently(null);

        doThrow(new IllegalStateException()).when(transport).close();
        Clients.closeSilently(client);
    }

    @Test
    public void testCloseWhenTransportIsNull() throws TException
    {
        System.out.println("testCloseWhenTransportIsNull");
        
        when(protocol.getTransport()).thenReturn(null);
        Clients.close(client);
        
        when(protocol.getTransport()).thenReturn(transport);
        when(client.getInputProtocol()).thenReturn(null);
        Clients.close(client);
        when(client.getOutputProtocol()).thenReturn(null);
        Clients.close(client);
    }
}
