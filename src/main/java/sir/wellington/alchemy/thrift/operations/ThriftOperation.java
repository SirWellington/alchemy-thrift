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
package sir.wellington.alchemy.thrift.operations;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;

/**
 * This interface encourages your Thrift Services to represent each Operation separately. Each
 * Thrift Operation should have a single Request Object and a single Response Object.
 *
 * This is very flexible model which allows a service to evolve its input and output without
 * adversely affecting the clients.
 *
 * @param <Request>  The Request Object the Operation accepts
 * @param <Response> The Response Object the Operation returns. Use {@code <Void>} for none.
 *
 * @author SirWellington
 */
public interface ThriftOperation<Request extends TBase, Response extends TBase>
{

    /**
     * Process the Request and return a Response.
     *
     * @param request The Request Object for the operation
     *
     * @return A Response for the operation
     *
     * @throws TException
     */
    Response process(Request request) throws TException;
}
