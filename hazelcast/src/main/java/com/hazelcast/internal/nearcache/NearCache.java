/*
 * Copyright (c) 2008-2016, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.internal.nearcache;

import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.NearCachePreloaderConfig;
import com.hazelcast.internal.adapter.DataStructureAdapter;
import com.hazelcast.monitor.NearCacheStats;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.spi.InitializingObject;

/**
 * {@link NearCache} is the contract point to store keys and values in underlying
 * {@link NearCacheRecordStore}.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public interface NearCache<K, V> extends InitializingObject {

    /**
     * Default expiration task initial delay time as seconds
     */
    int DEFAULT_EXPIRATION_TASK_INITIAL_DELAY_IN_SECONDS = 5;

    /**
     * Default expiration task delay time as seconds
     */
    int DEFAULT_EXPIRATION_TASK_DELAY_IN_SECONDS = 5;

    /**
     * NULL Object
     */
    Object NULL_OBJECT = new Object();

    /**
     * Gets the name of this {@link NearCache} instance.
     *
     * @return the name of this {@link NearCache} instance
     */
    String getName();

    /**
     * Gets the value associated with the given {@code key}.
     *
     * @param key the key of the requested value
     * @return the value associated with the given {@code key}
     */
    V get(K key);

    /**
     * Puts (associates) a value with the given {@code key}.
     *
     * @param key   the key of the value will be stored
     * @param value the value will be stored
     */
    void put(K key, V value);

    /**
     * Removes the value associated with the given {@code key}.
     *
     * @param key the key of the value will be removed
     */
    boolean remove(K key);

    /**
     * Checks if values are invalidated on changes.
     *
     * @return {@code true} if values are invalidated on changes, {@code false} otherwise
     */
    boolean isInvalidatedOnChange();

    /**
     * Removes all stored values.
     */
    void clear();

    /**
     * Clears the record store and destroys it.
     */
    void destroy();

    /**
     * Gets the {@link com.hazelcast.config.InMemoryFormat} of the storage for internal records.
     *
     * @return the {@link com.hazelcast.config.InMemoryFormat} of the storage for internal records
     */
    InMemoryFormat getInMemoryFormat();

    /**
     * Gets the {@link com.hazelcast.config.NearCachePreloaderConfig} of this Near Cache.
     *
     * @return the {@link NearCachePreloaderConfig} of this Near Cache
     */
    NearCachePreloaderConfig getPreloaderConfig();

    /**
     * Get the {@link com.hazelcast.monitor.NearCacheStats} instance to monitor this store.
     *
     * @return the {@link com.hazelcast.monitor.NearCacheStats} instance to monitor this store
     */
    NearCacheStats getNearCacheStats();

    /**
     * Selects the best candidate object to store from the given {@code candidates}.
     *
     * @param candidates the candidates from which the best candidate object will be selected.
     * @return the best candidate object to store, selected from the given {@code candidates}.
     */
    Object selectToSave(Object... candidates);

    /**
     * Gets the count of stored records.
     *
     * @return the count of stored records
     */
    int size();

    /**
     * Executes the Near Cache pre-loader on the given {@link DataStructureAdapter}.
     */
    void preload(DataStructureAdapter<Data, ?> adapter);

    /**
     * Stores the keys of the Near Cache.
     */
    void storeKeys();

    /**
     * Checks if the pre-loading of the Near Cache is done.
     *
     * @return {@code true} if the pre-loading is done, {@code false} otherwise.
     */
    boolean isPreloadDone();

    /**
     * Used to access non-standard methods of an implementation.
     * <p>
     * If this method is called on a wrapper object, result is wrapped object.
     *
     * @param clazz the type of returning object.
     * @param <T>   the type of the class modeled by this Class object
     * @return an instance of the supplied clazz type.
     * @throws IllegalArgumentException if no implementation found for the supplied clazz type.
     */
    <T> T unwrap(Class<T> clazz);
}
