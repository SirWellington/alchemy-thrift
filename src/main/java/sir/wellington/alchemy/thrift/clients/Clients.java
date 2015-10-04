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

package sir.wellington.alchemy.thrift.clients;

import org.apache.thrift.TException;
import org.apache.thrift.TServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common operations performed on Thrift Clients.
 * 
 * @author SirWellington
 */
public class Clients
{

    private static final Logger LOG = LoggerFactory.getLogger(Clients.class);

    /**
     * Closes a Thrift Client, both the Input and Output Transports.
     *
     * @param client The client to close. Will be ignored if null.
     *
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
