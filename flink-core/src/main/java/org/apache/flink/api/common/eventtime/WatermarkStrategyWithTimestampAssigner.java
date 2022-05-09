/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.api.common.eventtime;

/**
 * 这个跟代理模式有点像，或者说是部分代理。可以这么进行理解。
 * A {@link WatermarkStrategy} that overrides the {@link TimestampAssigner} of the given base {@link
 * WatermarkStrategy}.
 */
final class WatermarkStrategyWithTimestampAssigner<T> implements WatermarkStrategy<T> {

    private static final long serialVersionUID = 1L;

    /**
     * 负责调用 createWatermarkGenerator方法
     */
    private final WatermarkStrategy<T> baseStrategy;
    /**
     * 负责调用 createTimestampAssigner方法
     */
    private final TimestampAssignerSupplier<T> timestampAssigner;

    WatermarkStrategyWithTimestampAssigner(
            WatermarkStrategy<T> baseStrategy, TimestampAssignerSupplier<T> timestampAssigner) {
        this.baseStrategy = baseStrategy;
        this.timestampAssigner = timestampAssigner;
    }

    @Override
    public TimestampAssigner<T> createTimestampAssigner(TimestampAssignerSupplier.Context context) {
        return timestampAssigner.createTimestampAssigner(context);
    }

    @Override
    public WatermarkGenerator<T> createWatermarkGenerator(
            WatermarkGeneratorSupplier.Context context) {
        return baseStrategy.createWatermarkGenerator(context);
    }
}
