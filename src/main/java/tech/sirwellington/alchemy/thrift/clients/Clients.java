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

package tech.sirwellington.alchemy.thrift.clients;

import org.apache.thrift.TException;
import org.apache.thrift.TServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;

/**
 * Common operations performed on Thrift Clients.
 *
 * @author SirWellington
 */
@NonInstantiable
public class Clients
{

    private static final Logger LOG = LoggerFactory.getLogger(Clients.class);

    Clients() throws IllegalAccessException
    {
        throw new IllegalAccessException("cannot instantiate");
    }

    /**
     * This function attempts to close Thrift Client, similar to {@link #close(org.apache.thrift.TServiceClient) }.
     * <p>
     * This function differs in that it accepts a generic Object type.
     * <p>ß
     * This is necessary because Thrift {@code Iface}'s don't contain protocol information, and hence cannot
     * be closed. This prevents programming to the interface while retaining the ability to close the client.
     * This operation closes the client if it's a {@link TServiceClient}, and ignores it otherwise.
     *
     * @param client Expected to be of type {@link TServiceClient}, ignored otherwise.
     * @throws TException
     */
    public static void attemptClose(Object client) throws TException
    {
        if (client == null)
        {
            return;
        }

        if (!(client instanceof TServiceClient))
        {
            return;
        }

        TServiceClient thriftClient = (TServiceClient) client;
        close(thriftClient);
    }

    /**
     * Like {@link #attemptClose(java.lang.Object) }, but swallows any exceptions that occurs
     * in the attempt to close the Client.
     *
     * @param client
     */
    public static void attemptCloseSilently(Object client)
    {
        try
        {
            attemptClose(client);
        }
        catch (TException ex)
        {
            LOG.info("Could not silently close client: {}", client, ex);
        }
    }


    /**
     * Closes a Thrift Client, both the Input and Output Transports.
     *
     * @param client The client to close. Will be ignored if null.
     * @throws TException If the Client cannot be closed
     */
    public static void close(TServiceClient client) throws TException
    {
        if (client != null)
        {
            try
            {
                if (client.getInputProtocol() != null && client.getInputProtocol().getTransport() != null)
                {
                    client.getInputProtocol().getTransport().close();
                }
                if (client.getOutputProtocol() != null && client.getOutputProtocol().getTransport() != null)
                {
                    client.getOutputProtocol().getTransport().close();
                }
            }
            catch (Exception ex)
            {
                throw new TException(ex);
            }
        }
    }

    /**
     * Attempts to close a Thrift Client silently. Any Exceptions will be logged and swallowed.
     *
     * @param client The Client to close. If null will be ignored.
     */
    public static void closeSilently(TServiceClient client)
    {
        try
        {
            close(client);
        }
        catch (TException ex)
        {
            LOG.error("Failed to close Thrift Client {}", client, ex);
        }
    }

}
