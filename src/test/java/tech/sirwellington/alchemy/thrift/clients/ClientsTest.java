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
package tech.sirwellington.alchemy.thrift.clients;

import org.apache.thrift.TException;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import tech.sirwellington.alchemy.test.junit.ExceptionOperation;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.mockito.Mockito.*;
import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.*;

/**
 * @author SirWellington
 */
@Repeat(20)
@RunWith(AlchemyTestRunner.class)
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
        Clients.close(client);
        verify(transport, atLeastOnce()).close();

        Clients.close(null);
    }

    @Test
    public void testCloseWhenFails() throws Exception
    {
        doThrow(new IllegalStateException()).when(transport).close();

        ExceptionOperation op = new ExceptionOperation()
        {
            @Override
            public void call() throws Throwable
            {
                Clients.close(client);
            }
        };

        assertThrows(op).isInstanceOf(TException.class);
    }

    @Test
    public void testCloseSilently()
    {
        Clients.closeSilently(client);
        verify(transport, atLeastOnce()).close();
        Clients.closeSilently(null);

        doThrow(new IllegalStateException()).when(transport).close();
        Clients.closeSilently(client);
    }

    @Test
    public void testCloseWhenTransportIsNull() throws TException
    {
        when(protocol.getTransport()).thenReturn(null);
        Clients.close(client);

        when(protocol.getTransport()).thenReturn(transport);
        when(client.getInputProtocol()).thenReturn(null);
        Clients.close(client);
        when(client.getOutputProtocol()).thenReturn(null);
        Clients.close(client);
    }

    @Test
    public void testAttemptClose() throws Exception
    {
        Object object = mock(Object.class);

        Clients.attemptClose(object);
        verifyZeroInteractions(object);

        Clients.attemptClose(null);

        Clients.attemptClose(client);
        verify(transport, atLeastOnce()).close();
    }

    @Test
    public void testAttemptCloseSilently()
    {
        Object object = mock(Object.class);
        Clients.attemptCloseSilently(object);
        verifyZeroInteractions(object);

        Clients.attemptCloseSilently(client);
        verify(transport, atLeastOnce()).close();
    }
}
