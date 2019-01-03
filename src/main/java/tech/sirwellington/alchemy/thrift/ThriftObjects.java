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

import org.apache.thrift.*;
import org.apache.thrift.protocol.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.sirwellington.alchemy.annotations.access.Internal;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;
import tech.sirwellington.alchemy.annotations.arguments.NonEmpty;
import tech.sirwellington.alchemy.annotations.arguments.Required;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * A Set of Operations that simplify Serialization and Deserialization of Thrift Objects.
 *
 * @author SirWellington
 */
@NonInstantiable
public class ThriftObjects
{

    private ThriftObjects() throws IllegalAccessException
    {
        throw new IllegalAccessException("cannot instantiate");
    }

    private static final Logger LOG = LoggerFactory.getLogger(ThriftObjects.class);

    /**
     * Converts a Thrift Object into a Pretty Print JSON format which cannot be read back.
     *
     * @param <T>    The type of the Thrift Object
     * @param object The object to serialize
     * @return JSON Representation of the Thrift Object, in a UTF-8 String.
     * @throws TException IF Serialization fails
     * @see #fromPrettyJson(org.apache.thrift.TBase, java.lang.String)
     */
    public static <T extends TBase> String toPrettyJson(@Required T object) throws TException
    {
        checkNotNull(object, "Thrift object is null");

        TProtocolFactory protocol = new TSimpleJSONProtocol.Factory();
        TSerializer serializer = new TSerializer(protocol);

        byte[] data = serializer.serialize(object);
        String json = new String(data, UTF_8);

        LOG.debug("TObject {} converted to Pretty JSON: {}", object, json);
        return json;
    }

    /**
     * Converts a Thrift Object into a Simple JSON format.
     *
     * @param <T>    The type of the Thrift Object
     * @param object The object to serialize
     * @return JSON Representation of the Thrift Object.
     * @throws TException
     * @see #fromJson(org.apache.thrift.TBase, java.lang.String)
     */
    public static <T extends TBase> String toJson(@Required T object) throws TException
    {
        checkNotNull(object);

        TProtocolFactory protocol = new TJSONProtocol.Factory();
        TSerializer serializer = new TSerializer(protocol);

        byte[] data = serializer.serialize(object);
        String json = new String(data, UTF_8);

        LOG.debug("TObject {} converted to JSON: {}", object, json);
        return json;
    }

    /**
     * Inflates the prototype object from the supplied JSON.
     *
     * @param <T>       The type of the Thrift Object
     * @param prototype The prototype Object to deserialize into
     * @param json      The Simple JSON generated from {@link #toJson(org.apache.thrift.TBase) }
     * @return The Deserialized Prototype Object.
     * @throws TException
     */
    public static <T extends TBase> T fromJson(@Required T prototype, @NonEmpty String json) throws TException
    {
        checkNotNull(prototype, "missing prototype");

        if (isNullOrEmpty(json))
        {
            LOG.warn("JSON String is empty");
            return prototype;
        }

        TProtocolFactory protocol = new TJSONProtocol.Factory();
        TDeserializer deserializer = new TDeserializer(protocol);
        deserializer.deserialize(prototype, json, UTF_8.name());
        LOG.debug("Prototype TObject inflated to {} from json {}", prototype, json);
        return prototype;
    }

    public static <T extends TBase> byte[] toBinary(@Required T object) throws TException
    {
        checkNotNull(object);

        TProtocolFactory protocol = new TBinaryProtocol.Factory(true, true);
        TSerializer serializer = new TSerializer(protocol);

        byte[] data = serializer.serialize(object);
        LOG.debug("TObject {} converted to binary of length {}", data.length);
        return data;
    }

    public static <T extends TBase> T fromBinary(@Required T prototype, @NonEmpty byte[] binary) throws TException
    {
        checkNotNull(prototype, "missing prototype");

        if (binary == null || binary.length == 0)
        {
            LOG.warn("missing binary: {}", binary);
            return prototype;
        }

        TProtocolFactory protocol = new TBinaryProtocol.Factory(true, true);
        TDeserializer deserializer = new TDeserializer(protocol);
        deserializer.deserialize(prototype, binary);
        LOG.debug("Binary with length {} deserialized into TObject {}", binary.length, protocol);
        return prototype;
    }

    private static boolean isNullOrEmpty(String string)
    {
        return string == null || string.isEmpty();
    }

    @Internal
    static void checkNotNull(Object argument)
    {
        checkNotNull(argument, "null argument");
    }

    @Internal
    static void checkNotNull(Object argument, String message)
    {
        if (argument == null)
        {
            throw new IllegalArgumentException(message);
        }
    }

}
